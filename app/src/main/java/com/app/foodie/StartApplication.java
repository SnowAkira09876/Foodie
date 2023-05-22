package com.app.foodie;

import android.app.Application;
import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class StartApplication extends Application {
  
  @Override
  public void onCreate() {
    super.onCreate();
  }
}
