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
package com.konka.kkinterface.tv;

import com.konka.kkinterface.tv.DataBaseDesk.EN_SOUND_MODE;
import com.konka.kkinterface.tv.DataBaseDesk.EN_SPDIF_MODE;
import com.konka.kkinterface.tv.DataBaseDesk.EN_SRS_SET_TYPE;
import com.konka.kkinterface.tv.DataBaseDesk.EN_SURROUND_MODE;
import com.konka.kkinterface.tv.DataBaseDesk.HdmiAudioSource;
import com.tvos.common.vo.TvOsType;
import com.tvos.common.vo.TvOsType.EnumInputSource;
/**
 * 
 * Base Interface of TV Service
 * 
 * 
 * 
 * @author stan
 * 
 * 
 */
public interface SoundDesk extends BaseDesk{	/**	 * 	 * Set sound mode	 * 	 * 	 * 	 * @param EN_SOUND_MODE	 * 	 * @return true if operation success or false if fail.	 */	public boolean setSoundMode(EN_SOUND_MODE SoundMode);
		public boolean setSoundMode(EN_SOUND_MODE SoundMode,boolean bWriteDB);	/**	 * 	 * Get sound mode	 * 	 * 	 * 	 * @return surround mode Enum	 */	public EN_SOUND_MODE getSoundMode();	/**	 * 	 * Set bass value	 * 	 * 	 * 	 * @param bass	 * 	 *            value 0~100	 * 	 * @return true if operation success or false if fail.	 */	public boolean setBass(short bassValue);	/**	 * 	 * Get Bass Value	 * 	 * 	 * 	 * @return Bass Value 0~100	 */	public short getBass();	/**	 * 	 * Set treble value	 * 	 * 	 * 	 * @param treble	 * 	 *            value 0~100	 * 	 * @return true if operation success or false if fail.	 */	public boolean setTreble(short bassValue);	/**	 * 	 * Get treble value	 * 	 * 	 * 	 * @return treble value 0~100	 */	public short getTreble();	/**	 * 	 * Set balance	 * 	 * 	 * 	 * @param balance	 * 	 *            value 0~100	 * 	 * @return true if operation success or false if fail.	 */	public boolean setBalance(short balanceValue);	/**	 * 	 * Get balance	 * 	 * 	 * 	 * @return balance value 0~100	 */	public short getBalance();	/**	 * 	 * Set Avc mode	 * 	 * 	 * 	 * @param AvcEnable	 * 	 *            , boolean value, true if enable, false if disable	 * 	 * @return true if operation success or false if fail.	 */	public boolean setAVCMode(boolean isAvcEnable);	/**	 * 	 * Get Avc mode	 * 	 * 	 * 	 * @return isAvcEnable, boolean value, true if enable, false if disable	 */	public boolean getAVCMode();	/**	 * 	 * Set surround sound mode	 * 	 * 	 * 	 * @param EN_SURROUND_MODE	 * 	 * @return boolean value, true if enable, false if disable	 */	public boolean setSurroundMode(EN_SURROUND_MODE surroundMode);	/**	 * 	 * Get surround sound mode	 * 	 * 	 * 	 * @return EN_SURROUND_MODE	 */	public EN_SURROUND_MODE getSurroundMode();	/**	 * 	 * Set spdif out mode	 * 	 * 	 * 	 * @param EN_SPDIF_MODE	 * 	 * @return boolean value, true if enable, false if disable	 */	public boolean setSpdifOutMode(EN_SPDIF_MODE spdifMode);	/**	 * 	 * Get spdif out mode	 * 	 * 	 * 	 * @return EN_SPDIF_MODE	 */	public EN_SPDIF_MODE getSpdifOutMode();	// public boolean SetMusicOnlyMode();	// public boolean GetMusicOnlyMode();	/**	 * 	 * Set EQ of band 120	 * 	 * 	 * 	 * @param eq	 * 	 *            value 0~100	 * 	 * @return boolean value, true if enable, false if disable	 */	public boolean setEqBand120(short eqValue);	/**	 * 	 * Get EQ of band 120	 * 	 * 	 * 	 * @return eq value 0~100	 */	public short getEqBand120();	/**	 * 	 * Set EQ of band 500	 * 	 * 	 * 	 * @param eq	 * 	 *            value 0~100	 * 	 * @return boolean value, true if enable, false if disable	 */	public boolean setEqBand500(short eqValue);	/**	 * 	 * Get EQ of band 500	 * 	 * 	 * 	 * @return eq value 0~100	 */	public short getEqBand500();	/**	 * 	 * Set EQ of band 1500	 * 	 * 	 * 	 * @param eq	 * 	 *            value 0~100	 * 	 * @return boolean value, true if enable, false if disable	 */	public boolean setEqBand1500(short eqValue);	/**	 * 	 * Get EQ of band 1500	 * 	 * 	 * 	 * @return eq value 0~100	 */	public short getEqBand1500();	/**	 * 	 * Set EQ of band 5k	 * 	 * 	 * 	 * @param eq	 * 	 *            value 0~100	 * 	 * @return boolean value, true if enable, false if disable	 */	public boolean setEqBand5k(short eqValue);	/**	 * 	 * Get EQ of band 5k	 * 	 * 	 * 	 * @return eq value 0~100	 */	public short getEqBand5k();	/**	 * 	 * Set EQ of band 10k	 * 	 * 	 * 	 * @param eq	 * 	 *            value 0~100	 * 	 * @return boolean value, true if enable, false if disable	 */	public boolean setEqBand10k(short eqValue);	/**	 * 	 * Get EQ of band 10k	 * 	 * 	 * 	 * @return eq value 0~100	 */	public short getEqBand10k();	/**	 * Set Volume function	 * 	 * @param volume	 * @return result status	 */	public boolean setVolume(short volume);	/**	 * Get Volume function	 * 	 * @return Volume value	 */	public short getVolume();
	public boolean setKaraSystemVolume(short volume);
	public boolean setKaraVolume(short volume);
	public boolean setKaraMicVolume(short volume);
	public boolean setKaraMixVolume(short volume);
	public boolean setKaraAudioTrack(int mode);
	public boolean setAudioInputSource(TvOsType.EnumInputSource enAudioInputSource);
	public boolean setHdmiAudioSource(EnumInputSource inputSource, HdmiAudioSource audioSource);	public HdmiAudioSource getHdmiAudioSource(EnumInputSource inputSource);	public boolean setKaraMixEffect(boolean flag);	
	public boolean setKaraADC();
	public boolean setKaraMicNR(int value);
	public boolean setSoundSpeakerDelay(int delay);
	public int getSoundSpeakerDelay();
	/**
	 * set SRS
	 */
	public void setSRSTSHD(boolean bOnOff);
	public void setSRSTrueBass(boolean bOnOff);
	public void setSRSDynamicClarity(boolean bOnOff);
	public void setSRSDefination(boolean bOnOff);
	
	public void setSRSPara(EN_SRS_SET_TYPE eSRS,int iValue);
}