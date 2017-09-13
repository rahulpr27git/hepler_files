package com.jbmatrix.utils.helper;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;

import com.jbmatrix.egov_krishnagarmunicipality.R;

/**
 * Created by jbmatrix on 05/09/17.
 */

public class Developer {
	public static Spanned getCompanyDetails(Context context) {
		Spanned details = null;
		String label = "<small>"+context.getString(R.string.developed_by)+"</small> " +
				"<b>"+context.getString(R.string.company_name)+"</b>";

		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
			details = Html.fromHtml(label,Html.FROM_HTML_MODE_COMPACT);
		} else {
			details = Html.fromHtml(label);
		}

		return details;
	}
}
