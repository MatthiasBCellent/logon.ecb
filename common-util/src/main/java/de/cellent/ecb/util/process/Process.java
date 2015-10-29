package de.cellent.ecb.util.process;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


/**
 * This class represents a superclass of all possible Processes
 *
 */
public abstract class Process {

	private Logger log = Logger.getLogger(Process.class);
	
	public static final int PROCESS_END = 99;
	public static final int PROCESS_START = 1;
	private int runstate = 1;
	
	private boolean hasFinished = false;

	private Map<String, ProcessParameter> processParams = new HashMap<String, ProcessParameter>();
	
	public Process() {
		// lazy
	}
	
	public Process(HashMap<String, ProcessParameter> processParams) {
		this.processParams = processParams;
	}
	
	/** by default a 'false' will be returned */
	private Object operationResult = new Boolean(false);
	
	/**
	 * Start each Process. For implementing an own Process, you have to
	 * implement the method runSteps.
	 */
	public synchronized void proceed() throws ProcessException {
		try {
			beforeProcess();
			while (runstate != Process.PROCESS_END) {
				runSteps();
			}
			afterProcess();
			end();
		} catch (ProcessException e) {
			log.debug("proceed() failed in " + this + " at runstate=" + runstate, e);
			throw e;	
		} catch (Exception e) {
			log.debug("proceed() failed in " + this + " at runstate=" + runstate, e);
			throw new ProcessException(e.getMessage(), e);
		}
	}

	/**
	 * steps through each step of a Process. This method is called by the <code>proceed</code>
	 * method of this Process. This method is called by the <code>proceed</code> method as long as
	 * the end of this Process is not reached. Means, that the last Process Activity has to set the
	 * run state to <code>Process.PROCESS_END</code>
	 * @throws ProcessException
	 */
	public abstract void runSteps() throws ProcessException;
	
	/**
	 * overwrite this method if it's necessary for the Process to do something before the Process.
	 * This method will be called once at the beginning of each Process in the <code>proceed</code> method.
	 * @throws ProcessException
	 */	
	public void beforeProcess() throws ProcessException {
		this.setRunstate(PROCESS_START);
		
	}

	/**
	 * overwrite this method if it's necessary for the Process to do something after the Process.
	 * This method will be called once at the end of each Process in the <code>proceed</code> method.
	 * @throws ProcessException
	 */
	public void afterProcess() throws ProcessException {
		this.setRunstate(PROCESS_END);	
	}
	
	
	/**
	 * @return the hasFinished
	 */
	public boolean hasFinished() {
		return hasFinished;
	}

	public void end() {
		hasFinished = true;
	}
	
	public void setRunstate(int runstate) {
		this.runstate = runstate;
	}
	
	public int getRunstate() {
		return runstate;
	}
	
	public void setProcessParams(Map<String, ProcessParameter> params) {	
		this.processParams = params;	
	}

	public Map<String, ProcessParameter> getProcessParams() {
		return processParams;
	}
	
	/**
	 * Getting a certain parameter by it's key, will be cast automatically
	 * to the appropriate class.
	 * 
	 * @param key the key
	 * @return the value
	 * @throws ProcessException
	 */
	public <T> T getProcessParameterValueByKey(String key) throws ProcessException {
		
		ProcessParameter p = this.processParams.get(key);
		if (null == p) {
			throw new ProcessException("requested process parameter doesn't exist");
		}
		
		Class clazz = p.getParamClass();
		clazz.cast(p.getParamValue());
		
		return (T) p.getParamValue();
	}

	/**
	 * 
	 * @return
	 */
	public <T> T getOperationResult() {
		return (T) operationResult;
	}

	protected void setOperationResult(Object operationResult) {
		this.operationResult = operationResult;
	}
}
