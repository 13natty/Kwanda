package com.example.f3838284.kwanda;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;

import java.lang.reflect.Array;
import java.util.Calendar;

import static com.example.f3838284.kwanda.MainActivity.MyPREFERENCES;

/**
 * Implementation of App Widget functionality.
 */
public class MyWidget extends AppWidgetProvider {

    private static int pregnancyDuration;
    private static int year;
    private static int dateIndex = 0;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        CharSequence widgetText = context.getString(R.string.appwidget_text);
        Calendar c = Calendar.getInstance();
        int dayOfTheYear = c.get(Calendar.DAY_OF_YEAR);
        year = c.get(Calendar.YEAR);

        // Retrieve date records
        String URL = "content://com.example.f3838284.kwanda/data";

        Uri uri = Uri.parse(URL);
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, "FDLP");

        int date = -1;
        if (cursor.moveToFirst()) {
            do{

                date = Integer.parseInt(cursor.getString(cursor.getColumnIndex( MyProvider.SelectedDay)));
            } while (cursor.moveToNext());
        }
        pregnancyDuration = dayOfTheYear-date;
        if(date != -1){
            widgetText = " Week "+pregnancyDuration/7+"";
        }

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widget);
        views.setTextViewText(R.id.delivery_text, "Birth day : "+getDateStr(date+280));
        views.setTextViewText(R.id.appwidget_text, widgetText);
        int trimester = 0;
        if(pregnancyDuration/30.41 > 2.0f){
            trimester = 3;
        }else if(pregnancyDuration/30.41 > 1.0f){
            trimester = 2;
        }else{
            trimester = 1;
        }
        views.setTextViewText(R.id.trimester_text, "Trimester : "+trimester+"");

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);
        //Drawable top = context.getResources().getDrawable(R.drawable.one_to_three_weeks);
        //views.setTextViewCompoundDrawables(R.id.appwidget_text, 0, R.drawable.thirtysix_fourthyone_weeks, 0, 0);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static String getDateStr(int date) {
        int localYear =  year;
        if(date==0){
            String dateStr = "DEC, 31, " + (localYear - 1);
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

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

