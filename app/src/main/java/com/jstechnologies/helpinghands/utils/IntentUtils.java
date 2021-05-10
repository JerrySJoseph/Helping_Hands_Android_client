package com.jstechnologies.helpinghands.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class IntentUtils {
    public static void startCallIntent(Context context,String phone)
    {
        phone="+91"+phone;
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        context.startActivity(intent);
    }
}
