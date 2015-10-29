package de.cellent.personService.boundary.rmi;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import de.cellent.personService.entity.Address;
import de.cellent.personService.entity.Department;
import de.cellent.personService.entity.Job;
import de.cellent.personService.entity.Person;

public class PersonServiceTest {
	
	private static PersonService service;

	public static void main(String[] args) {
		PersonServiceTest.setup();
		
		PersonServiceTest test = new PersonServiceTest();
//		test.testCeatePerson();
		test.testFindAll();
	}
	
	public void testCeatePerson() {
		Address a1 = new Address();
		Address a2 = new Address();
		
		Department d = new Department();
		
		Job j1 = new Job();
		j1.setDepartment(d);
		d.getJobs().add(j1);
		
		Job j2 = new Job();
		j2.setDepartment(d);
		d.getJobs().add(j2);
		
		Person p = new Person();
		p.getAddresses().add(a1);
		a1.setPerson(p);
		
		p.getAddresses().add(a2);
		a2.setPerson(p);
		
		p.getJobs().add(j1);
		j1.setPerson(p);
		
		p.getJobs().add(j2);
		j2.setPerson(p);
		
		try {
			service.createOrUpdateEntity(p);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testFindAll() {
		List<Person> hits = service.findAll(Person.class);
		for (Person person : hits) {
			System.out.println(person.getId());
		}
	}
	
	public static void setup() {
		try {
			Context ctx = new InitialContext();
			service = (PersonService) ctx.lookup("personService/personService_impl-0.0.1-SNAPSHOT/PersonServiceBean!de.cellent.personService.boundary.rmi.PersonService");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}

}
