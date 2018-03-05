package com.hk.lockdemo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.hk.lockdemo.config.SharedPreferencesFinal;
import com.hk.lockdemo.utils.FingerprintUtils;
import com.hk.lockdemo.utils.SPUtils;

public class MainActivity extends AppCompatActivity {

    private SPUtils spUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spUtils=new SPUtils(this, SharedPreferencesFinal.DEMO);
    }

    public void btn1(View view) {
        startActivity(new Intent(this,AddGestureLockActivity.class));
    }

    public void btn2(View view) {

        if (!FingerprintUtils.isFingerPwd()) {
            showToast("当前设备不支持指纹验证");
            return;
        }
        if (!FingerprintUtils.isSafeCondition()) {
            showToast("当前设备未处于安全保护状态");
            return;
        }
        if (!FingerprintUtils.isFingerprint()) {
            showToast("当前设备未录入指纹");
            return;
        }
        startActivity(new Intent(this,AddFingerprintLockActivity.class));
    }

    public void btn3(View view) {
    }

    public void btn4(View view) {
        if (spUtils.getBoolean(SharedPreferencesFinal.FP_STATUS))
            startActivity(new Intent(MainActivity.this,VerificationFingerprintLockActivity.class));
        else
            showToast("请先添加指纹。。。。也不能说是添加。。。流程嘛。。。");
    }

    private void showToast(String msg){
        Toast.makeText(MainActivity.this,msg,Toast.LENGTH_LONG).show();
    }

}
