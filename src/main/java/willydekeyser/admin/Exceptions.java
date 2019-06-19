package willydekeyser.admin;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class Exceptions {

	@ExceptionHandler
	@ResponseBody
	public String handleCustomException(CustomException exception) {
		return exception.getMessage();
	}
}
