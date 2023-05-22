package com.app.foodie.data;

import android.net.Uri;
import androidx.core.util.Consumer;
import com.app.foodie.pojos.FoodItemModel;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import dagger.hilt.android.scopes.ActivityScoped;
import io.reactivex.rxjava3.core.Observable;
import com.google.firebase.firestore.ListenerRegistration;
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
        .continueWith(
            task -> {
              DocumentReference documentRef = task.getResult();
              String documentId = documentRef.getId();
              documentRef.update("id", documentId);

              storage.insert(
                  documentId,
                  model.getFileName(),
                  Uri.parse(model.getImage()),
                  (link, path, msg) -> {
                    documentRef.update("image", link);
                    documentRef.update("storagePath", path);

                    consumer.accept(msg);
                  });

              return task.isComplete();
            });
  }

  public Observable<List<FoodItemModel>> getFoods() {
    return Observable.create(
        emitter -> {
          ListenerRegistration registration =
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

          emitter.setCancellable(() -> registration.remove());
        });
  }

  public void delete(FoodItemModel model, Consumer<String> consumer) {
    String docPath = "food/" + model.getId();
    db.document(docPath)
        .delete()
        .continueWith(
            task -> {
              storage.delete(model);
              return task.isComplete();
            })
        .addOnSuccessListener(
            aVoid -> {
              consumer.accept("Deleted successfully");
            });
  }
}
