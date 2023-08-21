package com.example.finalprojectphase2.service;

import com.example.finalprojectphase2.exception.CustomException;
import com.example.finalprojectphase2.model.User;
import com.example.finalprojectphase2.model.enums.UserRole;
import com.example.finalprojectphase2.model.enums.ExpertStatus;
import com.example.finalprojectphase2.payload.UserDTO;
import com.example.finalprojectphase2.repository.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    private final UserRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    
    public User save(User user) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<User>> constraintViolationsInvalidUser = validator.validate(user);
        if (!constraintViolationsInvalidUser.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (ConstraintViolation<User> constraintViolation : constraintViolationsInvalidUser) {
                logger.error(constraintViolation.getMessage());
                stringBuilder.append(constraintViolation.getMessage()).append(", ");
            }
            // todo: handle exception
            throw new CustomException(stringBuilder.toString());
        } else {
            return this.repository.save(user);
        }
    }
    
    public User update(Long userId, UserDTO payload) {
        User user = this.findById(userId);
        user.setUserName(payload.getUserName());
        user.setFirstName(payload.getFirstName());
        user.setLastName(payload.getLastName());
        user.setEmail(payload.getEmail());
        user.setPassword(payload.getPassword());

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<User>> constraintViolationsInvalidUser = validator.validate(user);
        if (!constraintViolationsInvalidUser.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (ConstraintViolation<User> constraintViolation : constraintViolationsInvalidUser) {
                logger.error(constraintViolation.getMessage());
                stringBuilder.append(constraintViolation.getMessage()).append(", ");
            }
            throw new CustomException(stringBuilder.toString());
        } else {
            return this.repository.save(user);
        }
    }

    public void delete(User user) {
        this.repository.delete(user);
    }

    public void deleteById(Long id) {
        User user = this.findById(id);
        this.repository.delete(user);
    }
    
    public User findById(Long id) {
        return this.repository.findById(id).orElseThrow();
    }

    
    public List<User> findAll() {
        return this.repository.findAll();
    }

    public User findByUserName(String userName) {
        return this.repository.findByUserName(userName).orElse(null);
    }

    public User createUser(UserDTO payload) {
        User user = new User();
        user.setUserName(payload.getUserName());
        user.setFirstName(payload.getFirstName());
        user.setLastName(payload.getLastName());
        user.setEmail(payload.getEmail());
        user.setPassword(payload.getPassword());
        user.setCreatorUser(payload.getCreatorUserId());
        user.setModifierUser(payload.getModifierUserId());
        return this.save(user);
    }

    public User createExpert(UserDTO payload) {
        User user = new User();
        user.setUserName(payload.getUserName());
        user.setFirstName(payload.getFirstName());
        user.setLastName(payload.getLastName());
        user.setEmail(payload.getEmail());
        user.setPassword(payload.getPassword());
        user.setCreatorUser(payload.getCreatorUserId());
        user.setModifierUser(payload.getModifierUserId());
        user.setRole(UserRole.EXPERT);
        return this.save(user);
    }

    public User createCustomer(UserDTO payload) {
        User user = new User();
        user.setUserName(payload.getUserName());
        user.setFirstName(payload.getFirstName());
        user.setLastName(payload.getLastName());
        user.setEmail(payload.getEmail());
        user.setPassword(payload.getPassword());
        user.setCreatorUser(payload.getCreatorUserId());
        user.setModifierUser(payload.getModifierUserId());
        user.setRole(UserRole.CUSTOMER);
        return this.save(user);
    }

    public User createCustomer(
            String userName, String firstName, String lastName, String email,
            String password, Float credit, User creatorUser
    ) {
        User user = new User();
        user.setUserName(userName);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setCredit(credit);
        user.setRole(UserRole.CUSTOMER);
        user.setCreatorUser(creatorUser);
        user.setModifierUser(creatorUser);
        return this.save(user);
    }

    public User createExpert(
            String userName, String firstName, String lastName, String email,
            String password, ExpertStatus status, User creatorUser
    ) {
        if (!creatorUser.getRole().equals(UserRole.ADMIN)) {
            logger.error("You don't have permission to create expert!");
            return null;
        }
        User user = new User();
        user.setUserName(userName);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(UserRole.EXPERT);
        user.setStatus(status);
        user.setCreatorUser(creatorUser);
        user.setModifierUser(creatorUser);
        return this.save(user);
    }


    public User createAdmin(
            String userName, String firstName, String lastName, String email,
            String password, User creatorUser
    ) {
        if (!creatorUser.getRole().equals(UserRole.ADMIN)) {
            logger.error("You don't have permission to create admin!");
            return null;
        }
        User user = new User();
        user.setUserName(userName);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(UserRole.ADMIN);
        user.setCreatorUser(creatorUser);
        user.setModifierUser(creatorUser);
        return this.save(user);
    }

    public void changeRole(User user, UserRole role, User modifierUser) {
        if (!modifierUser.getRole().equals(UserRole.ADMIN)) {
            logger.error("You don't have permission to change user role!");
            return;
        }
        user.setRole(role);
        user.setModifierUser(modifierUser);
        this.save(user);
    }

    public void changeRole(String userName, UserRole role, User modifierUser) {
        User load = this.findByUserName(userName);
        this.changeRole(load, role, modifierUser);
    }

    public User changeStatus(User user, ExpertStatus status) {
        //todo
//        if (!modifierUser.getRole().equals(UserRole.ADMIN)) {
//            logger.error("You don't have permission to change user status!");
//            return;
//        }
        user.setStatus(status);
//        user.setModifierUser(modifierUser);
        return this.save(user);
    }

    public User changeStatus(Long id, ExpertStatus status) {
        User load = this.findById(id);
        return this.changeStatus(load, status);
    }
    public User approveExpert(Long id) {
        //todo: check if user is admin
        return this.changeStatus(id, ExpertStatus.APPROVED);
    }

    public void changePassword(User user, String newPassword, User modifierUser) {
        //todo
//        if (!modifierUser.getRole().equals(UserRole.ADMIN)) {
//            logger.error("You don't have permission to change user status password!");
//            return;
//        }
        user.setPassword(newPassword);
        user.setModifierUser(modifierUser);
        this.save(user);
    }

    public void changePassword(Long userId, String newPassword) {
        //get user from session
        User load = this.findById(userId);
        this.changePassword(load, newPassword, null);
    }

    @Transactional
    public void changeUserPicture(Long userId, MultipartFile userPicture) {
        if (userPicture.getSize() > 300000L)
            throw new CustomException("image size is more than 300 KB");
        try {
            User load = this.findById(userId);
            load.setImage(toByteArray(userPicture.getInputStream()));
            load.setImageType(userPicture.getContentType());
            this.save(load);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
    }

    public static byte[] toByteArray(InputStream inputStream) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[300000];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
        }
        return outputStream.toByteArray();
    }
}
