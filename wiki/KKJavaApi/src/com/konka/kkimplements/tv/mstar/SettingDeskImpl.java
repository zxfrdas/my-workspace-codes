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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.util.Log;

import com.konka.kkimplements.tv.mstar.GruleIndex.MST_GRule_COLOR_LEVEL_STRETCH_Index_Main;
import com.konka.kkimplements.tv.mstar.GruleIndex.MST_GRule_Index_Main;
import com.konka.kkinterface.tv.CommonDesk;
import com.konka.kkinterface.tv.DataBaseDesk.ColorWheelMode;
import com.konka.kkinterface.tv.DataBaseDesk.EN_MS_CHANNEL_SWITCH_MODE;
import com.konka.kkinterface.tv.DataBaseDesk.EN_MS_FILM;
import com.konka.kkinterface.tv.DataBaseDesk.EN_MS_OFFLINE_DET_MODE;
import com.konka.kkinterface.tv.DataBaseDesk.MEMBER_LANGUAGE;
import com.konka.kkinterface.tv.DataBaseDesk.SmartEnergySavingMode;
import com.konka.kkinterface.tv.SettingDesk;
import com.tvos.atv.AtvManager;
import com.tvos.atv.AtvPlayer;
import com.tvos.common.PictureManager;
import com.tvos.common.PictureManager.EnumFilm;
import com.tvos.common.TvManager;
import com.tvos.common.TvManager.EnumPowerOnLogoMode;
import com.tvos.common.TvManager.EnumPowerOnMusicMode;
import com.tvos.common.exception.TvCommonException;
import com.tvos.common.vo.TvOsType.EnumLanguage;
import com.tvos.factory.FactoryManager;

	
	private Context context;
	@Override
	public boolean SetOsdLanguage(MEMBER_LANGUAGE eLang)
	{
		// TODO Auto-generated method stub
		int value;
		databaseMgr.getUsrData().enLanguage = eLang;
		databaseMgr.getSubtitleSet().SubtitleDefaultLanguage = eLang;
		databaseMgr.getSubtitleSet().SubtitleDefaultLanguage_2 = eLang;
		switch (eLang)
		{
			case E_LANGUAGE_CHINESE_S:
				value = EnumLanguage.E_CHINESE.ordinal();
				break;
			case E_LANGUAGE_CHINESE_T:
				value = EnumLanguage.E_ACHINESE.ordinal();
				break;
			case E_LANGUAGE_ENGLISH:
			default:
				value = EnumLanguage.E_ENGLISH.ordinal();
				break;
		}
		// try
		{
			// dManager.setOsdLanguage(EnumLanguage.values()[value]);
		}
		// catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		databaseMgr.updateUserSysSetting(databaseMgr.getUsrData());
		databaseMgr.updateUserSubtitleSetting(databaseMgr.getSubtitleSet());
		try
		{
			TvManager.setLanguage(EnumLanguage.values()[value]);
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
		if(eMode == EN_MS_CHANNEL_SWITCH_MODE.MS_CHANNEL_SWM_BLACKSCREEN)
		{
			try
            {
	            matvplayer.setChannelChangeFreezeMode(false);
            }
            catch (TvCommonException e)
            {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
            }
		}
		else
		{
			try
            {
	            matvplayer.setChannelChangeFreezeMode(true);
            }
            catch (TvCommonException e)
            {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
            }
		}
		
    
	public int getStandbyNoOperation()
	{
		// TODO Auto-generated method stub
		return databaseMgr.getUsrData().standbyNoOperation;
	}

	@Override
	public boolean setStandbyNoOperation(int minutes)
	{
		// TODO Auto-generated method stub
		databaseMgr.getUsrData().standbyNoOperation = minutes;
		databaseMgr.updateUserSysSetting(databaseMgr.getUsrData());
		return true;
	}

	@Override
	public boolean getStandbyNoSignal()
	{
		// TODO Auto-generated method stub
		return databaseMgr.getUsrData().standbyNoSignal;
	}

	@Override
	public boolean setStandbyNoSignal(boolean status)
	{
		// TODO Auto-generated method stub
		databaseMgr.getUsrData().standbyNoSignal = status;
		databaseMgr.updateUserSysSetting(databaseMgr.getUsrData());
		return true;
	}

	@Override
	public boolean getScreenSaveModeStatus()
	{
		// TODO Auto-generated method stub
		return databaseMgr.getUsrData().screenSaveMode;
	}

	@Override
	public boolean setScreenSaveModeStatus(boolean status)
	{
		// TODO Auto-generated method stub
		databaseMgr.getUsrData().screenSaveMode = status;
		databaseMgr.updateUserSysSetting(databaseMgr.getUsrData());
		return true;
	}

	@Override
	public SmartEnergySavingMode getSmartEnergySaving()
	{
		// TODO Auto-generated method stub
		return databaseMgr.getUsrData().smartEnergySaving;
	}

	@Override
	public boolean setSmartEnergySaving(SmartEnergySavingMode mode)
	{
		// TODO Auto-generated method stub
		databaseMgr.getUsrData().smartEnergySaving = mode;
		databaseMgr.updateUserSysSetting(databaseMgr.getUsrData());
		try
		{
			if(mode == SmartEnergySavingMode.MODE_OFF)
			{
				TvManager.getPictureManager().setBacklight(databaseMgr.getVideo().astPicture[0].backlight);
			}
			else
			{
				TvManager.getPictureManager().setBacklight(100);
			}
		} catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public ColorWheelMode getColorWheelMode()
	{
		// TODO Auto-generated method stub
		return databaseMgr.getUsrData().colorWheelMode;
	}

	@Override
	public boolean setColorWheelMode(ColorWheelMode mode)
	{
		// TODO Auto-generated method stub
		databaseMgr.getUsrData().colorWheelMode = mode;
		databaseMgr.updateUserSysSetting(databaseMgr.getUsrData());
		MST_GRule_Index_Main type = MST_GRule_Index_Main.PQ_GRule_COLOR_LEVEL_STRTECH_Main_Color;
		MST_GRule_COLOR_LEVEL_STRETCH_Index_Main index;
		if(mode == ColorWheelMode.MODE_OFF)
		{
			index = MST_GRule_COLOR_LEVEL_STRETCH_Index_Main.PQ_GRule_COLOR_LEVEL_STRETCH_Off_Main;
		}
		else
		{
			index = MST_GRule_COLOR_LEVEL_STRETCH_Index_Main.PQ_GRule_COLOR_LEVEL_STRETCH_On_Main;
		}
		try {
			TvManager.getPictureManager().setStatusByCustomerPqRule(type.ordinal(), index.ordinal());
		} catch (TvCommonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}
	
	@Override
	public boolean getBurnInMode()
	{
		// TODO Auto-generated method stub
		return databaseMgr.getFactoryExt().bBurnIn;
	}