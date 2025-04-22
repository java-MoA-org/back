package com.MoA.moa_back.service.implement;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.MoA.moa_back.common.dto.request.auth.*;
import com.MoA.moa_back.common.dto.request.user.Interests;
import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.dto.response.auth.SignInResponseDto;
import com.MoA.moa_back.common.dto.response.auth.TokenRefreshResponseDto;
import com.MoA.moa_back.common.entity.UserEntity;
import com.MoA.moa_back.common.entity.UserInterestsEntity;
import com.MoA.moa_back.common.enums.UserRole;
import com.MoA.moa_back.provider.JwtProvider;
import com.MoA.moa_back.repository.UserInterestsRepository;
import com.MoA.moa_back.repository.UserRepository;
import com.MoA.moa_back.service.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {

    private final UserRepository userRepository;
    private final UserInterestsRepository userInterestsRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final JwtProvider jwtProvider;

    @Override
    public ResponseEntity<ResponseDto> idCheck(IdCheckRequestDto requestDto) {
        try {
            String userId = requestDto.getUserId();
            boolean existsUserId = userRepository.existsByUserId(userId);
            if (existsUserId) return ResponseDto.existUserId();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseDto> nicknameCheck(NicknameCheckRequestDto requestDto) {
        try {
            String userNickname = requestDto.getUserNickname();
            boolean existsUserNickname = userRepository.existsByUserNickname(userNickname);
            if (existsUserNickname) return ResponseDto.existUserNickname();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseDto> emailCheck(EmailCheckRequestDto requestDto) {
        try {
            String email = requestDto.getUserEmail();
            boolean existsEmail = userRepository.existsByUserEmail(email);
            if (existsEmail) return ResponseDto.existUserEmail();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseDto> phoneNumberCheck(PhoneNumberCheckRequestDto requestDto) {
        try {
            String userPhoneNumber = requestDto.getUserPhoneNumber();
            boolean existsPhoneNumber = userRepository.existsByUserPhoneNumber(userPhoneNumber);
            if (existsPhoneNumber) return ResponseDto.existUserPhoneNumber();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseDto> signUp(SignUpRequestDto requestDto) {
        boolean existsUserId = userRepository.existsById(requestDto.getUserId());
        if (existsUserId) return ResponseDto.existUserId();
        boolean existsUserNickname = userRepository.existsByUserNickname(requestDto.getUserNickname());
        if (existsUserNickname) return ResponseDto.existUserNickname();
        boolean existsUserEmail = userRepository.existsByUserEmail(requestDto.getUserEmail());
        if (existsUserEmail) return ResponseDto.existUserEmail();
        boolean existsUserPhoneNumber = userRepository.existsByUserPhoneNumber(requestDto.getUserPhoneNumber());
        if (existsUserPhoneNumber) return ResponseDto.existUserPhoneNumber();

        try {
            String password = requestDto.getUserPassword();
            String encodedPassword = passwordEncoder.encode(password);
            requestDto.setUserPassword(encodedPassword);

            if (requestDto.getUserIntroduce() == null) requestDto.setUserIntroduce("");

            Interests interests = requestDto.getInterests();
            if (interests != null) {
                boolean allFalse = !interests.isUserInterestTrip() &&
                                   !interests.isUserInterestGame() &&
                                   !interests.isUserInterestFashion() &&
                                   !interests.isUserInterestWorkout() &&
                                   !interests.isUserInterestFood() &&
                                   !interests.isUserInterestMusic() &&
                                   !interests.isUserInterestEconomics();
                interests.setUserInterestNull(allFalse);
                requestDto.setInterests(interests);
            }

            UserEntity userEntity = new UserEntity(requestDto);
            userRepository.save(userEntity);

            if (interests != null) {
                UserInterestsEntity interestsEntity = new UserInterestsEntity(
                    requestDto.getUserId(),
                    interests.isUserInterestTrip(),
                    interests.isUserInterestGame(),
                    interests.isUserInterestFashion(),
                    interests.isUserInterestWorkout(),
                    interests.isUserInterestFood(),
                    interests.isUserInterestMusic(),
                    interests.isUserInterestEconomics(),
                    interests.isUserInterestNull()
                );
                userInterestsRepository.save(interestsEntity);
            }

            return ResponseDto.success(HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
    }

    @Override
    public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto requestDto, HttpServletResponse response) {
        String accessToken = null;
        String refreshToken = null;
        UserRole userRole = null; // 🔥 사용자 권한 추가

        try {
            String userId = requestDto.getUserId();
            UserEntity userEntity = userRepository.findByUserId(userId);
            if (userEntity == null) return ResponseDto.signInFail();

            boolean isMatch = passwordEncoder.matches(requestDto.getUserPassword(), userEntity.getUserPassword());
            if (!isMatch) return ResponseDto.signInFail();

            accessToken = jwtProvider.createAccessToken(userId);
            refreshToken = jwtProvider.createRefreshToken(userId);
            userRole = userEntity.getUserRole(); // 🔥 권한 추출

            Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
            refreshCookie.setHttpOnly(true);
            refreshCookie.setPath("/");
            refreshCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(refreshCookie);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return SignInResponseDto.success(accessToken, userRole);
    }

    @Override
    public ResponseEntity<? super TokenRefreshResponseDto> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = jwtProvider.extractRefreshToken(request);
        String userId = jwtProvider.validate(refreshToken);

        if (userId == null) {
            Cookie cookie = new Cookie("refreshToken", null);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String newAccessToken = jwtProvider.createAccessToken(userId);
        TokenRefreshResponseDto body = TokenRefreshResponseDto.success(newAccessToken);
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<ResponseDto> signOut(HttpServletResponse response) {
        Cookie refreshTokenCookie = new Cookie("refreshToken", null);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(0);

        Cookie accessTokenCookie = new Cookie("accessToken", null);
        accessTokenCookie.setHttpOnly(false);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(0);

        Cookie userIdCookie = new Cookie("userId", null);
        userIdCookie.setHttpOnly(false);
        userIdCookie.setPath("/");
        userIdCookie.setMaxAge(0);

        response.addCookie(refreshTokenCookie);
        response.addCookie(accessTokenCookie);
        response.addCookie(userIdCookie);

        return ResponseDto.success(HttpStatus.OK);
    }
}