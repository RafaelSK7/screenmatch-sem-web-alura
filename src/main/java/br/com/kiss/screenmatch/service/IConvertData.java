package br.com.kiss.screenmatch.service;

public interface IConvertData {

    <T> T obtainData(String json, Class<T> classToConvert);

}
