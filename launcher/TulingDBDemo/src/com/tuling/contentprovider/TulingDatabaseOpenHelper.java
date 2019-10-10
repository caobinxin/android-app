package com.tuling.contentprovider;

import android.content.Context;
import android.content.UriMatcher;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class TulingDatabaseOpenHelper extends SQLiteOpenHelper {

    @SuppressWarnings("unused")
    private Context mContext;

    // ���ݿ�汾
    private static final int DATABASE_VERSION = 1;
    // ���ݿ��ļ���
    private static final String DATABASE_FILE_NAME = "tuling_settings.db";

    private static final String DATABASE_AUTHOR = "com.tuling.contentprovider.settings";

    // ����
    public static final String TABLE_USER_INFO = "user_info";
    // �û�ID
    public static final String FIELD_USER_ID = "_id";
    // �û�����
    public static final String FIELD_USER_NAME = "user_name";
    // �û��Ա�
    public static final String FIELD_USER_GENDER = "user_gender";
    // �û�����
    public static final String FIELD_USER_AGE = "user_age";

    // �������ݿ��
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_USER_INFO + " ( " + FIELD_USER_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FIELD_USER_NAME + " TEXT NOT NULL, " + FIELD_USER_GENDER
            + " TEXT NOT NULL, " + FIELD_USER_AGE + " INTGER DEFAULT 0 " + ");";

    // ɾ�����ݿ��
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER_INFO;
    
    // �������ݿ�Ļ���URI:content://com.tuling.contentprovider.settings/user_info
    public static final Uri BASE_URI = Uri.parse("content://" + DATABASE_AUTHOR + "/" + TABLE_USER_INFO);
    public static final int MATCHER_RESULT_ALL = 0;
    public static final int MATHCER_RESULT_ONE = 1;
    public static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(DATABASE_AUTHOR, TABLE_USER_INFO, MATCHER_RESULT_ALL);
        sURIMatcher.addURI(DATABASE_AUTHOR, TABLE_USER_INFO + "/#", MATHCER_RESULT_ONE);
    }

    // ���캯��ʵ��
    public TulingDatabaseOpenHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // �������ݿ��
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // ���ݿ�汾����
        if (oldVersion < newVersion) {
            db.beginTransaction();
            try {
                db.execSQL(DROP_TABLE);
                db.execSQL(CREATE_TABLE);
                db.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.endTransaction();
            }
        }
    }

}
