/**
 *  konka event info class
 *  
 */
package com.konka.kkimplements.tv.vo;

public class KonkaEventInfo {
	
	 public int _mStartTime			= 0;
	 public int _mEndTime			= 0;
	 public String _mEventName		= null;
	 public int _mEventId			= 0;
	 public int _mProgNo			= 0;
	 public boolean	_mIsScheduled 	= false;
	 public int _mWhichDay			= 0;
	 public int _mIndex				= 0;
	 public int	_mServiceType		= 5;
	 
	 public KonkaEventInfo()
	 {
		 _mStartTime	= 0;
		 _mEndTime		= 0;
		 _mEventName	= "";
		 _mEventId		= 0;
		 _mProgNo 		= 0;
		 _mIsScheduled 	= false;
		 _mWhichDay 	= 0;
		 _mIndex		= 0;
		 _mServiceType 	= 5;
	 }
}
