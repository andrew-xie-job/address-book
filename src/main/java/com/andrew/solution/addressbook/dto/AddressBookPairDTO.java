package com.andrew.solution.addressbook.dto;

import java.io.Serializable;

public class AddressBookPairDTO implements Serializable {
    private final String fromName;
    private final String toName;

    public AddressBookPairDTO(String fromName, String toName) {
        this.fromName = fromName;
        this.toName = toName;
    }

    public String getFromName() {
        return fromName;
    }

    public String getToName() {
        return toName;
    }

}
