package com.interview;

import net.unicon.cas.client.configuration.EnableCasClient;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 启动程序
 *
 * @author interview
 */

@SpringBootApplication
// @EnableCasClient
@EnableRabbit
public class InterviewApplication {

    public static void main(String[] args) {
        System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication application = new SpringApplication(InterviewApplication.class);
        application.setApplicationStartup(new BufferingApplicationStartup(2048));
        application.run(args);
        System.out.println("新版本启动！");
        System.out.println("(♥◠‿◠)ﾉﾞ  Interview-Vue-Plus启动成功   ლ(´ڡ`ლ)ﾞ");
    }

}
