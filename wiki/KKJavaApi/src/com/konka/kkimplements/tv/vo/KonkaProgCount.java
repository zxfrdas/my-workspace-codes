/**
 * konka  program count class
 * 
 */
package com.konka.kkimplements.tv.vo;

import com.tvos.common.vo.TvOsType.EnumProgramCountType;
import com.tvos.common.vo.TvOsType.EnumServiceType;

public class KonkaProgCount {
	
	public int DTVTvProgCount 	    = 0;  //program count DTV TV type only
	public int DTVRadioProgCount 	= 0;  //program count DTV Radio type only
	public int DTVDataProgCount 	= 0;  //program count DTV Data type only
	public int DTVAllProgCount		= 0;  //program count DTV(TV+Radid+Data) type
	
	/**
	 * 
	 * construct
	 */
	public KonkaProgCount()
	{
		DTVTvProgCount	= 0;
		DTVRadioProgCount	= 0;
		DTVDataProgCount	= 0;
		DTVAllProgCount	= 0;
	}
	
	/**
	 * 
	 * construct
	 */
	public KonkaProgCount(int iDtv, int iRadio, int iData, int iDTVAll)
	{
		DTVTvProgCount	= iDtv;
		DTVRadioProgCount	= iRadio;
		DTVDataProgCount	= iData;
		DTVAllProgCount	= iDTVAll;
	}
	
	/**
	 * 
	 * @param eServiceType
	 * 
	 * @return boolean (true or false)
	 */
	public boolean isNoProgInServiceType(EnumProgramCountType eServiceType)
	{
		boolean bIsZero = false;
		
		switch(eServiceType)
		{
		case E_COUNT_DTV_TV:
			{
				bIsZero = (DTVTvProgCount == 0) ? true : false;
			}
			break;
		case E_COUNT_DTV_RADIO:
			{
				bIsZero = (DTVRadioProgCount == 0) ? true : false;
			}
			break;
		case E_COUNT_DTV_DATA:
			{
				bIsZero = (DTVDataProgCount == 0) ? true : false;
			}
			break;
		case E_COUNT_DTV:
		default:
			{
				bIsZero = (DTVAllProgCount == 0) ? true : false;
			}
			break;
		}
		
		return bIsZero;
	}
	
	/**
	 * get program count 
	 * 
	 * @param service type
	 * 
	 * @return int
	 */
	public int getProgCountByServiceType(EnumServiceType eServiceType)
	{
		int iProgCount = 0;
		
		switch (eServiceType)
		{
		case E_SERVICETYPE_DTV:
			{
				iProgCount = this.DTVTvProgCount;
			}
			break;
		case E_SERVICETYPE_RADIO:
			{
				iProgCount = this.DTVRadioProgCount;
			}
			break;
		case E_SERVICETYPE_DATA:
			{
				iProgCount = this.DTVDataProgCount;
			}
			break;
		default:
			{
				iProgCount = 0;
			}
			break;
		}
		return iProgCount;
	}
}
