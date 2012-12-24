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
package com.konka.kkimplements.tv.mstar;import java.io.File;
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
/** *  * @author Shuai.Wang *  *  */public class SettingDeskImpl extends BaseDeskImpl implements SettingDesk{	private DataBaseDeskImpl databaseMgr;	private static SettingDeskImpl settingMgrImpl;	private CommonDesk com = null;	private static int menuTimeOut;	AtvPlayer matvplayer = AtvManager.getAtvPlayerManager();	PictureManager pm = TvManager.getPictureManager();	FactoryManager fm = TvManager.getFactoryManager();	// private DatabaseManager dManager = DatabaseManager.getInstance();
	
	private Context context;	public static SettingDeskImpl getSettingMgrInstance(Context context)	{		if (settingMgrImpl == null)			settingMgrImpl = new SettingDeskImpl(context);		return settingMgrImpl;	}	private SettingDeskImpl(Context context)	{		int value;		databaseMgr = DataBaseDeskImpl.getDataBaseMgrInstance(context);		com = CommonDeskImpl.getInstance(context);		com.printfE("TvService", "SettingServiceImpl constructor!!");		value = databaseMgr.getUsrData().u8OsdDuration;		menuTimeOut = (5 + 5 * value);		if (value == 4) // Idx 4: 30 Second		{			menuTimeOut += 10;		}		if (value >= 5)			menuTimeOut = 0xFFFF;	}	@Override	public MEMBER_LANGUAGE GetOsdLanguage()	{		// TODO Auto-generated method stub		com.printfE("TvService", "GetOsdLanguage Parameter:" + databaseMgr.getUsrData().enLanguage + "!!");		return databaseMgr.getUsrData().enLanguage;	}
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
	}	@Override	public short GetOsdDuration()	{		// TODO Auto-generated method stub		com.printfE("TvService", "GetOsdDuration Parameter:"		        + databaseMgr.getUsrData().u8OsdDuration + "!!");		//databaseMgr.getUsrData().u8OsdDuration = (short)((databaseMgr.getUsrData().u32MenuTimeOut/1000 - 5)/5);		int second = (int) (databaseMgr.getUsrData().u32MenuTimeOut/1000);		switch(second){			case 5:				databaseMgr.getUsrData().u8OsdDuration = 0;				break;			case 10:				databaseMgr.getUsrData().u8OsdDuration = 1;				break;			case 15:				databaseMgr.getUsrData().u8OsdDuration = 2;				break;			case 20:				databaseMgr.getUsrData().u8OsdDuration = 3;				break;			case 30:				databaseMgr.getUsrData().u8OsdDuration = 4;				break;			default:				databaseMgr.getUsrData().u8OsdDuration = 5;				break;					}		return databaseMgr.getUsrData().u8OsdDuration;	}	@Override	public boolean SetOsdDuration(short value)	{		// TODO Auto-generated method stub		com.printfE("TvService", "SetOsdDuration Parameter: Nothing to do in mock!!");		databaseMgr.getUsrData().u8OsdDuration = value;		databaseMgr.getUsrData().u32MenuTimeOut = (5 + 5 * value) * 1000;		menuTimeOut = (5 + 5 * value);		if (value == 4) // Idx 4: 30 Second		{			databaseMgr.getUsrData().u32MenuTimeOut += 5 * 1000;			menuTimeOut += 5;		}		if (value >= 5){			menuTimeOut = 0xFFFF;			databaseMgr.getUsrData().u32MenuTimeOut = 0xFFFF;		}		com.printfE("TvService", "SetOsdDuration : TimeOut = " + databaseMgr.getUsrData().u32MenuTimeOut + "ms!!");		databaseMgr.updateUserSysSetting(databaseMgr.getUsrData());		return true;	}	@Override	public int getOsdTimeoutSecond()	{		menuTimeOut = (int) (databaseMgr.getUsrData().u32MenuTimeOut / 1000);		return menuTimeOut;	}	@Override	public EN_MS_CHANNEL_SWITCH_MODE GetChannelSWMode()	{		// TODO Auto-generated method stub		com.printfE("TvService", "GetChannelSWMode Parameter:" + databaseMgr.getUsrData().eChSwMode + "!!");		return databaseMgr.getUsrData().eChSwMode;	}	@Override	public boolean SetChannelSWMode(EN_MS_CHANNEL_SWITCH_MODE eMode)	{		// TODO Auto-generated method stub		com.printfE("TvService", "SetOsdLanguage SetChannelSWMode: Nothing to do in mock!!");		databaseMgr.getUsrData().eChSwMode = eMode;		databaseMgr.updateUserSysSetting(databaseMgr.getUsrData());
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
				return true;	}	@Override	public EN_MS_OFFLINE_DET_MODE GetOffDetMode()	{		// TODO Auto-generated method stub		com.printfE("TvService", "GetOffDetMode Parameter:" + databaseMgr.getUsrData().eOffDetMode + "!!");		return databaseMgr.getUsrData().eOffDetMode;	}	@Override	public boolean SetOffDetMode(EN_MS_OFFLINE_DET_MODE eMode)	{		// TODO Auto-generated method stub		com.printfE("TvService", "SetOsdLanguage SetOffDetMode: Nothing to do in mock!!");		databaseMgr.getUsrData().eOffDetMode = eMode;		databaseMgr.updateUserSysSetting(databaseMgr.getUsrData());		return true;	}	@Override	public short GetColorRange()	{		return databaseMgr.getUsrData().u8ColorRangeMode;	}	@Override	public boolean SetColorRanger(short value)	{		boolean colorRange0_255 = false;		databaseMgr.getUsrData().u8ColorRangeMode = value;		databaseMgr.updateUserSysSetting(databaseMgr.getUsrData());		if(value!=0)		{			colorRange0_255 = false;		}		else		{			colorRange0_255 = true;		}		try        {            pm.setColorRange(colorRange0_255);        }        catch (TvCommonException e)        {            // TODO Auto-generated catch block            e.printStackTrace();        }		return true;	}	@Override	public EN_MS_FILM GetFilmMode()	{		return databaseMgr.getVideo().eFilm;	}	@Override	public boolean SetFilmMode(EN_MS_FILM eMode)	{		databaseMgr.getVideo().eFilm = eMode;		databaseMgr.updateVideoBasePara(databaseMgr.getVideo(), com.GetCurrentInputSource().ordinal());		try        {	        pm.setFilm(EnumFilm.values()[eMode.ordinal() + 1]);        }        catch (TvCommonException e)        {	        // TODO Auto-generated catch block	        e.printStackTrace();        }		return true;	}	@Override	public boolean GetBlueScreenFlag()	{		return databaseMgr.getUsrData().bBlueScreen;	}	@Override	public boolean SetBlueScreenFlag(boolean bFlag)	{		databaseMgr.getUsrData().bBlueScreen = bFlag;		databaseMgr.updateUserSysSetting(databaseMgr.getUsrData());		return true;	}	@Override	public short GetCecStatus()	{		return databaseMgr.getCECVar().mCECStatus;	}	@Override	public boolean SetCecStatus(short value)	{		databaseMgr.getCECVar().mCECStatus = value;		return true;	}	@Override	public boolean ExecRestoreToDefault()	{		boolean ret = true;		boolean result=false;		File srcFile = new File("/tvdatabase/DatabaseBackup/", "user_setting.db");		File destFile = new File("/tvdatabase/Database/", "user_setting.db");		result = copyFile(srcFile,destFile);		result = false;		if(result == false){			ret = false;		}//		srcFile = new File("/tvdatabase/DatabaseBackup/", "factory.db");//		destFile = new File("/tvdatabase/Database/", "factory.db"); //		result = copyFile(srcFile,destFile);//		result = false;//		if(result == false){//			ret = false;//		}		return ret;	}	/**     * Copy data from a source stream to destFile.     * Return true if succeed, return false if failed.     */	private  boolean copyToFile(InputStream inputStream, File destFile) {        try {            if (destFile.exists()) {                destFile.delete();            }            FileOutputStream out = new FileOutputStream(destFile);            try {                byte[] buffer = new byte[4096];                int bytesRead;                while ((bytesRead = inputStream.read(buffer)) >= 0) {                	Log.d(" out.write(buffer, 0, bytesRead);", " out.write(buffer, 0, bytesRead);");                    out.write(buffer, 0, bytesRead);                }            } finally {                out.flush();                try {                    out.getFD().sync();                } catch (IOException e) {                }                out.close();            }            return true;        } catch (IOException e) {        	Log.d("copyToFile(InputStream inputStream, File destFile)", e.getMessage());            return false;        }    }	// copy a file from srcFile to destFile, return true if succeed, return    // false if fail    private  boolean copyFile(File srcFile, File destFile) {        boolean result = false;        try {        	InputStream in = new FileInputStream(srcFile);            try {                result = copyToFile(in, destFile);            } finally  {                in.close();            }        } catch (IOException e) {        	Log.d("copyFile(File srcFile, File destFile)", e.getMessage());            result = false;        }        chmodFile(destFile);        return result;    }    private void chmodFile(File destFile)    {    	try {            String command = "chmod 666 " + destFile.getAbsolutePath();            Log.i("zyl", "command = " + command);            Runtime runtime = Runtime.getRuntime();              Process proc = runtime.exec(command);           } catch (IOException e) {            Log.i("zyl","chmod fail!!!!");            e.printStackTrace();           }	}
    	@Override    public EnumPowerOnMusicMode GetEnvironmentPowerOnMusicMode()    {	    // TODO Auto-generated method stub		EnumPowerOnMusicMode ret = EnumPowerOnMusicMode.E_POWERON_MUSIC_OFF;    	try        {	        ret =  TvManager.getEnvironmentPowerOnMusicMode();        }        catch (TvCommonException e)        {	        // TODO Auto-generated catch block	        e.printStackTrace();        }    	return ret;    }	@Override    public boolean SetEnvironmentPowerOnMusicMode(EnumPowerOnMusicMode eMusicMode)    {		boolean ret = false;    	try        {	        ret = TvManager.setEnvironmentPowerOnMusicMode(eMusicMode);        }        catch (TvCommonException e)        {	        // TODO Auto-generated catch block	        e.printStackTrace();        }    	Log.d("SetEnvironmentPowerOnMusicMode","Set Music Mode:" + eMusicMode);    	return ret;    }	@Override    public EnumPowerOnLogoMode GetEnvironmentPowerOnLogoMode()    {	    // TODO Auto-generated method stub		EnumPowerOnLogoMode ret = EnumPowerOnLogoMode.E_POWERON_LOGO_OFF;    	try        {	        ret =  TvManager.getEnvironmentPowerOnLogoMode();        }        catch (TvCommonException e)        {	        // TODO Auto-generated catch block	        e.printStackTrace();        }    	return ret;    }	@Override    public boolean SetEnvironmentPowerOnLogoMode(EnumPowerOnLogoMode eLogoMode)    {	    // TODO Auto-generated method stub		boolean ret = false;    	try        {	        ret = TvManager.setEnvironmentPowerOnLogoMode(eLogoMode);        }        catch (TvCommonException e)        {	        // TODO Auto-generated catch block	        e.printStackTrace();        }		Log.d("SetEnvironmentPowerOnLogoMode","Set Logo Mode:" + eLogoMode);    	return ret;    }	@Override    public short GetEnvironmentPowerOnMusicVolume()    {	    // TODO Auto-generated method stub		short ret = 0;		try        {	        ret = fm.getEnvironmentPowerOnMusicVolume();        }        catch (TvCommonException e)        {	        // TODO Auto-generated catch block	        e.printStackTrace();        }		Log.d("GetEnvironmentPowerOnMusicVolume", "Get Music Volume: 0x" + Integer.toHexString(ret));		ret = (short) (100 - ret * 100 / 127); // 0x7F -> 0; 0x00 -> 100	    return ret;    }	@Override    public boolean SetEnvironmentPowerOnMusicVolume(short volume)    {	    // TODO Auto-generated method stub		boolean ret = false;		volume = (short) ((100 - volume) * 127 /100); // 0x7F -> 0; 0x00 -> 100		try        {	        ret = fm.setEnvironmentPowerOnMusicVolume(volume);        }        catch (TvCommonException e)        {	        // TODO Auto-generated catch block	        e.printStackTrace();        }		Log.d("SetEnvironmentPowerOnMusicVolume", "Set Music Volume: 0x" + Integer.toHexString(volume));		return ret;    }	@Override
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
	}}