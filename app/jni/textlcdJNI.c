#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <assert.h>
#include <jni.h>

#include "com_example_imp_snakegame_TextLcd_TextLcdJNI.h"

#ifndef TEXTLCD_H_
#define TEXTLCD_H_

#define TEXTLCD_ON 		1
#define TEXTLCD_OFF 	2
#define TEXTLCD_INIT 	3
#define TEXTLCD_CLEAR		4

#define TEXTLCD_LINE1		5
#define TEXTLCD_LINE2		6

#endif

static int fd;

JNIEXPORT void Java_com_example_imp_1snakegame_TextLcd_TextLcdJNI_on
  (JNIEnv * env, jobject obj){
	if (fd == 0)
		fd = open("/dev/fpga_textlcd", O_WRONLY);
	assert(fd != 0);

	ioctl(fd, TEXTLCD_ON);
}

JNIEXPORT void Java_com_example_imp_1snakegame_TextLcd_TextLcdJNI_clear
  (JNIEnv * env, jobject obj){
	//if (fd )
		ioctl(fd, TEXTLCD_CLEAR);
}

JNIEXPORT void Java_com_example_imp_1snakegame_TextLcd_TextLcdJNI_print1Line
  (JNIEnv * env, jobject obj, jstring msg){
	const char *str;

	if (fd )
	{
		str = (*env)->GetStringUTFChars(env, msg, 0);
		ioctl(fd, TEXTLCD_LINE1);
		write(fd, str, strlen(str));
		(*env)->ReleaseStringUTFChars(env, msg, str);
	}

}

JNIEXPORT void Java_com_example_imp_1snakegame_TextLcd_TextLcdJNI_print2Line
  (JNIEnv * env, jobject obj, jstring msg){
	const char *str;

	if (fd )
	{
		str = (*env)->GetStringUTFChars(env, msg, 0);
		ioctl(fd, TEXTLCD_LINE2);
		write(fd, str, strlen(str));
		(*env)->ReleaseStringUTFChars(env, msg, str);
	}
}
