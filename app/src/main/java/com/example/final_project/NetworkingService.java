package com.example.final_project;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;

public class NetworkingService {

    private String randomMealURL = "https://www.thecocktaildb.com/api/json/v1/1/search.php?f=";

    public static ExecutorService networkExecutorService = Executors.newFixedThreadPool(4);
    public static Handler networkingHandler = new Handler(Looper.getMainLooper());

    interface NetworkingListener{
        void dataListener(String jsonString);
        void imageListener(Bitmap image);
    }

    public NetworkingListener listener;
    public void searchForRandomMeal(){
        String urlString = randomMealURL + "a";
        connect(urlString);
    }

    public void getImageData(String image) {
        networkExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(image);
                    Bitmap bitmap = BitmapFactory.decodeStream((InputStream) url.getContent());
                    networkingHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.imageListener(bitmap);
                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void connect(String url) {
        networkExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                HttpsURLConnection httpsURLConnection = null;
                try {
                    String jsonData = "";
                    URL urlObj = new URL(url);
                    httpsURLConnection = (HttpsURLConnection) urlObj.openConnection();
                    httpsURLConnection.setRequestMethod("GET");
                    httpsURLConnection.setRequestProperty("Content-Type", "application/json");
                    InputStream in = httpsURLConnection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(in);
                    int inputSteamData = 0;
                    while ((inputSteamData = reader.read()) != -1) {
                        char current = (char) inputSteamData;
                        jsonData += current;
                    }

                    // Log the JSON data before parsing
                    Log.d("NetworkingService", "Received JSON response: " + jsonData);

                    final String finalData = jsonData;
                    // The data is ready
                    networkingHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            // Any code here will run in the main thread
                            listener.dataListener(finalData);
                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (httpsURLConnection != null) {
                        httpsURLConnection.disconnect();
                    }
                }
            }
        });
    }



}