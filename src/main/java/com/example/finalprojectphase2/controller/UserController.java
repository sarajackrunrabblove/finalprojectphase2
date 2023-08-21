package com.example.finalprojectphase2.controller;

import com.example.finalprojectphase2.exception.CustomException;
import com.example.finalprojectphase2.model.User;
import com.example.finalprojectphase2.model.enums.ExpertStatus;
import com.example.finalprojectphase2.model.enums.UserRole;
import com.example.finalprojectphase2.payload.UserDTO;
import com.example.finalprojectphase2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(path = "/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getUser(
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) UserRole role,
            @RequestParam(required = false) ExpertStatus status,
            @RequestParam(required = false) String registrationDate,
            @RequestParam(required = false) Float credit,
            @RequestParam(required = false) Long expertSkills
    ) {
        UserDTO payload = UserDTO.builder()
                .userName(userName)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .role(role)
                .status(status)
                .registrationDate(registrationDate)
                .credit(credit)
                .expertSkills(expertSkills != null ? Set.of(expertSkills) : null)
                .build();
        return ResponseEntity.ok(userService.findAll(payload));
    }

    @PostMapping
    public ResponseEntity<List<User>> searchUser(@RequestBody UserDTO payload) {
        return ResponseEntity.ok(userService.findAll(payload));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        User user = userService.findById(id);
        if (user == null)
            throw new CustomException("User not found");
        return ResponseEntity.ok(user);
    }

    @GetMapping(value = "get-by-username/{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") String username) {
        User user = userService.findByUserName(username);
        if (user == null)
            throw new CustomException("User not found");
        return ResponseEntity.ok(user);
    }

    @PostMapping(value = "/sign-up")
    public ResponseEntity<?> createUser(@RequestBody UserDTO user) {
        Map<String, Object> response = new HashMap<>();
        response.put("user", userService.createUser(user));
        response.put("message", "User created successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/sign-up-customer")
    public ResponseEntity<?> createCustomer(@RequestBody UserDTO user) {
        Map<String, Object> response = new HashMap<>();
        response.put("user", userService.createCustomer(user));
        response.put("message", "Customer created successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/sign-up-expert")
    public ResponseEntity<?> createExpert(@RequestBody UserDTO user) {
        Map<String, Object> response = new HashMap<>();
        response.put("user", userService.createExpert(user));
        response.put("message", "Expert created successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/approve-expert/{id}")
    public ResponseEntity<?> approveExpert(@PathVariable("id") Long id) {
        Map<String, Object> response = new HashMap<>();
        response.put("user", userService.approveExpert(id));
        response.put("message", "Expert approved successfully");
        return ResponseEntity.ok(response);
    }


    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("User deleted successfully");
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id,
                           @RequestBody UserDTO user) {

        Map<String, Object> response = new HashMap<>();
        response.put("user", userService.update(id, user));
        response.put("message", "User updated successfully");

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping("/upload/image/{userId}")
    public ResponseEntity<?> uploadImage(
            @PathVariable("userId") Long userId,
            @RequestParam("image") MultipartFile file
    ) {
        userService.changeUserPicture(userId, file);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Image uploaded successfully");
    }

    @GetMapping(path = {"/get/image/{userId}"})
    public ResponseEntity<byte[]> getImage(@PathVariable("userId") Long userId) {

        User user = userService.findById(userId);
        if (user.getImage() == null)
            throw new CustomException("Image not found");

        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(user.getImageType()))
                .body(user.getImage());
    }
}
