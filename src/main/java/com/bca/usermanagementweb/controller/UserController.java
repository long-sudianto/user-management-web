package com.bca.usermanagementweb.controller;

import java.io.Console;
import java.util.Arrays;
import java.util.List;

import com.bca.usermanagementweb.model.User;
import com.bca.usermanagementweb.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class UserController {

	private static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @GetMapping("/list-user")
    public String getUsers(Model model) {
		log.info("Get User List");
        List<User> users = userService.getUsers();
        model.addAttribute("users", users);
        return "user/list-user";
    }
	
    @GetMapping("/add-user")
	public String add(Model model) {
		log.info("Add User");
		User newUser = new User();
        model.addAttribute("user", newUser);
		return "user/add-user";
	}
	
    @PostMapping("/save-user")
	public String saveEmployee(@ModelAttribute User user) {
		log.info("Save User");
		userService.saveUser(user);
		return "redirect:/list-user";
	}
	
    @GetMapping("/update-user")
	public String updateEmployee(@RequestParam Long id, Model model) {
		log.info("Update User");
		User user = userService.getUserById(id);
        model.addAttribute("user", user);
		return "user/add-user";
	}
	
    @GetMapping("/delete-user")
	public String deleteEmployee(@RequestParam Long id) {
		log.info("Delete User");
		userService.delete(id);
		return "redirect:/list-user";
	}



}
