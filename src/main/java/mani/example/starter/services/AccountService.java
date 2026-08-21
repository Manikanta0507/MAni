package mani.example.starter.services;




import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import mani.example.starter.models.Account;
import mani.example.starter.repositories.AccountRepository;



@Service
public class AccountService implements UserDetailsService {      //implements UserDetailsService
    @Autowired
    public AccountRepository accountRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    public Account save(Account account){
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);

    }

    

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Account> optionalAccount = accountRepository.findOneByEmailIgnoreCase(email);

        // // Throw an exception if the user is not found
        Account account = optionalAccount.orElseThrow(() -> 
            new UsernameNotFoundException("User not found with email: " + email)
        );

        if (!optionalAccount.isEmpty()) {
            throw new UsernameNotFoundException("Account not Found");
        }
        
        // Account account=optionalAccount.get();
        List<GrantedAuthority> grantedAuthority= new ArrayList<>();
        grantedAuthority.add(new SimpleGrantedAuthority("Allow"));

        // Return a UserDetails object with username, password, and roles
        return new User(account.getEmail(), account.getPassword(), grantedAuthority);
                
    }

    public String checkId(String id) {
        return accountRepository.getReferenceById(id);
    }
}
