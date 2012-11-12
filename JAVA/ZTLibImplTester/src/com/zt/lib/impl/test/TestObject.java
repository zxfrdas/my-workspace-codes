package com.zt.lib.impl.test;

import com.zt.lib.annotations.TargetName;
import com.zt.lib.config.BaseConfigData;


public class TestObject extends BaseConfigData {
	@TargetName("pInt")
	public int publicInt = 0;
	@TargetName("pBoolean")
	public boolean publicBoolean = true;
	@TargetName("pString")
	public String publicString = "publicString";
}
