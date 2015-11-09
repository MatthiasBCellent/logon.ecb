package de.cellent.test.webui;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.cellent.personService.boundary.rmi.PersonService;
import de.cellent.personService.entity.Person;

/**
 * Invocation: http://localhost:8080/personService/personService?firstName=Peter&lastName=Pan
 * @author mbohnen
 *
 */
@WebServlet(urlPatterns = {"/personService"})
public class MainServlet extends HttpServlet {
	
	@EJB
	private PersonService personService;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		System.out.println(firstName + lastName);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("firstName", firstName);
		params.put("lastName", lastName);
		
		List<Person> hits = personService.findEntitiesByNamedQuery(Person.class, Person.FIND_BY_NAME, params);
		
		PrintWriter writer = resp.getWriter();
		for (Person person : hits) {
			writer.println("Person found: " + person.toString());
		}
		writer.close();	
	}
}
