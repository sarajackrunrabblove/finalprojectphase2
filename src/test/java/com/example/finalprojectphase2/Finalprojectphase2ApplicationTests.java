package com.example.finalprojectphase2;

import com.example.finalprojectphase2.Exception.CustomException;
import com.example.finalprojectphase2.model.*;
import com.example.finalprojectphase2.model.enums.ExpertStatus;
import com.example.finalprojectphase2.model.enums.OrderStatus;
import com.example.finalprojectphase2.model.enums.UserRole;
import com.example.finalprojectphase2.service.*;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static junit.framework.TestCase.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class Finalprojectphase2ApplicationTests {

    private final UserService userService;
    private final ServiceTypeService serviceTypeService;
    private final ServiceItemService serviceItemService;
    private final OrderService orderService;
    private final OfferService offerService;

    private final Logger logger = LoggerFactory.getLogger(Finalprojectphase2ApplicationTests.class);

    @Test
    @org.junit.jupiter.api.Order(0)
    void contextLoads() {
        User admin = userService.findByUserName("admin");
        if (admin == null) {
            admin = new User();
            admin.setUserName("admin");
            admin.setFirstName("admin");
            admin.setLastName("admin");
            admin.setEmail("test@test.com");
            admin.setPassword("Admin1234");
            admin.setRole(UserRole.ADMIN);
            admin = userService.save(admin);
            logger.info("Admin is added as a new user");
        }
        assertNotNull(admin);
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void testAddNewServiceType() {

        ServiceType serviceType = serviceTypeService.findByTitle("لوازم خانگی");
        if (serviceType == null) {
            User admin = userService.findByUserName("admin");
            assertNotNull(admin);
            serviceType = serviceTypeService.createCategory("لوازم خانگی", "خدمات لوازم خانگی", admin);
        }
        assertNotNull(serviceType);
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void testAddNewServiceItem() {
        ServiceType serviceType = serviceTypeService.findByTitle("لوازم خانگی");
        assertNotNull(serviceType);
        ServiceItem service = serviceItemService.findByTitle("لوازم آشپزخانه");
        if (service == null) {
            User admin = userService.findByUserName("admin");
            assertNotNull(admin);
            service = serviceItemService.createService("لوازم آشپزخانه", 100000F,
                    "لوازم آشپزخانه", serviceType, admin);
        }
        assertNotNull(service);
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void testAddNewExpert() {
        User admin = userService.findByUserName("admin");
        assertNotNull(admin);

        User expert = userService.findByUserName("alimoh");
        if (expert == null) {
            expert = userService.createExpert(
                    "alimoh", "Ali", "Mohammadi", "test1@test.com",
                    "Alimoooh1", ExpertStatus.APPROVED, admin);
        }
        assertNotNull(expert);

        ServiceItem service = serviceItemService.findByTitle("لوازم آشپزخانه");
        assertNotNull(service);

        if (service.getExperts().isEmpty()) {
            serviceItemService.addExpert(service, expert, admin);
            expert = userService.findByUserName("alimoh");  // get latest record
        }
        assertFalse(expert.getExpertSkills().isEmpty());
        assertFalse(service.getExperts().isEmpty());
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void testAddNewCustomer() {
        User customer = userService.findByUserName("maryhoseini");
        if (customer == null) {
            User admin = userService.findByUserName("admin");
            customer = userService.createCustomer(
                    "maryhoseini", "Maryam", "Hoseini", "test2@test.com",
                    "Maryam1234", 100000000F, admin);
        }
        assertNotNull(customer);
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    void testAddNewOrder() {
        User customer = userService.findByUserName("maryhoseini");
        Order order = orderService.findByCustomerId(customer.getId());
        ServiceItem service = serviceItemService.findByTitle("لوازم آشپزخانه");
        if (order == null) {
            orderService.newOrder(customer, service, "تهران، ولیعصر",
                    500000F, "توضیحات", LocalDateTime.now());
            orderService.findByCustomerId(customer.getId());
        }
    }


    @Test
    @org.junit.jupiter.api.Order(6)
    void testAddNewOffer() {
        User expert = userService.findByUserName("alimoh");
        assertNotNull(expert);

        User customer = userService.findByUserName("maryhoseini");
        assertNotNull(customer);

        Order order = orderService.findByCustomerId(customer.getId());
        assertNotNull(order);

        Offer offer = offerService.findByCustomerId(expert.getId());
        if (offer == null) {
            offerService.addNewOffer(expert, order, 4329253F, LocalDateTime.now().plusSeconds(30), Duration.ofSeconds(30));
            offer = offerService.findByCustomerId(expert.getId()); // get latest record
        }
        assertNotNull(offer);

        order.setStatus(OrderStatus.WAITING_FOR_CHOOSING_EXPERT);
        order = orderService.save(order);

        assertEquals(OrderStatus.WAITING_FOR_CHOOSING_EXPERT, order.getStatus());
    }

    @Test
    @org.junit.jupiter.api.Order(7)
    void testShowAllOffers() {
        User customer = userService.findByUserName("maryhoseini");
        assertNotNull(customer);

        Order order = orderService.findByCustomerId(customer.getId());
        assertNotNull(order);

        List<Offer> offerList = order.getOffers();
        assertFalse(offerList.isEmpty());

        Offer offer = offerList.get(0);
        assertNotNull(offer);

        orderService.setFinalOfferForOrder(order, offer);
        order = orderService.findByCustomerId(customer.getId());
        assertEquals(offer.getId(), order.getFinalOffer().getId());

        while (LocalDateTime.now().isBefore(offer.getOfferedStartingTime())) {
            logger.info("Waiting for Starting Project");
        }
        orderService.changeStatusTo(order, OrderStatus.STARTED);
        assertEquals(OrderStatus.STARTED, order.getStatus());

        while (LocalDateTime.now().plus(offer.getDuration()).isBefore(LocalDateTime.now())) {
            logger.info("Waiting for project to be Done!");
        }
        orderService.changeStatusTo(order, OrderStatus.DONE);

        order = orderService.findByCustomerId(customer.getId());
        assertEquals(OrderStatus.DONE, order.getStatus());

        try {
            orderService.payForExpert(order);
        } catch (CustomException customException) {
            fail(customException.getMessage());
        }
    }

    @Test
    @org.junit.jupiter.api.Order(8)
    void testAddUserPhoto() {
        try {
            User admin = userService.findByUserName("admin");
            assertNotNull(admin);
            InputStream is = getClass().getClassLoader().getResourceAsStream("admin_pic.png");
            assertNotNull(is);
            admin.setImage(toByteArray(is));
            admin.setImageMimeType("png");
            admin = userService.save(admin);
            assertNotNull(admin.getImage());
        } catch (Exception e) {
            fail("Unable to get resources");
        }
    }

    private static byte[] toByteArray(InputStream inputStream) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[300000];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
        }
        return outputStream.toByteArray();
    }

}
