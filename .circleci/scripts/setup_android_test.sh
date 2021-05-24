PROJECT_DIR=~/circleci-caver-java-android
cd $POJECT_DIR

CORE_DIR="$PROJECT_DIR/core/src/test/java/com/klaytn/caver"
CORE_ANDROID_DIR="$PROJECT_DIR/core_android/src/androidTest/java/com/klaytn/caver/core_android"

cd $CORE_ANDROID_DIR

cp -r $CORE_DIR/* .
find  . -type f -name '*.java' | xargs sed -i 's/package com.klaytn.caver/package com.klaytn.caver.core_android./g'
find  . -type f -name '*.java' | xargs sed -i 's/Caver.DEFAULT_URL/Caver.ANDROID_DEFAULT_URL/g'