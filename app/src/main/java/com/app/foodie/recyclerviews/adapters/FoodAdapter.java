package com.app.foodie.recyclerviews.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.app.foodie.databinding.ItemFoodBinding;
import com.app.foodie.pojos.FoodItemModel;
import com.app.foodie.recyclerviews.BaseListAdapter;
import com.app.foodie.recyclerviews.diffs.FoodDiff;
import com.app.foodie.recyclerviews.viewholders.FoodViewHolder;

public class FoodAdapter extends BaseListAdapter<FoodItemModel, FoodViewHolder> {
  private FoodItemClickListener listener;

  public FoodAdapter(FoodItemClickListener listener, FoodDiff diff) {
    super(diff);
    this.listener = listener;
  }

  @Override
  public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new FoodViewHolder(
        ItemFoodBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
  }

  @Override
  public void onBindViewHolder(FoodViewHolder holder, int position) {
    holder.bind(getItem(position), listener);
  }

  public interface FoodItemClickListener {
    void onFoodClick(FoodItemModel model, View view);

    void onFoodLongClick(FoodItemModel model);
  }
}
