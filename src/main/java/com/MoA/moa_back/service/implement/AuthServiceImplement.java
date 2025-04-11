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
import com.MoA.moa_back.common.dto.request.SignUpRequestDto;
import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.entity.UserEntity;
import com.MoA.moa_back.repository.UserRepository;
import com.MoA.moa_back.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService{
    
    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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
    
    try {
        String password = requestDto.getUserPassword();
        String encodedPassword = passwordEncoder.encode(password);
        requestDto.setUserPassword(encodedPassword);

        // Null-safe 처리
        if (requestDto.getUserIntroduce() == null) {
            requestDto.setUserIntroduce("");
        }

        UserEntity userEntity = new UserEntity(requestDto);
        userRepository.save(userEntity);

        return ResponseDto.success(HttpStatus.CREATED);
    } catch (Exception e) {
        e.printStackTrace(); // 콘솔에서 꼭 확인!
        return ResponseDto.databaseError(); // 500
    }
}


}
