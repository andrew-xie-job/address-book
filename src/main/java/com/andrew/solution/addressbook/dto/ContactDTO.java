package com.andrew.solution.addressbook.dto;

import java.io.Serializable;

public class ContactDTO implements Serializable {

    private final String addressBookName;
    private final String contactName;
    private final String phoneNumber;

    public ContactDTO(String addressBookName, String contactName, String phoneNumber) {
        this.addressBookName = addressBookName;
        this.contactName = contactName;
        this.phoneNumber = phoneNumber;
    }

    public String getAddressBookName() {
        return addressBookName;
    }

    public String getContactName() {
        return contactName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
