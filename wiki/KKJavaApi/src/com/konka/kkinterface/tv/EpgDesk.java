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
package com.konka.kkinterface.tv;import java.util.ArrayList;import android.text.format.Time;import com.konka.kkimplements.tv.vo.KonkaEventInfo;
import com.konka.kkimplements.tv.vo.KonkaProgCount;
import com.konka.kkimplements.tv.vo.KonkaProgInfo;
import com.konka.kkinterface.tv.DtvInterface.EPG_SCHEDULE_EVENT_INFO;
import com.konka.kkinterface.tv.DtvInterface.MEMBER_SERVICETYPE;
import com.konka.kkinterface.tv.DtvInterface.RESULT_ADDEVENT;
import com.tvos.common.exception.TvCommonException;import com.tvos.common.vo.EpgEventTimerInfo;import com.tvos.common.vo.PresentFollowingEventInfo;
import com.tvos.common.vo.ProgramInfo;
import com.tvos.common.vo.TvOsType;import com.tvos.common.vo.TvOsType.EnumInputSource;
import com.tvos.common.vo.TvOsType.EnumServiceType;
import com.tvos.dtv.vo.DtvEitInfo;import com.tvos.dtv.vo.DtvEventComponentInfo;import com.tvos.dtv.vo.DtvType.EnumEpgDescriptionType;import com.tvos.dtv.vo.EpgCridEventInfo;import com.tvos.dtv.vo.EpgCridStatus;import com.tvos.dtv.vo.EpgEventInfo;import com.tvos.dtv.vo.EpgFirstMatchHdCast;import com.tvos.dtv.vo.EpgHdSimulcast;import com.tvos.dtv.vo.EpgTrailerLinkInfo;import com.tvos.dtv.vo.NvodEventInfo;/** *  * Base Interface of Epg Service *  *  *  * @author steven.li *  *  */public interface EpgDesk extends BaseDesk
{
    /**
     * Get total event info from u32BaseTime
     * 
     * @param serviceType short Program service type. (DTV, radio, data service)
     * @param serviceNo int Program service number.
     * @param baseTime Time base time based on UTC time
     * @param maxEventInfoCount int maximum object size in return arraylist
     * @return ArrayList<EpgEventInfo>
     * @throws TvCommonException
     */
    public ArrayList<EpgEventInfo> getEventInfo(short serviceType,
        int serviceNo, Time baseTime, int maxEventInfoCount)
        throws TvCommonException;

    /**
     * Get total event counts from base time
     * 
     * @param serviceType short Program service type. (DTV, radio, data service)
     * @param serviceNo int Program service number.
     * @param baseTime Time UTC time
     * @return int event count from basetime
     */
    public int getEventCount(short serviceType, int serviceNo, Time baseTime);

    /**
     * Get program EPG event information by time.
     * 
     * @param serviceType short Program service type. (DTV, radio, data service)
     * @param serviceNo int Program service number.
     * @param baseTime Time base time based on UTC time
     * @return EpgEventInfo
     * @throws TvCommonException
     */
    public EpgEventInfo getEventInfoByTime(short serviceType, int serviceNo,
        Time baseTime) throws TvCommonException;

    /**
     * Get program EPG event information by event ID.
     * 
     * @param serviceType short Program service type. (DTV, radio, data service)
     * @param serviceNo int Program service number.
     * @param eventID short
     * @return EpgEventInfo
     * @throws TvCommonException
     */
    public EpgEventInfo getEventInfoById(short serviceType, int serviceNo,
        short eventID) throws TvCommonException;

    /**
     * Get program EPG event descriptor.
     * 
     * @param serviceType short Program service type. (DTV, radio, data service)
     * @param serviceNumber int Program service number.
     * @param baseTime Time UTC time
     * @param epgDescriptionType EN_EPG_DESCRIPTION_TYPE
     * @return String
     * @throws TvCommonException
     */
    public String getEventDescriptor(short serviceType, int serviceNumber,
        Time baseTime, EnumEpgDescriptionType epgDescriptionType)
        throws TvCommonException;

    /**
     * Get EPG HD simulcast
     * 
     * @param serviceType short Program service type. (DTV, radio, data service)
     * @param serviceNo int Program service number.
     * @param baseTime Time UTC time
     * @param maxCount short maximum count want to get
     * @return EpgHdSimulcast
     * @throws TvCommonException
     */
    public ArrayList<EpgHdSimulcast> getEventHdSimulcast(short serviceType,
        int serviceNo, Time baseTime, short maxCount) throws TvCommonException;

    /**
     * Reset the service priority to default for the case EPG database is full.
     * 
     * @throws TvCommonException
     */
    public void resetEpgProgramPriority() throws TvCommonException;

    /**
     * Get CRID status. Series / split / alternate / recommend.
     * 
     * @param serviceType short Program service type. (DTV, radio, data service)
     * @param serviceNumber int Program service number.
     * @param eventStartTime Time Target event start time base on UTC time.
     * @return EpgCridStatus EPG CRID Status
     * @throws TvCommonException
     */
    public EpgCridStatus getCridStatus(short serviceType, int serviceNumber,
        Time eventStartTime) throws TvCommonException;

    /**
     * Get CRID series list.
     * 
     * @param serviceType short Program service type. (DTV, radio, data service)
     * @param serviceNumber int Program service number.
     * @param eventStartTime Time Target event start time base on UTC time.
     * @return ArrayList<EpgCridEventInfo> ArrayList of EpgCridEventInfo
     * @throws TvCommonException
     */
    public ArrayList<EpgCridEventInfo> getCridSeriesList(short serviceType,
        int serviceNumber, Time eventStartTime) throws TvCommonException;

    /**
     * Get CRID split list.
     * 
     * @param serviceType short Program service type. (DTV, radio, data service)
     * @param serviceNumber int Program service number.
     * @param eventStartTime Time Target event start time base on UTC time.
     * @return ArrayList<EpgCridEventInfo> ArrayList of EpgCridEventInfo
     * @throws TvCommonException
     */
    public ArrayList<EpgCridEventInfo> getCridSplitList(short serviceType,
        int serviceNumber, Time eventStartTime) throws TvCommonException;

    /**
     * Get CRID alternate list.
     * 
     * @param serviceType short Program service type. (DTV, radio, data service)
     * @param serviceNumber int Program service number.
     * @param eventStartTime Time event start time based on UTC time
     * @return ArrayList<EpgCridEventInfo> vector of ST_DTV_EPG_CRID_EVENT_INFO
     * @throws TvCommonException
     */
    public ArrayList<EpgCridEventInfo> getCridAlternateList(short serviceType,
        int serviceNumber, Time eventStartTime) throws TvCommonException;

    /**
     * Get trailer link
     * 
     * @return ArrayList<EpgTrailerLinkInfo> arraylist of EPG trailer link
     * infomation
     * @throws TvCommonException
     */
    public ArrayList<EpgTrailerLinkInfo> getRctTrailerLink()
        throws TvCommonException;

    /**
     * Get event info(s) by trailer link
     * 
     * @param epgTrailerLinkInfo EpgTrailerLinkInfo
     * @return ArrayList<EpgCridEventInfo> arraylist of EpgCridEventInfo
     * @throws TvCommonException
     */
    public ArrayList<EpgCridEventInfo> getEventInfoByRctLink(
        EpgTrailerLinkInfo epgTrailerLinkInfo) throws TvCommonException;

    /**
     * Eable EPG Barker channel work.
     * 
     * @return boolean
     * @throws TvCommonException
     */
    public boolean enableEpgBarkerChannel() throws TvCommonException;

    /**
     * Disable EPG Barker channel work.
     * 
     * @return boolean
     * @throws TvCommonException
     */
    public boolean disableEpgBarkerChannel() throws TvCommonException;

    /**
     * Get offset time for event time between time of offset time change case
     * 
     * @param utcTime Time UTC time
     * @param isStartTime boolean TRUE: event start time, FALSE: event end time
     * @return int
     * @throws TvCommonException
     */
    public int getEpgEventOffsetTime(Time utcTime, boolean isStartTime)
        throws TvCommonException;

    /**
     * Get present/following event information of certain service
     * 
     * @param serviceType short Program service type. (DTV, radio, data service)
     * @param serviceNo int Program service number.
     * @param present boolean
	 * @param eventComponentInfo
	 *            DtvEventComponentInfo
	 * @param descriptionType
	 *            EnumEpgDescriptionType
     * @return EpgEventInfo
     * @throws TvCommonException
     */
    public PresentFollowingEventInfo getPresentFollowingEventInfo(
        short serviceType, int serviceNo, boolean present,
        EnumEpgDescriptionType descriptionType) throws TvCommonException;

    /**
     * Get first matched Epg HD simulcast.
     * 
     * @param serviceType short Program service type. (DTV, radio, data service)
     * @param serviceNo int Program service number.
     * @param baseTime Time Event start time based on UTC time.
     * @return Epg1stMatchHDSimulcastVO including if resolvable,target program
     * name, target event
     * @throws TvCommonException
     */
    public EpgFirstMatchHdCast getEvent1stMatchHdSimulcast(short serviceType,
        int serviceNo, Time baseTime) throws TvCommonException;

    /**
     * Get first matched EPG HD broadcast later.
     * 
     * @param serviceType short Program service type. (DTV, radio, data service)
     * @param serviceNo int Program service number.
     * @param baseTime Time Event start time base on UTC time.
     * @return Epg1stMatchHDSimulcastVO including if resolvable,target program
     * name, target event
     * @throws TvCommonException
     */
    public EpgFirstMatchHdCast getEvent1stMatchHdBroadcast(short serviceType,
        int serviceNo, Time baseTime) throws TvCommonException;

    /**
     * Get EIT information.
     * 
     * @return DtvEitInfo The EIT information.
     * @throws TvCommonException
     */
    public DtvEitInfo getEitInfo(boolean bPresent) throws TvCommonException;

    /**
     * Setting the service priority for the case EPG database is full. the EIT
     * buffer assigned to the service with lowest priority will be replaced.
     * CM/UI should call this function whenever channel change.
     * 
     * @param programIndex int service position or program index.
     * @throws TvCommonException
     */
    public void setEpgProgramPriority(int programIndex)
        throws TvCommonException;

    /**
     * Setting the service priority for the case EPG database is full. the EIT
     * buffer assigned to the service with lowest priority will be replaced.
     * CM/UI should call this function whenever channel change.
     * 
     * @param serviceType short Program service type. (DTV, radio, data service)
     * @param serviceNo int Program service number.
     * @throws TvCommonException
     */
    public void setEpgProgramPriority(short serviceType, int serviceNo)
        throws TvCommonException;

    /**
     * Get NVOD time shift event counts belong to the input NVOD reference
     * service
     * 
     * @param serviceType short nvod program service type.
     * @param serviceNumber int nvod program service number.
     * @return int the found events info count
     * @throws TvCommonException
     */
    public int getNvodTimeShiftEventCount(short serviceType, int serviceNumber)
        throws TvCommonException;

    /**
     * Get NVOD time shift event info service
     * 
     * @param serviceType short nvod program service type.
     * @param serviceNumber int nvod program service number.
     * @return int the found events info count
     * @throws TvCommonException
     */
    public ArrayList<NvodEventInfo> getNvodTimeShiftEventInfo(
        short serviceType, int serviceNumber, int maxEventNum,
        EnumEpgDescriptionType eEpgDescritionType) throws TvCommonException;

    /**
     * Add epg event
     * 
     * @param vo EpgEventTimerInfo
     * 
     * @return EnumEpgTimerCheck
     * 
     * @throws TvCommonException
     */
    public TvOsType.EnumEpgTimerCheck addEpgEvent(EpgEventTimerInfo vo)
        throws TvCommonException;

    /**
     * get epg timer event by index
     * 
     * @param index int
     * 
     * @return EpgEventTimerInfo timer event info
     * 
     * @throws TvCommonException
     */
    public EpgEventTimerInfo getEpgTimerEventByIndex(int index)
        throws TvCommonException;

    /**
     * get epg timer event count
     * 
     * @return int timer event count
     * 
     * @throws TvCommonException
     */
    public int getEpgTimerEventCount() throws TvCommonException;

    /**
     * is Epg TimerSetting Valid
     * 
     * @param timerInfoVo EpgEventTimerInfo.
     * 
     * @return TvOsType.EnumEpgTimerCheck
     * 
     * @throws TvCommonException
     */
    public TvOsType.EnumEpgTimerCheck isEpgTimerSettingValid(
        EpgEventTimerInfo timerInfoVo) throws TvCommonException;

    /**
     * del All Epg Event
     * 
     * @return boolean
     * 
     * @throws TvCommonException
     */
    public boolean delAllEpgEvent() throws TvCommonException;

    /**
     * is Epg TimerSetting Valid
     * 
     * @param epgEvent int.
     * 
     * @return boolean
     * 
     * @throws TvCommonException
     */
    public boolean delEpgEvent(int epgEvent) throws TvCommonException;

    /**
     * delete Past Epg Timer
     * 
     * @return boolean
     * 
     * @throws TvCommonException
     */
    public boolean deletePastEpgTimer() throws TvCommonException;

    /**
     * exec Epg Timer Action
     * 
     * @return boolean
     * 
     * @throws TvCommonException
     */
    public boolean execEpgTimerAction() throws TvCommonException;
    /**
     * Reconfig EPG timer list & setting monitors
     *
     * @param timeActing int (the time before valid list items.)
     * @param checkEndTime boolean (delete according to end time.)
     * @throws TvCommonException
     */
    public void cancelEpgTimerEvent(int timeActing,
        boolean checkEndTime) throws TvCommonException;
    /**
     * Get timer recording program.
     *
     * @return EpgEventTimerInfo The current timer info
     * @throws TvCommonException
     */
    public EpgEventTimerInfo getEpgTimerRecordingProgram()
        throws TvCommonException;

    /**
     * Gets timezone state
     * 
     * @return TvOsType.EnumTimeZone (Timezone state)
     * @throws TvCommonException
     */
    public TvOsType.EnumTimeZone getTimeZone() throws TvCommonException;
    
    
    /**
	 * Gets current channel program info
	 * 
	 * @return ProgramInfo (current program info)
	 */
	public ProgramInfo getCurProgramInfo();
	
	
    /**
     * Gets current input source
     * 
     * @return current input source
     */
    public EnumInputSource getCurInputSource();
    
    /**
     * Sets Dtv input source
     * 
     * void
     */
    public void setDtvInputSource();
    
    /**
     * Get Dtv program count
     * 
     * @return KonkaProgCount
     */
    public KonkaProgCount getDtvProgCount();
    
    /*
     * get program list 
     * 
     * @param EnumServiceType
     * 
     * @return ArrayList<KonkaProgInfo>
     */
    public ArrayList<KonkaProgInfo> getProgListByServiceType(EnumServiceType eServiceType);
    
    /*
     * get program list 
     * 
     * @param EnumServiceType(short)
     * 
     * @return ArrayList<KonkaProgInfo>
     */
    public ArrayList<KonkaProgInfo> getProgListByServiceType(short sServiceType);
    
    /**
     * get event list
     * 
     * @return ArrayList<KonkaEventList>
     * 
     */
    public ArrayList<KonkaEventInfo> getEventList(short sServiceType, int iProgNo);
    
    /**
     * get event list in which day
     * 
     * @param int day
     * 
     * @return ArrayList<KonkaEventList>
     */
    public ArrayList<KonkaEventInfo> getEventListByDay(int iDay);
    
	/**
	 * find wheather the program exit in the Program list
	 * 
	 * @param Program Arraylist , String input
	 * 
	 * @return false true
	 */
	public ArrayList<KonkaProgInfo> getEpgSearchReslut(String inputString);
	
	/**
	 * get the detail description of the event
	 * 
	 * @param starttime 
	 *
	 * @return String description
	 */
	public String getEventDetailDescriptor(int iStartTime);
	
	/**
	 * get current konka program info
	 * 
	 * @return KonkaProgInfo
	 * 
	 */
    public KonkaProgInfo getCurKonkaProgInfo();
    
    /**
     * get current clk time
     * 
     * @return Time
     * 
     */
    public Time getCurClkTime();
    
    /**
     * select program by number and service type
     * 
     * @param iNumber 
     * @param eServiceType
     * 
     */
    public void ProgramSel(KonkaProgInfo kpi);
    
    /**
     * delete epg timer event 
     * 
     * @param iIndex (index of event list)
     * 
     * @return boolean
     */
    public boolean delEpgTimerEvent(int iIndex, boolean bIsEpgMenu);
    
    /**
     * add epg timer event
     * 
     * @param KonkaEventInfo
     * 
     * @return RESULT_ADDEVENT
     */
    public RESULT_ADDEVENT addEpgTimerEvent(KonkaEventInfo kei);
    
    /**
     * get epg timer event list
     * 
     * @return ArrayList<EPG_SCHEDULE_EVENT_INFO>
     */
    public ArrayList<EPG_SCHEDULE_EVENT_INFO> getEpgTimerEventList();
    
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
    public void setDispalyWindow(int x, int y, int width, int height);
    
    /**
     *  modify schedule time
     *  
     * @param iDiffTime(int)
     * 
     * @return void
     */
    public void modifyTimerNotifyTime(int iDiffTime);
    
    /**
     * set schedule notity time(epg must use it)
     * 
     * @param iNowNotifyTime(int)
     * 
     * @return void
     */
    public void setScheduleNofityTime(int iNowNotifyTime);
    
    /**
     * cancel Epg Timer Event
     * 
     * @param lefttime(int)
     */
    public void cancelEpgTimerEvent(int iLeftTime);
    
    /**
     * execute epg timer event
     * 
     * 
     */
    public void execEpgTimerEvent();
    
    /**
     * open database 
     * 
     */
    public void openDB();
    
    /**
     * close database
     * 
     */
    public void closeDB();
    
    /**
     * get scheduleinfo by index
     * 
     * @param index(int)
     * 
     * @return EPG_SCHEDULE_EVENT_INFO
     */
    public EPG_SCHEDULE_EVENT_INFO getScheduleInfoByIndex(int index);
    
    /**
     * update timer info
     * 
     * @param servicename
     * @param eventname
     * @param starttime
     * @return void
     */
    public void updateTimerInfo(String servicename, String eventname, int starttime);
    
    /**
	 * 
	 * @param serviceName
	 * @param eventName
	 * @param starttime
	 */
	public void insertScheduleInfo(String serviceName, String eventName, int starttime);
	
	/**
	 * 
	 */
	public void deletePastScheduleInfo();
	
	/**
	 * delete schedule info
	 * @param starttime
	 */
	public void deleteScheduleInfoByStartTime(int starttime);
	
	
}