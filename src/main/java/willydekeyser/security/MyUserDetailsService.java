package willydekeyser.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import willydekeyser.model.User;
import willydekeyser.service.IUserService;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private IUserService userservice;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userservice.findByUserName(username);
		if (user.getUsername() == null) {
			throw new UsernameNotFoundException("User niet gevonden in de database");
		}
		return new MyUserDetails(user);
	}

}
