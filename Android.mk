LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional

LOCAL_PACKAGE_NAME := YYDRobotQrCode
LOCAL_SRC_FILES := $(call all-java-files-under, src)
LOCAL_RESOURCE_DIR := $(LOCAL_PATH)/res
#LOCAL_ASSET_DIR := $(LOCAL_PATH)/assets

LOCAL_STATIC_JAVA_LIBRARIES := \
	android-support-v4 \
	libqrgson \
	libqrcode 
	
LOCAL_PROGUARD_ENABLED := disabled
include $(BUILD_PACKAGE)	

include $(CLEAR_VARS)
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES := \
   libqrgson:libs/gson-2.3.1.jar \
   libqrcode:libs/core-3.0.0.jar 

include $(BUILD_MULTI_PREBUILT)