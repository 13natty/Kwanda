package com.example.f3838284.kwanda;

import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {
    @InjectView(R.id.inner) ImageView inner;
    @InjectView(R.id.outer) ImageView outer;
    private String Tag = MainActivity.class.getSimpleName();
    private int dialerWidth;
    private int dialerHeight;
    Matrix matrix = new Matrix();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        int outerWidth = outer.getDrawable().getIntrinsicWidth();
        Log.d(Tag, "outerWidth "+outerWidth);
        int outerHeight = outer.getDrawable().getIntrinsicHeight();

        inner.setScaleType(ImageView.ScaleType.MATRIX);
        dialerWidth = inner.getDrawable().getIntrinsicWidth();
        dialerHeight = inner.getDrawable().getIntrinsicHeight();

        //inner.setY((outerHeight-dialerHeight)/2);
        Log.d(Tag, "(outerWidth-innerWidth) "+(outerWidth-dialerWidth));
        //inner.setX((outerWidth-dialerWidth)/2);

        inner.setOnTouchListener(new MyOnTouchListener());
    }


    /**
     * Simple implementation of an {@link View.OnTouchListener} for registering the dialer's touch events.
     */
    private class MyOnTouchListener implements View.OnTouchListener {

        private double startAngle;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            ImageView view = (ImageView) v;
            view.setScaleType(ImageView.ScaleType.MATRIX);

            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    startAngle = getAngle(event.getX(), event.getY());
                    break;

                case MotionEvent.ACTION_MOVE:
                    double currentAngle = getAngle(event.getX(), event.getY());
                    rotateDialer((float) (startAngle - currentAngle), view);
                    startAngle = currentAngle;
                    // Perform the transformation
                    view.setImageMatrix(matrix);

                    return true; // indicate event was handled

                case MotionEvent.ACTION_UP:

                    break;
            }

            return true;
        }

    }

    /**
     * Rotate the dialer.
     *
     * @param degrees The degrees, the dialer should get rotated.
     */
    private void rotateDialer(float degrees, View view) {
        Log.d(Tag, "rotateDialer "+degrees);
        matrix.postRotate(degrees, view.getMeasuredWidth() / 2,
                view.getMeasuredHeight() / 2);
        Log.d(Tag, "inner.getRotation() "+inner.getRotation());
    }

    /**
     * @return The angle of the unit circle with the image view's center
     */
    private double getAngle(double xTouch, double yTouch) {
        double x = xTouch - (dialerWidth / 2d);
        double y = dialerHeight - yTouch - (dialerHeight / 2d);

        switch (getQuadrant(x, y)) {
            case 1:
                return Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
            case 2:
                return 180 - Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
            case 3:
                return 180 + (-1 * Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
            case 4:
                return 360 + Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
            default:
                return 0;
        }
    }

    /**
     * @return The selected quadrant.
     */
    private static int getQuadrant(double x, double y) {
        if (x >= 0) {
            return y >= 0 ? 1 : 4;
        } else {
            return y >= 0 ? 2 : 3;
        }
    }
}
