package com.app.foodie.recyclerviews.bindings;

import android.widget.ImageView;
import androidx.databinding.BindingAdapter;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.app.foodie.R;

public class ViewHolderBinding {
  @BindingAdapter("imageUri")
  public static void loadImage(ImageView imageView, String uri) {
    Picasso.get()
        .load(uri.isEmpty() ? null : uri)
        .fit()
        .centerCrop()
        .placeholder(R.drawable.ic_placeholder)
        .into(imageView);
  }
}
