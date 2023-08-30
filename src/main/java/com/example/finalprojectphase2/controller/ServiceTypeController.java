package com.example.finalprojectphase2.controller;

import com.example.finalprojectphase2.model.ServiceType;
import com.example.finalprojectphase2.payload.ServiceTypeDTO;
import com.example.finalprojectphase2.service.ServiceTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/serviceType")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceTypeController {

    private final ServiceTypeService serviceTypeService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(serviceTypeService.findAll());
    }

    @PostMapping(value = "/create-service-type")
    public ResponseEntity<?> createServiceType(@RequestBody ServiceTypeDTO serviceType) {
        return ResponseEntity.ok(serviceTypeService.createCategory(serviceType));
    }

    @GetMapping(value = "/get-service-type-by-title/{serviceTypeTitle}")
    public ResponseEntity<?> getServiceType(@PathVariable String serviceTypeTitle) {
        return ResponseEntity.ok(serviceTypeService.findByTitle(serviceTypeTitle));
    }

    @GetMapping(value = "/get-service-type-by-id/{id}")
    public ResponseEntity<?> getServiceItemById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceTypeService.findById(id));
    }

    @DeleteMapping(value = "/delete-service-type/{id}")
    public void deleteServiceType(@PathVariable Long id) {
        serviceTypeService.delete(serviceTypeService.findById(id));
    }

    @PutMapping(value = "/update-service-type/{id}")
    public ResponseEntity<?> updateServiceType(@PathVariable Long id, @RequestBody ServiceTypeDTO serviceType) {
        return ResponseEntity.ok(serviceTypeService.update(id, serviceType));
    }
}
