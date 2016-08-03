package com.team_coder.myapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.team_coder.myapplication.service.MyIntentService;
import com.team_coder.myapplication.service.MyService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView mTextView;

    private MyService mService;
    private boolean mBound;

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MyService.MyBinder binder = (MyService.MyBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

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
        findViewById(R.id.start_service).setOnClickListener(this);
        findViewById(R.id.intent_start_service).setOnClickListener(this);
        findViewById(R.id.bind_service).setOnClickListener(this);
        findViewById(R.id.control_bind_service).setOnClickListener(this);

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

        // Bind to LocalService
        Intent intent = new Intent(this, MyService.class);

        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
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
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
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
            case R.id.start_service:
                Intent serviceBasicIntent = new Intent(this, MyService.class);
                serviceBasicIntent.setAction("start");
                startService(serviceBasicIntent);
                break;
            case R.id.intent_start_service:
                Intent intentServiceIntent = new Intent(this, MyIntentService.class);
                intentServiceIntent.setAction("start");
                startService(intentServiceIntent);
                break;
            case R.id.bind_service:

                break;
            case R.id.control_bind_service:
                mService.control();
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
