package willydekeyser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import willydekeyser.customproperties.CustomProperties;
import willydekeyser.customproperties.CustomPropertiesService;

@Controller
@RequestMapping("restcontroller")
public class CustomPropertiesController {
	
	@Autowired
	CustomPropertiesService CustomPropertiesService;
	
	@GetMapping(path="/customproperties")
	public @ResponseBody CustomProperties customProperties() {
		return CustomPropertiesService.readCustomProperties();
	}
}
