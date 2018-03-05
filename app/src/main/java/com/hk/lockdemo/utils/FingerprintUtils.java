package com.hk.lockdemo.utils;

import android.app.KeyguardManager;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;

import com.hk.lockdemo.App;


/**
 * Created by Administrator on 2018/2/28.
 */

public class FingerprintUtils {

    //判断设备是否支持指纹验证
    public static boolean isFingerPwd(){
        FingerprintManagerCompat managerCompat = FingerprintManagerCompat.from(App.applicationContext);
        return managerCompat.isHardwareDetected();
    }

    //判断设备当前是否处于安全保护状态
    public static boolean isSafeCondition(){
        KeyguardManager keyguardManager =(KeyguardManager)App.applicationContext.getSystemService(App.applicationContext.KEYGUARD_SERVICE);
        return keyguardManager.isKeyguardSecure();//判断设备是否处于安全保护中   API>=16
    }

    //判断设备中是否录入指纹
    public static boolean isFingerprint(){
        FingerprintManagerCompat managerCompat = FingerprintManagerCompat.from(App.applicationContext);

        return managerCompat.hasEnrolledFingerprints();
    }

}
