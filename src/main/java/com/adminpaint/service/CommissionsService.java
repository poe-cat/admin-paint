package com.adminpaint.service;

import com.adminpaint.exceptions.CommissionNotFoundException;
import com.adminpaint.model.Commissions;
import com.adminpaint.repository.CommissionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommissionsService {

    @Autowired
    private CommissionsRepository repository;

    public List<Commissions> listAll() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public void save(Commissions commission) {
        repository.save(commission);
    }

    public Commissions get(Integer id) throws CommissionNotFoundException {
        Optional<Commissions> commission = repository.findById(id);

        if (commission.isPresent()) {
            return commission.get();
        }
        throw new CommissionNotFoundException("Couldn't find any commission with ID " + id);
    }


    public void delete(Integer id) throws CommissionNotFoundException {
        Long count = repository.countById(id);
        if(count == null || count == 0) {
            throw new CommissionNotFoundException("Couldn't find any commission with ID " + id);
        }
        repository.deleteById(id);
    }
}
