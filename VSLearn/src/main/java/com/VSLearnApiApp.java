package com;

import com.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.service.impl.UserService;

@SpringBootApplication
public class VSLearnApiApp implements CommandLineRunner {

  @Autowired
  UserService userService;

  public static void main(String[] args) {
    SpringApplication.run(VSLearnApiApp.class, args);
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurerAdapter() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*");
      }
    };
  }

  @Override
  public void run(String... params) throws Exception {
    try {
      // Create admin user
      User manager = new User();
      manager.setUserName("manager");
      manager.setUserPassword("manager");
      manager.setUserEmail("manager@email.com");
//      manager.setUserRole(new ArrayList<Role>(Arrays.asList(Role.ROLE_MANAGER)));
      userService.signup(manager);
    } catch (Exception e) {
      // Admin user already exists, continue
    }

    try {
      // Create client user
      User learner = new User();
      learner.setUserName("learner");
      learner.setUserPassword("learner");
      learner.setUserEmail("learner@email.com");
//      learner.setUserRole(new ArrayList<Role>(Arrays.asList(Role.ROLE_LEARNER)));
      userService.signup(learner);
    } catch (Exception e) {
      // Client user already exists, continue
    }

    try {
      // Create client user
      User contentCreator = new User();
      contentCreator.setUserName("contentCreator");
      contentCreator.setUserPassword("contentCreator");
      contentCreator.setUserEmail("contentCreator@email.com");
//      contentCreator.setUserRole(new ArrayList<Role>(Arrays.asList(Role.ROLE_CONTETN_CREATOR)));
      userService.signup(contentCreator);
    } catch (Exception e) {
      // Client user already exists, continue
    }


  }
}