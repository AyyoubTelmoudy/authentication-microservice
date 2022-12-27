package com.emsi.pfe;

import com.emsi.pfe.constant.SecurityConstants;
import com.emsi.pfe.entity.Role;
import com.emsi.pfe.repository.RoleRepository;
import com.emsi.pfe.util.Utils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class AuthenticationMicroserviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthenticationMicroserviceApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	@Bean
	CommandLineRunner start(RoleRepository roleRepository, Utils utils)
	{
		return  args -> {
           if(roleRepository.findByRole(SecurityConstants.PASSENGER)==null)
		   {
			   Role passengerRole=new Role();
			   passengerRole.setPublicId(utils.genereteRandomString(20));
			   passengerRole.setRole(SecurityConstants.PASSENGER);
			   roleRepository.save(passengerRole);
		   }
           if(roleRepository.findByRole(SecurityConstants.DRIVER)==null)
		   {
			   Role driverRole=new Role();
			   driverRole.setPublicId(utils.genereteRandomString(20));
			   driverRole.setRole(SecurityConstants.DRIVER);
			   roleRepository.save(driverRole);
		   }

		};
	}

}
