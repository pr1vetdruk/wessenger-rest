package ru.privetdruk.wessengerrest.domain;

public final class Views {
    public interface Id {}
    public interface Text{}
    public interface IdText extends Id, Text {}
    public interface FullMessage extends IdText {}
}
