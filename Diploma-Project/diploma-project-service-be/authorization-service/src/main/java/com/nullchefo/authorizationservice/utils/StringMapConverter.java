package com.nullchefo.authorizationservice.utils;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class StringMapConverter implements AttributeConverter<Map<String, String>, String> {

	private static final ObjectMapper mapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(Map<String, String> attribute) {
		try {
			return mapper.writeValueAsString(attribute);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException("Could not convert map to JSON string", e);
		}
	}

	@Override
	public Map<String, String> convertToEntityAttribute(String dbData) {
		try {
			return mapper.readValue(dbData, new TypeReference<Map<String, String>>() {});
		} catch (IOException e) {
			throw new IllegalArgumentException("Could not convert JSON string to map", e);
		}
	}
}
