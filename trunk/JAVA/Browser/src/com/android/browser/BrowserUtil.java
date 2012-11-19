/*
** Copyright (c) 2010-2011 MStar Semiconductor, Inc.
** All rights reserved.
**
** Unless otherwise stipulated in writing, any and all information contained
** herein regardless in any format shall remain the sole proprietary of
** MStar Semiconductor Inc. and be kept in strict confidence
** ("MStar Confidential Information") by the recipient.
** Any unauthorized act including without limitation unauthorized disclosure,
** copying, use, reproduction, sale, distribution, modification, disassembling,
** reverse engineering and compiling of the contents of MStar Confidential
** Information is unlawful and strictly prohibited. MStar hereby reserves the
** rights to any and all damages, losses, costs and expenses resulting therefrom.
*/

package com.android.browser;

import android.util.Log;
import java.util.List;

/**
 * native tool for browser
 */
public class BrowserUtil {
    //private static final String LOGTAG = "BrowserUtil";
    private boolean mToolLibControlAble = true;

    private native void  nativeMoveCursor(int x, int y);
    private native void  nativeMouseLeftClick();
    private native void  nativeCloseInput();

    public void  moveCursor(int x, int y) {
        Log.v("zb.wu", "moveCursor: x:" + x + "   y:" + y);
        if (mToolLibControlAble)
            nativeMoveCursor(x,y);
    }
    public void  mouseLeftClick() {
        if (mToolLibControlAble)
            nativeMouseLeftClick();
    }
    public void closeInputDevice() {
        if (mToolLibControlAble) {
            nativeCloseInput();
        }
    }
    public BrowserUtil() {
        try {
            System.loadLibrary("jni_browserUtil");
        } catch (UnsatisfiedLinkError ule) {
            Log.e("BROWSERTOOL", "Could not load native library: jni_devicecontrol");
            mToolLibControlAble = false;
        }
    }
}

