package com.epam.idea.task.nine.parser;

import com.epam.idea.task.nine.entity.Ship;
import com.epam.idea.task.nine.exception.ParsingException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class JsonParser {
    public List<Ship> parse(String path) throws ParsingException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            InputStream inputStream = new FileInputStream(path);
            return objectMapper.readValue(inputStream, new TypeReference<List<Ship>>(){});
        }catch (IOException e) {
            throw new ParsingException(e.getMessage());
        }
    }
}
