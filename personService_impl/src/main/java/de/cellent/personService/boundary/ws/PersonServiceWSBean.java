package de.cellent.personService.boundary.ws;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import de.cellent.personService.boundary.rmi.PersonServiceLocal;
import de.cellent.personService.boundary.ws.PersonServiceWS;
import de.cellent.personService.entity.Person;

@Stateless
@WebService(endpointInterface = "de.cellent.personService.boundary.ws.PersonServiceWS" )
public class PersonServiceWSBean implements PersonServiceWS {
	
	@EJB
	private PersonServiceLocal personService;

	public Person findPersonById(long id) {
		// avoiding nasty exceptions
		return this.personService.findAndLoadEntityWithLazyReferencesById(Person.class, id);
	}
}
