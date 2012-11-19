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

#include <string.h>
#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <pthread.h>
#include <sys/prctl.h>
#include <fcntl.h>
#include <linux/input.h>
#include <linux/uinput.h>
#include <dirent.h>
#include <cutils/log.h>

/* This is a trivial JNI example where we use a native method
 * to return a new VM String. See the corresponding Java source
 * file located at:
 *
 *   com/android/browser/BrowserActivity.java
 */
#define MYLOGD(...) __android_log_print(ANDROID_LOG_INFO, "zb.wu", __VA_ARGS__)

#ifndef NELEM
    #define NELEM(x) ((int) (sizeof(x) / sizeof((x)[0])))
#endif

const char *EmumouseName = "Virtual Mouse";//"Virtual mouse";
static int mFD = -1;
static int uinp_fd_mouse = -1;

// Setup the mouse device
int setup_mouse_device()
{
    struct uinput_user_dev uinp; // uInput device structure
    int i;
    // Open the input device
    uinp_fd_mouse = open("/dev/uinput", O_WRONLY | O_NDELAY);
    if (uinp_fd_mouse == 0) {
        MYLOGD("Unable to open /dev/uinput\n");
        return -1;
    }
    // Intialize the uInput device to NULL

    memset(&uinp, 0x00, sizeof(uinp));
    strncpy(uinp.name, EmumouseName, strlen(EmumouseName));
    uinp.id.version = 1;
    uinp.id.bustype = BUS_USB;

    // Setup the Mouse device
    ioctl(uinp_fd_mouse, UI_SET_EVBIT, EV_KEY);
    ioctl(uinp_fd_mouse, UI_SET_EVBIT, EV_REL);
    ioctl(uinp_fd_mouse, UI_SET_KEYBIT, BTN_MIDDLE);
    ioctl(uinp_fd_mouse, UI_SET_KEYBIT, BTN_LEFT);
    ioctl(uinp_fd_mouse, UI_SET_KEYBIT, BTN_RIGHT);
    ioctl(uinp_fd_mouse, UI_SET_RELBIT, REL_X);
    ioctl(uinp_fd_mouse, UI_SET_RELBIT, REL_Y);

    // Create input device into input sub-system
    if (write(uinp_fd_mouse, &uinp, sizeof(uinp)) != sizeof(uinp)) {
        MYLOGD("First write returned fail.\n");
        return -1;
    }

    if (ioctl(uinp_fd_mouse, UI_DEV_CREATE)) {
        MYLOGD("ioctl UI_DEV_CREATE returned fail.\n");
        return -1;
    }
    return 1;

}

void destroy_device(int fd)
{
    // Destroy the device
    ioctl(fd, UI_DEV_DESTROY);
    close(fd);
}

int openInput(const char* inputName)
{
    int fd = -1;
    system("su");
    system("chmod 777 /dev/input/*");
    const char *dirname = "/dev/input";
    char devname[PATH_MAX];
    char *filename;
    DIR *dir;
    struct dirent *de;
    dir = opendir(dirname);
    if (dir == NULL)
        return -1;

    strcpy(devname, dirname);
    filename = devname + strlen(devname);
    *filename++ = '/';
    while ((de = readdir(dir))) {
        if (de->d_name[0] == '.' &&
            (de->d_name[1] == '\0' ||
             (de->d_name[1] == '.' && de->d_name[2] == '\0')))
            continue;

        strcpy(filename, de->d_name);
        //MYLOGD("---> %s\n", devname);
        fd = open(devname, O_RDWR);
        if (fd>=0) {
            char name[80];
            if (ioctl(fd, EVIOCGNAME(sizeof(name) - 1), &name) < 1) {
                name[0] = '\0';
            }
            //MYLOGD("open %s  success! device name:%s\n", devname, name);
            if (!strcmp(name, inputName)) {
                MYLOGD("device name = %s\n", devname);
                break;
            } else {
                close(fd);
                fd = -1;
            }
        } else {
            MYLOGD("open %s  fail!\n", devname);
        }
    }
    closedir(dir);
    if (fd<0) {
        MYLOGD("couldn't find '%s' input device\n", inputName);
    }
    return fd;
}

int mouseLeftClick()
{
    if (mFD == -1) {
        mFD = openInput(EmumouseName);
        if (mFD == -1) {
            if (-1 != setup_mouse_device()) {
                mFD = openInput(EmumouseName);
            }
        }
    }
    if (mFD == -1) {
        MYLOGD("can not open device\n");
        return -1;
    }
    struct input_event ievent[10];
    struct timespec now;
    struct timeval tv;
    tv.tv_sec = 0;
    tv.tv_usec = 0;

    clock_gettime(CLOCK_MONOTONIC, &now);
    ievent[0].time.tv_sec = now.tv_sec;
    ievent[0].time.tv_usec = now.tv_nsec / 1000;
    ievent[0].type = EV_MSC;
    ievent[0].code = 4;
    ievent[0].value = 589825;

    ievent[1].time.tv_sec = now.tv_sec;
    ievent[1].time.tv_usec = now.tv_nsec / 1000;
    ievent[1].type = EV_KEY;
    ievent[1].code = 272;
    ievent[1].value = 1;

    ievent[2].time.tv_sec = now.tv_sec;
    ievent[2].time.tv_usec = now.tv_nsec / 1000;
    ievent[2].type = EV_SYN;
    ievent[2].code = 0;
    ievent[2].value = 0;

    write(mFD, &ievent[0], sizeof(ievent[0]));
    write(mFD, &ievent[1], sizeof(ievent[1]));
    write(mFD, &ievent[2], sizeof(ievent[2]));

    clock_gettime(CLOCK_MONOTONIC, &now);
    ievent[0].time.tv_sec = now.tv_sec;
    ievent[0].time.tv_usec = now.tv_nsec / 1000;
    ievent[0].type = EV_MSC;
    ievent[0].code = 4;
    ievent[0].value = 589825;

    ievent[1].time.tv_sec = now.tv_sec;
    ievent[1].time.tv_usec = now.tv_nsec / 1000;
    ievent[1].type = EV_KEY;
    ievent[1].code = 272;
    ievent[1].value = 0;

    ievent[2].time.tv_sec = now.tv_sec;
    ievent[2].time.tv_usec = now.tv_nsec / 1000;
    ievent[2].type = EV_SYN;
    ievent[2].code = 0;
    ievent[2].value = 0;

    write(mFD, &ievent[0], sizeof(ievent[0]));
    write(mFD, &ievent[1], sizeof(ievent[1]));
    write(mFD, &ievent[2], sizeof(ievent[2]));
    return 0;   
}

int mouseMove(int X, int Y)
{
    if (mFD == -1) {
        mFD = openInput(EmumouseName);
        if (mFD == -1) {
            if (-1 != setup_mouse_device()) {
                mFD = openInput(EmumouseName);
            }
        }
    }
    if (mFD == -1) {
        MYLOGD("can not open device\n");
        return -1;
    }

    struct input_event ievent[10];
    struct timespec now;
    struct timeval tv;
    tv.tv_sec = 0;
    tv.tv_usec = 0;

    MYLOGD("move X:%d,Y:%d\n",X,Y);

    clock_gettime(CLOCK_MONOTONIC, &now);
    ievent[0].time.tv_sec = now.tv_sec;
    ievent[0].time.tv_usec = now.tv_nsec / 1000;
    ievent[0].type = EV_REL;
    ievent[0].code = 0;
    ievent[0].value = X;

    ievent[1].time.tv_sec = now.tv_sec;
    ievent[1].time.tv_usec = now.tv_nsec / 1000;
    ievent[1].type = EV_REL;
    ievent[1].code = 1;
    ievent[1].value = Y;

    ievent[2].time.tv_sec = now.tv_sec;
    ievent[2].time.tv_usec = now.tv_nsec / 1000;
    ievent[2].type = EV_SYN;
    ievent[2].code = 0;
    ievent[2].value = 0;

    write(mFD, &ievent[0], sizeof(ievent[0]));
    write(mFD, &ievent[1], sizeof(ievent[1]));
    write(mFD, &ievent[2], sizeof(ievent[2]));

    return 0;
}

static void jni_MouseMove(JNIEnv *env, jobject thiz, jint x, jint y)
{
    MYLOGD("jni_MouseMove receive move X:%d,Y:%d\n",x,y);
    mouseMove(x,y);
}

static void jni_MouseLeftClick(JNIEnv *env, jobject thiz)
{
    mouseLeftClick();
}

static void jni_closeInput(JNIEnv *env, jobject thiz)
{
    if (mFD == -1) {
        return;
    }
    if ( -1 != uinp_fd_mouse ) {
        destroy_device(uinp_fd_mouse);
    }
    close(mFD);
    mFD = -1;
}

static JNINativeMethod BrowserMethods[] = {
    {"nativeMoveCursor" , "(II)V", (void*)jni_MouseMove},
    {"nativeMouseLeftClick" , "()V", (void*)jni_MouseLeftClick},
    {"nativeCloseInput" , "()V", (void*)jni_closeInput}
};

static int registerNativeMethods(JNIEnv* env, const char* className, JNINativeMethod*  Methods, int numMethods) {
    jclass clazz;
    clazz = env->FindClass(className);
    if (clazz == NULL) {
        MYLOGD("Native registration unable to find class '%s'", className);
        return JNI_FALSE;
    }
    if (env->RegisterNatives(clazz, BrowserMethods, numMethods) < 0) {
        MYLOGD("RegisterNatives failed for '%s'", className);
        return JNI_FALSE;
    }
    return JNI_TRUE;
}

int register_browserTool(JNIEnv *env) {
    const char* const ClassPathName = "com/android/browser/BrowserUtil";
    return registerNativeMethods(env, ClassPathName, BrowserMethods,  NELEM(BrowserMethods));
}

// Returns the JNI version on success, -1 on failure.
jint JNI_OnLoad(JavaVM* vm, void* reserved)
{
    JNIEnv* env = NULL;
    jint result = -1;
    if (vm->GetEnv((void**) &env, JNI_VERSION_1_4) != JNI_OK) {
        MYLOGD("ERROR: GetEnv failed");
        goto bail;
    }
    //assert(env != NULL);
    if (!register_browserTool(env)) {
        MYLOGD("ERROR: Test native registration failed");
        goto bail;
    }
    /* success -- return valid version number */
    result = JNI_VERSION_1_4;
    bail:  return result;
}

/*
jstring
Java_com_android_browser_BrowserActivity_nativeMoveCursor( JNIEnv* env,
                                                  jobject thiz, jint x, jint y)
{
     char str[40];
     memset(&str, 0, 40);
     sprintf(&str, "hello111 Mouse move : %d, %d\n", x, y);
     mouseMove(x, y);
     return (*env)->NewStringUTF(env, str);
}

jstring
Java_com_android_browser_BrowserActivity_nativeMouseLeftClick( JNIEnv* env,
                                                  jobject thiz)
{
     char str[40];
     memset(&str, 0, 40);
     sprintf(&str, "hello222 Mouse click \n");
     mouseLeftClick();
     return (*env)->NewStringUTF(env, str);
}
*/
