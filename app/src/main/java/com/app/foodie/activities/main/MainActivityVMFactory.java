package com.app.foodie.activities.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.app.foodie.data.FireStoreUtils;

public class MainActivityVMFactory implements ViewModelProvider.Factory {
  private FireStoreUtils firestore;
  
  public MainActivityVMFactory(FireStoreUtils firestore) {
    this.firestore = firestore;
  }

  @NonNull
  @Override
  public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
    if (modelClass.isAssignableFrom(MainActivityViewModel.class)) {
      return modelClass.cast(new MainActivityViewModel(firestore));
    }
    throw new IllegalArgumentException("Unknown ViewModel class");
  }
}
