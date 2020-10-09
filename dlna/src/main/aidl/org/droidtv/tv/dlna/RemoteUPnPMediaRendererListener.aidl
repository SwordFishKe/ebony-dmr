/*
* RemoteUPnPMediaRendererListener.aidl interface defines the set of APIs exposed to the Content Explorer for DMR functionality
* UPnPControlPointService implements these APIs and returns the handle of this interface 
*/

package org.droidtv.tv.dlna;

import org.droidtv.tv.dlna.UPnPAVObject;
import org.droidtv.tv.dlna.UPnPTransform;
import org.droidtv.tv.dlna.UPnPTransformSetting;

interface RemoteUPnPMediaRendererListener{
/* Name: DMR_SetAVTransportUrl
 * @ params: InstanceID - the instanceID of the player, expected to be 0
 * @ params: Url - the Url to be played
 * @ params: MyObject - JAVA Object containing metadata (or NULL)
 * @ return: 0 if request is honoured otherwise error values of
 * @				402 (invalid argument),
 * @				718 (invalid instance),
 * @				701 (transition not available)
 * @				714 (no such resource),
 * @				501 (general error)
 * Request for setting url
 */
 	int[] DMR_SetAVTransportUrl(int InstanceID, String Url, in UPnPAVObject MyObject);


/* Name: DMR_Play
 * @ params: InstanceID - the instanceID of the player, expected to be 0
 * @ params: Speed - Play Speed; Normal play will have play speed of 1
 * @ return: 0 if request is honoured otherwise error values of
 * @				402 (invalid argument),
 * @				718 (invalid instance),
 * @				701 (transition not available)
 * @				501 (general error)
 * Request for Play of media as indicated by SetAvt call; Actual playback state of Play will be set separately by ContentExplorer
 * Playbackstate not indicated within 30 seconds is treated as an error within DLNA stack
 */
	int DMR_Play(int InstanceID,int Speed);
	
/* Name: DMR_PlayWithPlaySpeed
 * @ params: InstanceID - the instanceID of the player, expected to be 0
 * @ params: Speed - Play Speed; Allowed values: -32,-16,-8,-4,-2,-1/2,-1/4,1/2,1/4,1,2,4,8,16,32
 * @ return: 0 if request is honoured otherwise error values of
 * @				402 (invalid argument),
 * @				718 (invalid instance),
 * @				701 (transition not available)
 * @				501 (general error)
 * Request for Play of media as indicated by SetAvt call; Actual playback state of Play will be set separately by ContentExplorer
 * Playbackstate not indicated within 30 seconds is treated as an error within DLNA stack
 */
	int DMR_PlayWithPlaySpeed(int InstanceID,String Speed);

/* Name: DMR_Stop
 * @ params: InstanceID - the instanceID of the player, expected to be 0
 * @ return: 0 if request is honoured otherwise error values of
 * @				402 (invalid argument),
 * @				718 (invalid instance),
 * @				701 (transition not available)
 * @				501 (general error)
 * Request for Stop; Actual playback state of Stop will be set separately by ContentExplorer; 
 * Playbackstate not indicated within 30 seconds is treated as an error within DLNA stack
 */
	int DMR_Stop(int InstanceID);

/* Name: DMR_Pause
 * @ params: InstanceID - the instanceID of the player, expected to be 0
 * @ return: 0 if request is honoured otherwise error values of
 * @				402 (invalid argument),
 * @				718 (invalid instance),
 * @				701 (transition not available)
 * @				501 (general error)
 * Request for Pause; Actual playback state of Pause will be set separately by ContentExplorer
 * Playbackstate not indicated within 30 seconds is treated as an error within DLNA stack
 */
	int DMR_Pause(int InstanceID);

/* Name: DMR_Seek
 * @ params: InstanceID - the instanceID of the player, expected to be 0
 * @ params: SeekMode - one of the seekmodes (time, track nr)
 * @ params: Target - string containing the seek (time,count etc)
 * @ return: 0 if request is honoured otherwise error values of
 * @				402 (invalid argument),
 * @				718 (invalid instance),
 * @				710 (seekmode not supported),
 * @				711 (seek target not supported),
 * @				701 (transition not available)
 * @				501 (general error)
 * Request for Seek
 */
	int DMR_Seek(int InstanceID, int SeekMode, String Target, int len);
	
/* Name: DMR_getAllAvaiableTransforms
 * 
 * @retval list of all available UPnPTransforms
 * 		
 * Request for all available Transforms
 */	
	
	UPnPTransform[] DMR_getAllAvaiableTransforms();
	
/* Name: DMR_getCurrentTransformsList
 * 
 * @retval list of currently applied UPnPTransformSetting
 * 		
 * Request for all currently applied Transforms
 */	

    UPnPTransformSetting[] DMR_getCurrentTransformsList();
    
/* Name: DMR_CreatePlayList
 * @ params: objList - The list of items pushed by DMC
 * @ return: No of items added to Playlist, if success
             -1, if failure
 * Create the Playlist pushed by DMC
 */
	int DMR_CreatePlayList(in UPnPAVObject[] objList, int size);

/* Name: DMR_AddToPlayList
 * @ params: objList - The list of items pushed by DMC
 * @ return: No of items added to Playlist, if success
             -1, if failure
 * Create the Playlist pushed by DMC
 */
	int DMR_AddToPlayList(in UPnPAVObject[] objList, int size);

/* Name: DMR_ResetPlayList 
 * @ return:  0, if success
             -1, if failure
 * Reset the Playlist 
 */
	int DMR_ResetPlayList();

/* Name: DMR_Next
 * @ return:  Index of the currently playing item
              701, if failure
 * Moves to the Next item in the Playlist
 */
    int DMR_Next();
	
/* Name: DMR_Previous
 * @ return:  Index of the currently playing item
              701, if failure
 * Moves to the Next item in the Playlist
 */
    int DMR_Previous();
	
	/* Name: DMR_SelectedTrack
	* @ params: selectedTrackNumber - selected track number
	* @ return:  Index of the currently playing item 701, if failure
	* Moves to the selected item in the Playlist
	*/
    int DMR_SelectedTrack(int selectedTrackNumber);
	
/* Name: DMR_generateSupportedExternalSubtitleList
 * @ params object : Video Object
 * @ return:  A Transform object containing the list of allowed Subtitles.
 * Returns the list of external subtitles supported for the object given.
 */
    UPnPTransformSetting DMR_generateSupportedExternalSubtitleList(in UPnPAVObject object);
	
/* Name: DMR_setTransform
 * @ params Transforms : Contains the information of the Transform to be applied
 * @ return:  0, on Success
 *          701, Transition not Available
 *          711, Transform Not allowed
 *          712, Invalid Values
 *          713, Internal Error
 * Applies a Transform specified in Transforms
 */
    int DMR_setTransform(in UPnPTransformSetting[] Transforms, int size);
	

/* Name: DMR_getSupportedSubtitleList
 * @ return:  A Transform object containing the list of allowed Subtitles.
 * Returns the list of allowed external subtitles at that point of time
 */
    UPnPTransformSetting[] DMR_getSupportedSubtitleList();
	
/* Name: DMR_getAllowedTransforms
 * @ return:  0, No Transform allowed
 *          1, Photo: Zoom, Pan, Rotate are allowed (If Playing an image)
 *          2, Subtitle is allowed allowed (If Playing a Video file having subtitles supported by TV)
 * Retuns an integer value indicating the allowed transform on the Object currently playing
 */
    int DMR_getAllowedTransforms();
}