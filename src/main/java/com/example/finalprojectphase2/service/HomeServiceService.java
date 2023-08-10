package com.example.finalprojectphase2.service;

import com.example.finalprojectphase2.model.Category;
import com.example.finalprojectphase2.model.HomeService;
import com.example.finalprojectphase2.model.User;
import com.example.finalprojectphase2.model.enums.ExpertStatus;
import com.example.finalprojectphase2.model.enums.UserRole;
import com.example.finalprojectphase2.repository.HomeServiceRepository;
import com.example.finalprojectphase2.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HomeServiceService implements BaseService<HomeService> {
    private final HomeServiceRepository homeServiceRepository;
    private static final Logger logger = LoggerFactory.getLogger(HomeServiceService.class);
    
    @Override
    public HomeService save(HomeService homeService) {
        return this.homeServiceRepository.save(homeService);
    }

    @Override
    public void update(HomeService homeService) {
        this.homeServiceRepository.save(homeService);
    }

    public void delete(HomeService homeService) {
        this.homeServiceRepository.delete(homeService);
    }

    @Override
    public HomeService findById(Long id) {
        return this.homeServiceRepository.findById(id).orElseThrow();
    }

    @Override
    public List<HomeService> findAll() {
        return this.homeServiceRepository.findAll();
    }

    public HomeService findByTitle(String title) {
        return this.homeServiceRepository.findByTitle(title).orElseThrow();
    }

    public void createService(String title, Float basePrice,
                              String description, Category category,
                              User creatorUser) {
        if (!creatorUser.getRole().equals(UserRole.ADMIN)) {
            logger.error("You don't have permission to create homeService!");
            return;
        }
        HomeService homeService = new HomeService();
        homeService.setTitle(title);
        homeService.setBasePrice(basePrice);
        homeService.setDescription(description);
        homeService.setCategory(category);
        homeService.setCreatorUser(creatorUser);
        this.save(homeService);
    }

//    public void createService(String title, Float basePrice,
//                              String description, String categoryName,
//                              User creatorUser) {
//        Category category = this.categoryService.findByTitle(categoryName);
//        this.createService(title, basePrice, description, category, creatorUser);
//    }

    public void addExpert(Long serviceId, User expert, User modifierUser) {
        if (!modifierUser.getRole().equals(UserRole.ADMIN)) {
            logger.error("You don't have permission to create homeService!");
            return;
        }

        if (!expert.getRole().equals(UserRole.EXPERT)) {
            logger.error("You can't add Non expert user!");
            return;
        }

        if (expert.getStatus().equals(ExpertStatus.NOT_APPROVED)) {
            logger.error("You can't add Not Approved expert!");
            return;
        }
        HomeService homeService = this.findById(serviceId);
        homeService.setModifierUser(modifierUser);
        homeService.getExperts().add(expert);
        this.update(homeService);
    }

//    public void addExpert(Long serviceId, String expertUserName, User modifierUser) {
//        User expert = this.userService.findByName(expertUserName);
//        addExpert(serviceId, expert, modifierUser);
//    }

}
