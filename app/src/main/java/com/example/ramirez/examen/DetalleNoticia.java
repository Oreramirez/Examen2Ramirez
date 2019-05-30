package com.example.ramirez.examen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class DetalleNoticia extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_noticia);

        webView=(WebView)findViewById(R.id.webView);
        Bundle bundle=getIntent().getExtras();

        String url=bundle.getString("Enlace");

        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        webView.loadUrl(url);
    }
}
