package com.app.foodie.recyclerviews.diffs;

import androidx.recyclerview.widget.DiffUtil;
import com.app.foodie.pojos.FoodItemModel;

public class FoodDiff extends DiffUtil.ItemCallback<FoodItemModel> {

  @Override
  public boolean areItemsTheSame(FoodItemModel oldItem, FoodItemModel newItem) {
    return oldItem.getTitle().equals(newItem.getTitle());
  }

  @Override
  public boolean areContentsTheSame(FoodItemModel oldItem, FoodItemModel newItem) {
    return oldItem.equals(newItem);
  }
}
