package org.droidtv.tv.dlna;

/**
 * Interface to store the information about the transform setting sent to DMR
 * 
 * @author sulakshana.vasanth
 */
public interface IUPnPTransformSetting {
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
}
