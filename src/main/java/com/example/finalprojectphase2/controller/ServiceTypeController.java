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

    @PostMapping(value = "/create-service-type")
    public ResponseEntity<?> createServiceType(ServiceTypeDTO serviceType) {
        return ResponseEntity.ok(serviceTypeService.createCategory(serviceType));
    }

    @GetMapping(value = "/get-service-type")
    public ResponseEntity<?> getServiceType(String serviceTypeTitle) {
        return ResponseEntity.ok(serviceTypeService.findByTitle(serviceTypeTitle));
    }

    @DeleteMapping(value = "/delete-service-type")
    public void deleteServiceType(String serviceTypeTitle) {
        serviceTypeService.delete(serviceTypeService.findByTitle(serviceTypeTitle));
    }

    @PutMapping(value = "/update-service-type")
    public ResponseEntity<?> updateServiceType(ServiceType serviceType) {
        return ResponseEntity.ok(serviceTypeService.save(serviceType));
    }

    //todo
//    @PutMapping(value = "/add-service-item/")
//    public ResponseEntity<?> addServiceItem(ServiceTypeDTO payload) {
//        return ResponseEntity.ok(serviceTypeService.save(serviceType));
//    }
}
