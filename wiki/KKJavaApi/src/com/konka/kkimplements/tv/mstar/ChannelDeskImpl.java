/**
 * MStar Software
 * Copyright (c) 2011 - 2012 MStar Semiconductor, Inc. All rights reserved.
 *
 * All software, firmware and related documentation herein (“MStar Software�? are 
 * intellectual property of MStar Semiconductor, Inc. (“MStar�? and protected by 
 * law, including, but not limited to, copyright law and international treaties. 
 * Any use, modification, reproduction, retransmission, or republication of all 
 * or part of MStar Software is expressly prohibited, unless prior written 
 * permission has been granted by MStar. 
 *
 * By accessing, browsing and/or using MStar Software, you acknowledge that you 
 * have read, understood, and agree, to be bound by below terms (“Terms�? and to 
 * comply with all applicable laws and regulations:
 *
 * 1. MStar shall retain any and all right, ownership and interest to MStar 
 * Software and any modification/derivatives thereof.  No right, ownership, 
 * or interest to MStar Software and any modification/derivatives thereof is 
 * transferred to you under Terms.
 *
 * 2. You understand that MStar Software might include, incorporate or be supplied 
 * together with third party’s software and the use of MStar Software may require 
 * additional licenses from third parties.  Therefore, you hereby agree it is your 
 * sole responsibility to separately obtain any and all third party right and 
 * license necessary for your use of such third party’s software. 
 *
 * 3. MStar Software and any modification/derivatives thereof shall be deemed as 
 * MStar’s confidential information and you agree to keep MStar’s confidential 
 * information in strictest confidence and not disclose to any third party.  
 *	
 * 4. MStar Software is provided on an “AS IS�?basis without warranties of any kind. 
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
 * in conjunction with your or your customer’s product (“Services�?.  You understand 
 * and agree that, except otherwise agreed by both parties in writing, Services are 
 * provided on an “AS IS�?basis and the warranty disclaimer set forth in Section 4 
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
package com.konka.kkimplements.tv.mstar;import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;import com.konka.kkinterface.tv.ChannelDesk;import com.konka.kkinterface.tv.DataBaseDesk.MEMBER_COUNTRY;import com.tvos.atv.AtvManager;import com.tvos.atv.AtvPlayer;import com.tvos.atv.AtvScanManager;import com.tvos.atv.AtvScanManager.EnumAtvManualTuneMode;import com.tvos.atv.AtvScanManager.EnumAutoScanState;import com.tvos.atv.AtvScanManager.EnumGetProgramCtrl;import com.tvos.atv.AtvScanManager.EnumGetProgramInfo;import com.tvos.atv.AtvScanManager.EnumSetProgramCtrl;import com.tvos.atv.AtvScanManager.EnumSetProgramInfo;import com.tvos.common.AudioManager;import com.tvos.common.AudioManager.EnumAtvAudioModeType;import com.tvos.common.AudioManager.EnumAtvSystemStandard;import com.tvos.common.ChannelManager;import com.tvos.common.ChannelManager.EnumFavoriteId;import com.tvos.common.ChannelManager.EnumFirstServiceInputType;import com.tvos.common.ChannelManager.EnumFirstServiceType;import com.tvos.common.ChannelManager.EnumProgramAttribute;import com.tvos.common.TvManager;import com.tvos.common.TvManager.EnumScreenMuteType;
import com.tvos.common.TvPlayer;import com.tvos.common.TvPlayer.EnumAvdVideoStandardType;import com.tvos.common.exception.TvCommonException;import com.tvos.common.vo.ProgramInfo;import com.tvos.common.vo.ProgramInfoQueryCriteria;import com.tvos.common.vo.TvOsType;
import com.tvos.common.vo.TvOsType.EnumChinaDvbcRegion;
import com.tvos.common.vo.TvOsType.EnumCountry;import com.tvos.common.vo.TvOsType.EnumInputSource;import com.tvos.common.vo.TvOsType.EnumProgramCountType;import com.tvos.common.vo.TvOsType.EnumProgramInfoType;import com.tvos.common.vo.TvOsType.EnumProgramLoopType;import com.tvos.dtv.common.DtvManager;import com.tvos.dtv.common.SubtitleManager;
import com.tvos.dtv.dvb.DvbPlayer;import com.tvos.dtv.dvb.dvbc.DvbcScanManager;import com.tvos.dtv.dvb.dvbc.DvbcScanManager.EnumCabConstelType;import com.tvos.dtv.dvb.dvbt.DvbtScanManager;import com.tvos.dtv.dvb.vo.DvbMuxInfo;
import com.tvos.dtv.vo.DtvAudioInfo;
import com.tvos.dtv.vo.DtvSubtitleInfo;
import com.tvos.dtv.vo.RfInfo;/** *  *  *  * @author caucy.niu *  *         implements of tv tuning */public class ChannelDeskImpl extends BaseDeskImpl implements ChannelDesk
{
	private int curChannelNumber = 0;
	private int prevChannelNumber = 0;
	// private int dvbt_CurRfNum = 23;
	private TV_TS_STATUS tv_tuning_status = TV_TS_STATUS.E_TS_NONE;
	DvbcScanParam dvbcsp;
	// private EN_ANTENNA_TYPE dtv_antenna_type = EN_ANTENNA_TYPE.E_ROUTE_DVBC;
	private EN_TUNING_SCAN_TYPE tuning_scan_type = EN_TUNING_SCAN_TYPE.E_SCAN_ATV;
	private static ChannelDeskImpl channelMgrImpl;
	AtvScanManager asm = AtvManager.getAtvScanManager();
	DvbcScanManager dcsm = DtvManager.getDvbcScanManager();
	DvbtScanManager dtsm = DtvManager.getDvbtScanManager();
	ChannelManager cm = TvManager.getChannelManager();
	AudioManager am = TvManager.getAudioManager();
	TvPlayer mtvplayer = TvManager.getPlayerManager();
	AtvPlayer matvplayer = AtvManager.getAtvPlayerManager();
	DvbPlayer mdtvplayer = DtvManager.getDvbPlayerManager();
	SubtitleManager sm = null;
	private short curDtvRoute = 0;

	private Context context ;
	
	public static ChannelDeskImpl getChannelMgrInstance(Context context)
	{
		if (channelMgrImpl == null)
			channelMgrImpl = new ChannelDeskImpl(context);
		return channelMgrImpl;
	}

	/**
	 * 
	 * TuningServiceImpl instruct for msg handler
	 * 
	 * @param msg
	 */
	private ChannelDeskImpl(Context context)
	{
		this.context = context;
		/**
		 * 
		 * debug only
		 */
		curChannelNumber = 0; // save channel 0 for debug
		dvbcsp = new DvbcScanParam();
		dvbcsp.u16SymbolRate = 6875;
		dvbcsp.QAM_Type = EnumCabConstelType.E_CAB_QAM64;
		dvbcsp.u32NITFrequency = 0;
		try
		{
			sm = DtvManager.getSubtitleManager();
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * exmple for myhandler in activity
	 * 
	 * private MyHandler myHandler = new MyHandler();
	 * 
	 * private TuningService ts= new TuningServiceImpl(myHandler);
	 * 
	 * 
	 * 
	 * class MyHandler extends Handler{
	 * 
	 * @Override
	 * 
	 *           public void handleMessage(Message msg) {
	 * 
	 *           Bundle b = msg.getData();
	 * 
	 *           int percent = b.getInt("percent");
	 * 
	 *           int frequencyKHz = b.getInt("frequency");
	 * 
	 *           int scannedChannelNum = b.getInt("scanNum");
	 * 
	 *           ..update views
	 */
	@Override
	public void sendAtvScaninfo()
	{
		// in target it will work in
		// msrvPlayer.setOnPlayerEventListener(deskLister);
		/*
		 * if (this.getHandler() != null) { Bundle b = new Bundle(); Message msg
		 * = getHandler().obtainMessage(); msg.what =
		 * CommonDeskImpl.getInstance().GetCurrentInputSource().ordinal();
		 * ATV_SCAN_EVENT atv_scan_info = vse.getatvscaninfo();
		 * b.putInt("percent", atv_scan_info.u8Percent); b.putInt("frequency",
		 * atv_scan_info.u32FrequencyKHz); b.putInt("scanNum",
		 * atv_scan_info.u16ScannedChannelNum); msg.setData(b);
		 * getHandler().sendMessage(msg); }
		 */
	}

	/**
	 * 
	 * sendDtvScaninfo to activity by handler
	 */
	@Override
	public void sendDtvScaninfo()
	{
		/*
		 * in moke this never use it work now in deskevent if (this.getHandler()
		 * != null) { Bundle b = new Bundle(); Message msg =
		 * getHandler().obtainMessage(); msg.what =
		 * CommonDeskImpl.getInstance().GetCurrentInputSource().ordinal();
		 * DTV_SCAN_EVENT dtv_scan_info = vse.getdtvscaninfo();
		 * b.putInt("dtvSrvCount", dtv_scan_info.u16DTVSrvCount);
		 * b.putInt("radioSrvCount", dtv_scan_info.u16RadioSrvCount);
		 * b.putInt("dataSrvCount", dtv_scan_info.u16DataSrvCount);
		 * b.putInt("percent", dtv_scan_info.u8ScanPercentageNum);
		 * b.putInt("quality", dtv_scan_info.u16SignalQuality);
		 * b.putInt("strength", dtv_scan_info.u16SignalStrength);
		 * b.putInt("scanstatus", dtv_scan_info.enScanStatus.ordinal());
		 * msg.setData(b); getHandler().sendMessage(msg); }
		 */
	}

	@Override
    public boolean atvSetManualTuningStart(int EventIntervalMs, int Frequency,
        EnumAtvManualTuneMode eMode)
	{
		// Log.d("TuningService","atvSetManualTuningStart!!");
		makeSourceAtv();
		if (CommonDeskImpl.getInstance(context).GetCurrentInputSource() != EnumInputSource.E_INPUT_SOURCE_ATV)
		{
            CommonDeskImpl.getInstance(context).SetInputSource(
                EnumInputSource.E_INPUT_SOURCE_ATV);
			try
			{
				Thread.sleep(1000);
                changeToFirstService(EnumFirstServiceInputType.E_FIRST_SERVICE_ATV,
                        EnumFirstServiceType.E_DEFAULT);
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}



		boolean result = false;
		try
		{
            result = asm
                .setManualTuningStart(EventIntervalMs, Frequency, eMode);
			if (eMode == EnumAtvManualTuneMode.E_MANUAL_TUNE_MODE_SEARCH_ONE_TO_UP)
			{
				tv_tuning_status = TV_TS_STATUS.E_TS_ATV_MANU_TUNING_RIGHT;
			}
			else if (eMode == EnumAtvManualTuneMode.E_MANUAL_TUNE_MODE_SEARCH_ONE_TO_DOWN)
			{
				tv_tuning_status = TV_TS_STATUS.E_TS_ATV_MANU_TUNING_LEFT;
			}
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//        try
//        {
//            TvManager.setVideoMute(false, EnumScreenMuteType.E_BLACK, 0,
//                EnumInputSource.E_INPUT_SOURCE_DTV);
//            TvManager.setVideoMute(false, EnumScreenMuteType.E_BLACK, 0,
//                EnumInputSource.E_INPUT_SOURCE_ATV);
//        }
//        catch (TvCommonException e1)
//        {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        }
		return result;
	}

	@Override
    public boolean atvSetAutoTuningStart(int EventIntervalMs,
        int FrequencyStart, int FrequencyEnd)
	{
		Log.d("TuningService", "atvSetAutoTuningStart");
		makeSourceAtv();
		boolean result = false;
		if (CommonDeskImpl.getInstance(context).GetCurrentInputSource() != EnumInputSource.E_INPUT_SOURCE_ATV)
		{
            CommonDeskImpl.getInstance(context).SetInputSource(
                EnumInputSource.E_INPUT_SOURCE_ATV);
			try
			{
				Thread.sleep(500);
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try
		{
            result = asm.setAutoTuningStart(EventIntervalMs, FrequencyStart,
                FrequencyEnd, EnumAutoScanState.E_NONE_NTSC_AUTO_SCAN);
			if (true)
			{
				tv_tuning_status = TV_TS_STATUS.E_TS_ATV_AUTO_TUNING;
			}
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void atvSetManualTuningEnd()
	{
		Log.d("TuningService", "atvSetManualTuningEnd");
		tv_tuning_status = TV_TS_STATUS.E_TS_NONE;
		try
		{
			asm.setManualTuningEnd();
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int atvGetCurrentFrequency()
	{
		Log.d("TuningService", "atvGetCurrentFrequency");
		int pll = 0;
		try
		{
			pll = asm.getCurrentFrequency();
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pll;// vse.patvdatabase.get(curChannelNumber).plldata * 50;// eg
		// 62.25M
		// = 625250
		// KHZ
	}

	@Override
	public boolean atvSetFrequency(int Frequency)
	{
		Log.d("TuningService", "atvSetFrequency!!!!!!! no such api ");
		return false;
	}

	@Override
	public boolean atvSetAutoTuningPause()
	{
		Log.d("TuningService", "atvSetAutoTuningPause");
		boolean result = false;
		try
		{
			result = asm.setAutoTuningPause();
			if (true)
			{
				tv_tuning_status = TV_TS_STATUS.E_TS_ATV_SCAN_PAUSING;
			}
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean atvSetAutoTuningResume()
	{
		Log.d("TuningService", "atvSetAutoTuningResume");
		boolean result = false;
		try
		{
			result = asm.setAutoTuningResume();
			if (true)
			{
				tv_tuning_status = TV_TS_STATUS.E_TS_ATV_AUTO_TUNING;
			}
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean atvSetAutoTuningEnd()
	{
		Log.d("TuningService", "atvSetAutoTuningStart");
		boolean result = false;
		try
		{
			result = asm.setAutoTuningEnd();
			if (true)
			{
				tv_tuning_status = TV_TS_STATUS.E_TS_NONE;
			}
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * 
	 * 
	 * @return TV_TS_STATUS
	 */
	@Override
	public TV_TS_STATUS GetTsStatus()
	{
		return tv_tuning_status;
	}

	@Override
	public EnumAtvSystemStandard atvGetSoundSystem()
	{
		int soundindx = 0;
		try
		{
			soundindx = am.getAtvSoundSystem().ordinal();
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EnumAtvSystemStandard.values()[soundindx];
	}

	@Override
	public boolean atvSetForceSoundSystem(EnumAtvSystemStandard eSoundSystem)
	{
		// ATV_DATABASE db = vse.patvdatabase.get(curChannelNumber);
		// db.asys = eSoundSystem;
		// int soundindx = eSoundSystem.ordinal();
		try
		{
			am.setAtvSoundSystem(eSoundSystem);
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public EnumAvdVideoStandardType atvGetVideoSystem()
	{
		int curNum = getCurrentChannelNumber();
		int res = 0;
		try
		{
            res = asm.getAtvProgramInfo(
                EnumGetProgramInfo.E_GET_VIDEO_STANDARD_OF_PROGRAM, curNum);
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EnumAvdVideoStandardType.values()[res];
	}

	@Override
	public void atvSetForceVedioSystem(EnumAvdVideoStandardType eVideoSystem)
	{
		int curNum = getCurrentChannelNumber();
		int res = eVideoSystem.ordinal();
		try
		{
			matvplayer.forceVideoStandard(eVideoSystem);
            asm.setAtvProgramInfo(
                EnumSetProgramInfo.E_SET_VIDEO_STANDARD_OF_PROGRAM, curNum, res);
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
    public int atvSetProgramInfo(EnumSetProgramInfo Cmd, int Program,
        int Param2, String str)
	{
		try
		{
			asm.setAtvProgramInfo(Cmd, Program, Param2);
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
    public int atvGetProgramInfo(EnumGetProgramInfo Cmd, int u16Program,
        int u16Param2, StringBuffer str)
	{
		int res = 0;
		try
		{
			// str.delete(0, str.length());
			// str.append("Name Null");
			res = asm.getAtvProgramInfo(Cmd, u16Program);
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@Override
    public int setProgramCtrl(EnumSetProgramCtrl Cmd, int u16Param2,
        int u16Param3, String pVoid)
	{
		try
		{
			asm.setProgramControl(Cmd, u16Param2, u16Param3);
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

    public int getProgramCtrl(EnumGetProgramCtrl Cmd, int u16Param2,
        int u16Param3, String pVoid)
	{
		int res = 0;
		try
		{
			res = asm.getProgramControl(Cmd, u16Param2, u16Param3);
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public int atvSetChannel(short ChannelNum, boolean bCheckBlock)
	{
		programSel(ChannelNum, MEMBER_SERVICETYPE.E_SERVICETYPE_ATV);
		return 0;
	}

	@Override
	public int getCurrentChannelNumber()
	{
		int res = 0;
		try
		{
			res = cm.getCurrChannelNumber();
			if (CommonDeskImpl.getInstance(context).GetCurrentInputSource() == EnumInputSource.E_INPUT_SOURCE_ATV)
			{
				if (res > max_atv_count || res < 0)
				{
					Log.d("Mapp", "getatvCurrentChannelNumber error:" + res);
					res = max_atv_count;
				}
			}
			else
			{
				if (res > max_dtv_count || res < 0)
				{
					Log.d("Mapp", "getdtvCurrentChannelNumber error:" + res);
					res = max_dtv_count;
				}
			}
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public void makeSourceDtv()
	{
		if (CommonDeskImpl.getInstance(context).GetCurrentInputSource() != EnumInputSource.E_INPUT_SOURCE_DTV)
		{
			Log.d("Tvapp", "makeSourceDtv");
            CommonDeskImpl.getInstance(context).SetInputSource(
                EnumInputSource.E_INPUT_SOURCE_DTV);
		}
	}

	@Override
	public void makeSourceAtv()
	{
		if (CommonDeskImpl.getInstance(context).GetCurrentInputSource() != EnumInputSource.E_INPUT_SOURCE_ATV)
		{
			Log.d("Tvapp", "makeSourceAtv");
            CommonDeskImpl.getInstance(context).SetInputSource(
                EnumInputSource.E_INPUT_SOURCE_ATV);
			// changeToFirstService(EnumFirstServiceInputType.E_FIRST_SERVICE_ATV,
			// EnumFirstServiceType.E_DEFAULT);
		}
	}

	@Override
	public void dtvSetAntennaType(EN_ANTENNA_TYPE type)
	{
		// dtv_antenna_type = type;
		switchMSrvDtvRouteCmd((short) (type.ordinal()));
	}

	@Override
	public EN_ANTENNA_TYPE dtvGetAntennaType()
	{
		// must read form msrv tv system not from init
		return EN_ANTENNA_TYPE.values()[getMSrvDtvRoute()];
	}

	@Override
	public boolean dtvAutoScan()
	{
		makeSourceDtv();
		try
		{
			dcsm.startAutoScan();
			if (true)
			{
				tv_tuning_status = TV_TS_STATUS.E_TS_DTV_AUTO_TUNING;
			}
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean dtvFullScan()
	{
		makeSourceDtv();
		try
		{
			dcsm.startFullScan();
			if (true)
			{
				tv_tuning_status = TV_TS_STATUS.E_TS_DTV_AUTO_TUNING;
			}
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean dtvManualScanRF(short RFNum)
	{
		// TODO Auto-generated method stub
		// dvbt_CurRfNum = RFNum;
		makeSourceDtv();
		try
		{
			mdtvplayer.setManualTuneByRf(RFNum);
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean dtvManualScanFreq(int FrequencyKHz)
	{
		// TODO Auto-generated method stub
		Log.d("TuningService", "dtvManualScanFreq:" + FrequencyKHz);
		makeSourceDtv();
		try
		{
			mdtvplayer.setManualTuneByFreq(FrequencyKHz);
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean dtvChangeManualScanRF(short RFNum)
	{
		// TODO Auto-generated method stub
		dtvManualScanRF(RFNum);
		return false;
	}

	@Override
	public boolean dtvPauseScan()
	{
		Log.d("TuningService", "dtvPauseScan");
		try
		{
			dcsm.pauseScan();
			if (true)
			{
				tv_tuning_status = TV_TS_STATUS.E_TS_DTV_SCAN_PAUSING;
			}
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean dtvResumeScan()
	{
		Log.d("TuningService", "dtvResumeScan");
		try
		{
			dcsm.resumeScan();
			if (true)
			{
				tv_tuning_status = TV_TS_STATUS.E_TS_DTV_AUTO_TUNING;
			}
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean DtvStopScan()
	{
		Log.d("TuningService", "DtvStopScan");
		try
		{
			dcsm.stopScan();
			if (true)
			{
				tv_tuning_status = TV_TS_STATUS.E_TS_NONE;
			}
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean dtvStartManualScan()
	{
		Log.d("TuningService", "dtvStartManualScan");
		makeSourceDtv();
		try
		{
			dcsm.startManualScan();
			if (true)
			{
				tv_tuning_status = TV_TS_STATUS.E_TS_DTV_MANU_TUNING;
			}
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public RfInfo dtvGetRFInfo(RfInfo.EnumInfoType enInfoType, int RFChNum)
	{
		// TODO Auto-generated method stub
		RfInfo result = null;
		try
		{
			result = mdtvplayer.getRfInfo(enInfoType, RFChNum);
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
    public boolean changeToFirstService(EnumFirstServiceInputType enInputType,
        EnumFirstServiceType enServiceType)
	{
		Log.d("TuningService", "ChangeToFirstService");
		curChannelNumber = 0;
		try
		{
			cm.changeToFirstService(enInputType, enServiceType);
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean programUp()
	{
		Log.d("TuningService", "programUp");
		try
		{
			cm.programUp(EnumProgramLoopType.E_PROG_LOOP_ALL);
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean programDown()
	{
		Log.d("TuningService", "programDown");
		try
		{
			cm.programDown(EnumProgramLoopType.E_PROG_LOOP_ALL);
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean programReturn()
	{
		Log.d("TuningService", "programReturn");
		curChannelNumber = prevChannelNumber;
		try
		{
			cm.returnToPreviousProgram();
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean programSel(int u32Number, MEMBER_SERVICETYPE u8ServiceType)
	{
        Log.d("TuningService", "programSel:" + u32Number + "u8ServiceType"
            + u8ServiceType);
		try
		{
			cm.selectProgram(u32Number, (short) u8ServiceType.ordinal(), 0x00);//
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
    public boolean getCurrentProgramSpecificInfo(
        ST_DTV_SPECIFIC_PROGINFO cResult)
	{
		//vse.getCurrentProgramSpecificInfo(cResult);
        Log.d("TuningService",
            "getCurrentProgramSpecificInfo!!!!!!! no such api ");
		return false;
	}

	@Override
    public boolean getProgramSpecificInfoByIndex(int programIndex,
        ST_DTV_SPECIFIC_PROGINFO cResult)
	{
		//vse.getProgramSpecificInfoByIndex(programIndex, cResult);
        Log.d("TuningService",
            "getCurrentProgramSpecificInfo!!!!!!! no such api ");
		return false;
	}

	@Override
	public ProgramInfo getCurrProgramInfo()
	{
		if (0 == getProgramCount(EnumProgramCountType.E_COUNT_ATV)) {
			return new ProgramInfo();
		}
		ProgramInfoQueryCriteria qc = new ProgramInfoQueryCriteria();
		return getProgramInfo(qc, EnumProgramInfoType.E_INFO_CURRENT);
	}

	@Override
	public ProgramInfo getProgramInfoByIndex(int programIndex)
	{
		ProgramInfoQueryCriteria qc = new ProgramInfoQueryCriteria();
		qc.queryIndex = programIndex;
        ProgramInfo pi = getProgramInfo(qc,
            EnumProgramInfoType.E_INFO_DATABASE_INDEX);
		return pi;
	}

	public int getPogramCount(EnumProgramCountType programCountType)
	{
		int progCnt = 0;
		try
		{
			progCnt = cm.getProgramCount(programCountType);
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.printf("\n###service type :%d,progrmaCount:%d",
		// programCountType.ordinal(), programCount);
		return progCnt;
	}

	@Override
	public void setUserScanType(EN_TUNING_SCAN_TYPE scantype)
	{
		tuning_scan_type = scantype;
	}

	@Override
	public EN_TUNING_SCAN_TYPE getUserScanType()
	{
		// TODO Auto-generated method stub
		return tuning_scan_type;
	}

	@Override
	public void setSystemCountry(MEMBER_COUNTRY mem_country)
	{
		// TODO Auto-generated method stub
		Log.d("TuningService", "mem_country :" + mem_country);
		// DataBaseDeskImpl.getDataBaseMgrInstance().getUsrData().Country =
		// mem_country;
		try
		{
        	MEMBER_COUNTRY mc = MEMBER_COUNTRY.E_UK;
        	switch(mem_country) //patch for osd only has 3 language
        	{
        		case E_CHINA:
        		case E_TAIWAN:
        			mc = mem_country;
        			break;
        		default:	
        		case E_UK:
        			mc = MEMBER_COUNTRY.E_UK;
        			break;
        	}
            mdtvplayer.setCountry(EnumCountry.values()[mc.ordinal()]);
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public MEMBER_COUNTRY getSystemCountry()
	{
		// TODO Auto-generated method stub
		return DataBaseDeskImpl.getDataBaseMgrInstance(context).getUsrData().Country;
	}

	@Override
	public boolean switchMSrvDtvRouteCmd(short u8DtvRoute)
	{
		// TODO Auto-generated method stub
		Log.d("TuningService", "switchMSrvDtvRouteCmd:" + u8DtvRoute);
		makeSourceDtv();
		try
		{
			mdtvplayer.switchDtvRoute(u8DtvRoute);
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public short getMSrvDtvRoute()
	{
		// TODO Auto-generated method stub
		// will be get form msrvPlayer
		try
		{
			curDtvRoute = (short) TvManager.getCurrentDtvRoute();
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("TuningService", "getMSrvDtvRoute:" + curDtvRoute);
		return curDtvRoute;
	}

	@Override
    public boolean dvbcsetScanParam(short u16SymbolRate,
        EnumCabConstelType enConstellation, int u32nitFrequency,
	        int u32EndFrequncy, short u16NetworkID)
	{
		// TODO Auto-generated method stub
        Log.d("TuningService", "dvbcsetScanParam:S_" + u16SymbolRate + "Q_"
            + enConstellation);
		makeSourceDtv();
        
        dvbcsp.u16SymbolRate = u16SymbolRate;
        dvbcsp.QAM_Type = enConstellation;
        dvbcsp.u32NITFrequency = u32nitFrequency;
        
		try
		{
            dcsm.setScanParam(u16SymbolRate, enConstellation, u32nitFrequency,
                u32EndFrequncy, u16NetworkID,false);
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean dvbcgetScanParam(DvbcScanParam sp)
	{
		// TODO Auto-generated method stub
		sp.u16SymbolRate = dvbcsp.u16SymbolRate;
		sp.QAM_Type = dvbcsp.QAM_Type;
		sp.u32NITFrequency = dvbcsp.u32NITFrequency;
		return false;
	}

	@Override
	public boolean dtvplayCurrentProgram()
	{
		// TODO Auto-generated method stub
		Log.d("TuningService", "dtvplayCurrentProgram");
		try
		{
			mdtvplayer.playCurrentProgram();
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public int getProgramCount(EnumProgramCountType programCountType)
	{
		// TODO Auto-generated method stub
		int pc = 0;
		try
		{
			pc = cm.getProgramCount(programCountType);
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("TuningService", "getProgramCount:" + pc);
		return pc;
	}

	@Override
    public String getProgramName(int progNo, MEMBER_SERVICETYPE progType,
        short progrID)
	{
		// TODO Auto-generated method stub
		Log.d("TuningService", "getProgramName:" + progNo + " " + progType);
		String progNm = null;
		try
		{
            progNm = cm.getProgramName(progNo, (short) progType.ordinal(),
                progrID);
			Log.d("TuningService", "getProgramName:" + progNm);
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return progNm;
	}

	@Override
    public ProgramInfo getProgramInfo(ProgramInfoQueryCriteria criteria,
        EnumProgramInfoType programInfoType)
	{
		Log.d("TuningService", "getdtvProgramInfo");
		ProgramInfo pi = null;
		try
		{
			pi = cm.getProgramInfo(criteria, programInfoType);
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return pi;
	}

	@Override
    public void setProgramAttribute(EnumProgramAttribute enpa, int programNo,
        short pt, int pd, boolean bv)
	{
		// TODO Auto-generated method stub
		try
		{
			cm.setProgramAttribute(enpa, programNo, pt, pd, bv);
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
    public boolean getProgramAttribute(EnumProgramAttribute enpa,
        int programNo, short pt, int pd, boolean bv)
	{
		// TODO Auto-generated method stub
		boolean bres = false;
		try
		{
			bres = cm.getProgramAttribute(enpa, programNo, pt, pd);
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bres;
	}

	@Override
    public void addProgramToFavorite(EnumFavoriteId favoriteId, int programNo,
        short programType, int programId)
	{
		// TODO Auto-generated method stub
		try
		{
            cm.addProgramToFavorite(favoriteId, programNo, programType,
                programId);
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
    public void deleteProgramFromFavorite(EnumFavoriteId favoriteId,
        int programNo, short programType, int programId)
	{
		// TODO Auto-generated method stub
		try
		{
            cm.deleteProgramFromFavorite(favoriteId, programNo, programType,
                programId);
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isSignalStabled()
	{
		if (mtvplayer != null)
		{
			try
			{
				return mtvplayer.isSignalStable();
			}
			catch (TvCommonException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		else
			return false;
	}

	public EnumAtvAudioModeType getSIFMtsMode()
	{
		try
		{
			return am.getAtvMtsMode();
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EnumAtvAudioModeType.E_ATV_AUDIOMODE_MONO;
	}

	public boolean isTtxChannel()
	{
		if (msrvPlayer != null)
		{
			return false;
		}
		return false;
	}

	public EnumAvdVideoStandardType getVideoStandard()
	{
		try
		{
			return mtvplayer.getVideoStandard();
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getAtvStationName(int programNo)
	{
		// TODO Auto-generated method stub
		String sn = null;
        int atvProgCount = this
            .getProgramCount(EnumProgramCountType.E_COUNT_ATV);
		if (atvProgCount == 0 || programNo >= atvProgCount)
		{
			Log.e("TvApp", "getAtvStationName null");
			return " ";
		}
		try
		{
			sn = asm.getAtvStationName(programNo);
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sn;
	}

	@Override
	public void moveProgram(int progSourcePosition, int progTargetPosition)
	{
		// TODO Auto-generated method stub
		try
		{
			cm.moveProgram(progSourcePosition, progTargetPosition);
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
    public void setProgramName(int programNum, short programType,
        String porgramName)
	{
		Log.d("TvApp", "setProgramName:" + porgramName);
		try
		{
			cm.setProgramName(programNum, programType, 0x00, porgramName);
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public DtvAudioInfo getAudioInfo()
	{
		try
		{
			return mdtvplayer.getAudioInfo();
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public TvOsType.EnumLanguage getCurrentLanguageIndex(String languageCode)
	{
		try
		{
			return TvManager.getCurrentLanguageIndex(languageCode);
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return TvOsType.EnumLanguage.E_CHINESE;
		}
	}

	@Override
	public void switchAudioTrack(int track)
	{
		try
		{
			mdtvplayer.switchAudioTrack(track);
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public DtvSubtitleInfo getSubtitleInfo()
	{
		if (sm != null)
		{
			try
			{
				return sm.getSubtitleInfo();
			}
			catch (TvCommonException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public boolean openSubtitle(int index)
	{
		if (sm != null)
		{
			try
			{
				return sm.openSubtitle(index);
			}
			catch (TvCommonException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean closeSubtitle()
	{
		if (sm != null)
		{
			try
			{
				return sm.closeSubtitle();
			}
			catch (TvCommonException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
    public DvbMuxInfo getCurrentMuxInfo()
    {
	    // TODO Auto-generated method stub
		try
        {
			return mdtvplayer.getCurrentMuxInfo();
        }
        catch (TvCommonException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
	    return null;
    }
    
    @Override
    public boolean saveAtvProgram(int currentProgramNo)
    {
        // TODO Auto-generated method stub
        try
        {
            return matvplayer.saveAtvProgram(currentProgramNo);
        }
        catch (TvCommonException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public void setChannelChangeFreezeMode(boolean freezeMode)
    {
    	try
        {
        matvplayer.setChannelChangeFreezeMode(freezeMode);
        }
        catch (TvCommonException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

	@Override
	public int readTurnChannelInterval(Context context) {
		// TODO Auto-generated method stub
		Uri uri = Uri.parse( "content://endy.eyeguard.provider.confprovider/t_system_config/3000" );
		ContentResolver contentResolver = context.getContentResolver();
		Cursor cursor = contentResolver.query( uri, 
				new String[] { "item_id","item_name","field_value1","field_value2","field_value3","field_value4","field_value5","field_value6","field_value7" }, 
				null, null, "item_id" );
		int item_id = 0;
		int turnChannelInter = 0;	
		if( cursor.moveToNext() )
		{
			turnChannelInter = cursor.getInt(cursor.getColumnIndex("field_value4"));	
		} //end while
		cursor.close();	
		return turnChannelInter;
	}

	//add by zhaotong for dtv city
	@Override
	public int[] getNitFrequencyByDtvRegion(EnumChinaDvbcRegion eRegion) throws TvCommonException
	{
		return mtvplayer.getNitFrequencyByDtvRegion(eRegion);
	}

	@Override
	public EnumChinaDvbcRegion getChinaDvbcRegion() throws TvCommonException
	{
		return mtvplayer.getChinaDvbcRegion();
	}

	@Override
	public void setChinaDvbcRegion(EnumChinaDvbcRegion eRegion) throws TvCommonException
	{
		mtvplayer.setChinaDvbcRegion(eRegion);
	}
	
	
}