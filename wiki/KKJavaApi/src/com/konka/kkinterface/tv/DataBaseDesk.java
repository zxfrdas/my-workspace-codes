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
 * additional licenses from third parties.  Therefore, you hereby agree it is your 
 * sole responsibility to separately obtain any and all third party right and 
 * license necessary for your use of such third party鈥檚 software. 
 *
 * 3. MStar Software and any modification/derivatives thereof shall be deemed as 
 * MStar鈥檚 confidential information and you agree to keep MStar鈥檚 confidential 
 * information in strictest confidence and not disclose to any third party.  
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
package com.konka.kkinterface.tv;
import com.tvos.common.vo.TvOsType.EnumInputSource;
public final static short T_ChinaDVBCSetting_IDX = 0x04;
		// / picture mode dynamic
		PICTURE_DYNAMIC,
		// / picture mode normal
	public static enum EN_MS_LOCALDIMMING
	{
		// / Local Dimming level on
		LOCALDIMMING_OFF,
		// / Local Dimming level off
		LOCALDIMMING_ON,
		/// local Diming level nums
		LOCALDIMMING_NUMS
	}
	/** color temperature */

	/** skin tone mode */
	public enum SkinToneMode
	{
		SKIN_TONE_OFF,
		SKIN_TONE_RED,
		SKIN_TONE_YELLOW,
	}
	

		}
		public SkinToneMode skinTone;
		public boolean detailEnhance;
		public EN_MS_NR DNR;
	}
	

	public static enum HdmiAudioSource
	{
		AUDIO_SOURCE_HDMI,
		AUDIO_SOURCE_VGA,
	}
	
	
	public static enum SmartEnergySavingMode
	{
		MODE_OFF,
		MODE_ON,
		MODE_DEMO,
	}
	
	public static enum ColorWheelMode
	{
		MODE_OFF,
		MODE_ON,
		MODE_DEMO,
	}
	
	public static enum EnumSwitchOnOff {
		SWITCH_OFF, SWITCH_ON
	}
	
		// / Invivid Settings
		public int standbyNoOperation;
		public boolean standbyNoSignal;
		public boolean screenSaveMode;
		public SmartEnergySavingMode smartEnergySaving;
		public ColorWheelMode colorWheelMode;
	}
		// / hdmi1AudioSource
		public HdmiAudioSource hdmi1AudioSource;
		// / hdmi2AudioSource
		public HdmiAudioSource hdmi2AudioSource;
		// / hdmi3AudioSource
		public HdmiAudioSource hdmi3AudioSource;
		// / hdmi4AudioSource
		public HdmiAudioSource hdmi4AudioSource;
	
	// / SRS settings
	public static enum EN_SRS_SET_TYPE{
		E_SRS_INPUTGAIN,
		E_SRS_SURRLEVEL_CONTROL,
		E_SRS_SPEAKERAUDIO,
		E_SRS_SPEAKERANALYSIS,
		E_SRS_TRUBASS_CONTROL,
		E_SRS_DC_CONTROL,
		E_SRS_DEFINITION_CONTROL,
	}
		public class KK_SRS_SET
		{
			public int srs_InputGain;
			public int srs_SurrLevelControl;
			public int srs_SpeakerAudio;
			public int srs_SpeakerAnalysis;
			public int srs_TrubassControl;
			public int srs_DCControl;
			public int srs_DefinitionControl;

			public KK_SRS_SET()
			{
				srs_InputGain = 6;
				srs_SurrLevelControl=7;
				srs_SpeakerAudio=9;
				srs_SpeakerAnalysis=4;
				srs_TrubassControl=5;
				srs_DCControl=7;
				srs_DefinitionControl=3;
			}
		}
	final int EN_3D_SELFADAPTIVE_LEVEL_MIDDLE = 0x01;
	final int EN_3D_SELFADAPTIVE_LEVEL_HIGH = 0x02;
	final int EN_3D_SELFADAPTIVE_LEVEL_MAX = 0x03;
	// }
		public int st3DSelfAdaptiveLevel;
		// / power mode setting
		// / burn in mode
		public boolean bBurnIn;
			stPowerMode = EN_POWER_MODE_MEMORY;
			bBurnIn = false;
	// / Misc Setting
	public class CustomerCfgMiscSetting
	{
		public boolean energyEnable;
		public short energyPercent;

		public CustomerCfgMiscSetting()
		{
			energyEnable = false;
			energyPercent = 50;
		}
	}
		

	

	public void SyncUserSettingDB();
	
	//edit by zhaotong 2012-3-26 for dtv city
	public EnumChinaDvbcRegion getDTVCity();
	
	public void setDTVCity(EnumChinaDvbcRegion eType);
	
	public int getDVBCNetTableFrequency();
	
	public void setDVBCNetTableFrequency(int iFre);

	public int getDynamicBLMode();
	
	public void setDynamicBLMode(EN_MS_LOCALDIMMING DynamicMode);
	
	public EN_ThreeD_Video_SELFADAPTIVE_DETECT queryVideo3DSelfAdaptiveDetectMode(int inputSrcType);
	
	/**
	 * SRS setting
	 */
	public void setSRSOnOff(EnumSwitchOnOff eOnOff);
	
	
	
	
	public KK_SRS_SET getSRSSet();
	
	public CustomerCfgMiscSetting getCustomerCfgMiscSetting();
	public boolean setCustomerCfgMiscSetting(CustomerCfgMiscSetting miscSetting);