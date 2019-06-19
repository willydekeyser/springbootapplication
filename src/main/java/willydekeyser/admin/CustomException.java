package willydekeyser.admin;

public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public CustomException(String message) {
		this.setMessage(message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
