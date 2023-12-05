package com.moing.backend.global.utils;

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
        if (attribute == null) {
            return null; // attribute가 null인 경우 null 반환
        }
        try {
            return aesUtil.encrypt(attribute);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to encrypt attribute", e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null; // dbData가 null인 경우 null 반환
        }
        try {
            return aesUtil.decrypt(dbData);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to decrypt dbData", e);
        }
    }
}