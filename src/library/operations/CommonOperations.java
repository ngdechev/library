package library.operations;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamReader;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import library.model.Command;
import library.xml.XmlReader;

public class CommonOperations {

	/**
	 * Reads all commands from commands.xml found in the root folder of this project.
	 * @return all available commands for this application
	 * @throws Exception
	 */
	public static String help() throws Exception {
		JAXBContext jc = JAXBContext.newInstance(Command.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();

		XMLStreamReader xsr = XmlReader.readXml("storage/commands.xml");
		xsr.nextTag(); // skip first line <xml version=...
		xsr.nextTag(); // skip <methods> line - this is another way if we don't need a wrapper class like Users or Books
		List<Command> methods = new ArrayList<>();
		while (xsr.hasNext()) {
			Command element = (Command) unmarshaller.unmarshal(xsr);
			methods.add(element);
			if (xsr.nextTag() != XMLStreamReader.START_ELEMENT) {
				break;
			}
		}
		StringBuilder helpBuilder = new StringBuilder();
		for (Command method : methods) {
			helpBuilder
				.append(method.getName())
				.append("\t")
				.append(method.getDescription())
				.append("\n");
		}
		
		return helpBuilder.toString();
	}

	public static void exit() {
		System.out.println("Exiting application.");
		System.exit(0);
	}
	
}
