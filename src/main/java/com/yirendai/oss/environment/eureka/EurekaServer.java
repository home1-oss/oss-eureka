package com.yirendai.oss.environment.eureka;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.feign.ribbon.FeignRibbonClientAutoConfiguration;
import org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration;

/**
 * Created by zhanghaolun on 16/9/26.
 */
@SpringBootApplication(exclude = { RibbonAutoConfiguration.class, FeignRibbonClientAutoConfiguration.class })
@EnableEurekaServer
@EnableDiscoveryClient
public class EurekaServer {

  public static void main(final String... args) {
    new SpringApplicationBuilder(EurekaServer.class).web(true).run(args);
  }
}
