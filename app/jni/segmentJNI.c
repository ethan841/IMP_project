#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <assert.h>
#include <jni.h>
#include "com_example_imp_snakegame_SegmentJNI_SegmentJNI.h"

static int fd;

JNIEXPORT void Java_com_example_imp_1snakegame_SegmentJNI_SegmentJNI_open
  (JNIEnv * env, jobject obj){
	fd = open("/dev/fpga_segment", O_WRONLY);
	assert(fd != -1);
}

JNIEXPORT void Java_com_example_imp_1snakegame_SegmentJNI_SegmentJNI_print
  (JNIEnv * env, jobject obj, jint num){
	char buf[7];
	sprintf(buf, "%06d", num);
	write(fd, buf, 6);
}


