package de.cellent.personService.control;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import de.cellent.ecb.util.control.GenericServiceBean;
import de.cellent.personService.boundary.rmi.PersonService;
import de.cellent.personService.boundary.rmi.PersonServiceLocal;
import de.cellent.personService.entity.PersonDAO;

@Stateless
@Remote(PersonService.class)
@Local(PersonServiceLocal.class)
public class PersonServiceBean extends GenericServiceBean implements PersonService, PersonServiceLocal {

	// not used in here, just to show how to make use of it
	@EJB
	private PersonDAO dao;
	
	// not much to do, all is done by GenericServiceBean 
	// (but specific code might go here)
	
	
}
