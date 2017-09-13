package com.jbmatrix.utils.helper;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.VolleyError;

/**
 * Created by jbmatrix on 05/09/17.
 */

public class Responses {

	public static final String UNEXPECTED_ERROR = "Something unexpected error has been occurred. Please try again later.";
	public static final String INTERNET_ERROR = "Please check your internet connection";

	public static void showVolleyError(VolleyError error, Context context) {
		if (error != null)
			showToast(context,error.getMessage());

		showToast(context,UNEXPECTED_ERROR);
	}

	public static void showToast(Context context,String text) {
		Toast.makeText(context, TextDecoration.decorate(text), Toast.LENGTH_SHORT).show();
	}
}
