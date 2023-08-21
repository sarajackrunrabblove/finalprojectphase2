package com.example.finalprojectphase2.service;

import com.example.finalprojectphase2.model.ServiceType;
import com.example.finalprojectphase2.model.ServiceItem;
import com.example.finalprojectphase2.model.User;
import com.example.finalprojectphase2.model.enums.UserRole;
import com.example.finalprojectphase2.payload.ServiceTypeDTO;
import com.example.finalprojectphase2.repository.ServiceTypeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceTypeService {
    private final ServiceTypeRepository repository;
    private final ServiceItemService homeServiceService;
    private static final Logger logger = LoggerFactory.getLogger(ServiceTypeService.class);

    
    public ServiceType save(ServiceType category) {
        return this.repository.save(category);
    }

    
    public void update(ServiceType category) {
        this.repository.save(category);
    }

    public void delete(Long id) {
        ServiceType category = this.repository.findById(id).orElseThrow();
        this.repository.delete(category);
    }

    public void delete(ServiceType category) {
        this.repository.delete(category);
    }

    
    public ServiceType findById(Long id) {
        return this.repository.findById(id).orElseThrow();
    }

    
    public List<ServiceType> findAll() {
        return this.repository.findAll();
    }

    public ServiceType findByTitle(String title) {
        return this.repository.findByTitle(title).orElse(null);
    }

    public ServiceType createCategory(String title, String description, User creatorUser) {
        if (!creatorUser.getRole().equals(UserRole.ADMIN)) {
            logger.error("You don't have permission to create category!");
            return null;
        }
        ServiceType category = new ServiceType();
        category.setTitle(title);
        category.setDescription(description);
        category.setCreatorUser(creatorUser);
        return this.save(category);
    }

    public ServiceType createCategory(ServiceTypeDTO serviceTypeDTO) {
        if (!serviceTypeDTO.getCreatorUser().getRole().equals(UserRole.ADMIN)) {
            logger.error("You don't have permission to create category!");
            return null;
        }
        ServiceType category = new ServiceType();
        category.setTitle(serviceTypeDTO.getTitle());
        category.setDescription(serviceTypeDTO.getDescription());
        category.setCreatorUser(serviceTypeDTO.getCreatorUser());
        return this.save(category);
    }

    @Transactional
    public void modifyCategory(Long categoryId, String title, String description, User modifierUser) {
        if (!modifierUser.getRole().equals(UserRole.ADMIN)) {
            logger.error("You don't have permission to modify category!");
            return;
        }
        ServiceType category = this.findById(categoryId);
        category.setTitle(title);
        category.setDescription(description);
        category.setModifierUser(modifierUser);
        this.update(category);
    }

    @Transactional
    public void addCategoryService(ServiceType category, ServiceItem service, User modifierUser) {
        if (!modifierUser.getRole().equals(UserRole.ADMIN)) {
            logger.error("You don't have permission to add service to category!");
            return;
        }
        category.setModifierUser(modifierUser);
        category.getHomeService().add(service);
        this.save(category);
    }

    @Transactional
    public void addCategoryService(Long categoryId, ServiceItem service, User modifierUser) {
        ServiceType category = this.findById(categoryId);
        this.addCategoryService(category, service, modifierUser);
    }

    @Transactional
    public void addCategoryService(Long categoryId, Long serviceId, User modifierUser) {
        ServiceItem service = this.homeServiceService.findById(serviceId);
        addCategoryService(categoryId, service, modifierUser);
    }
}
