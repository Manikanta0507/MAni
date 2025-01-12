package mani.example.starter.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import mani.example.starter.models.Account;
import mani.example.starter.models.Post;
import mani.example.starter.services.AccountService;
import mani.example.starter.services.PostService;

@Component
public class SeedData implements CommandLineRunner {
    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;


    @Override
    public void run(String... args) throws Exception {
        Account account01=new Account();
        Account account02=new Account();

        account01.setEmail("kmanikanta@gmail.com");
        account01.setPassword("password");
        account01.setFirstname("Manik");

        accountService.save(account01);

        account02.setEmail("kmanikanta2@gmail.com");
        account02.setPassword("password2");
        account02.setFirstname("Vamsi");

        accountService.save(account02);


        List<Post> posts=postService.getAll();
        if (posts.size()==0){
            Post post01=new Post();
            post01.setTitle("The Post");
            post01.setAccount(account01);
            post01.setBody("Post 01 Body");

            postService.save(post01);

            Post post02=new Post();
            post02.setTitle("The Post");
            post02.setBody("Post 02 Body");
            post02.setAccount(account02);
            postService.save(post02);
            
        }
    }
    
}
