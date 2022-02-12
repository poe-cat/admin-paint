package com.adminpaint.service;

import com.adminpaint.model.Commissions;
import com.adminpaint.repository.CommissionsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommissionsService {

    private final CommissionsRepository commissionsRepository;

    public CommissionsService(CommissionsRepository commissionsRepository) {
        this.commissionsRepository = commissionsRepository;
    }

    public List<Commissions> listAll() {
        return commissionsRepository.findAll();
    }

    public void save(Commissions commission) {
        commissionsRepository.save(commission);
    }

    public Commissions get(int id) {
        return commissionsRepository.findById(id).get();
    }

    public void delete(int id) {
        commissionsRepository.deleteById(id);
    }
}
