package willydekeyser.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import willydekeyser.model.Role;
import willydekeyser.model.User;
import willydekeyser.service.IUserService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private IUserService userservice;
	
	private MyUserDetails myuserDetails = new MyUserDetails();
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	//private SCryptPasswordEncoder encoder = new SCryptPasswordEncoder();
	//private Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder();
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		User user = userservice.findByUserName(username);
		if (user == null) {
			throw new UsernameNotFoundException("User name " + username + " not found");
		}
		List<GrantedAuthority> authorities = new ArrayList<>();
		for(Role role: user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getRole()));
		}
		myuserDetails = new MyUserDetails(user);
		if (username.equals(user.getUsername()) && encoder.matches(password, user.getPassword()) && user.getEnabled() && 
				user.getAccountnonexpired() && user.getAccountnonlocked() && user.getCredentialsnonexpired()) {
			return new UsernamePasswordAuthenticationToken(myuserDetails, password, authorities);
		} else {
			throw new BadCredentialsException("User name " + username + " niet toegelaten op deze site");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
}
