package com.team_coder.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by junsuk on 16. 7. 25..
 */
public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_second);

        // View
        GridView listView = (GridView) findViewById(R.id.list_view);

        // Data
        List<Student> data = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            data.add(new Student("아무개" + i, i + 1));
        }

        // Adapter
        StudentAdapter adapter = new StudentAdapter(data);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SecondActivity.this, "click : " + position, Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SecondActivity.this, "long click : " + position, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    public static class StudentAdapter extends BaseAdapter {
        private static final String TAG = StudentAdapter.class.getSimpleName();

        private List<Student> mData;

        public StudentAdapter(List<Student> data) {
            mData = data;
        }

        @Override
        public int getCount() {
            // 데이타가 몇 개냐?
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            // position 번 째 데이터
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            // 각 아이템의 고유 아이디
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // 각 포지션의 레이아웃
            Log.d(TAG, "getView: " + position);

            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext())
                        .inflate(android.R.layout.simple_list_item_2, parent, false);
                TextView name = (TextView) convertView.findViewById(android.R.id.text1);
                TextView number = (TextView) convertView.findViewById(android.R.id.text2);

                holder = new ViewHolder();
                holder.name = name;
                holder.number = number;

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Student student = mData.get(position);

            holder.name.setText(student.getName());
            holder.number.setText("" + student.getNumber());

            return convertView;
        }

        static class ViewHolder {
            TextView name;
            TextView number;
        }
    }

}
