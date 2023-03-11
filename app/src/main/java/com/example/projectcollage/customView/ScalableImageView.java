package com.example.projectcollage.customView;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

public class ScalableImageView extends androidx.appcompat.widget.AppCompatImageView {
    private final ScaleGestureDetector scaleGestureDetector;
    private float scaleFactor=1.0f;

    public ScalableImageView(Context context, AttributeSet attrs) {
        super(context,attrs);
        scaleGestureDetector=new ScaleGestureDetector(context, new ScaleGestureDetector.OnScaleGestureListener() {

            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                scaleFactor *=detector.getScaleFactor();
                scaleFactor=Math.max(0.1f, Math.min(scaleFactor, 5.0f));
                setScaleX(scaleFactor);
                setScaleY(scaleFactor);

                return true;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
                return true;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {

            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        return true;
    }
}
