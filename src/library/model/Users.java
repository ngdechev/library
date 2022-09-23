package library.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Wrapper class for list of User objects in XML file
 */
@XmlRootElement
public class Users {

	private List<User> users = new ArrayList<>();

	public Users() {};
	
	public Users(List<User> users) {
		this.setUsers(users);
	}

	@XmlElement(name = "user")
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}
