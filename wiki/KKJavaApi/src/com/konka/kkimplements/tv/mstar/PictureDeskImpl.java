/**
 * MStar Software
 * Copyright (c) 2011 - 2012 MStar Semiconductor, Inc. All rights reserved.
 *
 * All software, firmware and related documentation herein (闁炽儲鍙慡tar Software闁炽儻锟�are 
 * intellectual property of MStar Semiconductor, Inc. (闁炽儲鍙慡tar闁炽儻锟�and protected by 
 * law, including, but not limited to, copyright law and international treaties. 
 * Any use, modification, reproduction, retransmission, or republication of all 
 * or part of MStar Software is expressly prohibited, unless prior written 
 * permission has been granted by MStar. 
 *
 * By accessing, browsing and/or using MStar Software, you acknowledge that you 
 * have read, understood, and agree, to be bound by below terms (闁炽儲缈籩rms闁炽儻锟�and to 
 * comply with all applicable laws and regulations:
 *
 * 1. MStar shall retain any and all right, ownership and interest to MStar 
 * Software and any modification/derivatives thereof.  No right, ownership, 
 * or interest to MStar Software and any modification/derivatives thereof is 
 * transferred to you under Terms.
 *
 * 2. You understand that MStar Software might include, incorporate or be supplied 
 * together with third party闁炽儲锟�software and the use of MStar Software may require 
 * additional licenses from third parties.  Therefore, you hereby agree it is your 
 * sole responsibility to separately obtain any and all third party right and 
 * license necessary for your use of such third party闁炽儲锟�software. 
 *
 * 3. MStar Software and any modification/derivatives thereof shall be deemed as 
 * MStar闁炽儲锟�confidential information and you agree to keep MStar闁炽儲锟�confidential 
 * information in strictest confidence and not disclose to any third party.  
 *	
 * 4. MStar Software is provided on an 闁炽儲绌維 IS闁炽儻鎷穊asis without warranties of any kind. 
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
 * in conjunction with your or your customer闁炽儲锟�product (闁炽儲钘〆rvices闁炽儻锟�  You understand 
 * and agree that, except otherwise agreed by both parties in writing, Services are 
 * provided on an 闁炽儲绌維 IS闁炽儻鎷穊asis and the warranty disclaimer set forth in Section 4 
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
import android.os.Handler;
import android.util.Log;
import android.util.LogPrinter;

import com.konka.kkimplements.tv.mstar.GruleIndex.MST_GRule_DETAILS_Index_Main;
import com.konka.kkimplements.tv.mstar.GruleIndex.MST_GRule_DYNAMIC_CONTRAST_Index_Main;
import com.konka.kkimplements.tv.mstar.GruleIndex.MST_GRule_Index_Main;
import com.konka.kkimplements.tv.mstar.GruleIndex.MST_GRule_SKIN_TONE_Index_Main;
import com.konka.kkinterface.tv.CommonDesk;
import com.konka.kkinterface.tv.DataBaseDesk.EN_MS_COLOR_TEMP;
import com.konka.kkinterface.tv.DataBaseDesk.EN_MS_COLOR_TEMP_INPUT_SOURCE;
import com.konka.kkinterface.tv.DataBaseDesk.EN_MS_Dynamic_Contrast;
import com.konka.kkinterface.tv.DataBaseDesk.EN_MS_LOCALDIMMING;
import com.konka.kkinterface.tv.DataBaseDesk.EN_MS_MPEG_NR;
import com.konka.kkinterface.tv.DataBaseDesk.EN_MS_NR;
import com.konka.kkinterface.tv.DataBaseDesk.EN_MS_PICTURE;
import com.konka.kkinterface.tv.DataBaseDesk.EN_MS_VIDEOITEM;
import com.konka.kkinterface.tv.DataBaseDesk.MAPI_VIDEO_ARC_Type;
import com.konka.kkinterface.tv.DataBaseDesk.SkinToneMode;
import com.konka.kkinterface.tv.DataBaseDesk.T_MS_COLOR_TEMPEX_DATA;
import com.konka.kkinterface.tv.PictureDesk;
import com.tvos.common.PictureManager;
import com.tvos.common.PictureManager.EnumNoiseReduction;
import com.tvos.common.TvManager;
import com.tvos.common.TvPlayer;
import com.tvos.common.exception.TvCommonException;
import com.tvos.common.vo.ColorTemperature;
import com.tvos.common.vo.TvOsType;
import com.tvos.common.vo.TvOsType.EnumInputSource;
import com.tvos.common.vo.TvOsType.EnumScalerWindow;
import com.tvos.common.vo.TvOsType.EnumVideoArcType;
import com.tvos.common.vo.VideoWindowType;
import com.tvos.dtv.vo.DtvEventComponentInfo;
import com.tvos.dtv.vo.DtvType;
import com.tvos.factory.FactoryManager;

{
	boolean enableSetBacklight = true;
	private Context context;
	
		this.context = context;
		
		databaseMgr.updateVideoAstPicture(videoPara.astPicture[idx],com.GetCurrentInputSource().ordinal(),videoPara.ePicture.ordinal());
		
        }
        catch (TvCommonException e)
		
		return true;
	}
	@Override
	public int getDynamicBLModeIdx() {
		// TODO Auto-generated method stub		
		return databaseMgr.getDynamicBLMode();
	}

	@Override
	public boolean setDynamicBLModeIdx(EN_MS_LOCALDIMMING Localdimminglevel) {
		// TODO Auto-generated method stub		
		switch(Localdimminglevel)		
		{
		case LOCALDIMMING_OFF:			
			try {
				Log.d("TvosCommomCommand", "SetLocalDimmingMode_Off Sucess");
				TvManager.setTvosCommonCommand("SetLocalDimmingMode_Off");
			} catch (TvCommonException e) {
				// TODO Auto-generated catch block
				Log.d("TvosCommomCommand", "SetLocalDimmingMode_Off Fail");
				e.printStackTrace();
			}			
			break;
		case LOCALDIMMING_ON:			
			try {
				TvManager.setTvosCommonCommand("SetLocalDimmingMode_On");
				Log.d("TvosCommomCommand", "SetLocalDimmingMode_On Sucess");
			} catch (TvCommonException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.d("TvosCommomCommand", "SetLocalDimmingMode_On Fail");
			}			
			break;
		}
		//databaseMgr.setDynamicBLMode(Localdimminglevel); 这里不需write数据库，supernova做这个操作
		return true;
	}
	@Override
//		videoPara.ePicture = EN_MS_PICTURE.values()[
//		    databaseMgr.queryPicMode(com.GetCurrentInputSource().ordinal())];
	@Override
	public boolean enableSetBacklight(boolean enable)
	{
		// TODO Auto-generated method stub
		enableSetBacklight = enable;
		return true;
	}

		int pictureModeType = 0;//videoPara.ePicture.ordinal();
		videoPara.astPicture[pictureModeType].backlight = value;
		if(enableSetBacklight == true)
		{
		}
		return true;
		idx = 0;//videoPara.ePicture.ordinal();
		//com.printfE("TvService", "GetBacklight = " + videoPara.astPicture[idx].backlight + " !!");
	@Override
	public short GetBacklightOfPicMode(EN_MS_PICTURE ePicture)
	{
		int idx;
		//閸欘亞鏁ゆ稉锟界矋閼冲苯鍘滈敍灞肩瑝閸掑敄ource閿涘奔绗夐崚鍞抜cture mode, 閸欘亞鏁icture mode 0 閻ㄥ嫯鍎楅崗锟�	
		idx = ePicture.ordinal();
		return (short) (videoPara.astPicture[idx].backlight);
	}
	
		
		int idx;
		videoPara.enARCType = MAPI_VIDEO_ARC_Type.values()[databaseMgr.queryVideoArcMode(
				com.GetCurrentInputSource().ordinal())];
			
			switch(eNRIdx)
			{
			case MS_NR_OFF:
				nr = EnumNoiseReduction.E_NR_OFF;
				break;
			case MS_NR_LOW:
				nr = EnumNoiseReduction.E_NR_LOW;
				break;
			case MS_NR_MIDDLE:
				nr = EnumNoiseReduction.E_NR_MIDDLE;
				break;
			case MS_NR_HIGH:
				nr = EnumNoiseReduction.E_NR_HIGH;
				break;
			case MS_NR_AUTO:
				nr = EnumNoiseReduction.E_NR_AUTO;
				break;
			}
			pm.setNoiseReduction(nr);
		}
				final Handler handler = PictureDeskImpl.this.getHandler(1);
				if (handler != null)
					if (autotune_flag)

	@Override
    public void setDisplayWindow(VideoWindowType videoWindowType)
    {
		try {
		//	TvManager.getPictureManager().setDebugMode(true);
			TvManager.getPictureManager().selectWindow(EnumScalerWindow.E_MAIN_WINDOW);
			TvManager.getPictureManager().setDisplayWindow(videoWindowType);
			TvManager.getPictureManager().scaleWindow();
		//	TvManager.getPictureManager().setDebugMode(false);
		} catch (TvCommonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	public boolean SetKTVVideoArc()
	{
		DtvEventComponentInfo dtvEventInfo = new DtvEventComponentInfo();
		dtvEventInfo.setAspectRatioCode(DtvType.EnumAspectRatioCode.E_ASP_16TO9);
		/*
		MAPI_VIDEO_ARC_Type eArcIdx = MAPI_VIDEO_ARC_Type.E_AR_Panorama;
		System.out.println("TvService  SetVideoArc:" + eArcIdx);
		EnumVideoArcType arcType = EnumVideoArcType.E_DEFAULT;
		
		if(eArcIdx.ordinal() < MAPI_VIDEO_ARC_Type.E_AR_DotByDot.ordinal())
			arcType = EnumVideoArcType.values()[eArcIdx.ordinal()];
		else if(eArcIdx.ordinal() == MAPI_VIDEO_ARC_Type.E_AR_DotByDot.ordinal())
			arcType = EnumVideoArcType.E_16x9;
		try
		{
			pm.setAspectRatio(arcType);
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		return true;
	}

	@Override
	public int[] getDynamicContrastCurve() {
		// TODO Auto-generated method stub
		int[] ret = null;
		try
        {
	        ret = TvManager.getPictureManager().getDynamicContrastCurve();
        }
        catch (TvCommonException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
		return ret;
	}
	
	@Override
	public SkinToneMode getSkinToneMode()
	{
		// TODO Auto-generated method stub
		return videoPara.skinTone;
	}
	
	@Override
	public boolean setSkinToneMode(SkinToneMode mode)
	{
		// TODO Auto-generated method stub
		videoPara.skinTone = mode;
		databaseMgr.updateVideoBasePara(videoPara, com.GetCurrentInputSource().ordinal());

		MST_GRule_Index_Main type = MST_GRule_Index_Main.PQ_GRule_SKIN_TONE_Main_Color;
		MST_GRule_SKIN_TONE_Index_Main index;
		switch(mode)
		{
		case SKIN_TONE_OFF:
		default:
			index = MST_GRule_SKIN_TONE_Index_Main.PQ_GRule_SKIN_TONE_Off_Main;
			break;
		case SKIN_TONE_RED:
			index = MST_GRule_SKIN_TONE_Index_Main.PQ_GRule_SKIN_TONE_Red_Main;
			break;
		case SKIN_TONE_YELLOW:
			index = MST_GRule_SKIN_TONE_Index_Main.PQ_GRule_SKIN_TONE_Yellow_Main;
			break;
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
	public boolean getDetailEnhance()
	{
		// TODO Auto-generated method stub
		return videoPara.detailEnhance;
	}
	
	@Override
	public boolean setDetailEnhance(boolean status)
	{
		// TODO Auto-generated method stub
		videoPara.detailEnhance = status;
		databaseMgr.updateVideoBasePara(videoPara, com.GetCurrentInputSource().ordinal());

		MST_GRule_Index_Main type = MST_GRule_Index_Main.PQ_GRule_DETAILS_Main;
		MST_GRule_DETAILS_Index_Main index;
		if(status == true)
		{
			index = MST_GRule_DETAILS_Index_Main.PQ_GRule_DETAILS_On_Main;
		}
		else
		{
			index = MST_GRule_DETAILS_Index_Main.PQ_GRule_DETAILS_Off_Main;
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
	public EN_MS_NR getDNR()
	{
		// TODO Auto-generated method stub
		return videoPara.DNR;
	}
	
	@Override
	public boolean setDNR(EN_MS_NR mode)
	{
		// TODO Auto-generated method stub
		videoPara.DNR = mode;
		databaseMgr.updateVideoBasePara(videoPara, com.GetCurrentInputSource().ordinal());

		try
		{
			EnumNoiseReduction nr = EnumNoiseReduction.E_NR_AUTO;
			
			switch(mode)
			{
			case MS_NR_OFF:
				nr = EnumNoiseReduction.E_NR_OFF;
				break;
			case MS_NR_LOW:
				nr = EnumNoiseReduction.E_NR_LOW;
				break;
			case MS_NR_MIDDLE:
				nr = EnumNoiseReduction.E_NR_MIDDLE;
				break;
			case MS_NR_HIGH:
				nr = EnumNoiseReduction.E_NR_HIGH;
				break;
			case MS_NR_AUTO:
				nr = EnumNoiseReduction.E_NR_AUTO;
				break;
			}
			pm.setNoiseReduction(nr);
		}
		catch (TvCommonException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}
	
	@Override
	public EN_MS_Dynamic_Contrast getDynamicContrast()
	{
		// TODO Auto-generated method stub
		return videoPara.eDynamic_Contrast;
	}
	
	@Override
	public boolean setDynamicContrast(EN_MS_Dynamic_Contrast mode)
	{
		// TODO Auto-generated method stub
		videoPara.eDynamic_Contrast = mode;
		databaseMgr.updateVideoBasePara(videoPara, com.GetCurrentInputSource().ordinal());

		MST_GRule_Index_Main type = MST_GRule_Index_Main.PQ_GRule_DYNAMIC_CONTRAST_Main;
		MST_GRule_DYNAMIC_CONTRAST_Index_Main index;
		switch(mode)
		{
		case MS_Dynamic_Contrast_OFF:
		default:
			index = MST_GRule_DYNAMIC_CONTRAST_Index_Main.PQ_GRule_DYNAMIC_CONTRAST_Off_Main;
			break;
		case MS_Dynamic_Contrast_ON:
			index = MST_GRule_DYNAMIC_CONTRAST_Index_Main.PQ_GRule_DYNAMIC_CONTRAST_On_Main;
			break;
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
	public boolean getEnergyEnable()
	{
		// TODO Auto-generated method stub
		return databaseMgr.getCustomerCfgMiscSetting().energyEnable;
	}

	@Override
	public boolean setEnergyEnable(boolean enable)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public short getEnergyPercent()
	{
		// TODO Auto-generated method stub
		return databaseMgr.getCustomerCfgMiscSetting().energyPercent;
	}

	@Override
	public boolean setEnergyPercent(short percent)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean refreshVideoPara()
	{
		videoPara = databaseMgr.getVideo();
		return true;
	}
}