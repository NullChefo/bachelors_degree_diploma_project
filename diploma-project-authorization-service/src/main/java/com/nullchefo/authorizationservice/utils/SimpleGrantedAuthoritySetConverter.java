package com.nullchefo.authorizationservice.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Converter
public class SimpleGrantedAuthoritySetConverter implements AttributeConverter<Set<SimpleGrantedAuthority>, String> {

    @Override
    public String convertToDatabaseColumn(Set<SimpleGrantedAuthority> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return null;
        }

        Optional<Stream<SimpleGrantedAuthority>> optionalStream = Optional.ofNullable(attribute.stream());

        return optionalStream
                .orElse(Stream.empty())
                .map(SimpleGrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

    @Override
    public Set<SimpleGrantedAuthority> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.trim().isEmpty()) {
            return Collections.emptySet();
        }

        Optional<String> optionalDbData = Optional.of(dbData);

        // TODO if not activated don't give autorities or give not activated
        return optionalDbData
                .map(data -> Arrays.stream(data.split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
    }

}
