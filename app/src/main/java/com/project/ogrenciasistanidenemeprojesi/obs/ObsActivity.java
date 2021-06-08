package com.project.ogrenciasistanidenemeprojesi.obs;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.project.ogrenciasistanidenemeprojesi.R;

public class ObsActivity extends AppCompatActivity {

    String url;
    WebView myWebView;
    Boolean connected = false;
    private ProgressBar mProgressBar;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obs);

         url="https://obs.ticaret.edu.tr/SelfService/Home.aspx";
        //action bar gözükmesin diye bu kod parçasını yazdım
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();

        //progressbarı atladım

        frameLayout = findViewById(R.id.framelayout);
        mProgressBar = findViewById(R.id.progress_horizontal);
        mProgressBar.setMax(100);

        myWebView = findViewById(R.id.webview);

        myWebView.setWebChromeClient(new WebChromeClient() {

            public void onProgressChanged(WebView webView, int progress) {

                frameLayout.setVisibility(View.VISIBLE);
                mProgressBar.setProgress(progress);

                setTitle("yukleniyor");

                if (progress == 100) {

                    frameLayout.setVisibility(View.GONE);
                    setTitle(webView.getTitle());
                }
                super.onProgressChanged(webView, progress);
            }
        });

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED ) {

            connected = true;
        } else {
            connected = false;
        }


        if (connected) {
            myWebView.setWebViewClient(new WebViewClient());
            myWebView.loadUrl(url);
            mProgressBar.setProgress(0);
            //  progressDialog.show();

            myWebView.setWebViewClient(new WebViewClient() {

                @Override
                public void onPageStarted(WebView view, String url, Bitmap bitmap) {
                    super.onPageStarted(view, url, bitmap);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    // progressDialog.dismiss();
                }

                @Override
                public void onLoadResource(WebView view, String url) {
                    super.onLoadResource(view, url);
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {

                    frameLayout.setVisibility(View.VISIBLE);
                    myWebView.loadUrl(url);
                    return true;
                }


            });
        }
        if(!connected){
            Toast.makeText(getApplicationContext(),"Lütfen internet bağlantınızı kontrol ediniz",Toast.LENGTH_LONG).show();
        }

    }
}