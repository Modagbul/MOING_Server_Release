package com.moing.backend.domain.member.dto.response;

import com.moing.backend.domain.member.domain.constant.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserProperty {
    private Gender gender;
    private LocalDate birthDate;
}
