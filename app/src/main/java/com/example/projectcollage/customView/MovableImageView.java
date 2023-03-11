package com.example.projectcollage.customView;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MovableImageView extends androidx.appcompat.widget.AppCompatImageView {
    private float lastX;
    private float lastY;

    public MovableImageView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX=event.getX();
                lastY=event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX=event.getX()-lastX;
                float deltaY=event.getY()-lastY;
                setX(getX()+deltaX);
                setY(getY()+deltaY);
                lastX=event.getX();
                lastY=event.getY();
                break;

        }
        return true;
    }
}
