package com.sheygam.java_23_03_04_19_cw;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    MenuItem itemDone, itemDelete;
    Handler handler;
    ProgressBar progressBar, progressBarHor;
    TextView statusTxt;
    Handler.Callback callback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case Worker.STATE_START:
                    itemDelete.setEnabled(false);
                    statusTxt.setText("");
                    showProgress(true);
                    break;
                case Worker.STATE_END:
                    itemDelete.setEnabled(true);
                    showProgress(false);
                    break;
                case Worker.STATE_UPDATE:
                    statusTxt.setText(String.valueOf(msg.arg1));
                    progressBarHor.setProgress(msg.arg1);
                    break;
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        progressBarHor = findViewById(R.id.progressBarHor);
        statusTxt = findViewById(R.id.statusTxt);
        handler = new Handler(callback);

    }

    private void showProgress(boolean isShow) {
        progressBarHor.setVisibility(isShow ? View.VISIBLE: View.GONE);
        progressBar.setVisibility(isShow ? View.VISIBLE : View.GONE);
        statusTxt.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        itemDone = menu.findItem(R.id.item_done);
        itemDelete = menu.findItem(R.id.item_delete);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_done) {
            itemDone.setVisible(false);
            itemDelete.setVisible(true);
            Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.item_delete) {
            new Thread(new Worker(handler)).start();
//            itemDelete.setVisible(false);
//            itemDone.setVisible(true);
//            Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
