package com.moing.backend.domain.mypage.domain.entity;

import com.moing.backend.global.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Feedback extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private Long feedbackId;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false, length = 500)
    private String reason;

    public Feedback(Long memberId, String reason){
        this.memberId=memberId;
        this.reason=reason;
    }

}
