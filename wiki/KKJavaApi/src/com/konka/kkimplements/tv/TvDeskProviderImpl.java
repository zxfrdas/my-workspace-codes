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
package com.konka.kkimplements.tv;import android.content.Context;

import com.konka.kkimplements.tv.mstar.ChannelDeskImpl;
import com.konka.kkimplements.tv.mstar.CiDeskImpl;
import com.konka.kkimplements.tv.mstar.CommonDeskImpl;
import com.konka.kkimplements.tv.mstar.DataBaseDeskImpl;
import com.konka.kkimplements.tv.mstar.DemoDeskImpl;
import com.konka.kkimplements.tv.mstar.EpgDeskImpl;
import com.konka.kkimplements.tv.mstar.PictureDeskImpl;
import com.konka.kkimplements.tv.mstar.PvrDeskImpl;
import com.konka.kkimplements.tv.mstar.S3DDeskImpl;
import com.konka.kkimplements.tv.mstar.SettingDeskImpl;
import com.konka.kkimplements.tv.mstar.SoundDeskImpl;
import com.konka.kkinterface.tv.ChannelDesk;
import com.konka.kkinterface.tv.CiDesk;
import com.konka.kkinterface.tv.CommonDesk;
import com.konka.kkinterface.tv.DataBaseDesk;
import com.konka.kkinterface.tv.DemoDesk;
import com.konka.kkinterface.tv.EpgDesk;
import com.konka.kkinterface.tv.PictureDesk;
import com.konka.kkinterface.tv.PvrDesk;
import com.konka.kkinterface.tv.S3DDesk;
import com.konka.kkinterface.tv.SettingDesk;
import com.konka.kkinterface.tv.SoundDesk;
import com.konka.kkinterface.tv.TvDeskProvider;

public class TvDeskProviderImpl implements TvDeskProvider
{
	private static TvDeskProvider instance = null;
	CommonDesk comManager = null;
    PictureDesk pictureManager = null;
    DataBaseDesk dataBaseManager = null;
    SettingDesk settingManager = null;
    ChannelDesk channelManager = null;
    SoundDesk soundManager = null;
    S3DDesk s3dManager = null;
    DemoDesk demoManager = null;
    EpgDesk epgManager = null;
    PvrDesk pvrManager = null;
    CiDesk ciManager = null;

    private Context context;
    
    public static TvDeskProvider getInstance(Context context)
	{
		if (instance == null)
		{
			instance = new TvDeskProviderImpl(context);
		}
		return instance;
	}

    private TvDeskProviderImpl(Context context)
    {
    	this.context = context;
    }
    @Override
    public void initTvSrvProvider()
    {
        // TODO Auto-generated method stub
        // pictureService = new PictureServiceImpl();
    }

    @Override
    public CommonDesk getCommonManagerInstance()
    {
        comManager = CommonDeskImpl.getInstance(context);
        return comManager;
    }

    @Override
    public PictureDesk getPictureManagerInstance()
    {
        pictureManager = PictureDeskImpl.getPictureMgrInstance(context);
        return pictureManager;
    }

    @Override
    public DataBaseDesk getDataBaseManagerInstance()
    {
        dataBaseManager = DataBaseDeskImpl.getDataBaseMgrInstance(context);
        return dataBaseManager;
    }

    @Override
    public SettingDesk getSettingManagerInstance()
    {
        settingManager = SettingDeskImpl.getSettingMgrInstance(context);
        return settingManager;
    }

    @Override
    public ChannelDesk getChannelManagerInstance()
    {
        channelManager = ChannelDeskImpl.getChannelMgrInstance(context);
        return channelManager;
    }

    @Override
    public SoundDesk getSoundManagerInstance()
    {
        soundManager = SoundDeskImpl.getSoundMgrInstance(context);
        return soundManager;
    }

    @Override
    public S3DDesk getS3DManagerInstance()
    {
        s3dManager = S3DDeskImpl.getS3DMgrInstance(context);
        return s3dManager;
    }

    @Override
    public DemoDesk getDemoManagerInstance()
    {
        demoManager = DemoDeskImpl.getDemoMgrInstance(context);
        return demoManager;
    }


    @Override
    public EpgDesk getEpgManagerInstance()
    {
        epgManager = EpgDeskImpl.getEpgMgrInstance(context);
        return epgManager;
    }

    @Override
    public PvrDesk getPvrManagerInstance()
    {
        pvrManager = PvrDeskImpl.getPvrMgrInstance(context);
        return pvrManager;
    }

    @Override
    public CiDesk getCiManagerInstance()
    {
        ciManager = CiDeskImpl.getCiMgrInstance(context);
        return ciManager;
    }
}
