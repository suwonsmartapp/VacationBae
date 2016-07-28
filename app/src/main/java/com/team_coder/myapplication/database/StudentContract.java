package com.team_coder.myapplication.database;

import android.provider.BaseColumns;

/**
 * 학생 계약 클래스
 */
public final class StudentContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public StudentContract() {
    }

    /* Inner class that defines the table contents */
    public static abstract class StudentEntry implements BaseColumns {
        public static final String TABLE_NAME = "student";

        public static final String COLUMN_NAME_NAME = "name";
    }
}

