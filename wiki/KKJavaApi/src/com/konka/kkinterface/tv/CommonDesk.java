/**
 * MStar Software
 * Copyright (c) 2011 - 2012 MStar Semiconductor, Inc. All rights reserved.
 *
 * All software, firmware and related documentation herein (鈥淢Star Software锟� are 
 * intellectual property of MStar Semiconductor, Inc. (鈥淢Star锟� and protected by 
 * law, including, but not limited to, copyright law and international treaties. 
 * Any use, modification, reproduction, retransmission, or republication of all 
 * or part of MStar Software is expressly prohibited, unless prior written 
 * permission has been granted by MStar. 
 *
 * By accessing, browsing and/or using MStar Software, you acknowledge that you 
 * have read, understood, and agree, to be bound by below terms (鈥淭erms锟� and to 
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
 * 4. MStar Software is provided on an 鈥淎S IS锟�basis without warranties of any kind. 
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
 * in conjunction with your or your customer鈥檚 product (鈥淪ervices锟�.  You understand 
 * and agree that, except otherwise agreed by both parties in writing, Services are 
 * provided on an 鈥淎S IS锟�basis and the warranty disclaimer set forth in Section 4 
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
package com.konka.kkinterface.tv;

import com.tvos.common.vo.TvOsType.EnumInputSource;
import android.view.SurfaceHolder;

public interface CommonDesk extends BaseDesk {
	final static boolean DEBUG_FLAG = true;
	final static int SETIS_START = -100;
	final static int SETIS_END_COMPLETE = -101;
	public final static int Cmd_XXX_Min      = 0x40;
	public final static int Cmd_SourceInfo   = 0x41;
	public final static int Cmd_SignalLock   = 0x42;
	public final static int Cmd_SignalUnLock = 0x43;
	public final static int Cmd_TvApkExit    = 0x44;
	public final static int Cmd_CommonVedio  = 0x45;
	public final static int Cmd_XXX_Max      = 0x60;
	public enum EnumDeskEvent {
		// 0
		EV_DTV_CHANNELNAME_READY,
		// 1
		EV_ATV_AUTO_TUNING_SCAN_INFO,
		// 2
		EV_ATV_MANUAL_TUNING_SCAN_INFO,
		// 3
		EV_DTV_AUTO_TUNING_SCAN_INFO,
		// 4
		EV_DTV_PROGRAM_INFO_READY,
		// 5
		EV_SIGNAL_LOCK,
		// 6
		EV_SIGNAL_UNLOCK,
		// 7
		EV_POPUP_DIALOG,
		// 8
		EV_SCREEN_SAVER_MODE,
		// 9
		EV_CI_LOAD_CREDENTIAL_FAIL,
		// 10
		EV_EPGTIMER_SIMULCAST,
		// 11
		EV_HBBTV_STATUS_MODE,
		// 12
		EV_MHEG5_STATUS_MODE,
		// 13
		EV_MHEG5_RETURN_KEY,
		// 14
		EV_OAD_HANDLER,
		// 15
		EV_OAD_DOWNLOAD,
		// 16
		EV_PVR_NOTIFY_PLAYBACK_TIME,
		// 17
		EV_PVR_NOTIFY_PLAYBACK_SPEED_CHANGE,
		// 18
		EV_PVR_NOTIFY_RECORD_TIME,
		// 19
		EV_PVR_NOTIFY_RECORD_SIZE,
		// 20
		EV_PVR_NOTIFY_RECORD_STOP,
		// 21
		EV_PVR_NOTIFY_PLAYBACK_STOP,
		// 22
		EV_PVR_NOTIFY_PLAYBACK_BEGIN,
		// 23
		EV_PVR_NOTIFY_TIMESHIFT_OVERWRITES_BEFORE,
		// 24
		EV_PVR_NOTIFY_TIMESHIFT_OVERWRITES_AFTER,
		// 25
		EV_PVR_NOTIFY_OVER_RUN,
		// 26
		EV_PVR_NOTIFY_USB_REMOVED,
		// 27
		EV_PVR_NOTIFY_CI_PLUS_PROTECTION,
		// 28
		EV_PVR_NOTIFY_PARENTAL_CONTROL,
		// 29
		EV_PVR_NOTIFY_ALWAYS_TIMESHIFT_PROGRAM_READY,
		// 30
		EV_PVR_NOTIFY_ALWAYS_TIMESHIFT_PROGRAM_NOTREADY,
		// 32
		EV_PVR_NOTIFY_CI_PLUS_RETENTION_LIMIT_UPDATE,
		// 33
		EV_DTV_AUTO_UPDATE_SCAN,
		// 34
		EV_TS_CHANGE,
		// 35
		EV_POPUP_SCAN_DIALOGE_LOSS_SIGNAL,
		// 36
		EV_POPUP_SCAN_DIALOGE_NEW_MULTIPLEX,
		// 37
		EV_POPUP_SCAN_DIALOGE_FREQUENCY_CHANGE,
		// 37
		EV_RCT_PRESENCE, EV_CHANGE_TTX_STATUS,
		// 38
		EV_DTV_PRI_COMPONENT_MISSING,
		// 39
		EV_AUDIO_MODE_CHANGE,
		// 40
		EV_MHEG5_EVENT_HANDLER,
		// 41
		EV_OAD_TIMEOUT,
		// 42
		EV_GINGA_STATUS_MODE,
		// 43
		EV_HBBTV_UI_EVENT,
	}

	// / Screen Saver Mode in VGA or Ypbpr
	public enum EnumSignalProgSyncStatus {
		// /< Input timing stable, no input sync detected
		E_SIGNALPROC_NOSYNC,
		// /< Input timing stable, has stable input sync and support this timing
		E_SIGNALPROC_STABLE_SUPPORT_MODE,
		// /< Input timing stable, has stable input sync but this timing is not
		// supported
		E_SIGNALPROC_STABLE_UN_SUPPORT_MODE,
		// /< Timing change, has to wait InfoFrame if HDMI input
		E_SIGNALPROC_UNSTABLE,
		// /< Timing change, has to auto adjust if PCRGB input
		E_SIGNALPROC_AUTO_ADJUST,
	};

	// / Screen Saver Mode in DTV
	public enum EnumScreenMode {
		// / The screen saver mode is invalid service.
		MSRV_DTV_SS_INVALID_SERVICE,
		// / The screen saver mode is no CI module.
		MSRV_DTV_SS_NO_CI_MODULE,
		// / The screen saver mode is CI+ Authentication.
		MSRV_DTV_SS_CI_PLUS_AUTHENTICATION,
		// / The screen saver mode is scrambled program.
		MSRV_DTV_SS_SCRAMBLED_PROGRAM,
		// / The screen saver mode is channel block.
		MSRV_DTV_SS_CH_BLOCK,
		// / The screen saver mode is parental block.
		MSRV_DTV_SS_PARENTAL_BLOCK,
		// / The screen saver mode is audio only.
		MSRV_DTV_SS_AUDIO_ONLY,
		// / The screen saver mode is data only.
		MSRV_DTV_SS_DATA_ONLY, 
		// / The screen saver mode is common video.
		MSRV_DTV_SS_COMMON_VIDEO,
		// / The screen saver mode is Unsupported Format.
		MSRV_DTV_SS_UNSUPPORTED_FORMAT, 
		// / The screen saver mode is invalid pmt.
		MSRV_DTV_SS_INVALID_PMT,
		// / The screen saver mode support type.
		MSRV_DTV_SS_MAX
	};

	/**
	 * SourceSrctable for get sourcelist
	 * 
	 * @author caucy.niu
	 * 
	 */
	public int[] getSourceList();

	/**
	 * 
	 * @author caucy.niu
	 * 
	 */
	public static enum EN_SCAN_TYPE {
		// Video signal scan type is progressive
		E_PROGRESSIVE,
		// Video signal scan type is interlaced
		E_INTERLACED
	}

	/**	 * 	 */
	public class ST_VIDEO_INFO {
		// Video Horizontal direction size
		public short s16HResolution;
		// Video vertical direction size
		public short s16VResolution;
		// The video image refresh speed per second
		public short s16FrameRate;
		// Video signal scan type progressive or interlaced
		public EN_SCAN_TYPE enScanType;

		public ST_VIDEO_INFO(short hRes, short vRes, short fRate,
				EN_SCAN_TYPE eType) {
			this.s16HResolution = hRes;
			this.s16VResolution = vRes;
			this.s16FrameRate = fRate;
			this.enScanType = eType;
		}
	}

	/**
	 * 鏉跨殑淇℃伅
	 */
	public class SystemBoardInfo
	{
		/**
		 * 骞冲彴
		 */
		public String strPlatform;
		/**
		 * 鏈哄瀷
		 */
		public String strSerial;
		/**
		 * 锟�		 */
		public String strPanelName;
		/**
		 * release鐗堟湰
		 */
		public String strVersion;
		/**
		 * svn鐗堟湰
		 */
		public String strBuild;
		/**
		 * 杞欢鐗╂枡锟�		 */
		public String strCode;
		/**
		 * 缂栬瘧鏃ユ湡鍜屾椂锟�		 */
		public String strDate;
		/**
		 * 绯诲垪
		*/
		public String strBoard;
		/**
		 *  * mboot version
		 */
		public String strVersionMboot;
		/**
		 * 6m30 version
		 */
		public String strVersion6m30;
	}
	
	/**
	 * 閫氶亾閰嶇疆淇℃伅
	 */
	public class InputSourceInfo
	{
		/**
		 * ATV閫氶亾锟�		 */
		public int sourceCountATV;
		/**
		 * DTV閫氶亾锟�		 */
		public int sourceCountDTV;
		/**
		 * AV閫氶亾锟�		 */
		public int sourceCountAV;
		/**
		 * YPbPr閫氶亾锟�		 */
		public int sourceCountYPbPr;
		/**
		 * VGA閫氶亾锟�		 */
		public int sourceCountVGA;
		/**
		 * HDMI閫氶亾锟�		 */
		public int sourceCountHDMI;
	}

	public class AudioInfo
	{
		//srs
		public int enableSrs; 
		//Coaxial out
		public int enableCoaxial;
		//Sound Box
		public int enableSoundBox;
	}

	public class CiCardInfo
	{
		/*
		 *  0 : The platform does not support CiCard
		 *  1 : The platform support CiCard
		 */  
		public int enableCiCard; 
	}
	
	public class BatteryInfo
	{
		//Battery Info
		public int enableBattery; 
	}
	
	public class PipInfo
	{
		// pip enable
		public int enablePip;
		// dual view enable
		public int enableDualView;
	}
		
	public class SystemBuildInfo
	{
		//version
		public String strTVVersion;
		//build number
		public String strBuild;
		//date
		public String strDate;
		//UTC
		public String strDateUTC;
		//strHost
		public String strHost;
	}
	
	public class ApplicationsInfo
	{
		// cntv
		public int enableCNTV;
	}
	
	public class LocalDimmingInfo
	{
		public int localdimmingenable;
	}
	
	public class EightBarLightEnableInfo
	{
		public int eightbarlightenable;
	}
	/**
	 * for system setting
	 */
	public class WifiDeviceInfo {
		/**
		 *  @category 0: external; //for 800C
		 *  @category 1: internal. //default: 801,800
		 */
		public int isDeviceInternal;
	}
	
	public class Panel4K2K
	{
		/**
         * @category 0: screen resolution is not 3840X2160. //default: normal
         * @category 1: screen resolution is 3840X2160.     //for 4K2K
         */
		public int isPanelWidth4K = 0;
	}
	
	public class CPUInfo {
		/**
		 * @category 0: double cloud engine double-core 1GCPU+GPU //default: for 801
		 * @category 1: Cortex A9 CPU + double-core GPU           //for 800, 800C
		 */
		public int iCPUCoreType;
	}
	
	public class ExternalStorageInfo {
		/**
		 * @category 0: SD card slot does not exist, SD card is not supportable. //for 800, 800C
		 * @category 1: SD card slot exists, SD Card is supportable.			  //default: for 801
		 */
		public int hasSDCardSlot;
	}
	
	public class PanelSupportInfo {
		/**
		 * @category 0: panel_not_support back_light adjust while 3d_enable. //for 800C
		 * @category 1: panel_support back_light adjust while 3d_enable.     //default: for 80X
		 */
		public int isBLAdjustableFor3D;
	}
	/**
	 * 
	 * 
	 * 
	 * @return CurrentInputSource type
	 */
	public EnumInputSource GetCurrentInputSource();

	public void SetInputSource(EnumInputSource st);

	public void SetInputSource(EnumInputSource st, boolean bWriteDB);

	/**
	 * Signal Lock status
	 * 
	 * @return true:lock false unlock
	 */
	public boolean isSignalStable();

	/**
	 * HDMI video format
	 * 
	 * @return 0:DVI,1:HDMI
	 */
	public boolean isHdmiSignalMode();

	/**
	 * Get Current Video format
	 * 
	 * @return H/V Resoultion Frame rate and Scan type.
	 */
	public ST_VIDEO_INFO getVideoInfo();

	public boolean setDisplayHolder(SurfaceHolder sh);

	public void printfE(String sTag, String sMessage);

	public void printfE(String sMessage);

	public void printfV(String sTag, String sMessage);

	public void printfV(String sMessage);

	public void printfI(String sTag, String sMessage);

	public void printfI(String sMessage);

	public void printfW(String sTag, String sMessage);

	public void printfW(String sMessage);

	public boolean ExecSetInputSource(EnumInputSource st);

	public boolean startMsrv();
	
	public boolean enterSleepMode(boolean bMode, boolean bNoSignalPwDn);
	
	public SystemBoardInfo getSystemBoardInfo();
	
	public InputSourceInfo getInputSourceInfo();

	public AudioInfo getAudioInfo();
	
	public CiCardInfo getCiCardInfo();

	public BatteryInfo getBatteryInfo();
	
	public PipInfo getPipInfo();
	
	public SystemBuildInfo getSystemBuildInfo();
	
	public ApplicationsInfo getApplicationsInfo();
	
	public LocalDimmingInfo getLocalDimmingInfo();
	
	public EightBarLightEnableInfo getEgihtBarLightEnableInfo();
	
	public boolean isSupport3D();

	public Panel4K2K getPanel4K2K();
	
	public WifiDeviceInfo getWifiDeviceInfo();
	
	public PanelSupportInfo getPanelSupportInfo();
	
	public CPUInfo getCPUInfo();
	
	public ExternalStorageInfo getExternalStorageInfo();
}