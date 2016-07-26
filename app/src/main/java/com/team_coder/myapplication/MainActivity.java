package com.team_coder.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
        }
    }

}
