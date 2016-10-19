package com.example.f3838284.kwanda;

import android.content.Context;
import android.net.Network;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;

/**
 * Created by F3838284 on 10/19/2016.
 */

public class ToastListener extends AdListener {

    Context context;
    String mFailedReason;

    public ToastListener(Context context){
        this.context = context;
    }
    @Override
    public void onAdClosed() {
        super.onAdClosed();
        Toast.makeText(context, "Ad closed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAdFailedToLoad(int i) {
        super.onAdFailedToLoad(i);
        switch (i){
            case AdRequest.ERROR_CODE_INTERNAL_ERROR:
                mFailedReason = "Internal Error";
                break;
            case AdRequest.ERROR_CODE_INVALID_REQUEST:
                mFailedReason = "Invalid Request";
                break;
            case AdRequest.ERROR_CODE_NETWORK_ERROR:
                mFailedReason = "Network Error";
                break;
            case AdRequest.ERROR_CODE_NO_FILL:
                mFailedReason = "No Fill";
                break;
        }
        Toast.makeText(context, "Ad Failed To Load, "+mFailedReason, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAdLeftApplication() {
        super.onAdLeftApplication();
        Toast.makeText(context, "Ad Left Application", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAdLoaded() {
        super.onAdLoaded();
        Toast.makeText(context, "Ad Loaded", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAdOpened() {
        super.onAdOpened();
        Toast.makeText(context, "Ad Opened", Toast.LENGTH_LONG).show();
    }

    public String getFailedReason(){
        return mFailedReason==null?"":mFailedReason;
    }
}
