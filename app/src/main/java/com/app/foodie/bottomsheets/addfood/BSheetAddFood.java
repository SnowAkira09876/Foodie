package com.app.foodie.bottomsheets.addfood;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Toast;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;
import com.app.foodie.activities.main.MainActivityViewModel;
import com.app.foodie.databinding.BottomSheetAddFoodBinding;
import com.app.foodie.pojos.FoodItemModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

public class BSheetAddFood extends BottomSheetDialogFragment {
  private BottomSheetAddFoodBinding binding;
  private MainActivityViewModel viewModel;
  private TextInputLayout tl_title, tl_price, tl_rating, tl_phrase, tl_info;
  private TextInputEditText te_title, te_price, te_rating, te_phrase, te_info;
  private FoodItemModel model;
  private ActivityResultLauncher<String> imagePicker =
      registerForActivityResult(
          new ActivityResultContracts.GetContent(),
          new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri uri) {
              if (uri != null) {
                model.setImage(uri.toString());
                model.setFileName(System.currentTimeMillis() + "." + getFileExtension(uri));

                Picasso.get().load(uri).into(binding.ivFrame);
              }
            }
          });

  @Override
  public void onCreate(Bundle saveInstanceState) {
    super.onCreate(saveInstanceState);
    viewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
    model = new FoodItemModel();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle bundle) {
    binding = BottomSheetAddFoodBinding.inflate(inflater, parent, false);

    onsetViewBinding();
    onsetViews();
    return binding.getRoot();
  }

  private void onsetViewBinding() {
    tl_title = binding.tlTitle;
    tl_price = binding.tlPrice;
    tl_rating = binding.tlRating;
    tl_phrase = binding.tlPhrase;
    tl_info = binding.tlInfo;

    te_title = binding.teTitle;
    te_price = binding.tePrice;
    te_rating = binding.teRating;
    te_phrase = binding.tePhrase;
    te_info = binding.teInfo;
  }

  private void onsetViews() {
    binding.btnSave.setOnClickListener(
        v -> {
          String title = te_title.getText().toString().trim();
          String price = te_price.getText().toString().trim();
          String rating = te_rating.getText().toString().trim();
          String phrase = te_phrase.getText().toString().trim();
          String info = te_info.getText().toString();

          if (title.isEmpty()) {
            tl_title.setErrorEnabled(true);
            tl_title.setError("Title is empty");
          }

          if (price.isEmpty()) {
            tl_price.setErrorEnabled(true);
            tl_price.setError("Price is empty");
          }

          if (rating.isEmpty()) {
            tl_rating.setErrorEnabled(true);
            tl_rating.setError("Rating is empty");
          }

          if (phrase.isEmpty()) {
            tl_phrase.setErrorEnabled(true);
            tl_phrase.setError("Category is empty");
          }

          if (info.isEmpty()) {
            tl_info.setErrorEnabled(true);
            tl_info.setError("Info is empty");
          }

          if (model.getImage() == null) {
            Toast.makeText(requireActivity(), "Please select an image", Toast.LENGTH_SHORT).show();
          }

          if (!title.isEmpty()
              && !price.isEmpty()
              && !rating.isEmpty()
              && !phrase.isEmpty()
              && !info.isEmpty()
              && model.getImage() != null) {
            model.setTitle(title);
            model.setPrice(price);
            model.setRating(rating);
            model.setPhrase(phrase);
            model.setInfo(info);

            viewModel.insert(model);
            dismiss();
          }
        });

    binding.btnCancel.setOnClickListener(
        v -> {
          dismiss();
        });

    binding.ivFrame.setOnClickListener(
        v -> {
          imagePicker.launch("image/*");
        });
  }

  private String getFileExtension(Uri uri) {
    ContentResolver cR = getContext().getContentResolver();
    MimeTypeMap mime = MimeTypeMap.getSingleton();
    return mime.getExtensionFromMimeType(cR.getType(uri));
  }
}
