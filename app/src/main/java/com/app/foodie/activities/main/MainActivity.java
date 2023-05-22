package com.app.foodie.activities.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.ViewTreeObserver;
import androidx.appcompat.app.AppCompatActivity;
import android.os.*;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.splashscreen.SplashScreen;
import androidx.lifecycle.ViewModelProvider;
import com.app.foodie.activities.viewer.ViewerActivity;
import com.app.foodie.bottomsheets.addfood.BSheetAddFood;
import com.app.foodie.data.FireStoreUtils;
import com.app.foodie.databinding.ActivityMainBinding;
import com.app.foodie.pojos.FoodItemModel;
import com.app.foodie.recyclerviews.AdapterFactory;
import com.app.foodie.recyclerviews.adapters.FoodAdapter;
import com.app.foodie.widgets.recyclerview.AkiraRecyclerView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import javax.inject.Inject;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity
    implements FoodAdapter.FoodItemClickListener, ViewTreeObserver.OnPreDrawListener {
  private ActivityMainBinding binding;
  private MainActivityViewModel viewModel;
  private MainActivityVMFactory factory;
  private AkiraRecyclerView rv;
  private FoodAdapter adapter;
  private CompositeDisposable disposables;
  private FloatingActionButton fab;
  private View content;
  @Inject FireStoreUtils firestore;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

    super.onCreate(savedInstanceState);

    factory = new MainActivityVMFactory(firestore);

    viewModel = new ViewModelProvider(this, factory).get(MainActivityViewModel.class);

    disposables = new CompositeDisposable();

    adapter = AdapterFactory.getFoodAdapter(this);
    onsetViewBinding();
    onsetViews();
    onsetObservers();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    disposables.clear();
  }

  @Override
  public void onFoodClick(FoodItemModel model, View view) {
    Bundle bundle = new Bundle();
    bundle.putParcelable("model", model);

    ActivityOptionsCompat options =
        ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, view.getTransitionName());

    startActivity(new Intent(this, ViewerActivity.class).putExtras(bundle), options.toBundle());
  }

  @Override
  public void onFoodLongClick(FoodItemModel model) {
    MaterialAlertDialogBuilder builder =
        new MaterialAlertDialogBuilder(this)
            .setTitle("Delete Item?")
            .setMessage("Are you sure you want to delete " + model.getTitle() + "?");

    builder.setPositiveButton(
        "Delete",
        (DialogInterface dialog, int id) -> {
          Disposable deleteDisp = viewModel.delete(model);
          disposables.add(deleteDisp);
        });
    builder.setNegativeButton("Cancel", (DialogInterface dialog, int id) -> {});

    builder.create().show();
  }

  @Override
  public boolean onPreDraw() {
    if (viewModel.getDataIsReady().getValue() == true) {
      content.getViewTreeObserver().removeOnPreDrawListener(this);
      return true;
    }

    return false;
  }

  private void onsetViewBinding() {
    binding = ActivityMainBinding.inflate(getLayoutInflater());

    rv = binding.rv;
    fab = binding.fab;
    content = findViewById(android.R.id.content);
  }

  private void onsetViews() {
    viewModel.setDataIsReady(false);
    content.getViewTreeObserver().addOnPreDrawListener(this);

    setContentView(binding.getRoot());

    rv.setEmptyView(binding.emptyView);
    rv.setAdapter(adapter);

    fab.setOnClickListener(
        v -> {
          new BSheetAddFood().show(getSupportFragmentManager(), null);
        });
  }

  private void onsetObservers() {
    Disposable progressDisp =
        viewModel
            .getProgressObservable()
            .subscribe(
                msg -> Snackbar.make(binding.getRoot(), msg, Snackbar.LENGTH_INDEFINITE).show());
    Disposable foodDisp =
        viewModel
            .getFoodsObservable()
            .subscribe(
                list -> {
                  adapter.submitList(list);
                  viewModel.setDataIsReady(true);
                });
    Disposable deleteDisp =
        viewModel
            .getDeleteObservable()
            .subscribe(msg -> Snackbar.make(binding.getRoot(), msg, Snackbar.LENGTH_SHORT).show());

    Disposable getFoodsDisp = viewModel.getFoods();
    
    disposables.add(progressDisp);
    disposables.add(foodDisp);
    disposables.add(deleteDisp);
    disposables.add(getFoodsDisp);

    viewModel
        .getFood()
        .observe(
            this,
            model -> {
              Disposable insertDisp = viewModel.insert(model);

              disposables.add(insertDisp);
            });
  }
}
