package com.example.modul3

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class BrowserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val webView = WebView(this)

        webView.webViewClient = WebViewClient()

        webView.settings.javaScriptEnabled = true

        webView.loadUrl("https://bgn.go.id/")
        setContentView(webView)
    }
}