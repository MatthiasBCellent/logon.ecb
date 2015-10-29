package de.cellent.personService.entity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.cellent.ecb.util.entity.GenericDAO;

public class PersonDAO extends GenericDAO {
	
	@PersistenceContext
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
