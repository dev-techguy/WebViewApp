package techguy.webviewapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.builder.AnimateGifMode;

public class MainPage extends AppCompatActivity {
    Network network = new Network();
    WebView appWebView;

    //private ProgressBar spinner;
    private ImageView spinner;
    Url url = new Url();

    //@SuppressLint("SetJavaScriptEnabled")
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //method to store cookie session and destroy
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().startSync();


        //get components here
        appWebView = findViewById(R.id.appWebView);
        appWebView.setBackgroundColor(Color.TRANSPARENT);
        spinner = findViewById(R.id.spinner);
        loadGifImage(spinner);

        //progress
        spinner.setVisibility(View.GONE);

        /*
          check for network
          */
        String networkStatus = network.networkChecker(MainPage.this);
        if (networkStatus != null) {
            /*
              start of web view code
              set all web attributes here
              */
            WebSettings webSettings = appWebView.getSettings();
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setUseWideViewPort(true);
            webSettings.setJavaScriptEnabled(true);
            webSettings.setDomStorageEnabled(true);
            webSettings.setDefaultTextEncodingName("utf-8");
            webSettings.setPluginState(WebSettings.PluginState.ON);
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            appWebView.clearCache(false);
            appWebView.requestFocus();
            appWebView.requestFocusFromTouch();
            appWebView.setWebViewClient(new WebViewClient());
            appWebView.setWebChromeClient(new WebChromeClient());

            //allow google sign in here
            appWebView.getSettings().setUserAgentString(String.valueOf(R.string.app_name));

            //method for checking url state loading
            appWebView.setWebViewClient(new WebViewClient() {

                // This method will be triggered when the Page Started Loading
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    spinner.setVisibility(View.VISIBLE);
//                    loadGifImage(spinner);
                }

                // This method will be triggered when the Page loading is completed
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    spinner.setVisibility(View.GONE);
                }

                // This method will be triggered when error page appear
                public void onReceivedError(WebView view, int errorCode,
                                            String description, String failingUrl) {
                    // You can redirect to your own page instead getting the default
                    // error page
                    startActivity(new Intent(MainPage.this, Error404.class));
                    finish();
                    super.onReceivedError(view, errorCode, description, failingUrl);
                }
            });
            appWebView.loadUrl(url.appUrl);
            appWebView.loadUrl("javascript:fn()");
        } else {
            startActivity(new Intent(MainPage.this, Error404.class));
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        if (appWebView.canGoBack()) {
            appWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    //load gif image here
    public void loadGifImage(ImageView spinner) {
        Ion.with(spinner)
                .error(R.mipmap.spinner)
                .animateGif(AnimateGifMode.ANIMATE)
                .load("file:///android_asset/spinner.gif");
    }
}
