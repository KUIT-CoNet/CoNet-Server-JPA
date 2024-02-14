package com.kuit.conet.jpa.service;

import com.kuit.conet.auth.apple.AppleUserProvider;
import com.kuit.conet.auth.kakao.KakaoUserProvider;
import com.kuit.conet.common.exception.UserException;
import com.kuit.conet.dto.web.request.auth.LoginRequestDTO;
import com.kuit.conet.dto.web.response.auth.ApplePlatformUserResponseDTO;
import com.kuit.conet.dto.web.response.auth.KakaoPlatformUserResponseDTO;
import com.kuit.conet.dto.web.response.auth.LoginResponseDTO;
import com.kuit.conet.jpa.domain.auth.Platform;
import com.kuit.conet.jpa.domain.member.Member;
import com.kuit.conet.jpa.repository.MemberRepository;
import com.kuit.conet.utils.auth.JwtParser;
import com.kuit.conet.utils.auth.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.kuit.conet.common.response.status.BaseExceptionResponseStatus.NOT_FOUND_USER;
import static com.kuit.conet.jpa.service.validator.AuthValidator.compareClientIpFromRedis;
import static com.kuit.conet.jpa.service.validator.AuthValidator.validateRefreshTokenExisting;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final AppleUserProvider appleUserProvider;
    private final KakaoUserProvider kakaoUserProvider;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;
    private final JwtParser jwtParser;

    public LoginResponseDTO appleLogin(LoginRequestDTO loginRequest, String clientIp) {
        ApplePlatformUserResponseDTO applePlatformUser = appleUserProvider.getApplePlatformUser(loginRequest.getIdToken());
        return generateLoginResponse(Platform.APPLE, applePlatformUser.getEmail(), applePlatformUser.getPlatformId(), clientIp);
    }

    public LoginResponseDTO kakaoLogin(LoginRequestDTO loginRequest, String clientIp) {
        KakaoPlatformUserResponseDTO kakaoPlatformUser = kakaoUserProvider.getPayloadFromIdToken(loginRequest.getIdToken());
        return generateLoginResponse(Platform.KAKAO, kakaoPlatformUser.getEmail(), kakaoPlatformUser.getPlatformId(), clientIp);
    }

    private LoginResponseDTO generateLoginResponse(Platform platform, String email, String platformId, String clientIp) {
        List<Long> findUserId = memberRepository.findByPlatformAndPlatformId(platform, platformId);
        if (!findUserId.isEmpty()) {
            Member findMember = memberRepository.findById(findUserId.get(0));
            if (findMember == null) {
                throw new UserException(NOT_FOUND_USER);
            }

            // 회원가입은 되어있는데, 필수 약관 동의 혹은 이름 입력이 되어있지 않은 유저
            if (!findMember.getServiceTerm() || findMember.getName() == null) {
                log.info("회원가입은 되어 있으나, 약관 동의 및 이름 입력이 필요합니다.");
                return getLoginResponse(findMember, clientIp, false);
            } else {
                // 이미 회원가입과 약관 동의 및 이름 입력이 모두 되어있는 유저
                log.info("로그인에 성공하였습니다.");
                return getLoginResponse(findMember, clientIp, true);
            }
        } else {
            // 회원가입이 필요한 멤버
            Member oauthMember = Member.createMember(email, platform, platformId);
            Member savedMember = memberRepository.findById(memberRepository.save(oauthMember));
            log.info("회원가입 성공! 약관 동의 및 이름 입력이 필요합니다.");
            return getLoginResponse(savedMember, clientIp, false);
        }
    }

    private LoginResponseDTO getLoginResponse(Member targetMember, String clientIp, Boolean isRegistered) {
        String accessToken = jwtTokenProvider.createAccessToken(targetMember.getId());
        String refreshToken = jwtTokenProvider.createRefreshToken(targetMember.getId());
        // Redis 에 refresh token 저장
        redisTemplate.opsForValue().set(refreshToken, clientIp);

        return new LoginResponseDTO(targetMember.getEmail(), accessToken, refreshToken, isRegistered);
    }

    public LoginResponseDTO regenerateToken(String refreshToken, String clientIp) {
        // Redis 에서 해당 refresh token 찾기
        String existingIp = redisTemplate.opsForValue().get(refreshToken);

        // 찾은 값의 validation 처리
        validateRefreshTokenExisting(existingIp);
        compareClientIpFromRedis(existingIp, clientIp);

        Long userId = jwtParser.getUserIdFromToken(refreshToken);
        Member existingMember = memberRepository.findById(userId);
        return getLoginResponse(existingMember, clientIp, true);
    }
}
