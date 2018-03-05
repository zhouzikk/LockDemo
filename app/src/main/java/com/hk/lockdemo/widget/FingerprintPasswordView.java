package com.hk.lockdemo.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.hk.lockdemo.R;

/**
 * Created by Administrator on 2018/2/27.
 */

public class FingerprintPasswordView extends View {

    private Bitmap mScanningLineBitmap;
    private Bitmap mFrameBitmap;
//    private Bitmap mTopMaskBitmap;

    private Drawable mFrameDrawable;
    private Drawable mTopMaskDrawable;
    private Drawable mSuccessDrawable;
    private Drawable mFailureDrawable;

    private Rect mScanLineRect;
    private Rect mContentRect;
    private Rect mDestRect;

    private int mWholeWidth;
    private int mWholeHeight;

    private int mScannedPercentHeight = 0;

    private int status=1;


    public FingerprintPasswordView(Context context) {
        this(context,null);
    }

    public FingerprintPasswordView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FingerprintPasswordView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBitmap();
    }

    private void initBitmap(){

        mFrameDrawable = ContextCompat.getDrawable(getContext(), R.mipmap.fingerprint_cipher_huise);
        mTopMaskDrawable = ContextCompat.getDrawable(getContext(), R.mipmap.fingerprint_cipher_green);
        mSuccessDrawable= ContextCompat.getDrawable(getContext(),R.mipmap.fingerprint_cipher_chenggong);
        mFailureDrawable= ContextCompat.getDrawable(getContext(),R.mipmap.fingerprint_cipher_shibai);

        mFrameBitmap = ((BitmapDrawable) ContextCompat.getDrawable(getContext(),  R.mipmap.fingerprint_cipher_huise)).getBitmap();

        mScanningLineBitmap = ((BitmapDrawable) ContextCompat.getDrawable(getContext(), R.mipmap.fingerprint_cipherhuise_xiantiao)).getBitmap();

        mScanLineRect = new Rect(0, 0,mScanningLineBitmap.getWidth(), mScanningLineBitmap.getHeight());


        mDestRect = new Rect();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (status==1){
            mFrameDrawable.setBounds(mContentRect);
            mFrameDrawable.draw(canvas);


            mTopMaskDrawable.setBounds(mContentRect);
            canvas.save();

            int top = mScannedPercentHeight;
            canvas.clipRect(0, 0, mWholeWidth, top);
            mTopMaskDrawable.draw(canvas);
            canvas.restore();


            mDestRect.set(0, top-mScanningLineBitmap.getHeight(), mWholeWidth, top);
            canvas.drawBitmap(mScanningLineBitmap, mScanLineRect, mDestRect, null);

            if (mScannedPercentHeight >= mWholeHeight-(mWholeHeight-mFrameBitmap.getHeight())/2) {
                mScannedPercentHeight = (mWholeHeight-mFrameBitmap.getHeight())/2;
            }

            mScannedPercentHeight += 3;

            invalidate();
        }else if (status==2){
            mSuccessDrawable.setBounds(mContentRect);
            mSuccessDrawable.draw(canvas);
        }else{
            mFailureDrawable.setBounds(mContentRect);
            mFailureDrawable.draw(canvas);
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWholeWidth = getMeasuredWidth();
        mWholeHeight = getMeasuredHeight();

        int width=(mWholeWidth-mFrameBitmap.getWidth())/2;
        int height=(mWholeHeight-mFrameBitmap.getHeight())/2;

        mContentRect=new Rect(width,height,mFrameBitmap.getWidth()+width,mFrameBitmap.getHeight()+height);
        mScannedPercentHeight = (mWholeHeight-mFrameBitmap.getHeight())/2;
    }

    /**
     *
     * @param i 1=验证状态 2=成功 3=失败
     */
    public void setStatus(int i){
        this.status=i;
        invalidate();
    }

}
