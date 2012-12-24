/**
 * MStar Software
 * Copyright (c) 2011 - 2012 MStar Semiconductor, Inc. All rights reserved.
 *
 * All software, firmware and related documentation herein (“MStar Software�? are 
 * intellectual property of MStar Semiconductor, Inc. (“MStar�? and protected by 
 * law, including, but not limited to, copyright law and international treaties. 
 * Any use, modification, reproduction, retransmission, or republication of all 
 * or part of MStar Software is expressly prohibited, unless prior written 
 * permission has been granted by MStar. 
 *
 * By accessing, browsing and/or using MStar Software, you acknowledge that you 
 * have read, understood, and agree, to be bound by below terms (“Terms�? and to 
 * comply with all applicable laws and regulations:
 *
 * 1. MStar shall retain any and all right, ownership and interest to MStar 
 * Software and any modification/derivatives thereof.  No right, ownership, 
 * or interest to MStar Software and any modification/derivatives thereof is 
 * transferred to you under Terms.
 *
 * 2. You understand that MStar Software might include, incorporate or be supplied 
 * together with third party’s software and the use of MStar Software may require 
 * additional licenses from third parties.  Therefore, you hereby agree it is your 
 * sole responsibility to separately obtain any and all third party right and 
 * license necessary for your use of such third party’s software. 
 *
 * 3. MStar Software and any modification/derivatives thereof shall be deemed as 
 * MStar’s confidential information and you agree to keep MStar’s confidential 
 * information in strictest confidence and not disclose to any third party.  
 *	
 * 4. MStar Software is provided on an “AS IS�?basis without warranties of any kind. 
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
 * in conjunction with your or your customer’s product (“Services�?.  You understand 
 * and agree that, except otherwise agreed by both parties in writing, Services are 
 * provided on an “AS IS�?basis and the warranty disclaimer set forth in Section 4 
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

import com.konka.kkinterface.tv.CiDesk;
import com.konka.kkinterface.tv.CommonDesk;
import com.tvos.common.exception.TvCommonException;
import com.tvos.dtv.common.CiManager;
import com.tvos.dtv.common.CiManager.CredentialValidDateRange;
import com.tvos.dtv.common.CiManager.EnumCardState;
import com.tvos.dtv.common.CiManager.EnumMmiType;
import com.tvos.dtv.common.CiManager.OnCiEventListener;
import com.tvos.dtv.common.DtvManager;
public class CiDeskImpl extends BaseDeskImpl implements CiDesk
{
    private static CiDeskImpl ciMgrImpl = null;
    private CommonDesk com = null;
    private CiManager ciMgr = null;
    private Context context;
    
    private CiDeskImpl(Context context)
    {
        com = CommonDeskImpl.getInstance(context);
        com.printfI("TvService", "CiManagerImpl constructor!!");
        try
        {
            ciMgr = DtvManager.getCiManager();
        }
        catch (TvCommonException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static CiDeskImpl getCiMgrInstance(Context context)
    {
        if (ciMgrImpl == null)
            ciMgrImpl = new CiDeskImpl(context);
        return ciMgrImpl;
    }

    /**
     * Credential Mode Set (Set CI+ Credential Mode)
     * 
     * @param credentialMode short CI Credential Mode
     * @throws TvCommonException
     */
    public void setCiCredentialMode(short credentialMode)
        throws TvCommonException
    {
        ciMgr.setCiCredentialMode(credentialMode);
    }

    /**
     * Check CI+ Credential Mode is valid or not
     * 
     * @param credentialMode short one of CI Credential Mode
     * @return boolean
     * @throws TvCommonException
     */
    public boolean isCiCredentialModeValid(short credentialMode)
        throws TvCommonException
    {
        return ciMgr.isCiCredentialModeValid(credentialMode);
    }

    /**
     * To enter the CIMMI menu
     * 
     * @throws TvCommonException
     */
    public void enterMenu() throws TvCommonException
    {
        ciMgr.enterMenu();
    }

    /**
     * To return CIMMI current menu
     * 
     * @throws TvCommonException
     */
    public void backMenu() throws TvCommonException
    {
        ciMgr.backMenu();
    }

    /**
     * Check if the content received in CIMMI existed, UI will draw graph
     * according to the result.
     * 
     * @return TRUE: data existed; FALSE: data not existed.
     * @throws TvCommonException
     */
    public boolean isDataExisted() throws TvCommonException
    {
        return ciMgr.isDataExisted();
    }

    /**
     * To get CIMMI type
     * 
     * @return EnumMmiType one of MMI Type
     * @throws TvCommonException
     */
    public EnumMmiType getMmiType() throws TvCommonException
    {
        return ciMgr.getMmiType();
    }

    /**
     * To get menu string of CI menu
     * 
     * @return String Menu string
     * @throws TvCommonException
     */
    public String getMenuString() throws TvCommonException
    {
        return ciMgr.getMenuString();
    }

    /**
     * To get menu title string
     * 
     * @return String Menu title string
     * @throws TvCommonException
     */
    public String getMenuTitleString() throws TvCommonException
    {
        return ciMgr.getMenuTitleString();
    }

    /**
     * Get Menu Bottom String
     * 
     * @return String Menu bottom string
     * @throws TvCommonException
     */
    public String getMenuBottomString() throws TvCommonException
    {
        return ciMgr.getMenuBottomString();
    }

    /**
     * To get list title string
     * 
     * @return String List title string
     * @throws TvCommonException
     */
    public String getListTitleString() throws TvCommonException
    {
        return ciMgr.getListTitleString();
    }

    /**
     * To get List Title Length
     * 
     * @return String List title string
     * @throws TvCommonException
     */
    public int getListTitleLength() throws TvCommonException
    {
        return ciMgr.getListTitleLength();
    }

    /**
     * To get Menu Choice number
     * 
     * @return short Menu Choice number
     * @throws TvCommonException
     */
    public short getMenuChoiceNumber() throws TvCommonException
    {
        return ciMgr.getMenuChoiceNumber();
    }

    /**
     * To get List Choice number
     * 
     * @return short List Choice number
     * @throws TvCommonException
     */
    public short getListChoiceNumber() throws TvCommonException
    {
        return ciMgr.getListChoiceNumber();
    }

    /**
     * To get blind Answer
     * 
     * @return short show password or not
     * @throws TvCommonException
     */
    public short getEnqBlindAnswer() throws TvCommonException
    {
        return ciMgr.getEnqBlindAnswer();
    }

    /**
     * To get List Subtitle Length
     * 
     * @return String List subtitle length
     * @throws TvCommonException
     */
    public int getListSubtitleLength() throws TvCommonException
    {
        //TODO
        //return ciMgr.getListSubtitleString();
        return 1;
    }
    
    /**
     * To get List Subtitle String
     * 
     * @return String List subtitle string
     * @throws TvCommonException
     */
    public String getListSubtitleString() throws TvCommonException
    {
        return ciMgr.getListSubtitleString();
    }

    /**
     * Get list bottom string
     * 
     * @return String List bottom string
     * @throws TvCommonException
     */
    public String getListBottomString() throws TvCommonException
    {
        return ciMgr.getListBottomString();
    }

    /**
     * Get menu selection string
     * 
     * @param index Menu index
     * @return String Menu selection string
     * @throws TvCommonException
     */
    public String getMenuSelectionString(int index) throws TvCommonException
    {
        return ciMgr.getMenuSelectionString(index);
    }

    /**
     * Get list selection string
     * 
     * @param index List index
     * @return String List selection string
     * @throws TvCommonException
     */
    public String getListSelectionString(int index) throws TvCommonException
    {
        return ciMgr.getListSelectionString(index);
    }

    /**
     * Get enq String
     * 
     * @return String Enq string
     * @throws TvCommonException
     */
    public String getEnqString() throws TvCommonException
    {
        return ciMgr.getEnqString();
    }

    /**
     * Get Menu Title Length
     * 
     * @return int Menu title length
     * @throws TvCommonException
     */
    public int getMenuTitleLength() throws TvCommonException
    {
        return ciMgr.getMenuTitleLength();
    }

    /**
     * Get list subtitle length
     * 
     * @return int List subtitle length
     * @throws TvCommonException
     */
    public int getMenuSubtitleLength() throws TvCommonException
    {
        return ciMgr.getMenuSubtitleLength();
    }

    /**
     * To get Menu Subtitle String
     * 
     * @return String Menu Title String
     * @throws TvCommonException
     */
    public String getMenuSubtitleString() throws TvCommonException
    {
        return ciMgr.getMenuSubtitleString();
    }

    /**
     * To get Menu Bottom Length
     * 
     * @return int Menu Bottom Length
     * @throws TvCommonException
     */
    public int getMenuBottomLength() throws TvCommonException
    {
        return ciMgr.getMenuBottomLength();
    }

    /**
     * To close CIMMI
     * 
     * @throws TvCommonException
     */
    public void close() throws TvCommonException
    {
        ciMgr.close();
    }

    /**
     * Get list bottom length
     * 
     * @return int List bottom length
     * @throws TvCommonException
     */
    public int getListBottomLength() throws TvCommonException
    {
        return ciMgr.getListBottomLength();
    }

    /**
     * Get CI card password length
     * 
     * @return short Enq length
     * @throws TvCommonException
     */
    public short getEnqLength() throws TvCommonException
    {
        return ciMgr.getEnqLength();
    }

    /**
     * Get CI card password answer length
     * 
     * @return short Enq answer length
     * @throws TvCommonException
     */
    public short getEnqAnsLength() throws TvCommonException
    {
        return ciMgr.getEnqAnsLength();
    }

    /**
     * Back to CI card password menu
     * 
     * @return boolean
     * @throws TvCommonException
     */
    public boolean backEnq() throws TvCommonException
    {
        return ciMgr.backEnq();
    }

    /**
     * Enter CI card password
     * 
     * @param password String password
     * @return boolean
     * @throws TvCommonException
     */
    public boolean answerEnq(String password) throws TvCommonException
    {
        return ciMgr.answerEnq(password);
    }

    /**
     * To enter CIMMI menu
     * 
     * @param index short menu index
     * @throws TvCommonException
     */
    public void answerMenu(short index) throws TvCommonException
    {
        ciMgr.answerMenu(index);
    }

    /**
     * Get CI card state
     * 
     * @return EnumCardState card state
     * @throws TvCommonException
     */
    public EnumCardState getCardState() throws TvCommonException
    {
        return ciMgr.getCardState();
    }

    /**
     * Is CI Menu On
     * 
     * @return boolean return CI menu on or not
     * @throws TvCommonException
     */
    public boolean isCiMenuOn() throws TvCommonException
    {
        return ciMgr.isCiMenuOn();
    }

    /**
     * Get credential valid date range
     * 
     * @return CredentialValidDateRange credential valid date range
     * @throws TvCommonException
     */
    public CredentialValidDateRange getCiCredentialValidRange()
        throws TvCommonException
    {
        return ciMgr.getCiCredentialValidRange();
    }

    /**
     * set Debug Mode
     * 
     * @return none
     * @throws TvCommonException
     */
    public void setDebugMode(boolean mode) throws TvCommonException
    {
        ciMgr.setDebugMode(mode);
    }

    /**
     * Register setOnCiEventListener(OnCiEventListener listener), your listener
     * will be triggered when the events posted from native code.
     * 
     * @param listener OnCiEventListener
     */
    public void setOnCiEventListener(OnCiEventListener listener)
    {
        ciMgr.setOnCiEventListener(listener);
    }
}