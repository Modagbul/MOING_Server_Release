package com.moing.backend.global.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateTimeConverter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
@Setter
public class BaseTimeEntity {

    @CreatedDate
    @Column(updatable = false)
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime lastModifiedDate;


}

