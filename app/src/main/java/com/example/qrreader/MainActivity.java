package com.example.qrreader;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


import static android.webkit.URLUtil.isValidUrl;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        final TextView textView = findViewById(R.id.textView2);



        new IntentIntegrator(MainActivity.this).setBeepEnabled(false).setCaptureActivity(MyCaptureActivity.class).setPrompt(getString(R.string.CameraMessage)).initiateScan();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IntentIntegrator(MainActivity.this).setBeepEnabled(false).setCaptureActivity(MyCaptureActivity.class).setPrompt(getString(R.string.CameraMessage)).initiateScan();
            }
        });




        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                if(null == clipboardManager){
                    return;
                }
                clipboardManager.setPrimaryClip(ClipData.newPlainText("", textView.getText()));
                Toast.makeText(MainActivity.this, R.string.Clipboard, Toast.LENGTH_SHORT).show();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(R.string.message);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TextView textView = findViewById(R.id.textView2);

        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (isValidUrl(result.getContents())) {

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage(R.string.browsermessage);
            alert.setPositiveButton(R.string.open, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Uri uri = Uri.parse(result.getContents());
                    Intent i = new Intent(Intent.ACTION_VIEW,uri);
                    startActivity(i);
                }
            });
            alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alert.show();

        }
        //URL Judgment

        if(result.getContents() ==null){
            textView.setText(R.string.message);
        }else {
            textView.setText(result.getContents());
        }
    }


}
