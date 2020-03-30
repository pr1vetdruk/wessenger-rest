package ru.privetdruk.wessengerrest.domain;

public final class Views {
    public interface Message {
        interface Id {
        }

        interface Text {
        }

        interface IdText extends Id, Text {
        }

        interface Full extends IdText {
        }
    }

}
