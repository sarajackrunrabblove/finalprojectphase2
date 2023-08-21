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

    @PostMapping(value = "create-serviceItem")
    public ResponseEntity<?> createServiceItem(ServiceItemDTO serviceItem) {
        return ResponseEntity.ok(serviceItemService.createService(serviceItem));
    }

    @GetMapping(value = "get-serviceItem")
    public ResponseEntity<?> getServiceItem(String serviceItemTitle) {
        return ResponseEntity.ok(serviceItemService.findByTitle(serviceItemTitle));
    }

    @DeleteMapping(value = "delete-serviceItem")
    public void deleteServiceItem(String serviceItemTitle) {
        serviceItemService.delete(serviceItemService.findByTitle(serviceItemTitle));
    }

    @PostMapping(value = "update-serviceItem")
    public void updateServiceItem(ServiceItem serviceItem) {
        serviceItemService.update(serviceItem);
    }
}