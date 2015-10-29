package de.cellent.ecb.util.process;

import java.io.Serializable;

/**
 * The parameters a process needs
 * 
 * @author mbohnen
 *
 */
public class ProcessParameter implements Serializable {
	
	private String key;
	
	private Class paramClass;
	
	private Object paramValue;
	
	public ProcessParameter() {
		
	}
	
	public ProcessParameter(String key, Class paramClass, Object paramObject) {
		this.key = key;
		this.paramClass = paramClass;
		this.paramValue = paramObject;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Class getParamClass() {
		return paramClass;
	}

	public void setParamClass(Class paramClass) {
		this.paramClass = paramClass;
	}

	public Object getParamValue() {
		return paramValue;
	}

	public void setParamValue(Object paramValue) {
		this.paramValue = paramValue;
	}
}
