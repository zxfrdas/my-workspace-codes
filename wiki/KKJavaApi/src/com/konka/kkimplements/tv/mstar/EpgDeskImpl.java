/**
 * MStar Software
 * Copyright (c) 2011 - 2012 MStar Semiconductor, Inc. All rights reserved.
 *
 * All software, firmware and related documentation herein (��MStar Software?? are 
 * intellectual property of MStar Semiconductor, Inc. (��MStar?? and protected by 
 * law, including, but not limited to, copyright law and international treaties. 
 * Any use, modification, reproduction, retransmission, or republication of all 
 * or part of MStar Software is expressly prohibited, unless prior written 
 * permission has been granted by MStar. 
 *
 * By accessing, browsing and/or using MStar Software, you acknowledge that you 
 * have read, understood, and agree, to be bound by below terms (��Terms?? and to 
 * comply with all applicable laws and regulations:
 *
 * 1. MStar shall retain any and all right, ownership and interest to MStar 
 * Software and any modification/derivatives thereof.  No right, ownership, 
 * or interest to MStar Software and any modification/derivatives thereof is 
 * transferred to you under Terms.
 *
 * 2. You understand that MStar Software might include, incorporate or be supplied 
 * together with third party��s software and the use of MStar Software may require 
 * additional licenses from third parties.  Therefore, you hereby agree it is your 
 * sole responsibility to separately obtain any and all third party right and 
 * license necessary for your use of such third party��s software. 
 *
 * 3. MStar Software and any modification/derivatives thereof shall be deemed as 
 * MStar��s confidential information and you agree to keep MStar��s confidential 
 * information in strictest confidence and not disclose to any third party.  
 *	
 * 4. MStar Software is provided on an ��AS IS??basis without warranties of any kind. 
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
 * in conjunction with your or your customer��s product (��Services??.  You understand 
 * and agree that, except otherwise agreed by both parties in writing, Services are 
 * provided on an ��AS IS??basis and the warranty disclaimer set forth in Section 4 
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
package com.konka.kkimplements.tv.mstar;import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.text.format.Time;
import android.util.Log;

import com.konka.kkimplements.tv.vo.KonkaEventInfo;
import com.konka.kkimplements.tv.vo.KonkaProgCount;
import com.konka.kkimplements.tv.vo.KonkaProgInfo;
import com.konka.kkinterface.tv.CommonDesk;
import com.konka.kkinterface.tv.DtvInterface.EPG_SCHEDULE_EVENT_INFO;
import com.konka.kkinterface.tv.DtvInterface.RESULT_ADDEVENT;
import com.konka.kkinterface.tv.EpgDesk;
import com.tvos.common.ChannelManager;
import com.tvos.common.TimerManager;
import com.tvos.common.TimerManager.EnumEpgTimerActType;
import com.tvos.common.TvManager;
import com.tvos.common.exception.TvCommonException;
import com.tvos.common.vo.EpgEventTimerInfo;
import com.tvos.common.vo.PresentFollowingEventInfo;
import com.tvos.common.vo.ProgramInfo;
import com.tvos.common.vo.ProgramInfoQueryCriteria;
import com.tvos.common.vo.TvOsType;
import com.tvos.common.vo.TvOsType.EnumEpgTimerCheck;
import com.tvos.common.vo.TvOsType.EnumInputSource;
import com.tvos.common.vo.TvOsType.EnumProgramCountType;
import com.tvos.common.vo.TvOsType.EnumProgramInfoType;
import com.tvos.common.vo.TvOsType.EnumScalerWindow;
import com.tvos.common.vo.TvOsType.EnumServiceType;
import com.tvos.common.vo.VideoWindowType;
import com.tvos.dtv.common.DtvManager;
import com.tvos.dtv.common.EpgManager;
import com.tvos.dtv.vo.DtvEitInfo;
import com.tvos.dtv.vo.DtvType.EnumEpgDescriptionType;
import com.tvos.dtv.vo.EpgCridEventInfo;
import com.tvos.dtv.vo.EpgCridStatus;
import com.tvos.dtv.vo.EpgEventInfo;
import com.tvos.dtv.vo.EpgFirstMatchHdCast;
import com.tvos.dtv.vo.EpgHdSimulcast;
import com.tvos.dtv.vo.EpgTrailerLinkInfo;
import com.tvos.dtv.vo.NvodEventInfo;


public class EpgDeskImpl extends BaseDeskImpl implements EpgDesk
{
	private static final String 	TAG = "EpgDeskImpl";
	
	private static EpgDeskImpl epgMgrImpl = null;
	private CommonDesk com = null;
	private EpgManager epgMgr = null;
	
	private TimerManager tm = TvManager.getTimerManager();
	private ChannelManager channelManager = TvManager.getChannelManager();
	private EnumServiceType _mCurServiceType = EnumServiceType.E_SERVICETYPE_INVALID;
	private ArrayList<KonkaProgInfo> _mProgList = null;
	private KonkaProgCount	_mProgCount	= null;
	private ArrayList<KonkaEventInfo> _mEventList = null;
	private KonkaProgInfo _mCurKonkaProgInfo	= null;
	private	int			_mEventDiffTime	= 0;
	private final int TIME_OFFSET	=	0 * 3600;
	private Context context ;
	private EpgDeskImpl(Context context)
	{
		this.context = context;
		com = CommonDeskImpl.getInstance(context);
		com.printfI("TvService", "EpgManagerImpl constructor!!");
		try
		{
			epgMgr = DtvManager.getEpgManager();
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static EpgDeskImpl getEpgMgrInstance(Context context)
	{
		if (epgMgrImpl == null)
			epgMgrImpl = new EpgDeskImpl(context);
		return epgMgrImpl;
	}

	/**
	 * Get total event info from u32BaseTime
	 * 
	 * @param serviceType
	 *            short Program service type. (DTV, radio, data service)
	 * @param serviceNo
	 *            int Program service number.
	 * @param baseTime
	 *            Time base time based on UTC time
	 * @param maxEventInfoCount
	 *            int maximum object size in return arraylist
	 * @return ArrayList<EpgEventInfo>
	 * @throws TvCommonException
	 */
	public ArrayList<EpgEventInfo> getEventInfo(short serviceType, int serviceNo, Time baseTime, int maxEventInfoCount)
	        throws TvCommonException
	{
		return epgMgr.getEventInfo(serviceType, serviceNo, baseTime, maxEventInfoCount);
	}

	/**
	 * Get total event counts from base time
	 * 
	 * @param serviceType
	 *            short Program service type. (DTV, radio, data service)
	 * @param serviceNo
	 *            int Program service number.
	 * @param baseTime
	 *            Time UTC time
	 * @return int event count from basetime
	 */
	public int getEventCount(short serviceType, int serviceNo, Time baseTime)
	{
		try
		{
			return epgMgr.getEventCount(serviceType, serviceNo, baseTime);
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * Get program EPG event information by time.
	 * 
	 * @param serviceType
	 *            short Program service type. (DTV, radio, data service)
	 * @param serviceNo
	 *            int Program service number.
	 * @param baseTime
	 *            Time base time based on UTC time
	 * @return EpgEventInfo
	 * @throws TvCommonException
	 */
	public EpgEventInfo getEventInfoByTime(short serviceType, int serviceNo, Time baseTime) throws TvCommonException
	{
		return epgMgr.getEventInfoByTime(serviceType, serviceNo, baseTime);
	}

	/**
	 * Get program EPG event information by event ID.
	 * 
	 * @param serviceType
	 *            short Program service type. (DTV, radio, data service)
	 * @param serviceNo
	 *            int Program service number.
	 * @param eventID
	 *            short
	 * @return EpgEventInfo
	 * @throws TvCommonException
	 */
	public EpgEventInfo getEventInfoById(short serviceType, int serviceNo, short eventID) throws TvCommonException
	{
		return epgMgr.getEventInfoById(serviceType, serviceNo, eventID);
	}

	/**
	 * Get program EPG event descriptor.
	 * 
	 * @param serviceType
	 *            short Program service type. (DTV, radio, data service)
	 * @param serviceNumber
	 *            int Program service number.
	 * @param baseTime
	 *            Time UTC time
	 * @param epgDescriptionType
	 *            EN_EPG_DESCRIPTION_TYPE
	 * @return String
	 * @throws TvCommonException
	 */
	public String getEventDescriptor(short serviceType, int serviceNumber, Time baseTime,
	        EnumEpgDescriptionType epgDescriptionType) throws TvCommonException
	{
		return epgMgr.getEventDescriptor(serviceType, serviceNumber, baseTime, epgDescriptionType);
	}

	/**
	 * Get EPG HD simulcast
	 * 
	 * @param serviceType
	 *            short Program service type. (DTV, radio, data service)
	 * @param serviceNo
	 *            int Program service number.
	 * @param baseTime
	 *            Time UTC time
	 * @param maxCount
	 *            short maximum count want to get
	 * @return EpgHdSimulcast
	 * @throws TvCommonException
	 */
	public ArrayList<EpgHdSimulcast> getEventHdSimulcast(short serviceType, int serviceNo, Time baseTime, short maxCount)
	        throws TvCommonException
	{
		return epgMgr.getEventHdSimulcast(serviceType, serviceNo, baseTime, maxCount);
	}

	/**
	 * Reset the service priority to default for the case EPG database is full.
	 * 
	 * @throws TvCommonException
	 */
	public void resetEpgProgramPriority() throws TvCommonException
	{
		epgMgr.resetEpgProgramPriority();
	}

	/**
	 * Get CRID status. Series / split / alternate / recommend.
	 * 
	 * @param serviceType
	 *            short Program service type. (DTV, radio, data service)
	 * @param serviceNumber
	 *            int Program service number.
	 * @param eventStartTime
	 *            Time Target event start time base on UTC time.
	 * @return EpgCridStatus EPG CRID Status
	 * @throws TvCommonException
	 */
	public EpgCridStatus getCridStatus(short serviceType, int serviceNumber, Time eventStartTime)
	        throws TvCommonException
	{
		return epgMgr.getCridStatus(serviceType, serviceNumber, eventStartTime);
	}

	/**
	 * Get CRID series list.
	 * 
	 * @param serviceType
	 *            short Program service type. (DTV, radio, data service)
	 * @param serviceNumber
	 *            int Program service number.
	 * @param eventStartTime
	 *            Time Target event start time base on UTC time.
	 * @return ArrayList<EpgCridEventInfo> ArrayList of EpgCridEventInfo
	 * @throws TvCommonException
	 */
	public ArrayList<EpgCridEventInfo> getCridSeriesList(short serviceType, int serviceNumber, Time eventStartTime)
	        throws TvCommonException
	{
		return epgMgr.getCridSeriesList(serviceType, serviceNumber, eventStartTime);
	}

	/**
	 * Get CRID split list.
	 * 
	 * @param serviceType
	 *            short Program service type. (DTV, radio, data service)
	 * @param serviceNumber
	 *            int Program service number.
	 * @param eventStartTime
	 *            Time Target event start time base on UTC time.
	 * @return ArrayList<EpgCridEventInfo> ArrayList of EpgCridEventInfo
	 * @throws TvCommonException
	 */
	public ArrayList<EpgCridEventInfo> getCridSplitList(short serviceType, int serviceNumber, Time eventStartTime)
	        throws TvCommonException
	{
		return epgMgr.getCridSplitList(serviceType, serviceNumber, eventStartTime);
	}

	/**
	 * Get CRID alternate list.
	 * 
	 * @param serviceType
	 *            short Program service type. (DTV, radio, data service)
	 * @param serviceNumber
	 *            int Program service number.
	 * @param eventStartTime
	 *            Time event start time based on UTC time
	 * @return ArrayList<EpgCridEventInfo> vector of ST_DTV_EPG_CRID_EVENT_INFO
	 * @throws TvCommonException
	 */
	public ArrayList<EpgCridEventInfo> getCridAlternateList(short serviceType, int serviceNumber, Time eventStartTime)
	        throws TvCommonException
	{
		return epgMgr.getCridAlternateList(serviceType, serviceNumber, eventStartTime);
	}

	/**
	 * Get trailer link
	 * 
	 * @return ArrayList<EpgTrailerLinkInfo> arraylist of EPG trailer link
	 *         infomation
	 * @throws TvCommonException
	 */
	public ArrayList<EpgTrailerLinkInfo> getRctTrailerLink() throws TvCommonException
	{
		return epgMgr.getRctTrailerLink();
	}

	/**
	 * Get event info(s) by trailer link
	 * 
	 * @param epgTrailerLinkInfo
	 *            EpgTrailerLinkInfo
	 * @return ArrayList<EpgCridEventInfo> arraylist of EpgCridEventInfo
	 * @throws TvCommonException
	 */
	public ArrayList<EpgCridEventInfo> getEventInfoByRctLink(EpgTrailerLinkInfo epgTrailerLinkInfo)
	        throws TvCommonException
	{
		return epgMgr.getEventInfoByRctLink(epgTrailerLinkInfo);
	}

	/**
	 * Eable EPG Barker channel work.
	 * 
	 * @return boolean
	 * @throws TvCommonException
	 */
	public boolean enableEpgBarkerChannel() throws TvCommonException
	{
		return epgMgr.enableEpgBarkerChannel();
	}

	/**
	 * Disable EPG Barker channel work.
	 * 
	 * @return boolean
	 * @throws TvCommonException
	 */
	public boolean disableEpgBarkerChannel() throws TvCommonException
	{
		return epgMgr.disableEpgBarkerChannel();
	}

	/**
	 * Get offset time for event time between time of offset time change case
	 * 
	 * @param utcTime
	 *            Time UTC time
	 * @param isStartTime
	 *            boolean TRUE: event start time, FALSE: event end time
	 * @return int
	 * @throws TvCommonException
	 */
	public int getEpgEventOffsetTime(Time utcTime, boolean isStartTime) throws TvCommonException
	{
		return epgMgr.getEpgEventOffsetTime(utcTime, isStartTime);
	}

	/**
	 * Get present/following event information of certain service
	 * 
	 * @param serviceType
	 *            short Program service type. (DTV, radio, data service)
	 * @param serviceNo
	 *            int Program service number.
	 * @param present
	 *            boolean
	 * @param eventComponentInfo
	 *            DtvEventComponentInfo
	 * @param descriptionType
	 *            EnumEpgDescriptionType
	 * @return EpgEventInfo
	 * @throws TvCommonException
	 */
	public PresentFollowingEventInfo getPresentFollowingEventInfo(short serviceType, int serviceNo, boolean present,
	        EnumEpgDescriptionType descriptionType) 
	{
		try
	{
		return epgMgr.getPresentFollowingEventInfo(serviceType, serviceNo, present, descriptionType);
	}
        catch (TvCommonException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	        Log.d("TvApp","!!!getPresentFollowingEventInfo");
	        return null;
        }
	}

	/**
	 * Get first matched Epg HD simulcast.
	 * 
	 * @param serviceType
	 *            short Program service type. (DTV, radio, data service)
	 * @param serviceNo
	 *            int Program service number.
	 * @param baseTime
	 *            Time Event start time based on UTC time.
	 * @return Epg1stMatchHDSimulcastVO including if resolvable,target program
	 *         name, target event
	 * @throws TvCommonException
	 */
	public EpgFirstMatchHdCast getEvent1stMatchHdSimulcast(short serviceType, int serviceNo, Time baseTime)
	        throws TvCommonException
	{
		return epgMgr.getEvent1stMatchHdSimulcast(serviceType, serviceNo, baseTime);
	}

	/**
	 * Get first matched EPG HD broadcast later.
	 * 
	 * @param serviceType
	 *            short Program service type. (DTV, radio, data service)
	 * @param serviceNo
	 *            int Program service number.
	 * @param baseTime
	 *            Time Event start time base on UTC time.
	 * @return Epg1stMatchHDSimulcastVO including if resolvable,target program
	 *         name, target event
	 * @throws TvCommonException
	 */
	public EpgFirstMatchHdCast getEvent1stMatchHdBroadcast(short serviceType, int serviceNo, Time baseTime)
	        throws TvCommonException
	{
		return epgMgr.getEvent1stMatchHdBroadcast(serviceType, serviceNo, baseTime);
	}

	/**
	 * Get EIT information.
	 * 
	 * @return DtvEitInfo The EIT information.
	 * @throws TvCommonException
	 */
	public DtvEitInfo getEitInfo(boolean bPresent) throws TvCommonException
	{
		return epgMgr.getEitInfo(bPresent);
	}

	/**
	 * Setting the service priority for the case EPG database is full. the EIT
	 * buffer assigned to the service with lowest priority will be replaced.
	 * CM/UI should call this function whenever channel change.
	 * 
	 * @param programIndex
	 *            int service position or program index.
	 * @throws TvCommonException
	 */
	public void setEpgProgramPriority(int programIndex) throws TvCommonException
	{
		epgMgr.setEpgProgramPriority(programIndex);
	}

	/**
	 * Setting the service priority for the case EPG database is full. the EIT
	 * buffer assigned to the service with lowest priority will be replaced.
	 * CM/UI should call this function whenever channel change.
	 * 
	 * @param serviceType
	 *            short Program service type. (DTV, radio, data service)
	 * @param serviceNo
	 *            int Program service number.
	 * @throws TvCommonException
	 */
	public void setEpgProgramPriority(short serviceType, int serviceNo) throws TvCommonException
	{
		epgMgr.setEpgProgramPriority(serviceType, serviceNo);
	}

	/**
	 * Get NVOD time shift event counts belong to the input NVOD reference
	 * service
	 * 
	 * @param serviceType
	 *            short nvod program service type.
	 * @param serviceNumber
	 *            int nvod program service number.
	 * @return int the found events info count
	 * @throws TvCommonException
	 */
	public int getNvodTimeShiftEventCount(short serviceType, int serviceNumber) throws TvCommonException
	{
		return epgMgr.getNvodTimeShiftEventCount(serviceType, serviceNumber);
	}

	/**
	 * Get NVOD time shift event info service
	 * 
	 * @param serviceType
	 *            short nvod program service type.
	 * @param serviceNumber
	 *            int nvod program service number.
	 * @return int the found events info count
	 * @throws TvCommonException
	 */
	public ArrayList<NvodEventInfo> getNvodTimeShiftEventInfo(short serviceType, int serviceNumber, int maxEventNum,
	        EnumEpgDescriptionType eEpgDescritionType) throws TvCommonException
	{
		return epgMgr.getNvodTimeShiftEventInfo(serviceType, serviceNumber, maxEventNum, eEpgDescritionType);
	}

	/**
	 * Add epg event
	 * 
	 * @param vo
	 *            EpgEventTimerInfo
	 * 
	 * @return EnumEpgTimerCheck
	 * 
	 * @throws TvCommonException
	 */
	public TvOsType.EnumEpgTimerCheck addEpgEvent(EpgEventTimerInfo vo) throws TvCommonException
	{
		return tm.addEpgEvent(vo);
	}

	/**
	 * get epg timer event by index
	 * 
	 * @param index
	 *            int
	 * 
	 * @return EpgEventTimerInfo timer event info
	 * 
	 * @throws TvCommonException
	 */
	public EpgEventTimerInfo getEpgTimerEventByIndex(int index) throws TvCommonException
	{
		return tm.getEpgTimerEventByIndex(index);
	}

	/**
	 * get epg timer event count
	 * 
	 * @return int timer event count
	 * 
	 * @throws TvCommonException
	 */
	public int getEpgTimerEventCount() throws TvCommonException
	{
		return tm.getEpgTimerEventCount();
	}

	/**
	 * is Epg TimerSetting Valid
	 * 
	 * @param timerInfoVo
	 *            EpgEventTimerInfo.
	 * 
	 * @return TvOsType.EnumEpgTimerCheck
	 * 
	 * @throws TvCommonException
	 */
	public TvOsType.EnumEpgTimerCheck isEpgTimerSettingValid(EpgEventTimerInfo timerInfoVo) throws TvCommonException
	{
		return tm.isEpgTimerSettingValid(timerInfoVo);
	}

	/**
	 * del All Epg Event
	 * 
	 * @return boolean
	 * 
	 * @throws TvCommonException
	 */
	public boolean delAllEpgEvent() throws TvCommonException
	{
		return tm.delAllEpgEvent();
	}

	/**
	 * is Epg TimerSetting Valid
	 * 
	 * @param epgEvent
	 *            int.
	 * 
	 * @return boolean
	 * 
	 * @throws TvCommonException
	 */
	public boolean delEpgEvent(int epgEvent) throws TvCommonException
	{
		return tm.delEpgEvent(epgEvent);
	}

	/**
	 * delete Past Epg Timer
	 * 
	 * @return boolean
	 * 
	 * @throws TvCommonException
	 */
	public boolean deletePastEpgTimer() throws TvCommonException
	{
		return tm.deletePastEpgTimer();
	}

	/**
	 * exec Epg Timer Action
	 * 
	 * @return boolean
	 * 
	 * @throws TvCommonException
	 */
	public boolean execEpgTimerAction() throws TvCommonException
	{
		return tm.execEpgTimerAction();
	}

	/**
     * Reconfig EPG timer list & setting monitors
     *
     * @param timeActing int (the time before valid list items.)
     * @param checkEndTime boolean (delete according to end time.)
     * @throws TvCommonException
     */
    public void cancelEpgTimerEvent(int timeActing,
        boolean checkEndTime) throws TvCommonException
    {
        tm.cancelEpgTimerEvent(timeActing, checkEndTime);
    }
	/**
	 * Get timer recording program.
	 * 
	 * @return EpgEventTimerInfo The current timer info
	 * @throws TvCommonException
	 */
	public EpgEventTimerInfo getEpgTimerRecordingProgram() throws TvCommonException
	{
		return tm.getEpgTimerRecordingProgram();
	}
	
	/**
	 * Gets timezone state
	 * 
	 * @return TvOsType.EnumTimeZone (Timezone state)
	 * @throws TvCommonException
	 */
	public TvOsType.EnumTimeZone getTimeZone() throws TvCommonException
	{
		return tm.getTimeZone();
	}
	
	/**
	 * Gets current channel program info
	 * 
	 * @return ProgramInfo (current program info)
	 */
	public ProgramInfo getCurProgramInfo() 
	{
		com.printfI(TAG, "getCurProgramInfo");
		
		ProgramInfoQueryCriteria qc = new ProgramInfoQueryCriteria();
		ProgramInfo programInfo = null;//new ProgramInfo();
		
		try
		{
			programInfo = channelManager.getProgramInfo(qc, EnumProgramInfoType.E_INFO_CURRENT);
			_mCurServiceType = EnumServiceType.values()[programInfo.serviceType];
			com.printfI(TAG, "Cur ProgNo is -------->" + programInfo.number + "ProgramInfo ServiceType is" + programInfo.serviceType);
			com.printfI(TAG, "CurServiceType is -------->" + _mCurServiceType.ordinal());
		}
		catch (TvCommonException e)
		{
			e.printStackTrace();
		}
		
		return programInfo;
	}
	
	/**
     * Gets current input source
     * 
     * @return EnumInputSource (current input source)
     */
	@Override
	public EnumInputSource getCurInputSource() 
	{
		// TODO Auto-generated method stub
		EnumInputSource eCurSource = EnumInputSource.E_INPUT_SOURCE_NONE;
		
		try
		{
			eCurSource = TvManager.getCurrentInputSource();
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return eCurSource;
	}

	/**
	 * get current konka program info
	 * 
	 * @return KonkaProgInfo
	 * 
	 */
	@Override
	public KonkaProgInfo getCurKonkaProgInfo() 
	{
		// TODO Auto-generated method stub
		ProgramInfo info = getCurProgramInfo();
		KonkaProgInfo kpi = null;
		
		if (info != null)
		{
			kpi = new KonkaProgInfo();
			kpi._mProgDbId = info.progId;
			kpi._mProgIndex = info.queryIndex;
			kpi._mProgName = info.serviceName;
			com.printfI(TAG, "initPosInChannelList Kpi ProgName is ------>" + kpi._mProgName);
			kpi._mProgNo = info.number;
			com.printfI(TAG, "initPosInChannelList Kpi ProgNumber is ------>" + kpi._mProgNo);
			kpi._mProgType = info.serviceType;
		}
		_mCurKonkaProgInfo = kpi;
		
		return kpi;
	}

	/**
     * Sets Dtv input source
     * 
     * void
     */
	@Override
	public void setDtvInputSource() 
	{
		// TODO Auto-generated method stub
		try
		{
			if(EnumInputSource.E_INPUT_SOURCE_DTV.ordinal() != getCurInputSource().ordinal())
				TvManager.setInputSource(EnumInputSource.E_INPUT_SOURCE_DTV);
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
     * Get Dtv program count
     * 
     * @return KonkaProgCount //include Dtv radio Date and (Dtv+radio+Date)
     */
	@Override
	public KonkaProgCount getDtvProgCount() 
	{
		int iDtvOnly = 0;
		int iRadio = 0;
		int iData = 0;
		int iDtvAll = 0;
		
		try
		{
			iDtvAll = channelManager.getProgramCount(EnumProgramCountType.E_COUNT_DTV);
			
			if (iDtvAll == 0)
			{
				iDtvOnly = 0;
				iRadio = 0;
				iData = 0;
			}
			else
			{
				iDtvOnly = channelManager.getProgramCount(EnumProgramCountType.E_COUNT_DTV_TV);
				iRadio = channelManager.getProgramCount(EnumProgramCountType.E_COUNT_DTV_RADIO);
				iData = channelManager.getProgramCount(EnumProgramCountType.E_COUNT_DTV_DATA);
			}
		}
		catch (TvCommonException e)
		{
			e.printStackTrace();
		}
		_mProgCount = new KonkaProgCount(iDtvOnly, iRadio, iData, iDtvAll);
		
		return _mProgCount;
	}

	/**
     * get program list 
     * 
     * @param EnumProgramCountType
     * 
     * @return ArrayList<KonkaProgInfo>
     */
	@Override
	public synchronized ArrayList<KonkaProgInfo> getProgListByServiceType(
			EnumServiceType eServiceType) 
	{
		// TODO Auto-generated method stub
		getCurProgramInfo();
		com.printfI(TAG, "End getCurProgramInfo()" );
		
		if (_mProgCount.isNoProgInServiceType(EnumProgramCountType.E_COUNT_DTV))
		{
			com.printfI(TAG, "NoProgInServiceType");
			return null;
		}
		else
		{
			com.printfI(TAG, "HasProgInServiceType");
			
			if (isProgCountEqualToZeroByServiceType(eServiceType))
			{
				com.printfI(TAG, "ProgCount is Zero in ------>" + eServiceType.ordinal());
				return null;
			}
			else
			{
				int iProgCount = _mProgCount.getProgCountByServiceType(eServiceType);
				
				com.printfI(TAG, "ProgCount is ------>" + iProgCount);
				
				ProgramInfo	tvProgInfo = new ProgramInfo();
				ProgramInfoQueryCriteria piQc = new ProgramInfoQueryCriteria();
				
				if (_mProgList == null)
				{
					_mProgList = new ArrayList<KonkaProgInfo>();
					com.printfI(TAG, "ProgList is ------> a new one");
				}
				else
				{
					_mProgList.clear();
					com.printfI(TAG, "ProgList is ------> clear");
				}
				
				com.printfI(TAG, "the type count is " + String.valueOf(iProgCount));
				
				for (int i = 0; i < iProgCount; i++)
				{
					if(eServiceType == EnumServiceType.E_SERVICETYPE_RADIO)
					{
						piQc.queryIndex = i + _mProgCount.getProgCountByServiceType(EnumServiceType.E_SERVICETYPE_DTV);
					}
					else
					{
						piQc.queryIndex = i;
					}
					piQc.setServiceType(eServiceType);
					
					tvProgInfo = getProgramInfoByIndex(piQc);
					
					if(tvProgInfo == null)
					{
						com.printfI(TAG, "TvProgInfo is ------> null");
					}else
					{
						com.printfI(TAG, "TvProgInfo is ------>" + tvProgInfo.toString());
					}
					
					if (tvProgInfo != null)
					{
						com.printfI(TAG, "TvProgInfo is ------> not null");
						
						KonkaProgInfo kkProgInfo = new KonkaProgInfo();
						
						kkProgInfo._mProgDbId = tvProgInfo.progId;
						kkProgInfo._mProgIndex = tvProgInfo.queryIndex;
						kkProgInfo._mProgName = tvProgInfo.serviceName;
						kkProgInfo._mProgNo = tvProgInfo.number;
						kkProgInfo._mProgType = tvProgInfo.serviceType;
						
						com.printfI(TAG, "name[" + kkProgInfo._mProgName + "]no["
								+ String.valueOf(kkProgInfo._mProgNo) + "]");
						
//						if (tvProgInfo.isDelete || tvProgInfo.isHide 
//								|| tvProgInfo.isLock || tvProgInfo.isSkip)
//						{
//							continue;
//						}
//						else
//						{
							_mProgList.add(kkProgInfo);
							
							com.printfI(TAG, "_mProgList.add(kkProgInfo)");
//						}
					}
				}
				
				return _mProgList;
			}
		}
	}
	
	
	/**
     * get program list 
     * 
     * @param EnumServiceType(short)
     * 
     * @return ArrayList<KonkaProgInfo>
     */
	@Override
	public ArrayList<KonkaProgInfo> getProgListByServiceType(short sServiceType) 
	{
		// TODO Auto-generated method stub
		
		EnumServiceType eType = getServiceTypeByShort(sServiceType);
		
		if (eType.ordinal() == EnumServiceType.E_SERVICETYPE_INVALID.ordinal())
		{
			return null;
		}
		else
		{
			return getProgListByServiceType(eType);
		}
	}

	/**
     * get service type(only use in DTV source)
     * 
     * @param EnumServiceType(short)
     * 
     * @return EnumServiceType
     */
	private EnumServiceType getServiceTypeByShort(short sServiceType)
	{
		if (sServiceType < EnumServiceType.E_SERVICETYPE_DTV.ordinal() 
				|| sServiceType > EnumServiceType.E_SERVICETYPE_DATA.ordinal())
		{
			return EnumServiceType.E_SERVICETYPE_INVALID;
		}
		else
		{
			EnumServiceType eType = EnumServiceType.values()[sServiceType];
			
			return eType;
		}
	}
	
	/**
	 * @param EnumServiceType
	 * 
	 * @return boolean
	 */
	private boolean isProgCountEqualToZeroByServiceType(EnumServiceType eServiceType)
	{
		boolean bIsZero = false;
		
		if (_mProgCount == null)
		{
			getDtvProgCount();
		}
		
		switch (eServiceType)
		{
		case E_SERVICETYPE_DTV:
			{
				bIsZero = _mProgCount.isNoProgInServiceType(
						EnumProgramCountType.E_COUNT_DTV_TV);
			}
			break;
		case E_SERVICETYPE_RADIO:
			{
				bIsZero = _mProgCount.isNoProgInServiceType(
						EnumProgramCountType.E_COUNT_DTV_RADIO);
			}
			break;
		case E_SERVICETYPE_DATA:
			{
				bIsZero = _mProgCount.isNoProgInServiceType(
						EnumProgramCountType.E_COUNT_DTV_DATA);
			}
			break;
		default:
			{
				bIsZero = true;
			}
			break;
		}
		return bIsZero;
	}
	
	/**
	 * get program info
	 * 
	 * @param program number
	 * 
	 * @return ProgramInfo
	 */
	private ProgramInfo getProgramInfoByNumber(ProgramInfoQueryCriteria piQC)
	{
		ProgramInfo pi = null;
		
		try
		{
			pi = channelManager.getProgramInfo(piQC, EnumProgramInfoType.E_INFO_PROGRAM_NUMBER);
			if(pi == null)
			{
				com.printfI(TAG, "TvProgInfo is ------> null");
			}
			else
			{
				com.printfI(TAG, "TvProgInfo is ------>" + pi.serviceName);
			}
		}
		catch (TvCommonException e)
		{
			e.printStackTrace();
			com.printfI(TAG, "getProgramInfoByNumber =========");
		}
		
		return pi;
	}
	
	/**
	 * get program info
	 * 
	 * @param program number
	 * 
	 * @return ProgramInfo
	 */
	private ProgramInfo getProgramInfoByIndex(ProgramInfoQueryCriteria piQC)
	{
		ProgramInfo pi = null;
		
		try
		{
			pi = channelManager.getProgramInfo(piQC, EnumProgramInfoType.E_INFO_DATABASE_INDEX);
		}
		catch (TvCommonException e)
		{
			e.printStackTrace();
			com.printfI(TAG, "getProgramInfoByNumber =========");
		}
		
		return pi;
	}

	/**
	 * get event list
	 * 
	 * @return ArrayList<KonkaEventList>
	 * 
	 */
	@Override
	public synchronized ArrayList<KonkaEventInfo> getEventList(short sServiceType, int iProgNo)
	{
		// TODO Auto-generated method stub
		Time baseTime = new Time();
		Time zeroTime = new Time();
		baseTime.setToNow();
		
		zeroTime.set(0);

		if (getServiceTypeByShort(sServiceType).ordinal() == EnumServiceType.E_SERVICETYPE_INVALID.ordinal())
		{
			return null;
		}

		if (_mCurKonkaProgInfo._mProgNo != iProgNo)
		{
			com.printfE(TAG, "error program number !");
			return null;
		}

		if (baseTime.equals(zeroTime))
		{
			return null;
		}
		else
		{
			ArrayList<EpgEventInfo> tempEventList = null;
			int iPrevDay = 0;
			baseTime.set(baseTime.toMillis(true) + TIME_OFFSET * 1000);
			iPrevDay = baseTime.weekDay;
			baseTime.setToNow();
			int iDay = 0;
			int iEventCount = 0;

			iEventCount = getEventCount(sServiceType, iProgNo, baseTime);
			com.printfI(TAG, "the event is " + iEventCount);
			
			if (iEventCount <= 0)
			{
				com.printfI(TAG, "errrrrrrrrrrroooooo");
				return null;
			}
			
			try
			{
				com.printfI(TAG, "normallllllllllllll");
				tempEventList = getEventInfo(sServiceType, iProgNo, baseTime, iEventCount);
			}
			catch (TvCommonException e)
			{
				e.printStackTrace();
			}

			if (tempEventList != null && tempEventList.size() > 0)
			{
				if (_mEventList == null)
				{
					_mEventList = new ArrayList<KonkaEventInfo>();
				}
				else
				{
					_mEventList.clear();
				}

				int j = 0;
				int iEpgTimerCount = 0;

				try
				{
					iEpgTimerCount = getEpgTimerEventCount();
				}
				catch (TvCommonException e)
				{
					com.printfE(TAG, "getEpgTimerEventCount error");
				}

				for (int i = 0; i < tempEventList.size(); i++)
				{
					if (_mCurKonkaProgInfo._mProgNo != iProgNo)
					{
						com.printfE(TAG, "last program number !");
						return null;
					}

					KonkaEventInfo tempinfo = new KonkaEventInfo();
					EpgEventInfo info = tempEventList.get(i);
					
					tempinfo._mStartTime = info.startTime + TIME_OFFSET;
					tempinfo._mEndTime = info.endTime + TIME_OFFSET;
					tempinfo._mEventId = info.eventId;
					tempinfo._mEventName = info.name;
					tempinfo._mProgNo = iProgNo;
					tempinfo._mIndex = i;
					tempinfo._mServiceType = sServiceType;
					
					baseTime.set((long)tempinfo._mStartTime * 1000);
					

					if (i == 0)
					{
//						com.printfE(TAG, "getEventList baseTime year is -------->" + baseTime.year);
//						com.printfE(TAG, "getEventList baseTime month is -------->" + baseTime.month);
//						com.printfE(TAG, "getEventList baseTime monthDay is -------->" + baseTime.monthDay);
//						com.printfE(TAG, "getEventList baseTime weekDay is -------->" + baseTime.weekDay);
//						com.printfE(TAG, "getEventList baseTime Hour is -------->" + baseTime.hour);
//						com.printfE(TAG, "getEventList baseTime Min is -------->" + baseTime.minute);
//						com.printfE(TAG, "getEventList baseTime Seco is -------->" + baseTime.second);
//						com.printfE(TAG, "getEventList baseTime yearDay is -------->" + baseTime.yearDay);
//						com.printfE(TAG, "getEventList baseTime startTime is -------->" + tempinfo._mStartTime);
//						com.printfE(TAG, "getEventList PrevDay is -------->" + iPrevDay);
					}

					if (iPrevDay != baseTime.weekDay)
					{
						iDay++;
						iPrevDay = baseTime.weekDay;
					}
					tempinfo._mWhichDay = iDay;

					if (j < iEpgTimerCount && i != 0)
					{
						int iResult = 2;

						do
						{
							iResult = isEventScheduled(tempinfo, j);

							j = (iResult == 1) ? j : (j + 1);
						}
						while (iResult == 2 && j < iEpgTimerCount);

						if (iResult == 0)
						{
							tempinfo._mIsScheduled = true;
						}
						else
						{
							tempinfo._mIsScheduled = false;
						}
					}
					else
					{
						tempinfo._mIsScheduled = false;
					}
					_mEventList.add(i, tempinfo);
				}
				return _mEventList;
			}
			else
			{
				return null;
			}
		}
	}

	/**
     * get event list in which day
     * 
     * @param int day
     * 
     * @return ArrayList<KonkaEventList>
     */
	@Override
	public ArrayList<KonkaEventInfo> getEventListByDay(int iDay) 
	{
		// TODO Auto-generated method stub
		ProgramInfo pi = getCurProgramInfo();
		getEventList(pi.serviceType, pi.number);
		if (_mEventList == null || _mEventList.size() <= 0)
		{
			return null;
		}
		else
		{
			ArrayList<KonkaEventInfo> list = new ArrayList<KonkaEventInfo>();
			for (int i = 0; i < _mEventList.size(); i++)
			{
				if (_mEventList.get(i)._mWhichDay == iDay)
				{
					list.add(_mEventList.get(i));
				}
				else if (_mEventList.get(i)._mWhichDay > iDay)
				{
					continue;
				}
				else if (_mEventList.get(i)._mWhichDay < iDay)
				{
					continue;
				}
			}
			return list;
		}
	}
	
	/**
	 * whether the event is scheduled
	 * 
	 * @param int (starttime)
	 * 
	 * @return int (0:scheduled, 1,2,3:not scheduled)
	 */
	private int isEventScheduled(KonkaEventInfo eventInfo, int iTimerIndex)
	{
		EpgEventTimerInfo timerInfo = null;
		try
		{
			timerInfo = getEpgTimerEventByIndex(iTimerIndex);
		}
		catch (TvCommonException e)
		{
			com.printfE(TAG, "getEpgTimerEventByIndex error");
		}
		
		timerInfo.startTime = timerInfo.startTime + _mEventDiffTime + TIME_OFFSET;
		
		if (timerInfo != null && timerInfo.startTime == eventInfo._mStartTime
				&& timerInfo.serviceNumber == eventInfo._mProgNo
				&& timerInfo.serviceType == eventInfo._mServiceType)
		{
			return 0;
		}
		else
		{
			if (timerInfo.startTime > eventInfo._mStartTime)
			{
				return 1;
			}
			else
			{
				return 2;
			}
		}
	}
	
	/**
	 * get the detail description of the event
	 * 
	 * @param servertype ,server number,basetime,EnumEpgDescriptionType
	 *
	 * @return String description
	 * @throws TvCommonException 
	 */
	public String getEventDetailDescriptor(int iStartTime)
	{
		String tempString = null;
		ProgramInfo tempProgramInfo = null;
		Time baseTime = new Time();
		baseTime.set((long)(iStartTime - TIME_OFFSET)* 1000);
		
		tempProgramInfo = getCurProgramInfo();
		
		com.printfE(TAG, "获取节目信息=======当前频道信息" + tempProgramInfo.number + tempProgramInfo.serviceName + tempProgramInfo.queryIndex);
		
		try
		{
			tempString = getEventDescriptor(tempProgramInfo.serviceType, tempProgramInfo.number, baseTime,
					EnumEpgDescriptionType.E_DETAIL_DESCRIPTION);
		}
		catch (TvCommonException e)
		{
			e.printStackTrace();
		}
		
		com.printfE(TAG, "获取节目信息=======当前节目" + tempString);
		
		return tempString;
	}

	/**
     * get current clk time
     * 
     * @return Time
     * 
     */
	@Override
	public Time getCurClkTime() 
	{
		Time curTime = null;
		curTime = new Time();
		curTime.setToNow();
		curTime.set((long)curTime.toMillis(true) + TIME_OFFSET * 1000);
		
//		com.printfE(TAG, "getCurClkTime baseTime TimeZone is -------->" + Time.getCurrentTimezone());
//		com.printfE(TAG, "getCurClkTime baseTime year is -------->" + curTime.year);
//		com.printfE(TAG, "getCurClkTime baseTime month is -------->" + curTime.month);
//		com.printfE(TAG, "getCurClkTime baseTime monthDay is -------->" + curTime.monthDay);
//		com.printfE(TAG, "getCurClkTime baseTime weekDay is -------->" + curTime.weekDay);
//		com.printfE(TAG, "getCurClkTime baseTime Hour is -------->" + curTime.hour);
//		com.printfE(TAG, "getCurClkTime baseTime Min is -------->" + curTime.minute);
//		com.printfE(TAG, "getCurClkTime baseTime Seco is -------->" + curTime.second);
//		com.printfE(TAG, "getCurClkTime baseTime yearDay is -------->" + curTime.yearDay);
		 
		return curTime;
	}

	/**
     * select program by number and service type
     * 
     * @param iNumber 
     * @param eServiceType
     * 
     */
	@Override
	public void ProgramSel(KonkaProgInfo kpi)
	{
		try
		{
			channelManager.selectProgram(kpi._mProgNo, kpi._mProgType, kpi._mProgIndex);
			_mCurKonkaProgInfo = kpi;
			com.printfE(TAG, "program name sis ---------->" + kpi._mProgName);
		}
		catch (TvCommonException e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * get epg timer index by KonkaEventInfo
	 * 
	 * @param KonkaEventInfo
	 * 
	 * @retrun int
	 */
	private int getEpgTimerIndex(KonkaEventInfo kei)
	{
		int iIndex = 0;
		int iTimerCount = 0;
		EpgEventTimerInfo timerInfo = null;
		boolean bFind = false;
		
		try
		{
			iTimerCount = getEpgTimerEventCount();
		}
		catch (TvCommonException e)
		{
			e.printStackTrace();
		}
		
		for(iIndex = 0; iIndex < iTimerCount; iIndex++)
		{
			try
			{
				timerInfo = getEpgTimerEventByIndex(iIndex);
			}
			catch (TvCommonException e)
			{
				e.printStackTrace();
			}
			
			timerInfo.startTime = timerInfo.startTime + _mEventDiffTime + TIME_OFFSET;
			
			if (timerInfo != null)
			{
				if (kei._mStartTime == timerInfo.startTime
					&& timerInfo.serviceNumber == kei._mProgNo)
				{
					bFind = true;
					break;
				}
				else if (kei._mStartTime < timerInfo.startTime)
				{
					bFind = false;
					break;
				}
				
			}
		}
		if (bFind)
		{
			return iIndex;
		}
		else
		{
			return -1;
		}
	}
	
	/**
     * delete epg timer event 
     * 
     * @param iIndex (index of event list)
     * 
     * @return boolean
     */
    public boolean delEpgTimerEvent(int iIndex, boolean bIsEpgMenu)
    {
    	if (bIsEpgMenu)
    	{
	    	if (_mEventList == null)
	    	{
	    		return false;
	    	}
	    	int iTimerIndex = 0;

	    	iTimerIndex = getEpgTimerIndex(_mEventList.get(iIndex));
	    	
	    	if (iTimerIndex == -1)
	    	{
	    		return false;
	    	}
	    	else
	    	{
	    		EpgEventTimerInfo info = null;
	    		
	    		try
	    		{
	    			info = getEpgTimerEventByIndex(iTimerIndex);
	    		}
	    		catch (TvCommonException e)
	    		{
	    			e.printStackTrace();
	    		}
	    		
	    		deleteScheduleInfoByStartTime(info.startTime);
	    		
	    		try
	    		{
	    			delEpgEvent(iTimerIndex);
	    			_mEventList.get(iIndex)._mIsScheduled = false;
	    		}
	    		catch (TvCommonException e)
	    		{
	    			e.printStackTrace();
	    		}
	    		
	    		return true;
	    	}
    	}
    	else
    	{
    		int iTimerCount = 0;
    		
    		try
    		{
    			iTimerCount = getEpgTimerEventCount();
    		}
    		catch (TvCommonException e)
    		{
    			e.printStackTrace();
    		}
    		
    		if (iIndex < 0 && iIndex >= iTimerCount)
    		{
    			return false;
    		}
    		
    		EpgEventTimerInfo info = null;
    		
    		try
    		{
    			info = getEpgTimerEventByIndex(iIndex);
    		}
    		catch (TvCommonException e)
    		{
    			e.printStackTrace();
    		}
    		
    		deleteScheduleInfoByStartTime(info.startTime);
    		
    		try
    		{
    			delEpgEvent(iIndex);
    		}
    		catch (TvCommonException e)
    		{
    			e.printStackTrace();
    		}
    		
    		return true;
    	}
    }
    
    /**
     * check the time is scheduled in two minutes
     * 
     * @param starttime (int)
     * 
     * @return boolean 
     */
    private boolean checkIsScheduledInTwoMinutes(int iStartTime)
    {
    	int iTimerCount = 0;
    	
    	try
    	{
    		iTimerCount = tm.getEpgTimerEventCount();
    	}
    	catch (TvCommonException e)
    	{
    		e.printStackTrace();
    	}
    	
    	EpgEventTimerInfo timerInfo = null;
    	
    	for (int i = 0; i < iTimerCount; i++)
    	{
    		try
    		{
    			timerInfo = getEpgTimerEventByIndex(i);
    		}
    		catch (TvCommonException e)
    		{
    			e.printStackTrace();
    		}
    		
    		timerInfo.startTime = timerInfo.startTime + TIME_OFFSET;
    		
    		if (timerInfo.startTime > iStartTime - 60 * 2 
    				&& timerInfo.startTime < iStartTime + 60 * 2)
    		{
    			return true;
    		}
    		else if (timerInfo.startTime >= iStartTime + 60 * 2)
    		{
    			return false;
    		}
    	}
    	
    	return false;
    }
	
    /**
     * add epg timer event
     * 
     * @param KonkaEventInfo
     * 
     * @return RESULT_ADDEVENT
     */
    public RESULT_ADDEVENT addEpgTimerEvent(KonkaEventInfo kei)
    {
    	EpgEventTimerInfo info = new EpgEventTimerInfo();
    	Time baseTime = new Time();
    	int ibaseTime = 0;
    	baseTime = getCurClkTime();
    	ibaseTime = (int)(baseTime.toMillis(true) / 1000);
    	
    	com.printfE(TAG, "================ibaseTime" + ibaseTime);
    	com.printfE(TAG, "================_mStartTime" + kei._mStartTime);
    	
    	if (kei._mStartTime <= ibaseTime + _mEventDiffTime + 62)
    	{
    		return RESULT_ADDEVENT.E_ADDEVENT_FAIL_TIMEOUT;
    	}
    	
    	if (kei._mStartTime < ibaseTime + 2 * 60)
    	{
    		return RESULT_ADDEVENT.E_ADDEVENT_FAIL_OF_ATHAND;
    	}
    	
    	if (checkIsScheduledInTwoMinutes(kei._mStartTime - _mEventDiffTime))
    	{
    		return RESULT_ADDEVENT.E_ADDEVENT_FAIL_OF_EXIST;
    	}
    	EnumEpgTimerCheck eCheck = EnumEpgTimerCheck.E_NONE;
    	
    	info.startTime = kei._mStartTime - _mEventDiffTime - TIME_OFFSET;
    	info.eventID = kei._mEventId;
    	info.durationTime = kei._mEndTime - kei._mStartTime;
    	info.enTimerType = (short)EnumEpgTimerActType.EN_EPGTIMER_ACT_REMINDER.ordinal();
    	info.serviceNumber = kei._mProgNo;
    	info.serviceType = kei._mServiceType;
//    	info.enRepeatMode = (short)EnumTimerPeriod.EN_Timer_Once.ordinal();
    	info.enRepeatMode = 0x81;
    	info.isEndTimeBeforeStart = false;
    	
    	try
    	{
    		eCheck = addEpgEvent(info);
    	}
    	catch (TvCommonException e)
    	{
    		e.printStackTrace();
    	}

    	com.printfE(TAG, "add event result is [" + eCheck + "] here ");
    	
    	if (eCheck.ordinal() == EnumEpgTimerCheck.E_FULL.ordinal())
    	{
    		return RESULT_ADDEVENT.E_ADDEVENT_FAIL_FULL;
    	}
    	else if (eCheck.ordinal() == EnumEpgTimerCheck.E_SUCCESS.ordinal())
    	{
    		ProgramInfoQueryCriteria piQc = new ProgramInfoQueryCriteria();
        	ProgramInfo progInfo = null;
        	piQc.number = info.serviceNumber;
    		piQc.setServiceType(EnumServiceType.values()[info.serviceType]);
    		progInfo = getProgramInfoByNumber(piQc);
    		System.out.println("the name is " + progInfo.serviceName);
    		insertScheduleInfo(progInfo.serviceName, kei._mEventName, info.startTime);
    		return RESULT_ADDEVENT.E_ADDEVENT_SUCCESSS;
    	}
    	else if (eCheck.ordinal() == EnumEpgTimerCheck.E_OVERLAY.ordinal())
    	{
    		return RESULT_ADDEVENT.E_ADDEVENT_FAIL_OVERLAY;
    	}
    	else
    	{
    		return RESULT_ADDEVENT.E_ADDEVENT_FAIL_OTHER_EXCEPTION;
    	}
    }
    
    /**
     * update timer info
     * 
     * @param servicename
     * @param eventname
     * @param starttime
     * @return void
     */
    public void updateTimerInfo(String servicename, String eventname, int starttime)
    {
    	insertScheduleInfo(servicename, eventname, starttime);
    }
	
    /**
     * get epg timer event list
     * 
     * @return ArrayList<EPG_SCHEDULE_EVENT_INFO>
     */
    public ArrayList<EPG_SCHEDULE_EVENT_INFO> getEpgTimerEventList()
    {
    	ArrayList<EPG_SCHEDULE_EVENT_INFO> scheduleList = new ArrayList<EPG_SCHEDULE_EVENT_INFO>();
    	int iTimerCount = 0;
    	
    	try
    	{
    		iTimerCount = tm.getEpgTimerEventCount();
    	}
    	catch (TvCommonException e)
    	{
    		e.printStackTrace();
    	}
//    	ProgramInfoQueryCriteria piQc = new ProgramInfoQueryCriteria();
//    	ProgramInfo progInfo = new ProgramInfo();
//    	EpgEventInfo eventInfo = new EpgEventInfo();
    	Time baseTime = new Time();
    	EpgEventTimerInfo info = null;
    	
    	for (int i = 0; i < iTimerCount; i++)
    	{
    		EPG_SCHEDULE_EVENT_INFO scheduleInfo = new EPG_SCHEDULE_EVENT_INFO();
    		
    		try
    		{
    			info = getEpgTimerEventByIndex(i);
    		}
    		catch (TvCommonException e)
    		{
    			e.printStackTrace();
    		}
    		
    		if (info != null)
    		{
    			scheduleInfo.eventId = info.eventID;
    			scheduleInfo.progNumber = info.serviceNumber;
    			scheduleInfo.startTime = info.startTime + TIME_OFFSET;
    			scheduleInfo.eventName = queryEventName(info.startTime);
    			scheduleInfo.progName = queryServiceName(info.startTime);
    			scheduleInfo.serviceType = info.serviceType;
    			
//    			piQc.number = info.serviceNumber;
//				piQc.setServiceType(EnumServiceType.values()[info.serviceType]);
//				progInfo = getProgramInfoByNumber(piQc);
//				scheduleInfo.progName = progInfo.serviceName;
				
				com.printfE("timer start time is [" + info.startTime + "]");
				long start = (long)info.startTime * 1000 + TIME_OFFSET * 1000 
						+ _mEventDiffTime * 1000;
				com.printfE("timer start time is [" + start + "]");
				baseTime.set(start);
				
				com.printfE(TAG, "加入预约时间============" + baseTime.year + "/" + baseTime.month + "/" + baseTime.monthDay
						+ "/" + baseTime.hour + ":" + baseTime.minute);
				if(baseTime.toMillis(true) <= getCurClkTime().toMillis(true))
				{
					delEpgTimerEvent(i, false);
				}
				else
				{
					scheduleList.add(scheduleInfo);
				}
    		}
    	}
    	
    	return scheduleList;
    }
    
    /**
     * set display window region
     * 
     * @param x the start value of X
     * @param y the start value of Y
     * @param width the width of X
     * @param height the height of Y
     * 
     * @return void
     */
    public void setDispalyWindow(int x, int y, int width, int height)
    {
    	VideoWindowType vwType = new VideoWindowType();
    	vwType.x = x;
    	vwType.y = y;
    	vwType.width = width;
    	vwType.height = height;
    	
    	try 
    	{
			TvManager.getPictureManager().selectWindow(EnumScalerWindow.E_MAIN_WINDOW);
			TvManager.getPictureManager().setDisplayWindow(vwType);
			TvManager.getPictureManager().scaleWindow();
		} 
    	catch (TvCommonException e) 
    	{
			e.printStackTrace();
		}
    }

    /**
     *  modify schedule time
     *  
     * @param iDiffTime(int)(改变前后的提醒时间的差）
     */
	public synchronized void modifyTimerNotifyTime(int iDiffTime)
	{
		int iTimerCount = 0;
		ArrayList<EpgEventTimerInfo> timerList = new ArrayList<EpgEventTimerInfo>();
		EpgEventTimerInfo info = null;
		
		com.printfE("timer iDiffTime is [" + iDiffTime + "] =============");
		
		try
		{
			iTimerCount = getEpgTimerEventCount();
		}
		catch (TvCommonException e)
		{
			e.printStackTrace();
		}
		
		for (int i = 0; i < iTimerCount; i++)
		{
			try
			{
				info = getEpgTimerEventByIndex(i);
			}
			catch (TvCommonException e)
			{
				e.printStackTrace();
			}	
			
			if (info != null)
			{
				timerList.add(info);
			}
		}
		
		if (iTimerCount > 0)
		{
			for (int i = 0; i < iTimerCount; i++)
			{
				delEpgTimerEvent(0, false);
			}
		}
		
		iTimerCount = timerList.size();
		Time current = new Time();
		current.setToNow();
		int iCurTime = (int)(current.toMillis(true)/1000);
		
		for (int i = 0; i < iTimerCount; i++)
		{
			info = timerList.get(i);
			
			info.startTime = info.startTime - iDiffTime;
			
			if (info.startTime < iCurTime + 2 * 60)
			{
				continue;
			}
			
			try
			{
				addEpgEvent(info);
			}
			catch (TvCommonException e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void setScheduleNofityTime(int iNowNotifyTime) 
	{
		// TODO Auto-generated method stub
		com.printfE("timer iNowNotifyTime is [" + iNowNotifyTime + "]");
		_mEventDiffTime = iNowNotifyTime;
	}

	@Override
	public void cancelEpgTimerEvent(int iLeftTime) 
	{
		// TODO Auto-generated method stub
		try
		{
			tm.cancelEpgTimerEvent(iLeftTime, false);
		}
		catch (TvCommonException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void execEpgTimerEvent() 
	{
		try
		{
			execEpgTimerAction();
		}
		catch (TvCommonException e)
		{
			e.printStackTrace();
		}
		
	}
	
	/**
	 * get timer event name
	 * @param starttime
	 * @return String
	 */
	private String queryEventName(int starttime)
	{
		Cursor cursor = DataBaseDeskImpl.getDataBaseMgrInstance(context).queryEpg(true, starttime);
		String eventName = new String();
		if (cursor.moveToFirst())
		{
			eventName = cursor.getString(cursor.getColumnIndex("sEventName"));
			System.out.println("\n=====>>eventName "+eventName+" @starttime "+starttime);
		}
		cursor.close();

		return eventName;
	}
	
	/**
	 * delete schedule info
	 * @param starttime
	 */
	public void deleteScheduleInfoByStartTime(int starttime)
	{
		DataBaseDeskImpl.getDataBaseMgrInstance(context).deleteEpg(starttime);
	}
	
	/**
	 * 
	 */
	public void deletePastScheduleInfo()
	{
		Cursor c = DataBaseDeskImpl.getDataBaseMgrInstance(context).queryEpg(false, 0);
		List<Integer> starttimes = new ArrayList<Integer>();
		Time current = getCurClkTime();
		int iCurTime = (int)(current.toMillis(true)/1000) - TIME_OFFSET - _mEventDiffTime - 10;
		
		if (c.moveToFirst())
		{
			int index = c.getColumnIndex("u32StartTime");
			
			do
			{
				int starttime = c.getInt(index);
				
				if (starttime < iCurTime)
				{
					starttimes.add(new Integer(starttime));
				}
			}
			while(c.moveToNext());
		}
		
		if (!starttimes.isEmpty())
		{
			int size = starttimes.size();
			int starttime = 0;
			
			for (int i = 0; i < size; i++)
			{
				starttime = starttimes.get(i);
				DataBaseDeskImpl.getDataBaseMgrInstance(context).deleteEpg(starttime);
			}
		}
		
		starttimes.clear();
		starttimes = null;
		
		c.close();
	}
	
	/**
	 * 
	 * @param serviceName
	 * @param eventName
	 * @param starttime
	 */
	public void insertScheduleInfo(String serviceName, String eventName, int starttime)
	{
		deletePastScheduleInfo();
		ContentValues value = new ContentValues();
		value.put("sEventName", eventName);
		value.put("u32StartTime", starttime);
		value.put("sServiceName", serviceName);
		try
		{
			DataBaseDeskImpl.getDataBaseMgrInstance(context).insertEpg(value);
		}
		catch (SQLiteException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * get timer service name
	 * @param starttime
	 * @return
	 */
	private String queryServiceName(int starttime)
	{
		Cursor cursor = DataBaseDeskImpl.getDataBaseMgrInstance(context).queryEpg(true, starttime);
		String serviceName = new String();
		if (cursor.moveToFirst())
		{
			serviceName = cursor.getString(cursor.getColumnIndex("sServiceName"));
			System.out.println("\n=====>>serviceName "+serviceName+" @starttime "+starttime);
		}
		cursor.close();

		return serviceName;
	}

	@Override
	public void openDB() {
//		epgdeskimpl中的数据库操作已经不需要自己获取对象管理。
	}

	@Override
	public void closeDB() {
//		epgdeskimpl中的数据库操作已经不需要自己获取对象管理。
	}

	@Override
	public EPG_SCHEDULE_EVENT_INFO getScheduleInfoByIndex(int index) 
	{
		EPG_SCHEDULE_EVENT_INFO info = null;
		EpgEventTimerInfo timerInfo	= null;
		
		try
		{
			timerInfo = getEpgTimerEventByIndex(index);
		}
		catch (TvCommonException e)
		{
			e.printStackTrace();
		}
		
		if (timerInfo != null)
		{
			info = new EPG_SCHEDULE_EVENT_INFO();
			info.progNumber = timerInfo.serviceNumber;
			info.startTime = timerInfo.startTime + TIME_OFFSET;
			info.eventId = 0;
			info.eventName = queryEventName(timerInfo.startTime);
			info.progName = queryServiceName(timerInfo.startTime);
			info.serviceType = (short)timerInfo.serviceType;
		}
		
		return info;
	}

	@Override
	public ArrayList<KonkaProgInfo> getEpgSearchReslut(String inputString)
	{
		return null;
	}

}