package com.example.finalprojectphase2.service;

import com.example.finalprojectphase2.model.*;
import com.example.finalprojectphase2.repository.UserPictureRepository;
import com.example.finalprojectphase2.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserPictureService implements BaseService<UserPicture> {
    private final UserPictureRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(UserPicture.class);

    @Override
    public UserPicture save(UserPicture userPicture) {
        return this.repository.save(userPicture);
    }

    @Override
    public void update(UserPicture userPicture) {
        this.repository.save(userPicture);
    }

    public void delete(UserPicture userPicture) {
        this.repository.delete(userPicture);
    }

    @Override
    public UserPicture findById(Long id) {
        return this.repository.findById(id).orElseThrow();
    }

    @Override
    public List<UserPicture> findAll() {
        return this.repository.findAll();
    }

    public void savePicture(byte[] image, User user, User creatorUser) {
        if (image.length > 300000) {
            logger.error("image size is more than 300 KB");
            return;
        }
        UserPicture userPicture = new UserPicture();
        userPicture.setImage(image);
        userPicture.setUser(user);
        userPicture.setCreatorUser(creatorUser);
        this.save(userPicture);
    }
}
