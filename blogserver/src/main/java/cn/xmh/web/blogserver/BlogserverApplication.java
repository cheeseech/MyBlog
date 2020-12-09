package cn.xmh.web.blogserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class BlogserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogserverApplication.class, args);
    }

}
