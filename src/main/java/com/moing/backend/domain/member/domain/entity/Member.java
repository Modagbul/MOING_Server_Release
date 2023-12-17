package com.moing.backend.domain.member.domain.entity;

import com.moing.backend.domain.auth.application.dto.request.SignUpRequest;
import com.moing.backend.domain.member.domain.constant.Gender;
import com.moing.backend.domain.member.domain.constant.RegistrationStatus;
import com.moing.backend.domain.member.domain.constant.Role;
import com.moing.backend.domain.member.domain.constant.SocialProvider;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;
import com.moing.backend.global.entity.BaseTimeEntity;
import com.moing.backend.global.utils.AesConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(indexes = @Index(name = "idx_social_id", columnList = "socialId"))
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(nullable = false)
    private String socialId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SocialProvider provider;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RegistrationStatus registrationStatus;

    @Convert(converter = AesConverter.class)
    private String email;

    private String profileImage;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birthDate;

    @Convert(converter = AesConverter.class)
    private String nickName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    private String introduction;

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

    private boolean isDeleted;

    private LocalDateTime lastSignInTime;

    private boolean isSignOut;

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
        if(signUpRequest.getGender()!=null) {
            this.gender = signUpRequest.getGender();
        }
        if(signUpRequest.getBirthDate()!=null) {
            this.birthDate = LocalDate.parse(signUpRequest.getBirthDate(), DateTimeFormatter.ISO_DATE);
        }
        this.registrationStatus = RegistrationStatus.COMPLETED;
        updateAllPush(true);
    }

    @Builder
    public Member(String email, String profileImage, Gender gender, LocalDate birthDate, Role role) {
        this.email = email;
        this.profileImage = profileImage;
        this.gender = gender;
        this.birthDate = birthDate;
        this.role = role;
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

    public void updateFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public void updateLastSignInTime(LocalDateTime time){
        this.lastSignInTime=time;
    }

    public Member(LocalDate birthDate, String email, String fcmToken, Gender gender, String introduction, String nickName, String profileImage, SocialProvider provider, RegistrationStatus registrationStatus, Role role, String socialId) {
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

    public void deleteMember(){
        this.isDeleted=true;
    }

    public void signOut() {
        this.isSignOut = true;
    }

    public void signIn() {
        this.isSignOut = false;
    }

}
