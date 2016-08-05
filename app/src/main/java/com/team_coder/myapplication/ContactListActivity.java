package com.team_coder.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class ContactListActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {
    // This is the Adapter being used to display the list's data
    MyContactAdapter mAdapter;

    // These are the Contacts rows that we will retrieve
    final String[] PROJECTION = new String[]{ContactsContract.Data._ID,
            ContactsContract.Data.DISPLAY_NAME};

    // This is the select criteria
    final String SELECTION = "((" +
            ContactsContract.Data.DISPLAY_NAME + " NOTNULL) AND (" +
            ContactsContract.Data.DISPLAY_NAME + " != '' ))";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        ListView listView = (ListView) findViewById(R.id.list_view);

        // For the cursor adapter, specify which columns go into which views
        String[] fromColumns = {CallLog.Calls.CACHED_NAME, CallLog.Calls.TYPE};
        int[] toViews = {android.R.id.text1, android.R.id.text2}; // The TextView in simple_list_item_1

//        // UI Thread
//        Cursor cursor = getContentResolver().query(
//                ContactsContract.Data.CONTENT_URI,
//                null,
//                null,
//                null,
//                null);

        // Create an empty adapter we will use to display the loaded data.
        // We pass null for the cursor, then update it in onLoadFinished()
        mAdapter = new MyContactAdapter(this,
                android.R.layout.simple_list_item_2, null,
                fromColumns, toViews, 0);

        listView.setAdapter(mAdapter);


        // Loader 동작
        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // background
        return new CursorLoader(
                this,
                CallLog.Calls.CONTENT_URI,
                null,
                CallLog.Calls.TYPE + "=1 AND " + CallLog.Calls.CACHED_NAME + "='마누라'",
                null,
                CallLog.Calls.DATE + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // ui thread
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    static class MyContactAdapter extends SimpleCursorAdapter {


        public MyContactAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
            super(context, layout, c, from, to, flags);

        }


    }
}
