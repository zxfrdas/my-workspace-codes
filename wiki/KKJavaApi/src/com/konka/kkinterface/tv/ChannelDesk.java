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
package com.konka.kkinterface.tv;import android.content.Context;

import com.konka.kkinterface.tv.DataBaseDesk.MEMBER_COUNTRY;
import com.tvos.common.ChannelManager.EnumFirstServiceInputType;import com.tvos.common.ChannelManager.EnumFirstServiceType;import com.tvos.common.AudioManager.EnumAtvAudioModeType;import com.tvos.common.TvPlayer.EnumAvdVideoStandardType;import com.tvos.common.exception.TvCommonException;
import com.tvos.common.vo.ProgramInfo;import com.tvos.common.vo.ProgramInfoQueryCriteria;import com.tvos.common.vo.TvOsType;
import com.tvos.common.vo.TvOsType.EnumProgramCountType;import com.tvos.common.vo.TvOsType.EnumProgramInfoType;import com.tvos.dtv.vo.DtvAudioInfo;
import com.tvos.dtv.vo.DtvSubtitleInfo;
/** *  *  *  * @author caucy.niu *  **         Base Interface of TV tuning */public interface ChannelDesk extends AtvInterface, DtvInterface, BaseDesk 
{
	// / Define first service type
	public final static int max_atv_count = 255;
	public final static int max_dtv_count = 1000;

	// Define Tuning scan type
	public enum EN_TUNING_SCAN_TYPE 
	{
		E_SCAN_ATV, E_SCAN_DTV, E_SCAN_ALL,
	}

	public enum TV_TS_STATUS // TS = TUNINGSERVICE
	{
		E_TS_NONE,
		// channel tuning is in atv manual tuning
		E_TS_ATV_MANU_TUNING_LEFT, E_TS_ATV_MANU_TUNING_RIGHT,
		// channel tuning is in atv auto tuning
		E_TS_ATV_AUTO_TUNING,
		// channel tuning is in atv scan pausing
		E_TS_ATV_SCAN_PAUSING,
		// channel tuning is in dtv scan manual pausing
		E_TS_DTV_MANU_TUNING,
		// channel tuning is in dtv scan auto tuning
		E_TS_DTV_AUTO_TUNING,
		// channel tuning is in dtv scan full tuning
		E_TS_DTV_FULL_TUNING,
		// channel tuning is in dtv scan pausing
		E_TS_DTV_SCAN_PAUSING,
	};

	Object msrvPlayer = null;

	/**
	 * 
	 * 
	 * 
	 * @return TV_TS_STATUS
	 */
	public TV_TS_STATUS GetTsStatus();

	/**
	 * 
	 * 
	 * 
	 * @param enInputType
	 *            default:E_MSRV_FIRST_SERVICE_ALL
	 * 
	 * @param ServiceType
	 *            default:E_SERVICE_DEFAULT
	 * 
	 * @return
	 */
	public boolean changeToFirstService(EnumFirstServiceInputType enInputType,
			EnumFirstServiceType ServiceType);

	/**
	 * 
	 * Do DTV program up.
	 * 
	 * @return
	 */
	public boolean programUp();

	/**
	 * 
	 * Do program down.
	 * 
	 * @return
	 */
	public boolean programDown();

	/**
	 * 
	 * Do channel return to the last service.
	 * 
	 * @return
	 */
	public boolean programReturn();

	/**
	 * 
	 * Do program select by service number and service type
	 * 
	 * @param u32Number
	 *            \b IN: Specify the channel number.
	 * 
	 * @param u8ServiceType
	 *            \b IN: Specify the service type.
	 * 
	 * @return
	 */
	public boolean programSel(int u32Number, MEMBER_SERVICETYPE u8ServiceType);

	/**
	 * 
	 * Get current program information
	 * 
	 * @param pResult
	 *            \b IN: Specify the service information.
	 * 
	 * @return
	 */
	public ProgramInfo getCurrProgramInfo();

	/**
	 * 
	 * Get program specific structure informaion by index
	 * 
	 * @param program
	 *            index
	 * @param pResult
	 * @return boolean
	 */
	public ProgramInfo getProgramInfoByIndex(int programIndex);

	/**
	 * 
	 * Set Scan type by user
	 * 
	 * @param scantype
	 *            atv or dtv or all
	 */
	public void setUserScanType(EN_TUNING_SCAN_TYPE scantype);

	public EN_TUNING_SCAN_TYPE getUserScanType();

	/**
	 * 
	 * set SystemCountry for Dtv using
	 * 
	 * @param mem_country
	 */
	public void setSystemCountry(MEMBER_COUNTRY mem_country);

	public MEMBER_COUNTRY getSystemCountry();

	public int getProgramCount(EnumProgramCountType programCountType);

	/**
	 * get channelname by index
	 * 
	 * @param progNo
	 * @param progType
	 *            E_SERVICETYPE_ATV 0r E_SERVICETYPE_DTV
	 * @param progrID
	 * @return
	 */
	public String getProgramName(int progNo, MEMBER_SERVICETYPE progType,
			short progrID);

	/**
	 * get dtv programe info
	 * 
	 * @param programInfoType
	 *            E_INFO_CURRENT or E_INFO_prev or E_INFO_next
	 * @return
	 */
	public ProgramInfo getProgramInfo(ProgramInfoQueryCriteria criteria,
			EnumProgramInfoType programInfoType);

	public boolean isSignalStabled();

	/**
	 * 
	 * getSIFMtsMode
	 * 
	 * @param none
	 * @return EnumAtvMtsMode E_MONO, E_STEREO, E_DUAL,
	 */
	public EnumAtvAudioModeType getSIFMtsMode();

	/**
	 * isTtxChannel
	 * 
	 * @param none
	 * @return boolean
	 */
	public boolean isTtxChannel();

	public EnumAvdVideoStandardType getVideoStandard();

	void moveProgram(int progSourcePosition, int progTargetPosition);

	public void setProgramName(int programNum, short programType,
			String porgramName);

	public void makeSourceDtv();

	public void makeSourceAtv();

	public DtvAudioInfo getAudioInfo();

	public TvOsType.EnumLanguage getCurrentLanguageIndex(String languageCode);

	public void switchAudioTrack(int track);

	public DtvSubtitleInfo getSubtitleInfo();

	public boolean openSubtitle(int index);

	public boolean closeSubtitle();

        public boolean saveAtvProgram(int currentProgramNo);
	
    public void setChannelChangeFreezeMode(boolean freezeMode);

	public int readTurnChannelInterval( Context context );
	
	//add by zhaotong for dtv city
	public int[] getNitFrequencyByDtvRegion(TvOsType.EnumChinaDvbcRegion eRegion)
			throws TvCommonException;

	public TvOsType.EnumChinaDvbcRegion getChinaDvbcRegion()
			throws TvCommonException;


	public void setChinaDvbcRegion(TvOsType.EnumChinaDvbcRegion eRegion) 
			throws TvCommonException;
}