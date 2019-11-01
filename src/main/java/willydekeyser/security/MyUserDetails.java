package willydekeyser.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import willydekeyser.model.Role;
import willydekeyser.model.User;

public class MyUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String gegevenspaswoord;
	private String email;
	private Boolean accountnonexpired;
	private Boolean accountnonlocked;
	private Boolean credentialsnonexpired;
	private Boolean enabled;
	private List<Role> roles;
	
	public MyUserDetails(User user) {
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.gegevenspaswoord = user.getGegevenspaswoord();
		this.email = user.getEmail();
		this.accountnonexpired = user.getAccountnonexpired();
		this.accountnonlocked = user.getAccountnonlocked();
		this.credentialsnonexpired = user.getCredentialsnonexpired();
		this.enabled = user.getEnabled();
		this.roles = user.getRoles();
	}
	
	public MyUserDetails() {
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for(Role role: roles) {
			authorities.add(new SimpleGrantedAuthority(role.getRole()));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public String getGegevensPaswoord() {
		return gegevenspaswoord;
	}
	
	@Override
	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return accountnonexpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountnonlocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsnonexpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

}
