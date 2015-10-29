package de.cellent.ecb.util.control;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import de.cellent.ecb.util.boundary.GenericService;
import de.cellent.ecb.util.entity.BaseEntity;
import de.cellent.ecb.util.entity.GenericDAO;

//@LocalBean
//@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class GenericServiceBean implements GenericService {
	
	@Inject
	private GenericDAO dao;
	
	public <T extends BaseEntity> T createOrUpdateEntity(T entity) throws Exception {
		return this.dao.createOrUpdateEntity(entity);
	}

	public <T extends BaseEntity> void deleteEntity(T entity) {
		this.dao.deleteEntity(entity);		
	}

	public <T extends BaseEntity> long getIdHighValue(Class<T> clazz) {
		return this.dao.getIdHighValue(clazz);
	}

	public <T extends BaseEntity> T findAndLoadEntityWithLazyReferencesById(Class<T> clazz, long id) {
		return this.dao.findAndLoadEntityWithLazyReferencesById(clazz, id);
	}

	public <T extends BaseEntity> T findEntityById(Class<T> clazz, long id) {
		return this.dao.findEntityById(clazz, id);
	}

	public <T extends BaseEntity> T findEntityByKey(Class<T> clazz, String key) {
		return this.dao.findEntityByKey(clazz, key);
	}

	public <T extends BaseEntity> List<T> findAll(Class<T> clazz) {
		return this.dao.findAll(clazz);
	}

	public <T extends BaseEntity> List<T> findEntitiesByNamedQuery(Class<T> clazz, String queryName,
			Map<String, Object> params) {
		return this.dao.findEntitiesByNamedQuery(clazz, queryName, params);
	}
}
