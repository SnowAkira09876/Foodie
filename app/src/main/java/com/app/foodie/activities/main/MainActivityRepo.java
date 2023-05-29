package com.app.foodie.activities.main;

import com.app.foodie.data.FireStoreUtils;
import com.app.foodie.pojos.FoodItemModel;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.subjects.PublishSubject;
import java.util.List;

public class MainActivityRepo {
  private FireStoreUtils firestore;

  public MainActivityRepo(FireStoreUtils firestore) {
    this.firestore = firestore;
  }

  public Observable<List<FoodItemModel>> getFoods() {
    return firestore.getFoods();
  }

  public Single<String> insert(FoodItemModel model) {
    return firestore.insert(model);
  }

  public Single<String> delete(FoodItemModel model) {
    return firestore.delete(model);
  }
}
