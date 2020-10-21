package com.example.qrreader;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;


public class Clipboard {

    public void Copy(String Text, Context context){
        ClipboardManager clipboardManager = (ClipboardManager) context.getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
        if(null == clipboardManager){
            return;
        }
        clipboardManager.setPrimaryClip(ClipData.newPlainText("",Text));
    }

}
