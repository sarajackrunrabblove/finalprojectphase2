package com.example.finalprojectphase2.service;

import com.example.finalprojectphase2.model.Category;
import com.example.finalprojectphase2.model.HomeService;
import com.example.finalprojectphase2.model.User;
import com.example.finalprojectphase2.model.enums.UserRole;
import com.example.finalprojectphase2.repository.CategoryRepository;
import com.example.finalprojectphase2.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryService implements BaseService<Category> {
    private final CategoryRepository repository;
    private final HomeServiceService homeServiceService;
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Override
    public Category save(Category category) {
        return this.repository.save(category);
    }

    @Override
    public void update(Category category) {
        this.repository.save(category);
    }

    public void delete(Long id) {
        Category category = this.repository.findById(id).orElseThrow();
        this.repository.delete(category);
    }

    public void delete(Category category) {
        this.repository.delete(category);
    }

    @Override
    public Category findById(Long id) {
        return this.repository.findById(id).orElseThrow();
    }

    @Override
    public List<Category> findAll() {
        return this.repository.findAll();
    }

    public Category findByTitle(String title) {
        return this.repository.findByTitle(title).orElseThrow();
    }

    @Transactional
    public Category createCategory(String title, String description, User creatorUser) {
        if (!creatorUser.getRole().equals(UserRole.ADMIN)) {
            logger.error("You don't have permission to create category!");
            return null;
        }
        Category category = new Category();
        category.setTitle(title);
        category.setDescription(description);
        category.setCreatorUser(creatorUser);
        return this.save(category);
    }

    @Transactional
    public void modifyCategory(Long categoryId, String title, String description, User modifierUser) {
        if (!modifierUser.getRole().equals(UserRole.ADMIN)) {
            logger.error("You don't have permission to modify category!");
            return;
        }
        Category category = this.findById(categoryId);
        category.setTitle(title);
        category.setDescription(description);
        category.setModifierUser(modifierUser);
        this.update(category);
    }

    @Transactional
    public void addCategoryService(Category category, HomeService service, User modifierUser) {
        if (!modifierUser.getRole().equals(UserRole.ADMIN)) {
            logger.error("You don't have permission to add service to category!");
            return;
        }
        category.setModifierUser(modifierUser);
        category.getHomeService().add(service);
        this.save(category);
    }

    @Transactional
    public void addCategoryService(Long categoryId, HomeService service, User modifierUser) {
        Category category = this.findById(categoryId);
        this.addCategoryService(category, service, modifierUser);
    }

    @Transactional
    public void addCategoryService(Long categoryId, Long serviceId, User modifierUser) {
        HomeService service = this.homeServiceService.findById(serviceId);
        addCategoryService(categoryId, service, modifierUser);
    }
}
