package willydekeyser.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.ToString;
import willydekeyser.model.Roles;
import willydekeyser.model.User;

@ToString
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
	private List<Roles> roles;
	
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
		for(Roles role: roles) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountnonexpired == null) ? 0 : accountnonexpired.hashCode());
		result = prime * result + ((accountnonlocked == null) ? 0 : accountnonlocked.hashCode());
		result = prime * result + ((credentialsnonexpired == null) ? 0 : credentialsnonexpired.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((enabled == null) ? 0 : enabled.hashCode());
		result = prime * result + ((gegevenspaswoord == null) ? 0 : gegevenspaswoord.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyUserDetails other = (MyUserDetails) obj;
		if (accountnonexpired == null) {
			if (other.accountnonexpired != null)
				return false;
		} else if (!accountnonexpired.equals(other.accountnonexpired))
			return false;
		if (accountnonlocked == null) {
			if (other.accountnonlocked != null)
				return false;
		} else if (!accountnonlocked.equals(other.accountnonlocked))
			return false;
		if (credentialsnonexpired == null) {
			if (other.credentialsnonexpired != null)
				return false;
		} else if (!credentialsnonexpired.equals(other.credentialsnonexpired))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (enabled == null) {
			if (other.enabled != null)
				return false;
		} else if (!enabled.equals(other.enabled))
			return false;
		if (gegevenspaswoord == null) {
			if (other.gegevenspaswoord != null)
				return false;
		} else if (!gegevenspaswoord.equals(other.gegevenspaswoord))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.roles))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}
