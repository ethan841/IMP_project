//
// Created by Seung Gyun Lee on 2020/06/28.
//

#include <unistd.h>
#include <fcntl.h>
#include <assert.h>
#include <jni.h>
#include "com_example_imp_snakegame_Led_LedJNI.h"

JNIEXPORT void Java_com_example_imp_1snakegame_Led_LedJNI_on
  (JNIEnv * env, jobject obj, jchar data){
	int fd;

	fd = open("/dev/fpga_led", O_WRONLY);
	assert(fd != 0);

	write(fd, &data, 1);
	close(fd);
}