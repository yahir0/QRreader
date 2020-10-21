package com.example.qrreader;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.webkit.*;

import static android.webkit.URLUtil.isValidUrl;

public class URLjudge {
    public void Judge(final String Urlreceive, final Context context){
        if(isValidUrl(Urlreceive)){
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setMessage(R.string.browsermessage);
            alert.setPositiveButton(R.string.open, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Uri uri = Uri.parse(Urlreceive);
                    Intent i = new Intent(Intent.ACTION_VIEW,uri);
                    context.startActivity(i);
                }
            });
            alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alert.show();

        }
    }
}
