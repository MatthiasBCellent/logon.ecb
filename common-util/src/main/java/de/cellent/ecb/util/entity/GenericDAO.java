package de.cellent.ecb.util.entity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.cellent.ecb.util.boundary.GenericService;


@LocalBean
public abstract class GenericDAO implements GenericService {
	
	private static final Logger LOG = LoggerFactory.getLogger(GenericDAO.class);
	
	/**
     * Get the entity manager defined per activity.
     * 
     * @return the entity manager instance
     */
    protected abstract EntityManager getEntityManager();

    
	public <T extends BaseEntity> T createOrUpdateEntity(T entity) {
    	
        if (entity == null) {
            return null;
        }

        // as we have no id yet, it must be freshly brewed
        if (null == entity.getId()) {
            LOG.debug("createOrUpdateEntity::create:: {}", entity);
            this.getEntityManager().persist(entity);
        }
        // if there is an id, we must have dealt with it before
        else {
            LOG.debug("createOrUpdateEntity::update:: {}", entity);
            this.getEntityManager().merge(entity);
        }

        return entity;
    }

	public <T extends BaseEntity> void deleteEntity(T entity) {
        LOG.debug("deleteEntity:: {}", entity);
        this.getEntityManager().remove(entity);
    }
    

	public <T extends BaseEntity> long getIdHighValue(Class<T> clazz) {
        LOG.debug("getIdHighValue:: class={}", clazz);
        Query q = getEntityManager().createQuery("SELECT coalesce(MAX(e.id), 0) FROM " + clazz.getSimpleName() + " e");
        Object result = q.getSingleResult();
        return (Long) result;
    }

	public <T extends BaseEntity> T findAndLoadEntityWithLazyReferencesById(Class<T> clazz, long id) {
		
		LOG.debug("findAndLoadEntityWithLazyReferencesById:: clazz= {}, id= {}", clazz, id);
        T entity = this.getEntityManager().find(clazz, id);

        if (entity != null) {
            // getting all fields of the object
            Field[] fields = clazz.getDeclaredFields();

            // for each field we need to ...
            for (Field field : fields) {
                LOG.debug("Checking field: {}", field.getName());
                // ... determine the type and ...
                Class<?> fieldType = field.getType();

                // ... do the needful
                if (Collection.class.isAssignableFrom(fieldType)) {
                    try {
                        String getter = this.generateGetterMethodNameFromFieldName(field.getName());
                        LOG.debug("findAndLoadEntityWithLazyReferencesById mName: {}", getter);
                        
                    	String setter = this.generateSetterMethodNameFromFieldName(field.getName());
                    	LOG.debug("findAndLoadEntityWithLazyReferencesById mName: {}", setter);
                    	
                        Method getterMethod = clazz.getMethod(getter);
                        Method setterMethod = clazz.getMethod(setter, getterMethod.getReturnType());
                        
                        Collection<?> returnFromGetter = (Collection<?>) getterMethod.invoke(entity);
                        
                        if (CollectionUtils.isNotEmpty(returnFromGetter)) {
                            // 'touch' the first one
                            returnFromGetter.iterator().next();
                        } else {
                        	// unfortunately we can't touch the first element ... and still might get that 
                        	// LazyInitializationException. So we will put a plain new Collection to the field so
                        	// we get rid of the PersistentBag of Hibernate
                        	LOG.debug("Collection " + field.getName() + " of Type "+ field.getClass() +  " is empty");
                        	if(List.class.isAssignableFrom(returnFromGetter.getClass())) {
                        		setterMethod.invoke(entity, new ArrayList());
                        	}
                        }
                    } catch (Exception e) {
                        throw new PersistenceException("Failed find with lazy references for " + clazz.getSimpleName()
                                + ", id= " + id, e);
                    }
                }
            }
        }

        LOG.debug("findAndLoadEntityWithLazyReferencesById:: result= {}", entity);
        return entity;
	}
	
	public <T extends BaseEntity> T findEntityById(Class<T> clazz, long id) {
        LOG.debug("findEntityById:: class={}, id={}", clazz, id);
        T entity = this.getEntityManager().find(clazz, id);
        LOG.debug("findEntityById:: entity={}", entity);
        if (entity == null) {
            LOG.error("No entity found: " + id);
        }
        return entity;
    }
    
	public <T extends BaseEntity> T findEntityByKey(Class<T> clazz, String key) {
        LOG.debug("findEntityByKey:: class={}, key={}", clazz, key);
        
		TypedQuery<T> query = this.getEntityManager().createQuery("FROM " + clazz.getSimpleName() + " AS c WHERE c.key = :key", clazz);
		query.setParameter("key", key);
		T result = query.getSingleResult();
        LOG.debug("findEntityByKey:: result={}", result);
	    return result;
    }
    
	public <T extends BaseEntity> List<T> findAll(Class<T> clazz)
    {
        LOG.debug("findAll:: class= {}", clazz);
        String className = clazz.getSimpleName();

        TypedQuery<T> query = getEntityManager().createQuery("FROM " + className, clazz);

        List<T> result = query.getResultList();
        LOG.debug("findAll:: result={}", result);

        return result;
    }
	
	public <T extends BaseEntity> List<T> findEntitiesByNamedQuery(Class<T> clazz, String queryName, Map<String, Object> params) {
    	
    	TypedQuery<T> query = this.getEntityManager().createNamedQuery(queryName, clazz);

        if (params != null) 
        {
            for (String paramName : params.keySet()) {
            	query.setParameter(paramName,params.get(paramName));
			}
        }

        List<T> result = query.getResultList();
        LOG.debug("findByNamedQuery:: result={}", result);
        
        return result;
    }
	
    /**
     * Do the things needed to get a 'getFoo()' from a field named 'foo'
     * 
     * @param fieldName
     *            the field name
     * @return the getter method name for the give field name
     */
    private String generateGetterMethodNameFromFieldName(String fieldName) {
        return "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
    }
    
    private String generateSetterMethodNameFromFieldName(String fieldName) {
        return "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
    }
}
