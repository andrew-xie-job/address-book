package com.andrew.solution.addressbook.controller;


import com.andrew.solution.addressbook.dto.AddressBookPairDTO;
import com.andrew.solution.addressbook.dto.ContactDTO;
import com.andrew.solution.addressbook.model.Contact;
import com.andrew.solution.addressbook.service.AddressBookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api")
public class AddressBookController {

    private final AddressBookService addressBookService;

    public AddressBookController(AddressBookService addressBookService) {
        this.addressBookService = addressBookService;
    }

    @GetMapping("/addressBooks")
    public Set<String> addressBooks() {
        return addressBookService.getAddressBookNameSet();
    }

    @GetMapping("/addressBooks/{name}")
    public List<Contact> getContactListByName(@PathVariable String name) {
        return addressBookService.getAllContactsPerAddressBook(name);
    }

    @PostMapping("/addressBook/contact")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAddressBook(@RequestBody ContactDTO contactDTO) {
        addressBookService.createContactForAddressBook(contactDTO);
    }

    @PostMapping("/addressBooks/unique/contacts")
    public Set<String> getUniqueContact(@RequestBody AddressBookPairDTO addressBookPairDTO) {
        return addressBookService.getUniqueContactNameFromAddressBooks(addressBookPairDTO);
    }

}
