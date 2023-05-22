package com.app.foodie.data;

import android.net.Uri;
import com.app.foodie.pojos.FoodItemModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import dagger.hilt.android.scopes.ActivityScoped;
import javax.inject.Inject;

@ActivityScoped
public class FirebaseStorageUtils {
  private StorageReference mStorageRef;

  @Inject
  public FirebaseStorageUtils() {
    this.mStorageRef = FirebaseStorage.getInstance().getReference();
  }

  public void insert(String id, String fileName, Uri imageUri, StorageConsumer consumer) {
    StorageReference fileReference =
        mStorageRef.child("food").child(id).child(System.currentTimeMillis() + "." + fileName);

    StorageTask mUploadTask =
        fileReference
            .putFile(imageUri)
            .addOnProgressListener(
                taskSnapshot -> {
                  double progress =
                      (100.0 * taskSnapshot.getBytesTransferred())
                          / taskSnapshot.getTotalByteCount();

                  consumer.accept("", "", "Uploading " + progress + "%");
                })
            .addOnSuccessListener(
                taskSnapshot -> {
                  Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                  String path = taskSnapshot.getMetadata().getReference().getPath();

                  result.addOnSuccessListener(
                      uri -> {
                        consumer.accept(uri.toString(), path, "Uploaded successfully");
                      });
                });
  }

  public void delete(FoodItemModel model) {
    mStorageRef.child(model.getStoragePath()).delete();
  }

  public interface StorageConsumer {
    void accept(String link, String storagePath, String msg);
  }
}
