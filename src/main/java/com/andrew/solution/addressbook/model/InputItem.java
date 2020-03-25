package com.andrew.solution.addressbook.model;

public class InputItem {

    private final String bookName;
    private final String name;
    private final String phoneNumber;

    public InputItem(String bookName, String name, String phoneNumber) {
        this.bookName = bookName;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getBookName() {
        return bookName;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return   bookName + "," + name + "," + phoneNumber + System.lineSeparator();
    }
}
