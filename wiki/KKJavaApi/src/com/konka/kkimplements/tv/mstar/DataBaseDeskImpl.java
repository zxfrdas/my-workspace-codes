/**
 * MStar Software
 * Copyright (c) 2011 - 2012 MStar Semiconductor, Inc. All rights reserved.
 *
 * All software, firmware and related documentation herein (鈥淢Star Software锟� are  * intellectual property of MStar Semiconductor, Inc. (鈥淢Star锟� and protected by  * law, including, but not limited to, copyright law and international treaties. 
 * Any use, modification, reproduction, retransmission, or republication of all 
 * or part of MStar Software is expressly prohibited, unless prior written 
 * permission has been granted by MStar. 
 *
 * By accessing, browsing and/or using MStar Software, you acknowledge that you 
 * have read, understood, and agree, to be bound by below terms (鈥淭erms锟� and to  * comply with all applicable laws and regulations:
 *
 * 1. MStar shall retain any and all right, ownership and interest to MStar 
 * Software and any modification/derivatives thereof.  No right, ownership, 
 * or interest to MStar Software and any modification/derivatives thereof is 
 * transferred to you under Terms.
 *
 * 2. You understand that MStar Software might include, incorporate or be supplied 
 * together with third party鈥檚 software and the use of MStar Software may require  * additional licenses from third parties.  Therefore, you hereby agree it is your 
 * sole responsibility to separately obtain any and all third party right and 
 * license necessary for your use of such third party鈥檚 software.  *
 * 3. MStar Software and any modification/derivatives thereof shall be deemed as 
 * MStar鈥檚 confidential information and you agree to keep MStar鈥檚 confidential  * information in strictest confidence and not disclose to any third party.  
 *	
 * 4. MStar Software is provided on an 鈥淎S IS锟�basis without warranties of any kind.  * Any warranties are hereby expressly disclaimed by MStar, including without 
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
 * in conjunction with your or your customer鈥檚 product (鈥淪ervices锟�.  You understand  * and agree that, except otherwise agreed by both parties in writing, Services are 
 * provided on an 鈥淎S IS锟�basis and the warranty disclaimer set forth in Section 4  * above shall apply.  
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
/** *  * @author Shuai.Wang *  *  */public class DataBaseDeskImpl implements DataBaseDesk{
	private final String TAG = "com.konka.kkimplements.tv.mstar.DataBaseDeskImpl";	private static DataBaseDeskImpl dataBaseMgrImpl;	private CommonDesk com = null;	/*private static SQLiteDatabase factoryDB;*/
	private ContentResolver cr ;
	private Context context;
	//private static SQLiteDatabase factoryCusDB;	//private static SQLiteDatabase userSettingDB;	private String userSettingSchema = "content://mstar.tv.usersetting";
	private String factoryCusSchema = "content://konka.tv.factory";	private Boolean[] tableDirtyFlags;		public Boolean getTableDirtyFlags(int tableIdx)    {    	return tableDirtyFlags[tableIdx];    }		public void setTableDirty(int tableIdx)    {    	tableDirtyFlags[tableIdx] = true;    }	public static DataBaseDeskImpl getDataBaseMgrInstance(Context context)	{		if (dataBaseMgrImpl == null)		{
			Log.d("KKJAVAAPI", "create databasedesk now");			dataBaseMgrImpl = new DataBaseDeskImpl(context);		}
		openDB();		return dataBaseMgrImpl;	}	private DataBaseDeskImpl(Context context)	{
		this.context = context;
		cr = context.getContentResolver();		com = CommonDeskImpl.getInstance(context);		initVarPicture();		InitSettingVar();		initCECVar();		initVarSound();		initVarFactory();				//initial dirty flag		tableDirtyFlags = new Boolean[88];		for(int i = 0 ; i < 88 ; i++){			tableDirtyFlags[i] = false;		}
		openDB();
		loadEssentialDataFromDB();	}	public void syncDirtyDataOnResume(){		if(tableDirtyFlags[DataBaseDesk.T_VideoSetting_IDX] ||				tableDirtyFlags[DataBaseDesk.T_PicMode_Setting_IDX] ||				tableDirtyFlags[DataBaseDesk.T_NRMode_IDX] ||				tableDirtyFlags[DataBaseDesk.T_ThreeDVideoMode_IDX] ||				tableDirtyFlags[DataBaseDesk.T_UserOverScanMode_IDX] 				)		{			DataBaseDeskImpl.getDataBaseMgrInstance(context).queryAllVideoPara(CommonDeskImpl.getInstance(context).GetCurrentInputSource().ordinal());			tableDirtyFlags[DataBaseDesk.T_VideoSetting_IDX] = false;			tableDirtyFlags[DataBaseDesk.T_PicMode_Setting_IDX] = false;			tableDirtyFlags[DataBaseDesk.T_NRMode_IDX] = false;			tableDirtyFlags[DataBaseDesk.T_ThreeDVideoMode_IDX] = false;			tableDirtyFlags[DataBaseDesk.T_UserOverScanMode_IDX] = false;		}		if(tableDirtyFlags[DataBaseDesk.T_FacrotyColorTemp_IDX]){			tableDirtyFlags[DataBaseDesk.T_FacrotyColorTemp_IDX] = false;			DataBaseDeskImpl.getDataBaseMgrInstance(context).queryFactoryColorTempData();		}		if(tableDirtyFlags[DataBaseDesk.T_ADCAdjust_IDX]){			tableDirtyFlags[DataBaseDesk.T_ADCAdjust_IDX] = false;			DataBaseDeskImpl.getDataBaseMgrInstance(context).queryADCAdjusts();		}		if(tableDirtyFlags[DataBaseDesk.T_NonLinearAdjust_IDX]){			tableDirtyFlags[DataBaseDesk.T_NonLinearAdjust_IDX] = false;			DataBaseDeskImpl.getDataBaseMgrInstance(context).queryNonLinearAdjusts();		}		if(tableDirtyFlags[DataBaseDesk.T_FactoryExtern_IDX]){			tableDirtyFlags[DataBaseDesk.T_FactoryExtern_IDX] = false;			DataBaseDeskImpl.getDataBaseMgrInstance(context).queryFactoryExtern();		}		if(tableDirtyFlags[DataBaseDesk.T_NonStarndardAdjust_IDX]){			tableDirtyFlags[DataBaseDesk.T_NonStarndardAdjust_IDX] = false;			DataBaseDeskImpl.getDataBaseMgrInstance(context).queryNoStandSet();				DataBaseDeskImpl.getDataBaseMgrInstance(context).queryNoStandVifSet();		}		if(tableDirtyFlags[DataBaseDesk.T_SSCAdjust_IDX]){			tableDirtyFlags[DataBaseDesk.T_SSCAdjust_IDX] = false;			DataBaseDeskImpl.getDataBaseMgrInstance(context).querySSCAdjust();		}		if(tableDirtyFlags[DataBaseDesk.T_OverscanAdjust_IDX]){			tableDirtyFlags[DataBaseDesk.T_OverscanAdjust_IDX] = false;			DataBaseDeskImpl.getDataBaseMgrInstance(context).queryOverscanAdjusts(0);			DataBaseDeskImpl.getDataBaseMgrInstance(context).queryOverscanAdjusts(1);			DataBaseDeskImpl.getDataBaseMgrInstance(context).queryOverscanAdjusts(2);			DataBaseDeskImpl.getDataBaseMgrInstance(context).queryOverscanAdjusts(3);		}		if(tableDirtyFlags[DataBaseDesk.T_PEQAdjust_IDX]){			tableDirtyFlags[DataBaseDesk.T_PEQAdjust_IDX] = false;			DataBaseDeskImpl.getDataBaseMgrInstance(context).queryPEQAdjusts();		}		if(tableDirtyFlags[DataBaseDesk.T_UserLocationSetting_IDX]){			tableDirtyFlags[DataBaseDesk.T_UserLocationSetting_IDX] = false;			DataBaseDeskImpl.getDataBaseMgrInstance(context).queryUserLocSetting();		}		if(tableDirtyFlags[DataBaseDesk.T_USER_COLORTEMP_IDX]){			tableDirtyFlags[DataBaseDesk.T_USER_COLORTEMP_IDX] = false;			DataBaseDeskImpl.getDataBaseMgrInstance(context).queryUsrColorTmpData();		}		if(tableDirtyFlags[DataBaseDesk.T_SystemSetting_IDX]){			tableDirtyFlags[DataBaseDesk.T_SystemSetting_IDX] = false;			DataBaseDeskImpl.getDataBaseMgrInstance(context).queryUserSysSetting();		}		if(tableDirtyFlags[DataBaseDesk.T_SubtitleSetting_IDX]){			tableDirtyFlags[DataBaseDesk.T_SubtitleSetting_IDX] = false;			DataBaseDeskImpl.getDataBaseMgrInstance(context).queryUserSubtitleSetting();		}		if(tableDirtyFlags[DataBaseDesk.T_USER_COLORTEMP_EX_IDX]){			tableDirtyFlags[DataBaseDesk.T_USER_COLORTEMP_EX_IDX] = false;			DataBaseDeskImpl.getDataBaseMgrInstance(context).queryUsrColorTmpExData();		}		if(tableDirtyFlags[DataBaseDesk.T_SoundMode_Setting_IDX]){			tableDirtyFlags[DataBaseDesk.T_SoundMode_Setting_IDX] = false;			DataBaseDeskImpl.getDataBaseMgrInstance(context).querySoundModeSettings();		}		if(tableDirtyFlags[DataBaseDesk.T_SoundSetting_IDX]){			tableDirtyFlags[DataBaseDesk.T_SoundSetting_IDX] = false;			DataBaseDeskImpl.getDataBaseMgrInstance(context).querySoundSetting();			DataBaseDeskImpl.getDataBaseMgrInstance(context).querySoundModeSettings();		}		if(tableDirtyFlags[DataBaseDesk.T_FacrotyColorTempEx_IDX]){			tableDirtyFlags[DataBaseDesk.T_FacrotyColorTempEx_IDX] = false;			DataBaseDeskImpl.getDataBaseMgrInstance(context).queryFactoryColorTempExData();		}		if(tableDirtyFlags[DataBaseDesk.T_SRSAdjust_IDX]){			tableDirtyFlags[DataBaseDesk.T_SRSAdjust_IDX] = false;			DataBaseDeskImpl.getDataBaseMgrInstance(context).querySRSAdjust();		}	}
	
	@Override	public void loadEssentialDataFromDB(){		//the data will be load from DB to main memory when call query function		int CurrentDBInputSourceIdex = 34;  //default storage		/*Cursor cursor = userSettingDB.query("tbl_SystemSetting", null, null, null, null, null, null);*/
		
		Cursor cursor = cr.query(Uri.parse(userSettingSchema+"/systemsetting"), null, null, null, null);		if (cursor.moveToFirst())			CurrentDBInputSourceIdex = cursor.getInt(cursor.getColumnIndex("enInputSourceType"));		cursor.close();		queryAllVideoPara(CurrentDBInputSourceIdex);		queryFactoryColorTempData();		queryADCAdjusts();		queryNonLinearAdjusts();		queryFactoryExtern();		queryNoStandSet();		queryNoStandVifSet();		querySSCAdjust();		queryOverscanAdjusts(0);		queryOverscanAdjusts(1);		queryOverscanAdjusts(2);		queryOverscanAdjusts(3);		queryPEQAdjusts();		queryUserLocSetting();		queryUsrColorTmpData();		queryUserSysSetting();		queryUserSubtitleSetting();		queryUsrColorTmpExData();		querySoundModeSettings();		querySoundSetting();		queryFactoryColorTempExData();		querySRSAdjust();
		queryCustomerCfgMiscSetting();	}		/*private static void openDB()	{		if((factoryDB == null) || (!factoryDB.isOpen())){		factoryDB = SQLiteDatabase.openDatabase("/tvdatabase/Database/factory.db", null, SQLiteDatabase.OPEN_READWRITE|SQLiteDatabase.NO_LOCALIZED_COLLATORS);		}
		if((factoryCusDB == null) || (!factoryCusDB.isOpen())){
			factoryCusDB = SQLiteDatabase.openDatabase("/customercfg/panel/factory.db", null, SQLiteDatabase.OPEN_READWRITE|SQLiteDatabase.NO_LOCALIZED_COLLATORS);
		}		if((userSettingDB == null)||(!userSettingDB.isOpen())){			Log.d("KKJAVAAPI", "open usersettingDB now");
			userSettingDB = SQLiteDatabase.openDatabase("/tvdatabase/Database/user_setting.db", null, SQLiteDatabase.OPEN_READWRITE|SQLiteDatabase.NO_LOCALIZED_COLLATORS);
		}
	}*/	private static void openDB(){}
		/*public void closeDB()	{		factoryDB.close();
		factoryCusDB.close();		userSettingDB.close();	}*/
	public void closeDB(){};
		/**	 * for ADCAdjust	 * 	 * @param sourceId	 * @return	 */	public MS_ADC_SETTING queryADCAdjusts()	{		/*Cursor cursor = factoryDB.query("tbl_ADCAdjust", null, null, null, null, null, null);*/
		Cursor cursor = cr.query(Uri.parse("content://mstar.tv.factory/adcadjust"), null, null, null, null);		int i = 0;		int length = m_stFactoryAdc.stAdcGainOffsetSetting.length;		while (cursor.moveToNext())		{			if (i > length - 1)			{				break;			}			m_stFactoryAdc.stAdcGainOffsetSetting[i].redgain = cursor.getInt(cursor.getColumnIndex("u16RedGain"));			m_stFactoryAdc.stAdcGainOffsetSetting[i].greengain = cursor.getInt(cursor.getColumnIndex("u16GreenGain"));			m_stFactoryAdc.stAdcGainOffsetSetting[i].bluegain = cursor.getInt(cursor.getColumnIndex("u16BlueGain"));			m_stFactoryAdc.stAdcGainOffsetSetting[i].redoffset = cursor.getInt(cursor.getColumnIndex("u16RedOffset"));			m_stFactoryAdc.stAdcGainOffsetSetting[i].greenoffset = cursor.getInt(cursor			        .getColumnIndex("u16GreenOffset"));			m_stFactoryAdc.stAdcGainOffsetSetting[i].blueoffset = cursor.getInt(cursor.getColumnIndex("u16BlueOffset"));			i++;		}		cursor.close();		return m_stFactoryAdc;	}	public void updateADCAdjust(T_MS_CALIBRATION_DATA model, int sourceId)	{		ContentValues vals = new ContentValues();		vals.put("u16RedGain", model.redgain);		vals.put("u16GreenGain", model.greengain);		vals.put("u16BlueGain", model.bluegain);		vals.put("u16RedOffset", model.redoffset);		vals.put("u16GreenOffset", model.greenoffset);		vals.put("u16BlueOffset", model.blueoffset);		
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

		try        {	        TvManager.getDatabaseManager().setDatabaseDirtyByApplication(T_ADCAdjust_IDX);        }        catch (TvCommonException e)        {	        // TODO Auto-generated catch block	        e.printStackTrace();        }	}	/**	 * for NonLinearAdjust	 * 	 * @param sourceId	 * @return	 */	// public Object queryNonLinearAdjust(int curveTypeIndex){	// Cursor cursor = db.query("NonLinearAdjust", null, "CurveTypeIndex = " +	// curveTypeIndex, null, null, null, null);	// if (cursor.moveToFirst()) {	// Object other = new Object();	// value = cursor.getInt(cursor.getColumnIndex("u8OSD_V0"));	// value = cursor.getInt(cursor.getColumnIndex("u8OSD_V25"));	// value = cursor.getInt(cursor.getColumnIndex("u8OSD_V50"));	// value = cursor.getInt(cursor.getColumnIndex("u8OSD_V75"));	// value = cursor.getInt(cursor.getColumnIndex("u8OSD_V100"));	// }	// cursor.close();	// return null;	// }	public MS_NLA_SETTING queryNonLinearAdjusts()	{		/*Cursor cursor = factoryCusDB.query("tbl_NonLinearAdjust", null,		        "InputSrcType = " + com.GetCurrentInputSource().ordinal(), null, null, null, "CurveTypeIndex");
		*/
		String selection = " InputSrcType = ? ";
		String[] selectionArgs = {String.valueOf(com.GetCurrentInputSource().ordinal())};
		Cursor cursor = cr.query(Uri.parse(factoryCusSchema+"/nonlinearadjust"), null, selection, selectionArgs, "CurveTypeIndex");		int i = 0;		int length = m_pastNLASet.stNLASetting.length;		while (cursor.moveToNext())		{			if (i > length - 1)			{				break;			}			m_pastNLASet.stNLASetting[i].u8OSD_V0 = (short) cursor.getInt(cursor.getColumnIndex("u8OSD_V0"));			m_pastNLASet.stNLASetting[i].u8OSD_V25 = (short) cursor.getInt(cursor.getColumnIndex("u8OSD_V25"));			m_pastNLASet.stNLASetting[i].u8OSD_V50 = (short) cursor.getInt(cursor.getColumnIndex("u8OSD_V50"));			m_pastNLASet.stNLASetting[i].u8OSD_V75 = (short) cursor.getInt(cursor.getColumnIndex("u8OSD_V75"));			m_pastNLASet.stNLASetting[i].u8OSD_V100 = (short) cursor.getInt(cursor.getColumnIndex("u8OSD_V100"));			i++;		}		cursor.close();		return m_pastNLASet;	}	public void updateNonLinearAdjust(MS_NLA_POINT dataModel, int curveTypeIndex)	{		ContentValues vals = new ContentValues();		vals.put("u8OSD_V0", dataModel.u8OSD_V0);		vals.put("u8OSD_V25", dataModel.u8OSD_V25);		vals.put("u8OSD_V50", dataModel.u8OSD_V50);		vals.put("u8OSD_V75", dataModel.u8OSD_V75);		vals.put("u8OSD_V100", dataModel.u8OSD_V100);		
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
		
		try        {	        TvManager.getDatabaseManager().setDatabaseDirtyByApplication(T_NonLinearAdjust_IDX);        }        catch (TvCommonException e)        {	        // TODO Auto-generated catch block	        e.printStackTrace();        }	}	/**	 * for NonStandardAdjust	 * 	 * @param sourceId	 * @return	 */	public MS_Factory_NS_VD_SET queryNoStandSet()	{		/*Cursor cursor = factoryDB.query("tbl_NonStandardAdjust", null, null, null, null, null, null);*/
		Cursor cursor = cr.query(Uri.parse("content://mstar.tv.factory/nonstandardadjust"), null, null, null, null);		if (cursor.moveToFirst())		{			mNoStandSet.u8AFEC_D4 = (short) cursor.getInt(cursor.getColumnIndex("u8AFEC_D4"));			mNoStandSet.u8AFEC_D5_Bit2 = (short) cursor.getInt(cursor.getColumnIndex("u8AFEC_D5_Bit2"));			mNoStandSet.u8AFEC_D8_Bit3210 = (short) cursor.getInt(cursor.getColumnIndex("u8AFEC_D8_Bit3210"));			mNoStandSet.u8AFEC_D9_Bit0 = (short) cursor.getInt(cursor.getColumnIndex("u8AFEC_D9_Bit0"));			mNoStandSet.u8AFEC_D7_LOW_BOUND = (short) cursor.getInt(cursor.getColumnIndex("u8AFEC_D7_LOW_BOUND"));			mNoStandSet.u8AFEC_D7_HIGH_BOUND = (short) cursor.getInt(cursor.getColumnIndex("u8AFEC_D7_HIGH_BOUND"));			mNoStandSet.u8AFEC_A0 = (short) cursor.getInt(cursor.getColumnIndex("u8AFEC_A0"));			mNoStandSet.u8AFEC_A1 = (short) cursor.getInt(cursor.getColumnIndex("u8AFEC_A1"));			mNoStandSet.u8AFEC_66_Bit76 = (short) cursor.getInt(cursor.getColumnIndex("u8AFEC_66_Bit76"));			mNoStandSet.u8AFEC_6E_Bit7654 = (short) cursor.getInt(cursor.getColumnIndex("u8AFEC_6E_Bit7654"));			mNoStandSet.u8AFEC_6E_Bit3210 = (short) cursor.getInt(cursor.getColumnIndex("u8AFEC_6E_Bit3210"));			mNoStandSet.u8AFEC_44 = (short) cursor.getInt(cursor.getColumnIndex("u8AFEC_44"));			mNoStandSet.u8AFEC_CB = (short) cursor.getInt(cursor.getColumnIndex("u8AFEC_CB"));		}		cursor.close();		return mNoStandSet;	}	public MS_Factory_NS_VIF_SET queryNoStandVifSet()	{ 		/*Cursor cursor = factoryDB.query("tbl_NonStandardAdjust", null, null, null, null, null, null);*/
		
		Cursor cursor = cr.query(Uri.parse("content://mstar.tv.factory/nonstandardadjust"), null, null, null, null);		if (cursor.moveToFirst())		{			mVifSet.VifTop = (short) cursor.getInt(cursor.getColumnIndex("VifTop"));			mVifSet.VifVgaMaximum = cursor.getInt(cursor.getColumnIndex("VifVgaMaximum"));			mVifSet.VifCrKp = (short) cursor.getInt(cursor.getColumnIndex("VifCrKp"));			mVifSet.VifCrKi = (short) cursor.getInt(cursor.getColumnIndex("VifCrKi"));			mVifSet.VifCrKp1 = (short) cursor.getInt(cursor.getColumnIndex("VifCrKp1"));			mVifSet.VifCrKi1 = (short) cursor.getInt(cursor.getColumnIndex("VifCrKi1"));			mVifSet.VifCrKp2 = (short) cursor.getInt(cursor.getColumnIndex("VifCrKp2"));			mVifSet.VifCrKi2 = (short) cursor.getInt(cursor.getColumnIndex("VifCrKi2"));			mVifSet.VifAsiaSignalOption = cursor.getInt(cursor.getColumnIndex("VifAsiaSignalOption")) == 0 ? false			        : true;			mVifSet.VifCrKpKiAdjust = (short) cursor.getInt(cursor.getColumnIndex("VifCrKpKiAdjust")) == 0 ? false			        : true;			mVifSet.VifOverModulation = cursor.getInt(cursor.getColumnIndex("VifOverModulation")) == 0 ? false : true;			mVifSet.VifClampgainGainOvNegative = cursor.getInt(cursor.getColumnIndex("VifClampgainGainOvNegative"));			mVifSet.ChinaDescramblerBox = (short) cursor.getInt(cursor.getColumnIndex("ChinaDescramblerBox"));			mVifSet.VifDelayReduce = (short) cursor.getInt(cursor.getColumnIndex("VifDelayReduce"));			mVifSet.VifCrThr = (short) cursor.getInt(cursor.getColumnIndex("VifCrThr"));			mVifSet.VifVersion = (short) cursor.getInt(cursor.getColumnIndex("VifVersion"));			mVifSet.VifACIAGCREF = (short) cursor.getInt(cursor.getColumnIndex("VifACIAGCREF"));			mVifSet.GainDistributionThr = cursor.getInt(cursor.getColumnIndex("GainDistributionThr"));		}		cursor.close();		return mVifSet;	}	public void updateNonStandardAdjust(MS_Factory_NS_VD_SET nonStandSet)	{		ContentValues vals = new ContentValues();		vals.put("u8AFEC_D4", nonStandSet.u8AFEC_D4);		vals.put("u8AFEC_D5_Bit2", nonStandSet.u8AFEC_D5_Bit2);		vals.put("u8AFEC_D8_Bit3210", nonStandSet.u8AFEC_D8_Bit3210);		vals.put("u8AFEC_D9_Bit0", nonStandSet.u8AFEC_D9_Bit0);		vals.put("u8AFEC_D7_LOW_BOUND", nonStandSet.u8AFEC_D7_LOW_BOUND);		vals.put("u8AFEC_D7_HIGH_BOUND", nonStandSet.u8AFEC_D7_HIGH_BOUND);		vals.put("u8AFEC_A0", nonStandSet.u8AFEC_A0);		vals.put("u8AFEC_A1", nonStandSet.u8AFEC_A1);		vals.put("u8AFEC_66_Bit76", nonStandSet.u8AFEC_66_Bit76);		vals.put("u8AFEC_6E_Bit7654", nonStandSet.u8AFEC_6E_Bit7654);		vals.put("u8AFEC_6E_Bit3210", nonStandSet.u8AFEC_6E_Bit3210);		vals.put("u8AFEC_43", nonStandSet.u8AFEC_43);		vals.put("u8AFEC_44", nonStandSet.u8AFEC_44);		vals.put("u8AFEC_CB", nonStandSet.u8AFEC_CB);		
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
		
		try        {	        TvManager.getDatabaseManager().setDatabaseDirtyByApplication(T_NonStarndardAdjust_IDX);        }        catch (TvCommonException e)        {	        // TODO Auto-generated catch block	        e.printStackTrace();        }	}	public void updateNonStandardAdjust(MS_Factory_NS_VIF_SET vifSet)	{		ContentValues vals = new ContentValues();		vals.put("VifTop", vifSet.VifTop);		vals.put("VifVgaMaximum", vifSet.VifVgaMaximum);		vals.put("VifCrKp", vifSet.VifCrKp);		vals.put("VifCrKi", vifSet.VifCrKi);		vals.put("VifCrKp1", vifSet.VifCrKp1);		vals.put("VifCrKi1", vifSet.VifCrKi1);		vals.put("VifCrKp2", vifSet.VifCrKp2);		vals.put("VifCrKi2", vifSet.VifCrKi2);		vals.put("VifAsiaSignalOption", vifSet.VifAsiaSignalOption ? 1 : 0);		vals.put("VifCrKpKiAdjust", vifSet.VifCrKpKiAdjust ? 1 : 0);		vals.put("VifOverModulation", vifSet.VifOverModulation ? 1 : 0);		vals.put("VifClampgainGainOvNegative", vifSet.VifClampgainGainOvNegative);		vals.put("ChinaDescramblerBox", vifSet.ChinaDescramblerBox);		vals.put("VifDelayReduce", vifSet.VifDelayReduce);		vals.put("VifCrThr", vifSet.VifVersion);		vals.put("VifVersion", vifSet.VifVersion);		vals.put("VifACIAGCREF", vifSet.VifACIAGCREF);		vals.put("GainDistributionThr", vifSet.GainDistributionThr);		
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
		
		try        {	        TvManager.getDatabaseManager().setDatabaseDirtyByApplication(T_NonStarndardAdjust_IDX);        }        catch (TvCommonException e)        {	        // TODO Auto-generated catch block	        e.printStackTrace();        }	}	/**	 * for factory extern	 * 	 * @param sourceId	 * @return	 */	public MS_FACTORY_EXTERN_SETTING queryFactoryExtern()	{		/*Cursor cursor = factoryDB.query("tbl_FactoryExtern", null, null, null, null, null, null);*/
		Cursor cursor = cr.query(Uri.parse("content://mstar.tv.factory/factoryextern"), null, null, null, null);		if (cursor.moveToFirst())		{			m_stFactoryExt.softVersion = cursor.getString(cursor.getColumnIndex("SoftWareVersion"));			m_stFactoryExt.boardType = cursor.getString(cursor.getColumnIndex("BoardType"));			m_stFactoryExt.panelType = cursor.getString(cursor.getColumnIndex("PanelType"));			m_stFactoryExt.dayAndTime = cursor.getString(cursor.getColumnIndex("CompileTime"));			m_stFactoryExt.testPatternMode = cursor.getInt(cursor.getColumnIndex("TestPatternMode"));			m_stFactoryExt.stPowerMode = cursor.getInt(cursor.getColumnIndex("stPowerMode"));			m_stFactoryExt.dtvAvAbnormalDelay = cursor.getInt(cursor.getColumnIndex("DtvAvAbnormalDelay")) == 0 ? false			        : true;			m_stFactoryExt.factoryPreset = cursor.getInt(cursor.getColumnIndex("FactoryPreSetFeature"));			m_stFactoryExt.panelSwingVal = (short) cursor.getInt(cursor.getColumnIndex("PanelSwing"));			m_stFactoryExt.audioPreScale = (short) cursor.getInt(cursor.getColumnIndex("AudioPrescale"));//			m_stFactoryExt.st3DSelfAdaptiveLevel = (short) cursor.getInt(cursor.getColumnIndex("ThreeDSelfAdaptieveLevel"));
			m_stFactoryExt.vdDspVersion = (short) cursor.getInt(cursor.getColumnIndex("vdDspVersion"));			m_stFactoryExt.eHidevMode = (short) cursor.getInt(cursor.getColumnIndex("eHidevMode"));			m_stFactoryExt.audioNrThr = (short) cursor.getInt(cursor.getColumnIndex("audioNrThr"));			m_stFactoryExt.audioSifThreshold = (short) cursor.getInt(cursor.getColumnIndex("audioSifThreshold"));			m_stFactoryExt.audioDspVersion = (short) cursor.getInt(cursor.getColumnIndex("audioDspVersion"));
			m_stFactoryExt.bBurnIn = cursor.getInt(cursor.getColumnIndex("bBurnIn")) == 0 ? false : true;		}		cursor.close();		return m_stFactoryExt;	}	public void updateFactoryExtern(MS_FACTORY_EXTERN_SETTING model)	{		ContentValues vals = new ContentValues();		vals.put("SoftWareVersion", model.softVersion);		vals.put("BoardType", model.boardType);		vals.put("PanelType", model.panelType);		vals.put("CompileTime", model.dayAndTime);		vals.put("TestPatternMode", model.testPatternMode);		vals.put("stPowerMode", model.stPowerMode);		vals.put("DtvAvAbnormalDelay", model.dtvAvAbnormalDelay ? 1 : 0);		vals.put("FactoryPreSetFeature", model.factoryPreset);		vals.put("PanelSwing", model.panelSwingVal);		vals.put("AudioPrescale", model.audioPreScale);//		vals.put("ThreeDSelfAdaptieveLevel", model.st3DSelfAdaptiveLevel);
		vals.put("vdDspVersion", model.vdDspVersion);		vals.put("eHidevMode", model.eHidevMode);		vals.put("audioNrThr", model.audioNrThr);		vals.put("audioSifThreshold", model.audioSifThreshold);		vals.put("audioDspVersion", model.audioDspVersion);
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
		
		try        {	        TvManager.getDatabaseManager().setDatabaseDirtyByApplication(T_FactoryExtern_IDX);        }        catch (TvCommonException e)        {	        // TODO Auto-generated catch block	        e.printStackTrace();        }	}	// for OverscanAdjust	public ST_MAPI_VIDEO_WINDOW_INFO[][] queryOverscanAdjusts(int FactoryOverScanType)	{		switch (FactoryOverScanType)		{			// ST_MAPI_VIDEO_WINDOW_INFO m_DTVOverscanSet;//DTV's primary key is 0 in DB			case 0:
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
			}								return m_DTVOverscanSet;			// ST_MAPI_VIDEO_WINDOW_INFO m_HDMIOverscanSet;//HDMI's primary key is 1 			case 1:
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
			}								return m_HDMIOverscanSet;			// ST_MAPI_VIDEO_WINDOW_INFO m_YPbPrOverscanSet;//YPbPr's primary key is 2			case 2:
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
			}								return m_YPbPrOverscanSet;			// ST_MAPI_VIDEO_WINDOW_INFO m_VDOverscanSet;//VD's primary key is 3 in DB			case 3:
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
			}								return m_VDOverscanSet;			default:				return null;		}	}	public void updateOverscanAdjust(int FactoryOverScanType, ST_MAPI_VIDEO_WINDOW_INFO[][] model)	{		int max1 = 0;		switch (FactoryOverScanType)		{			case 0:				max1 = MAX_DTV_Resolution_Info.E_DTV_MAX.ordinal();				break;			case 1:				max1 = MAX_HDMI_Resolution_Info.E_HDMI_MAX.ordinal();				break;			case 2:				max1 = MAX_YPbPr_Resolution_Info.E_YPbPr_MAX.ordinal();				break;			case 3:				max1 = EN_VD_SIGNALTYPE.SIG_NUMS.ordinal();				break;		}		int max2 = MAPI_VIDEO_ARC_Type.E_AR_MAX.ordinal();		int _id = 0;		for (int i = 0; i < max1; i++)		{			for (int j = 0; j < max2; j++)			{				ContentValues vals = new ContentValues();				vals.put("u16H_CapStart", model[i][j].u16H_CapStart);				vals.put("u16V_CapStart", model[i][j].u16V_CapStart);				vals.put("u8HCrop_Left", model[i][j].u8HCrop_Left);				vals.put("u8HCrop_Right", model[i][j].u8HCrop_Right);				vals.put("u8VCrop_Up", model[i][j].u8VCrop_Up);				vals.put("u8VCrop_Down", model[i][j].u8VCrop_Down);				_id = i * max2 + j;				
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
			}		}		try        {	        TvManager.getDatabaseManager().setDatabaseDirtyByApplication(T_OverscanAdjust_IDX);        }        catch (TvCommonException e)        {	        // TODO Auto-generated catch block	        e.printStackTrace();        }	}	/**	 * for PEQAdjust	 * 	 * @param sourceId	 * @return	 */	public AUDIO_PEQ_PARAM queryPEQAdjust(int index)	{		AUDIO_PEQ_PARAM model = new AUDIO_PEQ_PARAM();		/*Cursor cursor = factoryDB.query("tbl_PEQAdjust", null, "_id = " + index, null, null, null, null);*/
		Uri uri = Uri.parse("content://mstar.tv.factory/peqadjust/"+index);
		Cursor cursor = cr.query(uri, null, null, null, null);		if (cursor.moveToFirst())		{			model.Band = cursor.getInt(cursor.getColumnIndex("Band"));			model.Gain = cursor.getInt(cursor.getColumnIndex("Gain"));			model.Foh = cursor.getInt(cursor.getColumnIndex("Foh"));			model.Fol = cursor.getInt(cursor.getColumnIndex("Fol"));			model.QValue = cursor.getInt(cursor.getColumnIndex("QValue"));		}		cursor.close();		return model;	}	public ST_FACTORY_PEQ_SETTING queryPEQAdjusts()	{		/*Cursor cursor = factoryDB.query("tbl_PEQAdjust", null, null, null, null, null, null);*/
		
		Cursor cursor = cr.query(Uri.parse("content://mstar.tv.factory/peqadjust"), null, null, null, null);		int i = 0;		int length = m_stPEQSet.stPEQParam.length;		while (cursor.moveToNext())		{			if (i > length - 1)			{				break;			}			m_stPEQSet.stPEQParam[i].Band = cursor.getInt(cursor.getColumnIndex("Band"));			m_stPEQSet.stPEQParam[i].Gain = cursor.getInt(cursor.getColumnIndex("Gain"));			m_stPEQSet.stPEQParam[i].Foh = cursor.getInt(cursor.getColumnIndex("Foh"));			m_stPEQSet.stPEQParam[i].Fol = cursor.getInt(cursor.getColumnIndex("Fol"));			m_stPEQSet.stPEQParam[i].QValue = cursor.getInt(cursor.getColumnIndex("QValue"));			i++;		}		cursor.close();		return m_stPEQSet;	}	public void updatePEQAdjust(AUDIO_PEQ_PARAM model, int index)	{		ContentValues vals = new ContentValues();		vals.put("Band", model.Band);		vals.put("Gain", model.Gain);		vals.put("Foh", model.Foh);		vals.put("Fol", model.Fol);		vals.put("QValue", model.QValue);		
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

		try        {	        TvManager.getDatabaseManager().setDatabaseDirtyByApplication(T_PEQAdjust_IDX);        }        catch (TvCommonException e)        {	        // TODO Auto-generated catch block	        e.printStackTrace();        }	}	/**	 * for SSCAdjust	 * 	 * @param sourceId	 * @return	 */	public MS_FACTORY_SSC_SET querySSCAdjust()	{		/*Cursor cursor = factoryDB.query("tbl_SSCAdjust", null, null, null, null, null, null);*/
		
		Cursor cursor = cr.query(Uri.parse("content://mstar.tv.factory/sscadjust"), null, null, null, null);		if (cursor.moveToFirst())		{			mSscSet.Miu_SscEnable = cursor.getInt(cursor.getColumnIndex("Miu_SscEnable")) == 0 ? false : true;			mSscSet.Lvds_SscEnable = cursor.getInt(cursor.getColumnIndex("Lvds_SscEnable")) == 0 ? false : true;			mSscSet.Lvds_SscSpan = cursor.getInt(cursor.getColumnIndex("Lvds_SscSpan"));			mSscSet.Lvds_SscStep = cursor.getInt(cursor.getColumnIndex("Lvds_SscStep"));			mSscSet.Miu0_SscSpan = cursor.getInt(cursor.getColumnIndex("Miu_SscSpan"));			mSscSet.Miu0_SscStep = cursor.getInt(cursor.getColumnIndex("Miu_SscStep"));			mSscSet.Miu1_SscSpan = cursor.getInt(cursor.getColumnIndex("Miu1_SscSpan"));			mSscSet.Miu1_SscStep = cursor.getInt(cursor.getColumnIndex("Miu1_SscStep"));			mSscSet.Miu2_SscSpan = cursor.getInt(cursor.getColumnIndex("Miu2_SscSpan"));			mSscSet.Miu2_SscStep = cursor.getInt(cursor.getColumnIndex("Miu2_SscStep"));		}		cursor.close();		return mSscSet;	}	public void updateSSCAdjust(MS_FACTORY_SSC_SET model)	{		ContentValues vals = new ContentValues();		vals.put("Miu_SscEnable", model.Miu_SscEnable ? 1 : 0);		vals.put("Lvds_SscEnable", model.Lvds_SscEnable ? 1 : 0);		vals.put("Lvds_SscSpan", model.Lvds_SscSpan);		vals.put("Lvds_SscStep", model.Lvds_SscStep);		vals.put("Miu_SscSpan", model.Miu0_SscSpan);		vals.put("Miu_SscStep", model.Miu0_SscStep);		vals.put("Miu1_SscSpan", model.Miu1_SscSpan);		vals.put("Miu1_SscStep", model.Miu1_SscStep);		vals.put("Miu2_SscSpan", model.Miu2_SscSpan);		vals.put("Miu2_SscStep", model.Miu2_SscStep);		
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

		try        {	        TvManager.getDatabaseManager().setDatabaseDirtyByApplication(T_SSCAdjust_IDX);        }        catch (TvCommonException e)        {	        // TODO Auto-generated catch block	        e.printStackTrace();        }	}	/**	 * for factory White balance (Color tempreture data)	 * 	 * @param sourceId	 * @return	 */	public T_MS_COLOR_TEMP queryFactoryColorTempData()	{		/*Cursor cursor = factoryDB.query("tbl_FactoryColorTemp", null, null, null, null, null, "ColorTemperatureID");*/
		Uri uri = Uri.parse("content://mstar.tv.factory/factorycolortemp");
		Cursor cursor = cr.query(uri, null, null, null, "ColorTemperatureID");		int i = 0;		int length = m_stFactoryColorTemp.astColorTemp.length;		while (cursor.moveToNext())		{			if (i > length - 1)			{				break;			}			m_stFactoryColorTemp.astColorTemp[i].redgain = (short) cursor.getInt(cursor.getColumnIndex("u8RedGain"));			m_stFactoryColorTemp.astColorTemp[i].greengain = (short) cursor			        .getInt(cursor.getColumnIndex("u8GreenGain"));			m_stFactoryColorTemp.astColorTemp[i].bluegain = (short) cursor.getInt(cursor.getColumnIndex("u8BlueGain"));			m_stFactoryColorTemp.astColorTemp[i].redoffset = (short) cursor			        .getInt(cursor.getColumnIndex("u8RedOffset"));			m_stFactoryColorTemp.astColorTemp[i].greenoffset = (short) cursor.getInt(cursor			        .getColumnIndex("u8GreenOffset"));			m_stFactoryColorTemp.astColorTemp[i].blueoffset = (short) cursor.getInt(cursor			        .getColumnIndex("u8BlueOffset"));			i++;		}		cursor.close();		return m_stFactoryColorTemp;	}	public void updateFactoryColorTempData(T_MS_COLOR_TEMP_DATA model, int colorTmpId)	{		ContentValues vals = new ContentValues();		vals.put("u8RedGain", model.redgain);		vals.put("u8GreenGain", model.greengain);		vals.put("u8BlueGain", model.bluegain);		vals.put("u8RedOffset", model.redoffset);		vals.put("u8GreenOffset", model.greenoffset);		vals.put("u8BlueOffset", model.blueoffset);

		long ret = -1;
		try
		{			/*ret = factoryDB.update("tbl_FactoryColorTemp", vals, "ColorTemperatureID = " + colorTmpId , null);*/
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
				try        {	        TvManager.getDatabaseManager().setDatabaseDirtyByApplication(T_FacrotyColorTemp_IDX);        }        catch (TvCommonException e)        {	        // TODO Auto-generated catch block	        e.printStackTrace();        }	}	/**	 * for White balance (Color tempreture extern data)	 * 	 * @param sourceId	 * @return	 */	public T_MS_COLOR_TEMPEX queryFactoryColorTempExData()	{
		String selection = "";
		String[] selectionArgs = new String[1];
				for(int sourceIdx = 0; sourceIdx < EN_MS_COLOR_TEMP_INPUT_SOURCE.E_INPUT_SOURCE_NUM.ordinal();sourceIdx++){			/*Cursor cursor = factoryCusDB.query("tbl_FactoryColorTempEx", null, "InputSourceID = " + sourceIdx, null, null, null, "ColorTemperatureID");*/
			
			selection = " InputSourceID = ? ";
			selectionArgs[0] = String.valueOf(sourceIdx);
			Cursor cursor = cr.query(Uri.parse(factoryCusSchema+"/factorycolortempex"), null, selection, selectionArgs, "ColorTemperatureID");
						for (int colorTmpIdx = 0; colorTmpIdx < EN_MS_COLOR_TEMP.MS_COLOR_TEMP_NUM.ordinal(); colorTmpIdx++)			{				if (cursor.moveToNext())				{					m_stFactoryColorTempEx.astColorTempEx[colorTmpIdx][sourceIdx].redgain =  cursor.getInt(cursor.getColumnIndex("u16RedGain"));					m_stFactoryColorTempEx.astColorTempEx[colorTmpIdx][sourceIdx].greengain =  cursor					        .getInt(cursor.getColumnIndex("u16GreenGain"));					m_stFactoryColorTempEx.astColorTempEx[colorTmpIdx][sourceIdx].bluegain = cursor.getInt(cursor.getColumnIndex("u16BlueGain"));					m_stFactoryColorTempEx.astColorTempEx[colorTmpIdx][sourceIdx].redoffset = cursor					        .getInt(cursor.getColumnIndex("u16RedOffset"));					m_stFactoryColorTempEx.astColorTempEx[colorTmpIdx][sourceIdx].greenoffset = cursor.getInt(cursor					        .getColumnIndex("u16GreenOffset"));					m_stFactoryColorTempEx.astColorTempEx[colorTmpIdx][sourceIdx].blueoffset = cursor.getInt(cursor					        .getColumnIndex("u16BlueOffset"));				}			}			cursor.close();		}		return m_stFactoryColorTempEx;	}	public void updateFactoryColorTempExData(T_MS_COLOR_TEMPEX_DATA model, int sourceId, int colorTmpId)	{		ContentValues vals = new ContentValues();		vals.put("u16RedGain", model.redgain);		vals.put("u16GreenGain", model.greengain);		vals.put("u16BlueGain", model.bluegain);		vals.put("u16RedOffset", model.redoffset);		vals.put("u16GreenOffset", model.greenoffset);		vals.put("u16BlueOffset", model.blueoffset);		
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
		
		try        {	        TvManager.getDatabaseManager().setDatabaseDirtyByApplication(T_FacrotyColorTempEx_IDX);        }        catch (TvCommonException e)        {	        // TODO Auto-generated catch block	        e.printStackTrace();        }	}		/** define video setting for */	/**	 * 	 * @param inputSrcType	 * @return	 */	public T_MS_VIDEO queryAllVideoPara(int inputSrcType)	{		// query tbl_VideoSetting for T_MS_VIDEO videopara base info and for		// T_MS_SUB_COLOR g_astSubColor of videoPara
		/*Cursor cursorVideo = userSettingDB.query("tbl_VideoSetting", null, "InputSrcType = " + inputSrcType, null, null, null,		        null);*/
		
		Cursor cursorVideo = cr.query(Uri.parse(userSettingSchema+"/videosetting/inputsrc/"+inputSrcType), null, null, null, null);		
		while (cursorVideo.moveToNext())		{			// videopara base info			videopara.ePicture = EN_MS_PICTURE.values()[cursorVideo.getInt(cursorVideo.getColumnIndex("ePicture"))];			videopara.enARCType = MAPI_VIDEO_ARC_Type.values()[cursorVideo.getInt(cursorVideo			        .getColumnIndex("enARCType"))];			videopara.fOutput_RES = EN_DISPLAY_RES_TYPE.values()[cursorVideo.getInt(cursorVideo			        .getColumnIndex("fOutput_RES"))];			videopara.tvsys = MAPI_VIDEO_OUT_VE_SYS.values()[cursorVideo.getInt(cursorVideo.getColumnIndex("tvsys"))];			videopara.LastVideoStandardMode = MAPI_AVD_VideoStandardType.values()[cursorVideo.getInt(cursorVideo			        .getColumnIndex("LastVideoStandardMode"))];			videopara.LastAudioStandardMode = AUDIOMODE_TYPE_.values()[cursorVideo.getInt(cursorVideo			        .getColumnIndex("LastAudioStandardMode"))];			videopara.eDynamic_Contrast = EN_MS_Dynamic_Contrast.values()[cursorVideo.getInt(cursorVideo			        .getColumnIndex("eDynamic_Contrast"))];			videopara.eFilm = EN_MS_FILM.values()[cursorVideo.getInt(cursorVideo.getColumnIndex("eFilm"))];			videopara.eTvFormat = EN_DISPLAY_TVFORMAT.values()[cursorVideo.getInt(cursorVideo			        .getColumnIndex("eTvFormat"))];
			videopara.skinTone = SkinToneMode.values()[cursorVideo.getInt(cursorVideo
			        .getColumnIndex("skinTone"))];
			videopara.detailEnhance = (cursorVideo.getInt(cursorVideo
			        .getColumnIndex("detailEnhance")) == 0) ? false : true;
			videopara.DNR = EN_MS_NR.values()[cursorVideo.getInt(cursorVideo
			        .getColumnIndex("DNR"))];
			// T_MS_SUB_COLOR g_astSubColor of videoPara			videopara.g_astSubColor.SubBrightness = (short) cursorVideo.getInt(cursorVideo			        .getColumnIndex("u8SubBrightness"));			videopara.g_astSubColor.SubContrast = (short) cursorVideo.getInt(cursorVideo			        .getColumnIndex("u8SubContrast"));		}		cursorVideo.close();
		// query tbl_PicMode_Setting for T_MS_PICTURE astPicture[] of videoPara		/*Cursor cursorPicMode = userSettingDB.query("tbl_PicMode_Setting", null, "InputSrcType = " + 0, null, null,		        null, "PictureModeType");*/
		
		String selection = " InputSrcType = ? ";
		String[] selectionArgs = {"0"};
		Cursor cursorPicMode = cr.query(Uri.parse(userSettingSchema+"/picmode_setting"), null, selection, selectionArgs, "PictureModeType");		int picModeIdx = 0;		int length = videopara.astPicture.length;		while (cursorPicMode.moveToNext())		{			if (picModeIdx > length - 1)			{				break;			}
			videopara.astPicture[picModeIdx].backlight = (short) cursorPicMode.getInt(cursorPicMode			        .getColumnIndex("u8Backlight"));
			videopara.astPicture[picModeIdx].contrast = (short) cursorPicMode.getInt(cursorPicMode			        .getColumnIndex("u8Contrast"));
			videopara.astPicture[picModeIdx].brightness = (short) cursorPicMode.getInt(cursorPicMode			        .getColumnIndex("u8Brightness"));			videopara.astPicture[picModeIdx].saturation = (short) cursorPicMode.getInt(cursorPicMode			        .getColumnIndex("u8Saturation"));			videopara.astPicture[picModeIdx].sharpness = (short) cursorPicMode.getInt(cursorPicMode			        .getColumnIndex("u8Sharpness"));			videopara.astPicture[picModeIdx].hue = (short) cursorPicMode.getInt(cursorPicMode.getColumnIndex("u8Hue"));			videopara.astPicture[picModeIdx].eColorTemp = EN_MS_COLOR_TEMP.values()[cursorPicMode.getInt(cursorPicMode			        .getColumnIndex("eColorTemp"))];			videopara.astPicture[picModeIdx].eVibrantColour = EN_MS_PIC_ADV.values()[cursorPicMode.getInt(cursorPicMode			        .getColumnIndex("eVibrantColour"))];			videopara.astPicture[picModeIdx].ePerfectClear = EN_MS_PIC_ADV.values()[cursorPicMode.getInt(cursorPicMode			        .getColumnIndex("ePerfectClear"))];			videopara.astPicture[picModeIdx].eDynamicContrast = EN_MS_PIC_ADV.values()[cursorPicMode			        .getInt(cursorPicMode.getColumnIndex("eDynamicContrast"))];			videopara.astPicture[picModeIdx].eDynamicBacklight = EN_MS_PIC_ADV.values()[cursorPicMode			        .getInt(cursorPicMode.getColumnIndex("eDynamicBacklight"))];			picModeIdx++;		}		cursorPicMode.close();		// query tbl_NRMode for T_MS_NR_MODE eNRMode[] of videoPara		/*Cursor cursorNRMode = userSettingDB		        .query("tbl_NRMode", null, "InputSrcType = " + inputSrcType, null, null, null, "NRMode");*/
		
		selection = " InputSrcType = ? ";
		selectionArgs[0] = String.valueOf(inputSrcType);
		
		Cursor cursorNRMode = cr.query(Uri.parse(userSettingSchema+"/nrmode"), null, selection, selectionArgs, "NRMode");		int NRModeIdx = 0;		int length1 = videopara.eNRMode.length;		while (cursorNRMode.moveToNext())		{			if (NRModeIdx > length1 - 1)			{				break;			}			videopara.eNRMode[NRModeIdx].eNR = EN_MS_NR.values()[cursorNRMode			        .getInt(cursorNRMode.getColumnIndex("eNR"))];			videopara.eNRMode[NRModeIdx].eMPEG_NR = EN_MS_MPEG_NR.values()[cursorNRMode.getInt(cursorNRMode			        .getColumnIndex("eMPEG_NR"))];			NRModeIdx++;		}		cursorNRMode.close();		// query tbl_ThreeDVideoMode for ThreeD_Video_MODE ThreeDVideoMode of		// videoPara		/*Cursor cursor3DMode = userSettingDB.query("tbl_ThreeDVideoMode", null, "InputSrcType = " + inputSrcType, null, null, null,		        null);*/
		
		Cursor cursor3DMode = cr.query(Uri.parse(userSettingSchema+"/threedvideomode/inputsrc/"+inputSrcType), null, null, null, null);		if (cursor3DMode.moveToFirst())		{			videopara.ThreeDVideoMode.eThreeDVideo = EN_ThreeD_Video.values()[cursor3DMode.getInt(cursor3DMode			        .getColumnIndex("eThreeDVideo"))];//			videopara.ThreeDVideoMode.eThreeDVideoDisplayFormat = EN_ThreeD_Video_DISPLAYFORMAT.values()[cursor3DMode//			        .getInt(cursor3DMode.getColumnIndex("eThreeDVideoDisplayFormat"))];//			videopara.ThreeDVideoMode.eThreeDVideo3DTo2D = EN_ThreeD_Video_3DTO2D.values()[cursor3DMode.getInt(cursor3DMode//			        .getColumnIndex("eThreeDVideo3DTo2D"))];						videopara.ThreeDVideoMode.eThreeDVideo3DDepth = EN_ThreeD_Video_3DDEPTH.values()[cursor3DMode			        .getInt(cursor3DMode.getColumnIndex("eThreeDVideo3DDepth"))];			videopara.ThreeDVideoMode.eThreeDVideo3DOffset = EN_ThreeD_Video_3DOFFSET.values()[cursor3DMode.getInt(cursor3DMode			        .getColumnIndex("eThreeDVideo3DOffset"))];			videopara.ThreeDVideoMode.eThreeDVideoAutoStart = EN_ThreeD_Video_AUTOSTART.values()[cursor3DMode			        .getInt(cursor3DMode.getColumnIndex("eThreeDVideoAutoStart"))];			videopara.ThreeDVideoMode.eThreeDVideo3DOutputAspect = EN_ThreeD_Video_3DOUTPUTASPECT.values()[cursor3DMode			        .getInt(cursor3DMode.getColumnIndex("eThreeDVideo3DOutputAspect"))];			videopara.ThreeDVideoMode.eThreeDVideoLRViewSwitch = EN_ThreeD_Video_LRVIEWSWITCH.values()[cursor3DMode			        .getInt(cursor3DMode.getColumnIndex("eThreeDVideoLRViewSwitch"))];		}		cursor3DMode.close();		// query tbl_ThreeDVideoMode for ThreeD_Video_MODE ThreeDVideoMode eThreeDVideoSelfAdaptiveDetect of		// videoPara		/*Cursor cursor3DSelfAdaptiveMode = userSettingDB.query("tbl_ThreeDVideoMode", null, "InputSrcType = " + EnumInputSource.E_INPUT_SOURCE_HDMI.ordinal(),				null, null, null, null);*/
		
		
		Cursor cursor3DSelfAdaptiveMode = cr.query(Uri.parse(userSettingSchema+"/threedvideomode/inputsrc/"+EnumInputSource.E_INPUT_SOURCE_HDMI.ordinal()), null, null, null, null);		if (cursor3DSelfAdaptiveMode.moveToFirst())		{
						videopara.ThreeDVideoMode.eThreeDVideoSelfAdaptiveDetect = EN_ThreeD_Video_SELFADAPTIVE_DETECT.values()[cursor3DSelfAdaptiveMode.getInt(cursor3DSelfAdaptiveMode			        .getColumnIndex("eThreeDVideoSelfAdaptiveDetect"))];		}		cursor3DSelfAdaptiveMode.close();		// query tbl_UserOverScanMode for T_MS_OVERSCAN_SETTING_USER		// stUserOverScanMode of videoPara		/*Cursor cursorUserOverScanMode = userSettingDB.query("tbl_UserOverScanMode", null, "InputSrcType = " + inputSrcType, null,		        null, null, null);*/
		
		
		Cursor cursorUserOverScanMode = cr.query(Uri.parse(userSettingSchema+"/useroverscanmode/inputsrc/"+inputSrcType), null, null, null, null);		if (cursorUserOverScanMode.moveToFirst())		{			videopara.stUserOverScanMode.OverScanHposition = (short) cursorUserOverScanMode			        .getInt(cursorUserOverScanMode.getColumnIndex("OverScanHposition"));			videopara.stUserOverScanMode.OverScanVposition = (short) cursorUserOverScanMode			        .getInt(cursorUserOverScanMode.getColumnIndex("OverScanVposition"));			videopara.stUserOverScanMode.OverScanHRatio = (short) cursorUserOverScanMode.getInt(cursorUserOverScanMode			        .getColumnIndex("OverScanHRatio"));			videopara.stUserOverScanMode.OverScanVRatio = (short) cursorUserOverScanMode.getInt(cursorUserOverScanMode			        .getColumnIndex("OverScanVRatio"));		}		cursorUserOverScanMode.close();		return videopara;	}	public EN_ThreeD_Video_DISPLAYFORMAT queryThreeDVideoDisplayFormat(int inputSrcType)	{		EN_ThreeD_Video_DISPLAYFORMAT threeDVideoDisplayFormat = EN_ThreeD_Video_DISPLAYFORMAT.DB_ThreeD_Video_DISPLAYFORMAT_NONE;		/*Cursor cursor3DMode = userSettingDB.query("tbl_ThreeDVideoMode", null, "InputSrcType = " + inputSrcType, null, null, null,		        null);*/
		
		
		Cursor cursor3DMode = cr.query(Uri.parse(userSettingSchema+"/threedvideomode/inputsrc/"+inputSrcType), null, null, null, null);		if (cursor3DMode.moveToFirst())		{			threeDVideoDisplayFormat = EN_ThreeD_Video_DISPLAYFORMAT.values()[cursor3DMode			        .getInt(cursor3DMode.getColumnIndex("eThreeDVideoDisplayFormat"))];		}		cursor3DMode.close();		return threeDVideoDisplayFormat;	}	
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
		public void updatePicModeSetting(EN_MS_VIDEOITEM eIndex, int inputSrcType, int pictureModeType, int value)	{		ContentValues vals = new ContentValues();		switch(eIndex){		case MS_VIDEOITEM_BRIGHTNESS:			vals.put("u8Brightness", value);			break;		case MS_VIDEOITEM_CONTRAST:			vals.put("u8Contrast",value);			break;		case MS_VIDEOITEM_HUE:			vals.put("u8Hue", value);			break;		case MS_VIDEOITEM_SATURATION:			vals.put("u8Saturation", value);			break;		case MS_VIDEOITEM_SHARPNESS:			vals.put("u8Sharpness", value);			break;		case MS_VIDEOITEM_BACKLIGHT:			vals.put("u8Backlight", value);			default:				break;		}		
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
	}	public int queryPicModeSetting(EN_MS_VIDEOITEM eIndex, int inputSrcType, int pictureModeType){		/*Cursor cursorPicMode = userSettingDB.query("tbl_PicMode_Setting", null, "InputSrcType = " + 0 + " AND PictureModeType = " + pictureModeType, null, null,		        null, "PictureModeType");*/
		
		Uri uri = Uri.parse(userSettingSchema+"/picmode_setting/inputsrc/0/picmode/"+pictureModeType);
		Cursor cursorPicMode = cr.query(uri, null, null, null, "PictureModeType");		cursorPicMode.moveToFirst();		int value = 0;		switch(eIndex){		case MS_VIDEOITEM_BRIGHTNESS:			value = cursorPicMode.getInt(cursorPicMode.getColumnIndex("u8Brightness"));			break;		case MS_VIDEOITEM_CONTRAST:			value = cursorPicMode.getInt(cursorPicMode.getColumnIndex("u8Contrast"));			break;		case MS_VIDEOITEM_HUE:			value = cursorPicMode.getInt(cursorPicMode.getColumnIndex("u8Hue"));			break;		case MS_VIDEOITEM_SATURATION:			value = cursorPicMode.getInt(cursorPicMode.getColumnIndex("u8Saturation"));			break;		case MS_VIDEOITEM_SHARPNESS:			value = cursorPicMode.getInt(cursorPicMode.getColumnIndex("u8Sharpness"));			break;		case MS_VIDEOITEM_BACKLIGHT:			value = cursorPicMode.getInt(cursorPicMode.getColumnIndex("u8Backlight"));			break;			default:				break;		}		cursorPicMode.close();		return value;	}	/**	 * // update T_MS_VIDEO videopara base info	 * 	 * @param model	 * @param inputSrcType	 */	public void updateVideoBasePara(T_MS_VIDEO model, int inputSrcType)	{		ContentValues vals = new ContentValues();		vals.put("ePicture", model.ePicture.ordinal());		// vals.put("u8SubBrightness", model.);		// vals.put("u8SubContrast", model.);		vals.put("enARCType", model.enARCType.ordinal());		vals.put("fOutput_RES", model.fOutput_RES.ordinal());		vals.put("tvsys", model.tvsys.ordinal());		vals.put("LastVideoStandardMode", model.LastVideoStandardMode.ordinal());		vals.put("LastAudioStandardMode", model.LastAudioStandardMode.ordinal());		vals.put("eDynamic_Contrast", model.eDynamic_Contrast.ordinal());		vals.put("eFilm", model.eFilm.ordinal());		vals.put("eTvFormat", model.eTvFormat.ordinal());
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

		try        {	        TvManager.getDatabaseManager().setDatabaseDirtyByApplication(T_VideoSetting_IDX);        }        catch (TvCommonException e)        {	        // TODO Auto-generated catch block	        e.printStackTrace();        }	}
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
		cursor.close();		return result;
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
		cursor.close();		return result;
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
		cursor.close();		return result;
	}
		/**	 * // update T_MS_PICTURE astPicture[i] of videopara	 * 	 * @param model	 * @param inputSrcType	 * @param pictureModeType	 */	public void updateVideoAstPicture(T_MS_PICTURE model, int inputSrcType, int pictureModeType)	{		ContentValues vals = new ContentValues();		vals.put("u8Backlight", model.backlight);		vals.put("u8Contrast", model.contrast);		vals.put("u8Brightness", model.brightness);		vals.put("u8Saturation", model.saturation);		vals.put("u8Sharpness", model.sharpness);		vals.put("u8Hue", model.hue);		vals.put("eColorTemp", model.eColorTemp.ordinal());		vals.put("eVibrantColour", model.eVibrantColour.ordinal());		vals.put("ePerfectClear", model.ePerfectClear.ordinal());		vals.put("eDynamicContrast", model.eDynamicContrast.ordinal());		vals.put("eDynamicBacklight", model.eDynamicBacklight.ordinal());
		
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

		try        {	        TvManager.getDatabaseManager().setDatabaseDirtyByApplication(T_PicMode_Setting_IDX);	}
        catch (TvCommonException e)	{	        // TODO Auto-generated catch block	        e.printStackTrace();        }	}	/**	 * // update tbl_NRMode with T_MS_NR_MODE eNRMode[i] of videopara	 * 	 * @param model	 * @param inputSrcType	 * @param NRModeIdx	 */	public void updateVideoNRMode(T_MS_NR_MODE model, int inputSrcType, int NRModeIdx)	{		ContentValues vals = new ContentValues();		vals.put("eNR", model.eNR.ordinal());		vals.put("eMPEG_NR", model.eMPEG_NR.ordinal());		
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

		try        {	        TvManager.getDatabaseManager().setDatabaseDirtyByApplication(T_NRMode_IDX);        }        catch (TvCommonException e)        {	        // TODO Auto-generated catch block	        e.printStackTrace();        }	}	/**	 * // update tbl_VideoSetting with T_MS_SUB_COLOR g_astSubColor of videopara	 * 	 * @param model	 * @param inputSrcType	 */	public void updateVideoAstSubColor(T_MS_SUB_COLOR model, int inputSrcType)	{		ContentValues vals = new ContentValues();		vals.put("u8SubBrightness", model.SubBrightness);		vals.put("u8SubContrast", model.SubContrast);		
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

		try        {	        TvManager.getDatabaseManager().setDatabaseDirtyByApplication(T_VideoSetting_IDX);        }        catch (TvCommonException e)        {	        // TODO Auto-generated catch block	        e.printStackTrace();        }	}	/**	 * // update tbl_ThreeDVideoMode with ThreeD_Video_MODE ThreeDVideoMode of	 * videopara	 * 	 * @param model	 * @param inputSrcType	 */	public void updateVideo3DMode(ThreeD_Video_MODE model, int inputSrcType)	{		ContentValues vals = new ContentValues();		vals.put("eThreeDVideo", model.eThreeDVideo.ordinal());		vals.put("eThreeDVideoSelfAdaptiveDetect", model.eThreeDVideoSelfAdaptiveDetect.ordinal());				vals.put("eThreeDVideoDisplayFormat", model.eThreeDVideoDisplayFormat.ordinal());		vals.put("eThreeDVideo3DTo2D", model.eThreeDVideo3DTo2D.ordinal());				vals.put("eThreeDVideo3DDepth", model.eThreeDVideo3DDepth.ordinal());		vals.put("eThreeDVideo3DOffset", model.eThreeDVideo3DOffset.ordinal());				vals.put("eThreeDVideoAutoStart", model.eThreeDVideoAutoStart.ordinal());		vals.put("eThreeDVideo3DOutputAspect", model.eThreeDVideo3DOutputAspect.ordinal());		vals.put("eThreeDVideoLRViewSwitch", model.eThreeDVideoLRViewSwitch.ordinal());		
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

		try        {	        TvManager.getDatabaseManager().setDatabaseDirtyByApplication(T_ThreeDVideoMode_IDX);        }        catch (TvCommonException e)        {	        // TODO Auto-generated catch block	        e.printStackTrace();        }	}	/**	 * // update eThreeDVideoSelfAdaptiveDetect of tbl_ThreeDVideoMode    	 * 	 * @param model	 * @param inputSrcType	 */	public void updateVideo3DAdaptiveDetectMode(EN_ThreeD_Video_SELFADAPTIVE_DETECT model, int inputSrcType)	{		ContentValues vals = new ContentValues();		vals.put("eThreeDVideoSelfAdaptiveDetect", model.ordinal());				
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

		try        {	        TvManager.getDatabaseManager().setDatabaseDirtyByApplication(T_ThreeDVideoMode_IDX);        }        catch (TvCommonException e)        {	        // TODO Auto-generated catch block	        e.printStackTrace();        }	}	
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
		/**	 * // update eThreeDVideoDisplayFormat of tbl_ThreeDVideoMode    	 * 	 * @param model	 * @param inputSrcType	 */	public void updateVideo3DDisplayFormat(EN_ThreeD_Video_DISPLAYFORMAT model, int inputSrcType)	{		ContentValues vals = new ContentValues();		vals.put("eThreeDVideoDisplayFormat", model.ordinal());			
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

		try        {	        TvManager.getDatabaseManager().setDatabaseDirtyByApplication(T_ThreeDVideoMode_IDX);        }        catch (TvCommonException e)        {	        // TODO Auto-generated catch block	        e.printStackTrace();        }	}		/**	 * // update tbl_UserOverScanMode with T_MS_OVERSCAN_SETTING_USER	 * stUserOverScanMode of videopara	 * 	 * @param model	 * @param inputSrcType	 */	public void updateVideoUserOverScanMode(T_MS_OVERSCAN_SETTING_USER model, int inputSrcType)	{		ContentValues vals = new ContentValues();		vals.put("OverScanHposition", model.OverScanHposition);		vals.put("OverScanVposition", model.OverScanVposition);		vals.put("OverScanHRatio", model.OverScanHRatio);		vals.put("OverScanVRatio", model.OverScanVRatio);		
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

		try        {	        TvManager.getDatabaseManager().setDatabaseDirtyByApplication(T_UserOverScanMode_IDX);        }        catch (TvCommonException e)        {	        // TODO Auto-generated catch block	        e.printStackTrace();        }	}	// DB access control for T_MS_COLOR_TEMP_DATA stUsrColorTemp;		public T_MS_COLOR_TEMP_DATA queryUsrColorTmpData()		{			/*Cursor cursor = userSettingDB.query("tbl_UserColorTemp", null, null, null, null, null,			        null);*/
			
			Cursor cursor = cr.query(Uri.parse(userSettingSchema+"/usercolortemp"), null, null, null, null);			int i = 0;			while (cursor.moveToNext())			{				if(i> (EnumInputSource.E_INPUT_SOURCE_NUM.ordinal() - 1)) break;				stUsrColorTemp.redgain = (short)cursor.getInt(cursor.getColumnIndex("u8RedGain"));				stUsrColorTemp.greengain = (short)cursor.getInt(cursor.getColumnIndex("u8GreenGain"));				stUsrColorTemp.bluegain = (short)cursor.getInt(cursor.getColumnIndex("u8BlueGain"));				stUsrColorTemp.redoffset = (short)cursor.getInt(cursor.getColumnIndex("u8RedOffset"));				stUsrColorTemp.greenoffset = (short)cursor.getInt(cursor.getColumnIndex("u8GreenOffset"));				stUsrColorTemp.blueoffset = (short)cursor.getInt(cursor.getColumnIndex("u8BlueOffset"));				i++;			}			cursor.close();			return stUsrColorTemp;		}		public void updateUsrColorTmpData(T_MS_COLOR_TEMP_DATA model)		{			ContentValues vals = new ContentValues();			vals.put("u8RedGain", model.redgain);			vals.put("u8GreenGain", model.greengain);			vals.put("u8BlueGain", model.bluegain);			vals.put("u8RedOffset", model.redoffset);			vals.put("u8GreenOffset", model.greenoffset);			vals.put("u8BlueOffset", model.blueoffset);			
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

			try	        {		        TvManager.getDatabaseManager().setDatabaseDirtyByApplication(T_USER_COLORTEMP_IDX);	        }	        catch (TvCommonException e)	        {		        // TODO Auto-generated catch block		        e.printStackTrace();	        }		}		// DB access control for T_MS_COLOR_TEMPEX_DATA stUsrColorTempEx[];	public T_MS_COLOR_TEMPEX_DATA[] queryUsrColorTmpExData()	{		/*Cursor cursor = userSettingDB.query("tbl_UserColorTempEx", null, null, null, null, null,		        null);*/
		
		Cursor cursor = cr.query(Uri.parse(userSettingSchema+"/usercolortempex"), null, null, null, null);		int i = 0;		while (cursor.moveToNext())		{			if(i> (EnumInputSource.E_INPUT_SOURCE_NUM.ordinal() - 1)) break;			stUsrColorTempEx[i].redgain = cursor.getInt(cursor.getColumnIndex("u16RedGain"));			stUsrColorTempEx[i].greengain = cursor.getInt(cursor.getColumnIndex("u16GreenGain"));			stUsrColorTempEx[i].bluegain = cursor.getInt(cursor.getColumnIndex("u16BlueGain"));			stUsrColorTempEx[i].redoffset = cursor.getInt(cursor.getColumnIndex("u16RedOffset"));			stUsrColorTempEx[i].greenoffset = cursor.getInt(cursor.getColumnIndex("u16GreenOffset"));			stUsrColorTempEx[i].blueoffset = cursor.getInt(cursor.getColumnIndex("u16BlueOffset"));			i++;		}		cursor.close();		return stUsrColorTempEx;	}	public void updateUsrColorTmpExData(T_MS_COLOR_TEMPEX_DATA model, int colorTmpIdx)	{		ContentValues vals = new ContentValues();		vals.put("u16RedGain", model.redgain);		vals.put("u16GreenGain", model.greengain);		vals.put("u16BlueGain", model.bluegain);		vals.put("u16RedOffset", model.redoffset);		vals.put("u16GreenOffset", model.greenoffset);		vals.put("u16BlueOffset", model.blueoffset);		
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

		try        {	        TvManager.getDatabaseManager().setDatabaseDirtyByApplication(T_USER_COLORTEMP_EX_IDX);        }        catch (TvCommonException e)        {	        // TODO Auto-generated catch block	        e.printStackTrace();        }	}	// DB access control for MS_USER_SYSTEM_SETTING stUsrData;	public MS_USER_SYSTEM_SETTING queryUserSysSetting()	{
		/*if(userSettingDB == null){
			Log.e("KKJAVAAPI", "huge error,the usersettingDB is null");
		}
		if(!userSettingDB.isOpen()){
			Log.e("KKJAVAAPI", "huge error,the usersettingDB is not open");
		}*/
		/*Cursor cursor = userSettingDB.query("tbl_SystemSetting", null, null, null, null, null, null);*/
		Cursor cursor = cr.query(Uri.parse(userSettingSchema+"/systemsetting"), null, null, null, null);		if (cursor.moveToFirst())		{			stUsrData.fRunInstallationGuide = cursor.getInt(cursor.getColumnIndex("fRunInstallationGuide")) == 0 ? false			        : true;			stUsrData.fNoChannel = cursor.getInt(cursor.getColumnIndex("fNoChannel")) == 0 ? false : true;			stUsrData.bDisableSiAutoUpdate = cursor.getInt(cursor.getColumnIndex("bDisableSiAutoUpdate")) == 0 ? false			        : true;			stUsrData.enInputSourceType = EnumInputSource.values()[cursor.getInt(cursor			        .getColumnIndex("enInputSourceType"))];			stUsrData.Country = MEMBER_COUNTRY.values()[cursor.getInt(cursor.getColumnIndex("Country"))];			stUsrData.enCableOperators = EN_CABLE_OPERATORS.values()[cursor.getInt(cursor			        .getColumnIndex("enCableOperators"))];			stUsrData.enSatellitePlatform = EN_SATELLITE_PLATFORM.values()[cursor.getInt(cursor			        .getColumnIndex("enSatellitePlatform"))];			// stUsrData. = (short)			// cursor.getInt(cursor.getColumnIndex("u16NetworkId"));
			EnumLanguage eLang = EnumLanguage.values()[cursor.getInt(cursor.getColumnIndex("Language"))];
			if(eLang == EnumLanguage.E_ENGLISH)
				stUsrData.enLanguage = MEMBER_LANGUAGE.E_LANGUAGE_ENGLISH;
			else if(eLang == EnumLanguage.E_CHINESE)				stUsrData.enLanguage = MEMBER_LANGUAGE.E_LANGUAGE_CHINESE_S;
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
			}			// stUsrData. = (short)			// cursor.getInt(cursor.getColumnIndex("en3DARC"));			stUsrData.enSPDIFMODE = SPDIF_TYPE.values()[cursor.getInt(cursor.getColumnIndex("enSPDIFMODE"))];			stUsrData.fSoftwareUpdate = (short) cursor.getInt(cursor.getColumnIndex("fSoftwareUpdate"));			stUsrData.u8OADTime = (short) cursor.getInt(cursor.getColumnIndex("U8OADTime"));			stUsrData.fOADScanAfterWakeup = (short) cursor.getInt(cursor.getColumnIndex("fOADScanAfterWakeup"));			stUsrData.fAutoVolume = (short) cursor.getInt(cursor.getColumnIndex("fAutoVolume"));			stUsrData.fDcPowerOFFMode = (short) cursor.getInt(cursor.getColumnIndex("fDcPowerOFFMode"));			stUsrData.DtvRoute = (short) cursor.getInt(cursor.getColumnIndex("DtvRoute"));			stUsrData.ScartOutRGB = (short) cursor.getInt(cursor.getColumnIndex("ScartOutRGB"));			stUsrData.U8Transparency = (short) cursor.getInt(cursor.getColumnIndex("U8Transparency"));			stUsrData.u32MenuTimeOut = cursor.getLong(cursor.getColumnIndex("u32MenuTimeOut"));			stUsrData.AudioOnly = (short) cursor.getInt(cursor.getColumnIndex("AudioOnly"));			stUsrData.bEnableWDT = (short) cursor.getInt(cursor.getColumnIndex("bEnableWDT"));			stUsrData.u8FavoriteRegion = (short) cursor.getInt(cursor.getColumnIndex("u8FavoriteRegion"));			stUsrData.u8Bandwidth = (short) cursor.getInt(cursor.getColumnIndex("u8Bandwidth"));			stUsrData.u8TimeShiftSizeType = (short) cursor.getInt(cursor.getColumnIndex("u8TimeShiftSizeType"));			stUsrData.fOadScan = (short) cursor.getInt(cursor.getColumnIndex("fOadScan"));			stUsrData.bEnablePVRRecordAll = (short) cursor.getInt(cursor.getColumnIndex("bEnablePVRRecordAll"));			stUsrData.u8ColorRangeMode = (short) cursor.getInt(cursor.getColumnIndex("u8ColorRangeMode"));			stUsrData.u8HDMIAudioSource = (short) cursor.getInt(cursor.getColumnIndex("u8HDMIAudioSource"));			stUsrData.bEnableAlwaysTimeshift = (short) cursor.getInt(cursor.getColumnIndex("bEnableAlwaysTimeshift"));			stUsrData.eSUPER = EN_MS_SUPER.values()[cursor.getInt(cursor.getColumnIndex("eSUPER"))];			stUsrData.bUartBus = cursor.getInt(cursor.getColumnIndex("bUartBus")) == 0 ? false : true;			stUsrData.m_AutoZoom = (short) cursor.getInt(cursor.getColumnIndex("m_AutoZoom"));			stUsrData.bOverScan = cursor.getInt(cursor.getColumnIndex("bOverScan")) == 0 ? false : true;			stUsrData.m_u8BrazilVideoStandardType = (short) cursor.getInt(cursor			        .getColumnIndex("m_u8BrazilVideoStandardType"));			stUsrData.m_u8SoftwareUpdateMode = (short) cursor.getInt(cursor.getColumnIndex("m_u8SoftwareUpdateMode"));			stUsrData.u32OSD_Active_Time = cursor.getLong(cursor.getColumnIndex("OSD_Active_Time"));			stUsrData.m_MessageBoxExist = cursor.getInt(cursor.getColumnIndex("m_MessageBoxExist")) == 0 ? false : true;			stUsrData.u16LastOADVersion = cursor.getInt(cursor.getColumnIndex("u16LastOADVersion"));			stUsrData.bEnableAutoChannelUpdate = cursor.getInt(cursor.getColumnIndex("bEnableAutoChannelUpdate")) == 0 ? false			        : true;
			stUsrData.standbyNoOperation = cursor.getInt(cursor.getColumnIndex("standbyNoOperation"));
			stUsrData.standbyNoSignal = cursor.getInt(cursor.getColumnIndex("standbyNoSignal")) == 0 ? false : true;
			stUsrData.screenSaveMode = cursor.getInt(cursor.getColumnIndex("screenSaveMode")) == 0 ? false : true;
			stUsrData.smartEnergySaving = SmartEnergySavingMode.values()[cursor.getInt(cursor.getColumnIndex("smartEnergySaving"))];
			stUsrData.colorWheelMode = ColorWheelMode.values()[cursor.getInt(cursor.getColumnIndex("colorWheelMode"))];
		}		cursor.close();		return stUsrData;	}	public void updateUserSysSetting(MS_USER_SYSTEM_SETTING model)	{		EnumLanguage eLang;		ContentValues vals = new ContentValues();		vals.put("fRunInstallationGuide", model.fRunInstallationGuide ? 1 : 0);		vals.put("fNoChannel", model.fNoChannel ? 1 : 0);		vals.put("bDisableSiAutoUpdate", model.bDisableSiAutoUpdate ? 1 : 0);		vals.put("enInputSourceType", model.enInputSourceType.ordinal());		vals.put("Country", model.Country.ordinal());		vals.put("enCableOperators", model.enCableOperators.ordinal());		vals.put("enSatellitePlatform", model.enSatellitePlatform.ordinal());		// vals.put("u16NetworkId", model.);				if(model.enLanguage == MEMBER_LANGUAGE.E_LANGUAGE_ENGLISH)		{			eLang = EnumLanguage.E_ENGLISH;		}		else if(model.enLanguage == MEMBER_LANGUAGE.E_LANGUAGE_CHINESE_T)		{			eLang = EnumLanguage.E_ACHINESE;		}		else		{			eLang = EnumLanguage.E_CHINESE;		}		vals.put("Language", eLang.ordinal());		// vals.put("en3DARC", model);		vals.put("enSPDIFMODE", model.enSPDIFMODE.ordinal());		vals.put("fSoftwareUpdate", model.fSoftwareUpdate);		vals.put("U8OADTime", model.u8OADTime);		vals.put("fOADScanAfterWakeup", model.fOADScanAfterWakeup);		vals.put("fAutoVolume", model.fAutoVolume);		vals.put("fDcPowerOFFMode", model.fDcPowerOFFMode);		vals.put("DtvRoute", model.DtvRoute);		vals.put("ScartOutRGB", model.ScartOutRGB);		vals.put("U8Transparency", model.U8Transparency);		vals.put("u32MenuTimeOut", model.u32MenuTimeOut);		vals.put("AudioOnly", model.AudioOnly);		vals.put("bEnableWDT", model.bEnableWDT);		vals.put("u8FavoriteRegion", model.u8FavoriteRegion);		vals.put("u8Bandwidth", model.u8Bandwidth);		vals.put("u8TimeShiftSizeType", model.u8TimeShiftSizeType);		vals.put("fOadScan", model.fOadScan);		vals.put("bEnablePVRRecordAll", model.bEnablePVRRecordAll);		vals.put("u8ColorRangeMode", model.u8ColorRangeMode);		vals.put("u8HDMIAudioSource", model.u8HDMIAudioSource);		vals.put("bEnableAlwaysTimeshift", model.bEnableAlwaysTimeshift);		vals.put("eSUPER", model.eSUPER.ordinal());		vals.put("bUartBus", model.bUartBus ? 1 : 0);		vals.put("m_AutoZoom", model.m_AutoZoom);		vals.put("bOverScan", model.bOverScan ? 1 : 0);		vals.put("m_u8BrazilVideoStandardType", model.m_u8BrazilVideoStandardType);		vals.put("m_u8SoftwareUpdateMode", model.m_u8SoftwareUpdateMode);		vals.put("OSD_Active_Time", model.u32OSD_Active_Time);		vals.put("m_MessageBoxExist", model.m_MessageBoxExist ? 1 : 0);		vals.put("u16LastOADVersion", model.u16LastOADVersion);		vals.put("bEnableAutoChannelUpdate", model.bEnableAutoChannelUpdate ? 1 : 0);
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

		try        {	        TvManager.getDatabaseManager().setDatabaseDirtyByApplication(T_SystemSetting_IDX);        }        catch (TvCommonException e)        {	        // TODO Auto-generated catch block	        e.printStackTrace();        }	}	// DB access control for MS_USER_SUBTITLE_SETTING stSubtitleSet;	public MS_USER_SUBTITLE_SETTING queryUserSubtitleSetting()	{		/*Cursor cursor = userSettingDB.query("tbl_SubtitleSetting", null, null, null, null, null, null);*/
		
		Cursor cursor = cr.query(Uri.parse(userSettingSchema+"/subtitlesetting"), null, null, null, null);					if (cursor.moveToFirst())		{
			EnumLanguage eLang1 = EnumLanguage.values()[cursor.getInt(cursor.getColumnIndex("SubtitleDefaultLanguage"))];			EnumLanguage eLang2 = EnumLanguage.values()[cursor.getInt(cursor.getColumnIndex("SubtitleDefaultLanguage_2"))];			if(eLang1 == EnumLanguage.E_ENGLISH)				stSubtitleSet.SubtitleDefaultLanguage = MEMBER_LANGUAGE.E_LANGUAGE_ENGLISH;			else if(eLang1 == EnumLanguage.E_CHINESE)				stSubtitleSet.SubtitleDefaultLanguage = MEMBER_LANGUAGE.E_LANGUAGE_CHINESE_S;			else if(eLang1 == EnumLanguage.E_ACHINESE)				stSubtitleSet.SubtitleDefaultLanguage = MEMBER_LANGUAGE.E_LANGUAGE_CHINESE_T;			else			{				stSubtitleSet.SubtitleDefaultLanguage = MEMBER_LANGUAGE.E_LANGUAGE_CHINESE_S;				ContentValues vals = new ContentValues();				vals.put("SubtitleDefaultLanguage", stUsrData.enLanguage.ordinal());				
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
			}			if(eLang2 == EnumLanguage.E_ENGLISH)				stSubtitleSet.SubtitleDefaultLanguage_2 = MEMBER_LANGUAGE.E_LANGUAGE_ENGLISH;			else if(eLang2 == EnumLanguage.E_CHINESE)				stSubtitleSet.SubtitleDefaultLanguage_2 = MEMBER_LANGUAGE.E_LANGUAGE_CHINESE_S;			else if(eLang2 == EnumLanguage.E_ACHINESE)				stSubtitleSet.SubtitleDefaultLanguage_2 = MEMBER_LANGUAGE.E_LANGUAGE_CHINESE_T;			else			{				stSubtitleSet.SubtitleDefaultLanguage_2 = MEMBER_LANGUAGE.E_LANGUAGE_CHINESE_S;				ContentValues vals = new ContentValues();				vals.put("SubtitleDefaultLanguage_2", stUsrData.enLanguage.ordinal());				
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
			}			stSubtitleSet.fHardOfHearing = cursor.getInt(cursor.getColumnIndex("fHardOfHearing")) == 0 ? false : true;			stSubtitleSet.fEnableSubTitle = cursor.getInt(cursor.getColumnIndex("fEnableSubTitle")) == 0 ? false : true;		}		cursor.close();		return stSubtitleSet;	}	public void updateUserSubtitleSetting(MS_USER_SUBTITLE_SETTING model)	{		EnumLanguage eLang1;		EnumLanguage eLang2;		ContentValues vals = new ContentValues();				if(model.SubtitleDefaultLanguage == MEMBER_LANGUAGE.E_LANGUAGE_ENGLISH)		{			eLang1 = EnumLanguage.E_ENGLISH;		}		else if(model.SubtitleDefaultLanguage == MEMBER_LANGUAGE.E_LANGUAGE_CHINESE_T)		{			eLang1 = EnumLanguage.E_CHINESE;		}		else		{			eLang1 = EnumLanguage.E_ACHINESE;		}				if(model.SubtitleDefaultLanguage_2 == MEMBER_LANGUAGE.E_LANGUAGE_ENGLISH)		{			eLang2 = EnumLanguage.E_ENGLISH;		}		else if(model.SubtitleDefaultLanguage_2 == MEMBER_LANGUAGE.E_LANGUAGE_CHINESE_T)		{			eLang2 = EnumLanguage.E_CHINESE;		}		else		{			eLang2 = EnumLanguage.E_ACHINESE;		}					vals.put("SubtitleDefaultLanguage", eLang1.ordinal());		vals.put("SubtitleDefaultLanguage_2", eLang2.ordinal());		vals.put("fHardOfHearing", model.fHardOfHearing ? 1 : 0);		vals.put("fEnableSubTitle", model.fEnableSubTitle ? 1 : 0);		
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

		try        {	        TvManager.getDatabaseManager().setDatabaseDirtyByApplication(T_SubtitleSetting_IDX);        }        catch (TvCommonException e)        {	        // TODO Auto-generated catch block	        e.printStackTrace();        }	}	// DB access control for MS_USER_LOCATION_SETTING stUserLocationSetting;	public MS_USER_LOCATION_SETTING queryUserLocSetting()	{		/*Cursor cursor = userSettingDB.query("tbl_UserLocationSetting", null, null, null, null, null, null);*/
		
		Cursor cursor = cr.query(Uri.parse(userSettingSchema+"/userlocationsetting"), null, null, null, null);		while (cursor.moveToNext())		{			stUserLocationSetting.mLocationNo = (short) cursor.getInt(cursor.getColumnIndex("u16LocationNo"));			stUserLocationSetting.mManualLongitude = (short) cursor.getInt(cursor.getColumnIndex("s16ManualLongitude"));			stUserLocationSetting.mManualLatitude = (short) cursor.getInt(cursor.getColumnIndex("s16ManualLatitude"));		}		cursor.close();		return stUserLocationSetting;	}	public void updateUserLocSetting(MS_USER_LOCATION_SETTING model)	{		ContentValues vals = new ContentValues();		vals.put("u16LocationNo", model.mLocationNo);		vals.put("s16ManualLongitude", model.mManualLongitude);		vals.put("s16ManualLatitude", model.mManualLatitude);		
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

		try        {	        TvManager.getDatabaseManager().setDatabaseDirtyByApplication(T_UserLocationSetting_IDX);        }        catch (TvCommonException e)        {	        // TODO Auto-generated catch block	        e.printStackTrace();        }	}	/**	 * DB access control for MS_USER_SOUND_SETTING soundpara (tbl_SoundSetting)	 * 	 * @return	 */	public MS_USER_SOUND_SETTING querySoundSetting()	{		EnumLanguage eLang1;		EnumLanguage eLang2;		/*Cursor cursor = userSettingDB.query("tbl_SoundSetting", null, null, null, null, null, null);*/
		
		Cursor cursor = cr.query(Uri.parse(userSettingSchema+"/soundsetting"), null, null, null, null);		if (cursor.moveToFirst())		{			soundpara.SoundMode = EN_SOUND_MODE.values()[cursor.getInt(cursor.getColumnIndex("SoundMode"))];			soundpara.AudysseyDynamicVolume = EN_AUDYSSEY_DYNAMIC_VOLUME_MODE.values()[cursor.getInt(cursor			        .getColumnIndex("AudysseyDynamicVolume"))];			soundpara.AudysseyEQ = EN_AUDYSSEY_EQ_MODE.values()[cursor.getInt(cursor.getColumnIndex("AudysseyEQ"))];			soundpara.SurroundSoundMode = EN_SURROUND_SYSTEM_TYPE.values()[cursor.getInt(cursor			        .getColumnIndex("SurroundSoundMode"))];			//soundpara.Surround = EN_SURROUND_TYPE.values()[cursor.getInt(cursor.getColumnIndex("Surround"))];			soundpara.SurroundMode = EN_SURROUND_MODE.values()[cursor.getInt(cursor.getColumnIndex("Surround"))];			soundpara.bEnableAVC = cursor.getInt(cursor.getColumnIndex("bEnableAVC")) == 0 ? false : true;			soundpara.Volume = (short) cursor.getInt(cursor.getColumnIndex("Volume"));			soundpara.HPVolume = (short) cursor.getInt(cursor.getColumnIndex("HPVolume"));			soundpara.Balance = (short) cursor.getInt(cursor.getColumnIndex("Balance"));			soundpara.Primary_Flag = (short) cursor.getInt(cursor.getColumnIndex("Primary_Flag"));			eLang1 = EnumLanguage.values()[cursor.getInt(cursor.getColumnIndex("enSoundAudioLan1"))];			eLang2 = EnumLanguage.values()[cursor.getInt(cursor.getColumnIndex("enSoundAudioLan2"))];						if(eLang1 == EnumLanguage.E_ENGLISH)				soundpara.enSoundAudioLan1 = MEMBER_LANGUAGE.E_LANGUAGE_ENGLISH;			else if(eLang1 == EnumLanguage.E_CHINESE)				soundpara.enSoundAudioLan1 = MEMBER_LANGUAGE.E_LANGUAGE_CHINESE_S;			else if(eLang1 == EnumLanguage.E_ACHINESE)				soundpara.enSoundAudioLan1 = MEMBER_LANGUAGE.E_LANGUAGE_CHINESE_T;			else			{				soundpara.enSoundAudioLan1 = MEMBER_LANGUAGE.E_LANGUAGE_CHINESE_S;				ContentValues vals = new ContentValues();				vals.put("enSoundAudioLan1", soundpara.enSoundAudioLan1.ordinal());				
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
			}			if(eLang2 == EnumLanguage.E_ENGLISH)				soundpara.enSoundAudioLan2 = MEMBER_LANGUAGE.E_LANGUAGE_ENGLISH;			else if(eLang2 == EnumLanguage.E_CHINESE)				soundpara.enSoundAudioLan2 = MEMBER_LANGUAGE.E_LANGUAGE_CHINESE_S;			else if(eLang2 == EnumLanguage.E_ACHINESE)				soundpara.enSoundAudioLan2 = MEMBER_LANGUAGE.E_LANGUAGE_CHINESE_T;			else			{				soundpara.enSoundAudioLan2 = MEMBER_LANGUAGE.E_LANGUAGE_CHINESE_S;				ContentValues vals = new ContentValues();				vals.put("enSoundAudioLan2", soundpara.enSoundAudioLan2.ordinal());				
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
			}						soundpara.MUTE_Flag = (short) cursor.getInt(cursor.getColumnIndex("MUTE_Flag"));			soundpara.enSoundAudioChannel = EN_AUD_MODE.values()[cursor.getInt(cursor			        .getColumnIndex("enSoundAudioChannel"))];			soundpara.bEnableAD = cursor.getInt(cursor.getColumnIndex("bEnableAD")) == 0 ? false : true;			soundpara.ADVolume = (short) cursor.getInt(cursor.getColumnIndex("ADVolume"));			soundpara.ADOutput = EN_SOUND_AD_OUTPUT.values()[cursor.getInt(cursor.getColumnIndex("ADOutput"))];			soundpara.SPDIF_Delay = (short) cursor.getInt(cursor.getColumnIndex("SPDIF_Delay"));			soundpara.Speaker_Delay = (short) cursor.getInt(cursor.getColumnIndex("Speaker_Delay"));			// soundpara. = (short)			// cursor.getInt(cursor.getColumnIndex("SpeakerPreScale"));			// soundpara. = (short)			// cursor.getInt(cursor.getColumnIndex("HeadPhonePreScale"));			// soundpara. = (short)			// cursor.getInt(cursor.getColumnIndex("SCART1PreScale"));			// soundpara = (short)			// cursor.getInt(cursor.getColumnIndex("SCART2PreScale"));			// soundpara. = (short)			// cursor.getInt(cursor.getColumnIndex("bEnableHI"));
			soundpara.hdmi1AudioSource = HdmiAudioSource.values()[cursor.getInt(cursor.getColumnIndex("hdmi1AudioSource"))];
			soundpara.hdmi2AudioSource = HdmiAudioSource.values()[cursor.getInt(cursor.getColumnIndex("hdmi2AudioSource"))];
			soundpara.hdmi3AudioSource = HdmiAudioSource.values()[cursor.getInt(cursor.getColumnIndex("hdmi3AudioSource"))];
			soundpara.hdmi4AudioSource = HdmiAudioSource.values()[cursor.getInt(cursor.getColumnIndex("hdmi4AudioSource"))];		}		cursor.close();		return soundpara;	}	public void updateSoundSetting(MS_USER_SOUND_SETTING model)	{		EnumLanguage eLang1;		EnumLanguage eLang2;		ContentValues vals = new ContentValues();		vals.put("SoundMode", model.SoundMode.ordinal());		vals.put("AudysseyDynamicVolume", model.AudysseyDynamicVolume.ordinal());		vals.put("AudysseyEQ", model.AudysseyEQ.ordinal());		vals.put("SurroundSoundMode", model.SurroundSoundMode.ordinal());		//vals.put("Surround", model.Surround.ordinal());		vals.put("Surround", model.SurroundMode.ordinal());		vals.put("bEnableAVC", model.bEnableAVC ? 1 : 0);		vals.put("Volume", model.Volume);		vals.put("HPVolume", model.HPVolume);		vals.put("Balance", model.Balance);		vals.put("Primary_Flag", model.Primary_Flag);		if(model.enSoundAudioLan1 == MEMBER_LANGUAGE.E_LANGUAGE_ENGLISH)		{			eLang1 = EnumLanguage.E_ENGLISH;		}		else if(model.enSoundAudioLan1 == MEMBER_LANGUAGE.E_LANGUAGE_CHINESE_T)		{			eLang1 = EnumLanguage.E_CHINESE;		}		else		{			eLang1 = EnumLanguage.E_ACHINESE;		}				if(model.enSoundAudioLan2 == MEMBER_LANGUAGE.E_LANGUAGE_ENGLISH)		{			eLang2 = EnumLanguage.E_ENGLISH;		}		else if(model.enSoundAudioLan2 == MEMBER_LANGUAGE.E_LANGUAGE_CHINESE_T)		{			eLang2 = EnumLanguage.E_CHINESE;		}		else		{			eLang2 = EnumLanguage.E_ACHINESE;		}				vals.put("enSoundAudioLan1", eLang1.ordinal());		vals.put("enSoundAudioLan2", eLang2.ordinal());		vals.put("MUTE_Flag", model.MUTE_Flag);		vals.put("enSoundAudioChannel", model.enSoundAudioChannel.ordinal());		vals.put("bEnableAD", model.bEnableAD ? 1 : 0);		vals.put("ADVolume", model.ADVolume);		vals.put("ADOutput", model.ADOutput.ordinal());		vals.put("SPDIF_Delay", model.SPDIF_Delay);		vals.put("Speaker_Delay", model.Speaker_Delay);		//vals.put("spdifMode", model.spdifMode.ordinal());		// vals.put("SpeakerPreScale", model.);		// vals.put("HeadPhonePreScale", model.);		// vals.put("SCART1PreScale", model.);		// vals.put("SCART2PreScale", model.);		// vals.put("bEnableHI", model.);
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

		try        {	        TvManager.getDatabaseManager().setDatabaseDirtyByApplication(T_SoundSetting_IDX);        }        catch (TvCommonException e)        {	        // TODO Auto-generated catch block	        e.printStackTrace();        }	}	/**	 * DB access control for SoundModeSeting astSoundModeSetting[]	 * (tbl_SoundModeSetting)	 * 	 * @return	 */	public SoundModeSeting[] querySoundModeSettings()	{		/*Cursor cursor = userSettingDB.query("tbl_SoundModeSetting", null, null, null, null, null, null);*/
		
		Cursor cursor = cr.query(Uri.parse(userSettingSchema+"/soundmodesetting"), null, null, null, null);		int i = 0;		int length = astSoundModeSetting.length;		while (cursor.moveToNext())		{			if (i > length - 1)			{				break;			}			astSoundModeSetting[i].Bass = (short) cursor.getInt(cursor.getColumnIndex("Bass"));			astSoundModeSetting[i].Treble = (short) cursor.getInt(cursor.getColumnIndex("Treble"));			astSoundModeSetting[i].EqBand1 = (short) cursor.getInt(cursor.getColumnIndex("EqBand1"));			astSoundModeSetting[i].EqBand2 = (short) cursor.getInt(cursor.getColumnIndex("EqBand2"));			astSoundModeSetting[i].EqBand3 = (short) cursor.getInt(cursor.getColumnIndex("EqBand3"));			astSoundModeSetting[i].EqBand4 = (short) cursor.getInt(cursor.getColumnIndex("EqBand4"));			astSoundModeSetting[i].EqBand5 = (short) cursor.getInt(cursor.getColumnIndex("EqBand5"));			astSoundModeSetting[i].EqBand6 = (short) cursor.getInt(cursor.getColumnIndex("EqBand6"));			astSoundModeSetting[i].EqBand7 = (short) cursor.getInt(cursor.getColumnIndex("EqBand7"));			astSoundModeSetting[i].UserMode = cursor.getInt(cursor.getColumnIndex("UserMode")) == 0 ? false : true;			astSoundModeSetting[i].Balance = (short) cursor.getInt(cursor.getColumnIndex("Balance"));			astSoundModeSetting[i].enSoundAudioChannel = EN_AUD_MODE.values()[cursor.getInt(cursor			        .getColumnIndex("enSoundAudioChannel"))];			i++;		}		cursor.close();		return astSoundModeSetting;	}	public void updateSoundModeSetting(SoundModeSeting model, int soundModeType)	{		ContentValues vals = new ContentValues();		vals.put("Bass", model.Bass);		vals.put("Treble", model.Treble);		vals.put("EqBand1", model.EqBand1);		vals.put("EqBand2", model.EqBand2);		vals.put("EqBand3", model.EqBand3);		vals.put("EqBand4", model.EqBand4);		vals.put("EqBand5", model.EqBand5);		vals.put("EqBand6", model.EqBand6);		vals.put("EqBand7", model.EqBand7);		vals.put("UserMode", model.UserMode ? 1 : 0);		vals.put("Balance", model.Balance);		vals.put("enSoundAudioChannel", model.enSoundAudioChannel.ordinal());		
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

		try        {	        TvManager.getDatabaseManager().setDatabaseDirtyByApplication(T_SoundMode_Setting_IDX);        }        catch (TvCommonException e)        {	        // TODO Auto-generated catch block	        e.printStackTrace();        }	}	public int queryPCHPos(){		/*Cursor cursor = userSettingDB.query("tbl_UserPCModeSetting", null, "_id = 9", null, null, null, null);*/
		
		Cursor cursor = cr.query(Uri.parse(userSettingSchema+"/userpcmodesetting/9"), null, null, null, null);		int value = -1;		if (cursor.moveToFirst()){			value = cursor.getInt(cursor.getColumnIndex("u16UI_HorizontalStart"));		}		cursor.close();		return value;	}		public int queryPCVPos(){		/*Cursor cursor = userSettingDB.query("tbl_UserPCModeSetting", null, "_id = 9", null, null, null, null);*/
		
		Cursor cursor = cr.query(Uri.parse(userSettingSchema+"/userpcmodesetting/9"), null, null, null, null);
				int value = -1;		if (cursor.moveToFirst()){			value = cursor.getInt(cursor.getColumnIndex("u16UI_VorizontalStart"));		}		cursor.close();		return value;	}		public int queryPCClock(){		/*Cursor cursor = userSettingDB.query("tbl_UserPCModeSetting", null, "_id = 9", null, null, null, null);*/
		
		Cursor cursor = cr.query(Uri.parse(userSettingSchema+"/userpcmodesetting/9"), null, null, null, null);
				int value = -1;		if (cursor.moveToFirst()){			value = cursor.getInt(cursor.getColumnIndex("u16UI_Clock"));		}		cursor.close();		return value;	}		public int queryPCPhase(){		/*Cursor cursor = userSettingDB.query("tbl_UserPCModeSetting", null, "_id = 9", null, null, null, null);*/
		
		Cursor cursor = cr.query(Uri.parse(userSettingSchema+"/userpcmodesetting/9"), null, null, null, null);
				int value = -1;		if (cursor.moveToFirst()){			value = cursor.getInt(cursor.getColumnIndex("u16UI_Phase"));		}		cursor.close();		return value;	}
	
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
		T_MS_VIDEO videopara;
	T_MS_COLOR_TEMPEX_DATA colorParaEx;	// T_MS_COLOR_TEMP colorPara;	// MS_ADC_SETTING adcPara;	T_MS_COLOR_TEMP_DATA stUsrColorTemp;	T_MS_COLOR_TEMPEX_DATA stUsrColorTempEx[];	MS_USER_SYSTEM_SETTING stUsrData;	MS_USER_SUBTITLE_SETTING stSubtitleSet;	MS_USER_LOCATION_SETTING stUserLocationSetting;	MS_CEC_SETTING stCECPara;	MS_USER_SOUND_SETTING soundpara;	public SoundModeSeting astSoundModeSetting[];	// ------------------------------------------------------------	// Factory Data	// ------------------------------------------------------------	// / factory color temperature	T_MS_COLOR_TEMP m_stFactoryColorTemp;	T_MS_COLOR_TEMPEX m_stFactoryColorTempEx;	// / factory ADC setting	MS_ADC_SETTING m_stFactoryAdc;	// / factory NLA setting	MS_NLA_SETTING m_pastNLASet;	// / factory external setting	MS_FACTORY_EXTERN_SETTING m_stFactoryExt;	// / factory VD No-Standard	MS_Factory_NS_VD_SET mNoStandSet;	// / factory VD Vif	MS_Factory_NS_VIF_SET mVifSet;	// / Miu lvds Setting	MS_FACTORY_SSC_SET mSscSet;	// / SRS Setting	KK_SRS_SET mSRSSet;	// / factory ADC auto-tune	boolean m_bADCAutoTune;	// / factory DTV overscan setting  primary key is 0 in DB	ST_MAPI_VIDEO_WINDOW_INFO m_DTVOverscanSet[][] = new ST_MAPI_VIDEO_WINDOW_INFO[MAX_DTV_Resolution_Info.E_DTV_MAX.ordinal()][MAPI_VIDEO_ARC_Type.E_AR_MAX.ordinal()];												   	// / factory HDMI overscan setting   primary key is 1 in DB	ST_MAPI_VIDEO_WINDOW_INFO m_HDMIOverscanSet[][] = new ST_MAPI_VIDEO_WINDOW_INFO[MAX_HDMI_Resolution_Info.E_HDMI_MAX.ordinal()][MAPI_VIDEO_ARC_Type.E_AR_MAX.ordinal()];													// DB	// / factory YPbPr overscan setting    primary key is 2	ST_MAPI_VIDEO_WINDOW_INFO m_YPbPrOverscanSet[][] = new ST_MAPI_VIDEO_WINDOW_INFO[MAX_YPbPr_Resolution_Info.E_YPbPr_MAX.ordinal()][MAPI_VIDEO_ARC_Type.E_AR_MAX.ordinal()];													 	// / factory VD overscan setting   primary key is 3 in DB	ST_MAPI_VIDEO_WINDOW_INFO m_VDOverscanSet[][] = new ST_MAPI_VIDEO_WINDOW_INFO[EN_VD_SIGNALTYPE.SIG_NUMS.ordinal()][MAPI_VIDEO_ARC_Type.E_AR_MAX.ordinal()];													// / factory PEQ setting	ST_FACTORY_PEQ_SETTING m_stPEQSet;	// / factory CI setting	ST_FACTORY_CI_SETTING m_stCISet;
		CustomerCfgMiscSetting customerCfgMiscSetting;
		// ------------------------------------------------------------	// Factory Data over	// ------------------------------------------------------------	private boolean initVarFactory()	{		m_stFactoryColorTemp = new T_MS_COLOR_TEMP();		m_stFactoryAdc = new MS_ADC_SETTING();		m_pastNLASet = new MS_NLA_SETTING();		m_stFactoryExt = new MS_FACTORY_EXTERN_SETTING();		mNoStandSet = new MS_Factory_NS_VD_SET();		mVifSet = new MS_Factory_NS_VIF_SET();		mSscSet = new MS_FACTORY_SSC_SET();		mSRSSet = new KK_SRS_SET();		m_bADCAutoTune = false;		int max1 = MAX_DTV_Resolution_Info.E_DTV_MAX.ordinal();		int max2 = MAPI_VIDEO_ARC_Type.E_AR_MAX.ordinal();		for (int i = 0; i < max1; i++)		{			for (int j = 0; j < max2; j++)			{				m_DTVOverscanSet[i][j] = new ST_MAPI_VIDEO_WINDOW_INFO();			}		}		max1 = MAX_HDMI_Resolution_Info.E_HDMI_MAX.ordinal();		max2 = MAPI_VIDEO_ARC_Type.E_AR_MAX.ordinal();		for (int i1 = 0; i1 < max1; i1++)		{			for (int j1 = 0; j1 < max2; j1++)			{				m_HDMIOverscanSet[i1][j1] = new ST_MAPI_VIDEO_WINDOW_INFO();			}		}		max1 = MAX_YPbPr_Resolution_Info.E_YPbPr_MAX.ordinal();		max2 = MAPI_VIDEO_ARC_Type.E_AR_MAX.ordinal();		for (int i2 = 0; i2 < max1; i2++)		{			for (int j2 = 0; j2 < max2; j2++)			{				m_YPbPrOverscanSet[i2][j2] = new ST_MAPI_VIDEO_WINDOW_INFO();			}		}		max1 = EN_VD_SIGNALTYPE.SIG_NUMS.ordinal();		max2 = MAPI_VIDEO_ARC_Type.E_AR_MAX.ordinal();		for (int i3 = 0; i3 < max1; i3++)		{			for (int j3 = 0; j3 < max2; j3++)			{				m_VDOverscanSet[i3][j3] = new ST_MAPI_VIDEO_WINDOW_INFO();			}		}		m_stPEQSet = new ST_FACTORY_PEQ_SETTING();		m_stCISet = new ST_FACTORY_CI_SETTING();		m_stFactoryColorTemp = new T_MS_COLOR_TEMP();		m_stFactoryColorTempEx = new T_MS_COLOR_TEMPEX();
		customerCfgMiscSetting = new CustomerCfgMiscSetting();		return true;	}	private boolean initVarSound()	{		soundpara = new MS_USER_SOUND_SETTING();		soundpara.u16CheckSum = 0xFFFF;		astSoundModeSetting = new SoundModeSeting[EN_SOUND_MODE.SOUND_MODE_NUM.ordinal()];		astSoundModeSetting[0] = new SoundModeSeting((short) 50, (short) 50, (short) 50, (short) 50, (short) 50,		        (short) 50, (short) 50);		astSoundModeSetting[1] = new SoundModeSeting((short) 70, (short) 40, (short) 70, (short) 60, (short) 50,		        (short) 50, (short) 40);		astSoundModeSetting[2] = new SoundModeSeting((short) 60, (short) 30, (short) 60, (short) 50, (short) 45,		        (short) 40, (short) 30);		astSoundModeSetting[3] = new SoundModeSeting((short) 40, (short) 80, (short) 40, (short) 45, (short) 50,		        (short) 60, (short) 80);		astSoundModeSetting[4] = new SoundModeSeting((short) 50, (short) 50, (short) 50, (short) 50, (short) 50,		        (short) 50, (short) 50);		astSoundModeSetting[5] = new SoundModeSeting((short) 50, (short) 50, (short) 50, (short) 50, (short) 50,		        (short) 50, (short) 50);		astSoundModeSetting[6] = new SoundModeSeting((short) 50, (short) 50, (short) 50, (short) 50, (short) 50,		        (short) 50, (short) 50);		return true;	}	private boolean initVarPicture()	{		short[][] initPicData =			{				    // / contrast brightness saturation sharpness hue				    { 50, 50, 50, 50, 50 },// / PICTURE_NORMAL				    { 60, 55, 60, 60, 50 },// / PICTURE_DYNAMIC				        { 40, 45, 45, 40, 50 },// / PICTURE_SOFT				        { 50, 50, 50, 50, 50 }, // / PICTURE_USER				        { 50, 50, 50, 50, 50 }, // / PICTURE_VIVID				        { 50, 50, 50, 50, 50 }, // / PICTURE_NATURAL				        { 50, 50, 50, 50, 50 } // / PICTURE_SPORTS			};		videopara = new T_MS_VIDEO();		videopara.CheckSum = 0xFFFF;		videopara.ePicture = EN_MS_PICTURE.PICTURE_NORMAL;		int count = EN_MS_PICTURE.PICTURE_NUMS.ordinal();		videopara.astPicture = new T_MS_PICTURE[count];		for (int i = 0; i < count; i++)		{			short contrast, brightness, color, sharpness, hue;			int j = 0;			contrast = initPicData[i][j++];			brightness = initPicData[i][j++];			color = initPicData[i][j++];			sharpness = initPicData[i][j++];			hue = initPicData[i][j];			videopara.astPicture[i] = new T_MS_PICTURE((short) 100, contrast, brightness, color, sharpness, hue,			        EN_MS_COLOR_TEMP.MS_COLOR_TEMP_NATURE, EN_MS_PIC_ADV.MS_MIDDLE, EN_MS_PIC_ADV.MS_MIDDLE,			        EN_MS_PIC_ADV.MS_MIDDLE, EN_MS_PIC_ADV.MS_MIDDLE);		}		count = EN_MS_COLOR_TEMP.MS_COLOR_TEMP_NUM.ordinal();		videopara.eNRMode = new T_MS_NR_MODE[count];		for (int i = 0; i < count; i++)		{			videopara.eNRMode[i] = new T_MS_NR_MODE(EN_MS_NR.MS_NR_MIDDLE, EN_MS_MPEG_NR.MS_MPEG_NR_MIDDLE);		}		videopara.g_astSubColor = new T_MS_SUB_COLOR(0xFFFF, (short) 0, (short) 0);		videopara.enARCType = MAPI_VIDEO_ARC_Type.E_AR_16x9;		videopara.fOutput_RES = EN_DISPLAY_RES_TYPE.DISPLAY_RES_FULL_HD;		videopara.tvsys = MAPI_VIDEO_OUT_VE_SYS.MAPI_VIDEO_OUT_VE_AUTO;		videopara.LastVideoStandardMode = MAPI_AVD_VideoStandardType.E_MAPI_VIDEOSTANDARD_AUTO;		videopara.LastAudioStandardMode = AUDIOMODE_TYPE_.E_AUDIOMODE_MONO_;		videopara.eDynamic_Contrast = EN_MS_Dynamic_Contrast.MS_Dynamic_Contrast_ON;		videopara.eFilm = EN_MS_FILM.MS_FILM_OFF;		videopara.ThreeDVideoMode = new ThreeD_Video_MODE(EN_ThreeD_Video.DB_ThreeD_Video_OFF,		        EN_ThreeD_Video_SELFADAPTIVE_DETECT.DB_ThreeD_Video_SELF_ADAPTIVE_DETECT_OFF,		        EN_ThreeD_Video_SELFADAPTIVE_LEVEL.DB_ThreeD_Video_SELF_ADAPTIVE_LOW,		        EN_ThreeD_Video_DISPLAYFORMAT.DB_ThreeD_Video_DISPLAYFORMAT_NONE,		        EN_ThreeD_Video_3DTO2D.DB_ThreeD_Video_3DTO2D_NONE,		        EN_ThreeD_Video_3DDEPTH.DB_ThreeD_Video_3DDEPTH_LEVEL_15,		        EN_ThreeD_Video_3DOFFSET.DB_ThreeD_Video_3DOFFSET_LEVEL_15,		        EN_ThreeD_Video_AUTOSTART.DB_ThreeD_Video_AUTOSTART_OFF,		        EN_ThreeD_Video_3DOUTPUTASPECT.DB_ThreeD_Video_3DOUTPUTASPECT_FULLSCREEN,		        EN_ThreeD_Video_LRVIEWSWITCH.DB_ThreeD_Video_LRVIEWSWITCH_NOTEXCHANGE);		videopara.stUserOverScanMode = new T_MS_OVERSCAN_SETTING_USER((short) 0x00, (short) 0x00, (short) 0x00,		        (short) 0x00);		videopara.eTvFormat = EN_DISPLAY_TVFORMAT.DISPLAY_TVFORMAT_16TO9HD;
		videopara.skinTone = SkinToneMode.SKIN_TONE_OFF;
		videopara.detailEnhance = false;
		videopara.DNR = EN_MS_NR.MS_NR_AUTO;
		colorParaEx = new T_MS_COLOR_TEMPEX_DATA(0x80, 0x80, 0x80, 0x80, 0x80, 0x80);		// adcPara = new MS_ADC_SETTING();		// colorPara = new T_MS_COLOR_TEMP();		return true;	}	private boolean InitSettingVar()	{		Log.e("TvService", "SettingServiceImpl InitVar!!");		stUsrData = new MS_USER_SYSTEM_SETTING();		// init data		stUsrData.checkSum = 0xFFFF;		stUsrData.fRunInstallationGuide = true;		stUsrData.fNoChannel = false;		stUsrData.bDisableSiAutoUpdate = false;		stUsrData.enInputSourceType = EnumInputSource.E_INPUT_SOURCE_ATV;		stUsrData.Country = MEMBER_COUNTRY.E_CHINA;		stUsrData.enCableOperators = EN_CABLE_OPERATORS.EN_CABLEOP_CDSMATV;		stUsrData.enSatellitePlatform = EN_SATELLITE_PLATFORM.EN_SATEPF_HDPLUS;		// stUsrData.enLanguage = MEMBER_LANGUAGE.E_LANGUAGE_CHINESE_S;		// stUsrData.stSubtitleSetting = new MS_USER_SUBTITLE_SETTING(		// MEMBER_LANGUAGE.E_LANGUAGE_ENGLISH,		// MEMBER_LANGUAGE.E_LANGUAGE_ENGLISH, false, false);		// stUsrData.stUserLocationSetting = new MS_USER_LOCATION_SETTING(		// 0x00, 0x00, 0x00);		stUsrData.u8OADTime = 0x00;		stUsrData.fOADScanAfterWakeup = 0x00;		stUsrData.fAutoVolume = 0x00;		stUsrData.fDcPowerOFFMode = 0x00;		stUsrData.DtvRoute = 0x00;		stUsrData.ScartOutRGB = 0x00;		stUsrData.U8Transparency = 0x00;		stUsrData.u32MenuTimeOut = 0x00000000;		stUsrData.AudioOnly = 0x00;		stUsrData.bEnableWDT = 0x00;		stUsrData.u8FavoriteRegion = 0x00;		stUsrData.u8Bandwidth = 0x00;		stUsrData.u8TimeShiftSizeType = 0x00;		stUsrData.fOadScan = 0x00;		stUsrData.bEnablePVRRecordAll = 0x00;		stUsrData.u8ColorRangeMode = 0x00;		stUsrData.u8HDMIAudioSource = 0x00;		stUsrData.bEnableAlwaysTimeshift = 0x00;		stUsrData.eSUPER = EN_MS_SUPER.MS_SUPER_OFF;		stUsrData.bUartBus = false;		stUsrData.m_AutoZoom = 0x00;		stUsrData.bOverScan = false;		stUsrData.m_u8BrazilVideoStandardType = 0x00;		stUsrData.m_u8SoftwareUpdateMode = 0x00;		stUsrData.u32OSD_Active_Time = 0x00000000;		stUsrData.m_MessageBoxExist = false;		stUsrData.u16LastOADVersion = 0x0000;		stUsrData.bEnableAutoChannelUpdate = false;		stUsrData.u8OsdDuration = 0x00;		stUsrData.eChSwMode = EN_MS_CHANNEL_SWITCH_MODE.MS_CHANNEL_SWM_BLACKSCREEN;		stUsrData.eOffDetMode = EN_MS_OFFLINE_DET_MODE.MS_OFFLINE_DET_OFF;		stUsrData.bBlueScreen = false;		stUsrData.ePWR_Music = EN_MS_POWERON_MUSIC.MS_POWERON_MUSIC_DEFAULT;		stUsrData.ePWR_Logo = EN_MS_POWERON_LOGO.MS_POWERON_LOGO_DEFAULT;		stUsrData.enLanguage = MEMBER_LANGUAGE.E_LANGUAGE_ENGLISH;
		stUsrData.standbyNoOperation = 0;
		stUsrData.standbyNoSignal = false;
		stUsrData.screenSaveMode = false;
		stUsrData.smartEnergySaving = SmartEnergySavingMode.MODE_OFF;
		stUsrData.colorWheelMode = ColorWheelMode.MODE_OFF;
				stUsrColorTemp = new T_MS_COLOR_TEMP_DATA((short)0x80, (short)0x80, (short)0x80, (short)0x80, (short)0x80, (short)0x80);		stUsrColorTempEx = new  T_MS_COLOR_TEMPEX_DATA[EnumInputSource.E_INPUT_SOURCE_NUM.ordinal()];		for(int i = 0;i < EnumInputSource.E_INPUT_SOURCE_NUM.ordinal();i++){			stUsrColorTempEx[i] = new T_MS_COLOR_TEMPEX_DATA((short)0x80, (short)0x80, (short)0x80, (short)0x80, (short)0x80, (short)0x80);		}				stSubtitleSet = new MS_USER_SUBTITLE_SETTING(		        MEMBER_LANGUAGE.E_LANGUAGE_ENGLISH,		        MEMBER_LANGUAGE.E_LANGUAGE_ENGLISH, false,		        false);				stUserLocationSetting = new MS_USER_LOCATION_SETTING(				0x00, 0x00, 0x00);		//		try//		{//			int value;//			value = dm.getOsdLanguage();//			if (value == EnumLanguage.E_CHINESE.ordinal())//			{//				stUsrData.enLanguage = MEMBER_LANGUAGE.E_LANGUAGE_CHINESE_S;//				stUsrData.stSubtitleSetting = new MS_USER_SUBTITLE_SETTING(//				        MEMBER_LANGUAGE.E_LANGUAGE_CHINESE_S,//				        MEMBER_LANGUAGE.E_LANGUAGE_CHINESE_S, false,//				        false);//			}//			else if (value == EnumLanguage.E_ACHINESE.ordinal())//			{//				stUsrData.enLanguage = MEMBER_LANGUAGE.E_LANGUAGE_CHINESE_T;//				stUsrData.stSubtitleSetting = new MS_USER_SUBTITLE_SETTING(//				        MEMBER_LANGUAGE.E_LANGUAGE_CHINESE_T,//				        MEMBER_LANGUAGE.E_LANGUAGE_CHINESE_T, false,//				        false);//			}//			else//			{//				stUsrData.enLanguage = MEMBER_LANGUAGE.E_LANGUAGE_ENGLISH;//				stUsrData.stSubtitleSetting = new MS_USER_SUBTITLE_SETTING(//				        MEMBER_LANGUAGE.E_LANGUAGE_ENGLISH,//				        MEMBER_LANGUAGE.E_LANGUAGE_ENGLISH, false,//				        false);//				if (value != EnumLanguage.E_ENGLISH.ordinal())//				{//					// Other it's not support//					dm.setOsdLanguage(EnumLanguage.E_ENGLISH);//				}//			}//		}//		catch (TvCommonException e)//		{//			// TODO Auto-generated catch block//			e.printStackTrace();//		}		return true;	}	public int queryCurInputSrc(){		int value = 0;		/*Cursor cursor = userSettingDB.query("tbl_SystemSetting", null, null, null, null, null, null);*/
		
		Cursor cursor = cr.query(Uri.parse(userSettingSchema+"/systemsetting"), null, null, null, null);		if (cursor.moveToFirst())		{				value = cursor.getInt(cursor.getColumnIndex("enInputSourceType"));		}		cursor.close();		return value;	}		public void updateCurInputSrc(int inputSrc){		ContentValues vals = new ContentValues();		vals.put("enInputSourceType", inputSrc);		
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
	private boolean initCECVar()	{		stCECPara = new MS_CEC_SETTING((int) 0xFFFF, (short) 0x00, (short) 0x00, (short) 0x00, (short) 0x00);		return true;	}	@Override	public T_MS_VIDEO getVideo()	{		com.printfE("TvService", "DataBaseServiceImpl getVideo!!");		return this.videopara;	}	@Override	public boolean setVideo(T_MS_VIDEO video)	{		this.videopara = video;		return true;	}	@Override	public T_MS_COLOR_TEMPEX_DATA getVideoTempEx()	{		return this.colorParaEx;	}	@Override	public boolean setVideoTempEx(T_MS_COLOR_TEMPEX_DATA videotmp)	{		this.colorParaEx = videotmp;		return true;	}	@Override	public T_MS_COLOR_TEMP getVideoTemp()	{		return this.m_stFactoryColorTemp;	}	@Override	public boolean setVideoTemp(T_MS_COLOR_TEMP videotmp)	{		this.m_stFactoryColorTemp = videotmp;		return true;	}	@Override	public MS_ADC_SETTING getAdcSetting()	{		return this.m_stFactoryAdc;	}	@Override	public boolean setAdcSetting(MS_ADC_SETTING adcSet)	{		this.m_stFactoryAdc = adcSet;		return true;	}	@Override	public MS_USER_SYSTEM_SETTING getUsrData()	{		return this.stUsrData;	}	@Override	public boolean setUsrData(MS_USER_SYSTEM_SETTING stData)	{		stUsrData = stData;		return true;	}	@Override	public MS_USER_SUBTITLE_SETTING getSubtitleSet()	{		return stSubtitleSet;	}	@Override	public boolean setSubtitleSet(MS_USER_SUBTITLE_SETTING stData)	{		stSubtitleSet = stData;		return true;	}	@Override	public MS_USER_LOCATION_SETTING getLocationSet()	{		return stUserLocationSetting;	}	@Override	public boolean setLocationSet(MS_USER_LOCATION_SETTING stData)	{		stUserLocationSetting = stData;		return true;	}	@Override	public MS_CEC_SETTING getCECVar()	{		return this.stCECPara;	}	@Override	public boolean setCECVar(MS_CEC_SETTING stCec)	{		stCECPara = stCec;		return true;	}	@Override	public MS_USER_SOUND_SETTING getSound()	{		return this.soundpara;	}	@Override	public boolean setSound(MS_USER_SOUND_SETTING stMode)	{		this.soundpara = stMode;		return false;	}	@Override	public SoundModeSeting getSoundMode(EN_SOUND_MODE eMode)	{		int index;		index = eMode.ordinal();		return this.astSoundModeSetting[index];	}	@Override	public boolean setSoundMode(EN_SOUND_MODE eMode, SoundModeSeting stSoundMode)	{		int index;		index = eMode.ordinal();		this.astSoundModeSetting[index] = stSoundMode;		return true;	}	@Override
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

	@Override	public boolean restoreUsrDB(EN_SYSTEM_FACTORY_DB_COMMAND eType)	{		switch (eType)		{			case E_FACTORY_COLOR_TEMP_SET:				break;			case E_FACTORY_VIDEO_ADC_SET:				break;			case E_FACTORY_RESTORE_DEFAULT:				com.printfE("TvService", "restoreUsrDB : E_FACTORY_RESTORE_DEFAULT!!" + videopara);				initVarPicture();				InitSettingVar();				initCECVar();				initVarSound();				com.printfE("TvService", "restoreUsrDB : E_FACTORY_RESTORE_DEFAULT!!" + videopara);				break;			case E_USER_RESTORE_DEFAULT:				break;			default:				break;		}		return true;	}	@Override	public MS_FACTORY_EXTERN_SETTING getFactoryExt()	{		return m_stFactoryExt;	}	@Override	public boolean setFactoryExt(MS_FACTORY_EXTERN_SETTING stFactory)	{		m_stFactoryExt = stFactory;		return true;	}	@Override	public MS_Factory_NS_VD_SET getNoStandSet()	{		return mNoStandSet;	}	@Override	public boolean setNoStandSet(MS_Factory_NS_VD_SET stNonStand)	{		mNoStandSet = stNonStand;		return true;	}	@Override	public MS_Factory_NS_VIF_SET getNoStandVifSet()	{		return mVifSet;	}	@Override	public boolean setNoStandVifSet(MS_Factory_NS_VIF_SET stVif)	{		mVifSet = stVif;		return true;	}	@Override	public MS_FACTORY_SSC_SET getSscSet()	{		return mSscSet;	}	@Override	public boolean setSscSet(MS_FACTORY_SSC_SET stLvdsSsc)	{		mSscSet = stLvdsSsc;		return true;	}

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
