package service;

import bean.Transaction;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileHandler {
    private static ObjectMapper objectMapper = new ObjectMapper();
    public static void writeAsJson(List<Transaction> data, File file) throws IOException, JsonMappingException {
        objectMapper.writeValue(file, data);
    }

    public static List<Transaction> readAsJson(File file) throws IOException, JsonMappingException {
        return List.of(objectMapper.readValue(file, Transaction[].class));
    }
}
