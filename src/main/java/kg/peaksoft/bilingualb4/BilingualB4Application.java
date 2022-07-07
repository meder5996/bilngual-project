package kg.peaksoft.bilingualb4;

import kg.peaksoft.bilingualb4.model.entity.AuthInfo;
import kg.peaksoft.bilingualb4.model.entity.Role;
import kg.peaksoft.bilingualb4.model.entity.User;
import kg.peaksoft.bilingualb4.repository.RoleRepository;
import kg.peaksoft.bilingualb4.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@SpringBootApplication
@RestController
public class BilingualB4Application {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String checkHealth(){
        return "<h1>Welcome to Bilingual application!!!<h1/>";
    }

    public static void main(String[] args) {
        SpringApplication.run(BilingualB4Application.class, args);
        System.out.println("Welcome colleagues, project name is Bilingual!");
    }
    @PostConstruct
    public void init() {
        User user = new User();
        user.setUserName("Admin");
        user.setEmail("admin@gmail.com");
        user.setPassword(passwordEncoder.encode("admin"));

        User user1 = new User();
        user1.setUserName("User");
        user1.setEmail("user@gmail.com");
        user1.setPassword(passwordEncoder.encode("user"));

        Role role = new Role();
        Role role1 = new Role();
        role.setName("ADMIN");
        role1.setName("CLIENT");

        AuthInfo authInfo = new AuthInfo();
        authInfo.setEmail(user.getEmail());
        authInfo.setPassword(user.getPassword());

        AuthInfo authInfo1 = new AuthInfo();
        authInfo1.setEmail(user1.getEmail());
        authInfo1.setPassword(user1.getPassword());

        authInfo.setAuthInfo1(role);
        authInfo1.setAuthInfo1(role1);

        user.setAuthInfo(authInfo);
        user1.setAuthInfo(authInfo1);
        userRepository.save(user);
        userRepository.save(user1);
    }

}
