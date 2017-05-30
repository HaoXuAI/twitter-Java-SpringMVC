/**
 * A simulator of simplified twitter service.
 * 
 * @Author: Hao Xu
 * 
 * @Functionality (more detail in TwitterController):
 * 1. Follow/Unfollow user
 * 2. list tweet messages
 * 2. list following/follower users
 * 4. pair popular users
 * 5. find shortest distance to user
 */
package challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
@Configuration
@ComponentScan(basePackages={"services","controller","dao","model", "challenge","asset.pipeline.springboot"})
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
public class ChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeApplication.class, args);
	}
	
}

