package com.adminpaint.controller;

import com.adminpaint.exceptions.CommissionNotFoundException;
import com.adminpaint.model.Client;
import com.adminpaint.model.Commissions;
import com.adminpaint.repository.ClientRepository;
import com.adminpaint.service.CommissionsService;
import com.adminpaint.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private CommissionsService service;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UsersService usersService;


    @RequestMapping("/")
    public String viewHomePage() {
        return "index";
    }

    @RequestMapping("/commissions")
    public String viewCommissionsPage(Model model) {
        List<Commissions> listCommissions = service.listAll();
        model.addAttribute("listCommissions", listCommissions);

        return "commissions";
    }

    @RequestMapping("/commissions/new")
    public String showNewCommissionPage(Model model) {
        List<Client> clientsList = clientRepository.findAll();

        model.addAttribute("commission", new Commissions());
        model.addAttribute("clientsList", clientsList);

        model.addAttribute("pageTitle", "Add new commission");

        return "new_commission";
    }

    @RequestMapping(value = "/commissions/save", method = RequestMethod.POST)
    public String saveCommission(@ModelAttribute("commission") Commissions commission, RedirectAttributes re) {
        service.save(commission);
        re.addFlashAttribute("message", "Commission has been saved successfully.");

        return "redirect:/commissions";
    }

    @RequestMapping("/commissions/edit/{id}")
    public String showEditCommissionPage(@PathVariable(name = "id") Integer id,
                                               Model model, RedirectAttributes re) {
        try {
            List<Client> clientsList = clientRepository.findAll();
            Commissions commission = service.get(id);
            model.addAttribute("commission", commission);
            model.addAttribute("clientsList", clientsList);
            model.addAttribute("pageTitle", "Edit commission (ID: " + id + ")");
            return "edit_commission";
        } catch (CommissionNotFoundException e) {
            re.addFlashAttribute("message", e.getMessage());

            return "redirect:/commissions";
        }
    }

    @RequestMapping("/commissions/delete/{id}")
    public String deleteCommission(@PathVariable(name = "id") Integer id, RedirectAttributes re) {
        try {
            service.delete(id);
            re.addFlashAttribute("message", "The commission with ID: " + id + " has been deleted.");
        } catch (CommissionNotFoundException e) {
            re.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/commissions";
    }
}
