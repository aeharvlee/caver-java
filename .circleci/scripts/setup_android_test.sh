PROJECT_DIR=~/circleci-caver-java-android
cd $POJECT_DIR

CORE_DIR="$PROJECT_DIR/core/src/test/java/com/klaytn/caver"
CORE_ANDROID_DIR="$PROJECT_DIR/core_android/src/androidTest/java/com/klaytn/caver/core_android"

cd $CORE_ANDROID_DIR
mkdir -p src/androidTest/java/com/klaytn/caver/core_android

cp -r $CORE_DIR/* .
find  . -type f -name '*.java' | grep -v "legacy" | grep -v "ipfs" | xargs sed -i 's/package com.klaytn.caver/package com.klaytn.caver.core_android/g'
find  . -type f -name '*.java' | grep -v "legacy" | grep -v "ipfs" | xargs sed -i 's/import static com.klaytn.caver/import static com.klaytn.caver.core_android/g'
find  . -type f -name '*.java' | grep -v "legacy" | grep -v "ipfs" | xargs sed -i 's/import com.klaytn.caver.base/import com.klaytn.caver.core_android.base/g'
find  . -type f -name '*.java' | grep -v "legacy" | grep -v "ipfs" | xargs sed -i 's/Caver.DEFAULT_URL/Caver.ANDROID_DEFAULT_URL/g'
find  . -type f -name '*.java' | grep -v "legacy" | grep -v "ipfs" | xargs sed -i 's/localhost/10.0.2.2/g'