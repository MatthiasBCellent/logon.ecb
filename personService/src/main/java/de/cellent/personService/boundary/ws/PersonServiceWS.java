package de.cellent.personService.boundary.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

import de.cellent.personService.entity.Person;


@WebService
public interface PersonServiceWS {

	@WebMethod
	Person findPersonById(long id);
	
}
