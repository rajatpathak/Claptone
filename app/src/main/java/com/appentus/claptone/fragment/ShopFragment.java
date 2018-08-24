package com.appentus.claptone.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.appentus.claptone.MyWebViewClient;
import com.appentus.claptone.R;

public class ShopFragment extends Fragment {
    private WebView webPlateform;
    private String url="https://claptone.bigcartel.com";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_5, container, false);

        webPlateform = (WebView) view.findViewById(R.id.webPlateform);

        WebSettings webSettings = webPlateform.getSettings();
        webSettings.setJavaScriptEnabled(true);
        MyWebViewClient webViewClient = new MyWebViewClient(getActivity());
        webPlateform.setWebViewClient(webViewClient);
        webPlateform.loadUrl(url);
        return view;
    }

}


