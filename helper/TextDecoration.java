package com.jbmatrix.utils.helper;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.text.Spanned;

/**
 * Created by jbmatrix on 05/09/17.
 */

public class TextDecoration {
	
	private AppCompatTextView textView;
	private Context context;
	
	public static final String TAG_UNDERLINE_OPEN = "<u>";
	public static final String TAG_UNDERLINE_CLOSE = "</u>";
	public static final String TAG_BOLD_OPEN = "<b>";
	public static final String TAG_BOLD_CLOSE = "</b>";
	public static final String TAG_SMALL_OPEN = "<small>";
	public static final String TAG_SMALL_CLOSE = "</small>";
	public static final String TAG_ITALIC_OPEN = "<i>";
	public static final String TAG_ITALIC_CLOSE = "</i>";
	public static final String SPACE = " ";
	
	private String decoratedText = "";
	
	public TextDecoration(Context context) {
		this.context = context;
	}
	
	public static TextDecoration with(Context ctx) {
		return new TextDecoration(ctx);
	}

	/**
	 * Initiate AppCompatTextView to decorate
	 * @param textView
	 * @return
	 */
	public TextDecoration decorate(AppCompatTextView textView) {
		this.textView = textView;
		return this;
	}

	/**
	 * To decorate text which already has html tags property
	 * @param text
	 * @return
	 */
	public static Spanned decorate(String text) {
		Spanned spannedText = null;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			spannedText = Html.fromHtml(text,Html.FROM_HTML_MODE_COMPACT);
		} else {
			spannedText = Html.fromHtml(text);
		}
		return spannedText;
	}

	/**
	 * To add underline below the text,
	 * pass string data
	 * @param text
	 */
	public TextDecoration underline(String text) {
		if (textView == null)
			return this;

		decoratedText = TextDecoration.TAG_UNDERLINE_OPEN + text + TextDecoration.TAG_UNDERLINE_CLOSE;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			textView.setText(Html.fromHtml(decoratedText.trim(),Html.FROM_HTML_MODE_COMPACT));
		} else {
			textView.setText(Html.fromHtml(decoratedText.trim()));
		}

		return this;
	}

	/**
	 * To add underline below the text,
	 * pass string resource id
	 * @param id
	 */
	public TextDecoration underline(int id) {
		if (textView == null)
			return this;

		return underline(context.getString(id));
	}

	/**
	 * Underline only the text, Not the space
	 * pass string text
	 * @param text
	 */
	public TextDecoration underlineWithoutSpace(String text) {
		if (textView == null)
			return this;
		
		String[] texts = text.split(" ");

		for (String str : texts) {
			decoratedText += TextDecoration.TAG_UNDERLINE_OPEN + str + TextDecoration.TAG_UNDERLINE_CLOSE + TextDecoration.SPACE;
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			textView.setText(Html.fromHtml(decoratedText.trim(),Html.FROM_HTML_MODE_COMPACT));
		} else {
			textView.setText(Html.fromHtml(decoratedText.trim()));
		}

		return this;
	}

	/**
	 * Underline only the text, Not the space
	 * pass string resource id
	 * @param id
	 */
	public TextDecoration underlineWithoutSpace(int id) {
		if (textView == null)
			return this;

		return underlineWithoutSpace(context.getString(id));
	}

	/**
	 * To finish Decorating
	 */
	public void finish(){
		context = null;
	}
}
