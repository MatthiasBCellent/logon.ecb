package de.cellent.ecb.util.process;


/**
 * Represents the superclass for all activities used in the steps of a
 * {@link Process}.
 */
public interface ProcessActivity<T> {
	
	public T proceed() throws ProcessException;
}