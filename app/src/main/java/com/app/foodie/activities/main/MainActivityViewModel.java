package com.app.foodie.activities.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.app.foodie.data.FireStoreUtils;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import java.util.List;
import com.app.foodie.pojos.FoodItemModel;

public class MainActivityViewModel extends ViewModel {
  private MainActivityRepo repo;
  private final MutableLiveData<FoodItemModel> food = new MutableLiveData<>();
  private final MutableLiveData<Boolean> dataIsReady = new MutableLiveData<>();

  public MainActivityViewModel(FireStoreUtils firestore) {
    this.repo = new MainActivityRepo(firestore);
  }

  // Disposable
  public Observable<List<FoodItemModel>> getFoods() {
    return repo.getFoods();
  }

  public Single<String> insert(FoodItemModel model) {
    return repo.insert(model);
  }

  public Single<String> delete(FoodItemModel model) {
    return repo.delete(model);
  }

  // LiveData
  public LiveData<FoodItemModel> getFood() {
    return this.food;
  }

  public void setFood(FoodItemModel model) {
    this.food.setValue(model);
  }

  public LiveData<Boolean> getDataIsReady() {
    return this.dataIsReady;
  }

  public void setDataIsReady(boolean isReady) {
    this.dataIsReady.setValue(isReady);
  }
}
