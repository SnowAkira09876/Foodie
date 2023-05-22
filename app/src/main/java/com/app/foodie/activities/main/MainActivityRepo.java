package com.app.foodie.activities.main;

import com.app.foodie.data.FireStoreUtils;
import com.app.foodie.pojos.FoodItemModel;
import io.reactivex.rxjava3.core.Observable;
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

  public Observable<String> insert(FoodItemModel model) {
    return Observable.create(
        emitter -> {
          firestore.insert(model, emitter::onNext);
        });
  }

  public Observable<String> delete(FoodItemModel model) {
    return Observable.create(
        emitter -> {
          firestore.delete(model, emitter::onNext);
        });
  }
}
