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
package com.konka.kkimplements.tv.mstar;import android.os.Bundle;import android.os.Handler;import android.os.Message;import android.util.Log;import com.konka.kkinterface.tv.CommonDesk;import com.konka.kkinterface.tv.CommonDesk.EnumDeskEvent;import com.tvos.common.vo.TvOsType.EnumInputSource;import com.tvos.dtv.common.DtvPlayer;import com.tvos.dtv.vo.DtvEventScan;public class DeskDtvPlayerEventListener implements DtvPlayer.OnDtvPlayerEventListener{	private Handler m_handler = null;	static private DeskDtvPlayerEventListener dtvEventListener = null;	public DeskDtvPlayerEventListener()	{		m_handler = null;	}	public void attachHandler(Handler handler)	{		m_handler = handler;	}	public void releaseHandler()	{		m_handler = null;	}	static DeskDtvPlayerEventListener getInstance()	{		if (dtvEventListener == null)		{			dtvEventListener = new DeskDtvPlayerEventListener();		}		return dtvEventListener;	}	@Override	public boolean onDtvChannelNameReady(int what)	{		// TODO Auto-generated method stub		Log.d("TvApp", "onDtvChannelNameReady");		if (m_handler == null)		{			return false;		}		else		{			Bundle b = new Bundle();			Message msg = m_handler.obtainMessage();			msg.what = CommonDesk.Cmd_SourceInfo;			b.putInt("CmdIndex", EnumDeskEvent.EV_DTV_CHANNELNAME_READY.ordinal());			m_handler.sendMessage(msg);		}		return true;	}	@Override	public boolean onDtvAutoTuningScanInfo(int what, DtvEventScan extra)	{		Log.d("TvApp", "onDtvAutoTuningScanInfo");		if (m_handler == null)		{			return false;		}		else		{			Bundle b = new Bundle();			Message msg = m_handler.obtainMessage();			msg.what = EnumInputSource.E_INPUT_SOURCE_DTV.ordinal();			DtvEventScan dtv_scan_info = extra;			b.putInt("dtvSrvCount", (int) dtv_scan_info.dtvSrvCount);			b.putInt("radioSrvCount", (int) dtv_scan_info.radioSrvCount);			b.putInt("dataSrvCount", (int) dtv_scan_info.dataSrvCount);			b.putInt("percent", (int) dtv_scan_info.scanPercentageNum);			b.putInt("quality", (int) dtv_scan_info.signalQuality);			b.putInt("strength", (int) dtv_scan_info.signalStrength);			b.putInt("scanstatus", (int) dtv_scan_info.scanStatus);
			b.putInt("curFre", dtv_scan_info.currFrequency);			msg.setData(b);			m_handler.sendMessage(msg);			Log.d("TvApp", "scanstatus" + dtv_scan_info.scanStatus);			Log.d("TvApp", "dtvSrvCount" + dtv_scan_info.dtvSrvCount);			Log.d("TvApp", "quality" + dtv_scan_info.signalQuality);			Log.d("TvApp", "strength" + dtv_scan_info.signalStrength);		}		// TODO Auto-generated method stub		return true;	}	@Override	public boolean onDtvProgramInfoReady(int what)	{		// TODO Auto-generated method stub		Log.d("TvApp", "onDtvProgramInfoReady");		if (m_handler == null)		{			return false;		}		else		{			Bundle b = new Bundle();			Message msg = m_handler.obtainMessage();			msg.what = CommonDesk.Cmd_SourceInfo;			b.putInt("CmdIndex", (int) (EnumDeskEvent.EV_DTV_PROGRAM_INFO_READY.ordinal()));			msg.setData(b);			m_handler.sendMessage(msg);		}		return true;	}	@Override	public boolean onCiLoadCredentialFail(int what)	{		// TODO Auto-generated method stub		return false;	}	@Override	public boolean onEpgTimerSimulcast(int what, int arg1)	{		// TODO Auto-generated method stub		return false;	}	@Override	public boolean onHbbtvStatusMode(int what, boolean arg1)	{		// TODO Auto-generated method stub		return false;	}	@Override	public boolean onMheg5StatusMode(int what, int arg1)	{		// TODO Auto-generated method stub		return false;	}	@Override	public boolean onMheg5ReturnKey(int what, int arg1)	{		// TODO Auto-generated method stub		return false;	}	@Override	public boolean onOadHandler(int what, int arg1, int arg2)	{		// TODO Auto-generated method stub		return false;	}	@Override	public boolean onOadDownload(int what, int arg1)	{		// TODO Auto-generated method stub		return false;	}	@Override	public boolean onDtvAutoUpdateScan(int what)	{		// TODO Auto-generated method stub		return false;	}	@Override	public boolean onTsChange(int what)	{		// TODO Auto-generated method stub		return false;	}	@Override	public boolean onPopupScanDialogLossSignal(int what)	{		// TODO Auto-generated method stub		return false;	}	@Override	public boolean onPopupScanDialogNewMultiplex(int what)	{		// TODO Auto-generated method stub		return false;	}	@Override	public boolean onPopupScanDialogFrequencyChange(int what)	{		// TODO Auto-generated method stub		return false;	}	@Override	public boolean onRctPresence(int what)	{		// TODO Auto-generated method stub		return false;	}	@Override	public boolean onChangeTtxStatus(int what, boolean arg1)	{		// TODO Auto-generated method stub		return false;	}	@Override	public boolean onDtvPriComponentMissing(int what)	{		// TODO Auto-generated method stub		return false;	}	@Override	public boolean onAudioModeChange(int what, boolean arg1)	{		// TODO Auto-generated method stub		return false;	}	@Override	public boolean onMheg5EventHandler(int what, int arg1)	{		// TODO Auto-generated method stub		return false;	}	@Override	public boolean onOadTimeout(int what, int arg1)	{		// TODO Auto-generated method stub		return false;	}	@Override	public boolean onGingaStatusMode(int what, boolean arg1)	{		// TODO Auto-generated method stub		return false;	}
	@Override
    public boolean onSignalUnLock(int what)
    {
	    // TODO Auto-generated method stub
		Log.d("TvApp", "onSignalUnLock in DeskDtvPlayerEventListener");
		if (m_handler == null)
		{
			return false;
		}
		else
		{
			Message msg = m_handler.obtainMessage();
			msg.what = EnumDeskEvent.EV_SIGNAL_UNLOCK.ordinal();
			Bundle b = new Bundle();
			b.putString("MsgSource", "DeskDtvPlayerEventListener");
			msg.setData(b);
			m_handler.sendMessage(msg);
			
		}
	    return false;
    }
	@Override
    public boolean onSignalLock(int what)
	{
		// TODO Auto-generated method stub
		Log.d("TvApp", "onSignalLock in DeskDtvPlayerEventListener");
		if (m_handler == null)
		{
			return false;
		}
		else
		{
			Message msg = m_handler.obtainMessage();
			msg.what = EnumDeskEvent.EV_SIGNAL_LOCK.ordinal();
			Bundle b = new Bundle();
			b.putString("MsgSource", "DeskDtvPlayerEventListener");
			msg.setData(b);
			m_handler.sendMessage(msg);
		}
		return false;
	}}