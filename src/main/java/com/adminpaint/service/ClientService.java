package com.adminpaint.service;

import com.adminpaint.exceptions.CommissionNotFoundException;
import com.adminpaint.model.Client;
import com.adminpaint.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    public List<Client> listAll() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public void save(Client client) {
        repository.save(client);
    }

    public Client get(Integer id) throws CommissionNotFoundException {
        Optional<Client> client = repository.findById(id);

        if (client.isPresent()) {
            return client.get();
        }
        throw new CommissionNotFoundException("Couldn't find any client with ID " + id);
    }


    public void delete(Integer id) throws CommissionNotFoundException {
        Long count = repository.countById(id);
        if(count == null || count == 0) {
            throw new CommissionNotFoundException("Couldn't find any client with ID " + id);
        }
        repository.deleteById(id);
    }
}
