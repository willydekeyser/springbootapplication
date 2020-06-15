package willydekeyser.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Controller
@RequestMapping("users")
public class UsersController {

	@GetMapping(path="/validateLogin", produces = "application/json")
	public @ResponseBody User validateLogin(CsrfToken token) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();		
		return new User(authentication.getName(), token.getToken());
	}
	
	@GetMapping(path="/user", produces = "application/json")
	public @ResponseBody List<User>  currentUserName(CsrfToken token) {
		List<User> users = new ArrayList<User>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority authority : authorities) {
		     users.add(new User(authority.getAuthority(), token.getToken()));
		  }
		return users;
	}
	
	@GetMapping(path="/headerMenu/{user}", produces = "application/json")
	public @ResponseBody List<Menu>  headerMenu(@PathVariable String user) {
		List<Menu> menus = new ArrayList<Menu>();
		if(user.equals("Willy De Keyser")) {
			Menu menu1 = new Menu("1", "Leden", "/leden");
			Menu menu2 = new Menu("2", "Lidgeld", "/lidgeld");
			Menu menu3 = new Menu("3", "Kasboek", "/kasboek");
			Menu menu4 = new Menu("4", "Rubrieken", "/rubrieken");
			Menu menu5 = new Menu("5", "Soorten leden", "/soortenleden");
			menus.add(menu1);
			menus.add(menu2);
			menus.add(menu3);
			menus.add(menu4);
			menus.add(menu5);
		}
		
		if(user.equals("Computerclub")) {
			Menu menu1 = new Menu("1", "Leden", "/leden");
			Menu menu2 = new Menu("2", "Lidgeld", "/lidgeld");
			menus.add(menu1);
			menus.add(menu2);
		
		}
		
		if(user.equals("user")) {
			
			Menu menu4 = new Menu("4", "Rubrieken", "/rubrieken");
			Menu menu5 = new Menu("5", "Soorten leden", "/soortenleden");
			menus.add(menu4);
			menus.add(menu5);
		}
		
		return menus;
	}
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Test{
	
	private String id;
	private String naam;
	
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Menu{
	
	private String id;
	private String naam;
	private String link;
	
}

class User {
	private String naam;
	private String token;

	public User(String naam, String token) {
		this.naam = naam;
		this.setToken(token);
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
