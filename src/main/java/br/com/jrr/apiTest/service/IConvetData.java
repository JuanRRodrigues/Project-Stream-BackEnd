package br.com.jrr.apiTest.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface IConvetData {
    <T> T getDate(String json, Class<T> classe) throws JsonProcessingException;
}
