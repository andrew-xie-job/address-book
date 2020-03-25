package com.andrew.solution.addressbook.service;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.StringWriter;

public class StreamUtils {

    public static String getStringFromInputStream(String fileName) throws IOException {
        Resource resource = new ClassPathResource(fileName);
        StringWriter writer = new StringWriter();
        IOUtils.copy(resource.getInputStream(), writer, "UTF-8");
        return writer.toString();
    }
}
