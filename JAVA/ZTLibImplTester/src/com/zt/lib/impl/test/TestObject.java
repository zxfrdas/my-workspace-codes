package com.zt.lib.impl.test;

import com.zt.lib.annotations.TargetName;
import com.zt.lib.config.IConfigData;

public class TestObject implements IConfigData {
	@TargetName("pInt")
	public int publicInt = 0;
	@TargetName("pBoolean")
	public boolean publicBoolean = true;
	@TargetName("pString")
	public String publicString = "publicString";
	@TargetName("pStringArray")
	public String[] publicStringArray = {"a", "b", "c"};
}
