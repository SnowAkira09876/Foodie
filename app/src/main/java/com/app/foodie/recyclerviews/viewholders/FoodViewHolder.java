package com.app.foodie.recyclerviews.viewholders;

import androidx.recyclerview.widget.RecyclerView;
import com.app.foodie.databinding.ItemFoodBinding;
import com.app.foodie.pojos.FoodItemModel;
import com.app.foodie.recyclerviews.adapters.FoodAdapter;

public class FoodViewHolder extends RecyclerView.ViewHolder {
  private ItemFoodBinding binding;

  public FoodViewHolder(ItemFoodBinding binding) {
    super(binding.getRoot());
    this.binding = binding;
  }

  public void bind(FoodItemModel model, FoodAdapter.FoodItemClickListener listener) {
    binding.setModel(model);

    itemView.setOnClickListener(
        v -> {
          listener.onFoodClick(model, binding.ivImage);
        });

    itemView.setOnLongClickListener(
        v -> {
          listener.onFoodLongClick(model);
          return true;
        });
  }
}
