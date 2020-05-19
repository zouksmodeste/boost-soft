package boostSoft.boostTest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Qualifier("customUserDetailsService")
	@Autowired
	private UserDetailsService userDetailsService;
	
	
	// configuration de securite d'acces aux end-points
	protected void configure(HttpSecurity http) throws Exception{
		http
		.httpBasic()
			.and()
				.authorizeRequests()
				
				// ouverture des end-points d'enregistrement et de validation du compte

					.antMatchers("/auth/register","/auth/validate","/auth/verifyaccount").permitAll()
					.antMatchers("/auth/**")
						.hasAnyRole("CUSTOMER")
					.antMatchers("/admin/**")
						.hasRole("ADMIN").and()
							.csrf().disable();
	}

	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		
//  ****Authentification a partir de la base de donnĂ©es !! ****
		auth.userDetailsService(userDetailsService).passwordEncoder(
				passwordEncoder());
	}

	
	// fonction de cryptage du mot de passe
	@Bean
	public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
	
}

