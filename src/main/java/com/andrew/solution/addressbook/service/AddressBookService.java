package com.andrew.solution.addressbook.service;

import com.andrew.solution.addressbook.dto.AddressBookPairDTO;
import com.andrew.solution.addressbook.dto.ContactDTO;
import com.andrew.solution.addressbook.model.Contact;
import com.andrew.solution.addressbook.model.InputItem;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AddressBookService {
    private final ContactDataService contactService;

    public AddressBookService(ContactDataService contactService) {
        this.contactService = contactService;
    }

    public Set<String> getAddressBookNameSet() {
        return contactService.readContacts().keySet();
    }

    public List<Contact> getAllContactsPerAddressBook(String addressBookName) {
        return contactService.readContacts()
                .get(addressBookName)
                .stream()
                .sorted(Comparator.comparing(Contact::getName))
                .collect(Collectors.toList());
    }

    public void createContactForAddressBook(ContactDTO dto) {
        InputItem item = new InputItem(dto.getAddressBookName(), dto.getContactName(), dto.getPhoneNumber());
        contactService.saveContact(item);
    }

    public Set<String> getUniqueContactNameFromAddressBooks(AddressBookPairDTO addressBookPairDTO) {
        Map<String, List<Contact>> allContacts = contactService.readContacts();
        Set<String> fromNameSet = getContactNameSet(addressBookPairDTO.getFromName(), allContacts);
        Set<String> toNameSet = getContactNameSet(addressBookPairDTO.getToName(), allContacts);
        Set<String> filteredFromNameSet = filterUniqueContactName(fromNameSet, toNameSet);
        Set<String> filteredToNameSet = filterUniqueContactName(toNameSet, fromNameSet);
        filteredFromNameSet.addAll(filteredToNameSet);
        return filteredFromNameSet;
    }

    private Set<String> filterUniqueContactName(Set<String> fromNameSet, Set<String> toNameSet) {
        return fromNameSet.stream()
                .filter(contactName -> toNameSet.stream().noneMatch(name -> name.equals(contactName)))
                .collect(Collectors.toSet());
    }

    private Set<String> getContactNameSet(String addressBookName, Map<String, List<Contact>> allContacts) {
        return allContacts.get(addressBookName)
                .stream()
                .map(Contact::getName)
                .collect(Collectors.toSet());
    }
}
