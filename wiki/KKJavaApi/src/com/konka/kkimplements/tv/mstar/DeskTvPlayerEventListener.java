/**
 * MStar Software
 * Copyright (c) 2011 - 2012 MStar Semiconductor, Inc. All rights reserved.
 *
 * All software, firmware and related documentation herein (â€œMStar Softwareï¿? are 
 * intellectual property of MStar Semiconductor, Inc. (â€œMStarï¿? and protected by 
 * law, including, but not limited to, copyright law and international treaties. 
 * Any use, modification, reproduction, retransmission, or republication of all 
 * or part of MStar Software is expressly prohibited, unless prior written 
 * permission has been granted by MStar. 
 *
 * By accessing, browsing and/or using MStar Software, you acknowledge that you 
 * have read, understood, and agree, to be bound by below terms (â€œTermsï¿? and to 
 * comply with all applicable laws and regulations:
 *
 * 1. MStar shall retain any and all right, ownership and interest to MStar 
 * Software and any modification/derivatives thereof.  No right, ownership, 
 * or interest to MStar Software and any modification/derivatives thereof is 
 * transferred to you under Terms.
 *
 * 2. You understand that MStar Software might include, incorporate or be supplied 
 * together with third partyâ€™s software and the use of MStar Software may require 
 * additional licenses from third parties.  Therefore, you hereby agree it is your 
 * sole responsibility to separately obtain any and all third party right and 
 * license necessary for your use of such third partyâ€™s software. 
 *
 * 3. MStar Software and any modification/derivatives thereof shall be deemed as 
 * MStarâ€™s confidential information and you agree to keep MStarâ€™s confidential 
 * information in strictest confidence and not disclose to any third party.  
 *	
 * 4. MStar Software is provided on an â€œAS ISï¿?basis without warranties of any kind. 
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
 * in conjunction with your or your customerâ€™s product (â€œServicesï¿?.  You understand 
 * and agree that, except otherwise agreed by both parties in writing, Services are 
 * provided on an â€œAS ISï¿?basis and the warranty disclaimer set forth in Section 4 
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
package com.konka.kkimplements.tv.mstar;import android.os.Bundle;
import android.os.Handler;import android.os.Message;
import android.util.Log;

import com.konka.kkinterface.tv.CommonDesk.EnumDeskEvent;
import com.tvos.common.TvPlayer;import com.tvos.common.vo.HbbtvEventInfo;public class DeskTvPlayerEventListener implements TvPlayer.OnTvPlayerEventListener{	private Handler m_handler = null;	static private DeskTvPlayerEventListener tvEventListener;	public DeskTvPlayerEventListener()	{		m_handler = null;	}	public void attachHandler(Handler handler)	{		m_handler = handler;	}	public void releaseHandler()	{		m_handler = null;	}	static DeskTvPlayerEventListener getInstance()	{		if (tvEventListener == null)		{			tvEventListener = new DeskTvPlayerEventListener();		}		return tvEventListener;	}	/**	 *  what = EV_SCREEN_SAVER_MODE 	 *  arg1 = EnumScreenMode	 */	@Override	public boolean onScreenSaverMode(int what, int arg1)	{		// TODO Auto-generated method stub		//Log.d("TvApp", "onScreenSaverMode in DeskTvPlayerEventListener");		if (m_handler == null)		{			return false;		}		else		{			if( what != EnumDeskEvent.EV_SCREEN_SAVER_MODE.ordinal())			{				Log.e("TvApp" ,"!!!!onScreenSaverMode msg match error");			}			Message msg = m_handler.obtainMessage();			msg.what = EnumDeskEvent.EV_SCREEN_SAVER_MODE.ordinal();			Bundle b = new Bundle();			b.putInt("Status", arg1);			msg.setData(b);			m_handler.sendMessage(msg);					}		return false;	}	@Override	public boolean onHbbtvUiEvent(int what, HbbtvEventInfo eventInfo)	{		// TODO Auto-generated method stub		return false;	}	@Override	public boolean onPopupDialog(int what, int arg1, int arg2)	{		// TODO Auto-generated method stub		return false;	}	@Override	public boolean onPvrNotifyPlaybackTime(int what, int arg1)	{		// TODO Auto-generated method stub		return false;	}	@Override	public boolean onPvrNotifyPlaybackSpeedChange(int what)	{		// TODO Auto-generated method stub		return false;	}	@Override	public boolean onPvrNotifyRecordTime(int what, int arg1)	{		// TODO Auto-generated method stub		return false;	}	@Override	public boolean onPvrNotifyRecordSize(int what, int arg1)	{		// TODO Auto-generated method stub		return false;	}	@Override	public boolean onPvrNotifyRecordStop(int what)	{		// TODO Auto-generated method stub		return false;	}	@Override	public boolean onPvrNotifyPlaybackStop(int what)	{		// TODO Auto-generated method stub		return false;	}	@Override	public boolean onPvrNotifyPlaybackBegin(int what)	{		// TODO Auto-generated method stub		return false;	}	@Override	public boolean onPvrNotifyTimeShiftOverwritesBefore(int what, int arg1)	{		// TODO Auto-generated method stub		return false;	}	@Override	public boolean onPvrNotifyTimeShiftOverwritesAfter(int what, int arg1)	{		// TODO Auto-generated method stub		return false;	}	@Override	public boolean onPvrNotifyOverRun(int what)	{		// TODO Auto-generated method stub		return false;	}	@Override	public boolean onPvrNotifyUsbRemoved(int what, int arg1)	{		// TODO Auto-generated method stub		return false;	}	@Override	public boolean onPvrNotifyCiPlusProtection(int what)	{		// TODO Auto-generated method stub		return false;	}	@Override	public boolean onPvrNotifyParentalControl(int what, int arg1)	{		// TODO Auto-generated method stub		return false;	}	@Override	public boolean onPvrNotifyAlwaysTimeShiftProgramReady(int what)	{		// TODO Auto-generated method stub		return false;	}		@Override	public boolean onPvrNotifyCiPlusRetentionLimitUpdate(int what, int arg1)	{		// TODO Auto-generated method stub		return false;	}	@Override	public boolean onTvProgramInfoReady(int what)	{		// TODO Auto-generated method stub		return false;	}	@Override    public boolean onPvrNotifyAlwaysTimeShiftProgramNotReady(int what)    {	    // TODO Auto-generated method stub	    return false;    }
	@Override
    public boolean onSignalUnLock(int what)
    {
	    // TODO Auto-generated method stub
		//Log.d("TvApp", "onSignalUnLock in DeskTvPlayerEventListener");		if (m_handler == null)
		{
			return false;
		}
		else
		{
			if( what != EnumDeskEvent.EV_SIGNAL_UNLOCK.ordinal())			{				Log.e("TvApp" ,"!!!!onSignalUnLock msg match error");			}			Message msg = m_handler.obtainMessage();
			msg.what = EnumDeskEvent.EV_SIGNAL_UNLOCK.ordinal();
			Bundle b = new Bundle();
			b.putString("MsgSource", "DeskTvPlayerEventListener");
			msg.setData(b);
			m_handler.sendMessage(msg);
			
		}
	    return false;
    }
	@Override
    public boolean onSignalLock(int what)
	{
		// TODO Auto-generated method stub
		//Log.d("TvApp", "onSignalLock in DeskTvPlayerEventListener");		if (m_handler == null)
		{
			return false;
		}
		else
		{
			if( what != EnumDeskEvent.EV_SIGNAL_LOCK.ordinal())			{				Log.e("TvApp" ,"!!!!onSignalLock msg match error");			}			Message msg = m_handler.obtainMessage();
			msg.what = EnumDeskEvent.EV_SIGNAL_LOCK.ordinal();
			Bundle b = new Bundle();
			b.putString("MsgSource", "DeskTvPlayerEventListener");
			msg.setData(b);
			m_handler.sendMessage(msg);
		}
		return false;
	}}