package com.app.foodie.activities.viewer;

import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.app.foodie.databinding.ActivityViewerBinding;
import com.app.foodie.pojos.FoodItemModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.transition.platform.MaterialFade;

public class ViewerActivity extends AppCompatActivity {

  private ActivityViewerBinding binding;
  private FoodItemModel model;

  @SuppressWarnings("deprecation")
  @Override
  protected void onCreate(Bundle saveInstanceState) {
    MaterialFade fade = new MaterialFade();

    getWindow().setEnterTransition(fade);
    getWindow().setExitTransition(fade);

    getWindow().setAllowEnterTransitionOverlap(true);
    getWindow().setAllowReturnTransitionOverlap(true);

    super.onCreate(saveInstanceState);
    binding = ActivityViewerBinding.inflate(getLayoutInflater());

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
      this.model =
          getIntent().getExtras() != null
              ? getIntent().getExtras().getParcelable("model", FoodItemModel.class)
              : null;
    else
      this.model =
          getIntent().getExtras() != null ? getIntent().getExtras().getParcelable("model") : null;

    binding.setModel(model);
    setContentView(binding.getRoot());

    binding.fab.setOnClickListener(
        v -> {
          Snackbar.make(binding.coordinator, "Purchased successfully", Snackbar.LENGTH_SHORT)
              .show();
        });
  }
}
