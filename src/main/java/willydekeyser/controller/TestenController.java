package willydekeyser.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Controller
@RequestMapping("testen")
public class TestenController {

	@GetMapping(path="/validateLogin", produces = "application/json")
	public @ResponseBody TestUser validateLogin() {
		return new TestUser("User successfully authenticated");
	}
	
	@GetMapping(path="/user", produces = "application/json")
	public @ResponseBody List<TestUser>  currentUserName() {
		List<TestUser> users = new ArrayList<TestUser>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority authority : authorities) {
		     users.add(new TestUser(authority.getAuthority()));
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
class TestTest{
	
	private String id;
	private String naam;
	
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class TestMenu{
	
	private String id;
	private String naam;
	private String link;
	
}

class TestUser {
	private String status;

	public TestUser(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
