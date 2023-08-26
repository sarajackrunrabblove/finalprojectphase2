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
            @RequestParam(required = false) Integer rate,
            @RequestParam(required = false) Long expertSkills
    ) {
        UserDTO payload = new UserDTO();
        payload.setUserName(userName);
        payload.setFirstName(firstName);
        payload.setLastName(lastName);
        payload.setEmail(email);
        payload.setRole(role);
        payload.setStatus(status);
        payload.setRegistrationDate(registrationDate);
        payload.setCredit(credit);
        payload.setRate(rate);
        payload.setExpertSkills(expertSkills != null ? Set.of(expertSkills) : null);
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
            throw new CustomException("User not found", HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(user);
    }

    @GetMapping(value = "find-by-username/{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") String username) {
        User user = userService.findByUserName(username);
        if (user == null)
            throw new CustomException("User not found", HttpStatus.NOT_FOUND);
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

    @PostMapping(value = "/add-credit/{id}")
    public ResponseEntity<?> addCredit(@PathVariable("id") Long id, @RequestBody UserDTO userDTO) {
        Map<String, Object> response = new HashMap<>();
        //todo: add credit from payment service
        response.put("user", userService.addCredit(id, userDTO));
        response.put("message", "Expert approved successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("User deleted successfully");
    }

    @PutMapping(value = "/change-role/{id}")
    public ResponseEntity<?> changeRole(@PathVariable("id") Long id,
                                        @RequestBody UserDTO user) {

        Map<String, Object> response = new HashMap<>();
        response.put("user", userService.changeRole(id, user));
        response.put("message", "User updated successfully");

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @PutMapping(value = "/change-password/{id}")
    public ResponseEntity<?> changePassword(@PathVariable("id") Long id,
                                        @RequestBody UserDTO user) {

        Map<String, Object> response = new HashMap<>();
        response.put("user", userService.changePassword(id, user));
        response.put("message", "User password updated successfully");

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
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
            throw new CustomException("Image not found", HttpStatus.NOT_FOUND);

        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(user.getImageType()))
                .body(user.getImage());
    }
}
