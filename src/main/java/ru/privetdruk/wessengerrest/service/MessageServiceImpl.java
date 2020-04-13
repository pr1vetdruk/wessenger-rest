package ru.privetdruk.wessengerrest.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.privetdruk.wessengerrest.domain.Message;
import ru.privetdruk.wessengerrest.domain.User;
import ru.privetdruk.wessengerrest.domain.UserSubscription;
import ru.privetdruk.wessengerrest.domain.Views;
import ru.privetdruk.wessengerrest.dto.EventType;
import ru.privetdruk.wessengerrest.dto.MessagePageDto;
import ru.privetdruk.wessengerrest.dto.MetaDto;
import ru.privetdruk.wessengerrest.dto.ObjectType;
import ru.privetdruk.wessengerrest.repository.MessageRepository;
import ru.privetdruk.wessengerrest.repository.UserSubscriptionRepository;
import ru.privetdruk.wessengerrest.util.WebSocketSender;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {
    private static final Pattern URL_REGEX = Pattern.compile("https?://?[\\w\\d._\\-%/?=&#]+", Pattern.CASE_INSENSITIVE);
    private static final Pattern IMG_REGEX = Pattern.compile("\\.(jpeg|jpg|gif|png)$", Pattern.CASE_INSENSITIVE);

    private final MessageRepository messageRepository;
    private final UserSubscriptionRepository subscriptionRepository;
    private final BiConsumer<EventType, Message> webSocketSender;

    public MessageServiceImpl(MessageRepository messageRepository, UserSubscriptionRepository subscriptionRepository, WebSocketSender webSocketSender) {
        this.messageRepository = messageRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.webSocketSender = webSocketSender.getSender(ObjectType.MESSAGE, Views.FullMessage.class);
    }

    @Override
    public Message create(Message message, User user) throws IOException {
        fillMeta(message);
        message.setAuthor(user);
        message.setCreationDate(LocalDateTime.now());
        Message updatedMessage = messageRepository.save(message);
        webSocketSender.accept(EventType.CREATE, updatedMessage);
        return updatedMessage;
    }

    @Override
    public Message update(Message newMessage, Message oldMessage) throws IOException {
        oldMessage.setText(newMessage.getText());
        fillMeta(oldMessage);
        Message updatedMessage = messageRepository.save(oldMessage);
        webSocketSender.accept(EventType.UPDATE, updatedMessage);
        return updatedMessage;
    }

    @Override
    public void delete(Message message) {
        messageRepository.delete(message);
        webSocketSender.accept(EventType.REMOVE, message);
    }

    @Override
    public MessagePageDto findForUser(User user, Pageable pageable) {
        List<User> channels = subscriptionRepository.findBySubscriber(user)
                .stream()
                .filter(UserSubscription::isActive)
                .map(UserSubscription::getChannel)
                .collect(Collectors.toList());

        channels.add(user);

        Page<Message> page = messageRepository.findByAuthorIn(channels, pageable);

        return new MessagePageDto(page.getContent(), pageable.getPageNumber(), page.getTotalPages());
    }

    private void fillMeta(Message message) throws IOException {
        String text = message.getText();
        Matcher matcher = URL_REGEX.matcher(text);

        if (matcher.find()) {
            String url = text.substring(matcher.start(), matcher.end());
            message.setLink(url);

            matcher = IMG_REGEX.matcher(url);

            if (matcher.find()) {
                message.setLinkCover(url);
            } else if (!url.contains("youtu")) {
                MetaDto meta = getMeta(url);

                message.setLinkCover(meta.getCover());
                message.setLinkTitle(meta.getTitle());
                message.setLinkDescription(meta.getDescription());
            }
        }
    }

    private MetaDto getMeta(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();

        Elements title = doc.select("meta[name$=title], meta[property$=title]");
        Elements description = doc.select("meta[name$=description], meta[property$=description]");
        Elements cover = doc.select("meta[name$=image], meta[property$=image]");

        return new MetaDto(getContent(title.first()), getContent(description.first()), getContent(cover.first()));
    }

    private String getContent(Element element) {
        return element == null ? "" : element.attr("content");
    }
}
