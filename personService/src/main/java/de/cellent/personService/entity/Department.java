package de.cellent.personService.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import de.cellent.ecb.util.entity.BaseEntity;


@Entity
public class Department extends BaseEntity {
	
	@OneToMany(mappedBy = "department")
	private List<Job> jobs = new ArrayList<Job>();
	

	public List<Job> getJobs() {
		return jobs;
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}

}
