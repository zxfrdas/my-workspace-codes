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
package com.konka.kkinterface.tv;import com.konka.kkinterface.tv.DataBaseDesk.EN_ThreeD_Video_3DDEPTH;import com.konka.kkinterface.tv.DataBaseDesk.EN_ThreeD_Video_3DOFFSET;import com.konka.kkinterface.tv.DataBaseDesk.EN_ThreeD_Video_3DOUTPUTASPECT;import com.konka.kkinterface.tv.DataBaseDesk.EN_ThreeD_Video_3DTO2D;import com.konka.kkinterface.tv.DataBaseDesk.EN_ThreeD_Video_AUTOSTART;import com.konka.kkinterface.tv.DataBaseDesk.EN_ThreeD_Video_DISPLAYFORMAT;import com.konka.kkinterface.tv.DataBaseDesk.EN_ThreeD_Video_LRVIEWSWITCH;import com.konka.kkinterface.tv.DataBaseDesk.EN_ThreeD_Video_SELFADAPTIVE_DETECT;import com.konka.kkinterface.tv.DataBaseDesk.EN_ThreeD_Video_SELFADAPTIVE_LEVEL;/** *  * Base Interface of TV Service *  * @author stan *  */public interface S3DDesk extends BaseDesk{	/**	 * 	 * set self adaptive detect	 * 	 * @param EN_ThreeD_Video_SELFADAPTIVE_DETECT	 * 	 * @return true if operation success or false if fail.	 */	public boolean setSelfAdaptiveDetect(	        EN_ThreeD_Video_SELFADAPTIVE_DETECT selfAdaptiveDetect);	/**	 * 	 * get self adaptive detect	 * 	 * @return EN_ThreeD_Video_SELFADAPTIVE_DETECT	 * 	 */	public EN_ThreeD_Video_SELFADAPTIVE_DETECT getSelfAdaptiveDetect();		/**	 * 	 * set self adaptive level	 * 	 * @param EN_ThreeD_Video_SELFADAPTIVE_LEVEL	 * 	 * @return true if operation success or false if fail.	 */	public boolean setSelfAdaptiveLevel(	        EN_ThreeD_Video_SELFADAPTIVE_LEVEL selfAdaptiveLevel);	/**	 * 	 * get self adaptive level	 * 	 * @return EN_ThreeD_Video_SELFADAPTIVE_LEVEL	 * 	 */	public EN_ThreeD_Video_SELFADAPTIVE_LEVEL getSelfAdaptiveLevel();		/**	 * 	 * set display format	 * 	 * @param EN_ThreeD_Video_DISPLAYFORMAT	 * 	 * @return true if operation success or false if fail.	 */	public boolean setDisplayFormat(	        EN_ThreeD_Video_DISPLAYFORMAT displayFormat);
	public boolean setVideoScaleforKTV();
	/**	 * 	 * get display format	 * 	 * @return EN_ThreeD_Video_DISPLAYFORMAT	 * 	 */	public EN_ThreeD_Video_DISPLAYFORMAT getDisplayFormat();	/**	 * 	 * set display 3dto2d mode	 * 	 * @param EN_ThreeD_Video_3DTO2D	 * 	 * @return true if operation success or false if fail.	 */	public boolean set3DTo2D(	        EN_ThreeD_Video_3DTO2D displayMode);	/**	 * 	 * get display 3dto2d mode	 * 	 * @return EN_ThreeD_Video_3DTO2D	 * 	 */	public EN_ThreeD_Video_3DTO2D getDisplay3DTo2DMode();			/**	 * 	 * set 3D depth mode	 * 	 * @param EN_ThreeD_Video_3DDEPTH	 *            (level 0-31)	 * 	 * @return true if operation success or false if fail.	 */	public boolean set3DDepthMode(int mode3DDepth);	/**	 * 	 * get 3D depth mode	 * 	 * @return EN_ThreeD_Video_3DDEPTH	 * 	 */	public int get3DDepthMode();	/**	 * 	 * set 3D offset mode	 * 	 * @param EN_ThreeD_Video_3DOFFSET	 *            (level 0-31)	 * 	 * @return true if operation success or false if fail.	 */	public boolean set3DOffsetMode(int mode3DOffset);	/**	 * 	 * get 3D offset mode	 * 	 * @return EN_ThreeD_Video_3DOFFSET	 * 	 */	public int get3DOffsetMode();	/**	 * 	 * set auto start mode	 * 	 * @param EN_ThreeD_Video_AUTOSTART	 * 	 * @return true if operation success or false if fail.	 */	public boolean setAutoStartMode(	        EN_ThreeD_Video_AUTOSTART autoStartMode);	/**	 * 	 * get auto start mode	 * 	 * @return EN_ThreeD_Video_AUTOSTART	 * 	 */	public EN_ThreeD_Video_AUTOSTART getAutoStartMode();	/**	 * 	 * set 3D output aspect mode	 * 	 * @param EN_ThreeD_Video_3DOUTPUTASPECT	 * 	 * @return true if operation success or false if fail.	 */	public boolean set3DOutputAspectMode(	        EN_ThreeD_Video_3DOUTPUTASPECT outputAspectMode);	/**	 * 	 * get 3D output aspect mode	 * 	 * @return EN_ThreeD_Video_3DOUTPUTASPECT	 */	public EN_ThreeD_Video_3DOUTPUTASPECT get3DOutputAspectMode();	/**	 * 	 * set LR view switch enable or disable	 * 	 * @param EN_ThreeD_Video_LRVIEWSWITCH	 * 	 * @return true if operation success or false if fail.	 */	public boolean setLRViewSwitch(	        EN_ThreeD_Video_LRVIEWSWITCH LRViewSwitchMode);	/**	 * 	 * get LR view switch status (enable or disable)	 * 	 * @return EN_ThreeD_Video_LRVIEWSWITCH	 */	public EN_ThreeD_Video_LRVIEWSWITCH getLRViewSwitch();
	
	/**
	 * 
	 * enable 3d clarity intelligent
	 * 
	 */
	public void enableLow3dQuality();

	/**
	 * 
	 * enable 3d clarity intelligent
	 * 
	 */
	public void disableLow3dQuality(); 
	}