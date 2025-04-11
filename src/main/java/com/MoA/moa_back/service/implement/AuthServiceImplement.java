package com.MoA.moa_back.service.implement;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.MoA.moa_back.common.dto.request.EmailCheckRequestDto;
import com.MoA.moa_back.common.dto.request.IdCheckRequestDto;
import com.MoA.moa_back.common.dto.request.NicknameCheckRequestDto;
import com.MoA.moa_back.common.dto.request.PhoneNumberCheckRequestDto;
import com.MoA.moa_back.common.dto.request.SignInRequestDto;
import com.MoA.moa_back.common.dto.request.SignUpRequestDto;
import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.dto.response.SignInResponseDto;
import com.MoA.moa_back.common.entity.UserEntity;
import com.MoA.moa_back.provider.*;
import com.MoA.moa_back.repository.UserRepository;
import com.MoA.moa_back.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService{

    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final JwtProvider jwtProvider;

    @Override
    public ResponseEntity<ResponseDto> idCheck(IdCheckRequestDto requestDto) {
        
        try {
            String userId = requestDto.getUserId();
            boolean existsUserId = userRepository.existsByUserId(userId);
            if(existsUserId) return ResponseDto.existUserId();
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
            if(existsUserNickname) return ResponseDto.existUserNickname();
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
            if(existsEmail) return ResponseDto.existUserEmail();
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
            if(existsPhoneNumber) return ResponseDto.existUserPhoneNumber();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success(HttpStatus.OK);
    }

    @Override
public ResponseEntity<ResponseDto> signUp(SignUpRequestDto requestDto) {
    
    boolean existsUserId = userRepository.existsById(requestDto.getUserId());
    if(existsUserId) return ResponseDto.existUserId();
    boolean existsUserNickname = userRepository.existsByUserNickname(requestDto.getUserNickname());
    if(existsUserNickname) return ResponseDto.existUserNickname();
    boolean existsUserEmail = userRepository.existsByUserEmail(requestDto.getUserEmail());
    if(existsUserEmail) return ResponseDto.existUserEmail();
    boolean existsUserPhoneNumber = userRepository.existsByUserPhoneNumber(requestDto.getUserPhoneNumber());
    if(existsUserPhoneNumber) return ResponseDto.existUserPhoneNumber();
    

    try {
        String password = requestDto.getUserPassword();
        String encodedPassword = passwordEncoder.encode(password);
        requestDto.setUserPassword(encodedPassword);

        if (requestDto.getUserIntroduce() == null) {
            requestDto.setUserIntroduce("");
        }

        UserEntity userEntity = new UserEntity(requestDto);
        userRepository.save(userEntity);

        return ResponseDto.success(HttpStatus.CREATED);
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseDto.databaseError();
    }
}

    @Override
    public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto requestDto) {

        String accessToken = null;

        try {
            String userId = requestDto.getUserId();
            
            UserEntity userEntity = userRepository.findByUserId(userId);
            if(userEntity == null) return ResponseDto.signInFail();
            
            String userPassword = requestDto.getUserPassword();

            String encodedUserPassword = userEntity.getUserPassword();
            boolean isMatch = passwordEncoder.matches(userPassword, encodedUserPassword);
            if(!isMatch) return ResponseDto.signInFail();

            accessToken = jwtProvider.create(userId);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return SignInResponseDto.success(accessToken);
        
    }


}
