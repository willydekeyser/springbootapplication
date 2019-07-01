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
	CustomProperties customProperties;
	
	@Autowired
	CustomPropertiesService customPropertiesService;
	
	@GetMapping(path="/custompropertieswrite")
	public @ResponseBody CustomProperties writeCustomProperties() {
		
		customProperties = customPropertiesService.readCustomProperties();
		customProperties.setEen(500);
		customPropertiesService.writeCustomProperties(customProperties);
		return customProperties;
	}
	
	@GetMapping(path="/custompropertiesread")
	public @ResponseBody CustomProperties readCustomProperties() {
				
		return customPropertiesService.readCustomProperties();
	}
	
	@GetMapping(path="/customproperties")
	public @ResponseBody CustomProperties customProperties() {
				
		return customProperties;
	}
}
