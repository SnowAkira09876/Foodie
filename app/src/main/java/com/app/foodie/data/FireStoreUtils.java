package com.app.foodie.data;

import android.net.Uri;
import androidx.core.util.Consumer;
import com.app.foodie.pojos.FoodItemModel;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import dagger.hilt.android.scopes.ActivityScoped;
import io.reactivex.rxjava3.core.Observable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

@ActivityScoped
public class FireStoreUtils {
  private FirebaseFirestore db;
  @Inject FirebaseStorageUtils storage;

  @Inject
  public FireStoreUtils() {
    db = FirebaseFirestore.getInstance();
  }

  public void insert(FoodItemModel model, Consumer<String> consumer) {
    db.collection("food")
        .add(model)
        .continueWithTask(
            task -> {
              DocumentReference documentRef = task.getResult();
              String documentId = documentRef.getId();
              documentRef.update("id", documentId);

              return storage.insert(
                  documentId,
                  model.getFileName(),
                  Uri.parse(model.getImage()),
                  (link, path, msg) -> {
                    documentRef.update("image", link);
                    documentRef.update("storagePath", path);

                    consumer.accept(msg);
                  });
            });
  }

  public Observable<List<FoodItemModel>> getFoods() {
    return Observable.create(
        emitter -> {
          db.collection("food")
              .orderBy("date")
              .addSnapshotListener(
                  (queryDocumentSnapshots, exception) -> {
                    if (exception != null) {
                      emitter.onError(exception);
                      return;
                    }
                    List<FoodItemModel> foods = new ArrayList<>();

                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                      FoodItemModel food = document.toObject(FoodItemModel.class);
                      foods.add(food);
                    }

                    emitter.onNext(foods);
                  });
        });
  }

  public void delete(FoodItemModel model, Consumer<String> consumer) {
    String docPath = "food/" + model.getId();
    db.document(docPath)
        .delete()
        .continueWithTask(
            task -> {
              return storage.delete(model);
            })
        .addOnCompleteListener(
            task -> {
              if (task.isSuccessful()) consumer.accept("Deleted successfully");
            });
  }
}
