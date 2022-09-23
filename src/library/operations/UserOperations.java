package library.operations;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import javax.xml.stream.XMLStreamReader;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.PropertyException;
import jakarta.xml.bind.Unmarshaller;
import library.exceptions.UserException;
import library.model.User;
import library.model.Users;
import library.utils.Constants.Files;
import library.xml.XmlReader;

public class UserOperations {

	public static User find(String name, String password) throws Exception {
		Users users = getUsers();
		for (User dbUser : users.getUsers()) {
			if (dbUser.getName().equals(name)) {
				if (dbUser.getPassword().equals(password)) {
					return dbUser;
				}
			}
		}
		throw new UserException("Incorrect username or password.");
	}

	public static void add(User user) throws Exception {
		List<User> users = getAllUsers();
		for (User existingUser : users) {
			if (existingUser.getName().equals(user.getName())) {
				throw new UserException(String.format("User with name %s already exists.", user.getName()));
			}
		}
		users.add(user);
		Users updatedUsers = new Users(users);
		updateUsersDb(updatedUsers);
	}

	public static void remove(String username) throws Exception {
		List<User> users = getAllUsers();
		User userToRemove = null;
		for (User user : users) {
			if (user.getName().equals(username)) {
				userToRemove = user;
				break;
			}
		}
		if (userToRemove == null) 
			throw new UserException(String.format("User with name %s does not exist.", username));
		
		users.remove(userToRemove);
		
		Users updatedUsers = new Users(users);
		updateUsersDb(updatedUsers);
	}
	
	private static Users getUsers() throws Exception {
		JAXBContext jc = JAXBContext.newInstance(Users.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();

		XMLStreamReader xsr = XmlReader.readXml(Files.USERS_DB_XML);
		Users users = (Users) unmarshaller.unmarshal(xsr);
		
		return users;
	}

	private static List<User> getAllUsers() throws Exception {
		Users users = getUsers();
		List<User> list = users.getUsers();
		return list;
	}

	private static void updateUsersDb(Users updatedUsers) throws JAXBException, PropertyException, FileNotFoundException {
		JAXBContext contextObj = JAXBContext.newInstance(Users.class);
	
		Marshaller marshallerObj = contextObj.createMarshaller();
		marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	
		marshallerObj.marshal(updatedUsers, new FileOutputStream(Files.USERS_DB_XML));
	}
	
}
