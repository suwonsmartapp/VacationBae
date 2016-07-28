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

import java.util.ArrayList;
import java.util.List;

public class MemoMainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_NEW_MEMO = 1;

    private List<Student> mData;
    private SecondActivity.StudentAdapter mAdapter;
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
        mData = new ArrayList<>();
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(StudentContract.StudentEntry.COLUMN_NAME_NAME)
            );
            long itemId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(StudentContract.StudentEntry._ID)
            );

            mData.add(new Student(name, (int) itemId));
        }

        // Adapter
        mAdapter = new SecondActivity.StudentAdapter(mData);

        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Student student = (Student) parent.getAdapter().getItem(position);
                Intent intent = new Intent(MemoMainActivity.this, MemoEditActivity.class);
                intent.putExtra("name", student.getName());
                intent.putExtra("position", position);
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
                mData.remove(info.position);
                mAdapter.notifyDataSetChanged();
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
                int position = data.getIntExtra("position", -1);
                if (position == -1) {

                    // DB 작업
                    long newRowId = mDbHelper.insertStudent(addData);

                    // 추가
                    mData.add(new Student(addData, (int) newRowId));

                    if (newRowId == -1) {
                        Toast.makeText(MemoMainActivity.this, "에러", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MemoMainActivity.this, "성공", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    // 수정
                    Student modify = mData.get(position);
                    modify.setName(addData);
                }

                // 데이터 변경 됨을 어댑터에 알려주기
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}
