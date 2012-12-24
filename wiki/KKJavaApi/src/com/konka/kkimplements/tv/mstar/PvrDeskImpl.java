/**
 * MStar Software
 * Copyright (c) 2011 - 2012 MStar Semiconductor, Inc. All rights reserved.
 *
 * All software, firmware and related documentation herein (鈥淢Star Software鈥� are 
 * intellectual property of MStar Semiconductor, Inc. (鈥淢Star鈥� and protected by 
 * law, including, but not limited to, copyright law and international treaties. 
 * Any use, modification, reproduction, retransmission, or republication of all 
 * or part of MStar Software is expressly prohibited, unless prior written 
 * permission has been granted by MStar. 
 *
 * By accessing, browsing and/or using MStar Software, you acknowledge that you 
 * have read, understood, and agree, to be bound by below terms (鈥淭erms鈥� and to 
 * comply with all applicable laws and regulations:
 *
 * 1. MStar shall retain any and all right, ownership and interest to MStar 
 * Software and any modification/derivatives thereof.  No right, ownership, 
 * or interest to MStar Software and any modification/derivatives thereof is 
 * transferred to you under Terms.
 *
 * 2. You understand that MStar Software might include, incorporate or be supplied 
 * together with third party鈥檚 software and the use of MStar Software may require 
 * additional licenses from third parties.  Therefore, you hereby agree it is your 
 * sole responsibility to separately obtain any and all third party right and 
 * license necessary for your use of such third party鈥檚 software. 
 *
 * 3. MStar Software and any modification/derivatives thereof shall be deemed as 
 * MStar鈥檚 confidential information and you agree to keep MStar鈥檚 confidential 
 * information in strictest confidence and not disclose to any third party.  
 *	
 * 4. MStar Software is provided on an 鈥淎S IS鈥�basis without warranties of any kind. 
 * Any warranties are hereby expressly disclaimed by MStar, including without 
 * limitation, any warranties of merchantability, non-infringement of intellectual 
 * property rights, fitness for a particular purpose, error free and in conformity 
 * with any international standard.  You agree to waive any claim against MStar for 
 * any loss, damage, cost or expense that you may incur related to your use of MStar 
 * Software.  In no event shall MStar be liable for any direct, indirect, incidental 
 * or consequential damages, including without limitation, lost of profit or revenues, 
 * lost or damage of data, and unauthorized system use.  You agree that this Section 4 
 * shall still apply without being affected even if MStar Software has been modified 
 * by MStar in accordance with your request or instruction for your use, except 
 * otherwise agreed by both parties in writing.
 *
 * 5. If requested, MStar may from time to time provide technical supports or 
 * services in relation with MStar Software to you for your use of MStar Software 
 * in conjunction with your or your customer鈥檚 product (鈥淪ervices鈥�.  You understand 
 * and agree that, except otherwise agreed by both parties in writing, Services are 
 * provided on an 鈥淎S IS鈥�basis and the warranty disclaimer set forth in Section 4 
 * above shall apply.  
 *
 * 6. Nothing contained herein shall be construed as by implication, estoppels or 
 * otherwise: (a) conferring any license or right to use MStar name, trademark, 
 * service mark, symbol or any other identification; (b) obligating MStar or any 
 * of its affiliates to furnish any person, including without limitation, you and 
 * your customers, any assistance of any kind whatsoever, or any information; or 
 * (c) conferring any license or right under any intellectual property right.
 *
 * 7. These terms shall be governed by and construed in accordance with the laws 
 * of Taiwan, R.O.C., excluding its conflict of law rules.  Any and all dispute 
 * arising out hereof or related hereto shall be finally settled by arbitration 
 * referred to the Chinese Arbitration Association, Taipei in accordance with 
 * the ROC Arbitration Law and the Arbitration Rules of the Association by three (3) 
 * arbitrators appointed in accordance with the said Rules.  The place of 
 * arbitration shall be in Taipei, Taiwan and the language shall be English.  
 * The arbitration award shall be final and binding to both parties.
 */
package com.konka.kkimplements.tv.mstar;import android.content.Context;

import com.konka.kkinterface.tv.CommonDesk;
import com.konka.kkinterface.tv.PvrDesk;
import com.tvos.common.PvrManager;
import com.tvos.common.PvrManager.CaptureThumbnailResult;
import com.tvos.common.PvrManager.EnumPvrPlaybackSpeed;
import com.tvos.common.PvrManager.EnumPvrStatus;
import com.tvos.common.PvrManager.EnumPvrUsbDeviceLabel;
import com.tvos.common.PvrManager.OnPvrEventListener;
import com.tvos.common.TvManager;
import com.tvos.common.exception.TvCommonException;
import com.tvos.common.vo.PvrFileInfo;
import com.tvos.common.vo.VideoWindowType;


public class PvrDeskImpl extends BaseDeskImpl implements PvrDesk
{
	private static PvrDeskImpl pvrMgrImpl = null;
	private CommonDesk com = null;
	private PvrManager pvrMgr = TvManager.getPvrManager();

	private Context context;
	private PvrDeskImpl(Context context)
	{
		this.context = context;
		com = CommonDeskImpl.getInstance(context);
		com.printfI("TvService", "PvrManagerImpl constructor!!");
	}

	public static PvrDeskImpl getPvrMgrInstance(Context context)
	{
		if (pvrMgrImpl == null)
			pvrMgrImpl = new PvrDeskImpl(context);
		return pvrMgrImpl;
	}

	/**
	 * Start PVR AlwaysTimeShift record .
	 * 
	 * @return short return PVR status
	 * @throws TvCommonException
	 */
	public short startAlwaysTimeShiftRecord() throws TvCommonException
	{
		return pvrMgr.startAlwaysTimeShiftRecord();
	}

	/**
	 * Stop PVR AlwaysTimeShift record.
	 * 
	 * @return short return PVR status
	 * @throws TvCommonException
	 */
	public short stopAlwaysTimeShiftRecord() throws TvCommonException
	{
		return pvrMgr.stopAlwaysTimeShiftRecord();
	}

	/**
	 * Pause PVR AlwaysTimeShift record.
	 * 
	 * @return short return PVR status
	 * @throws TvCommonException
	 */
	public short pauseAlwaysTimeShiftRecord() throws TvCommonException
	{
		return pvrMgr.pauseAlwaysTimeShiftRecord();
	}

	/**
	 * Start PVR AlwaysTimeShift Playback.
	 * 
	 * @return short
	 * @throws TvCommonException
	 */
	public short startAlwaysTimeShiftPlayback() throws TvCommonException
	{
		return pvrMgr.startAlwaysTimeShiftPlayback();
	}

	/**
	 * Stop PVR AlwaysTimesShift Playback.
	 * 
	 * @return none
	 * @throws TvCommonException
	 */
	public void stopAlwaysTimeShiftPlayback() throws TvCommonException
	{
		pvrMgr.stopAlwaysTimeShiftPlayback();
	}

	/**
	 * Check if AlwaysTimeShift playback status is ready.
	 * 
	 * @return boolean
	 * @throws TvCommonException
	 */
	public boolean isAlwaysTimeShiftPlaybackPaused() throws TvCommonException
	{
		return pvrMgr.isAlwaysTimeShiftPlaybackPaused();
	}

	/**
	 * Check if AlwaysTimeShift is recording.
	 * 
	 * @return boolean
	 * @throws TvCommonException
	 */
	public boolean isAlwaysTimeShiftRecording() throws TvCommonException
	{
		return pvrMgr.isAlwaysTimeShiftRecording();
	}

	/**
	 * Start PVR record
	 * 
	 * @return EnumPvrStatus pvr status
	 * 
	 * @throws TvCommonException
	 */
	public EnumPvrStatus startRecord() throws TvCommonException
	{
		return pvrMgr.startRecord();
	}

	/**
	 * Pause PVR record
	 * 
	 * @throws TvCommonException
	 */
	public void pauseRecord() throws TvCommonException
	{
		pvrMgr.pauseRecord();
	}

	/**
	 * Resume PVR record
	 * 
	 * @throws TvCommonException
	 */
	public void resumeRecord() throws TvCommonException
	{
		pvrMgr.resumeRecord();
	}

	/**
	 * Stop PVR record
	 * 
	 * @throws TvCommonException
	 */
	public void stopRecord() throws TvCommonException
	{
		pvrMgr.stopRecord();
	}

	/**
	 * Get the remaining time of current recording, which is estimated by
	 * average bit-rate and free space.
	 * 
	 * @return int
	 * @throws TvCommonException
	 */
	public int getEstimateRecordRemainingTime() throws TvCommonException
	{
		return pvrMgr.getEstimateRecordRemainingTime();
	}

	/**
	 * Start play back
	 * 
	 * @param fileName
	 *            String
	 * @return EnumPvrStatus return status of start playerback
	 * @throws TvCommonException
	 */
	public EnumPvrStatus startPlayback(String fileName) throws TvCommonException
	{
		return pvrMgr.startPlayback(fileName);
	}

	/**
	 * Start play back
	 * 
	 * @param fileName
	 *            String
	 * @param playbackTimeInSecond
	 *            int
	 * @return EnumPvrStatus
	 * @throws TvCommonException
	 */
	public EnumPvrStatus startPlayback(String fileName, int playbackTimeInSecond) throws TvCommonException
	{
		return pvrMgr.startPlayback(fileName, playbackTimeInSecond);
	}

	/**
	 * Start play back
	 * 
	 * @param fileName
	 *            String
	 * @param playbackTimeInSecond
	 *            int
	 * @param thumbnailPts
	 *            int
	 * @return EnumPvrStatus
	 * @throws TvCommonException
	 */
	public EnumPvrStatus startPlayback(String fileName, int playbackTimeInSecond, int thumbnailPts)
	        throws TvCommonException
	{
		return pvrMgr.startPlayback(fileName, playbackTimeInSecond);
	}

	/**
	 * Pause PVR playback
	 * 
	 * @throws TvCommonException
	 */
	public void pausePlayback() throws TvCommonException
	{
		pvrMgr.pausePlayback();
	}

	/**
	 * Resume PVR playback
	 * 
	 * @throws TvCommonException
	 */
	public void resumePlayback() throws TvCommonException
	{
		pvrMgr.resumePlayback();
	}

	/**
	 * Stop PVR playback
	 * 
	 * @throws TvCommonException
	 */
	public void stopPlayback() throws TvCommonException
	{
		pvrMgr.stopPlayback();
	}

	/**
	 * Fast foward playback
	 * 
	 * @throws TvCommonException
	 */
	public void doPlaybackFastForward() throws TvCommonException
	{
		pvrMgr.doPlaybackFastForward();
	}

	/**
	 * Back back playback
	 * 
	 * @throws TvCommonException
	 */
	public void doPlaybackFastBackward() throws TvCommonException
	{
		pvrMgr.doPlaybackFastBackward();
	}

	/**
	 * Jump foward playback
	 * 
	 * @throws TvCommonException
	 */
	public void doPlaybackJumpForward() throws TvCommonException
	{
		pvrMgr.doPlaybackJumpForward();
	}

	/**
	 * Jump back playback
	 * 
	 * @throws TvCommonException
	 */
	public void doPlaybackJumpBackward() throws TvCommonException
	{
		pvrMgr.doPlaybackJumpBackward();
	}

	/**
	 * Play back step in
	 * 
	 * @throws TvCommonException
	 */
	public void stepInPlayback() throws TvCommonException
	{
		pvrMgr.stepInPlayback();
	}

	/**
	 * Start PVR playback loop between AB periodyyg
	 * 
	 * @param abLoopBeginTime
	 *            int Loop begin time
	 * @param abLoopEndTime
	 *            int Loop end time
	 * @throws TvCommonException
	 */
	public void startPlaybackLoop(int abLoopBeginTime, int abLoopEndTime) throws TvCommonException
	{
		pvrMgr.startPlaybackLoop(abLoopBeginTime, abLoopEndTime);
	}

	/**
	 * Stop playback loop
	 * 
	 * @throws TvCommonException
	 */
	public void stopPlaybackLoop() throws TvCommonException
	{
		pvrMgr.stopPlaybackLoop();
	}

	/**
	 * Set playback speed
	 * 
	 * @param playbackSpeed
	 *            EnumPvrPlaybackSpeed one of EnumPvrPlaybackSpeed
	 * @throws TvCommonException
	 */
	public void setPlaybackSpeed(EnumPvrPlaybackSpeed playbackSpeed) throws TvCommonException
	{
		pvrMgr.setPlaybackSpeed(playbackSpeed);
	}

	/**
	 * Get playback speed
	 * 
	 * @return EnumPvrPlaybackSpeed one of EnumPvrPlaybackSpeed
	 * @throws TvCommonException
	 */
	public EnumPvrPlaybackSpeed getPlaybackSpeed() throws TvCommonException
	{
		return pvrMgr.getPlaybackSpeed();
	}

	/**
	 * Jump to a specific time stamp in playback
	 * 
	 * @param jumpToTimeInSeconds
	 *            int
	 * @return boolean
	 * @throws TvCommonException
	 */
	public boolean jumpPlaybackTime(int jumpToTimeInSeconds) throws TvCommonException
	{
		return pvrMgr.jumpPlaybackTime(jumpToTimeInSeconds);
	}

	/**
	 * PVR time shift record start.
	 * 
	 * @return EnumPvrStatus pvr status
	 * @throws TvCommonException
	 */
	public EnumPvrStatus startTimeShiftRecord() throws TvCommonException
	{
		return pvrMgr.startTimeShiftRecord();
	}

	/**
	 * PVR time shift record stop.
	 * 
	 * @throws TvCommonException
	 */
	public void stopTimeShiftRecord() throws TvCommonException
	{
		pvrMgr.stopTimeShiftRecord();
	}

	/**
	 * PVR time shift playback stop.
	 * 
	 * @throws TvCommonException
	 */
	public void stopTimeShiftPlayback() throws TvCommonException
	{
		pvrMgr.stopTimeShiftPlayback();
	}

	/**
	 * PVR time shift stop.
	 * 
	 * @throws TvCommonException
	 */
	public void stopTimeShift() throws TvCommonException
	{
		pvrMgr.stopTimeShift();
	}

	/**
	 * PVR stop both playback, record and time-shift.
	 * 
	 * @return boolean success or not
	 * 
	 * @throws TvCommonException
	 */
	public boolean stopPvr() throws TvCommonException
	{
		return pvrMgr.stopPvr();
	}

	/**
	 * Query PVR is recroding or not.
	 * 
	 * @return boolean TRUE : PVR is recording, FALSE: PVR is not recording.
	 * @throws TvCommonException
	 */
	public boolean isRecording() throws TvCommonException
	{
		return pvrMgr.isRecording();
	}

	/**
	 * Query PVR is playbacking.
	 * 
	 * @return boolean TRUE : PVR is playbacking, FALSE: PVR is not playbacking.
	 * @throws TvCommonException
	 */
	public boolean isPlaybacking() throws TvCommonException
	{
		return pvrMgr.isPlaybacking();
	}

	/**
	 * Query PVR is timeShift-recording.
	 * 
	 * @return boolean TRUE : PVR is timeShift-recording, FALSE: PVR is not
	 *         timeShift-recording.
	 * @throws TvCommonException
	 */
	public boolean isTimeShiftRecording() throws TvCommonException
	{
		return pvrMgr.isTimeShiftRecording();
	}

	/**
	 * Query PVR is playback Parental Lock.
	 * 
	 * @return boolean TRUE : PVR is Parental Lock, FALSE: PVR is not Parental
	 *         Lock.
	 * @throws TvCommonException
	 */
	public boolean isPlaybackParentalLock() throws TvCommonException
	{
		return pvrMgr.isPlaybackParentalLock();
	}

	/**
	 * Query PVR is playback paused.
	 * 
	 * @return boolean TRUE : PVR is playback Pause, FALSE: PVR is not
	 *         playbacking.
	 * @throws TvCommonException
	 */
	public boolean isPlaybackPaused() throws TvCommonException
	{
		return pvrMgr.isPlaybackPaused();
	}

	/**
	 * Query PVR is record pause Mode.
	 * 
	 * @return boolean TRUE : PVR is record Pause, FALSE: PVR is not record
	 *         Pause.
	 * @throws TvCommonException
	 */
	public boolean isRecordPaused() throws TvCommonException
	{
		return pvrMgr.isRecordPaused();
	}

	/**
	 * Query PVR set playback window.
	 * 
	 * @param videoWindowType
	 *            VideoWindowType
	 * 
	 * @param containerWidth
	 *            int
	 * 
	 * @param containerHeight
	 *            int
	 * 
	 * @return none
	 * @throws TvCommonException
	 */
	public void setPlaybackWindow(VideoWindowType videoWindowType, int containerWidth, int containerHeight)
	        throws TvCommonException
	{
		pvrMgr.setPlaybackWindow(videoWindowType, containerWidth, containerHeight);
	}

	/**
	 * Get Current recording File Name
	 * 
	 * @return String The name of current recording file
	 * @throws TvCommonException
	 */
	public String getCurRecordingFileName() throws TvCommonException
	{
		return pvrMgr.getCurRecordingFileName();
	}

	/**
	 * Get current playbacking time in secends
	 * 
	 * @return int current playbacking time in secends.
	 * @throws TvCommonException
	 */
	public int getCurPlaybackTimeInSecond() throws TvCommonException
	{
		return pvrMgr.getCurPlaybackTimeInSecond();
	}

	/**
	 * Get Current recording File Name
	 * 
	 * @return int The name of current recording file.
	 * @throws TvCommonException
	 */
	public int getCurRecordTimeInSecond() throws TvCommonException
	{
		return pvrMgr.getCurRecordTimeInSecond();
	}

	/**
	 * Jump to specific PVR file target thumbnail
	 * 
	 * @param thumbnailIndex
	 *            int
	 * 
	 * @return boolean TRUE : successful, FALSE: fail.
	 * @throws TvCommonException
	 */
	public boolean jumpToThumbnail(int thumbnailIndex) throws TvCommonException
	{
		return pvrMgr.jumpToThumbnail(thumbnailIndex);
	}

	/**
	 * PVR timeShift SetTimeShiftFileSize.
	 * 
	 * @param timeShiftFileSizeInKB
	 *            int
	 * 
	 * @throws TvCommonException
	 */
	public void setTimeShiftFileSize(long timeShiftFileSizeInKb) throws TvCommonException
	{
		pvrMgr.setTimeShiftFileSize(timeShiftFileSizeInKb);
	}

	/**
	 * Capture specific PVR file target thumbnail
	 * 
	 * @return CaptureThumbnailResult capture thumbnail result
	 * @throws TvCommonException
	 */
	public CaptureThumbnailResult captureThumbnail() throws TvCommonException
	{
		return pvrMgr.captureThumbnail();
	}

	/**
	 * Get Current playbacking File Name
	 * 
	 * @return String
	 * @throws TvCommonException
	 */
	public String getCurPlaybackingFileName() throws TvCommonException
	{
		return pvrMgr.getCurPlaybackingFileName();
	}

	/**
	 * set PVR AlwaysTimeShift playback status as ready. Which means Live DTV
	 * image is freezed.
	 * 
	 * @param ready
	 *            boolean ready or not
	 * @return EnumPvrStatus
	 * @throws TvCommonException
	 */
	public EnumPvrStatus pauseAlwaysTimeShiftPlayback(boolean ready) throws TvCommonException
	{
		return pvrMgr.pauseAlwaysTimeShiftPlayback(ready);
	}

	/**
	 * PVR time shift playback start.
	 * 
	 * @return EnumPvrStatus
	 * @throws TvCommonException
	 */
	public EnumPvrStatus startTimeShiftPlayback() throws TvCommonException
	{
		return pvrMgr.startTimeShiftPlayback();
	}

	/**
	 * PVR u disk format start.
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	public void startPvrFormat() throws TvCommonException
	{
		return;
	}

	/**
	 * check the read/write speed of curent USB storage device
	 * 
	 * @return int Read/Write speed of current USB storage device
	 * @throws TvCommonException
	 */
	public int checkUsbSpeed() throws TvCommonException
	{
		return pvrMgr.checkUsbSpeed();
	}

	/**
	 * To get partition number in storage device
	 * 
	 * @return int partition number in storage device.
	 * @throws TvCommonException
	 */
	public int getUsbPartitionNumber() throws TvCommonException
	{
		return pvrMgr.getUsbPartitionNumber();
	}

	/**
	 * To get Device number
	 * 
	 * @return int Device number
	 * @throws TvCommonException
	 */
	public int getUsbDeviceNumber() throws TvCommonException
	{
		return pvrMgr.getUsbDeviceNumber();
	}

	/**
	 * To get Device index
	 * 
	 * @return short Device index.
	 * @throws TvCommonException
	 */
	public short getUsbDeviceIndex() throws TvCommonException
	{
		return pvrMgr.getUsbDeviceIndex();
	}

	/**
	 * Get usb device label in E_LABEL for identification.
	 * 
	 * @param deviceIndex
	 *            int index of the device, which changes if other device is
	 *            removed.
	 * @return EnumPvrUsbDeviceLabel
	 * @throws TvCommonException
	 */
	public EnumPvrUsbDeviceLabel getUsbDeviceLabel(int deviceIndex) throws TvCommonException
	{
		return pvrMgr.getUsbDeviceLabel(deviceIndex);
	}

	/**
	 * To get PVR file number in storage device
	 * 
	 * @return int PVR file number in storage device.
	 * @throws TvCommonException
	 */
	public int getPvrFileNumber() throws TvCommonException
	{
		return pvrMgr.getPvrFileNumber();
	}

	/**
	 * To get PVR file info sorted by the given key, ascending or descending
	 * 
	 * @param index
	 *            int index
	 * @return PvrFileInfo PVR file info
	 * @throws TvCommonException
	 */
	public PvrFileInfo getPvrFileInfo(int index) throws TvCommonException
	{
		return pvrMgr.getPvrFileInfo(index);
	}

	/**
	 * Get LCN of the specific file
	 * 
	 * @param index
	 *            int Target index file
	 * @return int LCN (logical channel number)
	 * @throws TvCommonException
	 */
	public int getFileLcn(int index) throws TvCommonException
	{
		return pvrMgr.getFileLcn(index);
	}

	/**
	 * Get service name (channel name) of the specific file
	 * 
	 * @param fileName
	 *            String Specific file name
	 * @return String Service name
	 * @throws TvCommonException
	 */
	public String getFileServiceName(String fileName) throws TvCommonException
	{
		return pvrMgr.getFileServiceName(fileName);
	}

	/**
	 * Get event name (program name) of the specific file
	 * 
	 * @param fileName
	 *            String Specific file name
	 * @return String Event name
	 * @throws TvCommonException
	 */
	public String getFileEventName(String fileName) throws TvCommonException
	{
		return pvrMgr.getFileEventName(fileName);
	}

	/**
	 * Assign specific PVR file info for thumbnail using
	 * 
	 * @param fileName
	 *            String Specific file name
	 * @return boolean TRUE : successful;FALSE: fail.
	 * @throws TvCommonException
	 */
	public boolean assignThumbnailFileInfoHandler(String fileName) throws TvCommonException
	{
		return pvrMgr.assignThumbnailFileInfoHandler(fileName);
	}

	/**
	 * Get specific PVR file thumbnail number
	 * 
	 * @return int thumbnail number
	 * @throws TvCommonException
	 */
	public int getThumbnailNumber() throws TvCommonException
	{
		return pvrMgr.getThumbnailNumber();
	}

	/**
	 * Get specific PVR file target thumbnail complete path
	 * 
	 * @param index
	 *            int Target index thumbnail
	 * @return String thumbnail complete path
	 * @throws TvCommonException
	 */
	public String getThumbnailPath(int index) throws TvCommonException
	{
		return pvrMgr.getThumbnailPath(index);
	}

	/**
	 * Get specific PVR file target thumbnail display UI info
	 * 
	 * @param index
	 *            int Target index thumbnail
	 * @return String display UI info
	 * @throws TvCommonException
	 */
	public String getThumbnailDisplay(int index) throws TvCommonException
	{
		return pvrMgr.getThumbnailDisplay(index);
	}

	/**
	 * Get specific PVR file target thumbnail TimeStamp
	 * 
	 * @param index
	 *            int Target index thumbnail
	 * @return int[] int[0]: JumpTimeStamp(sec), int[1]:JumpCapturePTS(VDEC use)
	 * @throws TvCommonException
	 */
	public int[] getThumbnailTimeStamp(int index) throws TvCommonException
	{
		return pvrMgr.getThumbnailTimeStamp(index);
	}

	/**
	 * Establish database by another USB Device
	 * 
	 * @param deviceIndex
	 *            short Device index
	 * @return boolean TRUE : establish Success;FALSE: establish Failure.
	 * @throws TvCommonException
	 */
	public boolean changeDevice(short deviceIndex) throws TvCommonException
	{
		return pvrMgr.changeDevice(deviceIndex);
	}

	public void setOnPvrEventListener(OnPvrEventListener listener)
	{
		pvrMgr.setOnPvrEventListener(listener);
	}
}