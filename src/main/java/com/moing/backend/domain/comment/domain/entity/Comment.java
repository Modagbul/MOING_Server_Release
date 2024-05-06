package com.moing.backend.domain.comment.domain.entity;

import com.moing.backend.global.entity.BaseTimeEntity;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Comment extends BaseTimeEntity {

    @Column(nullable = false, length = 300)
    protected String content;

    protected boolean isLeader; /*작성자 소모임장유무*/
    public void updateContent(String content) {
        this.content = content;
    }

}