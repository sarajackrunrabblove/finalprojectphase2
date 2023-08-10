package com.example.finalprojectphase2.service;

import com.example.finalprojectphase2.model.Offer;
import com.example.finalprojectphase2.repository.OfferRepository;
import com.example.finalprojectphase2.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OfferService implements BaseService<Offer> {
    private final OfferRepository repository;

    @Override
    public Offer save(Offer offer) {
        return this.repository.save(offer);
    }

    @Override
    public void update(Offer offer) {
        this.repository.save(offer);
    }

    public void delete(Offer offer) {
        this.repository.delete(offer);
    }

    @Override
    public Offer findById(Long id) {
        return this.repository.findById(id).orElseThrow();
    }

    @Override
    public List<Offer> findAll() {
        return this.repository.findAll();
    }
}
