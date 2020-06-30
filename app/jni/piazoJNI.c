//
// Created by Seung Gyun Lee on 2020/06/28.
//

#include <unistd.h>
#include <fcntl.h>
#include <assert.h>
#include <jni.h>
#include "com_example_imp_snakegame_Piazo_PiazoJNI.h"

static int fd;
static int ret;
static int value;

JNIEXPORT jint Java_com_example_imp_1snakegame_Piazo_PiazoJNI_write
(JNIEnv * env, jobject obj, jint value){

    int data = value;

    fd = open("/dev/fpga_piezo", O_WRONLY);
    assert(fd != -1);

    ret = write(fd, &data, 1);
    close(fd);

    if(ret == 1) return 0;

    return -1;
}

