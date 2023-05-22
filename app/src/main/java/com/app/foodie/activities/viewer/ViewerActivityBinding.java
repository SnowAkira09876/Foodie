package com.app.foodie.activities.viewer;

import android.widget.TextView;
import android.text.Spanned;
import androidx.core.text.HtmlCompat;
import androidx.databinding.BindingAdapter;

public class ViewerActivityBinding {

  @BindingAdapter("htmlText")
  public static void setHtmlText(TextView textView, String htmlText) {
    Spanned spannedText = HtmlCompat.fromHtml(htmlText, HtmlCompat.FROM_HTML_MODE_LEGACY);
    textView.setText(spannedText);
  }
}
