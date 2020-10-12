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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IntentIntegrator(MainActivity.this).setBeepEnabled(false).setCaptureActivity(MyCaptureActivity.class).setPrompt("QRコードにかざしてね☆").initiateScan();
                //QRコードにかざしてね☆ = Please bring your QR code
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
                Toast.makeText(MainActivity.this, "クリップボードにコピーされました", Toast.LENGTH_SHORT).show();
                //クリップボードにコピーされました = Complete Copy Clipboard
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("QRコードを読み込んでください");
                //QRコードを読み込んでください = Please bring your QR code
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
            alert.setMessage("ブラウザを開きますか？");
            //ブラウザを起動しますか？ = Do you want to start the browser?
            alert.setPositiveButton("開く", new DialogInterface.OnClickListener() {
            //開く = Open
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Uri uri = Uri.parse(result.getContents());
                    Intent i = new Intent(Intent.ACTION_VIEW,uri);
                    startActivity(i);
                }
            });
            alert.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
            //キャンセル = Cancel
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alert.show();
        }
        //URL Judgment
        textView.setText(result.getContents());
    }
}
