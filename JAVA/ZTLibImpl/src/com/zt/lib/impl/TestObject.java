package com.zt.lib.impl;

import com.zt.lib.annotations.TargetName;
import com.zt.lib.config.BaseConfigData;

public class TestObject implements BaseConfigData {
	@TargetName("pInt")
	public int publicInt = 0;
	@TargetName("pBoolean")
	public boolean publicBoolean = true;
	@TargetName("pString")
	public String publicString = "publicString";
	@TargetName("pStringArray")
	public String[] publicStringArray = {"a", "b", "c"};
}
