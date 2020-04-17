package edu.fullsail.mgems.cse.tictactoe.vampireorprincess;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;



public class PaintView extends View {
    private static final String DEBUG_TAG = "Multitouch";
    private final int paintColor = Color.BLACK;
    // defines paint and canvas
    private Paint drawPaint;
    // stores next circle
    private Path path = new Path();
    private int mActivePointerId;
    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setupPaint();

    }



    public PaintView(Context context) {
        super(context);
    }




    private void setupPaint() {
        // Setup paint with color and stroke styles
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(1000);
        drawPaint.setStyle(Paint.Style.FILL);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, drawPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Get the pointer ID
        mActivePointerId = event.getPointerId(0);

        if (event.getPointerCount() > 1) {

            Log.d(DEBUG_TAG,"Multitouch event");
            float pointX = event.getX(mActivePointerId);
            float pointY = event.getY(mActivePointerId);

        }
        float pointX = event.getX();
        float pointY = event.getY();
        // Checks for the event that occurs
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(pointX, pointY);
                return true;
            case MotionEvent.ACTION_UP:
                path.moveTo(pointX, pointY);
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(pointX, pointY);
                break;
            default:
                return false;
        }
        // Force a view to draw again
        postInvalidate();
        return true;
    }
}





