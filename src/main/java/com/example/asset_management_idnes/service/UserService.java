package com.example.asset_management_idnes.service;

import com.example.asset_management_idnes.domain.Otp;
import com.example.asset_management_idnes.domain.Role;
import com.example.asset_management_idnes.domain.User;
import com.example.asset_management_idnes.exception.ActivatedAccountException;
import com.example.asset_management_idnes.exception.NotFoundException;
import com.example.asset_management_idnes.exception.RefreshTokenNotFoundException;
import com.example.asset_management_idnes.model.request.ChangePasswordRequest;
import com.example.asset_management_idnes.model.request.RefreshTokenRequest;
import com.example.asset_management_idnes.model.request.RegistrationRequest;
import com.example.asset_management_idnes.model.response.JwtResponse;
import com.example.asset_management_idnes.model.response.ListUserResponse;
import com.example.asset_management_idnes.model.response.UserResponse;
import com.example.asset_management_idnes.repository.OtpRepository;
import com.example.asset_management_idnes.repository.RefreshTokenRepository;
import com.example.asset_management_idnes.repository.RoleRepository;
import com.example.asset_management_idnes.repository.UserRepository;
import com.example.asset_management_idnes.security.CustomUserDetails;
import com.example.asset_management_idnes.security.JwtUtils;
import com.example.asset_management_idnes.security.SecurityUtils;
import com.example.asset_management_idnes.statics.Roles;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dockerjava.api.exception.BadRequestException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService {

    final PasswordEncoder passwordEncoder;

    final UserRepository userRepository;

    final RoleRepository roleRepository;

    final ObjectMapper objectMapper;

    final RefreshTokenRepository refreshTokenRepository;

    @Value("${application.security.refreshToken.tokenValidityMilliseconds}")
    long refreshTokenValidityMilliseconds;

    final OtpRepository otpRepository;

    final EmailService emailService;

    final JwtUtils jwtUtils;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository, ObjectMapper objectMapper, RefreshTokenRepository refreshTokenRepository, OtpRepository otpRepository, EmailService emailService, JwtUtils jwtUtils) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.objectMapper = objectMapper;
        this.refreshTokenRepository = refreshTokenRepository;
        this.otpRepository = otpRepository;
        this.emailService = emailService;
        this.jwtUtils = jwtUtils;
    }


    public void registerUser(RegistrationRequest registrationRequest) {
        Optional<Role> optionalRole = roleRepository.findByName(Roles.USER);
        Set<Role> roles = new HashSet<>();
        roles.add(optionalRole.get());
        User user = User.builder()
                .name((registrationRequest.getName()))
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .roles(roles)
                .build();
        userRepository.save(user);
        emailService.sendOtpActivatedAccount(user.getEmail());

    }

    public void registerAdmin(RegistrationRequest registrationRequest) {
        Optional<Role> optionalRole = roleRepository.findByName(Roles.ADMIN);
        Set<Role> roles = new HashSet<>();
        roles.add(optionalRole.get());
        User user = User.builder()
                .name((registrationRequest.getName()))
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .roles(roles)
                .build();
        userRepository.save(user);
        emailService.sendOtpActivatedAccount(user.getEmail());
    }


    public List<UserResponse> getAll() {
        List<User> users = userRepository.findAll();
        if (!CollectionUtils.isEmpty(users)) {
            return users.stream().map(u -> objectMapper.convertValue(u, UserResponse.class)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public UserResponse getDetail(Long id) throws ClassNotFoundException {
        return userRepository.findById(id).map(u -> objectMapper.convertValue(u, UserResponse.class)).orElseThrow(ClassNotFoundException::new);
    }

    public JwtResponse refreshToken(RefreshTokenRequest request) throws RefreshTokenNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String newToken = userRepository.findById(userDetails.getId())
                .flatMap(user -> refreshTokenRepository.findByUserAndRefreshTokenAndInvalidated(user, request.getRefreshToken(), false)
                        .map(refreshToken -> {
                            LocalDateTime createdDateTime = refreshToken.getCreatedDateTime();
                            LocalDateTime expiryTime = createdDateTime.plusSeconds(refreshTokenValidityMilliseconds / 1000);
                            if (expiryTime.isBefore(LocalDateTime.now())) {
                                refreshToken.setInvalidated(true);
                                refreshTokenRepository.save(refreshToken);
                                return null;
                            }
                            return jwtUtils.generateJwtToken(authentication);
                        }))
                .orElseThrow(() -> new UsernameNotFoundException("Tài khoản không tồn tại"));


        if (newToken == null) {
            throw new RefreshTokenNotFoundException();
        }
//        JwtResponse jwtResponse = JwtResponse.builder()
//                .jwt(newToken)
//                .build();
//
//        Cookie jwtCookie = new Cookie("jwtToken", jwtResponse.getJwt());
//        jwtCookie.setPath("/");
//        response.addCookie(jwtCookie);
        return JwtResponse.builder()
                .jwt(newToken)
                .build();
    }

    @Transactional
    public void logout() {
        Optional<Long> userIdOptional = SecurityUtils.getCurrentUserLoginId();
        if (userIdOptional.isPresent()) {
            // Đăng xuất người dùng
            refreshTokenRepository.logOut(userIdOptional.get());
            SecurityContextHolder.clearContext();
        } else {
            // Xử lý trường hợp người dùng không tồn tại
            throw new UsernameNotFoundException("Tài khoản không tồn tại");
        }
    }


    public void deleteUser(Long id) throws BadRequestException {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw new NotFoundException("Không tìm thấy người dùng này!");
        }
        deleteOtpsByUserId(id);
        userRepository.deleteById(id);
    }

    private void deleteOtpsByUserId(Long userId) {
        otpRepository.deleteByUserId(userId);
    }


    public Page<ListUserResponse> getAllUserPageable(Pageable pageable) {
        Page<User> userList = userRepository.findAll(pageable);
        List<ListUserResponse> userResponses = new ArrayList<>();

        for (User user : userList) {
            ListUserResponse userResponse = ListUserResponse.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .roles(user.getRoles())
                    .build();
            userResponses.add(userResponse);
        }

        return new PageImpl<>(userResponses, pageable, userList.getTotalElements());
    }


    public void activeAccount(String otpCode) throws ActivatedAccountException {
        Otp otp = otpRepository.findByOtpCode(otpCode)
                .orElseThrow(() -> new ActivatedAccountException("Không tìm thấy mã OTP"));

        User user = otp.getUser();
        user.setActivated(true);
        userRepository.save(user);
    }

    public void changePassword(ChangePasswordRequest request) throws BadRequestException {
        Long currentUserLoginId = SecurityUtils.getCurrentUserLoginId().get();
        User user = userRepository.findById(currentUserLoginId).get();
        String newPassword = request.getNewPassword();
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new BadRequestException("lỗi");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
