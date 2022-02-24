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

    public Users registerUser(String login, String password, String email) {

        if(login == null || password == null) {
            return null;
        }
        else {
            if(usersRepository.findFirstByLogin(login).isPresent()) {
                System.out.println("Duplicate login");
                return null;
            }
            Users users = new Users();
            users.setLogin(login);
            users.setPassword(password);
            users.setEmail(email);

            return usersRepository.save(users);
        }
    }

    public Users authenticate(String login, String password) {
        return usersRepository.findByLoginAndPassword(login, password).orElse(null);
    }
}
