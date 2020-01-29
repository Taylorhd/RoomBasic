package com.ecut.roombasic;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Word.class},version = 2,exportSchema = false)
public abstract class WordDataBase extends RoomDatabase {
    private static WordDataBase INSTANCE;
    static synchronized WordDataBase getDatabase(Context context){
        if (INSTANCE == null ){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),WordDataBase.class,"word_databse")
                    .addMigrations(MIGRATION_1_2)
                    .build();
        }
        return INSTANCE;
    }
    static  final Migration MIGRATION_1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE word ADD COLUMN foo INTEGER NOT NULL DEFAULT 1 ");
        }
    };
    public abstract WordDao getWordDao();
}
