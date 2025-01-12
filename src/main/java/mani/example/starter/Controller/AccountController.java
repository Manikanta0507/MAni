package mani.example.starter.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import mani.example.starter.models.Account;
import mani.example.starter.services.AccountService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;






@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/register")
    public String register(Model model) {
        Account account=new Account();
        model.addAttribute("account", account);
        return "register";
    }
    
    @PostMapping("/register")
    public String register_user(@ModelAttribute Account account) {
        accountService.save(account);
        
        return "redirect:/register";
    }

    @GetMapping("/login")
    public String login(Model model) {
        
        return "login";
    }

    @PostMapping("/login")
    public String postMethodName(@ModelAttribute("email") String email,
                            @ModelAttribute("password") String password,
                            Model model) {
        
        UserDetails userDetails = accountService.loadUserByUsername(email);
        if (accountService.passwordEncoder.matches(password, userDetails.getPassword())) {
                            // Login success
                            return "redirect:/register"; // Redirect to a secured page (e.g., home/dashboard)
                        } else {
                            // Password mismatch
                            model.addAttribute("error", "Invalid email or password");
                            return "login";
                        }

        
    }
    

//     @PostMapping("/login")
//     public String loginUser(@ModelAttribute("email") String email,
//                             @ModelAttribute("password") String password,
//                             Model model) {
//     //     try {
//     //         // Fetch user from the database using email
//     //         UserDetails userDetails = accountService.loadUserByUsername(email);

//     //         // Verify the password
//     //         if (accountService.passwordEncoder.matches(password, userDetails.getPassword())) {
//     //             // Login success
//     //             return "redirect:/home"; // Redirect to a secured page (e.g., home/dashboard)
//     //         } else {
//     //             // Password mismatch
//     //             model.addAttribute("error", "Invalid email or password");
//     //             return "login";
//     //         }
//     //     } catch (UsernameNotFoundException e) {
//     //         // User not found
//     //         model.addAttribute("error", "Invalid email or password");
//     //         return "login";
//     //     }
//     // }
//     UserDetails userDetails = accountService.loadUserByUsername(email);
//     if (accountService.passwordEncoder.matches(password, userDetails.getPassword())) {
//         //             // Login success
//     return "redirect:/"; // Redirect to a secured page (e.g., home/dashboard)
//     }
//         return "login";
// }


}
    

