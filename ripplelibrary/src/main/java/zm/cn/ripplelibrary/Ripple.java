package zm.cn.ripplelibrary;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.View;

import java.util.Random;

import custom.ripple.App;

/**
 * Created by Administrator on 2017/1/10.
 */
public class Ripple {

    public static final int SINGLE_COLOR = 0;
    public static final int MULTI_COLOR = 1;
    private static Random random;
    private static Rect rect;
    private static RippleView rippleView;
    private static RippleListener rippleListener;
    private static Context app;
    private static int singleColor;

    private Ripple() {
    }

    public static Random getRandom() {
        if (random == null) {
            random = new Random();
        }
        return random;
    }

    public static Rect getRect() {
        if (rect == null) {
            rect = new Rect();
        }
        return rect;
    }

    public static RippleView getRippleView() {
        if (rippleView == null) {
            rippleView = new RippleView(app);
        }
        return rippleView;
    }

    public static int getRandomColor() {
        if (singleColor == SINGLE_COLOR) {
            return Color.DKGRAY;
        } else if (singleColor == MULTI_COLOR) {
            return Color.rgb(getRandom().nextInt(256), getRandom().nextInt(256), getRandom().nextInt(256));
        } else {
            return singleColor;
        }
    }

    public static RippleListener getRippleListener() {
        if (rippleListener == null) {
            rippleListener = new RippleListener();
        }
        return rippleListener;
    }


    public static void setRipples(View... views) {
        if (rippleListener == null) {
            rippleListener = getRippleListener();
        }
        for (View view : views) {
            if (view == null) continue;
            view.setOnTouchListener(rippleListener);
        }
    }

    public static void init(Context app, int singleColor) {
        Ripple.app = app;
        Ripple.singleColor = singleColor;
    }
}
