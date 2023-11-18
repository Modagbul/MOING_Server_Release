package com.moing.backend.global.config.sns;

import com.moing.backend.global.config.fcm.exception.InitializeException;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.security.PrivateKey;

@Configuration
public class AppleConfig {


    @Value("${oauth2.apple.keyPath}")
    private String keyPath;

    @Bean
    public PrivateKey applePrivateKey(){
        try{
        ClassPathResource resource = new ClassPathResource(keyPath);
        String privateKeyString = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
        Reader reader = new StringReader(privateKeyString);
        PEMParser pemParser = new PEMParser(reader);
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
        PrivateKeyInfo object = (PrivateKeyInfo) pemParser.readObject();
        return converter.getPrivateKey(object);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("파일을 찾을 수 없습니다." + e.getMessage());
        } catch (IOException e) {
            throw new InitializeException();
        }
    }
}