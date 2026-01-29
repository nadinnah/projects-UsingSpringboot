package com.example.tacocloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class TacoCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(TacoCloudApplication.class, args);
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

//Application Context is like a factory and registry for your objects (aka beans).
//it is Spring’s brain for managing your app components

//A bean is just an object that Spring creates, manages, and knows about.
//like any normal Java object (new MyClass()), but Spring controls its lifecycle, wiring, and availability for your whole application

//Spring uses annotations to register classes or methods as beans in the context
//@Component → class is a bean
//@Bean → method returns a bean
//@Autowired → inject bean from context

//Application Context is like a factory and registry for your objects (aka beans).
//it is Spring’s brain for managing your app components

//A bean is just an object that Spring creates, manages, and knows about.
//like any normal Java object (new MyClass()), but Spring controls its lifecycle, wiring, and availability for your whole application

//Spring uses annotations to register classes or methods as beans in the context
//@Component → class is a bean
//@Bean → method returns a bean
//@Autowired → inject bean from context