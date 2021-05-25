#!/bin/sh

PROJECT_DIR=~/circleci-caver-java-android
cd $POJECT_DIR

CORE_DIR=$PROJECT_DIR/core
CORE_TEST_DIR=$PROJECT_DIR/core/src/test
CORE_TEST_CAVER_DIR=$CORE_TEST_DIR/java/com/klaytn/caver

CORE_ANDROID_DIR=$PROJECT_DIR/core_android
CORE_ANDROID_TEST_DIR=$PROJECT_DIR/core_android/src/androidTest
CORE_ANDROID_TEST_CAVER_DIR=$CORE_ANDROID_TEST_DIR/java/com/klaytn/caver/core_android

mkdir -p $CORE_ANDROID_TEST_CAVER_DIR

cp -r $CORE_TEST_CAVER_DIR/base $CORE_ANDROID_TEST_CAVER_DIR/
cp -r $CORE_TEST_CAVER_DIR/common $CORE_ANDROID_TEST_CAVER_DIR/

find  $CORE_ANDROID_TEST_CAVER_DIR -type f -name '*.java' | xargs sed -i 's/package com.klaytn.caver/package com.klaytn.caver.core_android/g'
find  $CORE_ANDROID_TEST_CAVER_DIR -type f -name '*.java' | xargs sed -i 's/import static com.klaytn.caver/import static com.klaytn.caver.core_android/g'
find  $CORE_ANDROID_TEST_CAVER_DIR -type f -name '*.java' | xargs sed -i 's/import com.klaytn.caver.base/import com.klaytn.caver.core_android.base/g'
find  $CORE_ANDROID_TEST_CAVER_DIR -type f -name '*.java' | xargs sed -i 's/Caver.DEFAULT_URL/Caver.ANDROID_DEFAULT_URL/g'
find  $CORE_ANDROID_TEST_CAVER_DIR -type f -name '*.java' | xargs sed -i 's/localhost/10.0.2.2/g'