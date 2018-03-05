package com.hk.lockdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.hk.lockdemo.config.SharedPreferencesFinal;
import com.hk.lockdemo.utils.SPUtils;
import com.hk.lockdemo.widget.GestureLockLayout;

import java.util.List;

/**
 * Created by Administrator on 2018/3/5.
 */

public class AddGestureLockActivity extends AppCompatActivity {

    private GestureLockLayout gll_lock_view;
    private SPUtils spUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gesture_lock);
        gll_lock_view=findViewById(R.id.gll_lock_view);

        spUtils=new SPUtils(this, SharedPreferencesFinal.DEMO);

        gll_lock_view.setMinCount(4);//最少连接四个点
        gll_lock_view.setDotCount(3);//设置手势解锁view 每行每列点的个数
        gll_lock_view.setMode(GestureLockLayout.RESET_MODE);//设置手势解锁view 模式为重置密码模式
        gll_lock_view.setOnLockResetListener(new GestureLockLayout.OnLockResetListener() {
            @Override
            public void onConnectCountUnmatched(int connectCount, int minCount) {
                //连接数小于最小连接数时调用
                showToast("最少连接" + minCount + "个点,请重新输入");
            }

            @Override
            public void onFirstPasswordFinished(List<Integer> answerList) {
                //第一次绘制手势成功时调用
                showToast("请再次绘制解锁图案");
            }

            @Override
            public void onSetPasswordFinished(boolean isMatched, List<Integer> answerList) {
                //第二次密码绘制成功时调用
                if (isMatched) {
                    //两次答案一致，保存
                    //do something
                    showToast("手势密码绘制成功");
                    spUtils.putBoolean(SharedPreferencesFinal.GP_STATUS, true);
                    String pwd="";
                    for (int i=0;i<answerList.size();i++){
                        pwd+=answerList.get(i);
                    }
                    spUtils.putString(SharedPreferencesFinal.GP_PWD,pwd);

                } else {
                    showToast("两次图案不同，请重新绘制");
                }
            }
        });


    }

    private void showToast(String msg){
        Toast.makeText(AddGestureLockActivity.this,msg,Toast.LENGTH_LONG).show();
    }

    /**
     * 验证的代码如下：（快下班了，不写了。。）
     */

//    gll_lock_view.setOnLockVerifyListener(new GestureLockLayout.OnLockVerifyListener() {
//        @Override
//        public void onGestureSelected(int id) {
//            //每选中一个点时调用
//        }
//
//        @Override
//        public void onGestureFinished(boolean isMatched) {
//            //绘制手势解锁完成时调用
//            if (isMatched) {
//                //密码匹配
//                tvPrompt.setText("验证成功");
//                tvPrompt.setTextColor(Color.parseColor("#3CBD23"));
//                spUtils.putBoolean(SharedPreferencesFinal.GP_STATUS,false);
//                closeActivity();
//            } else {
//                //不匹配
//                if (gllLockView.getTryTimes() < 4 && gllLockView.getTryTimes() > 0) {
//                    tvPrompt.setText("还有" + gllLockView.getTryTimes() + "次机会");
//                } else if (gllLockView.getTryTimes() == 0) {
//                    showToast("跳转到登录界面");
//                    tvPrompt.setText("");
//                    resetLock();
//                } else {
//                    tvPrompt.setText("手势错误，请重新绘制");
//                }
//                shakeAnimation();
//                tvPrompt.setTextColor(Color.parseColor("#FF5E4C"));
//                resetGesture();
//            }
//        }
//
//        @Override
//        public void onGestureTryTimesBoundary() {
//            //超出最大尝试次数时调用
//            gll_lock_view.setTouchable(false);
//        }
//    });



}
