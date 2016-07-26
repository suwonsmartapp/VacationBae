package com.team_coder.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

public class MemoEditActivity extends AppCompatActivity {

    private EditText mNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_edit);

        mNameEditText = (EditText) findViewById(R.id.name_edit);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        mNameEditText.setText(name);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.memo_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                // 저장
                Intent intent = new Intent();
                intent.putExtra("name", mNameEditText.getText().toString());
                intent.putExtra("position", getIntent().getIntExtra("position", -1));
                setResult(RESULT_OK, intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
