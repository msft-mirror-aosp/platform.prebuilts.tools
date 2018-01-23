LOCAL_PATH:= $(call my-dir)

###########################################

# trove prebuilt. Module stem is chosen so it can be used as a static library.

include $(CLEAR_VARS)

LOCAL_MODULE := trove-prebuilt
LOCAL_MODULE_TAGS := optional
LOCAL_SRC_FILES := repository/net/sf/trove4j/trove4j/1.1/trove4j-1.1.jar
LOCAL_IS_HOST_MODULE := true
LOCAL_MODULE_CLASS := JAVA_LIBRARIES
LOCAL_BUILT_MODULE_STEM := javalib.jar
LOCAL_MODULE_SUFFIX := $(COMMON_JAVA_PACKAGE_SUFFIX)

include $(BUILD_PREBUILT)

###########################################
prebuilts := \
    asm-commons-prebuilt-jar:repository/org/ow2/asm/asm-commons/5.0.1/asm-commons-5.0.1.jar \
    asm-tree-prebuilt-jar:repository/org/ow2/asm/asm-tree/5.0.1/asm-tree-5.0.1.jar \
    asm-prebuilt-jar:repository/org/ow2/asm/asm/5.0.1/asm-5.0.1.jar \
    byte-buddy-prebuilt-jar:repository/net/bytebuddy/byte-buddy/1.6.5/byte-buddy-1.6.5.jar \
    mockito2-prebuilt-jar:repository/org/mockito/mockito-core/2.7.6/mockito-core-2.7.6.jar \
    objenesis-prebuilt-jar:repository/org/objenesis/objenesis/2.5/objenesis-2.5.jar \
    squareup-haha-prebuilt:repository/com/squareup/haha/haha/2.0.2/haha-2.0.2.jar \
    truth-prebuilt-jar:repository/com/google/truth/truth/0.28/truth-0.28.jar

define define-prebuilt
  $(eval tw := $(subst :, ,$(strip $(1)))) \
  $(eval include $(CLEAR_VARS)) \
  $(eval LOCAL_MODULE := $(word 1,$(tw))) \
  $(eval LOCAL_MODULE_TAGS := optional) \
  $(eval LOCAL_MODULE_CLASS := JAVA_LIBRARIES) \
  $(eval LOCAL_SRC_FILES := $(word 2,$(tw))) \
  $(eval LOCAL_UNINSTALLABLE_MODULE := true) \
  $(eval LOCAL_SDK_VERSION := current) \
  $(eval include $(BUILD_PREBUILT))
endef

$(foreach p,$(prebuilts),\
  $(call define-prebuilt,$(p)))

prebuilts :=

###########################################
# org.mockito prebuilt for Robolectric
###########################################

include $(CLEAR_VARS)

LOCAL_MODULE := mockito-robolectric-prebuilt

LOCAL_STATIC_JAVA_LIBRARIES := \
    byte-buddy-prebuilt-jar \
    mockito2-prebuilt-jar \
    objenesis-prebuilt-jar

LOCAL_SDK_VERSION := current
include $(BUILD_STATIC_JAVA_LIBRARY)

###########################################
# com.google.truth prebuilt
###########################################

include $(CLEAR_VARS)

LOCAL_MODULE := truth-prebuilt

LOCAL_STATIC_JAVA_LIBRARIES := \
    truth-prebuilt-jar \
    guava

LOCAL_SDK_VERSION := current
include $(BUILD_STATIC_JAVA_LIBRARY)

include $(CLEAR_VARS)

LOCAL_PREBUILT_JAVA_LIBRARIES := \
    truth-prebuilt-host-jar:repository/com/google/truth/truth/0.28/truth-0.28.jar

include $(BUILD_HOST_PREBUILT)

include $(CLEAR_VARS)

LOCAL_MODULE := truth-host-prebuilt

LOCAL_STATIC_JAVA_LIBRARIES := \
    truth-prebuilt-host-jar \
    guavalib

include $(BUILD_HOST_JAVA_LIBRARY)

