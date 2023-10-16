package com.moing.backend.domain.member.domain.entity;

import com.moing.backend.domain.auth.application.dto.request.SignUpRequest;
import com.moing.backend.domain.member.domain.constant.Gender;
import com.moing.backend.domain.member.domain.constant.RegistrationStatus;
import com.moing.backend.domain.member.domain.constant.Role;
import com.moing.backend.domain.member.domain.constant.SocialProvider;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;
import com.moing.backend.global.entity.BaseTimeEntity;
import com.moing.backend.global.util.AesConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(nullable = false, unique = true)
    private String socialId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SocialProvider provider;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RegistrationStatus registrationStatus;

    @Convert(converter = AesConverter.class)
    @Column(nullable = false, unique = true)
    private String email;

    @Convert(converter = AesConverter.class)
    private String profileImage; //없으면 undef

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birthDate;

    @Convert(converter = AesConverter.class)
    @Column(unique = true)
    private String nickName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Convert(converter = AesConverter.class)
    private String introduction;

    @Column(nullable = false)
    private String fcmToken;

    @ColumnDefault("true")
    @Column(nullable = false)
    private boolean isNewUploadPush;

    @ColumnDefault("true")
    @Column(nullable = false)
    private boolean isRemindPush;

    @ColumnDefault("true")
    @Column(nullable = false)
    private boolean isFirePush;

    @OneToMany(mappedBy = "member")
    private List<TeamMember> teamMembers = new ArrayList<>(); //최대 3개이므로 양방향

    //==생성 메서드==//
    public static Member valueOf(OAuth2User oAuth2User) {
        var attributes = oAuth2User.getAttributes();
        return Member.builder()
                .socialId((String) attributes.get("socialId"))
                .provider((SocialProvider) attributes.get("provider"))
                .nickName((String) attributes.get("nickname"))
                .email((String) attributes.get("email"))
                .role((Role) attributes.get("role"))
                .registrationStatus(RegistrationStatus.UNCOMPLETED)
                .build();
    }

    public void signUp(SignUpRequest signUpRequest) {
        this.nickName = signUpRequest.getNickName();
        this.gender = signUpRequest.getGender();
        this.birthDate = LocalDate.parse(signUpRequest.getBirthDate(), DateTimeFormatter.ISO_DATE);;
        this.fcmToken = signUpRequest.getFcmToken();
        this.registrationStatus = RegistrationStatus.COMPLETED;
    }

    @Builder
    public Member(String email, String profileImage, Gender gender, LocalDate birthDate, Role role) {
        this.email = email;
        this.profileImage = profileImage;
        this.gender = gender;
        this.birthDate = birthDate;
        this.role = role;
    }


    public void updateMember(String nickName, String fcmToken) {
        this.nickName = nickName;
        this.fcmToken = fcmToken;
    }

    public void updateProfile(String profileImage, String nickName, String introduction) {
        this.profileImage = profileImage;
        this.nickName = nickName;
        this.introduction = introduction;
    }

    public void updateNewUploadPush(boolean newUploadPush) {
        this.isNewUploadPush = newUploadPush;
    }

    public void updateRemindPush(boolean remindPush) {
        this.isRemindPush = remindPush;
    }

    public void updateFirePush(boolean firePush) {
        this.isFirePush = firePush;
    }

    public void updateAllPush(boolean allPush) {
        this.isNewUploadPush = allPush;
        this.isRemindPush = allPush;
        this.isFirePush = allPush;
    }

    public Member(LocalDate birthDate, String email, String fcmToken, Gender øgender, String introduction, String nickName, String profileImage, SocialProvider provider, RegistrationStatus registrationStatus, Role role, String socialId) {
        this.birthDate = birthDate;
        this.email = email;
        this.fcmToken = fcmToken;
        this.gender = gender;
        this.introduction = introduction;
        this.nickName = nickName;
        this.profileImage = profileImage;
        this.provider = provider;
        this.registrationStatus = registrationStatus;
        this.role = role;
        this.socialId = socialId;
    }

    public void deleteTeamMember(){
        List<TeamMember> teamMemberList=this.getTeamMembers();
        for(TeamMember teamMember:teamMemberList){
            teamMember.deleteMember();
        }
    }

}
