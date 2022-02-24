package com.adminpaint.controller;

import com.adminpaint.exceptions.CommissionNotFoundException;
import com.adminpaint.model.Commissions;
import com.adminpaint.model.Users;
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
    private UsersService usersService;

    @RequestMapping("/")
    public String viewHomePage(Model model) {
        List<Commissions> listCommissions = service.listAll();
        model.addAttribute("listCommissions", listCommissions);

        return "index";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerRequest", new Users());

        return "register_page";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("loginRequest", new Users());

        return "login_page";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Users users) {
        System.out.println("register request: " + users);
        Users registeredUser = usersService.registerUser(users.getLogin(), users.getPassword(), users.getEmail());

        return registeredUser == null ? "error_page" : "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute Users users, Model model) {
        System.out.println("login request: " + users);

        Users authenticated = usersService.authenticate(users.getLogin(), users.getPassword());

        if(authenticated != null) {
            model.addAttribute("userLogin", authenticated.getLogin());

            List<Commissions> listCommissions = service.listAll();
            model.addAttribute("listCommissions", listCommissions);

            return "redirect:/";
        }
        else {
            return "error_page";
        }
    }

    @RequestMapping("/new")
    public String showNewCommissionPage(Model model) {
        model.addAttribute("commission", new Commissions());
        model.addAttribute("pageTitle", "Add new commission");

        return "new_commission";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveCommission(@ModelAttribute("commission") Commissions commission, RedirectAttributes re) {
        service.save(commission);
        re.addFlashAttribute("message", "Commission has been saved successfully.");

        return "redirect:/";
    }

    @RequestMapping("/edit/{id}")
    public String showEditCommissionPage(@PathVariable(name = "id") Integer id,
                                               Model model, RedirectAttributes re) {
        try {
            Commissions commission = service.get(id);
            model.addAttribute("commission", commission);
            model.addAttribute("pageTitle", "Edit commission (ID: " + id + ")");
            return "edit_commission";
        } catch (CommissionNotFoundException e) {
            re.addFlashAttribute("message", e.getMessage());

            return "redirect:/";
        }
    }

    @RequestMapping("/delete/{id}")
    public String deleteCommission(@PathVariable(name = "id") Integer id, RedirectAttributes re) {
        try {
            service.delete(id);
            re.addFlashAttribute("message", "The commission with ID: " + id + " has been deleted.");
        } catch (CommissionNotFoundException e) {
            re.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/";
    }
}
