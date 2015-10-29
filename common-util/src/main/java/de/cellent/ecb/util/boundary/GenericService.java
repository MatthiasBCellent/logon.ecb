package de.cellent.ecb.util.boundary;

import java.util.List;
import java.util.Map;

import de.cellent.ecb.util.entity.BaseEntity;

public interface GenericService {

	public abstract <T extends BaseEntity> T createOrUpdateEntity(T entity) throws Exception;

	public abstract  <T extends BaseEntity> void deleteEntity(T entity);

	public abstract  <T extends BaseEntity> long getIdHighValue(Class<T> clazz);

	public abstract  <T extends BaseEntity> T findAndLoadEntityWithLazyReferencesById(Class<T> clazz,
			long id);

	public abstract  <T extends BaseEntity> T findEntityById(Class<T> clazz, long id);

	public abstract  <T extends BaseEntity> T findEntityByKey(Class<T> clazz, String key);

	public abstract  <T extends BaseEntity> List<T> findAll(Class<T> clazz);

	public abstract  <T extends BaseEntity> List<T> findEntitiesByNamedQuery(Class<T> clazz,
			String queryName, Map<String, Object> params);

}