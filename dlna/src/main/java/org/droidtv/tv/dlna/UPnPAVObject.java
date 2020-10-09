/*******************************************************************************
 *
 * Copyright (c) 2009 Koninklijke Philips Electronics N.V. All rights reserved.
 *
 * This source code and any compilation or derivative thereof is the proprietary
 * information of Koninklijke Philips Electronics N.V. and is confidential in
 * nature.
 *
 * Under no circumstances is this software to be combined with any Open Source
 * Software in any way or placed under an Open Source License of any type
 * without the express written permission of Koninklijke Philips Electronics
 * N.V.
 *
 * Developed and Maintained by Philips Applied Technologies Eindhoven - The
 * Netherlands
 *
 *******************************************************************************/

package org.droidtv.tv.dlna;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

/**
 * AV object data java equivalent of CPMSO_Object. the AV data is stored in this
 * object, and this object can be used to go from Java to C and from C to Java
 * domain. AV object made parsable so that it can be shared across Android
 * activities.
 *
 * @author Florent Noel
 */
public class UPnPAVObject implements Parcelable, IUPnPAVObject {

    private static final String TAG = "DLNA/UPnPAVObject";

    private static final String AUDIO = "audio";

    private static final String VIDEO = "video";

    private static final String IMAGE = "image";

    private String mObjectID = null;

    private String mRefID = null;

    private String mParentID = null;

    private int mNrItems = 0;

    private int mBrowseResult = 0;

    private int mChildCount = 0;

    // METADATA
    private int mUpdateID = -1;

    private String mTitle = null;

    private String mArtist = null;

    private String mAlbum = null;

    private String mGenre = null;

    private String mDate = null;

    private String mLongDescription = null;

    private String mCreator = null;

    private String mProducer = null;

    private String mActor = null;

    private String mDirector = null;

    private String mPublisher = null;

    private String mLanguage = null;

    private String mAlbumArtURI = null;

    private String mScheduledStartTime = null;

    private String mScheduledEndTime = null;

    private String mChannelName = null;

    private String mRating = null;

    private String mRatingType = null;

    private String mIcon = null;

    private String mUPnPClass = null;

    private String mMIMEType = null;

    private String mMETADATA = null;

    private String mDescription = null;

    private int mType = 0;

    private int mChannelNr = 0;

    private int mPlaybackCount = 0;

    private int mNrResource = 0;

    private int mNrResExtensions = 0;

    private int mIsRestricted = 0;

    private int mTotalNR = 0;

    private ArrayList<Resource> resourceList;

    private ArrayList<ResourceExtension> resourceExtensionList;

    private String browseCookie = null;

    private ArrayList<String> mChannelIdList;

    private ArrayList<String> mChannelIDTypeList;

    private ArrayList<String> mChannelIDDistriNetworkNameList;

    private ArrayList<String> mChannelIDDistriNetworkIDList;

    private String mLastPlayedPosition = null;

    public static class Component implements Parcelable, IComponent {
        private String mID = null;

        private String mClass = null;

        private String mMimeType = null;

        private String mExtendedType = null;

        // Comp. Res. Var.
        private String mResProtocolInfo = null;

        private String mResURL = null;

        public Component() {
        }

        public Component(final String compID, final String compClass, final String compMimeType,
                         final String compExtType, final String compResProtocolInfo, final String compResURL) {
            mID = compID;
            mClass = compClass;
            mMimeType = compMimeType;
            mExtendedType = compExtType;
            mResProtocolInfo = compResProtocolInfo;
            mResURL = compResURL;
        }

        public Component(final Parcel in) {
            mID = in.readString();
            mClass = in.readString();
            mMimeType = in.readString();
            mExtendedType = in.readString();

            mResProtocolInfo = in.readString();
            mResURL = in.readString();
        }

        public String getComponentID() {
            return mID;
        }

        public String getComponentClass() {
            return mClass;
        }

        public String getComponentMimeType() {
            return mMimeType;
        }

        public String getComponentExtendedType() {
            return mExtendedType;
        }

        public String getComponentResProtocolInfo() {
            return mResProtocolInfo;
        }

        public String getComponentResURL() {
            return mResURL;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeString(mID);
            dest.writeString(mClass);
            dest.writeString(mMimeType);
            dest.writeString(mExtendedType);
            dest.writeString(mResProtocolInfo);
            dest.writeString(mResURL);
        }

        public static final Creator<Component> CREATOR = new Creator<Component>() {

            @Override
            public Component createFromParcel(final Parcel source) {
                return new Component(source);
            }

            @Override
            public Component[] newArray(final int size) {
                return new Component[size];
            }
        };
    }

    public static class ComponentGroup implements Parcelable, IComponentGroup {
        private String mGroupID = null;

        private int mIsRequired = 1;

        private ArrayList<Component> mComponentList;

        public ComponentGroup() {
        }

        public ComponentGroup(final String ID, final int isRequired) {
            mGroupID = ID;
            mIsRequired = isRequired;
        }

        public ComponentGroup(final Parcel in) {
            mGroupID = in.readString();
            mIsRequired = in.readInt();

            final Parcelable[] par = in.readParcelableArray(Component.class.getClassLoader());
            if (par != null) {
                mComponentList = new ArrayList<Component>();
                for (final Parcelable item : par) {
                    mComponentList.add((Component)item);
                }
            }
        }

        public void addComponent(final IComponent comp) {
            if (comp != null) {
                if (mComponentList == null) {
                    mComponentList = new ArrayList<Component>();
                }

                mComponentList.add((Component)comp);
            }
        }

        public void addComponent(final String compID, final String compClass, final String compMimeType,
                                 final String compExtType, final String compResProtocolInfo, final String compResURL) {
            final Component comp = new Component(compID, compClass, compMimeType, compExtType, compResProtocolInfo,
                    compResURL);
            addComponent(comp);
        }

        public String getGroupID() {
            return mGroupID;
        }

        public Boolean isComponentRequired() {
            return mIsRequired == 0 ? true : false;
        }

        public ArrayList<Component> getComponentList() {
            return mComponentList;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeString(mGroupID);
            dest.writeInt(mIsRequired);

            if (mComponentList != null) {
                dest.writeParcelableArray(mComponentList.toArray(new Component[mComponentList.size()]),
                        Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
            } else {
                dest.writeParcelableArray(new Component[0], Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
            }
        }

        public static final Creator<ComponentGroup> CREATOR = new Creator<ComponentGroup>() {

            @Override
            public ComponentGroup createFromParcel(final Parcel source) {
                return new ComponentGroup(source);
            }

            @Override
            public ComponentGroup[] newArray(final int size) {
                return new ComponentGroup[size];
            }
        };
    }

    public static class ResourceExtension implements Parcelable, IResourceExtension {

        // same as the resource this ext. is linked to
        private String mResExtID = null;
        private ArrayList<ComponentGroup> mComponentGroupList;

        public ResourceExtension() {
        }

        public ResourceExtension(final String resExtID) {
            mResExtID = resExtID;
        }

        public ResourceExtension(final Parcel in) {
            mResExtID = in.readString();

            final Parcelable[] par = in.readParcelableArray(ComponentGroup.class.getClassLoader());
            if (par != null) {
                mComponentGroupList = new ArrayList<ComponentGroup>();
                for (final Parcelable item : par) {
                    mComponentGroupList.add((ComponentGroup)item);
                }
            }
        }

        public void addComponentGroup(final IComponentGroup compGroup) {

            if (compGroup != null) {
                if (mComponentGroupList == null) {
                    mComponentGroupList = new ArrayList<ComponentGroup>();
                }

                mComponentGroupList.add((ComponentGroup)compGroup);
            }
        }

        public String getResExtID() {
            return mResExtID;
        }

        public void setResExtID(final String id) {
            mResExtID = id;
        }

        public ComponentGroup getComponentGroup(final String id) {
            if ((id != null) && (mComponentGroupList != null)) {
                for (int i = 0; i < mComponentGroupList.size(); i++) {
                    if (mComponentGroupList.get(i).getGroupID().equals(id)) {
                        return mComponentGroupList.get(i);
                    }
                }
            }

            return null;
        }

        public ArrayList<ComponentGroup> getComponentGroupList() {
            return mComponentGroupList;
        }

        @Override
        public int describeContents() {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeString(mResExtID);

            if (mComponentGroupList != null) {
                dest.writeParcelableArray(mComponentGroupList.toArray(new ComponentGroup[mComponentGroupList.size()]),
                        Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
            } else {
                dest.writeParcelableArray(new ComponentGroup[0], Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
            }
        }

        public static final Creator<ResourceExtension> CREATOR = new Creator<ResourceExtension>() {

            @Override
            public ResourceExtension createFromParcel(final Parcel source) {
                return new ResourceExtension(source);
            }

            @Override
            public ResourceExtension[] newArray(final int size) {
                return new ResourceExtension[size];
            }
        };
    }

    /*
     * inner class representing a resource
     */
    public static class Resource implements Parcelable, IResource {
        private String mResID = null;

        private String mResProtocolInfo = null;

        private String mResURL = null;

        private int mResBitRate = -1;

        private float mResSampleFrequency = -1;

        private int mResXSize = -1;

        private int mResYSize = -1;

        private int mResColorDepth = -1;

        private int mResFileSize = -1;
        private int mResNumChannel = -1;

        private int mResBitsPerSample = -1;

        private float mResFrameRate = -1;

        private int mDuration = -1;

        public Resource(final String url, final String protocol) {
            mResURL = url;
            mResProtocolInfo = protocol;
        }

        public Resource() {
        }

        public Resource(final Parcel in) {
            mResID = in.readString();
            mResBitRate = in.readInt();
            mResSampleFrequency = in.readFloat();
            mResXSize = in.readInt();
            mResYSize = in.readInt();
            mResColorDepth = in.readInt();
            mResNumChannel = in.readInt();
            mResBitsPerSample = in.readInt();
            mResFrameRate = in.readFloat();
            mResProtocolInfo = in.readString();
            mResURL = in.readString();
            mDuration = in.readInt();
            mResFileSize = in.readInt();
        }

        public void setResID(final String id) {
            mResID = id;
        }

        public void setURL(final String url) {
            mResURL = url;
        }

        public void setBitrate(final int bRate) {
            mResBitRate = bRate;
        }

        public void setFilesize(final int bSize) {
            mResFileSize = bSize;
        }

        public void setProtocolInfo(final String protocol) {
            mResProtocolInfo = protocol;
        }

        public void setXSize(final int size) {
            mResXSize = size;
        }

        public void setYSize(final int size) {
            mResYSize = size;
        }

        public void setColorDepth(final int depth) {
            mResColorDepth = depth;
        }

        public void setNumChannel(final int num) {
            mResNumChannel = num;
        }

        public void setBitsPerSample(final int bPerSample) {
            mResBitsPerSample = bPerSample;
        }

        public void setSampleFrequency(final float sampleFreq) {
            mResSampleFrequency = sampleFreq;
        }

        public void setFrameRate(final float fRate) {
            mResFrameRate = fRate;
        }

        public void setDuration(final int duration) {
            mDuration = duration;
        }

        public String getResID() {
            return mResID;
        }

        public String getURL() {
            return mResURL;
        }

        public String getProtocolInfo() {
            return mResProtocolInfo;
        }

        public int getBitrate() {
            return mResBitRate;
        }

        public int getXSize() {
            return mResXSize;
        }

        public int getYSize() {
            return mResYSize;
        }

        public int getColorDepth() {
            return mResColorDepth;
        }

        public int getFileSize() {
            return mResFileSize;
        }

        public int getNumChannel() {
            return mResNumChannel;
        }

        public int getBitsPerSample() {
            return mResBitsPerSample;
        }

        public float getSampleFrequency() {
            return mResSampleFrequency;
        }

        public float getFrameRate() {
            return mResFrameRate;
        }

        public int getDuration() {
            return mDuration;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeString(mResID);
            dest.writeInt(mResBitRate);
            dest.writeFloat(mResSampleFrequency);
            dest.writeInt(mResXSize);
            dest.writeInt(mResYSize);
            dest.writeInt(mResColorDepth);
            dest.writeInt(mResNumChannel);
            dest.writeInt(mResBitsPerSample);
            dest.writeFloat(mResFrameRate);
            dest.writeString(mResProtocolInfo);
            dest.writeString(mResURL);
            dest.writeInt(mDuration);
            dest.writeInt(mResFileSize);
        }

        public static final Creator<Resource> CREATOR = new Creator<Resource>() {

            @Override
            public Resource createFromParcel(final Parcel source) {
                return new Resource(source);
            }

            @Override
            public Resource[] newArray(final int size) {
                return new Resource[size];
            }
        };
    }

    /**
     * UPnPAVObject
     */
    public UPnPAVObject() {
    }

    public UPnPAVObject(final Parcel in) {
        readFromParcel(in);
    }

    public UPnPAVObject(String URL) {
        if (URL == null) {
            URL = "";
        }

        // add a resource
        resourceList = new ArrayList<Resource>();
        resourceList.add(new Resource());

        setResourceValue(UPnPConstants.UPNP_AV_OBJECT_RESOURCE_URL, 0, URL);

        // guess form URL and add TITLE
        setStringValue(UPnPConstants.UPNP_AV_OBJECT_TITLE, getFileNameFromURL(URL));
    }

    public UPnPAVObject(final String URL, final String metadata) {
        // add a resource
        resourceList = new ArrayList<Resource>();
        resourceList.add(new Resource());

        setResourceValue(UPnPConstants.UPNP_AV_OBJECT_RESOURCE_URL, 0, URL);

        // guess form URL and add TITLE
        setStringValue(UPnPConstants.UPNP_AV_OBJECT_TITLE, getFileNameFromURL(URL));

        setStringValue(UPnPConstants.UPNP_AV_OBJECT_METADATA, metadata);
    }

    public UPnPAVObject(final String URL, final ArrayList<String> res) {
        // add URL
        // guess from URL and add TITLE
        setStringValue(UPnPConstants.UPNP_AV_OBJECT_TITLE, getFileNameFromURL(URL));
    }

    /**
     * Set a multivalue parameter. To be used with : - mChannelIdList; -
     * mChannelIDTypeList; - mChannelIDDistriNetworkNameList; -
     * mChannelIDDistriNetworkIDList;
     *
     * @param id    id of the parameter to be set
     * @param value value to be set
     */
    public void setMultiValueValue(final int id, final String value) {
        if (value == null) {
            return;
        }

        switch (id) {
            case UPnPConstants.UPNP_AV_OBJECT_CHANNEL_ID:
                if (mChannelIdList == null) {
                    mChannelIdList = new ArrayList<String>();
                }

                mChannelIdList.add(value);

                break;

            case UPnPConstants.UPNP_AV_OBJECT_CHANNEL_ID_TYPE:
                if (mChannelIDTypeList == null) {
                    mChannelIDTypeList = new ArrayList<String>();
                }

                mChannelIDTypeList.add(value);

                break;
            case UPnPConstants.UPNP_AV_OBJECT_CHANNEL_ID_DISTRIBUTION_NETWORK_NAME:
                if (mChannelIDDistriNetworkNameList == null) {
                    mChannelIDDistriNetworkNameList = new ArrayList<String>();
                }

                mChannelIDDistriNetworkNameList.add(value);

                break;
            case UPnPConstants.UPNP_AV_OBJECT_CHANNEL_ID_DISTRIBUTION_NETWORK_ID:
                if (mChannelIDDistriNetworkIDList == null) {
                    mChannelIDDistriNetworkIDList = new ArrayList<String>();
                }

                mChannelIDDistriNetworkIDList.add(value);

                break;
            default:
                Log.v(TAG, "setMultiValueValue default case");
                break;
        }
    }

    /**
     * Retrieve a multivalue parameter from its list. To be used with : -
     * mChannelIdList; - mChannelIDTypeList; - mChannelIDDistriNetworkNameList; -
     * mChannelIDDistriNetworkIDList;
     *
     * @param id    id of the parameter to retrieve
     * @param index index in the list
     * @return the value as a String
     */
    public String getMultiValueValue(final int id, final int index) {
        String value = null;

        switch (id) {
            case UPnPConstants.UPNP_AV_OBJECT_CHANNEL_ID:
                if (mChannelIdList != null) {
                    value = mChannelIdList.get(index);
                }
                break;

            case UPnPConstants.UPNP_AV_OBJECT_CHANNEL_ID_TYPE:
                if (mChannelIDTypeList != null) {
                    value = mChannelIDTypeList.get(index);
                }
                break;

            case UPnPConstants.UPNP_AV_OBJECT_CHANNEL_ID_DISTRIBUTION_NETWORK_NAME:
                if (mChannelIDDistriNetworkNameList != null) {
                    value = mChannelIDDistriNetworkNameList.get(index);
                }

                break;

            case UPnPConstants.UPNP_AV_OBJECT_CHANNEL_ID_DISTRIBUTION_NETWORK_ID:
                if (mChannelIDDistriNetworkIDList != null) {
                    value = mChannelIDDistriNetworkIDList.get(index);
                }
                break;
            default:
                Log.v(TAG, "getMultiValueValue default case");
                break;
        }

        return value;
    }

    /**
     * setResourceValue sets the value of an resource
     *
     * @param id     content parameter id
     * @param resNum the resource identifier [0,Max-1]
     * @param value  the value of the resource parameter
     */
    public void setResourceValue(final int id, final int resNum, final String value) {
        if (value != null) {

            if (resourceList == null) {
                resourceList = new ArrayList<Resource>();

                for (int i = 0; i < mNrResource; i++) {
                    resourceList.add(new Resource());
                }
            }

            switch (id) {
                case UPnPConstants.UPNP_AV_OBJECT_RESOURCE_ID:
                    resourceList.get(resNum).setResID(value);
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_RESOURCE_URL:
                    resourceList.get(resNum).setURL(value);
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_RESOURCE_PROTOCOL:
                    resourceList.get(resNum).setProtocolInfo(value);
                    break;
                default:
                    Log.v(TAG, "setResourceValue default case");
            }
        }
    }

    public String getResourceExntID(final int resNum) {
        final String value = null;
        if (resourceExtensionList != null) {
            return resourceExtensionList.get(resNum).getResExtID();
        }
        return value;
    }

    public int getNrResourceExntComponentGroup(final String resExtnID) {
        final int value = 0;
        final ResourceExtension ext = getResourceExt(resExtnID);
        if (ext != null) {
            return ext.getComponentGroupList().size();
        }
        return value;
    }

    public int getNrResourceExntComponent(final String resExtnID, final int groupNum) {
        int value = 0;
        Log.i("UPNP", "getNrResourceExntComponent:resExtnID:  " + resExtnID + ",  groupNum: " + groupNum);
        final ResourceExtension ext = getResourceExt(resExtnID);
        if (ext != null) {
            final ArrayList<ComponentGroup> groupList = ext.getComponentGroupList();
            if ((groupList != null) && (groupList.size() > groupNum)) {
                final ComponentGroup group = groupList.get(groupNum);
                if (group != null) {
                    value = group.getComponentList().size();
                } else {
                    Log.e("UPNP", "getNrResourceExntComponent: ComponentGroup is null\n");
                }
            } else {
                Log.e("UPNP", "getNrResourceExntComponent: ComponentGroup list is doesnt have the group no\n");
            }

        }
        return value;
    }

    public String getResourceExtnInfo(final String resExtnID, final int id, final int groupNum,
                                      final int componentNum) {
        String value = null;
        final ResourceExtension ext = getResourceExt(resExtnID);
        if (ext != null) {
            switch (id) {
                case UPnPConstants.UPNP_AV_OBJECT_COMPONENTGROUP_ID:
                    value = ext.getComponentGroupList().get(groupNum).getGroupID();
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_COMPONENT_ID:
                    value = ext.getComponentGroupList().get(groupNum).getComponentList().get(componentNum).getComponentID();
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_COMPONENT_CLASS:
                    value = ext.getComponentGroupList().get(groupNum).getComponentList().get(componentNum)
                            .getComponentClass();
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_COMPONENT_MIMETYPE:
                    value = ext.getComponentGroupList().get(groupNum).getComponentList().get(componentNum)
                            .getComponentMimeType();
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_COMPONENT_EXTTYPE:
                    value = ext.getComponentGroupList().get(groupNum).getComponentList().get(componentNum)
                            .getComponentExtendedType();
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_COMPONENT_PROTOCOLINFO:
                    value = ext.getComponentGroupList().get(groupNum).getComponentList().get(componentNum)
                            .getComponentResProtocolInfo();
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_COMPONENT_URL:
                    value = ext.getComponentGroupList().get(groupNum).getComponentList().get(componentNum)
                            .getComponentResURL();
                    break;
                default:
                    break;
            }
        }
        return value;
    }

    /**
     * setResourceValue sets the value of an resource
     *
     * @param id     content parameter id
     * @param resNum the resource identifier [0,Max-1]
     * @param value  the value of the resource parameter
     */
    public void setResourceValue(final int id, final int resNum, final int value) {

        if (resourceList == null) {
            resourceList = new ArrayList<Resource>();

            for (int i = 0; i < mNrResource; i++) {
                resourceList.add(new Resource());
            }
        }

        switch (id) {
            case UPnPConstants.UPNP_AV_OBJECT_RESOURCE_BITRATE:
                resourceList.get(resNum).setBitrate(value);
                break;
            case UPnPConstants.UPNP_AV_OBJECT_RESOURCE_X_SIZE:
                resourceList.get(resNum).setXSize(value);
                break;
            case UPnPConstants.UPNP_AV_OBJECT_RESOURCE_Y_SIZE:
                resourceList.get(resNum).setYSize(value);
                break;
            case UPnPConstants.UPNP_AV_OBJECT_RESOURCE_COLOR_DEPTH:
                resourceList.get(resNum).setColorDepth(value);
                break;
            case UPnPConstants.UPNP_AV_OBJECT_RESOURCE_NUM_CHANNEL:
                resourceList.get(resNum).setNumChannel(value);
                break;
            case UPnPConstants.UPNP_AV_OBJECT_RESOURCE_BITS_PER_SAMPLE:
                resourceList.get(resNum).setBitsPerSample(value);
                break;
            case UPnPConstants.UPNP_AV_OBJECT_RESOURCE_DURATION:
                resourceList.get(resNum).setDuration(value);
                break;
            case UPnPConstants.UPNP_AV_OBJECT_RESOURCE_FILESIZE:
                resourceList.get(resNum).setFilesize(value);
                break;
            default:
                Log.v(TAG, "setResourceValue default case ");
                break;
        }
    }

    /**
     * setResourceValue sets the value of an resource
     *
     * @param id     content parameter id
     * @param resNum the resource identifier [0,Max-1]
     * @param value  the value of the resource parameter
     */
    public void setResourceValue(final int id, final int resNum, final float value) {

        if (resourceList == null) {
            resourceList = new ArrayList<Resource>();

            for (int i = 0; i < mNrResource; i++) {
                resourceList.add(new Resource());
            }
        }

        switch (id) {
            case UPnPConstants.UPNP_AV_OBJECT_RESOURCE_SAMPLE_FREQUENCY:
                resourceList.get(resNum).setSampleFrequency(value);
                break;

            case UPnPConstants.UPNP_AV_OBJECT_RESOURCE_FRAMERATE:
                resourceList.get(resNum).setFrameRate(value);
                break;
            default:
                Log.v(TAG, "setResourceValue default case ");
                break;
        }
    }

    public void addResourceExt(final String resExtID) {
        if (resourceExtensionList == null) {
            resourceExtensionList = new ArrayList<ResourceExtension>();
        }

        if (getResourceExt(resExtID) == null) {
            resourceExtensionList.add(new ResourceExtension(resExtID));
        }
    }

    public ResourceExtension getResourceExt(final String id) {
        if ((id != null) && (resourceExtensionList != null)) {
            for (int i = 0; i < resourceExtensionList.size(); i++) {
                if (resourceExtensionList.get(i).getResExtID().equals(id)) {
                    return resourceExtensionList.get(i);
                }
            }
        }

        return null;
    }

    public ComponentGroup getResourceExtComponentGroup(final String resExtID, final String compGroupID) {
        final ResourceExtension ext = getResourceExt(resExtID);

        if ((ext != null) && (compGroupID != null)) {
            return ext.getComponentGroup(compGroupID);
        }

        return null;
    }

    public void addResourceExtComponentGroup(final String resExtID, final String compGroupID,
                                             final int isCompGroupReq) {
        if ((resExtID != null) && (compGroupID != null)) {
            final ResourceExtension ext = getResourceExt(resExtID);

            if (ext != null) {
                ext.addComponentGroup(new ComponentGroup(compGroupID, isCompGroupReq));
            }
        }
    }

    public void addResourceExtComponent(final String resExtID, final String compGroupID, final String compID,
                                        final String compClass, final String compMimeType, final String compExtType,
                                        final String compResProtocolInfo, final String compResURL) {
        final ComponentGroup group = getResourceExtComponentGroup(resExtID, compGroupID);

        if (group != null) {
            group.addComponent(compID, compClass, compMimeType, compExtType, compResProtocolInfo, compResURL);
        }
    }

    public ArrayList<Component> getComponents(final String compClassName) {
        ArrayList<Component> resultList = null;
        if ((resourceExtensionList != null) && (compClassName != null)) {
            for (int i = 0; i < resourceExtensionList.size(); i++) {
                final ResourceExtension res = resourceExtensionList.get(i);

                if (res.getComponentGroupList() != null) {
                    final ArrayList<ComponentGroup> list = res.getComponentGroupList();

                    for (int y = 0; y < list.size(); y++) {
                        final ComponentGroup compGroup = list.get(y);

                        if (compGroup.getComponentList() != null) {
                            final ArrayList<Component> listComp = compGroup.getComponentList();

                            for (int z = 0; z < listComp.size(); z++) {
                                final Component comp = listComp.get(z);
                                final String componentClass = comp.getComponentClass();

                                if ((componentClass != null) && componentClass.equals(compClassName)) {
                                    if (resultList == null) {
                                        resultList = new ArrayList<Component>();
                                    }
                                    resultList.add(comp);
                                }
                            }
                        }
                    }
                }
            }
        }

        return resultList;
    }

    /**
     * getResourceValue sets the value of an resource
     *
     * @param id     content parameter id
     * @param resNum the resource identifier [0,Max-1]
     * @return the value of the resource parameter
     */
    public String getResourceValue(final int id, final int resNum) {
        String value = null;

        if ((resourceList != null) && (resourceList.size() > 0)) {
            switch (id) {
                case UPnPConstants.UPNP_AV_OBJECT_RESOURCE_URL:
                    if (resourceList.get(resNum) != null) {
                        value = resourceList.get(resNum).getURL();
                    }
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_RESOURCE_PROTOCOL:
                    if (resourceList.get(resNum) != null) {
                        value = resourceList.get(resNum).getProtocolInfo();
                    }
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_RESOURCE_ID:
                    if (resourceList.get(resNum) != null) {
                        value = resourceList.get(resNum).getResID();
                    }
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_RESOURCE_RESOLUTION:
                    if (resourceList.get(resNum) != null) {
                        value = "" + resourceList.get(resNum).getXSize() + "x" + resourceList.get(resNum).getYSize();
                    }
                    break;
                default:
                    Log.v(TAG, "getResourceValue default case");
                    break;
            }
        }
        return value;
    }

    public float getResourceValuefloat(final int id, final int resNum) {
        float value = (float)0;

        if ((resourceList != null) && (resourceList.size() > 0)) {
            switch (id) {
                case UPnPConstants.UPNP_AV_OBJECT_RESOURCE_SAMPLE_FREQUENCY:
                    if (resourceList.get(resNum) != null) {
                        value = resourceList.get(resNum).getSampleFrequency();
                    }
                    break;
                default:
                    Log.v(TAG, "getResourceValuefloat default case");
                    break;

            }
        }
        return value;
    }

    public int getResourceValueInt(final int id, final int resNum) {
        int value = -1;

        if ((resourceList != null) && (resourceList.size() > 0)) {
            switch (id) {
                case UPnPConstants.UPNP_AV_OBJECT_RESOURCE_DURATION:
                    if (resourceList.get(resNum) != null) {
                        value = resourceList.get(resNum).getDuration();
                    }
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_RESOURCE_X_SIZE:
                    if (resourceList.get(resNum) != null) {
                        value = resourceList.get(resNum).getXSize();
                    }
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_RESOURCE_Y_SIZE:
                    if (resourceList.get(resNum) != null) {
                        value = resourceList.get(resNum).getYSize();
                    }
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_RESOURCE_BITRATE:
                    if (resourceList.get(resNum) != null) {
                        value = resourceList.get(resNum).getBitrate();
                    }
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_RESOURCE_BITS_PER_SAMPLE:
                    value = resourceList.get(resNum).getBitsPerSample();
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_RESOURCE_COLOR_DEPTH:
                    value = resourceList.get(resNum).getColorDepth();
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_RESOURCE_FILESIZE:
                    value = resourceList.get(resNum).getFileSize();
                    break;

                case UPnPConstants.UPNP_AV_OBJECT_RESOURCE_NUM_CHANNEL:
                    value = resourceList.get(resNum).getNumChannel();
                    break;
                default:
                    Log.v(TAG, "getResourceValueInt default case");
                    break;
            }
        }
        return value;
    }

    /**
     * setByteArrayToStringValue Sets a string parameter from Byte array in the AV
     * object
     *
     * @param id    content parameter id
     * @param value the value of the parameter
     */
    public void setByteArrayToStringValue(final int id, final byte[] value) {
        switch (id) {
            case UPnPConstants.UPNP_AV_OBJECT_TITLE:
                mTitle = new String(value);
                break;
            case UPnPConstants.UPNP_AV_OBJECT_METADATA:
                mMETADATA = new String(value);
                break;
            case UPnPConstants.UPNP_AV_OBJECT_DESCRIPTION:
                mDescription = new String(value);
                break;
            default:
                Log.v(TAG, "setByteArrayToStringValue default case");
                break;
        }
    }

    /**
     * setStringValue Sets a string parameter in the AV object
     *
     * @param id    content parameter id
     * @param value the value of the parameter
     */
    public void setStringValue(final int id, final String value) {
        if ((value != null) && !value.contentEquals("")) {
            switch (id) {
                case UPnPConstants.UPNP_AV_OBJECT_TITLE:
                    mTitle = value;
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_ARTIST:
                    mArtist = value;
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_ALBUM:
                    mAlbum = value;
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_GENRE:
                    mGenre = value;
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_DATE:
                    mDate = value;
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_UPNPCLASS:
                    mUPnPClass = value;
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_METADATA:
                    mMETADATA = value;
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_DESCRIPTION:
                    mDescription = value;
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_OBJECT_ID:
                    mObjectID = value;
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_PARENT_ID:
                    mParentID = value;
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_REF_ID:
                    mRefID = value;
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_MIME_TYPE:
                    mMIMEType = value;
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_LONG_DESCRIPTION:
                    mLongDescription = value;
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_CREATOR:
                    mCreator = value;
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_PRODUCER:
                    mProducer = value;
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_ACTOR:
                    mActor = value;
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_DIRECTOR:
                    mDirector = value;
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_PUBLISHER:
                    mPublisher = value;
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_LANGUAGE:
                    mLanguage = value;
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_ALBUM_ART_URI:
                    mAlbumArtURI = value;
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_SCHEDULE_START_TIME:
                    mScheduledStartTime = value;
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_SCHEDULE_END_TIME:
                    mScheduledEndTime = value;
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_CHANNEL_NAME:
                    mChannelName = value;
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_RATING:
                    mRating = value;
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_RATING_TYPE:
                    mRatingType = value;
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_ICON:
                    mIcon = value;
                    break;
                case UPnPConstants.UPNP_AV_OBJECT_LASTPLAYBACK_POSITION:
                    mLastPlayedPosition = value;
                    Log.v(TAG, "set string value UPNPAVOBEJCT: " + mLastPlayedPosition);
                    break;
                default:
                    Log.v(TAG, "setStringValue default case");
                    break;
            }
        }
    }

    /**
     * setIntegerValue Sets a integer parameter in the AV object
     *
     * @param id    content parameter id
     * @param value the value of the parameter
     */
    public void setIntegerValue(final int id, final int value) {
        switch (id) {
            case UPnPConstants.UPNP_AV_OBJECT_TYPE:
                mType = value;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_NR_RESOURCE:
                mNrResource = value;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_NR_RESOURCE_EXT:
                mNrResExtensions = value;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_NR_CONTAINER_CHILDREN:
                mNrItems = value;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_BROWSE_RESULT:
                mBrowseResult = value;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_CHILD_COUNT:
                mChildCount = value;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_CHANNEL_NR:
                mChannelNr = value;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_PLAYBACK_COUNT:
                mPlaybackCount = value;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_UPDATE_ID:
                mUpdateID = value;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_IS_RESTRICTED:
                mIsRestricted = value;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_TOTAL_NR:
                mTotalNR = value;
                break;
            default:
                Log.v(TAG, "setIntegerValue default case");
                break;
        }
    }

    public void setIntegerValue() {
        Log.e("the test JNI", "test test test");
    }

    /**
     * getIntegerValue retrieves a integer parameter in the AV object
     *
     * @param id content parameter id
     * @return the value of the parameter
     */
    public int getIntegerValue(final int id) {
        int value = 0;

        switch (id) {
            case UPnPConstants.UPNP_AV_OBJECT_TYPE:
                value = mType;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_NR_RESOURCE:
                value = mNrResource;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_NR_RESOURCE_EXT:
                value = mNrResExtensions;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_NR_CONTAINER_CHILDREN:
                value = mNrItems;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_BROWSE_RESULT:
                value = mBrowseResult;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_CHILD_COUNT:
                value = mChildCount;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_CHANNEL_NR:
                value = mChannelNr;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_PLAYBACK_COUNT:
                value = mPlaybackCount;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_UPDATE_ID:
                value = mUpdateID;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_IS_RESTRICTED:
                value = mIsRestricted;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_TOTAL_NR:
                value = mTotalNR;
                break;
            default:
                value = 0;
                break;
        }

        return value;
    }

    /**
     * getStringValue retrieves a string parameter in the AV object
     *
     * @param id content parameter id
     * @return the value of the parameter
     */
    public String getStringValue(final int id) {
        String value = null;

        switch (id) {
            case UPnPConstants.UPNP_AV_OBJECT_TITLE:
                value = mTitle;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_ARTIST:
                value = mArtist;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_ALBUM:
                value = mAlbum;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_GENRE:
                value = mGenre;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_DATE:
                value = mDate;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_UPNPCLASS:
                value = mUPnPClass;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_METADATA:
                value = mMETADATA;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_DESCRIPTION:
                value = mDescription;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_OBJECT_ID:
                value = mObjectID;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_PARENT_ID:
                value = mParentID;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_REF_ID:
                value = mRefID;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_MIME_TYPE:
                value = mMIMEType;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_LONG_DESCRIPTION:
                value = mLongDescription;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_CREATOR:
                value = mCreator;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_PRODUCER:
                value = mProducer;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_ACTOR:
                value = mActor;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_DIRECTOR:
                value = mDirector;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_PUBLISHER:
                value = mPublisher;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_LANGUAGE:
                value = mLanguage;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_ALBUM_ART_URI:
                value = mAlbumArtURI;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_SCHEDULE_START_TIME:
                value = mScheduledStartTime;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_SCHEDULE_END_TIME:
                value = mScheduledEndTime;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_CHANNEL_NAME:
                value = mChannelName;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_RATING:
                value = mRating;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_RATING_TYPE:
                value = mRatingType;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_ICON:
                value = mIcon;
                break;
            case UPnPConstants.UPNP_AV_OBJECT_LASTPLAYBACK_POSITION:
                Log.v(TAG, "get string value UPNPAVOBEJCT: " + mLastPlayedPosition);
                value = mLastPlayedPosition;
                break;
            default:
                value = "";
                break;
        }
        return value;
    }

    public String getThumbnailURL() {
        String thumbnailURL = null;
        if (mUPnPClass != null) {
            if (mUPnPClass.contains(AUDIO) || mUPnPClass.contains(VIDEO)) {
                thumbnailURL = getStringValue(UPnPConstants.UPNP_AV_OBJECT_ALBUM_ART_URI);
            } else if (mUPnPClass.contains(IMAGE)) {
                final ArrayList<Resource> res = getRessourcesList();
                if ((res != null) && !res.isEmpty()) {
                    for (final Resource resource : res) {
                        final String protocolInfo = resource.getProtocolInfo();

                        final int begin = protocolInfo.indexOf("DLNA.ORG_PN=") + 12;
                        if (begin != 11) {
                            if (protocolInfo.length() >= (begin + 7)) {
                                final String imagetype = protocolInfo.substring(begin, begin + 7);
                                if (imagetype.contains("JPEG_TN")) {

                                    thumbnailURL = resource.getURL();
                                } else if (imagetype.contains("JPEG_SM")) {
                                    thumbnailURL = resource.getURL();
                                }
                            }

                        }
                    }
                }
            }

        }

        return thumbnailURL;
    }

    public String getBestResource(final String protocolInfo) {
        String upnpClass = getStringValue(UPnPConstants.UPNP_AV_OBJECT_UPNPCLASS);

        if (upnpClass == null || null == protocolInfo) {
            return null;
        }

        if (upnpClass.contains(AUDIO)) {
            upnpClass = AUDIO;
        } else if (upnpClass.contains(VIDEO)) {
            upnpClass = VIDEO;
        } else if (upnpClass.contains(IMAGE)) {
            upnpClass = IMAGE;
        }

        ArrayList<Resource> resourceList = getRessourcesList();
        if (null == resourceList) {
            return null;
        }

        for (final Resource resource : resourceList) {
            final String mimetype = getMIMEtypeFromProtocolInfo(resource.getProtocolInfo());
            if (null == mimetype) {
                continue;
            }

            if (!mimetype.contains(upnpClass)) {
                continue;
            }

            if (upnpClass.equals(VIDEO)) {
                String ProtocolInfoVideo = resource.getProtocolInfo();
                if (null != ProtocolInfoVideo) {
                    final int orgPnPosition = ProtocolInfoVideo.indexOf("DLNA_ORG_PN");
                    if (orgPnPosition != -1) {
                        final int firstQuote = ProtocolInfoVideo.indexOf("=", orgPnPosition);
                        final int secondQuote = ProtocolInfoVideo.indexOf(";", firstQuote + 1);
                        final String orgPN = ProtocolInfoVideo.substring(firstQuote, secondQuote);
                        if ((null != orgPN) && (protocolInfo.contains(orgPN))) {
                            return resource.getURL();
                        }
                        // TODO Take the highest resolution
                    }
                }
            } else if (upnpClass.equals(AUDIO)) {
                String ProtocolInfoAudio = resource.getProtocolInfo();
                if (null != ProtocolInfoAudio) {
                    final int orgPnPosition = ProtocolInfoAudio.indexOf("DLNA_ORG_PN");
                    if (orgPnPosition != -1) {
                        final int firstQuote = ProtocolInfoAudio.indexOf("=", orgPnPosition);
                        final int secondQuote = ProtocolInfoAudio.indexOf(";", firstQuote + 1);
                        final String orgPN = ProtocolInfoAudio.substring(firstQuote, secondQuote);
                        if ((null != orgPN) && (protocolInfo.contains(orgPN))) {
                            return resource.getURL();
                        }
                    }
                }

            } else if (upnpClass.equals(IMAGE)) {
                String ProtocolInfoIMAGE = resource.getProtocolInfo();
                if (null != ProtocolInfoIMAGE) {
                    final int orgPnPosition = ProtocolInfoIMAGE.indexOf("DLNA_ORG_PN");
                    if (orgPnPosition != -1) {
                        final int beginString = ProtocolInfoIMAGE.indexOf("=", orgPnPosition);
                        final int endString = ProtocolInfoIMAGE.indexOf(";", beginString + 1);
                        final String orgPN = ProtocolInfoIMAGE.substring(beginString, endString);
                        if ((null != orgPN) && (protocolInfo.contains(orgPN))) {
                            return resource.getURL();
                        }
                        // TODO take JPEG_LG (LARGE) then JPEG_MED then JPEG_SM
                        // If not available, resolution should be checked.
                    }
                }

            }
        }

        resourceList = getRessourcesList();
        if (null == resourceList) {
            return null;
        }
        for (final Resource resource : resourceList) {
            final String mimetype = getMIMEtypeFromProtocolInfo(resource.getProtocolInfo());
            if (null == mimetype) {
                continue;
            }
            if (!mimetype.contains(upnpClass)) {
                continue;
            }

            if (protocolInfo.contains(mimetype)) {
                return resource.getURL();
            }
        }

        return null;
    }

    public static String getMIMEtypeFromProtocolInfo(final String protocol) {

        if (null == protocol) {
            return "";
        }

        String temp = protocol;
        String mediaFormat = null;

        // get the index of the first comma
        int columnPos = temp.indexOf(':');

        if (columnPos != -1) {
            // cut the first arg
            temp = temp.substring(columnPos + 1, temp.length());
            columnPos = temp.indexOf(':');

            // cut the second one
            temp = temp.substring(columnPos + 1, temp.length());
            columnPos = temp.indexOf(':');

            if (columnPos != -1) {
                mediaFormat = temp.substring(0, columnPos);
            } else {
                // this DLNA flag is not implemented
                mediaFormat = "";
            }
        }

        return mediaFormat;
    }

    /**
     * getRessourcesList retrieves all the resources as an array
     *
     * @return the resources of the AV object
     */
    @Override
    public ArrayList<Resource> getRessourcesList() {
        return resourceList;
    }

    /**
     * Method to get Resource extension list
     *
     * @return list of ResourceExtension
     */
    public ArrayList<ResourceExtension> getResourceExtensionsList() {
        return resourceExtensionList;
    }

    /**
     * sets the number of items in the container
     *
     * @param value the amount of items in a container
     */
    public void setNrItems(final int value) {
        mNrItems = value;
    }

    /**
     * sets the error value returned from browse
     *
     * @param value from browse
     */
    public void setBrowseError(final int value) {
        mBrowseResult = value;
    }

    @Override
    public String toString() {
        return mTitle;
    }

    /*
     * Extract a file name form a URL for display purposes only!
     *
     * @param url the url
     *
     * @return the filename of the url... e.g. last part of the URL
     */
    private String getFileNameFromURL(final String url) {
        if (url != null) {
            final int pos = url.lastIndexOf('/');

            if (pos == -1) {
                return "";
            }

            return url.substring(pos + 1, url.length());
        }

        return "";
    }

    // ----------------------------------------------
    // all overloaded function of the parcelable object
    // ----------------------------------------------

    /*
     * implements the describeContents function Describe the kinds of special
     * objects contained in this Parcelable's marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled by
     * the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(mActor);
        dest.writeString(mAlbum);
        dest.writeString(mAlbumArtURI);
        dest.writeString(mArtist);
        dest.writeString(mChannelName);
        dest.writeString(mCreator);
        dest.writeString(mDate);
        dest.writeString(mDescription);
        dest.writeString(mDirector);
        dest.writeString(mGenre);
        dest.writeString(mIcon);
        dest.writeString(mLanguage);
        dest.writeString(mLongDescription);
        dest.writeString(mMETADATA);
        dest.writeString(mMIMEType);
        dest.writeString(mObjectID);
        dest.writeString(mParentID);
        dest.writeString(mProducer);
        dest.writeString(mPublisher);
        dest.writeString(mRating);
        dest.writeString(mRatingType);
        dest.writeString(mScheduledEndTime);
        dest.writeString(mScheduledStartTime);
        dest.writeString(mTitle);
        dest.writeString(mUPnPClass);
        dest.writeInt(mChannelNr);
        dest.writeInt(mNrResource);
        dest.writeInt(mNrResExtensions);
        dest.writeInt(mNrItems);
        dest.writeInt(mBrowseResult);
        dest.writeInt(mChildCount);
        dest.writeInt(mPlaybackCount);
        dest.writeInt(mType);
        dest.writeInt(mUpdateID);

        if (resourceList != null) {
            dest.writeParcelableArray(resourceList.toArray(new Resource[resourceList.size()]),
                    Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
        } else {
            dest.writeParcelableArray(new Resource[0], Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
        }

        if (resourceExtensionList != null) {
            dest.writeParcelableArray(
                    resourceExtensionList.toArray(new ResourceExtension[resourceExtensionList.size()]),
                    Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
        } else {
            dest.writeParcelableArray(new ResourceExtension[0], Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
        }

        dest.writeList(mChannelIdList);
        dest.writeList(mChannelIDTypeList);
        dest.writeList(mChannelIDDistriNetworkNameList);
        dest.writeList(mChannelIDDistriNetworkIDList);
    }

    public void readFromParcel(final Parcel in) {

        mActor = in.readString();
        mAlbum = in.readString();
        mAlbumArtURI = in.readString();
        mArtist = in.readString();
        mChannelName = in.readString();
        mCreator = in.readString();
        mDate = in.readString();
        mDescription = in.readString();
        mDirector = in.readString();
        mGenre = in.readString();
        mIcon = in.readString();
        mLanguage = in.readString();
        mLongDescription = in.readString();
        mMETADATA = in.readString();
        mMIMEType = in.readString();
        mObjectID = in.readString();
        mParentID = in.readString();
        mProducer = in.readString();
        mPublisher = in.readString();
        mRating = in.readString();
        mRatingType = in.readString();
        mScheduledEndTime = in.readString();
        mScheduledStartTime = in.readString();
        mTitle = in.readString();
        mUPnPClass = in.readString();

        mChannelNr = in.readInt();
        mNrResource = in.readInt();
        mNrResExtensions = in.readInt();
        mNrItems = in.readInt();
        mBrowseResult = in.readInt();
        mChildCount = in.readInt();
        mPlaybackCount = in.readInt();
        mType = in.readInt();
        mUpdateID = in.readInt();

        final Parcelable[] par = in.readParcelableArray(Resource.class.getClassLoader());
        if (par != null) {
            resourceList = new ArrayList<Resource>();
            for (final Parcelable item : par) {
                resourceList.add((Resource)item);
            }
        }

        final Parcelable[] parResExt = in.readParcelableArray(ResourceExtension.class.getClassLoader());
        if (parResExt != null) {
            resourceExtensionList = new ArrayList<ResourceExtension>();
            for (final Parcelable item : parResExt) {
                resourceExtensionList.add((ResourceExtension)item);
            }
        }

        in.readList(mChannelIdList, String.class.getClassLoader());
        in.readList(mChannelIDTypeList, String.class.getClassLoader());
        in.readList(mChannelIDDistriNetworkNameList, String.class.getClassLoader());
        in.readList(mChannelIDDistriNetworkIDList, String.class.getClassLoader());
    }

    public static final Creator<UPnPAVObject> CREATOR = new Creator<UPnPAVObject>() {

        @Override
        public UPnPAVObject createFromParcel(final Parcel source) {
            return new UPnPAVObject(source);
        }

        @Override
        public UPnPAVObject[] newArray(final int size) {
            return new UPnPAVObject[size];
        }
    };

    public String getPreviewURL() {
        final String thumbnailURL = null;

        for (final Resource resource : getRessourcesList()) {
            final String protocolInfo = resource.getProtocolInfo();

            final int begin = protocolInfo.indexOf("DLNA.ORG_PN=") + 12;

            if (begin != 11) {
                final String imagetype = protocolInfo.substring(begin, begin + 7);
                if (imagetype.contains("JPEG_ME")) {
                    return resource.getURL();
                } else if (imagetype.contains("JPEG_TN")) {
                    return resource.getURL();
                } else if (imagetype.contains("JPEG_SM")) {
                    return resource.getURL();
                }
            }
        }
        for (final Resource resource : getRessourcesList()) {
            return resource.getURL();
        }

        return thumbnailURL;
    }

    public void setCookie(String cookie) {
        browseCookie = cookie;
    }

    public String getCookie() {
        return browseCookie;
    }

    public static String[] parseProtocolInfo(String protocolInfo) {

        String pInfo[] = null;
        if (protocolInfo != null) {
            pInfo = protocolInfo.split(":");
        }
        return pInfo;

    }

    public static String findDlnaInfo(String p4, String flag) {

        if (p4 == null || flag == null || p4.equals("*") || !p4.contains(flag)) {
            return null;
        }

        String[] res = p4.split(";");
        if (res.length <= 1) {
            String result[] = p4.split("=");
            return result[1];
        }
        for (int i = 0; i < res.length; i++) {
            if (res[i].contains(flag)) {
                String result[] = res[i].split("=");
                return result[1];
            }
        }
        return null;
    }

    /*
     * Returns the list of all subtitle. It includes ComponentId, Mime-Type and URL
     * for each subtitle Pattern of String is:
     * <CompId>,<Mime-Type>,<URL>;<CompId>,<Mime-Type>,<URL>;<CompId>,<Mime-Type>,<
     * URL> ...
     */
    public String getSubtitleList() {

        Log.v(TAG, "Subtitle: getSubtitleList, Retreiving Subtitle list for:"
                + getStringValue(UPnPConstants.UPNP_AV_OBJECT_TITLE));
        String result = "";
        int no = 0;
        int i = 0;
        // Get the Components for Subtitle
        ArrayList<Component> components = getComponents("Subtitle");
        if (components == null) {
            Log.v(TAG, "No Components present for Subtitle");
            return "";
        }

        for (i = 0; i < components.size(); i++) {
            String compId = components.get(i).getComponentID();
            if (compId != null) {
                // Check if component if matches the TV supported extensions
                if (compId.endsWith(".srt") || compId.endsWith(".sub") || compId.endsWith(".smi")
                        || compId.endsWith(".sami")) {
                    if (no > 0) {
                        // If it is not the first item, add a ',' before appending to the result
                        result += ";";
                    }
                    result += components.get(i).getComponentResURL() + "?componentId=\""
                            + components.get(i).getComponentID() + "\"&MimeType=\""
                            + components.get(i).getComponentMimeType() + "\"";
                    no++;
                } else {
                    // ComponentId is null, so check for Mime-Types supported by the TV
                    String compMimeType = components.get(i).getComponentMimeType();
                    if (compMimeType == null) {
                        Log.e(TAG, "Subtitle: Component Mime-Type is NULL");
                        continue;
                    }
                    if (compMimeType.equals("text/plain") || compMimeType.equals("text/x-microdvd")
                            || compMimeType.equals("application/x-subrip")
                            || compMimeType.equals("application/x-sami")) {
                        if (no > 0) {
                            // If it is not the first item, add a ',' before appending to the result
                            result += ";";
                        }
                        result += components.get(i).getComponentResURL() + "?componentId=\""
                                + components.get(i).getComponentID() + "\"&MimeType=\""
                                + components.get(i).getComponentMimeType() + "\"";
                        no++;
                    } else {
                        Log.e(TAG, "Subtitle: Mime-Type doesn't match with TV Supported list");
                    }
                }
            } else {
                Log.e(TAG, "Subtitle: ComponentId is NULL");
            }
        }
        Log.i(TAG, "Subtitle:getSubtitleList, No of Components:" + components.size() + "Total no of subtitles:" + no
                + " SubtileList:" + result);
        return result;
    }
}
