package zm.cn.ripplelibrary;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;


/**
 * Created by Administrator on 2017/1/4.
 */


public class RippleView extends View {

    private float mDownX;
    private float mDownY;
    private float mAlphaFactor;
    private float mDensity;
    private float mRadius;
    private float mMaxRadius;

    private int mRippleColor;

    private RadialGradient mRadialGradient;
    private Paint mPaint;
    private ObjectAnimator mRadiusAnimator;
    private View view;


    public RippleView(Context context) {
        this(context, null);
        init();
    }

    public RippleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public RippleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private int dp(int dp) {
        return (int) (dp * mDensity + 0.5f);
    }

    public void setNextView(View view) {
        this.view = view;
    }

    public void init() {
        mDensity = getContext().getResources().getDisplayMetrics().density;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAlpha(100);

    }

    public void setRippleColor(int rippleColor, float alphaFactor) {
        mRippleColor = rippleColor;
        mAlphaFactor = alphaFactor;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mMaxRadius = (float) Math.sqrt(w * w + h * h);
    }


    @Override
    public boolean onTouchEvent(final MotionEvent event) {

        setRippleColor(Ripple.getRandomColor(), 0.2f);

        mDownX = event.getX();
        mDownY = event.getY();

        final float tempRadius = (float) Math.sqrt(mDownX * mDownX + mDownY * mDownY);
        final float targetRadius = Math.max(tempRadius, mMaxRadius);

        mRadiusAnimator = ObjectAnimator.ofFloat(this, "radius", dp(50), targetRadius);
        mRadiusAnimator.setDuration(400);
        mRadiusAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mRadiusAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                view.performClick();
                ((ViewGroup) view.getParent()).getOverlay().clear();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        mRadiusAnimator.start();
        return false;
    }


    public int adjustAlpha(int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    public void setRadius(final float radius) {

        mRadius = radius;
        if (mRadius > 0) {
            mRadialGradient = new RadialGradient(mDownX, mDownY, mRadius, adjustAlpha(mRippleColor, mAlphaFactor), mRippleColor, Shader
                    .TileMode.MIRROR);
            mPaint.setShader(mRadialGradient);
        }
        invalidate();
    }


    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        if (isInEditMode()) {
            return;
        }

        canvas.save(Canvas.CLIP_SAVE_FLAG);

        canvas.drawCircle(mDownX, mDownY, mRadius, mPaint);
    }


}