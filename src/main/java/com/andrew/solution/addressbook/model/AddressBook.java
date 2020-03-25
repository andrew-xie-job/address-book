package com.andrew.solution.addressbook.model;

import java.io.Serializable;
import java.util.Set;

public class AddressBook implements Serializable {

    private String name;
    private Set<Contact> contacts;

    public AddressBook() {
    }

    public AddressBook(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }
}
