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
package com.konka.kkimplements.tv.mstar;

import java.io.IOException;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;

import com.konka.kkimplements.common.IniEditor;
import com.konka.kkimplements.common.IniReader;
import com.konka.kkinterface.tv.CommonDesk;
import com.konka.kkinterface.tv.CommonDesk.EightBarLightEnableInfo;
import com.tvos.atv.AtvManager;
import com.tvos.atv.AtvPlayer;
import com.tvos.common.ChannelManager.EnumFirstServiceInputType;
import com.tvos.common.ChannelManager.EnumFirstServiceType;
import com.tvos.common.TimerManager;
import com.tvos.common.TvManager;
import com.tvos.common.TvPlayer;
import com.tvos.common.exception.TvCommonException;
import com.tvos.common.vo.TvOsType.EnumInputSource;
import com.tvos.common.vo.VideoInfo;
import com.tvos.dtv.common.CiManager;
import com.tvos.dtv.common.DtvManager;
import com.tvos.dtv.dvb.DvbPlayer;

public class CommonDeskImpl extends BaseDeskImpl implements CommonDesk
{
    int psl[] = null;
    private static CommonDesk commonService = null;
    private EnumInputSource curSourceType = EnumInputSource.E_INPUT_SOURCE_NONE;
    private ST_VIDEO_INFO videoInfo = null;
    public static boolean bThreadIsrunning = false;
    TvPlayer mtvplayer = TvManager.getPlayerManager();
    AtvPlayer matvplayer = AtvManager.getAtvPlayerManager();
    DvbPlayer mdtvplayer = DtvManager.getDvbPlayerManager();
    TvManager  tvmanager = TvManager.getListenerHandle();
    CiManager  cimanager = null;
    TimerManager  timermanager = null;
    DeskAtvPlayerEventListener deskAtvPlayerLister;
    DeskDtvPlayerEventListener deskDtvPlayerLister;
    DeskTvPlayerEventListener deskTvPlayerLister;
    DeskTvEventListener deskTvLister;
    DeskCiEventListener deskCiLister;
    DeskTimerEventListener deskTimerLister;

    private InputSourceInfo inputSourceInfo = null;
    private SystemBoardInfo systemBoardInfo = null;
    private AudioInfo audioInfo = null;
    private BatteryInfo batteryInfo = null;
    private CiCardInfo ciCardInfo = null;
    private PipInfo pipInfo = null;
    private SystemBuildInfo systemBuildInfo = null;
    private ApplicationsInfo applicationsInfo = null;
	private LocalDimmingInfo localdimmingInfo = null;
	private EightBarLightEnableInfo eightbarlightInfo = null;
	private Panel4K2K panel4k2k = null;
	private WifiDeviceInfo wifiDeviceInfo = null;
	private PanelSupportInfo panelsupportInfo = null;
	private CPUInfo cpuInfo = null;
	private ExternalStorageInfo exStorageInfo = null;
	
	private Context context;
	
	private CommonDeskImpl(Context context)
	{
		this.context = context;
		videoInfo = new ST_VIDEO_INFO((short) 1920, (short) 1080, (short) 60, EN_SCAN_TYPE.E_PROGRESSIVE);
		try
        {
	        cimanager = DtvManager.getCiManager();
        }
        catch (TvCommonException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
        timermanager = TvManager.getTimerManager();
		InitSourceList();
		deskAtvPlayerLister = DeskAtvPlayerEventListener.getInstance();
		deskDtvPlayerLister = DeskDtvPlayerEventListener.getInstance();
		deskTvPlayerLister = DeskTvPlayerEventListener.getInstance();
		deskTvLister = DeskTvEventListener.getInstance();
		deskCiLister = DeskCiEventListener.getInstance();
		deskTimerLister = DeskTimerEventListener.getInstance();
		mdtvplayer.setOnDtvPlayerEventListener(deskDtvPlayerLister);
		matvplayer.setOnAtvPlayerEventListener(deskAtvPlayerLister);
		mtvplayer.setOnTvPlayerEventListener(deskTvPlayerLister);
		tvmanager.setOnTvEventListener(deskTvLister);
		if(cimanager != null)
		{
			cimanager.setOnCiEventListener(deskCiLister);
		}
		if(timermanager != null)
		{
			timermanager.setOnTimerEventListener(deskTimerLister);
		}
	}

	public static CommonDesk getInstance(Context context)
	{
		if (commonService == null)
		{
			commonService = new CommonDeskImpl(context);
		}
		return commonService;
	}

	@Override
	public boolean setHandler(Handler handler , int index )
	{
		Log.d("TvApp", "setHandler:" + index);
		if(index == 0)//for rootactivity
		{
			deskTvLister.attachHandler(handler);
			deskTvPlayerLister.attachHandler(handler);
		}
		else if(index == 1) // for other  activity
		{
			deskAtvPlayerLister.attachHandler(handler);
			deskDtvPlayerLister.attachHandler(handler);

		}
                else if(index == 2) // for ci
		{
			deskCiLister.attachHandler(handler);

	}
                else if(index == 3) // for timer
		{
			deskTimerLister.attachHandler(handler);

		}
		return super.setHandler(handler, index);
	}
	
	

	@Override
	public void releaseHandler(int index )
	{
		Log.d("TvApp", "releaseHandler:" + index);
		while(bThreadIsrunning)
		{
			Log.e("TvApp","tv System not stable!!!");
		}
		if(index == 0)//for rootactivity
		{
			deskTvLister.releaseHandler();
			deskTvPlayerLister.releaseHandler();
		}
		else if(index == 1) // for other activity
		{
			deskAtvPlayerLister.releaseHandler();
			deskDtvPlayerLister.releaseHandler();
		}
                else if(index == 2) // for ci
		{
			deskCiLister.releaseHandler();
		
		}
                else if(index == 3) // for timer
		{
			deskTimerLister.releaseHandler();
	
		}
		super.releaseHandler(index);
	}

	@Override
	public EnumInputSource GetCurrentInputSource()
	{
		try
		{
			curSourceType = TvManager.getCurrentInputSource();
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return curSourceType;
	}


	@Override
	public void SetInputSource(EnumInputSource st)
	{
		curSourceType = st;
		Log.e("TvApp", "SetSourceType:" + curSourceType.toString());
		try
		{
			TvManager.setInputSource(st);
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void SetInputSource(EnumInputSource st, boolean bWriteDB)
	{
		curSourceType = st;
		Log.e("TvApp", "SetSourceType:" + curSourceType.toString());
		try
		{
			if (bWriteDB == true)
			{
				TvManager.setInputSource(st);
			}
			else
			{
				TvManager.setInputSource(st, false, false, false);
			}
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void InitSourceList()
	{
		try
		{
			psl = TvManager.getSourceList();
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int[] getSourceList()
	{
		if (psl != null)
		{
			/*
			 * 
			 * psl.get(EN_INPUT_SOURCE_TYPE.MAPI_INPUT_SOURCE_ATV.ordinal()).
			 * enablePort = 1;
			 * 
			 * psl.get(EN_INPUT_SOURCE_TYPE.MAPI_INPUT_SOURCE_CVBS.ordinal()).
			 * enablePort = 1;
			 * 
			 * psl.get(EN_INPUT_SOURCE_TYPE.MAPI_INPUT_SOURCE_SVIDEO.ordinal()).
			 * enablePort = 1;
			 * 
			 * psl.get(EN_INPUT_SOURCE_TYPE.MAPI_INPUT_SOURCE_YPBPR.ordinal()).
			 * enablePort = 1;
			 * 
			 * psl.get(EN_INPUT_SOURCE_TYPE.MAPI_INPUT_SOURCE_YPBPR2.ordinal()).
			 * enablePort = 1;
			 * 
			 * psl.get(EN_INPUT_SOURCE_TYPE.MAPI_INPUT_SOURCE_VGA.ordinal()).
			 * enablePort = 1;
			 * 
			 * psl.get(EN_INPUT_SOURCE_TYPE.MAPI_INPUT_SOURCE_HDMI.ordinal()).
			 * enablePort = 1;
			 * 
			 * psl.get(EN_INPUT_SOURCE_TYPE.MAPI_INPUT_SOURCE_HDMI2.ordinal()).
			 * enablePort = 1;
			 * 
			 * psl.get(EN_INPUT_SOURCE_TYPE.MAPI_INPUT_SOURCE_DTV.ordinal()).
			 * enablePort = 1;
			 */
			return psl;
		}
		return null;
	}

	@Override
	public boolean isSignalStable()
	{
		boolean bRet = false;
		try
		{
			bRet = mtvplayer.isSignalStable();
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bRet;
	}

	@Override
	public boolean isHdmiSignalMode()
	{
		
		return mtvplayer.isHdmiMode();
	}

	@Override
	public ST_VIDEO_INFO getVideoInfo()
	{
		try
		{
			VideoInfo snvideoinfo;
			snvideoinfo = mtvplayer.getVideoInfo();
			videoInfo.s16FrameRate = (short) (snvideoinfo.frameRate);
			videoInfo.s16HResolution = (short) (snvideoinfo.hResolution);
			videoInfo.s16VResolution = (short) (snvideoinfo.vResolution);
			videoInfo.enScanType = EN_SCAN_TYPE.values()[snvideoinfo.getScanType().ordinal()];
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return videoInfo;
	}

	@Override
	public boolean setDisplayHolder(SurfaceHolder sh)
	{
		try
        {
	        mtvplayer.setDisplay(sh);
        }
        catch (TvCommonException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
		return true;
	}

	@Override
	public void printfE(String sTag, String sMessage)
	{
		if (DEBUG_FLAG)
		{
			Log.e(sTag, sMessage);
		}
	}

	@Override
	public void printfE(String sMessage)
	{
		if (DEBUG_FLAG)
		{
			Log.e("TvApp", sMessage);
		}
	}

	@Override
	public void printfV(String sTag, String sMessage)
	{
		if (DEBUG_FLAG)
		{
			Log.v(sTag, sMessage);
		}
	}

	@Override
	public void printfV(String sMessage)
	{
		if (DEBUG_FLAG)
		{
			Log.v("TvApp", sMessage);
		}
	}

	@Override
	public void printfI(String sTag, String sMessage)
	{
		if (DEBUG_FLAG)
		{
			Log.i(sTag, sMessage);
		}
	}

	@Override
	public void printfI(String sMessage)
	{
		if (DEBUG_FLAG)
		{
			Log.i("TvApp", sMessage);
		}
	}

	@Override
	public void printfW(String sTag, String sMessage)
	{
		if (DEBUG_FLAG)
		{
			Log.w(sTag, sMessage);
		}
	}

	@Override
	public void printfW(String sMessage)
	{
		if (DEBUG_FLAG)
		{
			Log.w("TvApp", sMessage);
		}
	}

	@Override
	public boolean ExecSetInputSource(EnumInputSource st)
	{
		curSourceType = st;
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				if (CommonDeskImpl.this.getHandler(1) != null)
				{
					CommonDeskImpl.this.getHandler(1).sendEmptyMessage(SETIS_START);
					SetInputSource(curSourceType);
					CommonDeskImpl.this.getHandler(1).sendEmptyMessage(SETIS_END_COMPLETE);
				}
			}
		}).start();
		return true;
	}

	private boolean bforcechangesource = false;

	private boolean execStartMsrv(EnumInputSource st, boolean forcechangesource)
	{
		curSourceType = st;
		bforcechangesource = forcechangesource;
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				bThreadIsrunning = true;
				if (bforcechangesource)
				{
					SetInputSource(curSourceType);
				}
				if (curSourceType == EnumInputSource.E_INPUT_SOURCE_ATV)
				{
					ChannelDeskImpl.getChannelMgrInstance(context).changeToFirstService(
					        EnumFirstServiceInputType.E_FIRST_SERVICE_ATV, EnumFirstServiceType.E_DEFAULT);
				}
				else if(curSourceType == EnumInputSource.E_INPUT_SOURCE_DTV)
				{
					ChannelDeskImpl.getChannelMgrInstance(context).changeToFirstService(
					        EnumFirstServiceInputType.E_FIRST_SERVICE_DTV, EnumFirstServiceType.E_DEFAULT);
				}
				bThreadIsrunning = false;
			}
		}).start();
		return true;
	}

	@Override
	public boolean startMsrv()
	{
		// TODO Auto-generated method stub
		EnumInputSource curInputSource = this.GetCurrentInputSource();
		DataBaseDeskImpl db = DataBaseDeskImpl.getDataBaseMgrInstance(context);
		curInputSource = EnumInputSource.values()[db.queryCurInputSrc()];
		//curInputSource = DataBaseDeskImpl.getDataBaseMgrInstance().getUsrData().enInputSourceType;
		Log.d("TvApp", "5.Start Msrv------------GetCurrentInputSource:" + curInputSource);
		this.execStartMsrv(curInputSource, true);
		return false;
	}

	@Override
    public boolean enterSleepMode(boolean bMode, boolean bNoSignalPwDn)
    {
	    // TODO Auto-generated method stub
		try
        {
			Log.d("TvApp", "enterSleepMode:" + bMode);
	        TvManager.enterSleepMode(bMode, bNoSignalPwDn);
        }
        catch (TvCommonException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
	    return false;
    }
	
	@Override
	public SystemBoardInfo getSystemBoardInfo()
	{
		if(systemBoardInfo == null)
		{
			systemBoardInfo = new SystemBoardInfo();
			IniEditor iniEditor = new IniEditor();
			iniEditor.loadFile("/customercfg/model/config.ini");
			systemBoardInfo.strPlatform = iniEditor.getValue("MISC_BOARD_CFG:PLATFORM", "6a801");
			systemBoardInfo.strBoard = iniEditor.getValue("MISC_BOARD_CFG:BOARD", "LEDxx");
			iniEditor.unloadFile();
			try
			{
				systemBoardInfo.strPanelName = TvManager.getSystemPanelName();
                systemBoardInfo.strVersionMboot = TvManager.getEnvironment("MBoot_VER");
                systemBoardInfo.strVersion6m30 = "No";
			}
			catch(TvCommonException e)
			{
				systemBoardInfo.strPanelName = "panel";
                systemBoardInfo.strVersionMboot = "V1.0.00";
                systemBoardInfo.strVersion6m30 = "No";
			}
			iniEditor.loadFile("/customercfg/model/version.ini");
			String strKey = systemBoardInfo.strPanelName + ":MODEL";
			systemBoardInfo.strSerial = iniEditor.getValue(strKey, "LED");
			strKey = systemBoardInfo.strPanelName + ":VERSION";
			systemBoardInfo.strVersion = iniEditor.getValue(strKey, "V1.0.00");
			strKey = systemBoardInfo.strPanelName + ":BUILD";
			systemBoardInfo.strBuild = iniEditor.getValue(strKey, "1234");
			strKey = systemBoardInfo.strPanelName + ":CODE";
			systemBoardInfo.strCode = iniEditor.getValue(strKey, "99000000");
			strKey = systemBoardInfo.strPanelName + ":DATE";
			systemBoardInfo.strDate = iniEditor.getValue(strKey, "00:00:00 2012-02-25");
			iniEditor.unloadFile();
		}
		System.out.println(systemBoardInfo.strPlatform);
		System.out.println(systemBoardInfo.strSerial);
		System.out.println(systemBoardInfo.strPanelName);
		System.out.println(systemBoardInfo.strVersion);
		System.out.println(systemBoardInfo.strBuild);
		System.out.println(systemBoardInfo.strCode);
		System.out.println(systemBoardInfo.strDate);
		
		return systemBoardInfo;
	}
	
	@Override
	public InputSourceInfo getInputSourceInfo()
	{
		if(inputSourceInfo == null)
		{
			inputSourceInfo = new InputSourceInfo();
			IniEditor iniEditor = new IniEditor();
			iniEditor.loadFile("customercfg/model/config.ini");
			inputSourceInfo.sourceCountATV = Integer.valueOf(iniEditor.getValue("MISC_SYSTEM_OPTIONS:INPUT_SOURCE_ATV_COUNT", "1"));
			inputSourceInfo.sourceCountDTV = Integer.valueOf(iniEditor.getValue("MISC_SYSTEM_OPTIONS:INPUT_SOURCE_DTV_COUNT", "1"));
			inputSourceInfo.sourceCountAV = Integer.valueOf(iniEditor.getValue("MISC_SYSTEM_OPTIONS:INPUT_SOURCE_AV_COUNT", "2"));
			inputSourceInfo.sourceCountYPbPr = Integer.valueOf(iniEditor.getValue("MISC_SYSTEM_OPTIONS:INPUT_SOURCE_YPBPR_COUNT", "1"));
			inputSourceInfo.sourceCountVGA = Integer.valueOf(iniEditor.getValue("MISC_SYSTEM_OPTIONS:INPUT_SOURCE_VGA_COUNT", "1"));
			inputSourceInfo.sourceCountHDMI = Integer.valueOf(iniEditor.getValue("MISC_SYSTEM_OPTIONS:INPUT_SOURCE_HDMI_COUNT", "3"));
			iniEditor.unloadFile();
		}
		
		return inputSourceInfo;
	}

	@Override
	public AudioInfo getAudioInfo()
	{
		if(audioInfo == null)
		{
			audioInfo = new AudioInfo();
			IniEditor iniEditor = new IniEditor();
			iniEditor.loadFile("customercfg/model/config.ini");
			audioInfo.enableSrs = Integer.valueOf(iniEditor.getValue("MISC_SYSTEM_OPTIONS:AUDIO_SRS_ENABLE", "1"));
            audioInfo.enableCoaxial = Integer.valueOf(iniEditor.getValue("MISC_SYSTEM_OPTIONS:AUDIO_COAXIAL_ENABLE", "1"));
            audioInfo.enableSoundBox = Integer.valueOf(iniEditor.getValue("MISC_SYSTEM_OPTIONS:SOUND_BOX_ENABLE", "1"));
		}

		return audioInfo;
	}
	@Override
    public CiCardInfo getCiCardInfo()
    {
        if(ciCardInfo == null)
        {
            ciCardInfo = new CiCardInfo();
            IniEditor iniEditor = new IniEditor();
            iniEditor.loadFile("customercfg/model/config.ini");
            ciCardInfo.enableCiCard = Integer.valueOf(iniEditor.getValue("MISC_SYSTEM_OPTIONS:CICARD_ENABLE", "1"));
        }


        return ciCardInfo;
    }

    @Override
    public BatteryInfo getBatteryInfo()
    {
        if(batteryInfo == null)
        {
            batteryInfo = new BatteryInfo();
            IniEditor iniEditor = new IniEditor();
            iniEditor.loadFile("customercfg/model/config.ini");
            batteryInfo.enableBattery = Integer.valueOf(iniEditor.getValue("MISC_SYSTEM_OPTIONS:BATTERY_ENABLE", "0"));
        }


        return batteryInfo;
    }

	@Override
	public PipInfo getPipInfo()
	{
		if(pipInfo == null)
		{
			pipInfo = new PipInfo();
			IniEditor iniEditor = new IniEditor();
			iniEditor.loadFile("customercfg/model/config.ini");
			pipInfo.enablePip = Integer.valueOf(iniEditor.getValue("MISC_SYSTEM_OPTIONS:PIP_ENABLE", "0"));
			pipInfo.enableDualView = Integer.valueOf(iniEditor.getValue("MISC_SYSTEM_OPTIONS:DUALVIEW_ENABLE", "0"));
		}

		return pipInfo;
	}
	
	@Override
	public SystemBuildInfo getSystemBuildInfo() {
		// TODO Auto-generated method stub
		if(systemBuildInfo == null)
		{
			systemBuildInfo = new SystemBuildInfo();
			IniReader iniReader = null;
			try {
				iniReader = new IniReader("/tvcustomer/Customer/sn_build.ini");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(iniReader != null){
				systemBuildInfo.strTVVersion = iniReader.getValue("SN_BUILD", "VERSION");
				systemBuildInfo.strBuild = iniReader.getValue("SN_BUILD", "BUILD");
				systemBuildInfo.strDate = iniReader.getValue("SN_BUILD", "DATE");
				systemBuildInfo.strDateUTC = iniReader.getValue("SN_BUILD", "DATE_UTC");
				systemBuildInfo.strHost = iniReader.getValue("SN_BUILD", "HOST");
			}
		}
		
		return systemBuildInfo;
	}

	@Override
    public ApplicationsInfo getApplicationsInfo()
    {
        // TODO Auto-generated method stub
        if(applicationsInfo == null)
        {
            applicationsInfo = new ApplicationsInfo();
            IniEditor iniEditor = new IniEditor();
            iniEditor.loadFile("customercfg/model/config.ini");
            applicationsInfo.enableCNTV = Integer.valueOf(iniEditor.getValue("APPLICATION_OPTIONS:CNTV_ENABLE", "0"));
        }

        return applicationsInfo;
    }

	@Override
	public LocalDimmingInfo getLocalDimmingInfo() {
		// TODO Auto-generated method stub
		Log.d("DEBUG", "getLocalDimmingInfo1");
		if(localdimmingInfo == null)
		{
			Log.d("DEBUG", "getLocalDimmingInfo2");
			localdimmingInfo = new LocalDimmingInfo();
			IniEditor inieditor = new IniEditor();
			inieditor.loadFile("/customercfg/panel/panel.ini");
			localdimmingInfo.localdimmingenable = Integer.parseInt(
					inieditor.getValue("LocalDIMMING:bLocalDIMMINGEnable", "0"));
//					inieditor.getValue("bLocalDIMMINGEnable", "0"));
		}
		Log.d("DEBUG", "bLocalDIMMINGEnable =" +localdimmingInfo.localdimmingenable);
		return localdimmingInfo;
	}
	
	
	@Override
	public EightBarLightEnableInfo getEgihtBarLightEnableInfo() {
		// TODO Auto-generated method stub
		Log.d("DEBUG", "getEightBarLightInfo");
		if(eightbarlightInfo == null)
		{
			eightbarlightInfo = new EightBarLightEnableInfo();
			IniEditor inieditor = new IniEditor();
			inieditor.loadFile("/customercfg/panel/panel.ini");
			eightbarlightInfo.eightbarlightenable = Integer.parseInt(
					inieditor.getValue("LocalDIMMING:bLightBarEnable", "0"));
//					inieditor.getValue("bLocalDIMMINGEnable", "0"));
		}
		Log.d("DEBUG", "bLightBarEnable =" +eightbarlightInfo.eightbarlightenable);
		return eightbarlightInfo;
	}

	@Override
	public boolean isSupport3D() {
		boolean ret = true;
		IniEditor iniEditor = new IniEditor();
		iniEditor.loadFile("/customercfg/model/config.ini");
		int flag = Integer.parseInt(iniEditor.getValue("MISC_SYSTEM_OPTIONS:IS_SUPPORT_3D", "1"));
		if(flag == 1) {
			ret = true;
		} else {
			ret = false;
		}
		return ret;
	}
	
	@Override
	public Panel4K2K getPanel4K2K() {
		// TODO Auto-generated method stub
		if(panel4k2k == null) {
			panel4k2k = new Panel4K2K();
			IniEditor iniEditor = new IniEditor();
			iniEditor.loadFile("/customercfg/panel/panel.ini");
			panel4k2k.isPanelWidth4K = Integer.valueOf(iniEditor.getValue("ursa:Toshiba4K2KEnable", "0"));
		}
		return panel4k2k;
	}
	
	@Override
	public WifiDeviceInfo getWifiDeviceInfo() {
		// TODO Auto-generated method stub
		if(wifiDeviceInfo == null) {
			wifiDeviceInfo = new WifiDeviceInfo();
			IniEditor iniEditor = new IniEditor();
			iniEditor.loadFile("customercfg/model/config.ini");
			wifiDeviceInfo.isDeviceInternal = Integer.valueOf(iniEditor.getValue("MISC_SYSTEM_OPTIONS:WIFIDEVICE_INTERNAL", "1"));
		}
		return wifiDeviceInfo;
	}
	
	@Override
	public PanelSupportInfo getPanelSupportInfo() {
		// TODO Auto-generated method stub
		if(panelsupportInfo == null) {
			panelsupportInfo = new PanelSupportInfo();
			IniEditor iniEditor = new IniEditor();
			iniEditor.loadFile("customercfg/panel/panel.ini");
			panelsupportInfo.isBLAdjustableFor3D = Integer.valueOf(iniEditor.getValue("PANEL_SUPPORT:3DBL_ADJUSTABLE", "0"));
		}
		return panelsupportInfo;
	}
	
	@Override
	public CPUInfo getCPUInfo() {
		// TODO Auto-generated method stub
		if(cpuInfo == null) {
			cpuInfo = new CPUInfo();
			IniEditor iniEditor = new IniEditor();
			iniEditor.loadFile("customercfg/model/config.ini");
			cpuInfo.iCPUCoreType = Integer.valueOf(iniEditor.getValue("MISC_SYSTEM_OPTIONS:CPUCORE_TYPE", "0"));
		}
		return cpuInfo;
	}
	
	@Override
	public ExternalStorageInfo getExternalStorageInfo() {
		// TODO Auto-generated method stub
		if(exStorageInfo == null) {
			exStorageInfo = new ExternalStorageInfo();
			IniEditor iniEditor = new IniEditor();
			iniEditor.loadFile("customercfg/model/config.ini");
			exStorageInfo.hasSDCardSlot = Integer.valueOf(iniEditor.getValue("MISC_SYSTEM_OPTIONS:SDCARD_SLOT_ENABLE", "1"));
		}
		return exStorageInfo;
	}
}