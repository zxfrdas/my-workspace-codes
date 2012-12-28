/**
 * MStar Software
 * Copyright (c) 2011 - 2012 MStar Semiconductor, Inc. All rights reserved.
 *
 * All software, firmware and related documentation herein (鈥淢Star Software锟� are 
 * Any use, modification, reproduction, retransmission, or republication of all 
 * or part of MStar Software is expressly prohibited, unless prior written 
 * permission has been granted by MStar. 
 *
 * By accessing, browsing and/or using MStar Software, you acknowledge that you 
 * have read, understood, and agree, to be bound by below terms (鈥淭erms锟� and to 
 *
 * 1. MStar shall retain any and all right, ownership and interest to MStar 
 * Software and any modification/derivatives thereof.  No right, ownership, 
 * or interest to MStar Software and any modification/derivatives thereof is 
 * transferred to you under Terms.
 *
 * 2. You understand that MStar Software might include, incorporate or be supplied 
 * together with third party鈥檚 software and the use of MStar Software may require 
 * sole responsibility to separately obtain any and all third party right and 
 * license necessary for your use of such third party鈥檚 software. 
 * 3. MStar Software and any modification/derivatives thereof shall be deemed as 
 * MStar鈥檚 confidential information and you agree to keep MStar鈥檚 confidential 
 *	
 * 4. MStar Software is provided on an 鈥淎S IS锟�basis without warranties of any kind. 
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
 * provided on an 鈥淎S IS锟�basis and the warranty disclaimer set forth in Section 4 
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
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.util.Log;

import com.konka.kkinterface.tv.CommonDesk;
import com.konka.kkinterface.tv.DataBaseDesk;
import com.tvos.common.TvManager;
import com.tvos.common.exception.TvCommonException;
import com.tvos.common.vo.TvOsType.EnumChinaDvbcRegion;
import com.tvos.common.vo.TvOsType.EnumInputSource;
import com.tvos.common.vo.TvOsType.EnumLanguage;

	private final String TAG = "com.konka.kkimplements.tv.mstar.DataBaseDeskImpl";
	private ContentResolver cr ;
	private Context context;
	//private static SQLiteDatabase factoryCusDB;
	private String factoryCusSchema = "content://konka.tv.factory";
			Log.d("KKJAVAAPI", "create databasedesk now");
		openDB();
		this.context = context;
		cr = context.getContentResolver();
		openDB();
		loadEssentialDataFromDB();
	
	@Override
		
		Cursor cursor = cr.query(Uri.parse(userSettingSchema+"/systemsetting"), null, null, null, null);
		queryCustomerCfgMiscSetting();
		if((factoryCusDB == null) || (!factoryCusDB.isOpen())){
			factoryCusDB = SQLiteDatabase.openDatabase("/customercfg/panel/factory.db", null, SQLiteDatabase.OPEN_READWRITE|SQLiteDatabase.NO_LOCALIZED_COLLATORS);
		}
			userSettingDB = SQLiteDatabase.openDatabase("/tvdatabase/Database/user_setting.db", null, SQLiteDatabase.OPEN_READWRITE|SQLiteDatabase.NO_LOCALIZED_COLLATORS);
		}
	}*/
	
		factoryCusDB.close();
	public void closeDB(){};
	
		Cursor cursor = cr.query(Uri.parse("content://mstar.tv.factory/adcadjust"), null, null, null, null);
		long ret = -1;
		try
		{
			/*ret = factoryDB.update("tbl_ADCAdjust", vals, "SourceID = " + sourceId, null);*/
			ret = cr.update(Uri.parse("content://mstar.tv.factory/adcadjust/sourceid/"+sourceId), vals, null, null);
		}
		catch(SQLException e)
		{
			Log.e("DataBaseDeskImpl", "update failed");
		}
		if(ret == -1)
		{
			return;
		}

		try
		*/
		String selection = " InputSrcType = ? ";
		String[] selectionArgs = {String.valueOf(com.GetCurrentInputSource().ordinal())};
		Cursor cursor = cr.query(Uri.parse(factoryCusSchema+"/nonlinearadjust"), null, selection, selectionArgs, "CurveTypeIndex");
		long ret = -1;
		try
		{
			/*ret = factoryCusDB.update("tbl_NonLinearAdjust", vals, "CurveTypeIndex = " + curveTypeIndex + " AND InputSrcType = "
			        + com.GetCurrentInputSource().ordinal(), null);*/
			
			String where = " CurveTypeIndex = ? and InputSrcType = ? ";
			String[] selectionArgs = {String.valueOf(curveTypeIndex),String.valueOf(com.GetCurrentInputSource().ordinal())};
			ret = cr.update(Uri.parse(factoryCusSchema+"/nonlinearadjust"), vals, where, selectionArgs);
		}
		catch(SQLException e)
		{
			Log.e("DataBaseDeskImpl", "update failed");
		}
		if(ret == -1)
		{
			return;
		}
		
		try
		Cursor cursor = cr.query(Uri.parse("content://mstar.tv.factory/nonstandardadjust"), null, null, null, null);
		
		Cursor cursor = cr.query(Uri.parse("content://mstar.tv.factory/nonstandardadjust"), null, null, null, null);
		long ret = -1;
		try
		{
			/*ret = factoryDB.update("tbl_NonStandardAdjust", vals, null, null);*/
			
			ret = cr.update(Uri.parse("content://mstar.tv.facory/nonstandardadjust"), vals, null, null);
		}
		catch(SQLException e)
		{
			Log.e("DataBaseDeskImpl", "update failed");
		}
		if(ret == -1)
		{
			return;
		}
		
		try
		long ret = -1;
		try
		{
			/*ret = factoryDB.update("tbl_NonStandardAdjust", vals, null, null);*/
			ret = cr.update(Uri.parse("content://mstar.tv.facory/nonstandardadjust"), vals, null, null);
		}
		catch(SQLException e)
		{
			Log.e("DataBaseDeskImpl", "update failed");
		}
		if(ret == -1)
		{
			return;
		}
		
		try
		Cursor cursor = cr.query(Uri.parse("content://mstar.tv.factory/factoryextern"), null, null, null, null);
			m_stFactoryExt.vdDspVersion = (short) cursor.getInt(cursor.getColumnIndex("vdDspVersion"));
			m_stFactoryExt.bBurnIn = cursor.getInt(cursor.getColumnIndex("bBurnIn")) == 0 ? false : true;
		vals.put("vdDspVersion", model.vdDspVersion);
		vals.put("bBurnIn", model.bBurnIn ? 1 : 0);
		long ret = -1;
		try
		{
			/*ret = factoryDB.update("tbl_FactoryExtern", vals, null, null);*/
			
			ret = cr.update(Uri.parse("content://mstar.tv.factory/factoryextern"), vals, null, null);
		}
		catch(SQLException e)
		{
			Log.e("DataBaseDeskImpl", "update failed");
		}
		if(ret == -1)
		{
			return;
		}
		
		try
			{
				/*Cursor cursor = factoryDB.query("tbl_OverscanAdjust", null, "FactoryOverScanType = " + FactoryOverScanType, null, null,
		        null, "_id");*/
		Uri uri = Uri.parse("content://mstar.tv.factory/overscanadjust");
		String selection = " FactoryOverScanType = ? ";
		String[] selectionArgs = {String.valueOf(FactoryOverScanType)};
		Cursor cursor = cr.query(uri, null, selection, selectionArgs, "_id");
		int maxDTV1 = MAX_DTV_Resolution_Info.E_DTV_MAX.ordinal();
		int maxDTV2 = MAPI_VIDEO_ARC_Type.E_AR_MAX.ordinal();
		for (int i = 0; i < maxDTV1; i++)
		{
			for (int j = 0; j < maxDTV2; j++)
			{
				if (cursor.moveToNext())
				{

					m_DTVOverscanSet[i][j].u16H_CapStart = cursor
					        .getInt(cursor.getColumnIndex("u16H_CapStart"));
					m_DTVOverscanSet[i][j].u16V_CapStart = cursor
					        .getInt(cursor.getColumnIndex("u16V_CapStart"));
					m_DTVOverscanSet[i][j].u8HCrop_Left = (short) cursor.getInt(cursor
					        .getColumnIndex("u8HCrop_Left"));
					m_DTVOverscanSet[i][j].u8HCrop_Right = (short) cursor.getInt(cursor
					        .getColumnIndex("u8HCrop_Right"));
					m_DTVOverscanSet[i][j].u8VCrop_Up = (short) cursor.getInt(cursor
					        .getColumnIndex("u8VCrop_Up"));
					m_DTVOverscanSet[i][j].u8VCrop_Down = (short) cursor.getInt(cursor
					        .getColumnIndex("u8VCrop_Down"));
				}
			}
		}
		cursor.close();
			}
			{
				/*Cursor cursor1 = factoryDB.query("tbl_OverscanAdjust", null, "FactoryOverScanType = " + FactoryOverScanType, null, null,
		        null, "_id");*/
		Uri uri = Uri.parse("content://mstar.tv.factory/overscanadjust");
		String selection = " FactoryOverScanType = ? ";
		String[] selectionArgs = {String.valueOf(FactoryOverScanType)};
		Cursor cursor1 = cr.query(uri, null, selection, selectionArgs, "_id");
		int maxHDMI1 = MAX_HDMI_Resolution_Info.E_HDMI_MAX.ordinal();
		int maxHDMI2 = MAPI_VIDEO_ARC_Type.E_AR_MAX.ordinal();
		for (int i = 0; i < maxHDMI1; i++)
		{
			for (int j = 0; j < maxHDMI2; j++)
			{
				if (cursor1.moveToNext())
				{
	
					m_HDMIOverscanSet[i][j].u16H_CapStart = cursor1.getInt(cursor1
					        .getColumnIndex("u16H_CapStart"));
					m_HDMIOverscanSet[i][j].u16V_CapStart = cursor1.getInt(cursor1
					        .getColumnIndex("u16V_CapStart"));
					m_HDMIOverscanSet[i][j].u8HCrop_Left = (short) cursor1.getInt(cursor1
					        .getColumnIndex("u8HCrop_Left"));
					m_HDMIOverscanSet[i][j].u8HCrop_Right = (short) cursor1.getInt(cursor1
					        .getColumnIndex("u8HCrop_Right"));
					m_HDMIOverscanSet[i][j].u8VCrop_Up = (short) cursor1.getInt(cursor1
					        .getColumnIndex("u8VCrop_Up"));
					m_HDMIOverscanSet[i][j].u8VCrop_Down = (short) cursor1.getInt(cursor1
					        .getColumnIndex("u8VCrop_Down"));
				}
			}
		}
		cursor1.close();
			}
			{
				
				/*Cursor cursor2 = factoryDB.query("tbl_OverscanAdjust", null, "FactoryOverScanType = " + FactoryOverScanType, null, null,
				        null, "_id");*/
				Uri uri = Uri.parse("content://mstar.tv.factory/overscanadjust");
				String selection = " FactoryOverScanType = ? ";
				String[] selectionArgs = {String.valueOf(FactoryOverScanType)};
				Cursor cursor = cr.query(uri, null, selection, selectionArgs, "_id");
				int maxYPbPr1 = MAX_YPbPr_Resolution_Info.E_YPbPr_MAX.ordinal();
				int maxYPbPr2 = MAPI_VIDEO_ARC_Type.E_AR_MAX.ordinal();
				for (int i = 0; i < maxYPbPr1; i++)
				{
					for (int j = 0; j < maxYPbPr2; j++)
					{
						if (cursor.moveToNext())
						{
					
							m_YPbPrOverscanSet[i][j].u16H_CapStart = cursor.getInt(cursor
							        .getColumnIndex("u16H_CapStart"));
							m_YPbPrOverscanSet[i][j].u16V_CapStart = cursor.getInt(cursor
							        .getColumnIndex("u16V_CapStart"));
							m_YPbPrOverscanSet[i][j].u8HCrop_Left = (short) cursor.getInt(cursor
							        .getColumnIndex("u8HCrop_Left"));
							m_YPbPrOverscanSet[i][j].u8HCrop_Right = (short) cursor.getInt(cursor
							        .getColumnIndex("u8HCrop_Right"));
							m_YPbPrOverscanSet[i][j].u8VCrop_Up = (short) cursor.getInt(cursor
							        .getColumnIndex("u8VCrop_Up"));
							m_YPbPrOverscanSet[i][j].u8VCrop_Down = (short) cursor.getInt(cursor
							        .getColumnIndex("u8VCrop_Down"));
						}
					}
				}
				cursor.close();
			}
			{
				/*Cursor cursor3 = factoryDB.query("tbl_OverscanAdjust", null, "FactoryOverScanType = " + FactoryOverScanType, null, null,
				        null, "_id");*/
				
				Uri uri = Uri.parse("content://mstar.tv.factory/overscanadjust");
				String selection = " FactoryOverScanType = ? ";
				String[] selectionArgs = {String.valueOf(FactoryOverScanType)};
				Cursor cursor = cr.query(uri, null, selection, selectionArgs, "_id");
				int maxVD1 = EN_VD_SIGNALTYPE.SIG_NUMS.ordinal();
				int maxVD2 = MAPI_VIDEO_ARC_Type.E_AR_MAX.ordinal();
				for (int i = 0; i < maxVD1; i++)
				{
					for (int j = 0; j < maxVD2; j++)
					{
						if (cursor.moveToNext())
						{
				
							m_VDOverscanSet[i][j].u16H_CapStart = cursor.getInt(cursor
							        .getColumnIndex("u16H_CapStart"));
							m_VDOverscanSet[i][j].u16V_CapStart = cursor.getInt(cursor
							        .getColumnIndex("u16V_CapStart"));
							m_VDOverscanSet[i][j].u8HCrop_Left = (short) cursor.getInt(cursor
							        .getColumnIndex("u8HCrop_Left"));
							m_VDOverscanSet[i][j].u8HCrop_Right = (short) cursor.getInt(cursor
							        .getColumnIndex("u8HCrop_Right"));
							m_VDOverscanSet[i][j].u8VCrop_Up = (short) cursor.getInt(cursor
							        .getColumnIndex("u8VCrop_Up"));
							m_VDOverscanSet[i][j].u8VCrop_Down = (short) cursor.getInt(cursor
							        .getColumnIndex("u8VCrop_Down"));
						}
					}
				}
				cursor.close();
			}
				long ret = -1;
				try
				{
					/*ret = factoryDB.update("tbl_OverscanAdjust", vals, "FactoryOverScanType = " + FactoryOverScanType + " AND _id =" + _id, null);*/
					
					Uri uri = Uri.parse("content://mstar.tv.factory/overscanadjust/factoryoverscantype/"+FactoryOverScanType+"/_id/"+_id);
					ret = cr.update(uri, vals, null, null);
				}
				catch(SQLException e)
				{
					Log.e("DataBaseDeskImpl", "update failed");
				}
				if(ret == -1)
				{
					return;
				}				
			}
		Uri uri = Uri.parse("content://mstar.tv.factory/peqadjust/"+index);
		Cursor cursor = cr.query(uri, null, null, null, null);
		
		Cursor cursor = cr.query(Uri.parse("content://mstar.tv.factory/peqadjust"), null, null, null, null);
		long ret = -1;
		try
		{
			/*ret = factoryDB.update("tbl_PEQAdjust", vals, "_id = " + index, null);*/
			Uri uri = Uri.parse("content://mstar.tv.factory/peqadjust/"+index);
			ret = cr.update(uri, vals, null, null);
		}
		catch(SQLException e)
		{
			Log.e("DataBaseDeskImpl", "update failed");
		}
		if(ret == -1)
		{
			return;
		}

		try
		
		Cursor cursor = cr.query(Uri.parse("content://mstar.tv.factory/sscadjust"), null, null, null, null);
		long ret = -1;
		try
		{
			/*ret = factoryDB.update("tbl_SSCAdjust", vals, null, null);*/
			
			ret = cr.update(Uri.parse("content://mstar.tv.factory/sscadjust"), vals, null, null);
		}
		catch(SQLException e)
		{
			Log.e("DataBaseDeskImpl", "update failed");
		}
		if(ret == -1)
		{
			return;
		}

		try
		Uri uri = Uri.parse("content://mstar.tv.factory/factorycolortemp");
		Cursor cursor = cr.query(uri, null, null, null, "ColorTemperatureID");

		long ret = -1;
		try
		{
			Uri uri = Uri.parse("content://mstar.tv.factory/factorycolortemp/colortemperatureid/"+colorTmpId);
			ret = cr.update(uri, vals, null, null);
		}
		catch(SQLException e)
		{
			Log.e("DataBaseDeskImpl", "update failed");
		}
		if(ret == -1)
		{
			return;
		}
		
		String selection = "";
		String[] selectionArgs = new String[1];
		
			
			selection = " InputSourceID = ? ";
			selectionArgs[0] = String.valueOf(sourceIdx);
			Cursor cursor = cr.query(Uri.parse(factoryCusSchema+"/factorycolortempex"), null, selection, selectionArgs, "ColorTemperatureID");
			
		long ret = -1;
		try
		{
			/*ret = factoryCusDB.update("tbl_FactoryColorTempEx", vals, "InputSourceID = " + sourceId + " AND ColorTemperatureID = "
			        + colorTmpId, null);*/
			String where = " InputSourceID = ? and ColorTemperatureID = ? ";
			String[] selectionArgs = {String.valueOf(sourceId),String.valueOf(colorTmpId)};
			
			ret = cr.update(Uri.parse(factoryCusSchema+"/factorycolortempex"), vals, where, selectionArgs);
		}
		catch(SQLException e)
		{
			Log.e("DataBaseDeskImpl", "update failed");
		}
		if(ret == -1)
		{
			return;
		}
		
		try
		/*Cursor cursorVideo = userSettingDB.query("tbl_VideoSetting", null, "InputSrcType = " + inputSrcType, null, null, null,
		
		Cursor cursorVideo = cr.query(Uri.parse(userSettingSchema+"/videosetting/inputsrc/"+inputSrcType), null, null, null, null);
		while (cursorVideo.moveToNext())
			videopara.skinTone = SkinToneMode.values()[cursorVideo.getInt(cursorVideo
			        .getColumnIndex("skinTone"))];
			videopara.detailEnhance = (cursorVideo.getInt(cursorVideo
			        .getColumnIndex("detailEnhance")) == 0) ? false : true;
			videopara.DNR = EN_MS_NR.values()[cursorVideo.getInt(cursorVideo
			        .getColumnIndex("DNR"))];
			// T_MS_SUB_COLOR g_astSubColor of videoPara
		// query tbl_PicMode_Setting for T_MS_PICTURE astPicture[] of videoPara
		
		String selection = " InputSrcType = ? ";
		String[] selectionArgs = {"0"};
		Cursor cursorPicMode = cr.query(Uri.parse(userSettingSchema+"/picmode_setting"), null, selection, selectionArgs, "PictureModeType");
			videopara.astPicture[picModeIdx].backlight = (short) cursorPicMode.getInt(cursorPicMode
			videopara.astPicture[picModeIdx].contrast = (short) cursorPicMode.getInt(cursorPicMode
			videopara.astPicture[picModeIdx].brightness = (short) cursorPicMode.getInt(cursorPicMode
		
		selection = " InputSrcType = ? ";
		selectionArgs[0] = String.valueOf(inputSrcType);
		
		Cursor cursorNRMode = cr.query(Uri.parse(userSettingSchema+"/nrmode"), null, selection, selectionArgs, "NRMode");
		
		Cursor cursor3DMode = cr.query(Uri.parse(userSettingSchema+"/threedvideomode/inputsrc/"+inputSrcType), null, null, null, null);
		
		
		Cursor cursor3DSelfAdaptiveMode = cr.query(Uri.parse(userSettingSchema+"/threedvideomode/inputsrc/"+EnumInputSource.E_INPUT_SOURCE_HDMI.ordinal()), null, null, null, null);
			
		
		
		Cursor cursorUserOverScanMode = cr.query(Uri.parse(userSettingSchema+"/useroverscanmode/inputsrc/"+inputSrcType), null, null, null, null);
		
		
		Cursor cursor3DMode = cr.query(Uri.parse(userSettingSchema+"/threedvideomode/inputsrc/"+inputSrcType), null, null, null, null);
	public EN_ThreeD_Video_3DTO2D queryThreeDVideo3DTo2D(int inputSrcType) {
		EN_ThreeD_Video_3DTO2D threeDVideo3DTo2D = EN_ThreeD_Video_3DTO2D.DB_ThreeD_Video_3DTO2D_NONE;
		/*Cursor cursor3DMode = userSettingDB.query("tbl_ThreeDVideoMode", null, "InputSrcType = " + inputSrcType, null, null, null,
		        null);*/
		
		
		Cursor cursor3DMode = cr.query(Uri.parse(userSettingSchema+"/threedvideomode/inputsrc/"+inputSrcType), null, null, null, null);
		if (cursor3DMode.moveToFirst())
		{
			threeDVideo3DTo2D = EN_ThreeD_Video_3DTO2D.values()[cursor3DMode
			        .getInt(cursor3DMode.getColumnIndex("eThreeDVideo3DTo2D"))];
		}
		cursor3DMode.close();
		return threeDVideo3DTo2D;
	}
	
		long ret = -1;
		try
		{
			/*ret = userSettingDB.update("tbl_PicMode_Setting", vals, "InputSrcType = " + 0 + " AND PictureModeType = "
			        + pictureModeType, null);*/
			
			ret = cr.update(Uri.parse(userSettingSchema+"/picmode_setting/inputsrc/0/picmode/"+pictureModeType), vals, null, null);
		}
		catch(SQLException e)
		{
			Log.e("DataBaseDeskImpl", "update failed");
		}
		if(ret == -1)
		{
			return;
		}
	}
		
		Uri uri = Uri.parse(userSettingSchema+"/picmode_setting/inputsrc/0/picmode/"+pictureModeType);
		Cursor cursorPicMode = cr.query(uri, null, null, null, "PictureModeType");
		vals.put("skinTone", model.skinTone.ordinal());
		vals.put("detailEnhance", model.detailEnhance ? 1 : 0);
		vals.put("DNR", model.DNR.ordinal());
		
		long ret = -1;
		try
		{
			/*ret = userSettingDB.update("tbl_VideoSetting", vals, "InputSrcType = " + inputSrcType, null);*/
			
			ret = cr.update(Uri.parse(userSettingSchema+"/videosetting/inputsrc/"+inputSrcType), vals, null, null);
		}
		catch(SQLException e)
		{
			Log.e("DataBaseDeskImpl", "update failed");
		}
		if(ret == -1)
		{
			return;
		}

		try
	/**
	 * // query picture mode
	 * 
	 * @param inputSrcType
	 */
	public int queryPicMode(int inputSrcType) {
		/*Cursor cursor = userSettingDB.query("tbl_VideoSetting", null, "InputSrcType=?", new String[]{"" + inputSrcType}, null, null, null);*/
		
		Cursor cursor = cr.query(Uri.parse(userSettingSchema+"/videosetting/inputsrc/"+inputSrcType), null, null, null, null);
		cursor.moveToNext();
		int result = cursor.getInt(cursor.getColumnIndex("ePicture")); 
		cursor.close();
	}
	/**
	 * // query sound mode
	 * 
	 */
	public int querySoundMode() {
		/*Cursor cursor = userSettingDB.query("tbl_SoundSetting", null, null, null, null, null, null);*/
		
		Cursor cursor = cr.query(Uri.parse(userSettingSchema+"/soundsetting"), null, null, null, null);
		cursor.moveToNext();
		int result = cursor.getInt(cursor.getColumnIndex("SoundMode")); 
		cursor.close();
	}
	/**
	 * // query video arc mode
	 * 
	 */
	public int queryVideoArcMode(int value) {
		/*Cursor cursor = userSettingDB.query("tbl_VideoSetting", null, "InputSrcType=?", new String[]{"" + value}, null, null, null);*/
		
		Cursor cursor = cr.query(Uri.parse(userSettingSchema+"/videosetting/inputsrc/"+value), null, null, null, null);
		cursor.moveToNext();
		int result = cursor.getInt(cursor.getColumnIndex("enARCType")); 
		cursor.close();
	}
	
		
		long ret = -1;
		try
		{
		/*	ret = userSettingDB.update("tbl_PicMode_Setting", vals, "InputSrcType = " + 0 + " AND PictureModeType = "
			        + pictureModeType, null); */
			
			ret = cr.update(Uri.parse(userSettingSchema+"/picmode_setting/inputsrc/0/picmode/"+pictureModeType), vals, null, null);
		}
		catch(SQLException e)
		{
			Log.e("DataBaseDeskImpl", "update failed");
		}
		if(ret == -1)
		{
			return;
		}

		try
        catch (TvCommonException e)
		long ret = -1;
		try
		{
			/*ret = userSettingDB.update("tbl_NRMode", vals, "InputSrcType = " + inputSrcType + " AND NRMode = " + NRModeIdx, null);*/
			
			ret = cr.update(Uri.parse(userSettingSchema+"/nrmode/nrmode/"+NRModeIdx+"/inputsrc/"+inputSrcType), vals, null, null);
		}
		catch(SQLException e)
		{
			Log.e("DataBaseDeskImpl", "update failed");
		}
		if(ret == -1)
		{
			return;
		}

		try
		long ret = -1;
		try
		{
			/*ret = userSettingDB.update("tbl_VideoSetting", vals, "InputSrcType = " + inputSrcType, null);*/
			
			ret = cr.update(Uri.parse(userSettingSchema+"/videosetting/inputsrc/"+inputSrcType), vals, null, null);
		}
		catch(SQLException e)
		{
			Log.e("DataBaseDeskImpl", "update failed");
		}
		if(ret == -1)
		{
			return;
		}

		try
		long ret = -1;
		try
		{
			/*ret = userSettingDB.update("tbl_ThreeDVideoMode", vals, "InputSrcType = " + inputSrcType, null); */
			
			ret = cr.update(Uri.parse(userSettingSchema+"/threedvideomode/inputsrc/"+inputSrcType), vals, null, null);
		}
		catch(SQLException e)
		{
			Log.e("DataBaseDeskImpl", "update failed");
		}
		if(ret == -1)
		{
			return;
		}

		try
		long ret = -1;
		try
		{
			/*ret = userSettingDB.update("tbl_ThreeDVideoMode", vals, "InputSrcType = " + inputSrcType, null);*/
			
			ret = cr.update(Uri.parse(userSettingSchema+"/threedvideomode/inputsrc/"+inputSrcType), vals, null, null);
		}
		catch(SQLException e)
		{
			Log.e("DataBaseDeskImpl", "update failed");
		}
		if(ret == -1)
		{
			return;
		}

		try
	public EN_ThreeD_Video_SELFADAPTIVE_DETECT queryVideo3DSelfAdaptiveDetectMode(int inputSrcType) {
		EN_ThreeD_Video_SELFADAPTIVE_DETECT mode = EN_ThreeD_Video_SELFADAPTIVE_DETECT.DB_ThreeD_Video_SELF_ADAPTIVE_DETECT_OFF;
		/*Cursor cursor = userSettingDB.query("tbl_ThreeDVideoMode", null, "InputSrcType=?", new String[]{"" + inputSrcType}, null, null, null);*/
		Cursor cursor = cr.query(Uri.parse(userSettingSchema+"/threedvideomode/inputsrc/"+inputSrcType), null, null, null, null);
		if(cursor.moveToNext()) {
			int index = cursor.getColumnIndex("eThreeDVideoSelfAdaptiveDetect");
			Log.d("DEBUG", "===========================index============================" + index);
			mode = EN_ThreeD_Video_SELFADAPTIVE_DETECT.values()[cursor.getInt(index)];
		}
		cursor.close();
		return mode;
	}
	
		long ret = -1;
		try
		{
			/*ret = userSettingDB.update("tbl_ThreeDVideoMode", vals, "InputSrcType = " + inputSrcType, null);*/
			
			ret = cr.update(Uri.parse(userSettingSchema+"/threedvideomode/inputsrc/"+inputSrcType), vals, null, null);
		}
		catch(SQLException e)
		{
			Log.e("DataBaseDeskImpl", "update failed");
		}
		if(ret == -1)
		{
			return;
		}

		try
		long ret = -1;
		try
		{
			/*ret = userSettingDB.update("tbl_UserOverScanMode", vals, "InputSrcType = " + inputSrcType, null);*/
			
			ret = cr.update(Uri.parse(userSettingSchema+"/useroverscanmode/inputsrc/"+inputSrcType), vals, null, null);
		}
		catch(SQLException e)
		{
			Log.e("DataBaseDeskImpl", "update failed");
		}
		if(ret == -1)
		{
			return;
		}

		try
			
			Cursor cursor = cr.query(Uri.parse(userSettingSchema+"/usercolortemp"), null, null, null, null);
			long ret = -1;
			try
			{
				/*ret = userSettingDB.update("tbl_UserColorTemp", vals, null, null);*/
				
				ret = cr.update(Uri.parse(userSettingSchema+"/usercolortemp"), vals, null, null);
			}
			catch(SQLException e)
			{
				Log.e("DataBaseDeskImpl", "update failed");
			}
			if(ret == -1)
			{
				return;
			}

			try
		
		Cursor cursor = cr.query(Uri.parse(userSettingSchema+"/usercolortempex"), null, null, null, null);
		long ret = -1;
		try
		{
			/*ret = userSettingDB.update("tbl_UserColorTempEx", vals, "_id = " + colorTmpIdx, null);*/
			
			ret = cr.update(Uri.parse(userSettingSchema+"/usercolortempex/"+colorTmpIdx), vals, null, null);
		}
		catch(SQLException e)
		{
			Log.e("DataBaseDeskImpl", "update failed");
		}
		if(ret == -1)
		{
			return;
		}

		try
		/*if(userSettingDB == null){
			Log.e("KKJAVAAPI", "huge error,the usersettingDB is null");
		}
		if(!userSettingDB.isOpen()){
			Log.e("KKJAVAAPI", "huge error,the usersettingDB is not open");
		}*/
		/*Cursor cursor = userSettingDB.query("tbl_SystemSetting", null, null, null, null, null, null);*/
		Cursor cursor = cr.query(Uri.parse(userSettingSchema+"/systemsetting"), null, null, null, null);
			EnumLanguage eLang = EnumLanguage.values()[cursor.getInt(cursor.getColumnIndex("Language"))];
			if(eLang == EnumLanguage.E_ENGLISH)
				stUsrData.enLanguage = MEMBER_LANGUAGE.E_LANGUAGE_ENGLISH;
			else if(eLang == EnumLanguage.E_CHINESE)
			else if(eLang == EnumLanguage.E_ACHINESE)
				stUsrData.enLanguage = MEMBER_LANGUAGE.E_LANGUAGE_CHINESE_T;
			else
			{
				stUsrData.enLanguage = MEMBER_LANGUAGE.E_LANGUAGE_CHINESE_S;
				ContentValues vals = new ContentValues();
				vals.put("Language", stUsrData.enLanguage.ordinal());
				
				long ret = -1;
				try
				{
					/*ret = userSettingDB.update("tbl_SystemSetting", vals, null, null);*/
					
					ret = cr.update(Uri.parse(userSettingSchema+"/systemsetting"), vals, null, null);
				}
				catch(SQLException e)
				{
					Log.e("DataBaseDeskImpl", "update failed");
				}
			}
			stUsrData.standbyNoOperation = cursor.getInt(cursor.getColumnIndex("standbyNoOperation"));
			stUsrData.standbyNoSignal = cursor.getInt(cursor.getColumnIndex("standbyNoSignal")) == 0 ? false : true;
			stUsrData.screenSaveMode = cursor.getInt(cursor.getColumnIndex("screenSaveMode")) == 0 ? false : true;
			stUsrData.smartEnergySaving = SmartEnergySavingMode.values()[cursor.getInt(cursor.getColumnIndex("smartEnergySaving"))];
			stUsrData.colorWheelMode = ColorWheelMode.values()[cursor.getInt(cursor.getColumnIndex("colorWheelMode"))];
		}
		vals.put("standbyNoOperation", model.standbyNoOperation);
		vals.put("standbyNoSignal", model.standbyNoSignal ? 1 : 0);
		vals.put("screenSaveMode", model.screenSaveMode ? 1 : 0);
		vals.put("smartEnergySaving", model.smartEnergySaving.ordinal());
		vals.put("colorWheelMode", model.colorWheelMode.ordinal());
		
		long ret = -1;
		try
		{
			/*ret = userSettingDB.update("tbl_SystemSetting", vals, null, null);*/
			
			ret = cr.update(Uri.parse(userSettingSchema+"/systemsetting"), vals, null, null);
		}
		catch(SQLException e)
		{
			Log.e("DataBaseDeskImpl", "update failed");
		}
		if(ret == -1)
		{
			return;
		}

		try
		
		Cursor cursor = cr.query(Uri.parse(userSettingSchema+"/subtitlesetting"), null, null, null, null);
			EnumLanguage eLang1 = EnumLanguage.values()[cursor.getInt(cursor.getColumnIndex("SubtitleDefaultLanguage"))];
				long ret = -1;
				try
				{
					/*ret = userSettingDB.update("tbl_SubtitleSetting", vals, null, null);*/
					
					ret = cr.update(Uri.parse(userSettingSchema+"/subtitlesetting"), vals, null, null);
				}
				catch(SQLException e)
				{
					Log.e("DataBaseDeskImpl", "update failed");
				}
			}
				long ret = -1;
				try
				{
					/*ret = userSettingDB.update("tbl_SubtitleSetting", vals, null, null);*/
					
					ret = cr.update(Uri.parse(userSettingSchema+"/subtitlesetting"), vals, null, null);
				}
				catch(SQLException e)
				{
					Log.e("DataBaseDeskImpl", "update failed");
				}
			}
		long ret = -1;
		try
		{
			/*ret = userSettingDB.update("tbl_SubtitleSetting", vals, null, null);*/
			
			ret = cr.update(Uri.parse(userSettingSchema+"/subtitlesetting"), vals, null, null);
		}
		catch(SQLException e)
		{
			Log.e("DataBaseDeskImpl", "update failed");
		}
		if(ret == -1)
		{
			return;
		}

		try
		
		Cursor cursor = cr.query(Uri.parse(userSettingSchema+"/userlocationsetting"), null, null, null, null);
		long ret = -1;
		try
		{
			/*ret = userSettingDB.update("tbl_UserLocationSetting", vals, null, null);*/
			
			ret = cr.update(Uri.parse(userSettingSchema+"/userlocationsetting"), vals, null, null);
		}
		catch(SQLException e)
		{
			Log.e("DataBaseDeskImpl", "update failed");
		}
		if(ret == -1)
		{
			return;
		}

		try
		
		Cursor cursor = cr.query(Uri.parse(userSettingSchema+"/soundsetting"), null, null, null, null);
				long ret = -1;
				try
				{
					/*ret = userSettingDB.update("tbl_SoundSetting", vals, null, null);*/
					
					ret = cr.update(Uri.parse(userSettingSchema+"/soundsetting"), vals, null, null);
				}
				catch(SQLException e)
				{
					Log.e("DataBaseDeskImpl", "update failed");
				}
			}
				long ret = -1;
				try
				{
					/*ret = userSettingDB.update("tbl_SoundSetting", vals, null, null);*/
					
					ret = cr.update(Uri.parse(userSettingSchema+"/soundsetting"), vals, null, null);
				}
				catch(SQLException e)
				{
					Log.e("DataBaseDeskImpl", "update failed");
				}
			}
			soundpara.hdmi1AudioSource = HdmiAudioSource.values()[cursor.getInt(cursor.getColumnIndex("hdmi1AudioSource"))];
			soundpara.hdmi2AudioSource = HdmiAudioSource.values()[cursor.getInt(cursor.getColumnIndex("hdmi2AudioSource"))];
			soundpara.hdmi3AudioSource = HdmiAudioSource.values()[cursor.getInt(cursor.getColumnIndex("hdmi3AudioSource"))];
			soundpara.hdmi4AudioSource = HdmiAudioSource.values()[cursor.getInt(cursor.getColumnIndex("hdmi4AudioSource"))];
		vals.put("hdmi1AudioSource", model.hdmi1AudioSource.ordinal());
		vals.put("hdmi2AudioSource", model.hdmi2AudioSource.ordinal());
		vals.put("hdmi3AudioSource", model.hdmi3AudioSource.ordinal());
		vals.put("hdmi4AudioSource", model.hdmi4AudioSource.ordinal());
		long ret = -1;
		try
		{
			/*ret = userSettingDB.update("tbl_SoundSetting", vals, null, null);*/
			
			ret = cr.update(Uri.parse(userSettingSchema+"/soundsetting"), vals, null, null);
		}
		catch(SQLException e)
		{
			Log.e("DataBaseDeskImpl", "update failed");
		}
		if(ret == -1)
		{
			return;
		}

		try
		
		Cursor cursor = cr.query(Uri.parse(userSettingSchema+"/soundmodesetting"), null, null, null, null);
		long ret = -1;
		try
		{
			/*ret = userSettingDB.update("tbl_SoundModeSetting", vals, "_id = " + soundModeType, null);*/
			
			ret = cr.update(Uri.parse(userSettingSchema+"/soundmodesetting/"+soundModeType), vals, null, null);
		}
		catch(SQLException e)
		{
			Log.e("DataBaseDeskImpl", "update failed");
		}
		if(ret == -1)
		{
			return;
		}

		try
		
		Cursor cursor = cr.query(Uri.parse(userSettingSchema+"/userpcmodesetting/9"), null, null, null, null);
		
		Cursor cursor = cr.query(Uri.parse(userSettingSchema+"/userpcmodesetting/9"), null, null, null, null);
		
		
		Cursor cursor = cr.query(Uri.parse(userSettingSchema+"/userpcmodesetting/9"), null, null, null, null);
		
		
		Cursor cursor = cr.query(Uri.parse(userSettingSchema+"/userpcmodesetting/9"), null, null, null, null);
		
	
	@Override
	public EnumChinaDvbcRegion getDTVCity()
	{
		EnumChinaDvbcRegion value = null;
		/*Cursor cursor = userSettingDB.query("tbl_ChinaDVBCSetting", null, null,
				null, null, null, null);*/
		
		Cursor cursor = cr.query(Uri.parse(userSettingSchema+"/chinadvbcsetting"), null, null, null, null);
		if (cursor.moveToFirst()) {
			value = EnumChinaDvbcRegion.values()[cursor.getInt(cursor
					.getColumnIndex("eDVBCRegion"))];
		}
		cursor.close();
		return value;
	}
	
	@Override
	public void setDTVCity(EnumChinaDvbcRegion eType)
	{
		ContentValues v = new ContentValues();
		v.put("eDVBCRegion", eType.ordinal());
		
		long ret = -1;
		try
		{
			/*ret = userSettingDB.update("tbl_ChinaDVBCSetting", v, null, null);*/
			
			ret = cr.update(Uri.parse(userSettingSchema+"/chinadvbcsetting"), v, null, null);
		}
		catch(SQLException e)
		{
			Log.e("DataBaseDeskImpl", "update failed");
		}
		if(ret == -1)
		{
			return;
		}

		try
        {
	        TvManager.getDatabaseManager().setDatabaseDirtyByApplication(T_ChinaDVBCSetting_IDX);
        }
        catch (TvCommonException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
	}
	
	@Override
	public int getDVBCNetTableFrequency()
	{
		int value = 0;
		/*Cursor cursor = userSettingDB.query("tbl_ChinaDVBCSetting", null, null,
				null, null, null, null);*/
		
		Cursor cursor = cr.query(Uri.parse(userSettingSchema+"/chinadvbcsetting"), null, null, null, null);
		if (cursor.moveToFirst()) {
			value = cursor.getInt(cursor.getColumnIndex("u32NITFreq"));
		}
		cursor.close();
		return value;
	}
	
	@Override
	public void setDVBCNetTableFrequency(int iFre)
	{
		ContentValues v = new ContentValues();
		v.put("u32NITFreq", iFre * 1000);
		
		long ret = -1;
		try
		{
			/*ret = userSettingDB.update("tbl_ChinaDVBCSetting", v, null, null);*/
			
			ret = cr.update(Uri.parse(userSettingSchema+"/chinadvbcsetting"), v, null, null);
		}
		catch(SQLException e)
		{
			Log.e("DataBaseDeskImpl", "update failed");
		}
		if(ret == -1)
		{
			return;
		}

		try
        {
	        TvManager.getDatabaseManager().setDatabaseDirtyByApplication(T_ChinaDVBCSetting_IDX);
        }
        catch (TvCommonException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
	}
	@Override
	public void setDynamicBLMode(EN_MS_LOCALDIMMING DynamicMode) {
		// TODO Auto-generated method stub
		ContentValues v = new ContentValues();		
		v.put("enLocalDimm", DynamicMode.ordinal());		
		long ret = -1;
		try
		{
			ret = cr.update(Uri.parse(userSettingSchema+"/systemsetting"), v, null, null);
		}
		catch(SQLException e)
		{
			Log.e("DataBaseDeskImpl", "update failed");
		}
		if(ret == -1)
		{
			return;
		}
	}
	@Override
	public int getDynamicBLMode()
	{
		// TODO Auto-generated method stub
		int value = 0;
		Cursor cursor = cr.query(Uri.parse(userSettingSchema+"/systemsetting"),null, null, null, null);
		if (cursor.moveToFirst()) {
			value = cursor.getInt(cursor.getColumnIndex("enLocalDimm"));
		}
		cursor.close();
		Log.d("enLocalDimm", "value======="+value);
		return value;
	}
	
	public Cursor queryEpg(boolean bIsWithStartTime, int iStartTime)
	{
		Cursor cursor = null;
		if(bIsWithStartTime)
		{
			//TODO  need to implement in content Provider
			/*return userSettingDB.query("tbl_ScheduleInfo", null, 
					"u32StartTime = " + iStartTime, null, null, null, null);*/
			
			String selection = "u32StartTime = ? ";
			String[] selectionArgs = {String.valueOf(iStartTime)};
			cursor = cr.query(Uri.parse(userSettingSchema+"/scheduleinfo"), null, selection, selectionArgs, null);
			
		}
		else
		{
			cursor = cr.query(Uri.parse(userSettingSchema+"/scheduleinfo"), null, null, null, null);
			/*return userSettingDB.query("tbl_ScheduleInfo", null, 
					null, null, null, null, null);*/
		}
		
		return cursor;
	}
	
	public int deleteEpg(int iStartTime)
	{
		try
		{
			String where = " u32StartTime = ? ";
			String[] selectionArgs = {String.valueOf(iStartTime)};
			int ret = cr.delete(Uri.parse(userSettingSchema+"/scheduleinfo"), where, selectionArgs);
			return ret;
			/*return userSettingDB.delete("tbl_ScheduleInfo", "u32StartTime = " + iStartTime, null);*/
		}
		catch (SQLiteException e)
		{
			e.printStackTrace();
			return -1;
		}
	}
	
	public long insertEpg(ContentValues value)
	{
		long keyValLong ;
		try
		{
			/*return userSettingDB.insert("tbl_ScheduleInfo", null, value);*/
			Uri uri = cr.insert(Uri.parse(userSettingSchema+"/scheduleinfo"), value);
			String keyVal = uri.getLastPathSegment();
			
			try
			{
				keyValLong = Long.parseLong(keyVal);
			}catch(NumberFormatException ex)
			{
				Log.e(TAG, "error occurred when getting new inserted EPG ID.Here is the detail:"+ex.getMessage());
				keyValLong = -1;
			}
			
		}
		catch (SQLiteException e)
		{
			e.printStackTrace();
			keyValLong = -1;
		}
		return keyValLong;
	}
	
	T_MS_COLOR_TEMPEX_DATA colorParaEx;
	
	
		customerCfgMiscSetting = new CustomerCfgMiscSetting();
		videopara.skinTone = SkinToneMode.SKIN_TONE_OFF;
		videopara.detailEnhance = false;
		videopara.DNR = EN_MS_NR.MS_NR_AUTO;
		colorParaEx = new T_MS_COLOR_TEMPEX_DATA(0x80, 0x80, 0x80, 0x80, 0x80, 0x80);
		stUsrData.standbyNoOperation = 0;
		stUsrData.standbyNoSignal = false;
		stUsrData.screenSaveMode = false;
		stUsrData.smartEnergySaving = SmartEnergySavingMode.MODE_OFF;
		stUsrData.colorWheelMode = ColorWheelMode.MODE_OFF;
		
		
		Cursor cursor = cr.query(Uri.parse(userSettingSchema+"/systemsetting"), null, null, null, null);
		long ret = -1;
		try
		{
			/*ret = userSettingDB.update("tbl_SystemSetting", vals, null, null);*/
			
			ret = cr.update(Uri.parse(userSettingSchema+"/systemsetting"), vals, null, null);
		}
		catch(SQLException e)
		{
			Log.e("DataBaseDeskImpl", "update failed");
		}
		if(ret == -1)
		{
			return;
		}
	}

	public boolean setSoundMode(EN_SOUND_MODE eMode) {
		// TODO Auto-generated method stub
		ContentValues vals = new ContentValues();
		vals.put("SoundMode", eMode.ordinal());
		int iRet = -1;
		try
		{
			/*iRet = userSettingDB.update("tbl_SoundSetting", vals, null, null);*/
			
			iRet = cr.update(Uri.parse(userSettingSchema+"/soundsetting"), vals, null, null);
		}
		catch(SQLException e)
		{
			Log.e("DataBaseDeskImpl", "update failed");
		}
		if(iRet == -1){
			return false;
		}else{
			return true;
		}
	}

	@Override

	@Override
	public void SyncUserSettingDB() {
		// TODO Auto-generated method stub
		queryUserSysSetting();
		queryAllVideoPara(com.GetCurrentInputSource().ordinal());
	}

	@Override
	public void setSRSOnOff(EnumSwitchOnOff eOnOff) {
		// TODO Auto-generated method stub
		ContentValues values = new ContentValues();
		values.put("bSRSOn", eOnOff.ordinal());
		try {
			/*userSettingDB.update("tbl_SRSSetting", values, null, null);*/
			
			cr.update(Uri.parse(userSettingSchema+"/srssetting"), values, null, null);
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("KKJAVAAPI","update tbl_SRSSetting error");
		}
	}

	@Override
	public EnumSwitchOnOff getSRSOnOff() {
		// TODO Auto-generated method stub
		EnumSwitchOnOff value = EnumSwitchOnOff.SWITCH_OFF;
		/*Cursor cursor = userSettingDB.query("tbl_SRSSetting", null, null, null, null, null, null);*/
		
		Cursor cursor = cr.query(Uri.parse(userSettingSchema+"/srssetting"), null, null, null, null);
		if(cursor.moveToFirst()){
			value = EnumSwitchOnOff.values()[cursor.getInt(cursor.getColumnIndex("bSRSOn"))];
		}
		cursor.close();
		return value;
	}

	@Override
	public void setTruebaseOnOff(EnumSwitchOnOff eOnOff) {
		// TODO Auto-generated method stub
		ContentValues values = new ContentValues();
		values.put("bTruebassOn", eOnOff.ordinal());
		try {
			/*userSettingDB.update("tbl_SRSSetting", values, null, null);*/
			
			cr.update(Uri.parse(userSettingSchema+"/srssetting"), values, null, null);
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("KKJAVAAPI","update tbl_SRSSetting error");
		}
	}

	@Override
	public EnumSwitchOnOff getTruebaseOnOff() {
		// TODO Auto-generated method stub
		EnumSwitchOnOff value = EnumSwitchOnOff.SWITCH_OFF;
		/*Cursor cursor = userSettingDB.query("tbl_SRSSetting", null, null, null, null, null, null);*/
		
		Cursor cursor = cr.query(Uri.parse(userSettingSchema+"/srssetting"), null, null, null, null);
		if(cursor.moveToFirst()){
			value = EnumSwitchOnOff.values()[cursor.getInt(cursor.getColumnIndex("bTruebassOn"))];
		}
		cursor.close();
		return value;
	}

	@Override
	public void setDialogClarityOnOff(EnumSwitchOnOff eOnOff) {
		// TODO Auto-generated method stub
		ContentValues values = new ContentValues();
		values.put("bDialogClarityOn", eOnOff.ordinal());
		try {
			/*userSettingDB.update("tbl_SRSSetting", values, null, null);*/
			
			cr.update(Uri.parse(userSettingSchema+"/srssetting"), values, null, null);
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("KKJAVAAPI","update tbl_SRSSetting error");
		}
	}

	@Override
	public EnumSwitchOnOff getDialogClarityOnOff() {
		// TODO Auto-generated method stub
		EnumSwitchOnOff value = EnumSwitchOnOff.SWITCH_OFF;
		/*Cursor cursor = userSettingDB.query("tbl_SRSSetting", null, null, null, null, null, null);*/
		
		Cursor cursor = cr.query(Uri.parse(userSettingSchema+"/srssetting"), null, null, null, null);
		if(cursor.moveToFirst()){
			value = EnumSwitchOnOff.values()[cursor.getInt(cursor.getColumnIndex("bDialogClarityOn"))];
		}
		cursor.close();
		return value;
	}

	@Override
	public void setDefinitionOnOff(EnumSwitchOnOff eOnOff) {
		// TODO Auto-generated method stub
		ContentValues values = new ContentValues();
		values.put("bDefinitionOn", eOnOff.ordinal());
		try {
			/*userSettingDB.update("tbl_SRSSetting", values, null, null);*/
			
			cr.update(Uri.parse(userSettingSchema+"/srssetting"), values, null, null);
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("KKJAVAAPI","update tbl_SRSSetting error");
		}
	}

	@Override
	public EnumSwitchOnOff getDefinitionOnOff() {
		// TODO Auto-generated method stub
		EnumSwitchOnOff value = EnumSwitchOnOff.SWITCH_OFF;
		/*Cursor cursor = userSettingDB.query("tbl_SRSSetting", null, null, null, null, null, null);*/
		
		Cursor cursor = cr.query(Uri.parse(userSettingSchema+"/srssetting"), null, null, null, null);
		if(cursor.moveToFirst()){
			value = EnumSwitchOnOff.values()[cursor.getInt(cursor.getColumnIndex("bDefinitionOn"))];
		}
		cursor.close();
		return value;
	}

	/**
	 * for SRSAdjust
	 * 
	 * @param sourceId
	 * @return
	 */
	public KK_SRS_SET querySRSAdjust()
	{
		/*Cursor cursor = factoryCusDB.query("tbl_SRSAdjust", null, null, null, null, null, null);*/
		
		Cursor cursor = cr.query(Uri.parse(factoryCusSchema+"/srsadjust"), null, null, null, null);
		if (cursor.moveToFirst())
		{
			mSRSSet.srs_InputGain = cursor.getInt(cursor.getColumnIndex("iInputGain"));
			mSRSSet.srs_SurrLevelControl = cursor.getInt(cursor.getColumnIndex("iSurrLevelCtl"));
			mSRSSet.srs_SpeakerAudio = cursor.getInt(cursor.getColumnIndex("iSpeakerAudio"));
			mSRSSet.srs_SpeakerAnalysis = cursor.getInt(cursor.getColumnIndex("iSpeakerAnalysis"));
			mSRSSet.srs_TrubassControl = cursor.getInt(cursor.getColumnIndex("iTrubassCtl"));
			mSRSSet.srs_DCControl = cursor.getInt(cursor.getColumnIndex("iDCCtl"));
			mSRSSet.srs_DefinitionControl = cursor.getInt(cursor.getColumnIndex("iDefinitionCtl"));

		}
		cursor.close();
		return mSRSSet;
	}
	
	@Override
	public KK_SRS_SET getSRSSet() {
		// TODO Auto-generated method stub
		return mSRSSet;
	}

	@Override
	public boolean setSRSSet(KK_SRS_SET stSRS, EN_SRS_SET_TYPE eSRS) {
		// TODO Auto-generated method stub
		ContentValues values = new ContentValues();
		if(eSRS == EN_SRS_SET_TYPE.E_SRS_INPUTGAIN){
			values.put("iInputGain", stSRS.srs_InputGain);
			try {
				/*factoryCusDB.update("tbl_SRSAdjust", values, null, null);*/
				
				cr.update(Uri.parse(factoryCusSchema+"/srsadjust"), values, null, null);
			} catch (Exception e) {
				// TODO: handle exception
				Log.e("KKJAVAAPI","update tbl_SRSAdjust error");
			}
			mSRSSet.srs_InputGain = stSRS.srs_InputGain;
		}else if(eSRS == EN_SRS_SET_TYPE.E_SRS_SURRLEVEL_CONTROL){
			values.put("iSurrLevelCtl", stSRS.srs_SurrLevelControl);
			try {
				/*factoryCusDB.update("tbl_SRSAdjust", values, null, null);*/
				cr.update(Uri.parse(factoryCusSchema+"/srsadjust"), values, null, null);
			} catch (Exception e) {
				// TODO: handle exception
				Log.e("KKJAVAAPI","update tbl_SRSAdjust error");
			}
			mSRSSet.srs_SurrLevelControl = stSRS.srs_SurrLevelControl;
		}else if(eSRS == EN_SRS_SET_TYPE.E_SRS_SPEAKERAUDIO){
			values.put("iSpeakerAudio", stSRS.srs_SpeakerAudio);
			try {
				/*factoryCusDB.update("tbl_SRSAdjust", values, null, null);*/
				cr.update(Uri.parse(factoryCusSchema+"/srsadjust"), values, null, null);
			} catch (Exception e) {
				// TODO: handle exception
				Log.e("KKJAVAAPI","update tbl_SRSAdjust error");
			}
			mSRSSet.srs_SpeakerAudio = stSRS.srs_SpeakerAudio;
		}else if(eSRS == EN_SRS_SET_TYPE.E_SRS_SPEAKERANALYSIS){
			values.put("iSpeakerAnalysis", stSRS.srs_SpeakerAnalysis);
			try {
				/*factoryCusDB.update("tbl_SRSAdjust", values, null, null);*/
				cr.update(Uri.parse(factoryCusSchema+"/srsadjust"), values, null, null);
			} catch (Exception e) {
				// TODO: handle exception
				Log.e("KKJAVAAPI","update tbl_SRSAdjust error");
			}
			mSRSSet.srs_SpeakerAnalysis = stSRS.srs_SpeakerAnalysis;
		}else if(eSRS == EN_SRS_SET_TYPE.E_SRS_TRUBASS_CONTROL){
			values.put("iTrubassCtl", stSRS.srs_TrubassControl);
			try {
				/*factoryCusDB.update("tbl_SRSAdjust", values, null, null);*/
				cr.update(Uri.parse(factoryCusSchema+"/srsadjust"), values, null, null);
			} catch (Exception e) {
				// TODO: handle exception
				Log.e("KKJAVAAPI","update tbl_SRSAdjust error");
			}
			mSRSSet.srs_TrubassControl = stSRS.srs_TrubassControl;
		}else if(eSRS == EN_SRS_SET_TYPE.E_SRS_DC_CONTROL){
			values.put("iDCCtl", stSRS.srs_DCControl);
			try {
				/*factoryCusDB.update("tbl_SRSAdjust", values, null, null);*/
				cr.update(Uri.parse(factoryCusSchema+"/srsadjust"), values, null, null);
			} catch (Exception e) {
				// TODO: handle exception
				Log.e("KKJAVAAPI","update tbl_SRSAdjust error");
			}
			mSRSSet.srs_DCControl = stSRS.srs_DCControl;
		}else if(eSRS == EN_SRS_SET_TYPE.E_SRS_DEFINITION_CONTROL){
			values.put("iDefinitionCtl", stSRS.srs_DefinitionControl);
			try {
				/*factoryCusDB.update("tbl_SRSAdjust", values, null, null);*/
				cr.update(Uri.parse(factoryCusSchema+"/srsadjust"), values, null, null);
			} catch (Exception e) {
				// TODO: handle exception
				Log.e("KKJAVAAPI","update tbl_SRSAdjust error");
			}
			mSRSSet.srs_DefinitionControl = stSRS.srs_DefinitionControl;
		}
		return true;
	}
	
	public void queryCustomerCfgMiscSetting() {
		/*Cursor cursor = factoryCusDB.query("tbl_MiscSetting", null, null,
				null, null, null, null);*/
		
		Cursor cursor = cr.query(Uri.parse(factoryCusSchema+"/miscsetting"), null, null, null, null);
		if (cursor.moveToFirst()) {
			System.out.printf("getColumnIndex=%x\n", cursor.getColumnIndex("EnergyEnable"));
			System.out.printf("getInt=%x\n", cursor.getInt(cursor.getColumnIndex("EnergyEnable")));
			customerCfgMiscSetting.energyEnable = (cursor.getInt(cursor.getColumnIndex("EnergyEnable")) == 0) ? false : true;
			customerCfgMiscSetting.energyPercent = (short) cursor.getInt(cursor.getColumnIndex("EnergyPercent"));
		}
		cursor.close();
	}

	public void updateCustomerCfgMiscSetting(CustomerCfgMiscSetting model) {
		ContentValues vals = new ContentValues();
		vals.put("EnergyEnable", model.energyEnable ? 1 : 0);
		vals.put("EnergyPercent", model.energyPercent);
		
		long ret = -1;
		try
		{
			/*ret = factoryCusDB.update("tbl_MiscSetting", vals, null, null);*/
			
			ret = cr.update(Uri.parse(factoryCusSchema+"/miscsetting"), vals, null, null);
		}
		catch(SQLException e)
		{
			Log.e("DataBaseDeskImpl", "update failed");
		}
		if(ret == -1)
		{
			return;
		}
	}
	
	@Override
	public CustomerCfgMiscSetting getCustomerCfgMiscSetting()
	{
		// TODO Auto-generated method stub
		return customerCfgMiscSetting;
	}

	@Override
	public boolean setCustomerCfgMiscSetting(CustomerCfgMiscSetting miscSetting)
	{
		customerCfgMiscSetting = miscSetting;
		return true;
	}
}