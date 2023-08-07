package com.moing.backend.global.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class AesConverter implements AttributeConverter<String, String> {
    private final AesUtil aesUtil;

    public AesConverter(AesUtil aesUtil) {
        this.aesUtil = aesUtil;
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        try {
            return aesUtil.encrypt(attribute);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to encrypt attribute", e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        try {
            return aesUtil.decrypt(dbData);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to decrypt dbData", e);
        }
    }
}