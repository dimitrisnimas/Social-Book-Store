package myy803.SocialBookStore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import myy803.SocialBookStore.entity.Role;
import myy803.SocialBookStore.entity.User;
import myy803.SocialBookStore.service.UserService;

@Controller
public class AuthController {
    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public String login(){
        return "auth/signin";	// the whole login process is handled by WebSecurityConfig and the custom handlers there
    }
    
    @RequestMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "auth/signup";
    }

    @RequestMapping("/save")
    public String registerUser(@ModelAttribute("user") User user, Model model){
       
        if(userService.isUserPresent(user)){
            model.addAttribute("successMessage", "User already registered!");
            return "auth/signin";
        }
        user.setRole(Role.GUEST);
        System.out.println("AuthController (save) : " + user.toString());
        userService.saveUser(user);
        model.addAttribute("successMessage", "User registered successfully!");

        return "auth/signin";
    }
}
