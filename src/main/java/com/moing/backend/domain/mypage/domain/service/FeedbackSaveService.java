package com.moing.backend.domain.mypage.domain.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.mypage.application.dto.request.WithdrawRequest;
import com.moing.backend.domain.mypage.domain.entity.Feedback;
import com.moing.backend.domain.mypage.domain.repository.FeedbackRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@DomainService
@Transactional
@RequiredArgsConstructor
public class FeedbackSaveService {
    private final FeedbackRepository feedbackRepository;

    public void saveFeedback(Member member, WithdrawRequest withdrawRequest){
        Feedback feedback=new Feedback(member.getMemberId(), withdrawRequest.getReason());
        feedbackRepository.save(feedback);
    }
}
