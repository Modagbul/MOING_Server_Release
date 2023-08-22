package com.moing.backend.domain.member.domain.entity;

import com.moing.backend.domain.member.domain.constant.RegistrationStatus;
import com.moing.backend.domain.member.domain.constant.Role;
import com.moing.backend.domain.member.domain.constant.SocialProvider;
import com.moing.backend.global.entity.BaseTimeEntity;
import com.moing.backend.global.util.AesConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.persistence.*;

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
    private RegistrationStatus registrationStatus;

    @Convert(converter = AesConverter.class)
    @Column(nullable = false, unique = true)
    private String email;

    @Convert(converter = AesConverter.class)
    private String profileImage; //없으면 undef

    private String gender; //없으면 undef

    private String ageRange; //없으면 undef

    private boolean isDeleted;

    // 추가정보
    @Convert(converter = AesConverter.class)
    private String nickName; //없으면 undef

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Convert(converter = AesConverter.class)
    private String introduction; //없으면 undef

    private String fcmToken; //없으면 undef

    @ColumnDefault("true")
    private boolean isNewUploadPush;

    @ColumnDefault("true")
    private boolean isRemindPush;

    @ColumnDefault("true")
    private boolean isFirePush;


    //==생성 메서드==//
    public static Member valueOf(OAuth2User oAuth2User) {
        var attributes = oAuth2User.getAttributes();
        return Member.builder()
                .socialId((String) attributes.get("socialId"))
                .provider((SocialProvider) attributes.get("provider"))
                .nickName((String) attributes.get("nickname"))
                .email((String) attributes.get("email"))
                .gender((String) attributes.get("gender"))
                .ageRange((String) attributes.get("age"))
                .role((Role) attributes.get("role"))
                .registrationStatus(RegistrationStatus.UNCOMPLETED)
                .build();
    }

    @PrePersist
    public void prePersist() {
        if (profileImage == null) profileImage = "undef";
        if (gender == null) gender = "undef";
        if (ageRange == null) ageRange = "undef";
        if (nickName == null) nickName = "undef";
        if (introduction == null) introduction = "undef";
        if (fcmToken == null) fcmToken = "undef";
        if (registrationStatus == null) registrationStatus = RegistrationStatus.UNCOMPLETED;
    }

    public void signUp(String nickName) {
        this.nickName = nickName;
        this.registrationStatus = RegistrationStatus.COMPLETED;
    }

    public void updateFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    @Builder
    public Member(String email, String profileImage, String gender, String ageRange, Role role) {
        this.email = email;
        this.profileImage = profileImage;
        this.gender = gender;
        this.ageRange = ageRange;
        this.role = role;
    }


    public void updateMember(String nickName, String fcmToken) {
        this.nickName = nickName;
        this.fcmToken = fcmToken;
    }

    public void updateMypage(String nickName, String introduction) {
        this.nickName = nickName;
        this.introduction = introduction;
    }

    public void deleteAccount() {
        this.isDeleted = true;
    }
    public void reSignUp(){this.isDeleted=false;}

    public void updateNewUploadPush(boolean newUploadPush) {
        this.isNewUploadPush = newUploadPush;
    }

    public void updateRemindPush(boolean remindPush) {
        this.isRemindPush = remindPush;
    }

    public void updateFirePush(boolean firePush) {
        this.isFirePush = firePush;
    }

    public void updateProfile(String string) {
        this.profileImage = string;
    }

}
