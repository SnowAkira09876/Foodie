package com.app.foodie.pojos;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.firebase.firestore.ServerTimestamp;
import java.util.Date;

public class FoodItemModel implements Parcelable {
  private String id, image, title, price, rating, phrase, fileName, info, storagePath;

  @ServerTimestamp private Date date;

  public static final Creator<FoodItemModel> CREATOR =
      new Creator<FoodItemModel>() {
        @Override
        public FoodItemModel createFromParcel(Parcel in) {
          return new FoodItemModel(in);
        }

        @Override
        public FoodItemModel[] newArray(int size) {
          return new FoodItemModel[size];
        }
      };

  public FoodItemModel() {}

  protected FoodItemModel(Parcel in) {
    image = in.readString();
    title = in.readString();
    info = in.readString();
  }

  @Override
  public boolean equals(Object object) {
    return super.equals(object);
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(image);
    dest.writeString(title);
    dest.writeString(info);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getImage() {
    return this.image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getPrice() {
    return this.price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getRating() {
    return this.rating;
  }

  public void setRating(String rating) {
    this.rating = rating;
  }

  public String getPhrase() {
    return this.phrase;
  }

  public void setPhrase(String phrase) {
    this.phrase = phrase;
  }

  public Date getDate() {
    return this.date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getFileName() {
    return this.fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getInfo() {
    return this.info;
  }

  public void setInfo(String info) {
    this.info = info;
  }

  public String getStoragePath() {
    return this.storagePath;
  }

  public void setStoragePath(String storagePath) {
    this.storagePath = storagePath;
  }
}
