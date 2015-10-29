package de.cellent.personService.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import de.cellent.ecb.util.entity.BaseEntity;

@Entity
public class Address extends BaseEntity {

	@ManyToOne()
	@JoinColumn(name = "PERSON_ID")
	private Person person;

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
}
