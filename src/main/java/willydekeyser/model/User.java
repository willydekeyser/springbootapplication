package willydekeyser.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	private String username;
	private String password;
	private String email;
	private Boolean enabled;
	private List<Role> roles;
}
