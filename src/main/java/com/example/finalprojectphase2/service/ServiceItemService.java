package com.example.finalprojectphase2.service;

import com.example.finalprojectphase2.model.ServiceType;
import com.example.finalprojectphase2.model.ServiceItem;
import com.example.finalprojectphase2.model.User;
import com.example.finalprojectphase2.model.enums.ExpertStatus;
import com.example.finalprojectphase2.model.enums.UserRole;
import com.example.finalprojectphase2.repository.ServiceItemRepository;
import com.example.finalprojectphase2.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceItemService implements BaseService<ServiceItem> {
    private final ServiceItemRepository homeServiceRepository;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(ServiceItemService.class);
    
    @Override
    public ServiceItem save(ServiceItem homeService) {
        return this.homeServiceRepository.save(homeService);
    }

    @Override
    public void update(ServiceItem homeService) {
        this.homeServiceRepository.save(homeService);
    }

    public void delete(ServiceItem homeService) {
        this.homeServiceRepository.delete(homeService);
    }

    @Override
    public ServiceItem findById(Long id) {
        return this.homeServiceRepository.findById(id).orElseThrow();
    }

    @Override
    public List<ServiceItem> findAll() {
        return this.homeServiceRepository.findAll();
    }

    public ServiceItem findByTitle(String title) {
        return this.homeServiceRepository.findByTitle(title).orElse(null);
    }

    public ServiceItem createService(String title, Float basePrice,
                              String description, ServiceType category,
                              User creatorUser) {
        if (!creatorUser.getRole().equals(UserRole.ADMIN)) {
            logger.error("You don't have permission to create homeService!");
            return null;
        }
        ServiceItem homeService = new ServiceItem();
        homeService.setTitle(title);
        homeService.setBasePrice(basePrice);
        homeService.setDescription(description);
        homeService.setServiceTypeId(category);
        homeService.setCreatorUser(creatorUser);
        return this.save(homeService);
    }

//    public void createService(String title, Float basePrice,
//                              String description, String categoryName,
//                              User creatorUser) {
//        Category category = this.categoryService.findByTitle(categoryName);
//        this.createService(title, basePrice, description, category, creatorUser);
//    }

    public void addExpert(ServiceItem serviceItem, User expert, User modifierUser) {
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
        serviceItem.setModifierUser(modifierUser);
        serviceItem.getExperts().add(expert);
        expert.setModifierUser(modifierUser);
        expert.getExpertSkills().add(serviceItem);
        this.update(serviceItem);
        userService.update(expert);
    }

//    public void addExpert(Long serviceId, String expertUserName, User modifierUser) {
//        User expert = this.userService.findByName(expertUserName);
//        addExpert(serviceId, expert, modifierUser);
//    }

}
