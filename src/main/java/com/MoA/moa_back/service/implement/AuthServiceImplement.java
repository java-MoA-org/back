package com.MoA.moa_back.service.implement;

import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.MoA.moa_back.common.dto.request.auth.EmailCheckRequestDto;
import com.MoA.moa_back.common.dto.request.auth.EmailCodeVerifyRequestDto;
import com.MoA.moa_back.common.dto.request.auth.IdCheckRequestDto;
import com.MoA.moa_back.common.dto.request.auth.NicknameCheckRequestDto;
import com.MoA.moa_back.common.dto.request.auth.PhoneNumberCheckRequestDto;
import com.MoA.moa_back.common.dto.request.auth.PhoneNumberCodeVerifyRequestDto;
import com.MoA.moa_back.common.dto.request.auth.SignInRequestDto;
import com.MoA.moa_back.common.dto.request.auth.SignUpRequestDto;
import com.MoA.moa_back.common.dto.request.user.Interests;
import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.dto.response.auth.EmailVerifyResponseDto;
import com.MoA.moa_back.common.dto.response.auth.PhoneNumberVerifyResponseDto;
import com.MoA.moa_back.common.dto.response.auth.SignInResponseDto;
import com.MoA.moa_back.common.dto.response.auth.TokenRefreshResponseDto;
import com.MoA.moa_back.common.entity.UserEntity;
import com.MoA.moa_back.common.entity.UserInterestsEntity;
import com.MoA.moa_back.common.enums.UserRole;
import com.MoA.moa_back.provider.JwtProvider;
import com.MoA.moa_back.repository.UserInterestsRepository;
import com.MoA.moa_back.repository.UserRepository;
import com.MoA.moa_back.service.AuthService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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
    private final JavaMailSender javaMailSender;

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
    public ResponseEntity<? super EmailVerifyResponseDto> emailVerifyRequire(EmailCheckRequestDto requestDto) {

        String emailToken = null;

        try{
            String userEmail = requestDto.getUserEmail();
            boolean existsUserEmail = userRepository.existsByUserEmail(userEmail);
            if(existsUserEmail) return ResponseDto.existUserEmail();
            String code = String.format("%06d", new Random().nextInt(1_000_000)); // 6자리, 앞에 0 채움
            emailToken = jwtProvider.createVerifyToken(userEmail, code);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(userEmail);
            message.setSubject("[MoA] 인증번호");
            message.setText("인증번호는: " + code + "입니다");
            javaMailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return EmailVerifyResponseDto.success(emailToken);
    }

    @Override
    public ResponseEntity<ResponseDto> verifyEmailCode(EmailCodeVerifyRequestDto requestDto){

        String token = requestDto.getEmailToken();

        try {
            Claims claims = jwtProvider.parseToken(token);
            String codeInToken = claims.get("code", String.class);
            String emailInToken = claims.get("email", String.class);
    
            if (!requestDto.getUserEmail().equals(emailInToken)) return ResponseDto.verifyCodeError();
            if (!requestDto.getUserEmailVC().equals(codeInToken)) return ResponseDto.verifyCodeError();
            
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            return ResponseDto.tokenTimeOut();
        }catch(Exception e){
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<? super PhoneNumberVerifyResponseDto> phoneNumberVerifyRequire(PhoneNumberCheckRequestDto requestDto) {

        String phoneNumberToken = null;

        try{
            String userPhoneNumber = requestDto.getUserPhoneNumber();
            boolean existsUserPhoneNumber = userRepository.existsByUserPhoneNumber(userPhoneNumber);
            if(existsUserPhoneNumber) return ResponseDto.existUserPhoneNumber();
            String code = String.format("%06d", new Random().nextInt(1_000_000)); // 6자리, 앞에 0 채움
            System.out.println(code);
            phoneNumberToken = jwtProvider.createVerifyToken(userPhoneNumber, code);

            // 인증번호 전송은 사업자 인증이 요구되므로 추후 개발 예정

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return PhoneNumberVerifyResponseDto.success(phoneNumberToken);
    }

    @Override
    public ResponseEntity<ResponseDto> verifyPhoneNumberCode(PhoneNumberCodeVerifyRequestDto requestDto){

        String token = requestDto.getUserPhoneNumberToken();

        try {
            Claims claims = jwtProvider.parseToken(token);
            String codeInToken = claims.get("code", String.class);
            String phoneNumberInToken = claims.get("phoneNumber", String.class);
    
            if (!requestDto.getUserPhoneNumber().equals(phoneNumberInToken)) return ResponseDto.verifyCodeError();
            if (!requestDto.getUserPhoneNumberVC().equals(codeInToken)) return ResponseDto.verifyCodeError();
            
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            return ResponseDto.tokenTimeOut();
        }catch(Exception e){
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
