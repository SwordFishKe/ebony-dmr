/*
* RemoteUPnPMediaRendererService.aidl interface defines the set of APIs exposed to the Content Explorer for DMR functionality
* UPnPControlPointService implements these APIs and returns the handle of this interface 
*/

package org.droidtv.tv.dlna;
import org.droidtv.tv.dlna.UPnPAVObject;
import org.droidtv.tv.dlna.UPnPTransform;
import org.droidtv.tv.dlna.UPnPTransformSetting;

interface RemoteUPnPMediaRendererService{

/* Name: registerRendererCallback
 * @ params: cb - Handle to the Content Explorer interface to send callbacks
 * Content Explorer will register the callback interface on binding successfully
 */
void registerRendererCallback(IBinder listener);

/* Name: unregisterRendererCallback
 * Content Explorer will unregister on unbinding successfully
 */
void unregisterRendererCallback();

/* Name: DMR_SetPlayState
 * @ params: PlayState - Indicates what is DMR playstate
 */
void DMR_SetPlayState(int PlayState);

/* Name: DMR_SetPlayState
 * @ params: PlaySpeed - Actual playspeed for current DMR playback
 */
void DMR_SetPlaySpeed(int PlaySpeed);

/* Name: DMR_SetPlayProgressState
 * @ params seconds : elapsed time since start of play  (in sec)     
 * @ params totaltime : total (estimated) time of play  (in sec)     
 * @ params readbytes : bytes consumed (sofar)       
 * @ params totalbytes : total bytes to consume      
 */
void DMR_SetPlayProgressState(int seconds, int totaltime, int readbytes, int totalbytes);

/* Name: DMR_EventLastChange
 * @ params Service : the service identifier [MR_SERVICE_RCS,MR_SERVICE_AVTS]     
 */
void DMR_EventLastChange(int Service);

/* Name: DMR_RemovePauseSeek
 * @ params removePause : 1 indicates to be removed; 0 indicates not to be removed     
 * @ params removeSeek : 1 indicates to be removed; 0 indicates not to be removed     
 */
void DMR_RemovePauseSeek(int removePause, int removeSeek);

/* Name: DMR_SetStateVar
 * @ params servId : the service identifier [MR_SERVICE_RCS]
 * @ params varId : 1 indicates to be removed; 0 indicates not to be removed     
 * @ params Value : 1 indicates to be removed; 0 indicates not to be removed     
 * @ params len : 1 indicates to be removed; 0 indicates not to be removed     
 * @ params sendNotify : 1 indicates to be evented; 0 indicates not to be evented     
*/
void DMR_SetStateVar( int servId , int varId, String Value,int sendNotify );

/* Name: DMR_PlayListCleared
 * Notifies dlna that Playlist is cleared by CE
 */
	void DMR_PlayListCleared();
	
/* Name: DMR_OnPlayCallback
 * @ params index : Index of the file that is currently playing in the playlist
 * Notifies dlna that Playback of new file started
 */
	void DMR_OnPlayCallback(int index);
	
/* Name: DMR_SetSupportedSubtitleList
 * @ params subtitleTransformObject : Transform object containing the Embedded subtitle information
 * Notifies dlna about the Embedded subtitle detected
 */
	void DMR_SetSupportedSubtitleList(in UPnPTransformSetting[] subtitleTransformObject); 
	
/* Name: DMR_subtitleSelected
 * @ params subtitleTransformObject : Transform object containing the selected subtitle information
 * Notifies dlna if User selected a subtitle from Remote to Play
 */
	 void DMR_subtitleSelected (in UPnPTransformSetting subtitle);
	 
/* Name: DMR_SetTransformDone
 * @ params status : Status of SetStransform. 0- Success
 * Notifies dlna about the status of SetTransform request
 */
	 void DMR_SetTransformDone (int status);

}