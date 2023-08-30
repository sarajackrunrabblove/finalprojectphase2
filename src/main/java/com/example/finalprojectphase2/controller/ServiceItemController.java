package com.example.finalprojectphase2.controller;

import com.example.finalprojectphase2.model.ServiceItem;
import com.example.finalprojectphase2.payload.ServiceItemDTO;
import com.example.finalprojectphase2.service.ServiceItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/serviceItem")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceItemController {

    private final ServiceItemService serviceItemService;


    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(serviceItemService.findAll());
    }

    @PostMapping(value = "/create-service-item")
    public ResponseEntity<?> createServiceItem(@RequestBody ServiceItemDTO serviceItem) {
        return ResponseEntity.ok(serviceItemService.createService(serviceItem));
    }

    @GetMapping(value = "/get-service-item-by-title/{serviceItemTitle}")
    public ResponseEntity<?> getServiceItemByTitle(@PathVariable String serviceItemTitle) {
        return ResponseEntity.ok(serviceItemService.findByTitle(serviceItemTitle));
    }

    @GetMapping(value = "/get-service-item-by-id/{id}")
    public ResponseEntity<?> getServiceItemById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceItemService.findById(id));
    }

    @DeleteMapping(value = "/delete-service-item/{id}")
    public void deleteServiceItem(@PathVariable Long id) {
        serviceItemService.delete(serviceItemService.findById(id));
    }

    @PutMapping(value = "/update-service-item/{id}")
    public ResponseEntity<?> updateServiceItem(@PathVariable Long id ,@RequestBody ServiceItemDTO serviceItem) {
        return ResponseEntity.ok(serviceItemService.update(id, serviceItem));
    }
}
