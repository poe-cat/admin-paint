package com.adminpaint.controller;

import com.adminpaint.model.Commissions;
import com.adminpaint.model.Users;
import com.adminpaint.service.CommissionsService;
import com.adminpaint.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private CommissionsService service;

    private final UsersService usersService;

    public MainController(UsersService usersService) {
        this.usersService = usersService;
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

            return "personal_page";
        }
        else {
            return "error_page";
        }
    }

    @RequestMapping("/new")
    public String showNewCommissionPage(Model model) {
        Commissions commission = new Commissions();
        model.addAttribute("commission", commission);

        return "new_commission";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveCommission(@ModelAttribute("commission") Commissions commission) {
        service.save(commission);

        return "redirect:/";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView showEditCommissionPage(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("edit_commission");
        Commissions commission = service.get(id);
        mav.addObject("commission", commission);

        return mav;
    }

    @RequestMapping("/delete/{id}")
    public String deleteCommission(@PathVariable(name = "id") int id) {
        service.delete(id);
        return "redirect:/";
    }
}
