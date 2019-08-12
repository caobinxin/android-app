package com.tuling.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class TulingContentProvider extends ContentProvider {

    private TulingDatabaseOpenHelper mDatabaseHelper;

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int match_result = TulingDatabaseOpenHelper.sURIMatcher.match(uri);
        int delete_effect = -1;
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        switch (match_result) {
            // ����������Ҫ���µ�����
            case TulingDatabaseOpenHelper.MATCHER_RESULT_ALL:
                delete_effect = db.delete(TulingDatabaseOpenHelper.TABLE_USER_INFO, selection, selectionArgs);
                break;
            // ͨ��ָ����_id�ֶ�ֵ�������������
            case TulingDatabaseOpenHelper.MATHCER_RESULT_ONE:
                String current_where = TulingDatabaseOpenHelper.FIELD_USER_ID + " = ?";
                String query_id = uri.getPathSegments().get(1);
                String[] current_args = new String[] { query_id };
                delete_effect = db.delete(TulingDatabaseOpenHelper.TABLE_USER_INFO, current_where, current_args);
                break;
            default:
                throw new UnsupportedOperationException("Invaild URI");
        }
        // ͨ��BASE_URI֪ͨӦ�ó������ݿⷢ���˱仯
        if (delete_effect > 0) {
            getContext().getContentResolver().notifyChange(TulingDatabaseOpenHelper.BASE_URI, null);
        }

        return delete_effect;
    }

    @Override
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int match_result = TulingDatabaseOpenHelper.sURIMatcher.match(uri);
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        switch (match_result) {
            case TulingDatabaseOpenHelper.MATCHER_RESULT_ALL:
            case TulingDatabaseOpenHelper.MATHCER_RESULT_ONE:
                long result_id = db.insert(TulingDatabaseOpenHelper.TABLE_USER_INFO, null, values);
                Uri result_uri = Uri.withAppendedPath(TulingDatabaseOpenHelper.BASE_URI, String.valueOf(result_id));
                // ͨ��BASE_URI֪ͨӦ�ó������ݿⷢ���˱仯
                if (result_id >= 0) {
                    getContext().getContentResolver().notifyChange(TulingDatabaseOpenHelper.BASE_URI, null);
                }
                return result_uri;
            default:
                throw new UnsupportedOperationException("Invaild URI");
        }
    }

    @Override
    public boolean onCreate() {
        mDatabaseHelper = new TulingDatabaseOpenHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int match_result = TulingDatabaseOpenHelper.sURIMatcher.match(uri);
        Cursor result_cursor = null;
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        switch (match_result) {
            // ��ѯ������Ҫ������
            case TulingDatabaseOpenHelper.MATCHER_RESULT_ALL:
                result_cursor = db.query(TulingDatabaseOpenHelper.TABLE_USER_INFO, projection, selection, selectionArgs,
                        null, null, sortOrder);
                return result_cursor;
            // ͨ��ָ����_id�ֶ�ֵ����ѯ�������
            case TulingDatabaseOpenHelper.MATHCER_RESULT_ONE:
                String current_where = TulingDatabaseOpenHelper.FIELD_USER_ID + " = ?";
                String query_id = uri.getPathSegments().get(1);
                String[] current_args = new String[] { query_id };
                result_cursor = db.query(TulingDatabaseOpenHelper.TABLE_USER_INFO, projection, current_where,
                        current_args, null, null, sortOrder);
                return result_cursor;
            default:
                throw new UnsupportedOperationException("Invaild URI");
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        int match_result = TulingDatabaseOpenHelper.sURIMatcher.match(uri);
        int update_effect = -1;
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        switch (match_result) {
            // ����������Ҫ���µ�����
            case TulingDatabaseOpenHelper.MATCHER_RESULT_ALL:
                update_effect = db.update(TulingDatabaseOpenHelper.TABLE_USER_INFO, values, selection, selectionArgs);
                break;
            // ͨ��ָ����_id�ֶ�ֵ�������������
            case TulingDatabaseOpenHelper.MATHCER_RESULT_ONE:
                String current_where = TulingDatabaseOpenHelper.FIELD_USER_ID + " = ?";
                String query_id = uri.getPathSegments().get(1);
                String[] current_args = new String[] { query_id };
                update_effect = db.update(TulingDatabaseOpenHelper.TABLE_USER_INFO, values, current_where,
                        current_args);
                break;
            default:
                throw new UnsupportedOperationException("Invaild URI");
        }
        // ͨ��BASE_URI֪ͨӦ�ó������ݿⷢ���˱仯
        if (update_effect > 0) {
            getContext().getContentResolver().notifyChange(TulingDatabaseOpenHelper.BASE_URI, null);
        }

        return update_effect;
    }
}
