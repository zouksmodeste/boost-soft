package boostSoft.boostTest.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import boostSoft.boostTest.data.User;
import boostSoft.boostTest.data.UserType;
import boostSoft.boostTest.repository.UserRepository;
import boostSoft.boostTest.utils.RoleEnum;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository compteRepository;

	// logger l'utilisateur en lui attribuant les droits correspondants a son type
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User compte = compteRepository.findByUserName(username);
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		UserDetails userDetails = new org.springframework.security.core.userdetails.User(compte.getUserName(),
				compte.getPassWord(), true, accountNonExpired, credentialsNonExpired, accountNonLocked,
				getAuthorities(compte.getUserType()));
		return userDetails;
	}

	private Collection<? extends GrantedAuthority> getAuthorities(UserType typeCompte) {

		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();

		// si le type de compte est administrateur
		if (typeCompte.getStatut().equals("ADMIN")) {

			// les roles user et admin lui sont accordés
			grantedAuthorities.add(new SimpleGrantedAuthority(RoleEnum.ROLE_CUSTOMER.getRole()));
			grantedAuthorities.add(new SimpleGrantedAuthority(RoleEnum.ROLE_ADMIN.getRole()));

		}

		// si le type de compte est user
		else if (typeCompte.getStatut().equals("CUSTOMER")) {

			// le role user lui est accordé
			grantedAuthorities.add(new SimpleGrantedAuthority(RoleEnum.ROLE_CUSTOMER.getRole()));
		}

		return grantedAuthorities;

	}

}
