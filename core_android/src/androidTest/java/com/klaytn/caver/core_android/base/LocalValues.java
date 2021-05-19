package com.klaytn.caver.core_android.base;

import com.klaytn.caver.Caver;
import com.klaytn.caver.wallet.keyring.SingleKeyring;

public class LocalValues {
    public static final String ANDROID_DEFAULT_URL = "http://10.0.2.2:8551";
    private static Caver caver = new Caver(ANDROID_DEFAULT_URL);
    public static final SingleKeyring KLAY_PROVIDER = caver.wallet.keyring.createFromPrivateKey(
            "871ccee7755bb4247e783110cafa6437f9f593a1eaeebe0efcc1b0852282c3e5"
    );
    public static final int LOCAL_CHAIN_ID = 2019;
    public static final int LOCAL_NETWORK_ID = 2018;
}
