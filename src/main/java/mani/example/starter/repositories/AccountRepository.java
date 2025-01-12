package mani.example.starter.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import mani.example.starter.models.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    // Optional<Account> findOneByEmailIgnoreCase(UserDetails userDetails);

    Optional<Account> findOneByEmailIgnoreCase(String email);
    

} 
