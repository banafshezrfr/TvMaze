package com.sheypoor.application.tvmaze.util;

import android.text.Html;

/**
 * Created by Banafshe.Zarefar on 18/08/2017.
 */

public class UtilString {
    public static String stripHtml(String html) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString();
        } else {
            return Html.fromHtml(html).toString();
        }
    }
}
