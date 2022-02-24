package com.adminpaint.controller;

import com.adminpaint.model.Client;
import com.adminpaint.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/clients")
    public String listClients(Model model) {
        List<Client> clientsList = clientRepository.findAll();
        model.addAttribute("clientsList", clientsList);

        return "clients";
    }

    @RequestMapping("/clients/new")
    public String showNewClientForm(Model model) {
        model.addAttribute("client", new Client());

        return "new_client";
    }

    @PostMapping("/clients/save")
    public String saveClient(Client client) {
        clientRepository.save(client);

        return "redirect:/clients";
    }
}
