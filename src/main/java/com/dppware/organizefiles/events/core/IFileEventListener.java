package com.dppware.organizefiles.events.core;

public interface IFileEventListener {

	/**
	 * Simple on file generated event log info
	 * @param log
	 */
	public void onFileGeneratedEvent(String log);
	
	/**
	 * Simple on error event
	 * @param log
	 */
	public void onError(String log);
	
	/**
	 * On finish event
	 */
	public void onFinish();
}
