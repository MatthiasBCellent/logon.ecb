/**
 * 
 */
package de.cellent.ecb.util.xml;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Uses JAX-WS/-RS (jackson) for parsing ...
 * 
 * @author mbohnen
 *
 */
public class XMLJSONObjectParser {

	private static final XMLJSONObjectParser THIS = new XMLJSONObjectParser();
	private ObjectMapper objectMapper = new ObjectMapper();

	private XMLJSONObjectParser() {
		// lazy
	}

	public static XMLJSONObjectParser getInstance() {
		return THIS;
	}

	public String toJSON(Object o) {

		String ret = null;

		try {
			objectMapper.setSerializationInclusion(Include.NON_NULL);
			ret = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
		} catch (JsonProcessingException e) {

			e.printStackTrace();
		}

		return ret;
	}

	public <T> T fromJSON(Class<T> clazz, String json) {
		T ret = null;

		try {
			ret = (T) objectMapper.readValue(json, clazz);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ret;
	}
	
	public String toXML(Class<?> clazz, Object obj) {
		
		String ret = null;
		
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
 
			java.io.StringWriter sw = new StringWriter();
			
			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(obj, sw);
			
			ret = sw.toString();
			
		} catch (PropertyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ret;
		
	}
	
	public <T> T fromXML(Class<T> clazz, String xml) {
		
		T obj = null;
		try {
			obj = (T) JAXBContext.newInstance(clazz).createUnmarshaller().unmarshal(new StringReader(xml));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return obj;
	}
}
