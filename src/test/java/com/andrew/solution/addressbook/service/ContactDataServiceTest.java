package com.andrew.solution.addressbook.service;

import com.andrew.solution.addressbook.exception.InvalidInputFileException;
import com.andrew.solution.addressbook.model.Contact;
import com.andrew.solution.addressbook.model.InputItem;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContactDataServiceTest {

    private static final String CONTACTS_CSV = "contacts.csv";
    private ContactDataService contactDataService;

    @Test
    public void readContacts() {
        contactDataService = new ContactDataService(CONTACTS_CSV);
        Contact expectedContact = new Contact("Bob", "0422222222");

        Map<String, List<Contact>> contacts = contactDataService.readContacts();

        assertThat(contacts.size()).isEqualTo(2);
        assertThat(contacts.get("Book1")).contains(expectedContact);
    }

    @Test
    public void readContacts_WillFail_WithInvalidFileName() {
        contactDataService = new ContactDataService("invalid-fileName.csv");
        Throwable exception = assertThrows(InvalidInputFileException.class,
                () -> contactDataService.readContacts());
        assertTrue(exception.getMessage().contains("Cannot Read Contact List File"));
    }

    @Test
    public void savContacts() throws IOException {
        String fileName = "save-contacts.csv";
        contactDataService = new ContactDataService(fileName);
        InputItem appendContact = new InputItem("Book3", "Bob", "0422222222");

        contactDataService.saveContact(appendContact);
        assertThat(StreamUtils.getStringFromInputStream(fileName)).contains("Book3");

    }

}
