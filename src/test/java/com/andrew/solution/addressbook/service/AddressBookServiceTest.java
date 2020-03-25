package com.andrew.solution.addressbook.service;

import com.andrew.solution.addressbook.dto.AddressBookPairDTO;
import com.andrew.solution.addressbook.dto.ContactDTO;
import com.andrew.solution.addressbook.model.Contact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class AddressBookServiceTest {

    private static final String CONTACTS_CSV = "contacts.csv";

    private AddressBookService addressBookService;
    private ContactDataService contactService;

    @BeforeEach
    void setup() {
        contactService = new ContactDataService(CONTACTS_CSV);
        addressBookService = new AddressBookService(contactService);
    }


    @Test
    public void getAddressBookNameList() {
        Set<String> expectedName = new HashSet<>();
        expectedName.add("Book1");
        expectedName.add("Book2");

        Set<String> addressBookNames = addressBookService.getAddressBookNameSet();

        assertThat(addressBookNames.size()).isEqualTo(2);
        assertThat(addressBookNames.equals(expectedName)).isTrue();
    }

    @Test
    public void getAllContactsPerAddressBook() {
        String firstContactName = "Bob";

        List<Contact> allContacts = addressBookService.getAllContactsPerAddressBook("Book1");

        assertThat(allContacts.size()).isEqualTo(3);
        assertThat(allContacts.get(0).getName()).isEqualTo(firstContactName);
    }

    @Test
    public void createContactForAddressBook() throws IOException {
        String fileName = "save-contacts.csv";
        ContactDTO dto = new ContactDTO("Book4", "name","99999999");

        contactService = new ContactDataService(fileName);
        addressBookService = new AddressBookService(contactService);
        addressBookService.createContactForAddressBook(dto);

        assertThat(StreamUtils.getStringFromInputStream(fileName)).contains("Book4");
    }

    @Test
    public void getUniqueContactNameFromAddressBooks() {
        String fromPhoneBookName = "Book1";
        String toPhoneBookName = "Book2";
        AddressBookPairDTO dto = new AddressBookPairDTO(fromPhoneBookName, toPhoneBookName);
        Set<String> expectedNames = new HashSet<>();
        expectedNames.add("Bob");
        expectedNames.add("John");

        Set<String> uniqueNameSet = addressBookService.getUniqueContactNameFromAddressBooks(dto);

        assertThat(uniqueNameSet.size()).isEqualTo(2);
        assertThat(uniqueNameSet).isEqualTo(expectedNames);
    }
}
