package com.example.f3838284.kwanda;

import android.animation.ObjectAnimator;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {
    @InjectView(R.id.inner) ImageView inner;
    @InjectView(R.id.today) ImageView today;
    @InjectView(R.id.fdlmp) TextView fdlmp;
    @InjectView(R.id.delivery) TextView delivery;
    @InjectView(R.id.duration) TextView duration;
    @InjectView(R.id.units) Spinner units;
    @InjectView(R.id.progressBar) ProgressBar progressBar;

    private String Tag = MainActivity.class.getSimpleName();
    private int dialerWidth;
    private int dialerHeight;
    Matrix matrix = new Matrix();
    Matrix todayMatrix = new Matrix();
    float angle = 0;
    private int monthInt;
    private String monthStr;
    private int dayOfTheYear;
    private int year;
    int dateIndex = 0;
    private int pregnancyDuration;
    private String dateStr;
    private String deliveryDateStr;
    private Calendar c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        c = Calendar.getInstance();
        monthInt = c.get(Calendar.MONTH);
        dayOfTheYear = c.get(Calendar.DAY_OF_YEAR);
        year = c.get(Calendar.YEAR);
        monthStr = getMonthForInt(monthInt);
        angle = 0;
        pregnancyDuration = 0;

        dateStr = "Not set";
        deliveryDateStr = "Not set";
        fdlmp.setText(": "+dateStr);
        delivery.setText(": "+deliveryDateStr);
        duration.setText(": "+deliveryDateStr);
        units.setVisibility(View.INVISIBLE);


        inner.setScaleType(ImageView.ScaleType.MATRIX);
        today.setScaleType(ImageView.ScaleType.MATRIX);
        dialerWidth = inner.getDrawable().getIntrinsicWidth();
        dialerHeight = inner.getDrawable().getIntrinsicHeight();



        inner.setOnTouchListener(new MyOnTouchListener());

        rotateToday(dayOfTheYear, today);

        ObjectAnimator animation = ObjectAnimator.ofInt (progressBar, "progress", 120, 297); // see this max value coming back here, we animale towards that value
        animation.setDuration (5000); //in milliseconds
        animation.setInterpolator (new DecelerateInterpolator());
        animation.start ();
    }

    String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }


    /**
     * Simple implementation of an {@link View.OnTouchListener} for registering the dialer's touch events.
     */
    private class MyOnTouchListener implements View.OnTouchListener {

        private double startAngle;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            units.setVisibility(View.VISIBLE);
            ImageView view = (ImageView) v;
            view.setScaleType(ImageView.ScaleType.MATRIX);

            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    startAngle = getAngle(event.getX(), event.getY());
                    break;

                case MotionEvent.ACTION_MOVE:
                    double currentAngle = getAngle(event.getX(), event.getY());
                    Log.d(Tag, "currentAngle ... "+currentAngle);
                    Log.d(Tag, "startAngle ... "+startAngle);
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
        angle += degrees;
        Log.d(Tag, "angle... "+angle);
        if(angle<-360){
            angle+=360;
        }else if(angle > 360){
            angle-=360;
        }

        if(angle<0){
            angle+=360;
        }

        int date = (int) Math.round(angle*1.0138888888888);


        dateStr = getDateStr(date);
        deliveryDateStr = getDateStr(date+280);

        pregnancyDuration = dayOfTheYear-date;
        if(pregnancyDuration<0){
            //previous year;
            pregnancyDuration+=365;
            year = c.get(Calendar.YEAR)-1;
        }else{
            year = c.get(Calendar.YEAR);
        }

        switch(units.getSelectedItemPosition()){
            case 0:
                duration.setText(": "+pregnancyDuration+""+(pregnancyDuration>1?" Days":" Day"));
                break;
            case 1:
                duration.setText((": "+pregnancyDuration/7)+""+((pregnancyDuration/7)>1?" Weeks":" Week"));
                break;
            case 2:
                duration.setText(": "+(int) ((pregnancyDuration/30.41))+""+(((pregnancyDuration/30.41))>1?"Months":"Month"));
                break;
        }

        final int final_date = date;
        units.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int pregnancyDuration = dayOfTheYear-final_date;
                if(pregnancyDuration<0){
                    //previous year;
                    pregnancyDuration+=365;
                    year = c.get(Calendar.YEAR)-1;
                }else{
                    year = c.get(Calendar.YEAR);
                }
                switch(i){
                    case 0:
                        duration.setText(": "+pregnancyDuration+""+(pregnancyDuration>1?" Days":" Day"));
                        break;
                    case 1:
                        duration.setText(": "+(pregnancyDuration/7)+""+((pregnancyDuration/7)>1?" Weeks":" Week"));
                        break;
                    case 2:
                        duration.setText(": "+(int) (pregnancyDuration/30.41)+""+(((pregnancyDuration/30.41))>1?"Months":"Month"));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        fdlmp.setText(": "+dateStr);
        delivery.setText(": "+deliveryDateStr);

        matrix.postRotate(degrees, view.getMeasuredWidth() / 2,
                view.getMeasuredHeight() / 2);
    }

    /**
     * Animate today.
     *
     * @param degrees The degrees, the today should get rotated.
     */
    private void rotateToday(float degrees, View v) {
        Animation a = new RotateAnimation(0.0f, (degrees+90),
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        a.setFillAfter(true);
        a.setRepeatCount(0);
        a.setDuration(5000);
        today.setAnimation(a);
    }

    private String getDateStr(int date) {
        int localYear =  year;
        if(date==0){
            dateStr = "DEC, 31, "+(localYear-1);
            dateIndex = 11;
            return dateStr;
        }
        if(date>365){
            //Next year
            localYear++;
            date-=365;
        }
        String dateStr;
        if(date<=31){
            dateStr = "JAN, "+date;
            dateIndex = 0;
        }else{
            date-=31;
            if(date<=28){
                dateStr = "FEB, "+date;
                dateIndex = 1;
            }else{
                date-=28;
                if(date<=31){
                    dateStr = "MAR, "+date;
                    dateIndex = 2;
                }else{
                    date-=31;
                    if(date<=30) {
                        dateStr = "APR, "+date;
                        dateIndex = 3;
                    }else {
                        date -= 30;
                        if (date <= 31) {
                            dateStr = "MAY, "+date;
                            dateIndex = 4;
                        }else {
                            date -= 31;
                            if (date <= 30) {
                                dateStr = "JUN, "+date;
                                dateIndex = 5;
                            }else {
                                date -= 30;
                                if (date <= 31) {
                                    dateStr = "JUL, "+date;
                                    dateIndex = 6;
                                }else {
                                    date -= 31;
                                    if (date <= 31) {
                                        dateStr = "AUG, "+date;
                                        dateIndex = 7;
                                    }else {
                                        date -= 31;
                                        if (date <= 30) {
                                            dateStr = "SEP, "+date;
                                            dateIndex = 8;
                                        }else {
                                            date -= 30;
                                            if (date <= 31) {
                                                dateStr = "OCT, "+date;
                                                dateIndex = 9;
                                            }else {
                                                date -= 31;
                                                if (date <= 30) {
                                                    dateStr = "NOV, "+date;
                                                    dateIndex = 10;
                                                }else {
                                                    date -= 30;
                                                    if (date <= 31) {
                                                        dateStr = "DEC, "+date;
                                                        dateIndex = 11;
                                                    }else{
                                                        dateStr = "WTF !!!, "+date;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return dateStr+" "+localYear;
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
