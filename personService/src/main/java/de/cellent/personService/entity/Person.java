package de.cellent.personService.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

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
public class Person extends BaseEntity {


	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@Fetch(FetchMode.SUBSELECT) // the proprietary annotation
//	private List<Address> addresses = new ArrayList<Address>(); // using List instead of Set
	private Set<Address> addresses = new HashSet<Address>();

	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@Fetch(FetchMode.SUBSELECT)  // the proprietary annotation
//	private List<Job> jobs = new ArrayList<Job>(); // using List instead of Set
	private Set<Job> jobs = new HashSet<Job>();


	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	public Set<Job> getJobs() {
		return jobs;
	}

	public void setJobs(Set<Job> jobs) {
		this.jobs = jobs;
	}
}
