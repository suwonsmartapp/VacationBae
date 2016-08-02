package com.team_coder.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // 부모꺼

        // 내꺼 추가
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text_view);

        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.memo_button).setOnClickListener(this);
        findViewById(R.id.call).setOnClickListener(this);
        findViewById(R.id.thread).setOnClickListener(this);
        findViewById(R.id.contact).setOnClickListener(this);

        Log.d(TAG, "onCreate: ");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState: ");
        if (savedInstanceState != null) {
            mTextView.setText(savedInstanceState.getString("data"));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d(TAG, "onSaveInstanceState: ");
        outState.putString("data", mTextView.getText().toString());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                Intent intent = new Intent(this, SecondActivity.class);
                startActivity(intent);
                break;
            case R.id.memo_button:
                startActivity(new Intent(this, MemoMainActivity.class));
                break;
            case R.id.call:
                String phoneNumber = ((Button)view).getText().toString();
                dialPhoneNumber(phoneNumber);
                break;
            case R.id.thread:
                new LongWorkTask().execute();
                break;
            case R.id.contact:
                startActivity(new Intent(this, ContactListActivity.class));
                break;
        }
    }

    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private class LongWorkTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.d(TAG, "onClick: 오래 걸리는 일 시작");
            try {
                Thread.sleep(3000);
                //publishProgress();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "onClick: 오래 걸리는 일 끝");
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mTextView.setText("끝났다");
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

    }

}
