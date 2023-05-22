package com.app.foodie.activities.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.app.foodie.data.FireStoreUtils;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;
import java.util.List;
import com.app.foodie.pojos.FoodItemModel;

public class MainActivityViewModel extends ViewModel {
  private MainActivityRepo repo;
  private BehaviorSubject<List<FoodItemModel>> foodSubject;
  private PublishSubject<String> progressSubject;
  private PublishSubject<String> deleteMsgSubject;
  private final MutableLiveData<FoodItemModel> food = new MutableLiveData<>();
  private final MutableLiveData<Boolean> dataIsReady = new MutableLiveData<>();

  public MainActivityViewModel(FireStoreUtils firestore) {
    this.repo = new MainActivityRepo(firestore);
    this.foodSubject = BehaviorSubject.create();
    this.progressSubject = PublishSubject.create();
    this.deleteMsgSubject = PublishSubject.create();
  }

  // Disposable
  public Disposable getFoods() {
    return repo.getFoods()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(foodSubject::onNext);
  }

  public Disposable insert(FoodItemModel model) {
    return repo.insert(model)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(progressSubject::onNext);
  }

  public Disposable delete(FoodItemModel model) {
    return repo.delete(model)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(deleteMsgSubject::onNext);
  }

  // Observable
  public Observable<List<FoodItemModel>> getFoodsObservable() {
    return foodSubject;
  }

  public Observable<String> getDeleteObservable() {
    return deleteMsgSubject;
  }

  public Observable<String> getProgressObservable() {
    return progressSubject;
  }

  // LiveData
  public MutableLiveData<FoodItemModel> getFood() {
    return this.food;
  }

  public void setFood(FoodItemModel model) {
    this.food.setValue(model);
  }

  public MutableLiveData<Boolean> getDataIsReady() {
    return this.dataIsReady;
  }

  public void setDataIsReady(boolean isReady) {
    this.dataIsReady.setValue(isReady);
  }
}
