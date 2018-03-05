package com.hk.lockdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.hk.lockdemo.config.SharedPreferencesFinal;
import com.hk.lockdemo.utils.SPUtils;
import com.hk.lockdemo.widget.FingerprintPasswordView;

/**
 * Created by Administrator on 2018/3/5.
 */

public class AddFingerprintLockActivity extends AppCompatActivity {

    private SPUtils spUtils;

    //指纹相关操作
    private CancellationSignal cancellationSignal;
    private FingerprintManagerCompat managerCompat;

    private Context mContext;

    private FingerprintPasswordView fpvImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fingerprint_lock);
        mContext=this;
        spUtils=new SPUtils(mContext, SharedPreferencesFinal.DEMO);
        fpvImg=findViewById(R.id.fpv_img);
        intoFingerPrint();
    }

    private void intoFingerPrint(){
        managerCompat = FingerprintManagerCompat.from(mContext);
        cancellationSignal  = new CancellationSignal(); //必须重新实例化，否则cancel 过一次就不能再使用了
        managerCompat.authenticate(null,0,cancellationSignal,new FingerprintManagerCompat.AuthenticationCallback(){
            // 当出现错误的时候回调此函数，比如多次尝试都失败了的时候，errString是错误信息，比如华为的提示就是：尝试次数过多，请稍后再试。
            @Override
            public void onAuthenticationError(int errMsgId, CharSequence errString) {
                switch (errMsgId){
                    case 7:
                        showToast("当前尝试次数过多，请稍后再试！！");
                        finish();
                        break;
                }
            }

            // 当指纹验证失败的时候会回调此函数，失败之后允许多次尝试，失败次数过多会停止响应一段时间然后再停止sensor的工作
            @Override
            public void onAuthenticationFailed() {
                showToast("验证失败");
                fpvImg.setStatus(3);
                Thread thread=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        handler.sendMessage(handler.obtainMessage());
                    }
                });
                thread.start();

            }

            @Override
            public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {

            }

            // 当验证的指纹成功时会回调此函数，然后不再监听指纹sensor
            @Override
            public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
               showToast("验证成功");
                fpvImg.setStatus(2);
                spUtils.putBoolean(SharedPreferencesFinal.FP_STATUS,true);
            }
        },null);
    }

    private void showToast(String msg){
        Toast.makeText(AddFingerprintLockActivity.this,msg,Toast.LENGTH_LONG).show();
    }

    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            fpvImg.setStatus(1);
        }
    };

}
