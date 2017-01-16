package zm.cn.ripplelibrary;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;


/**
 * Created by Administrator on 2017/1/10.
 */
public class RippleListener implements View.OnTouchListener {


    private static Rect rect = Ripple.getRect();
    private static RippleView rippleView = Ripple.getRippleView();
    private static ViewParent parent;
    private static long eventTime;
    private static long tempTime;


    public RippleListener() {

    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onTouch(final View view, MotionEvent motionEvent) {

        if (motionEvent.getActionMasked() == MotionEvent.ACTION_UP) {
            eventTime = motionEvent.getEventTime();
            if (eventTime - tempTime < 600) {
                return true;
            }
            tempTime = eventTime;

            view.getHitRect(rect);

            if (!rect.contains((int) motionEvent.getX() + rect.left, (int) motionEvent.getY() + rect.top)) {
                return false;
            }

            rippleView.setTop(rect.top);
            rippleView.setBottom(rect.bottom);
            rippleView.setLeft(rect.left);
            rippleView.setRight(rect.right);

            parent = view.getParent();
            rippleView.setNextView(view);


            ((ViewGroup) parent).getOverlay().add(rippleView);

            rippleView.onTouchEvent(motionEvent);
            return true;

        }

        return false;
    }

}
