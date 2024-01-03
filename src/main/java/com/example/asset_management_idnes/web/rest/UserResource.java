package com.example.asset_management_idnes.web.rest;

import com.example.asset_management_idnes.domain.Supplier;
import com.example.asset_management_idnes.domain.User;
import com.example.asset_management_idnes.exception.ActivatedAccountException;
import com.example.asset_management_idnes.exception.BadRequestException;
import com.example.asset_management_idnes.model.request.ChangePasswordRequest;
import com.example.asset_management_idnes.model.request.RegistrationRequest;
import com.example.asset_management_idnes.model.response.ListUserResponse;
import com.example.asset_management_idnes.model.response.UserResponse;
import com.example.asset_management_idnes.repository.UserRepository;
import com.example.asset_management_idnes.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserResource {

    UserService userService;

    UserRepository userRepository;

    // Lấy danh sách user
    @GetMapping
    public List<UserResponse> getAll() {
        return userService.getAll();
    }

    // Lấy danh sách user có phân trang
    @GetMapping("/get-all-user-pageable")
    public ResponseEntity<Page<ListUserResponse>> getAllUserPageable(Pageable pageable) {
        Page<ListUserResponse> getAllUserPageable = userService.getAllUserPageable(pageable);
        return new ResponseEntity<>(getAllUserPageable, HttpStatus.OK);
    }

    // Lấy thông tin user theo id
    @GetMapping("/{id}")
    public UserResponse getDetail(@PathVariable Long id) throws ClassNotFoundException {
        return userService.getDetail(id);
    }

    // Xóa user
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) throws BadRequestException {
        userService.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    // Tạo mới user
    @PostMapping("/create-user")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequest request) {
        return userRepository.findByEmail(request.getEmail())
                .map(user -> new ResponseEntity<>("Email is existed", HttpStatus.BAD_REQUEST))
                .orElseGet(() -> {
                    userService.registerUser(request);
                    return new ResponseEntity<>(null, HttpStatus.CREATED);
                });
    }

    // Tạo mới admin
    @PostMapping("/create-admin")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody RegistrationRequest request) {
        return userRepository.findByEmail(request.getEmail())
                .map(user -> new ResponseEntity<>("Email is existed", HttpStatus.BAD_REQUEST))
                .orElseGet(() -> {
                    userService.registerAdmin(request);
                    return new ResponseEntity<>(null, HttpStatus.CREATED);
                });
    }

    // Active tài khoản
    @GetMapping("/active-account")
    public ResponseEntity<String> activateAccount(@RequestParam("otpCode") String otpCode) throws ActivatedAccountException {
        userService.activeAccount(otpCode);
        return ResponseEntity.ok("Tài khoản đã được kích hoạt thành công.");
    }

    // Thay đổi mật khẩu
    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody @Valid ChangePasswordRequest request) {
        userService.changePassword(request);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
