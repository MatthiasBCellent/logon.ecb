package de.cellent.personService.control;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import de.cellent.ecb.util.control.GenericServiceBean;
import de.cellent.personService.boundary.rmi.PersonService;
import de.cellent.personService.boundary.rmi.PersonServiceLocal;

@Stateless
@Remote(PersonService.class)
@Local(PersonServiceLocal.class)
public class PersonServiceBean extends GenericServiceBean implements PersonService, PersonServiceLocal {

	// not much to do, all is done by GenericServiceBean
}
