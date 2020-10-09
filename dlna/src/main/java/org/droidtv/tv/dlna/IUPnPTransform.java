package org.droidtv.tv.dlna;

/**
 * Interface to store information about transforms that are supported by DMR
 * 
 * @author sulakshana.vasanth
 */
public interface IUPnPTransform {
	/**
	 * Function to set the string value
	 * 
	 * @param id ID of the variable
	 * @param value value to be set
	 */
	public void setStringValue(int id, String value);

	/**
	 * Function to get string value
	 * 
	 * @param id ID of the variable
	 * @return value of the variable
	 */
	public String getStringValue(int id);

	/**
	 * Function to set the integer value
	 * 
	 * @param id ID of the variable
	 * @param value value to be set
	 */
	public void setIntValue(int id, int value);

	/**
	 * Function to get integer value
	 * 
	 * @param id ID of the variable
	 * @return value of the variable
	 */
	public int getIntValue(int id);
}
