package com.example.finalprojectphase2;

import com.example.finalprojectphase2.model.Category;
import com.example.finalprojectphase2.model.HomeService;
import com.example.finalprojectphase2.model.User;
import com.example.finalprojectphase2.model.enums.ExpertStatus;
import com.example.finalprojectphase2.model.enums.UserRole;
import com.example.finalprojectphase2.service.CategoryService;
import com.example.finalprojectphase2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Finalprojectphase2Application implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    CategoryService categoryService;

    public static void main(String[] args) {
        SpringApplication.run(Finalprojectphase2Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        User admin = new User();
//        admin.setUserName("admin");
//        admin.setFirstName("admin");
//        admin.setLastName("admin");
//        admin.setEmail("test@test.com");
//        admin.setPassword("Admin1234");
//        admin.setRole(UserRole.ADMIN);
//        admin = userService.save(admin);
        admin = userService.findByUserName("admin");

//        User expert = userService.createExpert(
//                "alimoh", "Ali", "Mohammadi", "test1@test.com",
//                "Alimoh123", ExpertStatus.APPROVED, admin);

        Category category = categoryService.createCategory("لوازم خانگی", "خدمات لوازم خانگی", admin);

        HomeService homeService = new HomeService();
        homeService.setTitle("لوازم آشپزخانه");
        homeService.setBasePrice(100000F);
        homeService.setDescription("لوازم آشپزخانه");
        homeService.setCreatorUser(admin);

        categoryService.addCategoryService(category, homeService, admin);

    }
}
