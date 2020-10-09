package org.droidtv.tv.dlna;

public final class UPnPConstants {

	private UPnPConstants() {

	}

	// !< UPnP property dc:title
	public static final int UPNP_AV_OBJECT_TITLE = 0;

	// !< UPnP property upnp:artist
	public static final int UPNP_AV_OBJECT_ARTIST = 1;

	// !< UPnP property upnp:album
	public static final int UPNP_AV_OBJECT_ALBUM = 2;

	// !< UPnP property upnp:genre
	public static final int UPNP_AV_OBJECT_GENRE = 3;

	// !< UPnP property dc:date
	public static final int UPNP_AV_OBJECT_DATE = 4;

	// !< UPnP property upnp:class
	public static final int UPNP_AV_OBJECT_UPNPCLASS = 5;

	// !< abstracted meta data
	public static final int UPNP_AV_OBJECT_METADATA = 6;

	// !< UPnP tag: id
	public static final int UPNP_AV_OBJECT_OBJECT_ID = 7;

	// !< UPnP tag: ParentID
	public static final int UPNP_AV_OBJECT_PARENT_ID = 8;

	// !< CPMSO object type
	public static final int UPNP_AV_OBJECT_TYPE = 9;

	// !< full content resource
	public static final int UPNP_AV_OBJECT_RESOURCES = 10;

	// !< MIME type (3th field of protocolInfo)
	public static final int UPNP_AV_OBJECT_MIME_TYPE = 11;

	// !< amount of resources
	public static final int UPNP_AV_OBJECT_NR_RESOURCE = 12;

	// !< amount of children
	public static final int UPNP_AV_OBJECT_NR_CONTAINER_CHILDREN = 13;

	// !< UPnP property dc:description
	public static final int UPNP_AV_OBJECT_DESCRIPTION = 14;

	// dc:longDescription
	public static final int UPNP_AV_OBJECT_LONG_DESCRIPTION = 15;

	// !< UPnP creator
	public static final int UPNP_AV_OBJECT_CREATOR = 16;

	// !< UPnP producer
	public static final int UPNP_AV_OBJECT_PRODUCER = 17;

	// !< UPnP actor
	public static final int UPNP_AV_OBJECT_ACTOR = 18;

	// !< UPnP director
	public static final int UPNP_AV_OBJECT_DIRECTOR = 19;

	// !< UPnP publisher
	public static final int UPNP_AV_OBJECT_PUBLISHER = 20;

	// !< dc:language
	public static final int UPNP_AV_OBJECT_LANGUAGE = 21;

	// upnp:albumArtURI
	public static final int UPNP_AV_OBJECT_ALBUM_ART_URI = 22;

	// upnp:scheduledStartTime
	public static final int UPNP_AV_OBJECT_SCHEDULE_START_TIME = 23;

	// upnp:scheduledEndTime
	public static final int UPNP_AV_OBJECT_SCHEDULE_END_TIME = 24;

	// upnp:channelName
	public static final int UPNP_AV_OBJECT_CHANNEL_NAME = 25;

	// !< upnp:channelID
	public static final int UPNP_AV_OBJECT_CHANNEL_ID = 26;

	// upnp:channelID@type
	public static final int UPNP_AV_OBJECT_CHANNEL_ID_TYPE = 27;

	// upnp:channelID@distriNetworkName
	public static final int UPNP_AV_OBJECT_CHANNEL_ID_DISTRIBUTION_NETWORK_NAME = 28;

	// upnp:channelID@distriNetworkID
	public static final int UPNP_AV_OBJECT_CHANNEL_ID_DISTRIBUTION_NETWORK_ID = 29;

	// !< upnp:rating
	public static final int UPNP_AV_OBJECT_RATING = 30;

	// upnp:rating
	public static final int UPNP_AV_OBJECT_RATING_TYPE = 31;

	// !< upnp:channelNr
	public static final int UPNP_AV_OBJECT_CHANNEL_NR = 32;

	// !< upnp:icon
	public static final int UPNP_AV_OBJECT_ICON = 33;

	// upnp:playbackCount
	public static final int UPNP_AV_OBJECT_PLAYBACK_COUNT = 34;

	// !< the nr of resource extensions for the object
	public static final int UPNP_AV_OBJECT_NR_RESOURCE_EXT = 47;

	// !< UPnP Update ID
	public static final int UPNP_AV_OBJECT_UPDATE_ID = 48;

	// !< UPnP Child count
	public static final int UPNP_AV_OBJECT_CHILD_COUNT = 49;

	// Resource data

	// !< the url to be played
	public static final int UPNP_AV_OBJECT_RESOURCE_URL = 35;

	// !< the protocol info of the url
	public static final int UPNP_AV_OBJECT_RESOURCE_PROTOCOL = 36;

	// !< bitrate of audio/video
	public static final int UPNP_AV_OBJECT_RESOURCE_BITRATE = 37;

	// !< sample rate of audio/video
	public static final int UPNP_AV_OBJECT_RESOURCE_SAMPLE_FREQUENCY = 38;

	// !< "screensize X" of image/video
	public static final int UPNP_AV_OBJECT_RESOURCE_X_SIZE = 39;

	// !< "screensize Y" of image/video
	public static final int UPNP_AV_OBJECT_RESOURCE_Y_SIZE = 40;

	// !< ColorDepth of the image
	public static final int UPNP_AV_OBJECT_RESOURCE_COLOR_DEPTH = 41;

	// !< num channels in audio
	public static final int UPNP_AV_OBJECT_RESOURCE_NUM_CHANNEL = 42;

	// !<bitrate of audio/video
	public static final int UPNP_AV_OBJECT_RESOURCE_BITS_PER_SAMPLE = 43;

	// !< the video frame rate
	public static final int UPNP_AV_OBJECT_RESOURCE_FRAMERATE = 44;

	// !< the video duration
	public static final int UPNP_AV_OBJECT_RESOURCE_DURATION = 45;

	// !< the resource id
	public static final int UPNP_AV_OBJECT_RESOURCE_ID = 46;

	public static final int UPNP_AV_OBJECT_RESOURCE_FILESIZE = 51;

	public static final int UPNP_AV_OBJECT_RESOURCE_RESOLUTION = 52;

	public static final int UPNP_AV_OBJECT_BROWSE_RESULT = 50;
	public static final int UPNP_AV_OBJECT_SEARCH_RESULT = 50;

	public static final int UPNP_AV_OBJECT_REF_ID = 53;

	public static final int UPNP_AV_OBJECT_IS_RESTRICTED = 54;

	public static final int UPNP_AV_OBJECT_TOTAL_NR = 55;

	public static final int UPNP_AV_OBJECT_NR_COMPONENTINFO = 57;

	public static final int UPNP_AV_OBJECT_NR_COMPONENTGROUP = 58;

	public static final int UPNP_AV_OBJECT_NR_COMPONENT = 59;

	public static final int UPNP_AV_OBJECT_COMPONENTGROUP_ID = 60;

	public static final int UPNP_AV_OBJECT_COMPONENT_ID = 61;

	public static final int UPNP_AV_OBJECT_COMPONENT_CLASS = 62;

	public static final int UPNP_AV_OBJECT_COMPONENT_MIMETYPE = 63;

	public static final int UPNP_AV_OBJECT_COMPONENT_EXTTYPE = 64;

	public static final int UPNP_AV_OBJECT_COMPONENT_PROTOCOLINFO = 65;

	public static final int UPNP_AV_OBJECT_COMPONENT_URL = 66;

	public static final int UPNP_AV_OBJECT_LASTPLAYBACK_POSITION = 67;

	public static final int UPNP_NOT_INITIALIZED = 0;

	public static final int UPNP_INITIALIZED = 1;

	public static final int UPNP_INIT_IN_PROGRESS = 2;
}
