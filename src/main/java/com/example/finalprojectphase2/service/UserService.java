package com.example.finalprojectphase2.service;

import com.example.finalprojectphase2.Exception.CustomException;
import com.example.finalprojectphase2.model.User;
import com.example.finalprojectphase2.model.enums.UserRole;
import com.example.finalprojectphase2.model.enums.ExpertStatus;
import com.example.finalprojectphase2.repository.UserRepository;
import com.example.finalprojectphase2.service.base.BaseService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService implements BaseService<User> {
    private final UserRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    public User save(User user) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<User>> constraintViolationsInvalidUser = validator.validate(user);
        if (!constraintViolationsInvalidUser.isEmpty()) {
            for (ConstraintViolation<User> constraintViolation : constraintViolationsInvalidUser) {
                logger.error(constraintViolation.getMessage());
            }
            // todo: handle exception
            return null;
        } else {
            return this.repository.save(user);
        }
    }

    @Override
    public void update(User user) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<User>> constraintViolationsInvalidUser = validator.validate(user);
        if (!constraintViolationsInvalidUser.isEmpty()) {
            for (ConstraintViolation<User> constraintViolation : constraintViolationsInvalidUser) {
                logger.error(constraintViolation.getMessage());
            }
        } else {
            this.repository.save(user);
        }
    }

    public void delete(User user) {
        this.repository.delete(user);
    }

    @Override
    public User findById(Long id) {
        return this.repository.findById(id).orElseThrow();
    }

    @Override
    public List<User> findAll() {
        return this.repository.findAll();
    }

    public User findByUserName(String userName) {
        return this.repository.findByUserName(userName).orElse(null);
    }

    public User createUser(
            String userName, String firstName, String lastName, String email,
            String password, User creatorUser, byte[] userPicture
    ) {
        if (userPicture.length > 300000)
            throw new CustomException("image size is more than 300 KB");
        User user = new User();
        user.setUserName(userName);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setCreatorUser(creatorUser);
        user.setModifierUser(creatorUser);
        user.setImage(userPicture);
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
        this.update(user);
    }

    public void changeRole(String userName, UserRole role, User modifierUser) {
        User load = this.findByUserName(userName);
        this.changeRole(load, role, modifierUser);
    }

    public void changeStatus(User user, ExpertStatus status, User modifierUser) {
        if (!modifierUser.getRole().equals(UserRole.ADMIN)) {
            logger.error("You don't have permission to change user status!");
            return;
        }
        user.setStatus(status);
        user.setModifierUser(modifierUser);
        this.update(user);
    }

    public void changeStatus(String userName, ExpertStatus status, User modifierUser) {
        User load = this.findByUserName(userName);
        this.changeStatus(load, status, modifierUser);
    }

    public void changeRoleAndExpertStatus(User user, UserRole role,
                                          ExpertStatus status, User modifierUser) {
        if (!modifierUser.getRole().equals(UserRole.ADMIN)) {
            logger.error("You don't have permission to change user role!");
            return;
        }
        user.setRole(role);
        user.setStatus(status);
        user.setModifierUser(modifierUser);
        this.update(user);
    }

    public void changeRoleAndExpertStatus(String userName, UserRole role,
                                          ExpertStatus status, User modifierUser) {
        User load = this.findByUserName(userName);
        this.changeRoleAndExpertStatus(load, role, status, modifierUser);
    }

    public void changePassword(User user, String newPassword, User modifierUser) {
        if (!modifierUser.getRole().equals(UserRole.ADMIN)) {
            logger.error("You don't have permission to change user status password!");
            return;
        }
        user.setPassword(newPassword);
        user.setModifierUser(modifierUser);
        this.update(user);
    }

    public void changePassword(String userName, String newPassword, User modifierUser) {
        User load = this.findByUserName(userName);
        this.changePassword(load, newPassword, modifierUser);
    }
}
