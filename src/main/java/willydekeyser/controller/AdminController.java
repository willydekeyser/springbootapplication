package willydekeyser.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class AdminController {
	
	@Secured({"ROLE_GOLD", "ROLE_ADMIN", "ROLE_USER"})
	@RequestMapping(value = "/username", method = RequestMethod.GET)
	@ResponseBody
	public String currentUserName(Principal principal) {
		return principal.getName();
	}
	
	@ResponseStatus(code = HttpStatus.OK, reason = "Some parameters are invalid")
	@Secured("ROLE_GOLD")
	@RequestMapping(value = "/authentication_username", method = RequestMethod.GET)
	@ResponseBody
	public String currentUserName(Authentication authentication) {
		return authentication.getName();
	}
	
	@Secured("ROLE_GOLD")
	@RequestMapping(value = "/session", method = RequestMethod.GET)
    @ResponseBody
    public String retrieveMaxSessionIncativeInterval(HttpSession session) {
        return "Max Inactive Interval before Session expires: " + session.getMaxInactiveInterval();
    }
}
