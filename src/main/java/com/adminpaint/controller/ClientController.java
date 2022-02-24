package com.adminpaint.controller;

import com.adminpaint.exceptions.CommissionNotFoundException;
import com.adminpaint.model.Client;
import com.adminpaint.model.Commissions;
import com.adminpaint.repository.ClientRepository;
import com.adminpaint.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;

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

    @RequestMapping(value = "/clients/save", method = RequestMethod.POST)
    public String saveClient(@ModelAttribute("client") Client client, RedirectAttributes re) {
        clientRepository.save(client);
        re.addFlashAttribute("message", "Client has been saved successfully.");

        return "redirect:/clients";
    }

    @RequestMapping("/clients/edit/{id}")
    public String showEditClientsPage(@PathVariable(name = "id") Integer id,
                                         Model model, RedirectAttributes re) {
        try {
            Client client = clientService.get(id);
            model.addAttribute("client", client);
            model.addAttribute("pageTitle", "Edit client (ID: " + id + ")");
            return "edit_client";
        } catch (CommissionNotFoundException e) {
            re.addFlashAttribute("message", e.getMessage());

            return "redirect:/clients";
        }
    }

    @RequestMapping("/clients/delete/{id}")
    public String deleteClient(@PathVariable(name = "id") Integer id, RedirectAttributes re) {
        try {
            clientService.delete(id);
            re.addFlashAttribute("message", "The client with ID: " + id + " has been deleted.");
        } catch (CommissionNotFoundException e) {
            re.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/clients";
    }
}
