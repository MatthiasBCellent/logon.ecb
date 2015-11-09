package de.cellent.personService.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import de.cellent.ecb.util.entity.BaseEntity;

/**
 * 
drop table address ;
drop table Department ;
drop table job ;
drop table Person ;

 * @author mbohnen
 *
 */
@Entity
@NamedQueries(value = { 
		@NamedQuery(name = Person.FIND_BY_NAME, query = "SELECT p FROM Person AS p WHERE p.firstName =:firstName AND p.lastName =:lastName") })
public class Person extends BaseEntity {

	public static final String FIND_BY_NAME = "findByName";
	
	private String firstName;
	
	private String lastName;

	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT) // the proprietary annotation
	private List<Address> addresses = new ArrayList<Address>(); // using List instead of Set
//	private Set<Address> addresses = new HashSet<Address>();

	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)  // the proprietary annotation
	private List<Job> jobs = new ArrayList<Job>(); // using List instead of Set
//	private Set<Job> jobs = new HashSet<Job>();

	public List<Address> getAddresses() {
		return addresses;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public List<Job> getJobs() {
		return jobs;
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}
}
