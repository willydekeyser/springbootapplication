package willydekeyser.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Controller
@RequestMapping("testen")
public class TestenController {

	@GetMapping(path="/validateLogin", produces = "application/json")
	public @ResponseBody User validateLogin() {
		return new User("User successfully authenticated");
	}
	
	@GetMapping(path="/user", produces = "application/json")
	public @ResponseBody List<User>  currentUserName() {
		List<User> users = new ArrayList<User>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority authority : authorities) {
		     users.add(new User(authority.getAuthority()));
		  }
		return users;
	}
	
	@GetMapping(path="/menu", produces = "application/json")
	public @ResponseBody List<Menu>  menu() {
		List<Menu> menus = new ArrayList<Menu>();;
		Menu menu1 = new Menu("1", "link", "testen1");
		Menu menu2 = new Menu("2", "link", "testen2");
		Menu menu3 = new Menu("3", "link", "testen3");
		Menu menu4 = new Menu("4", "link", "testen4");
		menus.add(menu1);
		menus.add(menu2);
		menus.add(menu3);
		menus.add(menu4);
		
		return menus;
	}
	
	@GetMapping(path="/test1")
	public @ResponseBody Test test1() {
		Test test = new Test("1", "Willy De Keyser");
		return test;
	}
	
	@GetMapping(path="/test2")
	public @ResponseBody String test2() {	
		return "Test 2";
	}
	
	@GetMapping(path="/test3")
	public @ResponseBody String test3() {	
		return "Test 3";
	}
	
	@GetMapping(path="/test4")
	public @ResponseBody String test4() {	
		return "Test 4";
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
	private String status;

	public User(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
