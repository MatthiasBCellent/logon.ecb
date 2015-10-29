package de.cellent.ecb.util.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This is the ultimate, abstract super-class of all entities we make use of within our project.
 * Take this as an example which you might adopt to your needs, especially the generation of keys and the lack 
 * of the SerialVersionUID might be discussed ...
 * 
 * @author mbohnen
 *
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
	
	/** the primary key */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	/** optimistic locking */
	@Version
	private long version; 
	
	/** the user key */
	private String key;
	
	/** when was the object  created */
	private Date creationDate;
	
	public BaseEntity() {
		this.key = UUID.randomUUID().toString();
		this.creationDate = new Date(System.currentTimeMillis());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        // not using id coz might not be set yet
        hcb.append(this.key);

        return hcb.toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof BaseEntity)) {
            return false;
        }

        BaseEntity that = (BaseEntity) obj;
        EqualsBuilder eb = new EqualsBuilder();
        
        // not using id coz might not be set yet
        eb.append(this.getKey(), that.getKey());

        return eb.isEquals();
    }

    @Override
    public String toString() {
        ToStringBuilder tsb = new ToStringBuilder(this);
        
        // not using id coz might not be set yet
        tsb.append("key", this.key);

        return tsb.toString();
    }
}
