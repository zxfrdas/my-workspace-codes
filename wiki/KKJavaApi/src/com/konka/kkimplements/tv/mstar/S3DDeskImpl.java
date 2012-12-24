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
package com.konka.kkimplements.tv.mstar;import android.content.Context;

import com.konka.kkinterface.tv.CommonDesk;
import com.konka.kkinterface.tv.DataBaseDesk.EN_ThreeD_Video_3DOUTPUTASPECT;
import com.konka.kkinterface.tv.DataBaseDesk.EN_ThreeD_Video_3DTO2D;
import com.konka.kkinterface.tv.DataBaseDesk.EN_ThreeD_Video_AUTOSTART;
import com.konka.kkinterface.tv.DataBaseDesk.EN_ThreeD_Video_DISPLAYFORMAT;
import com.konka.kkinterface.tv.DataBaseDesk.EN_ThreeD_Video_LRVIEWSWITCH;
import com.konka.kkinterface.tv.DataBaseDesk.EN_ThreeD_Video_SELFADAPTIVE_DETECT;
import com.konka.kkinterface.tv.DataBaseDesk.EN_ThreeD_Video_SELFADAPTIVE_LEVEL;
import com.konka.kkinterface.tv.DataBaseDesk.ThreeD_Video_MODE;
import com.konka.kkinterface.tv.S3DDesk;
import com.tvos.common.ThreeDimensionManager;
import com.tvos.common.TvManager;
import com.tvos.common.TvManager.EnumScreenMuteType;
import com.tvos.common.exception.TvCommonException;
import com.tvos.common.vo.TvOsType.Enum3dAspectRatioType;
import com.tvos.common.vo.TvOsType.Enum3dType;
import com.tvos.common.vo.TvOsType.EnumInputSource;
import com.tvos.common.vo.TvOsType.EnumScalerWindow;
public class S3DDeskImpl extends BaseDeskImpl implements S3DDesk{	private static S3DDeskImpl s3DMgrImpl;	private DataBaseDeskImpl databaseMgrImpl = null;	private ThreeD_Video_MODE ThreeDSetting = null;	private CommonDesk com = null;	ThreeDimensionManager s3m;

	private Context context;
		private S3DDeskImpl(Context context)	{		com = CommonDeskImpl.getInstance(context);		com.printfI("TvService", "S3DManagerImpl constructor!!");		databaseMgrImpl = DataBaseDeskImpl.getDataBaseMgrInstance(context);		ThreeDSetting = databaseMgrImpl.getVideo().ThreeDVideoMode;		s3m = TvManager.getThreeDimensionManager();	}	public static S3DDeskImpl getS3DMgrInstance(Context context)	{		if (s3DMgrImpl == null)			s3DMgrImpl = new S3DDeskImpl(context);		return s3DMgrImpl;	}	@Override	public boolean setSelfAdaptiveDetect(EN_ThreeD_Video_SELFADAPTIVE_DETECT selfAdaptiveDetect)	{		if(selfAdaptiveDetect == EN_ThreeD_Video_SELFADAPTIVE_DETECT.DB_ThreeD_Video_SELF_ADAPTIVE_DETECT_OFF)		{		ThreeDSetting.eThreeDVideoSelfAdaptiveDetect = selfAdaptiveDetect;			ThreeDSetting.eThreeDVideoDisplayFormat = EN_ThreeD_Video_DISPLAYFORMAT.DB_ThreeD_Video_DISPLAYFORMAT_NONE;		}		else		{			ThreeDSetting.eThreeDVideoSelfAdaptiveDetect = EN_ThreeD_Video_SELFADAPTIVE_DETECT.DB_ThreeD_Video_SELF_ADAPTIVE_DETECT_WHEN_SOURCE_CHANGE;			ThreeDSetting.eThreeDVideoDisplayFormat = EN_ThreeD_Video_DISPLAYFORMAT.DB_ThreeD_Video_DISPLAYFORMAT_AUTO;		}				databaseMgrImpl.updateVideo3DAdaptiveDetectMode(ThreeDSetting.eThreeDVideoSelfAdaptiveDetect, EnumInputSource.E_INPUT_SOURCE_HDMI.ordinal());		databaseMgrImpl.updateVideo3DDisplayFormat(ThreeDSetting.eThreeDVideoDisplayFormat, com.GetCurrentInputSource().ordinal());		//databaseMgrImpl.updateVideo3DMode(ThreeDSetting, EnumInputSource.E_INPUT_SOURCE_HDMI.ordinal());		com.printfI("TvS3DManagerIml", "Self Adaptive Detect is "		        + ThreeDSetting.eThreeDVideoSelfAdaptiveLevel);				try		{			TvManager.setVideoMute(true, EnumScreenMuteType.E_BLACK, 0, com.GetCurrentInputSource());		}		catch (TvCommonException e)		{			com.printfE("S3DDeskImpl", "setVideoMute False Exception");			e.printStackTrace();		}				if(selfAdaptiveDetect == EN_ThreeD_Video_SELFADAPTIVE_DETECT.DB_ThreeD_Video_SELF_ADAPTIVE_DETECT_WHEN_SOURCE_CHANGE)		{			try			{				s3m.set3dFormatDetectFlag(true);			}			catch (TvCommonException e)			{				com.printfE("S3DDeskImpl", "Set set3dFormatDetectFlag True Exception");				e.printStackTrace();			}		}		else if (selfAdaptiveDetect == EN_ThreeD_Video_SELFADAPTIVE_DETECT.DB_ThreeD_Video_SELF_ADAPTIVE_DETECT_RIGHT_NOW)		{			if (com.isSignalStable())			{				com.printfE("S3DDeskImpl", "Signal Stable!!!");				try				{					Enum3dType _3dtype;					_3dtype = s3m.detect3dFormat(EnumScalerWindow.E_MAIN_WINDOW);					com.printfE("S3DDeskImpl", "Detect 3D formate is" + _3dtype);					s3m.enable3d(_3dtype);					s3m.set3dFormatDetectFlag(true);				}				catch (TvCommonException e)				{					com.printfE("S3DDeskImpl", "Set set3dFormatDetectFlag True Exception");					e.printStackTrace();				}			}			else			{				com.printfE("S3DDeskImpl", "Signal Not Stable!!!");				try				{					s3m.set3dFormatDetectFlag(true);				}				catch (TvCommonException e)				{					com.printfE("S3DDeskImpl", "Set set3dFormatDetectFlag True Exception");					e.printStackTrace();				}			}		}		else		// if(selfAdaptiveDetect ==		// EN_ThreeD_Video_SELFADAPTIVE_DETECT.DB_ThreeD_Video_SELF_ADAPTIVE_DETECT_OFF)		{			try			{				s3m.set3dFormatDetectFlag(false);				s3m.enable3d(Enum3dType.EN_3D_NONE);			}			catch (TvCommonException e)			{				com.printfE("S3DDeskImpl", "Set set3dFormatDetectFlag False Exception");				e.printStackTrace();			}		}				try		{			TvManager.setVideoMute(false, EnumScreenMuteType.E_BLACK, 0, com.GetCurrentInputSource());		}		catch (TvCommonException e)		{			com.printfE("S3DDeskImpl", "setVideoMute False Exception");			e.printStackTrace();		}				return true;	}	@Override	public EN_ThreeD_Video_SELFADAPTIVE_DETECT getSelfAdaptiveDetect()	{
		ThreeDSetting.eThreeDVideoSelfAdaptiveDetect = 
				databaseMgrImpl.queryVideo3DSelfAdaptiveDetectMode(EnumInputSource.E_INPUT_SOURCE_HDMI.ordinal());		return ThreeDSetting.eThreeDVideoSelfAdaptiveDetect;	}	@Override	public boolean setSelfAdaptiveLevel(EN_ThreeD_Video_SELFADAPTIVE_LEVEL selfAdaptiveLevel)	{		ThreeDSetting.eThreeDVideoSelfAdaptiveLevel = selfAdaptiveLevel;		databaseMgrImpl.updateVideo3DMode(ThreeDSetting, com.GetCurrentInputSource().ordinal());		com.printfI("TvS3DManagerIml", "Self Adaptive Level is "		        + ThreeDSetting.eThreeDVideoSelfAdaptiveLevel);		return true;	}	@Override	public EN_ThreeD_Video_SELFADAPTIVE_LEVEL getSelfAdaptiveLevel()	{		return ThreeDSetting.eThreeDVideoSelfAdaptiveLevel;	}	@Override	public boolean setDisplayFormat(EN_ThreeD_Video_DISPLAYFORMAT displayFormat)	{		ThreeDSetting.eThreeDVideoDisplayFormat = displayFormat;		databaseMgrImpl.updateVideo3DMode(ThreeDSetting, com.GetCurrentInputSource().ordinal());		com.printfI("TvS3DManagerIml", "Display format is "		        + ThreeDSetting.eThreeDVideoDisplayFormat);				try		{			TvManager.setVideoMute(true, EnumScreenMuteType.E_BLACK, 0, com.GetCurrentInputSource());		}		catch (TvCommonException e)		{			com.printfE("S3DDeskImpl", "setVideoMute False Exception");			e.printStackTrace();		}				if(displayFormat == EN_ThreeD_Video_DISPLAYFORMAT.DB_ThreeD_Video_DISPLAYFORMAT_SIDE_BY_SIDE)		{			try			{					s3m.enable3d(Enum3dType.EN_3D_SIDE_BY_SIDE_HALF);			}			catch (TvCommonException e)			{				com.printfE("S3DDeskImpl", "setDisplayFormat False Exception");				e.printStackTrace();			}		}		else if (displayFormat == EN_ThreeD_Video_DISPLAYFORMAT.DB_ThreeD_Video_DISPLAYFORMAT_TOP_BOTTOM)		{			try			{				s3m.enable3d(Enum3dType.EN_3D_TOP_BOTTOM);			}			catch (TvCommonException e)			{				com.printfE("S3DDeskImpl", "setDisplayFormat False Exception");				e.printStackTrace();			}		}		else if (displayFormat == EN_ThreeD_Video_DISPLAYFORMAT.DB_ThreeD_Video_DISPLAYFORMAT_FRAME_PACKING)		{			try			{				s3m.enable3d(Enum3dType.EN_3D_FRAME_PACKING_1080P);			}			catch (TvCommonException e)			{				com.printfE("S3DDeskImpl", "setDisplayFormat False Exception");				e.printStackTrace();			}		}		else if (displayFormat == EN_ThreeD_Video_DISPLAYFORMAT.DB_ThreeD_Video_DISPLAYFORMAT_LINE_ALTERNATIVE)		{			try			{				s3m.enable3d(Enum3dType.EN_3D_LINE_ALTERNATIVE);			}			catch (TvCommonException e)			{				com.printfE("S3DDeskImpl", "setDisplayFormat False Exception");				e.printStackTrace();			}		}		else if (displayFormat == EN_ThreeD_Video_DISPLAYFORMAT.DB_ThreeD_Video_DISPLAYFORMAT_2DTO3D)		{			try			{				s3m.enable3d(Enum3dType.EN_3D_2DTO3D);				s3m.set3dGain(get3DDepthMode());				s3m.set3dOffset(get3DOffsetMode());			}			catch (TvCommonException e)			{				com.printfE("S3DDeskImpl", "setDisplayFormat False Exception");				e.printStackTrace();			}		}		else if (displayFormat == EN_ThreeD_Video_DISPLAYFORMAT.DB_ThreeD_Video_DISPLAYFORMAT_AUTO)		{			if (com.isSignalStable())			{				com.printfE("S3DDeskImpl", "Signal Stable!!!");				try				{					Enum3dType _3dtype;					_3dtype = s3m.detect3dFormat(EnumScalerWindow.E_MAIN_WINDOW);					com.printfE("S3DDeskImpl", "Detect 3D formate is" + _3dtype);					s3m.enable3d(_3dtype);				}				catch (TvCommonException e)				{					com.printfE("S3DDeskImpl", "setDisplayFormat False Exception");					e.printStackTrace();				}			}			else			{				com.printfE("S3DDeskImpl", "Signal Not Stable!!!");			}		}		else		// if(displayFormat ==		// EN_ThreeD_Video_DISPLAYFORMAT.DB_ThreeD_Video_DISPLAYFORMAT_NONE)		{			try			{				s3m.enable3d(Enum3dType.EN_3D_NONE);			}			catch (TvCommonException e)			{				com.printfE("S3DDeskImpl", "setDisplayFormat False Exception");				e.printStackTrace();			}		}				try		{			TvManager.setVideoMute(false, EnumScreenMuteType.E_BLACK, 0, com.GetCurrentInputSource());		}		catch (TvCommonException e)		{			com.printfE("S3DDeskImpl", "setVideoMute False Exception");			e.printStackTrace();		}		return true;	}
	
	public boolean setVideoScaleforKTV()
	{
		try
		{
			s3m.enable3d(Enum3dType.EN_3D_NONE);
		}
		catch (TvCommonException e)
		{
			com.printfE("S3DDeskImpl", "setDisplayFormat False Exception");
			e.printStackTrace();
		}
		return true;
	}
		@Override	public EN_ThreeD_Video_DISPLAYFORMAT getDisplayFormat()	{		if(ThreeDSetting.eThreeDVideoSelfAdaptiveDetect == EN_ThreeD_Video_SELFADAPTIVE_DETECT.DB_ThreeD_Video_SELF_ADAPTIVE_DETECT_OFF)		{			ThreeDSetting.eThreeDVideoDisplayFormat = databaseMgrImpl.queryThreeDVideoDisplayFormat(com.GetCurrentInputSource().ordinal());		}		else		{			ThreeDSetting.eThreeDVideoDisplayFormat = EN_ThreeD_Video_DISPLAYFORMAT.DB_ThreeD_Video_DISPLAYFORMAT_AUTO;		}		return ThreeDSetting.eThreeDVideoDisplayFormat;	}	@Override	public boolean set3DTo2D(EN_ThreeD_Video_3DTO2D display3dto2dMode)	{		ThreeDSetting.eThreeDVideo3DTo2D = display3dto2dMode;		databaseMgrImpl.updateVideo3DMode(ThreeDSetting, com.GetCurrentInputSource().ordinal());		com.printfI("TvS3DManagerIml", "Display 3DTo2D Mode is "		        + ThreeDSetting.eThreeDVideo3DTo2D);				try		{			TvManager.setVideoMute(true, EnumScreenMuteType.E_BLACK, 0, com.GetCurrentInputSource());		}		catch (TvCommonException e)		{			com.printfE("S3DDeskImpl", "setVideoMute False Exception");			e.printStackTrace();		}				if(display3dto2dMode == EN_ThreeD_Video_3DTO2D.DB_ThreeD_Video_3DTO2D_SIDE_BY_SIDE)		{			try			{				s3m.enable3dTo2d(Enum3dType.EN_3D_SIDE_BY_SIDE_HALF);			}			catch (TvCommonException e)			{				com.printfE("S3DDeskImpl", "Set set3DTo2D False Exception");				e.printStackTrace();			}		}		else if (display3dto2dMode == EN_ThreeD_Video_3DTO2D.DB_ThreeD_Video_3DTO2D_TOP_BOTTOM)		{			try			{				s3m.enable3dTo2d(Enum3dType.EN_3D_TOP_BOTTOM);			}			catch (TvCommonException e)			{				com.printfE("S3DDeskImpl", "Set set3DTo2D False Exception");				e.printStackTrace();			}		}		else if (display3dto2dMode == EN_ThreeD_Video_3DTO2D.DB_ThreeD_Video_3DTO2D_FRAME_PACKING)		{			try			{				s3m.enable3dTo2d(Enum3dType.EN_3D_FRAME_PACKING_1080P);			}			catch (TvCommonException e)			{				com.printfE("S3DDeskImpl", "Set set3DTo2D False Exception");				e.printStackTrace();			}		}		else if (display3dto2dMode == EN_ThreeD_Video_3DTO2D.DB_ThreeD_Video_3DTO2D_LINE_ALTERNATIVE)		{			try			{				s3m.enable3dTo2d(Enum3dType.EN_3D_LINE_ALTERNATIVE);			}			catch (TvCommonException e)			{				com.printfE("S3DDeskImpl", "Set set3DTo2D False Exception");				e.printStackTrace();			}		}		else if (display3dto2dMode == EN_ThreeD_Video_3DTO2D.DB_ThreeD_Video_3DTO2D_AUTO)		{			if (com.isSignalStable())			{				com.printfE("S3DDeskImpl", "Signal Stable!!!");				try				{					Enum3dType _3dtype;					_3dtype = s3m.detect3dFormat(EnumScalerWindow.E_MAIN_WINDOW);					com.printfE("S3DDeskImpl", "Detect 3D formate is" + _3dtype);					s3m.enable3dTo2d(_3dtype);				}				catch (TvCommonException e)				{					com.printfE("S3DDeskImpl", "Set set3dFormatDetectFlag True Exception");					e.printStackTrace();				}			}			else			{				com.printfE("S3DDeskImpl", "Signal Not Stable!!!");			}		}		else		// (display3dto2dMode ==		// EN_ThreeD_Video_3DTO2D.DB_ThreeD_Video_3DTO2D_NONE)		{			try			{				s3m.enable3dTo2d(Enum3dType.EN_3D_NONE);			}			catch (TvCommonException e)			{				com.printfE("S3DDeskImpl", "Set set3DTo2D False Exception");				e.printStackTrace();			}		}		try		{			TvManager.setVideoMute(false, EnumScreenMuteType.E_BLACK, 0, com.GetCurrentInputSource());		}		catch (TvCommonException e)		{			com.printfE("S3DDeskImpl", "setVideoMute False Exception");			e.printStackTrace();		}		return true;	}	@Override	public EN_ThreeD_Video_3DTO2D getDisplay3DTo2DMode()	{
		return ThreeDSetting.eThreeDVideo3DTo2D = databaseMgrImpl.queryThreeDVideo3DTo2D(com.GetCurrentInputSource().ordinal());	}	@Override	public boolean set3DDepthMode(int mode3DDepth)	{//		ThreeDSetting.eThreeDVideo3DDepth = EN_ThreeD_Video_3DDEPTH.values()[mode3DDepth];//		databaseMgrImpl.updateVideo3DMode(ThreeDSetting, com.GetCurrentInputSource().ordinal());//		com.printfI("TvS3DManagerIml", "3D Depth mode is "//		        + ThreeDSetting.eThreeDVideo3DDepth);		try		{			s3m.set3dGain(mode3DDepth);
		}		catch (TvCommonException e)		{			com.printfE("S3DDeskImpl", "Set set3DDepthMode False Exception");			e.printStackTrace();		}		return true;	}	@Override	public int get3DDepthMode()	{		int index = 0;
		try {
			index = s3m.get3dGain();
		} catch (TvCommonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return index;
//		return ThreeDSetting.eThreeDVideo3DDepth.ordinal();
	}	@Override	public boolean set3DOffsetMode(int mode3DOffset)	{//		ThreeDSetting.eThreeDVideo3DOffset = EN_ThreeD_Video_3DOFFSET.values()[mode3DOffset];//		databaseMgrImpl.updateVideo3DMode(ThreeDSetting, com.GetCurrentInputSource().ordinal());//		com.printfI("TvS3DManagerIml", "3D Depth mode is "//		        + ThreeDSetting.eThreeDVideo3DDepth);		try		{			s3m.set3dOffset(mode3DOffset);		}		catch (TvCommonException e)		{			com.printfE("S3DDeskImpl", "Set set3DDepthMode False Exception");			e.printStackTrace();		}		return true;	}	@Override	public int get3DOffsetMode()	{
		int index = 0;
		try {
			index = s3m.get3dOffset();
		} catch (TvCommonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return index;
//		return ThreeDSetting.eThreeDVideo3DOffset.ordinal();
	}	@Override	public boolean setAutoStartMode(EN_ThreeD_Video_AUTOSTART autoStartMode)	{		ThreeDSetting.eThreeDVideoAutoStart = autoStartMode;		databaseMgrImpl.updateVideo3DMode(ThreeDSetting, com.GetCurrentInputSource().ordinal());		com.printfI("TvS3DManagerIml", "auto start mode is "		        + ThreeDSetting.eThreeDVideoAutoStart);		return true;	}	@Override	public EN_ThreeD_Video_AUTOSTART getAutoStartMode()	{		return ThreeDSetting.eThreeDVideoAutoStart;	}	@Override	public boolean set3DOutputAspectMode(EN_ThreeD_Video_3DOUTPUTASPECT outputAspectMode)	{		Enum3dAspectRatioType arType = Enum3dAspectRatioType.E_3D_ASPECTRATIO_FULL;		ThreeDSetting.eThreeDVideo3DOutputAspect = outputAspectMode;		databaseMgrImpl.updateVideo3DMode(ThreeDSetting, com.GetCurrentInputSource().ordinal());		com.printfI("TvS3DManagerIml", "output aspect mode is "		        + ThreeDSetting.eThreeDVideo3DOutputAspect);							if(outputAspectMode == EN_ThreeD_Video_3DOUTPUTASPECT.DB_ThreeD_Video_3DOUTPUTASPECT_CENTER)		{			arType = Enum3dAspectRatioType.E_3D_ASPECTRATIO_CENTER;		}		else if (outputAspectMode == EN_ThreeD_Video_3DOUTPUTASPECT.DB_ThreeD_Video_3DOUTPUTASPECT_AUTOADAPTED)		{			arType = Enum3dAspectRatioType.E_3D_ASPECTRATIO_AUTO;		}		else		// if(outputAspectMode ==		// EN_ThreeD_Video_3DOUTPUTASPECT.DB_ThreeD_Video_3DOUTPUTASPECT_FULLSCREEN)		{			arType = Enum3dAspectRatioType.E_3D_ASPECTRATIO_FULL;		}		try		{			s3m.set3dArc(arType);		}		catch (TvCommonException e)		{			com.printfE("S3DDeskImpl", "Set set3DDepthMode False Exception");			e.printStackTrace();		}		return true;	}	@Override	public EN_ThreeD_Video_3DOUTPUTASPECT get3DOutputAspectMode()	{		return ThreeDSetting.eThreeDVideo3DOutputAspect;	}	@Override	public boolean setLRViewSwitch(EN_ThreeD_Video_LRVIEWSWITCH LRViewSwitchMode)	{		ThreeDSetting.eThreeDVideoLRViewSwitch = LRViewSwitchMode;		databaseMgrImpl.updateVideo3DMode(ThreeDSetting, com.GetCurrentInputSource().ordinal());		com.printfI("TvS3DManagerIml", "set View switch is "		        + ThreeDSetting.eThreeDVideoLRViewSwitch);		try		{			if(ThreeDSetting.eThreeDVideoLRViewSwitch==EN_ThreeD_Video_LRVIEWSWITCH.DB_ThreeD_Video_LRVIEWSWITCH_EXCHANGE)			s3m.enable3dLrSwitch();			else 				s3m.disable3dLrSwitch();							}		catch (TvCommonException e)		{			com.printfE("S3DDeskImpl", "Get LRViewSwitch False Exception");			e.printStackTrace();		}		return true;	}	@Override	public EN_ThreeD_Video_LRVIEWSWITCH getLRViewSwitch()	{		boolean bLRSwitch = false;		try		{			bLRSwitch = s3m.is3dLrSwitched();		}		catch (TvCommonException e)		{			com.printfE("S3DDeskImpl", "Get LRViewSwitch False Exception");			e.printStackTrace();		}		if (bLRSwitch)		{			ThreeDSetting.eThreeDVideoLRViewSwitch = EN_ThreeD_Video_LRVIEWSWITCH.DB_ThreeD_Video_LRVIEWSWITCH_EXCHANGE;		}		else		{			ThreeDSetting.eThreeDVideoLRViewSwitch = EN_ThreeD_Video_LRVIEWSWITCH.DB_ThreeD_Video_LRVIEWSWITCH_NOTEXCHANGE;		}		return ThreeDSetting.eThreeDVideoLRViewSwitch;	}

	@Override
	public void enableLow3dQuality() {
		try {
			s3m.enableLow3dQuality();
		} catch (TvCommonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void disableLow3dQuality() {
		try {
			s3m.disableLow3dQuality();
		} catch (TvCommonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}