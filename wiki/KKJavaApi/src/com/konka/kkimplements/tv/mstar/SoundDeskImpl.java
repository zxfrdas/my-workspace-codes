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

import android.content.Context;
import android.util.Log;

import com.konka.kkinterface.tv.CommonDesk;
import com.konka.kkinterface.tv.DataBaseDesk.EN_SOUND_MODE;
import com.konka.kkinterface.tv.DataBaseDesk.EN_SPDIF_MODE;
import com.konka.kkinterface.tv.DataBaseDesk.EN_SRS_SET_TYPE;
import com.konka.kkinterface.tv.DataBaseDesk.EN_SURROUND_MODE;
import com.konka.kkinterface.tv.DataBaseDesk.EnumSwitchOnOff;
import com.konka.kkinterface.tv.DataBaseDesk.HdmiAudioSource;
import com.konka.kkinterface.tv.DataBaseDesk.KK_SRS_SET;
import com.konka.kkinterface.tv.DataBaseDesk.MS_USER_SOUND_SETTING;
import com.konka.kkinterface.tv.SoundDesk;
import com.tvos.common.AudioManager;
import com.tvos.common.AudioManager.AudioOutParameter;
import com.tvos.common.AudioManager.EnumAdvancedSoundParameterType;
import com.tvos.common.AudioManager.EnumAdvancedSoundSubProcessType;
import com.tvos.common.AudioManager.EnumAdvancedSoundType;
import com.tvos.common.AudioManager.EnumAudioInputLevelSourceType;
import com.tvos.common.AudioManager.EnumAudioVolumeSourceType;
import com.tvos.common.AudioManager.EnumKtvInfoType;
import com.tvos.common.AudioManager.EnumSoundEffectType;
import com.tvos.common.AudioManager.EnumSoundSetParamType;
import com.tvos.common.AudioManager.EnumSpdifType;
import com.tvos.common.TvManager;
import com.tvos.common.exception.TvCommonException;
import com.tvos.common.vo.AdvancedSoundParameter;
import com.tvos.common.vo.DtvSoundEffect;
import com.tvos.common.vo.TvOsType;
import com.tvos.common.vo.TvOsType.EnumInputSource;

public class SoundDeskImpl extends BaseDeskImpl implements SoundDesk
{
	private DataBaseDeskImpl databaseMgr = null;
	private MS_USER_SOUND_SETTING userSoundSetting = null;
	private static SoundDeskImpl soundMgrImpl = null;
	private CommonDesk com = null;
	// private AudioManager am = AudioManager.getInstance();
	// private DtvSoundEffect dtvSoundEff = null;
	AudioManager am = TvManager.getAudioManager();
	private Context context;
	
	private SoundDeskImpl(Context context)
	{
		this.context = context;
		com = CommonDeskImpl.getInstance(context);
		com.printfI("TvService", "SoundManagerImpl constructor!!");
		databaseMgr = DataBaseDeskImpl.getDataBaseMgrInstance(context);
		userSoundSetting = databaseMgr.getSound();
		// dtvSoundEff = new DtvSoundEffect();
	}

	public static SoundDeskImpl getSoundMgrInstance(Context context)
	{
		if (soundMgrImpl == null)
			soundMgrImpl = new SoundDeskImpl(context);
		return soundMgrImpl;
	}

	@Override
	public boolean setSoundMode(EN_SOUND_MODE SoundMode, boolean bWriteDB) {
		// TODO Auto-generated method stub
		DtvSoundEffect dtvSoundEff = null;
//		ST_FACTORY_PEQ_SETTING peqParam = null;
		
		databaseMgr.querySoundSetting();
		userSoundSetting = databaseMgr.getSound();

		dtvSoundEff = new DtvSoundEffect();
		userSoundSetting.SoundMode = SoundMode;
		dtvSoundEff.eqBandNumber = (short) EN_SOUND_MODE.SOUND_MODE_NUM.ordinal();
		com.printfI("TvSoundServiceImpl", "SoundMode is" + userSoundSetting.SoundMode);
		com.printfI("TvSoundServiceImpl", "Value:" + databaseMgr.getSoundMode(SoundMode).EqBand1 + ":"
		        + databaseMgr.getSoundMode(SoundMode).EqBand2 + ":" + databaseMgr.getSoundMode(SoundMode).EqBand3 + ":"
		        + databaseMgr.getSoundMode(SoundMode).EqBand4 + ":" + databaseMgr.getSoundMode(SoundMode).EqBand5);
		try
		{
			dtvSoundEff.soundParameterEqs[0].eqLevel = (int) (databaseMgr.getSoundMode(SoundMode).EqBand1);
			dtvSoundEff.soundParameterEqs[1].eqLevel = (int) (databaseMgr.getSoundMode(SoundMode).EqBand2);
			dtvSoundEff.soundParameterEqs[2].eqLevel = (int) (databaseMgr.getSoundMode(SoundMode).EqBand3);
			dtvSoundEff.soundParameterEqs[3].eqLevel = (int) (databaseMgr.getSoundMode(SoundMode).EqBand4);
			dtvSoundEff.soundParameterEqs[4].eqLevel = (int) (databaseMgr.getSoundMode(SoundMode).EqBand5);
			dtvSoundEff.eqBandNumber = 5;
			am.setBasicSoundEffect(EnumSoundEffectType.E_EQ, dtvSoundEff);
//			//load peq setting :
//			peqParam = databaseMgr.queryPEQAdjusts();
//			for (int i = 0; i < peqParam.stPEQParam.length; i++)
//			{
//				dtvSoundEff.soundParameterPeqs[i].peqGc = peqParam.stPEQParam[i].Foh * 100 + (peqParam.stPEQParam[i].Fol);
//				dtvSoundEff.soundParameterPeqs[i].peqQvalue = peqParam.stPEQParam[i].QValue;
//				
//				//here, peq0 must use the peq gain got from tbl_SoundModeSetting,
//				//EqBand6 is used to store peq0 gain value:
//				if ( i == 0)
//				{
//					dtvSoundEff.soundParameterPeqs[i].peqGain = (databaseMgr.getSoundMode(SoundMode).EqBand6);
//				}
//				else
//				{
//					dtvSoundEff.soundParameterPeqs[i].peqGain = peqParam.stPEQParam[i].Gain;
//				}
//					
////				System.out.println("==terry i:"+i+" peq,set soundmode Foh:"+peqParam.stPEQParam[i].Foh+"==");
////				System.out.println("==terry i:"+i+" peq,set soundmode Fol:"+peqParam.stPEQParam[i].Fol+"==");
////				System.out.println("==terry i:"+i+" peq,set soundmode EqBand6/Gain:"+databaseMgr.getSoundMode(SoundMode).EqBand6+"==");
////				System.out.println("==terry i:"+i+" peq,set soundmode QValue:"+peqParam.stPEQParam[i].QValue+"==");
////				
////				System.out.println("==terry i:"+i+" peq,set soundmode Gc:"+dtvSoundEff.soundParameterPeqs[i].peqGc+"==");
////				System.out.println("==terry i:"+i+" peq,set soundmode Q:"+dtvSoundEff.soundParameterPeqs[i].peqQvalue+"==");
////				System.out.println("==terry i:"+i+" peq,set soundmode Gain:"+dtvSoundEff.soundParameterPeqs[i].peqGain+"==");
//			}
//			dtvSoundEff.peqBandNumber = (short) peqParam.stPEQParam.length;
////			System.out.println("==terry peq,set soundmode peqBandNumber:"+dtvSoundEff.peqBandNumber+"==");
//
//			am.setBasicSoundEffect(EnumSoundEffectType.E_PEQ, dtvSoundEff);
		} catch (TvCommonException e) {
			com.printfE("TvSoundServiceImpl", "Set Sound Mode Exception");
			e.printStackTrace();
		}
		if (bWriteDB) {
			return databaseMgr.setSoundMode(userSoundSetting.SoundMode);
		} else {
			return true;
		}
	}

	@Override
	public boolean setSoundMode(EN_SOUND_MODE SoundMode)
	{
		return setSoundMode(SoundMode,true);
	}

	@Override
	public EN_SOUND_MODE getSoundMode()
	{
		userSoundSetting.SoundMode = EN_SOUND_MODE.values()[
		    databaseMgr.querySoundMode()];
		return userSoundSetting.SoundMode;
	}

	@Override
	public boolean setBass(short BassValue)
	{
		databaseMgr.querySoundSetting();
		userSoundSetting = databaseMgr.getSound();
		
		DtvSoundEffect dtvSoundEff = null;
		dtvSoundEff = new DtvSoundEffect();
		databaseMgr.getSoundMode(userSoundSetting.SoundMode).Bass = BassValue;
		com.printfI("TvSoundServiceImpl", "Bass is" + databaseMgr.getSoundMode(userSoundSetting.SoundMode).Bass);
		try
		{
			com.printfI("TvSoundServiceImpl", "Bass is$$$$$$$$" + BassValue);
			databaseMgr.getSoundMode(userSoundSetting.SoundMode).EqBand1 = BassValue;
			dtvSoundEff.soundParameterEqs[0].eqLevel = (int) BassValue;
			dtvSoundEff.soundParameterEqs[1].eqLevel = (int) (databaseMgr.getSoundMode(userSoundSetting.SoundMode).EqBand2);
			dtvSoundEff.soundParameterEqs[2].eqLevel = (int) (databaseMgr.getSoundMode(userSoundSetting.SoundMode).EqBand3);
			dtvSoundEff.soundParameterEqs[3].eqLevel = (int) (databaseMgr.getSoundMode(userSoundSetting.SoundMode).EqBand4);
			dtvSoundEff.soundParameterEqs[4].eqLevel = (int) (databaseMgr.getSoundMode(userSoundSetting.SoundMode).EqBand5);
			dtvSoundEff.eqBandNumber = 5;
			am.setBasicSoundEffect(EnumSoundEffectType.E_EQ, dtvSoundEff);
		}
		catch (TvCommonException e)
		{
			com.printfE("TvSoundServiceImpl", "Set Sound Mode Exception");
			e.printStackTrace();
		}
		databaseMgr.updateSoundModeSetting(databaseMgr.getSoundMode(userSoundSetting.SoundMode), userSoundSetting.SoundMode.ordinal());
		return true;
	}

	@Override
	public short getBass()
	{
		return databaseMgr.getSoundMode(userSoundSetting.SoundMode).Bass;
	}

	@Override
	public boolean setTreble(short TrebleValue)
	{
		databaseMgr.querySoundSetting();
		userSoundSetting = databaseMgr.getSound();
		
		DtvSoundEffect dtvSoundEff = null;
		dtvSoundEff = new DtvSoundEffect();
		databaseMgr.getSoundMode(userSoundSetting.SoundMode).Treble = TrebleValue;
		com.printfI("TvSoundServiceImpl", "Treble is" + databaseMgr.getSoundMode(userSoundSetting.SoundMode).Treble);
		try
		{
			dtvSoundEff.soundParameterEqs[0].eqLevel = (int) (databaseMgr.getSoundMode(userSoundSetting.SoundMode).EqBand1);
			dtvSoundEff.soundParameterEqs[1].eqLevel = (int) (databaseMgr.getSoundMode(userSoundSetting.SoundMode).EqBand2);
			dtvSoundEff.soundParameterEqs[2].eqLevel = (int) (databaseMgr.getSoundMode(userSoundSetting.SoundMode).EqBand3);
			dtvSoundEff.soundParameterEqs[3].eqLevel = (int) (databaseMgr.getSoundMode(userSoundSetting.SoundMode).EqBand4);
			databaseMgr.getSoundMode(userSoundSetting.SoundMode).EqBand5 = TrebleValue;
			dtvSoundEff.soundParameterEqs[4].eqLevel = (int) TrebleValue;
			dtvSoundEff.eqBandNumber = 5;
			am.setBasicSoundEffect(EnumSoundEffectType.E_EQ, dtvSoundEff);
		}
		catch (TvCommonException e)
		{
			com.printfE("TvSoundServiceImpl", "Set Sound Mode Exception");
			e.printStackTrace();
		}
		databaseMgr.updateSoundModeSetting(databaseMgr.getSoundMode(userSoundSetting.SoundMode), userSoundSetting.SoundMode.ordinal());
		return true;
	}

	@Override
	public short getTreble()
	{
		return databaseMgr.getSoundMode(userSoundSetting.SoundMode).Treble;
	}

	@Override
	public boolean setBalance(short BalanceValue)
	{
		databaseMgr.querySoundSetting();
		userSoundSetting = databaseMgr.getSound();
		
		DtvSoundEffect dtvSoundEff = null;
		dtvSoundEff = new DtvSoundEffect();
		userSoundSetting.Balance = BalanceValue;
		com.printfI("TvSoundServiceImpl", "Set Balance is" + userSoundSetting.Balance);
		try
		{
			dtvSoundEff.balance = BalanceValue;
			am.setBasicSoundEffect(EnumSoundEffectType.E_BALANCE, dtvSoundEff);
		}
		catch (TvCommonException e)
		{
			com.printfE("TvSoundServiceImpl", "Set Balance Exception");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		databaseMgr.updateSoundSetting(userSoundSetting);
		return true;
	}

	@Override
	public short getBalance()
	{
		com.printfI("TvSoundServiceImpl", "Get Balance is" + userSoundSetting.Balance);
		return userSoundSetting.Balance;
	}

	@Override
	public boolean setAVCMode(boolean AvcEnable)
	{
		databaseMgr.querySoundSetting();
		userSoundSetting = databaseMgr.getSound();
		
		userSoundSetting.bEnableAVC = AvcEnable;
		com.printfI("TvSoundServiceImpl", "bEnableAVC is"
		        + userSoundSetting.bEnableAVC);
		databaseMgr.updateSoundSetting(userSoundSetting);
		try
		{
			am.enableBasicSoundEffect(EnumSoundEffectType.E_AVC, AvcEnable);
		}
		catch (TvCommonException e)
		{
			com.printfE("TvSoundServiceImpl", "Set Balance Exception");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean getAVCMode()
	{
		return userSoundSetting.bEnableAVC;
	}

	@Override
	public boolean setSurroundMode(EN_SURROUND_MODE SurroundMode)
	{
		databaseMgr.querySoundSetting();
		userSoundSetting = databaseMgr.getSound();
		
		boolean bEnable = false;
		userSoundSetting.SurroundMode = SurroundMode;
		com.printfI("TvSoundServiceImpl", "SurroundMode is" + userSoundSetting.SurroundMode);
		if (SurroundMode == EN_SURROUND_MODE.E_SURROUND_MODE_OFF)
		{
			bEnable = false;
		}
		else
		{
			bEnable = true;
		}
		try
		{
			am.enableBasicSoundEffect(EnumSoundEffectType.E_Surround, bEnable);
		}
		catch (TvCommonException e)
		{
			com.printfE("TvSoundServiceImpl", "Set Balance Exception");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		databaseMgr.updateSoundSetting(userSoundSetting);
		return true;
	}

	@Override
	public EN_SURROUND_MODE getSurroundMode()
	{
		return userSoundSetting.SurroundMode;
	}

	@Override
	public boolean setSpdifOutMode(EN_SPDIF_MODE SpdifMode)
	{
		databaseMgr.querySoundSetting();
		userSoundSetting = databaseMgr.getSound();
		
		userSoundSetting.spdifMode = SpdifMode;
		EnumSpdifType mode;
		com.printfI("TvSoundServiceImpl", "spdifMode is"
		        + userSoundSetting.spdifMode);
		
		AudioOutParameter aoutp= am.new AudioOutParameter();
        aoutp.setspdifOutModeInUi(
        		EnumSpdifType.values()[SpdifMode.ordinal()]);  
        
        databaseMgr.updateSoundSetting(userSoundSetting);
        if(SpdifMode == EN_SPDIF_MODE.PDIF_MODE_PCM)
        {
        	mode = EnumSpdifType.E_AUD_SPDIF_PCM_;
        }
        else if(SpdifMode == EN_SPDIF_MODE.PDIF_MODE_RAW)
        {
        	mode = EnumSpdifType.E_AUD_SPDIF_NONPCM_;
        }
        else //if(SpdifMode == EN_SPDIF_MODE.PDIF_MODE_OFF)
        {
        	mode = EnumSpdifType.E_AUD_SPDIF_OFF_;
        }
  
        try
		{
			am.setDigitalOut(mode);
		}
		catch (TvCommonException e)
		{
			com.printfE("TvSoundServiceImpl", "Set Volume Exception");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public EN_SPDIF_MODE getSpdifOutMode()
	{
		return userSoundSetting.spdifMode;
	}

	// public boolean SetMusicOnlyMode();
	// public boolean GetMusicOnlyMode();
	@Override
	public boolean setEqBand120(short eqValue)
	{
		databaseMgr.querySoundSetting();
		userSoundSetting = databaseMgr.getSound();
		
		databaseMgr.getSoundMode(userSoundSetting.SoundMode).EqBand1 = eqValue;
		com.printfI("TvSoundServiceImpl", "EqBand1 is" + databaseMgr.getSoundMode(userSoundSetting.SoundMode).EqBand1);
		setSoundMode(userSoundSetting.SoundMode);
		databaseMgr.updateSoundModeSetting(databaseMgr.getSoundMode(userSoundSetting.SoundMode), userSoundSetting.SoundMode.ordinal());
		return true;
	}

	@Override
	public short getEqBand120()
	{
		return databaseMgr.getSoundMode(userSoundSetting.SoundMode).EqBand1;
	}

	@Override
	public boolean setEqBand500(short eqValue)
	{
		databaseMgr.querySoundSetting();
		userSoundSetting = databaseMgr.getSound();
		
		databaseMgr.getSoundMode(userSoundSetting.SoundMode).EqBand2 = eqValue;
		com.printfI("TvSoundServiceImpl", "EqBand2 is" + databaseMgr.getSoundMode(userSoundSetting.SoundMode).EqBand2);
		setSoundMode(userSoundSetting.SoundMode);
		databaseMgr.updateSoundModeSetting(databaseMgr.getSoundMode(userSoundSetting.SoundMode), userSoundSetting.SoundMode.ordinal());
		return true;
	}

	@Override
	public short getEqBand500()
	{
		return databaseMgr.getSoundMode(userSoundSetting.SoundMode).EqBand2;
	}

	@Override
	public boolean setEqBand1500(short eqValue)
	{
		databaseMgr.querySoundSetting();
		userSoundSetting = databaseMgr.getSound();
		
		databaseMgr.getSoundMode(userSoundSetting.SoundMode).EqBand3 = eqValue;
		com.printfI("TvSoundServiceImpl", "EqBand3 is" + databaseMgr.getSoundMode(userSoundSetting.SoundMode).EqBand3);
		setSoundMode(userSoundSetting.SoundMode);
		databaseMgr.updateSoundModeSetting(databaseMgr.getSoundMode(userSoundSetting.SoundMode), userSoundSetting.SoundMode.ordinal());
		return true;
	}

	@Override
	public short getEqBand1500()
	{
		return databaseMgr.getSoundMode(userSoundSetting.SoundMode).EqBand3;
	}

	@Override
	public boolean setEqBand5k(short eqValue)
	{
		databaseMgr.querySoundSetting();
		userSoundSetting = databaseMgr.getSound();
		
		databaseMgr.getSoundMode(userSoundSetting.SoundMode).EqBand4 = eqValue;
		com.printfI("TvSoundServiceImpl", "EqBand4 is" + databaseMgr.getSoundMode(userSoundSetting.SoundMode).EqBand4);
		setSoundMode(userSoundSetting.SoundMode);
		databaseMgr.updateSoundModeSetting(databaseMgr.getSoundMode(userSoundSetting.SoundMode), userSoundSetting.SoundMode.ordinal());
		return true;
	}

	@Override
	public short getEqBand5k()
	{
		return databaseMgr.getSoundMode(userSoundSetting.SoundMode).EqBand4;
	}

	@Override
	public boolean setEqBand10k(short eqValue)
	{
		databaseMgr.querySoundSetting();
		userSoundSetting = databaseMgr.getSound();
		
		databaseMgr.getSoundMode(userSoundSetting.SoundMode).EqBand5 = eqValue;
		com.printfI("TvSoundServiceImpl", "EqBand5 is" + databaseMgr.getSoundMode(userSoundSetting.SoundMode).EqBand5);
		setSoundMode(userSoundSetting.SoundMode);
		databaseMgr.updateSoundModeSetting(databaseMgr.getSoundMode(userSoundSetting.SoundMode), userSoundSetting.SoundMode.ordinal());
		return true;
	}

	@Override
	public short getEqBand10k()
	{
		return databaseMgr.getSoundMode(userSoundSetting.SoundMode).EqBand5;
	}

	@Override
	public boolean setVolume(short volume)
	{
		databaseMgr.querySoundSetting();
		userSoundSetting = databaseMgr.getSound();
		  
		userSoundSetting.Volume = volume;
		com.printfI("TvSoundServiceImpl", "setVolume is" + userSoundSetting.Volume);
		try
		{
			am.setAudioVolume(EnumAudioVolumeSourceType.E_VOL_SOURCE_SPEAKER_OUT, (byte) volume);
			am.setAudioVolume(EnumAudioVolumeSourceType. E_VOL_SOURCE_HP_OUT, (byte) volume);
		}
		catch (TvCommonException e)
		{
			com.printfE("TvSoundServiceImpl", "Set Volume Exception");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		databaseMgr.updateSoundSetting(userSoundSetting);
		return true;
	}

	@Override
	public short getVolume()
	{
		Log.d("TvSoundServiceImpl", "getVolume is" + userSoundSetting.Volume);
		return userSoundSetting.Volume;
	}
	
	public boolean setAudioInputSource(TvOsType.EnumInputSource enAudioInputSource)
	{
		Log.d("setAudioInputSource", "source is" + enAudioInputSource);
		try {
			am.setInputSource(enAudioInputSource);
		} catch (TvCommonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean setKaraVolume(short volume)
	{
		System.out.println("setKaraVolume SoundDeskImpl before try");
		try
		{
			System.out.println("setKaraVolume SoundDeskImpl accvolume = " + volume);
//			am.setAudioVolume(EnumAudioVolumeSourceType.E_VOL_SOURCE_SPEAKER_OUT, (byte) volume);
			am.setInputLevel(EnumAudioInputLevelSourceType.E_VOL_SOURCE_PREMIXER_KTV_MP3_IN, volume);
		}
		catch (TvCommonException e)
		{
			System.out.println("setKaraVolume SoundDeskImpl Error");
			com.printfE("TvSoundServiceImpl", "Set Volume Exception");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public boolean setKaraSystemVolume(short volume)
	{
		System.out.println("setKaraSystemVolume SoundDeskImpl before try");
		try
		{
			System.out.println("setKaraSystemVolume SoundDeskImpl accvolume = " + volume);
			am.setAudioVolume(EnumAudioVolumeSourceType.E_VOL_SOURCE_SPEAKER_OUT, (byte) volume);
		}
		catch (TvCommonException e)
		{
			System.out.println("setKaraSystemVolume SoundDeskImpl Error");
			com.printfE("TvSoundServiceImpl", "Set Volume Exception");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	public boolean setKaraMicVolume(short volume)
	{
		System.out.println("setKaraMicVolume SoundDeskImpl before try");
		try
		{
			System.out.println("setKaraVolume SoundDeskImpl micvolume = " + volume);
			am.setInputLevel(EnumAudioInputLevelSourceType.E_VOL_SOURCE_PREMIXER_KTV_MIC_IN, volume);
		}
		catch (TvCommonException e)
		{
			System.out.println("setKaraVolume SoundDeskImpl Error");
			com.printfE("TvSoundServiceImpl", "Set Volume Exception");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public boolean setKaraAudioTrack(int mode){		
		System.out.println("setKaraAudioMode SoundDeskImpl");
//		am.setKtvSoundTrack(mode);
		try {
			am.setKtvSoundInfo(EnumKtvInfoType.E_KTV_SETINFO_BG_MUSIC_SOUNDMODE,mode,0);
		} catch (TvCommonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean setSoundSpeakerDelay(int delay)
	{
		am.setSoundSpeakerDelay(delay);
		return true;
	}

	public int getSoundSpeakerDelay()
	{
		int param = 0;
		am.getSoundParameter(EnumSoundSetParamType.E_SOUND_SET_PARAM_AUDIODELAY_, param);
		return param;
	}
/*	
	private int ScaleValueX2Y(int u16X, int u16Y, int u16XMinValue, int u16XMaxValue)
	{
		u16XMaxValue = u16XMaxValue - u16XMinValue;
		u16X = u16X - u16XMinValue;

		u16X = ((int) u16X * u16Y + (u16XMaxValue + 2) / 2) / u16XMaxValue;
		if(u16X > u16Y)
			u16X = u16Y;

		return u16X;
	}

	private int Echo1_VolumeTable[] =
		{ 
			//   1       2       3       4       5       6       7       8       9       10 
			0x0400, //  00 
			0x0400, 0x0400, 0x0400, 0x0400, 0x0400, 0x0400, 0x0400, 0x0400, 0x0400, 0x0400, //  10 
			0x0400, 0x0400, 0x0400, 0x0400, 0x0400, 0x0400, 0x0400, 0x0400, 0x0400, 0x0400,  //  20 
			0x0400, 0x0400, 0x0400, 0x0400, 0x0400, 0x0400, 0x0400, 0x0400, 0x0400, 0x0400,  //  30 
			0x0400, 0x0400, 0x0400, 0x0400, 0x0400, 0x0400, 0x0400, 0x0400, 0x0400, 0x0400, //  40 
			0x0400, 0x0400, 0x0400, 0x0400, 0x0400, 0x0400, 0x0400, 0x0400, 0x0400, 0x0400, //  50 
			0x0400, 0x0400, 0x0400, 0x0400, 0x0400, 0x0400, 0x0400, 0x0400, 0x0400, 0x0400, //  60 
			0x0800, 0x0800, 0x0800, 0x0800, 0x0800, 0x0800, 0x0800, 0x0800, 0x0800, 0x0800,   //  70 
			0x0800, 0x0800, 0x0800, 0x0800, 0x0800, 0x0800, 0x0800, 0x0800, 0x0800, 0x0800,//  80 
			0x0800, 0x0800, 0x0800, 0x0800, 0x0800, 0x0800, 0x0800, 0x0800, 0x0800, 0x0800,  //  90 
			0x0800, 0x0800, 0x0800, 0x0800, 0x0800, 0x0800, 0x0800, 0x0800, 0x0800, 0x0800,//  100 
		};

	private int Echo2_VolumeTable[] =
		{ 
			//   1       2       3       4       5       6       7       8       9       10 
			0x0C00, //  00 
			0x0C00, 0x0C00, 0x0C00, 0x0C00, 0x0C00, 0x0C00, 0x0C00, 0x0C00, 0x0C00, 0x0C00, //  10 
			0x0C00, 0x0C00, 0x0C00, 0x0C00, 0x0C00, 0x0C00, 0x0C00, 0x0C00, 0x0C00, 0x0C00,  //  20 
			0x0BE0, 0x0BB0, 0x0BA0, 0x0B80, 0x0B50, 0x0B40, 0x0B30, 0x0B20, 0x0B10, 0x0B00,//  30 
			0x0B00, 0x0B00, 0x0B00, 0x0B00, 0x0B00, 0x0B00, 0x0B00, 0x0B00, 0x0B00, 0x0B00, //  40 
			0x0AE0, 0x0AB0, 0x0AA0, 0x0A80, 0x0A50, 0x0A40, 0x0A30, 0x0A20, 0x0A10, 0x0A00, //  50 
			0x09E0, 0x09B0, 0x09A0, 0x0980, 0x0950, 0x0940, 0x0930, 0x0920, 0x0910, 0x0900, //  60 
			0x08E0, 0x08B0, 0x08A0, 0x0880, 0x0850, 0x0840, 0x0830, 0x0820, 0x0810, 0x0800,//  70 
			0x07E0, 0x07B0, 0x07A0, 0x0780, 0x0750, 0x0740, 0x0730, 0x0720, 0x0710, 0x0700, //  80 
			0x06E0, 0x06B0, 0x06A0, 0x0680, 0x0650, 0x0640, 0x0630, 0x0620, 0x0610, 0x0600, //  90 
			0x05E0, 0x05B0, 0x05A0, 0x0580, 0x0550, 0x0540, 0x0530, 0x0520, 0x0510, 0x0500,//  100 
		};
*/
	public boolean setKaraMixVolume(short volume)
	{
		try
		{
/*			int u8vol1,u8vol2;
			DtvSoundEffect dtvSoundEff = null;
			dtvSoundEff = new DtvSoundEffect();
			u8vol1 = ScaleValueX2Y(volume, 0x3F, 0, 100);
			dtvSoundEff.surroundXaValue = ((u8vol1) & 0x03);
			dtvSoundEff.surroundXbValue = ((u8vol1 >> 2) & 0x03);
			dtvSoundEff.surroundXkValue = ((u8vol1 >> 4) & 0x03);
			System.out.println("setKaraAudioMode SoundDeskImpl");
			am.setBasicSoundEffect(EnumSoundEffectType.E_ECHO, dtvSoundEff);
			
			u8vol2 = ScaleValueX2Y(volume, 0x8A, 0, 100);
			am.setSoundSpeakerDelay(u8vol2);
			am.setKtvMixModeVolume(EnumKtvMixVolumeType.E_ECHO1_VOL_,(short)(((Echo1_VolumeTable[volume]>>8) &0xff)), (short)(Echo1_VolumeTable[volume] & 0x07));
			am.setKtvMixModeVolume(EnumKtvMixVolumeType.E_ECHO2_VOL_,(short)(((Echo2_VolumeTable[volume]>>8) &0xff)), (short)(Echo2_VolumeTable[volume] & 0x07));
*/
			am.setInputLevel(EnumAudioInputLevelSourceType.E_VOL_SOURCE_PREMIXER_ECHO1_IN,volume);
		
		}
		catch (TvCommonException e)
		{
			com.printfE("TvSoundServiceImpl", "Set Volume Exception");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean setHdmiAudioSource(EnumInputSource inputSource, HdmiAudioSource audioSource)
	{
		databaseMgr.querySoundSetting();
		userSoundSetting = databaseMgr.getSound();
		
		switch(inputSource)
		{
		case E_INPUT_SOURCE_HDMI:
			userSoundSetting.hdmi1AudioSource = audioSource;
			break;
		case E_INPUT_SOURCE_HDMI2:
			userSoundSetting.hdmi2AudioSource = audioSource;
			break;
		case E_INPUT_SOURCE_HDMI3:
			userSoundSetting.hdmi3AudioSource = audioSource;
			break;
		case E_INPUT_SOURCE_HDMI4:
			userSoundSetting.hdmi4AudioSource = audioSource;
			break;
		default:
			return false;
		}
		com.printfI("TvSoundServiceImpl", "hdmiAudioSource is" + audioSource);
		databaseMgr.updateSoundSetting(userSoundSetting);
		try
		{
			EnumInputSource audioSrc;
			if(audioSource == HdmiAudioSource.AUDIO_SOURCE_VGA)
				audioSrc = EnumInputSource.E_INPUT_SOURCE_VGA;
			else
				audioSrc = inputSource;
			am.setInputSource(audioSrc);
		}
		catch (TvCommonException e)
		{
			com.printfE("TvSoundServiceImpl", "Set hdmiAudioSource Exception");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public HdmiAudioSource getHdmiAudioSource(EnumInputSource inputSource)
	{
		HdmiAudioSource audioSource;
		switch(inputSource)
		{
		case E_INPUT_SOURCE_HDMI:
			audioSource = userSoundSetting.hdmi1AudioSource;
			break;
		case E_INPUT_SOURCE_HDMI2:
			audioSource = userSoundSetting.hdmi2AudioSource;
			break;
		case E_INPUT_SOURCE_HDMI3:
			audioSource = userSoundSetting.hdmi3AudioSource;
			break;
		case E_INPUT_SOURCE_HDMI4:
			audioSource = userSoundSetting.hdmi4AudioSource;
			break;
		default:
			audioSource = HdmiAudioSource.AUDIO_SOURCE_HDMI;
			break;
		}
		
		return audioSource;
	}
	public boolean setKaraMixEffect(boolean flag){		
		try
		{
			System.out.println("setKaraAudioMode SoundDeskImpl");
			am.enableBasicSoundEffect(EnumSoundEffectType.E_ECHO,flag);
		}
		catch (TvCommonException e)
		{
			com.printfE("TvSoundServiceImpl", "Set Volume Exception");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean setKaraADC()
	{
		try 
		{
			System.out.printf("setKaraMicNR");
			am.setKtvSoundInfo(EnumKtvInfoType.E_KTV_SETINFO_ADC_GAIN,6,0);
			am.setKtvSoundInfo(EnumKtvInfoType.E_KTV_SETINFO_ADC1_GAIN,6,0);
		} catch (TvCommonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean setKaraMicNR(int value) {
		// TODO Auto-generated method stub
		System.out.printf("setKaraMicNR");
		am.setSoundParameter(EnumSoundSetParamType.E_SOUND_SET_PARAM_NR_THRESHOLD_,value,0);
		return true;
	}

	@Override
	public void setSRSTSHD(boolean bOnOff) {
		// TODO Auto-generated method stub
		try {
			if (bOnOff) {
				Log.d("KKJAVAAPI", "set SRS TSHD on");
//				am.enableBasicSoundEffect(EnumSoundEffectType.E_PEQ, true);
				setSoundMode(EN_SOUND_MODE.SOUND_MODE_STANDARD, false);

				am.enableAdvancedSoundEffect(
						EnumAdvancedSoundType.E_SRS_TSHD,
						EnumAdvancedSoundSubProcessType.E_SRS_THEATERSOUND_TSHD_ON);
				databaseMgr.setSRSOnOff(EnumSwitchOnOff.SWITCH_ON);

			} else {
				Log.d("KKJAVAAPI", "set SRS TSHD off");
				am.enableAdvancedSoundEffect(
						EnumAdvancedSoundType.E_SRS_TSHD,
						EnumAdvancedSoundSubProcessType.E_SRS_THEATERSOUND_TSHD_OFF);
//				am.enableBasicSoundEffect(EnumSoundEffectType.E_PEQ, false);
				databaseMgr.setSRSOnOff(EnumSwitchOnOff.SWITCH_OFF);

				EN_SOUND_MODE soundMode = getSoundMode();
				setSoundMode(soundMode);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void setSRSTrueBass(boolean bOnOff) {
		// TODO Auto-generated method stub
		try {
			if (bOnOff) {
				Log.d("KKJAVAAPI", "set SRS TrueBass on");
				am.enableAdvancedSoundEffect(
						EnumAdvancedSoundType.E_SRS_TSHD,
						EnumAdvancedSoundSubProcessType.E_SRS_THEATERSOUND_TRUEBASS_ON);
				databaseMgr.setTruebaseOnOff(EnumSwitchOnOff.SWITCH_ON);
				AdvancedSoundParameter stPara = new AdvancedSoundParameter();
				//The data show be got from DB
				stPara.paramSrsTheaterSoundTrubassControl = 5 * 100;
				am.setAdvancedSoundEffect(
						EnumAdvancedSoundParameterType.E_ADVSND_SRS_THEATERSOUND_TRUBASS_CONTROL,
						stPara);

				int iTrubass = am
						.getAdvancedSoundEffect(EnumAdvancedSoundParameterType.E_ADVSND_SRS_THEATERSOUND_TRUBASS_CONTROL);

				Log.d("KKJAVAAPI", "the iTrubass==" + iTrubass);

			} else {
				Log.d("KKJAVAAPI", "set SRS TrueBass off!");
				am.enableAdvancedSoundEffect(
						EnumAdvancedSoundType.E_SRS_TSHD,
						EnumAdvancedSoundSubProcessType.E_SRS_THEATERSOUND_TRUEBASS_OFF);
				databaseMgr.setTruebaseOnOff(EnumSwitchOnOff.SWITCH_OFF);
			}
		} catch (TvCommonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void setSRSDynamicClarity(boolean bOnOff) {
		// TODO Auto-generated method stub
		try {
			if (bOnOff) {
				Log.d("KKJAVAAPI", "set SRS DynamicClarity on");
				am.enableAdvancedSoundEffect(
						EnumAdvancedSoundType.E_SRS_TSHD,
						EnumAdvancedSoundSubProcessType.E_SRS_THEATERSOUND_DYNAMIC_CLARITY_ON);
				databaseMgr.setDialogClarityOnOff(EnumSwitchOnOff.SWITCH_ON);
				AdvancedSoundParameter stPara = new AdvancedSoundParameter();
				// The data show be got from DB
				stPara.paramSrsTheaterSoundDcControl = 7 * 100;
				am.setAdvancedSoundEffect(
						EnumAdvancedSoundParameterType.E_ADVSND_SRS_THEATERSOUND_DC_CONTROL,
						stPara);
			} else {
				Log.d("KKJAVAAPI", "set SRS DynamicClarity off");
				am.enableAdvancedSoundEffect(
						EnumAdvancedSoundType.E_SRS_TSHD,
						EnumAdvancedSoundSubProcessType.E_SRS_THEATERSOUND_DYNAMIC_CLARITY_OFF);
				databaseMgr.setDialogClarityOnOff(EnumSwitchOnOff.SWITCH_OFF);
			}
		} catch (TvCommonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void setSRSDefination(boolean bOnOff) {
		// TODO Auto-generated method stub
		try {
			if (bOnOff) {
				Log.d("KKJAVAAPI", "set SRS Defination on!");
				am.enableAdvancedSoundEffect(
						EnumAdvancedSoundType.E_SRS_TSHD,
						EnumAdvancedSoundSubProcessType.E_SRS_THEATERSOUND_DEFINITION_ON);
				databaseMgr.setDefinitionOnOff(EnumSwitchOnOff.SWITCH_ON);
				AdvancedSoundParameter stPara = new AdvancedSoundParameter();
				// The data show be got from DB
				stPara.paramSrsTheaterSoundDefinitionControl = 3 * 100;
				am.setAdvancedSoundEffect(
						EnumAdvancedSoundParameterType.E_ADVSND_SRS_THEATERSOUND_DEFINITION_CONTROL,
						stPara);
			} else {
				Log.d("KKJAVAAPI", "set SRS Defination off");
				am.enableAdvancedSoundEffect(
						EnumAdvancedSoundType.E_SRS_TSHD,
						EnumAdvancedSoundSubProcessType.E_SRS_THEATERSOUND_DEFINITION_OFF);
				databaseMgr.setDefinitionOnOff(EnumSwitchOnOff.SWITCH_OFF);
			}
		} catch (TvCommonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void setSRSPara(EN_SRS_SET_TYPE eSRS, int iValue) {
		// TODO Auto-generated method stub
		AdvancedSoundParameter stPara = new AdvancedSoundParameter();
		KK_SRS_SET stSRS = new KK_SRS_SET();
		if(eSRS == EN_SRS_SET_TYPE.E_SRS_INPUTGAIN){
			stPara.paramSrsTheaterSoundInputGain = databaseMgr.getSRSSet().srs_InputGain * 100;
			try {
				am.setAdvancedSoundEffect(
						EnumAdvancedSoundParameterType.E_ADVSND_SRS_THEATERSOUND_INPUT_GAIN,
						stPara);
			} catch (TvCommonException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			stSRS.srs_InputGain = iValue;
			databaseMgr.setSRSSet(stSRS, eSRS);
		}else if(eSRS == EN_SRS_SET_TYPE.E_SRS_SURRLEVEL_CONTROL){
			stPara.paramSrsTheaterasoundSurrLevelControl = databaseMgr.getSRSSet().srs_SurrLevelControl * 100;
			try {
				am.setAdvancedSoundEffect(
						EnumAdvancedSoundParameterType.ADVSND_SRS_THEATERSOUND_SURR_LEVEL_CONTROL,
						stPara);
			} catch (TvCommonException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			stSRS.srs_SurrLevelControl = iValue;
			databaseMgr.setSRSSet(stSRS, eSRS);
		}else if(eSRS == EN_SRS_SET_TYPE.E_SRS_SPEAKERAUDIO){
			stPara.paramSrsTheaterasoundTrubassSpeakerAudio = databaseMgr.getSRSSet().srs_SpeakerAudio;
			try {
				am.setAdvancedSoundEffect(
						EnumAdvancedSoundParameterType.ADVSND_SRS_THEATERSOUND_TRUBASS_SPEAKER_AUDIO,
						stPara);
			} catch (TvCommonException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			stSRS.srs_SpeakerAudio = iValue;
			databaseMgr.setSRSSet(stSRS, eSRS);
		}else if(eSRS == EN_SRS_SET_TYPE.E_SRS_SPEAKERANALYSIS){
			stPara.paramSrsTheaterasoundTrubassSpeakerAnalysis = databaseMgr.getSRSSet().srs_SpeakerAnalysis;
			try {
				am.setAdvancedSoundEffect(
						EnumAdvancedSoundParameterType.ADVSND_SRS_THEATERSOUND_SPEAKER_ANALYSIS,
						stPara);
			} catch (TvCommonException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			stSRS.srs_SpeakerAnalysis = iValue;
			databaseMgr.setSRSSet(stSRS, eSRS);
		}else if(eSRS == EN_SRS_SET_TYPE.E_SRS_TRUBASS_CONTROL){
			stPara.paramSrsTheaterSoundTrubassControl = databaseMgr.getSRSSet().srs_TrubassControl * 100;
			try {
				am.setAdvancedSoundEffect(
						EnumAdvancedSoundParameterType.E_ADVSND_SRS_THEATERSOUND_TRUBASS_CONTROL,
						stPara);
			} catch (TvCommonException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			stSRS.srs_TrubassControl = iValue;
			databaseMgr.setSRSSet(stSRS, eSRS);
		}else if(eSRS == EN_SRS_SET_TYPE.E_SRS_DC_CONTROL){
			stPara.paramSrsTheaterSoundDcControl = databaseMgr.getSRSSet().srs_DCControl * 100;
			try {
				am.setAdvancedSoundEffect(
						EnumAdvancedSoundParameterType.E_ADVSND_SRS_THEATERSOUND_DC_CONTROL,
						stPara);
			} catch (TvCommonException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			stSRS.srs_DCControl = iValue;
			databaseMgr.setSRSSet(stSRS, eSRS);
		}else if(eSRS == EN_SRS_SET_TYPE.E_SRS_DEFINITION_CONTROL){
			stPara.paramSrsTheaterSoundDefinitionControl = databaseMgr.getSRSSet().srs_DefinitionControl * 100;
			try {
				am.setAdvancedSoundEffect(
						EnumAdvancedSoundParameterType.E_ADVSND_SRS_THEATERSOUND_DEFINITION_CONTROL,
						stPara);
			} catch (TvCommonException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			stSRS.srs_DefinitionControl = iValue;
			databaseMgr.setSRSSet(stSRS, eSRS);
		}
	}
}