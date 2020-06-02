package com.paymybuddy.fund_transfer.controller;

import com.paymybuddy.fund_transfer.domain.RoleType;
import com.paymybuddy.fund_transfer.domain.User;
import com.paymybuddy.fund_transfer.service.ConnectionService;
import com.paymybuddy.fund_transfer.service.RoleTypeService;
import com.paymybuddy.fund_transfer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@Transactional
public class UserController {

    private UserService userService;
    private RoleTypeService roleTypeService;
    private ConnectionService connectionService;

    @Autowired
    public UserController(UserService userService, RoleTypeService roleTypeService, ConnectionService connectionService) {
        this.userService = userService;
        this.roleTypeService = roleTypeService;
        this.connectionService = connectionService;
    }

    @GetMapping("/register")
    public ModelAndView register(@ModelAttribute("user") User user) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(user);
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerNewUser(@Valid @ModelAttribute("user") User user, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            result.rejectValue("email", "error-registration","The email you entered is already taken.");
        }

        if (result.hasErrors()) {
            modelAndView.setViewName("/register");
        }
        else {
            userService.createUserByRegistration(user);
            modelAndView.setViewName("/login");

        }
        return modelAndView;
    }

    @GetMapping("/user/profile")
    public ModelAndView profile() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserFromAuth(auth);
        if (user != null) {
            modelAndView.setViewName("profile");
        }
        else {
            modelAndView.setViewName("redirect:/login");
        }
        return modelAndView;
    }

    @GetMapping("/user/addConnection")
    public ModelAndView getAddConnection(@ModelAttribute("user") User user) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userFromAuth = userService.getUserFromAuth(auth);
        if (userFromAuth != null) {
            modelAndView.setViewName("addConnection");
            modelAndView.addObject("user", user);
        }
        else {
            modelAndView.setViewName("redirect:/login");
        }
        return modelAndView;
    }

    @PostMapping("/user/addConnection")
    public ModelAndView createAddConnection(@ModelAttribute("user") User user, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userFromAuth = userService.getUserFromAuth(auth);
        if (userFromAuth != null) {
            if (userService.findUserByEmail(user.getEmail()) != null) {
                connectionService.createConnection(userFromAuth, user.getEmail());
                RedirectView redirectView = new RedirectView();
                redirectView.setUrl("/user/transfer");
                modelAndView.setView(redirectView);
            }
            else {
                result.rejectValue("email", "error-addConnection", "The email you entered isn't registered with our system.");
                modelAndView.setViewName("addConnection");
            }
        }
        else {
            modelAndView.setViewName("redirect:/login");
        }
        return modelAndView;
    }





    //TODO: Delete unused controller methods?

    //User CRUD methods
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/getUserById")
    public User getUserById(@RequestParam int id) {
        return userService.findUserById(id);
    }

    @GetMapping("/getUserByEmail")
    public User getUserByEmail(@RequestParam String email) {
        return userService.findUserByEmail(email);
    }

    @PostMapping("/user")
    public User createUser(@RequestBody User user, HttpServletResponse response) {
        response.setStatus(201);
        return userService.createUser(user);
    }

    @PutMapping("/user")
    public void updateUser(@RequestBody User user, HttpServletResponse response) {
        userService.updateUser(user);
        response.setStatus(200);
    }

    @DeleteMapping("/user")
    public void deleteUser(@RequestParam int id, HttpServletResponse response) {
        userService.deleteUser(id);
        response.setStatus(200);
    }

    // RoleType CRUD Methods
    @GetMapping("/roleTypes")
    public List<RoleType> getAllRoleTypes() {
        return roleTypeService.findAllRoleTypes();
    }

    @GetMapping("/getRoleTypeById")
    public RoleType getRoleTypeById(@RequestParam int id) {
        return roleTypeService.findRoleTypeById(id);
    }

    @GetMapping("/getRoleTypeByRoleType")
    public RoleType getRoleTypeByRoleType(@RequestParam String roleType) {
        return roleTypeService.findRoleTypeByRoleType(roleType);
    }

    @PostMapping("/roleType")
    public RoleType createRoleType(@RequestBody RoleType roleType, HttpServletResponse response) {
        response.setStatus(201);
        return roleTypeService.createRoleType(roleType);
    }

    @PutMapping("/roleType")
    public void updateRoleType(@RequestBody RoleType roleType, HttpServletResponse response) {
        roleTypeService.updateRoleType(roleType);
        response.setStatus(200);
    }

    @DeleteMapping("/roleType")
    public void deleteRoleType(@RequestParam int id, HttpServletResponse response) {
        roleTypeService.deleteRoleType(id);
        response.setStatus(200);
    }
}
