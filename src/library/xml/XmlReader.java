package library.xml;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

public class XmlReader {

	public static XMLStreamReader readXml(String filename) throws Exception {
		XMLInputFactory xif = XMLInputFactory.newFactory();
		XMLStreamReader xsr = null;

		InputStream resourceStream = XmlReader.class.getClassLoader().getResourceAsStream(filename);
		if (resourceStream != null) {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceStream));
			xsr = xif.createXMLStreamReader(bufferedReader);
		} else {
			xsr = xif.createXMLStreamReader(new FileReader(filename));
		}
		return xsr;
	}
}
