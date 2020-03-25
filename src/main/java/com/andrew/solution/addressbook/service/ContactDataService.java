package com.andrew.solution.addressbook.service;

import com.andrew.solution.addressbook.exception.InvalidInputFileException;
import com.andrew.solution.addressbook.model.Contact;
import com.andrew.solution.addressbook.model.InputItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
public class ContactDataService {
    private static final int BOOK = 0;
    private static final int NAME = 1;
    private static final int PHONE = 2;
    private static String COMMA = ",";

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();

    private final Logger log = LoggerFactory.getLogger(ContactDataService.class);

    private final String inputFileName;

    @Autowired
    ResourceLoader resourceLoader;

    public ContactDataService(@Value("${input.file.name:contacts.csv}") String inputFileName) {
        this.inputFileName = inputFileName;
    }

    public HashMap<String, List<Contact>> readContacts() {
        return readContactList().stream()
                .parallel()
                .collect(
                        groupingBy(InputItem::getBookName,
                                HashMap::new,
                                mapping(item -> new Contact(item.getName(), item.getPhoneNumber()), toList()))

                );
    }

    private List<InputItem> readContactList() {
        List<InputItem> inputList;
        readLock.lock();
        try {
            Resource resource = new ClassPathResource(inputFileName);
            Path path = Paths.get(resource.getURI());
            inputList = Files.readAllLines(path).stream().map(mapToInputItem).collect(Collectors.toList());
        } catch (IOException e) {
            log.error("File Reading Error", e);
            throw new InvalidInputFileException("Cannot Read Contact List File " + inputFileName);
        } finally {
            readLock.unlock();
        }
        return inputList;
    }

    private Function<String, InputItem> mapToInputItem = (line) -> {
        String[] items = line.split(COMMA);
        return new InputItem(items[BOOK].trim(), items[NAME].trim(), items[PHONE].trim());
    };

    public void saveContact(InputItem item) {
        Resource resource = new ClassPathResource(inputFileName);

        writeLock.lock();
        try {
            Files.write(Paths.get(resource.getURI()), item.toString().getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            log.error("File Reading Error", e);
            throw new InvalidInputFileException("Cannot Read Contact List File " + inputFileName);
        } finally {
            writeLock.unlock();
        }
    }
}
