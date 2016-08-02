package com.team_coder.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.team_coder.myapplication.database.StudentContract;
import com.team_coder.myapplication.database.StudentDbHelper;

public class MemoMainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_NEW_MEMO = 1;

    private SecondActivity.StudentCursorAdapter mAdapter;
    private StudentDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_main);

        mDbHelper = new StudentDbHelper(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MemoMainActivity.this, MemoEditActivity.class),
                        REQUEST_CODE_NEW_MEMO);
            }
        });

        Cursor cursor = mDbHelper.getStudents();

        // Data
        mAdapter = new SecondActivity.StudentCursorAdapter(this, cursor);

        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) parent.getAdapter().getItem(position);

                Intent intent = new Intent(MemoMainActivity.this, MemoEditActivity.class);
                intent.putExtra("name", cursor.getString(cursor.getColumnIndexOrThrow(StudentContract.StudentEntry.COLUMN_NAME_NAME)));
                intent.putExtra("id", id);
                startActivityForResult(intent, REQUEST_CODE_NEW_MEMO);
            }
        });

        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.action_delete:
                // 삭제
                if (mDbHelper.deleteStudent(info.id) != 0) {
                    mAdapter.swapCursor(mDbHelper.getStudents());
                }

                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_NEW_MEMO) {
            if (resultCode == RESULT_OK) {
                // 데이터 추가
                String addData = data.getStringExtra("name");
                long id = data.getLongExtra("id", -1);
                if (id == -1) {

                    // DB에 추가 작업
                    long newRowId = mDbHelper.insertStudent(addData);

                    // 반영
                    mAdapter.swapCursor(mDbHelper.getStudents());

                    if (newRowId == -1) {
                        Toast.makeText(MemoMainActivity.this, "에러", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MemoMainActivity.this, "성공", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    // 수정
                    int updated = mDbHelper.updateStudent(id, addData);

                    if (updated != 0) {
                        mAdapter.swapCursor(mDbHelper.getStudents());
                    }
                }

                // 데이터 변경 됨을 어댑터에 알려주기
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}
