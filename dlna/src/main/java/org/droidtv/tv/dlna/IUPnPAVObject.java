package org.droidtv.tv.dlna;

import org.droidtv.tv.dlna.UPnPAVObject.Component;
import org.droidtv.tv.dlna.UPnPAVObject.ComponentGroup;
import org.droidtv.tv.dlna.UPnPAVObject.Resource;
import org.droidtv.tv.dlna.UPnPAVObject.ResourceExtension;

import java.util.ArrayList;

/**
 * Interface to store the information about the object returned as part of
 * browse of search result.
 * 
 * @author sulakshana.vasanth
 */

public interface IUPnPAVObject {
	/**
	 * Function to set channel information like ID and type
	 * 
	 * @param id ID of the information
	 * @param value value to be set for the ID.
	 */
	public void setMultiValueValue(final int id, final String value);

	/**
	 * FUnction to get channel information
	 * 
	 * @param id ID of the information
	 * @param index index of the ID from were data to be fetched
	 * @return channel information
	 */
	public String getMultiValueValue(final int id, final int index);

	/**
	 * Function to set resource of string type
	 * 
	 * @param id ID of the resource value
	 * @param resNum which resource to be used
	 * @param value value to be set
	 */
	public void setResourceValue(final int id, final int resNum, final String value);

	/**
	 * Function to set resource of interger type
	 * 
	 * @param id ID of the resource value
	 * @param resNum which resource to be used
	 * @param value value to be set
	 */
	public void setResourceValue(final int id, final int resNum, final int value);

	/**
	 * Function to set resource of interger type
	 * 
	 * @param id ID of the resource value
	 * @param resNum which resource to be used
	 * @param value value to be set
	 */
	public void setResourceValue(final int id, final int resNum, final float value);

	/**
	 * Function to add a resource extension
	 * 
	 * @param resExtID ID of the resource extension
	 */
	public void addResourceExt(final String resExtID);

	/**
	 * Function to get the resource extension
	 * 
	 * @param id ID of the resource
	 * @return resource extension object
	 */
	public IResourceExtension getResourceExt(final String id);

	/**
	 * Function to get the resource extension component group
	 * 
	 * @param resExtID ID of the resource extension
	 * @param compGroupID ID of component group
	 * @return resource extension component group object
	 */
	public ComponentGroup getResourceExtComponentGroup(final String resExtID, final String compGroupID);

	/**
	 * Function to add resource extension component group
	 * 
	 * @param resExtID ID of the resource extension
	 * @param compGroupID ID of component group
	 * @param isCompGroupReq to check if component group is required
	 */
	public void addResourceExtComponentGroup(final String resExtID, final String compGroupID, final int isCompGroupReq);

	/**
	 * Function to add resource extension component
	 * 
	 * @param resExtID ID of the resource extension
	 * @param compGroupID ID of component group
	 * @param compID ID of component
	 * @param compClass class of component
	 * @param compMimeType mime type of component
	 * @param compExtType extension type of component
	 * @param compResProtocolInfo protocol information of component
	 * @param compResURL resource URL of component
	 */
	public void addResourceExtComponent(final String resExtID, final String compGroupID, final String compID,
                                        final String compClass, final String compMimeType, final String compExtType,
                                        final String compResProtocolInfo, final String compResURL);

	/**
	 * Function to get components
	 *
	 * @param compClassName class name of component
	 * @return array list of components
	 */
	public ArrayList<Component> getComponents(final String compClassName);

	/**
	 * Function to get the resource value of string type
	 *
	 * @param id ID of the resource value
	 * @param resNum which resource to be used
	 * @return value the resource
	 */
	public String getResourceValue(final int id, final int resNum);

	/**
	 * Function to get the resource value of float type
	 *
	 * @param id ID of the resource value
	 * @param resNum which resource to be used
	 * @return value the resource
	 */
	public float getResourceValuefloat(final int id, final int resNum);

	/**
	 * Function to get the resource value of integer type
	 *
	 * @param id ID of the resource value
	 * @param resNum which resource to be used
	 * @return value the resource
	 */
	public int getResourceValueInt(final int id, final int resNum);

	/**
	 * Function to set the string value
	 *
	 * @param id ID of the variable
	 * @param value value to be set
	 */
	public void setStringValue(final int id, final String value);

	/**
	 * Function to set the integer value
	 *
	 * @param id ID of the variable
	 * @param value value to be set
	 */
	public void setIntegerValue(final int id, final int value);

	/**
	 * Function to get integer value
	 *
	 * @param id ID of the variable
	 * @return value of the variable
	 */
	public int getIntegerValue(final int id);

	/**
	 * Function to get string value
	 *
	 * @param id ID of the variable
	 * @return value of the variable
	 */
	public String getStringValue(final int id);

	/**
	 * Function to get the resource list
	 *
	 * @return array list of resources
	 */
	public ArrayList<Resource> getRessourcesList();

	/**
	 * Method to get Resource extension list
	 *
	 * @return list of ResourceExtension
	 */
	public ArrayList<ResourceExtension> getResourceExtensionsList();

	/**
	 * Function to set the number of items in browse result
	 *
	 * @param value value of the variable
	 */
	public void setNrItems(final int value);

	/**
	 * Function to set the error in browse call
	 *
	 * @param value error value
	 */
	public void setBrowseError(final int value);

	/**
	 * Function to get the cookie
	 *
	 * @return value of cookie
	 */
	public String getCookie();

	/**
	 * Function to get subtitle
	 *
	 * @return value of subtitle
	 */
	public String getSubtitleList();

	/**
	 * Function to set cookie
	 *
	 * @param cookie value of cookie
	 */
	public void setCookie(String cookie);

	/**
	 * Interface to store the component information
	 *
	 * @author sulakshana.vasanth
	 */
	public interface IComponent {
		/**
		 * Function to get the component ID
		 *
		 * @return component ID
		 */
		public String getComponentID();

		/**
		 * Function to get the component class
		 *
		 * @return component class
		 */
		public String getComponentClass();

		/**
		 * Function to get the component MIME type
		 *
		 * @return component MIME type
		 */
		public String getComponentMimeType();

		/**
		 * Function to get the component extended type
		 *
		 * @return component extended type
		 */
		public String getComponentExtendedType();

		/**
		 * Function to get the component protocol information
		 *
		 * @return component protocol information
		 */
		public String getComponentResProtocolInfo();

		/**
		 * Function to get the component resource URL
		 *
		 * @return component resource URL
		 */
		public String getComponentResURL();

	}

	/**
	 * Interface to store information about component groups
	 *
	 * @author sulakshana.vasanth
	 */
	public interface IComponentGroup {
		/**
		 * Function to add component
		 *
		 * @param comp component class
		 */
		public void addComponent(final IComponent comp);

		/**
		 * Function to add components
		 *
		 * @param compID ID of component
		 * @param compClass class of component
		 * @param compMimeType MIME type of component
		 * @param compExtType extension type of component
		 * @param compResProtocolInfo protocol information of component
		 * @param compResURL resource URL of component
		 */
		public void addComponent(final String compID, final String compClass, final String compMimeType,
                                 final String compExtType, final String compResProtocolInfo, final String compResURL);

		/**
		 * Function to get group ID
		 * 
		 * @return value of group ID
		 */
		public String getGroupID();

		/**
		 * Function to check if the component is required
		 * 
		 * @return true if required else false.
		 */
		public Boolean isComponentRequired();

		/**
		 * Function to get component list
		 * 
		 * @return array list of components
		 */
		public ArrayList<Component> getComponentList();
	}

	/**
	 * Interface to store information about resource extensions
	 * 
	 * @author sulakshana.vasanth
	 */
	public interface IResourceExtension {
		/**
		 * Function to add component group
		 * 
		 * @param compGroup component group class
		 */
		public void addComponentGroup(final IComponentGroup compGroup);

		/**
		 * Function to get resource extension ID
		 * 
		 * @return value of resource extension
		 */
		public String getResExtID();

		/**
		 * Function to set resource extension ID
		 * 
		 * @param id of resource extension
		 */
		public void setResExtID(final String id);

		/**
		 * Function to get component group
		 * 
		 * @param id ID of component group
		 * @return component group class
		 */
		public ComponentGroup getComponentGroup(final String id);

		/**
		 * Function to get component group
		 * 
		 * @return arry list of component group list class
		 */
		public ArrayList<ComponentGroup> getComponentGroupList();
	}

	/**
	 * Interface to store resouce information
	 * 
	 * @author sulakshana.vasanth
	 */
	public interface IResource {

		/**
		 * Function to set resource ID
		 * 
		 * @param id value of resource ID
		 */
		public void setResID(final String id);

		/**
		 * Function to set resource URL
		 * 
		 * @param url value of resource URL
		 */
		public void setURL(final String url);

		/**
		 * Function to set resource bit rate
		 * 
		 * @param bRate value of resource bit rate
		 */
		public void setBitrate(final int bRate);

		/**
		 * Function to set resource file size
		 * 
		 * @param bSize value of resource file size
		 */
		public void setFilesize(final int bSize);

		/**
		 * Function to set resource protocol information
		 * 
		 * @param protocol value of resource protocol information
		 */
		public void setProtocolInfo(final String protocol);

		/**
		 * Function to set resource x co-ordinate
		 * 
		 * @param size value of resource x co-ordinate
		 */
		public void setXSize(final int size);

		/**
		 * Function to set resource y co-ordinate
		 * 
		 * @param size value of resource y co-ordinate
		 */
		public void setYSize(final int size);

		/**
		 * Function to set resource color depth
		 * 
		 * @param depth value of resource color depth
		 */
		public void setColorDepth(final int depth);

		/**
		 * Function to set resource number of channel
		 * 
		 * @param num value of resource number of channel
		 */
		public void setNumChannel(final int num);

		/**
		 * Function to set resource bit rate sample
		 * 
		 * @param bPerSample value of resource bit rate sample
		 */
		public void setBitsPerSample(final int bPerSample);

		/**
		 * Function to set resource sample frequency
		 * 
		 * @param sampleFreq value of resource sample frequency
		 */
		public void setSampleFrequency(final float sampleFreq);

		/**
		 * Function to set resource frame rate
		 * 
		 * @param fRate value of resource frame rate
		 */
		public void setFrameRate(final float fRate);

		/**
		 * Function to set resource duration
		 *
		 * @param duration value of resource duration
		 */
		public void setDuration(final int duration);

		/**
		 * Function to get resource ID
		 * 
		 * @return value of resource ID
		 */
		public String getResID();

		/**
		 * Function to get resource URL
		 *
		 * @return value of resource URL
		 */
		public String getURL();

		/**
		 * Function to get resource protocol information
		 * 
		 * @return value of resource protocol information
		 */
		public String getProtocolInfo();

		/**
		 * Function to get resource bit rate
		 * 
		 * @return value of resource bit rate
		 */
		public int getBitrate();

		/**
		 * Function to get resource x co-ordinate
		 * 
		 * @return value of resource x co-ordinate
		 */
		public int getXSize();

		/**
		 * Function to get resource y co-ordinate
		 * 
		 * @return value of resource y co-ordinate
		 */
		public int getYSize();

		/**
		 * Function to get resource color depth
		 * 
		 * @return value of resource y color depth
		 */
		public int getColorDepth();

		/**
		 * Function to get resource file size
		 * 
		 * @return value of resource file size
		 */
		public int getFileSize();

		/**
		 * Function to get resource channel number
		 * 
		 * @return value of resource channel number
		 */
		public int getNumChannel();

		/**
		 * Function to get resource bit rate sample
		 * 
		 * @return value of resource bit rate sample
		 */
		public int getBitsPerSample();

		/**
		 * Function to get resource sample frequency
		 * 
		 * @return value of resource sample frequency
		 */
		public float getSampleFrequency();

		/**
		 * Function to get resource frame rate
		 * 
		 * @return value of resource frame rate
		 */
		public float getFrameRate();

		/**
		 * Function to get resource duration
		 * 
		 * @return value of resource duration
		 */
		public int getDuration();
	}
}
