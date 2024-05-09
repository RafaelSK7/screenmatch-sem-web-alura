package br.com.kiss.screenmatch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataConverter implements IConvertData{

    private ObjectMapper mapper = new ObjectMapper();


    @Override
    public <T> T obtainData(String json, Class<T> classToConvert) {
        try {
            return mapper.readValue(json, classToConvert);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
