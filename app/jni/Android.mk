LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := hwset

LOCAL_SRC_FILES := ledJNI.c piazoJNI.c textlcdJNI.c segmentJNI.c

include $(BUILD_SHARED_LIBRARY)
