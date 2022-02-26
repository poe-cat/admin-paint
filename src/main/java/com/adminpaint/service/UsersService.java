package com.adminpaint.service;

import com.adminpaint.model.Users;
import com.adminpaint.repository.UsersRepository;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Users registerUser(String username, String password, String email) {

        if(username == null || password == null) {
            return null;
        }
        else {
            if(usersRepository.findFirstByUsername(username).isPresent()) {
                System.out.println("Duplicate username");
                return null;
            }
            Users users = new Users();
            users.setUsername(username);
            users.setPassword(password);
            users.setEmail(email);

            return usersRepository.save(users);
        }
    }

    public Users authenticate(String username, String password) {
        return usersRepository.findByUsernameAndPassword(username, password).orElse(null);
    }
}
