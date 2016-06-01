package com.example.piechartexample;

import java.util.Arrays;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainFragment extends Fragment
{
 // save a reference so custom methods
 // can access views
 private View topLevelView;
     

 // save a reference to show the pie chart
 private WebView webview;
 
 public View onCreateView(
         LayoutInflater inflater, 
         ViewGroup container, 
         Bundle savedInstanceState)
     {
         super.onCreateView(inflater, container, 
             savedInstanceState);
       
         boolean attachToRoot = false;
         topLevelView = inflater.inflate(
             R.layout.fragment_main, 
             container, 
             attachToRoot);        
         
         // call now or after some condition is met
         initPieChart();
                  
         return topLevelView;
     }    

     // initialize the WebView and the pie chart
     public void initPieChart()
     {
         View stub = topLevelView.findViewById(
             R.id.pie_chart_stub);
                 
         if (stub instanceof ViewStub)
         {
             ((ViewStub)stub).setVisibility(View.VISIBLE);
                 
             webview = (WebView)topLevelView.findViewById(
                 R.id.pie_chart_webview);
                 
             WebSettings webSettings = 
                 webview.getSettings();

             webSettings.setJavaScriptEnabled(true);

             webview.setWebChromeClient(
                 new WebChromeClient());
             
             webview.setWebViewClient(new WebViewClient() 
             {
                 @Override  
                 public void onPageFinished(
                     WebView view, 
                     String url)  
                 {  
                     
                     // after the HTML page loads, 
                     // load the pie chart
                     loadPieChart();
                 }  
             });            
             
             // note the mapping from  file:///android_asset 
             // to Android-D3jsPieChart/assets
             webview.loadUrl("file:///android_asset/" + 
                 "html/piechart.html");
         }
     }

     public void loadPieChart()
     {        
         int dataset[] = new int[] {5,10,15,20,35};
         
         // use java.util.Arrays to format 
         // the array as text
         String text = Arrays.toString(dataset);
         
         // pass the array to the JavaScript function
         webview.loadUrl("javascript:loadPieChart(" + 
             text + ")");
     }
}
