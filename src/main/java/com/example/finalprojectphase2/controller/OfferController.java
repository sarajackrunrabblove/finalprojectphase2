package com.example.finalprojectphase2.controller;

import com.example.finalprojectphase2.model.Offer;
import com.example.finalprojectphase2.payload.OfferDTO;
import com.example.finalprojectphase2.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/offer")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OfferController {

    private final OfferService offerService;


    @PostMapping(value = "/create-offer")
    public ResponseEntity<?> createOffer(@RequestBody  OfferDTO offer) {
        return ResponseEntity.ok(offerService.addNewOffer(offer));
    }

    @GetMapping(value = "/get-offer")
    public ResponseEntity<?> getOffer(@RequestParam  Long customerId) {
        return ResponseEntity.ok(offerService.findByCustomerId(customerId));
    }

    @DeleteMapping(value = "/delete-offer")
    public void deleteOffer(@RequestParam Long customerId) {
        offerService.delete(offerService.findByCustomerId(customerId));
    }

    @PostMapping(value = "/update-offer")
    public void updateOffer(Offer offer) {
        offerService.update(offer);
    }
}
