#!/bin/sh

PROJECT_DIR=~/circleci-caver-java-android
cd $POJECT_DIR

CORE_DIR="$PROJECT_DIR/core/src/test/java/com/klaytn/caver"
CORE_ANDROID_DIR="$PROJECT_DIR/core_android/src/androidTest/java/com/klaytn/caver/core_android"

mkdir -p $PROJECT_DIR/src/androidTest/java/com/klaytn/caver/core_android

cp -r $CORE_DIR/base $CORE_ANDROID_DIR/
cp -r $CORE_DIR/common $CORE_ANDROID_DIR/

find  $CORE_ANDROID_DIR -type f -name '*.java' | xargs sed -i 's/package com.klaytn.caver/package com.klaytn.caver.core_android/g'
find  $CORE_ANDROID_DIR -type f -name '*.java' | xargs sed -i 's/import static com.klaytn.caver/import static com.klaytn.caver.core_android/g'
find  $CORE_ANDROID_DIR -type f -name '*.java' | xargs sed -i 's/import com.klaytn.caver.base/import com.klaytn.caver.core_android.base/g'
find  $CORE_ANDROID_DIR -type f -name '*.java' | xargs sed -i 's/Caver.DEFAULT_URL/Caver.ANDROID_DEFAULT_URL/g'
find  $CORE_ANDROID_DIR -type f -name '*.java' | xargs sed -i 's/localhost/10.0.2.2/g'