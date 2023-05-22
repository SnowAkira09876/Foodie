package com.app.foodie.recyclerviews;

import com.app.foodie.recyclerviews.adapters.FoodAdapter;
import com.app.foodie.recyclerviews.diffs.FoodDiff;

public class AdapterFactory {

  public static FoodAdapter getFoodAdapter(FoodAdapter.FoodItemClickListener listener) {
    return new FoodAdapter(listener, new FoodDiff());
  }
}
