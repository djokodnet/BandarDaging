package com.maxmobilemedia.bandardaging.bandardaging;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {


    WebView main1WebView = null;
    String subimsi = null;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //must be called before adding content!
        this.getWindow().requestFeature(Window.FEATURE_PROGRESS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main1WebView = (WebView) findViewById(R.id.mainWebView);
        if (getSubscriberId() != null){
            subimsi = getSubscriberId();
        } else { subimsi = "1234567890";}

        String suburl = "http://www.stikerkeren.com/bandardaging/index.php";
        main1WebView.loadUrl(suburl);

        main1WebView.setWebViewClient(new MainWebViewClient());

        getWindow().setFeatureInt(Window.FEATURE_PROGRESS,
                Window.PROGRESS_VISIBILITY_ON);

        main1WebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                MainActivity.this.setTitle("Loading...");
                MainActivity.this.setProgress(progress * 100);
                if (progress == 100){
                    MainActivity.this.setTitle(view.getTitle());
                }
            }
        });

    }

    @Override
    public void onBackPressed(){
        if(main1WebView.canGoBack())
            main1WebView.goBack();
        else
            super.onBackPressed();
    }

    public String getSubscriberId(){

        String IMSI = null;
        String serviceName = Context.TELEPHONY_SERVICE;
        TelephonyManager m_telephonyManager = (TelephonyManager) getSystemService(serviceName);
        int deviceType = m_telephonyManager.getPhoneType();
        switch (deviceType) {
            case (TelephonyManager.PHONE_TYPE_GSM):
                break;
            case (TelephonyManager.PHONE_TYPE_CDMA):
                break;
            case (TelephonyManager.PHONE_TYPE_NONE):
                break;
            default:
                break;
        }
        IMSI = m_telephonyManager.getSubscriberId();
        return IMSI;
    }

    private class MainWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.i("Log", "loading: " + url);

            view.loadUrl(url);
            return true;
        }
    }

}
