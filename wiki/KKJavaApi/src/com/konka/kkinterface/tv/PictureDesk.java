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
package com.konka.kkinterface.tv;import com.konka.kkinterface.tv.DataBaseDesk.EN_MS_COLOR_TEMP;import com.konka.kkinterface.tv.DataBaseDesk.EN_MS_Dynamic_Contrast;
import com.konka.kkinterface.tv.DataBaseDesk.EN_MS_LOCALDIMMING;
import com.konka.kkinterface.tv.DataBaseDesk.EN_MS_MPEG_NR;import com.konka.kkinterface.tv.DataBaseDesk.EN_MS_NR;import com.konka.kkinterface.tv.DataBaseDesk.EN_MS_PICTURE;import com.konka.kkinterface.tv.DataBaseDesk.EN_MS_VIDEOITEM;import com.konka.kkinterface.tv.DataBaseDesk.MAPI_VIDEO_ARC_Type;import com.konka.kkinterface.tv.DataBaseDesk.SkinToneMode;
import com.konka.kkinterface.tv.DataBaseDesk.T_MS_COLOR_TEMPEX_DATA;import com.tvos.common.exception.TvCommonException;
import com.tvos.common.vo.VideoWindowType;
/** *  *  *  * Picture adjust service *  * @author Shuai.Wang *  *  */public interface PictureDesk extends BaseDesk{	final static int AUTOPC_START = 1;	final static int AUTOPC_END_SUCESSED = 2;	final static int AUTOPC_END_FAILED = 3;	/**	 * 	 * Set Picture Value by Item Index	 * 	 * @param eIndex	 *            Item Index	 * 	 * @param value	 *            Set Item Value	 * 	 * @return true:sucessed; false:failed	 */	public boolean ExecVideoItem(EN_MS_VIDEOITEM eIndex, short value);	/**	 * 	 * Get Picture Value by Item Index	 * 	 * @param eIndex	 *            Item Index	 * 	 * @return Item Value	 */	public short GetVideoItem(EN_MS_VIDEOITEM eIndex);	/**	 * 	 * To Set Backlight Value	 * 	 * @param value	 *            Set Value	 * 	 * @return true:sucessed; false:failed	 */	public boolean SetPictureModeIdx(EN_MS_PICTURE ePicMode);
	public EN_MS_PICTURE GetPictureModeIdx();
	public int getDynamicBLModeIdx();
	public boolean setDynamicBLModeIdx(EN_MS_LOCALDIMMING Localdimminglevel);
	public boolean enableSetBacklight(boolean enable);
	public boolean SetBacklight(short value);	/**	 * 	 * To Get Backlight Value	 * 	 * @return Backlight Value	 */	public short GetBacklight();
	public short GetBacklightOfPicMode(EN_MS_PICTURE ePicture);
		public boolean SetColorTempIdx(EN_MS_COLOR_TEMP eColorTemp);	public EN_MS_COLOR_TEMP GetColorTempIdx();	public boolean SetColorTempPara(T_MS_COLOR_TEMPEX_DATA stColorTemp);	public T_MS_COLOR_TEMPEX_DATA GetColorTempPara();	public boolean SetVideoArc(MAPI_VIDEO_ARC_Type eArcIdx);	public MAPI_VIDEO_ARC_Type GetVideoArc();	public boolean SetMpegNR(EN_MS_MPEG_NR eMpNRIdx);	public EN_MS_MPEG_NR GetMpegNR();	public boolean SetNR(EN_MS_NR eNRIdx);	public EN_MS_NR GetNR();	public short GetPCHPos();	public boolean SetPCHPos(short hpos);	public short GetPCVPos();	public boolean SetPCVPos(short vpos);	public short GetPCClock();	public boolean SetPCClock(short clock);	public short GetPCPhase();	public boolean SetPCPhase(short phase);	public boolean ExecAutoPc();
	
	public void setDisplayWindow(VideoWindowType videoWindowType);

	public int[] getDynamicContrastCurve();	public short getDlcAverageLuma();
	public boolean SetKTVVideoArc();

	SkinToneMode getSkinToneMode();
	
	boolean setSkinToneMode(SkinToneMode mode);

	boolean getDetailEnhance();

	boolean setDetailEnhance(boolean status);

	EN_MS_NR getDNR();

	boolean setDNR(EN_MS_NR mode);

	EN_MS_Dynamic_Contrast getDynamicContrast();

	boolean setDynamicContrast(EN_MS_Dynamic_Contrast mode);
	
	boolean getEnergyEnable();
	
	boolean setEnergyEnable(boolean enable);

	short getEnergyPercent();
	
	boolean setEnergyPercent(short percent);
	
	public boolean refreshVideoPara();
}