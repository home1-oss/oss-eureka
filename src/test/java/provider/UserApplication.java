package provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UserApplication {

  static {
    System.setProperty("spring.config.name", "provider-application");
  }

  public static void main(final String... args) {
    SpringApplication.run(UserApplication.class, args);
  }
}
