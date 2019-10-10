package com.tuling.contentprovider;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

    private ContentObserver mTulingDatabaseObserver = new ContentObserver(null) {
        public void onChange(boolean selfChange) {
            android.util.Log.e("TulingTag", "mTulingDatabaseObserver::onChange= " + selfChange);
        };
    };

    private UserData mCurrentUser;
    private DatabaseUtils mUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ×¢²áÊý¾Ý¿â¼àÌý
        getContentResolver().registerContentObserver(TulingDatabaseOpenHelper.BASE_URI, true, mTulingDatabaseObserver);
        getContentResolver().unregisterContentObserver(mTulingDatabaseObserver);

        mUtils = new DatabaseUtils(this);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mCurrentUser = mUtils.getCurrentUserData();
        mUtils.setUserData(mCurrentUser);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public final class UserData {

        private long mUserId;
        private String mUserName;
        private String mUserGender;
        private String mUserAge;

        public UserData(long UserId, String UserName, String UserGender, String UserAge) {

            mUserId = UserId;
            mUserName = UserName;
            mUserGender = UserGender;
            mUserAge = UserAge;
        }

        public long getUserId() {
            return mUserId;
        }

        public void setUserId(long userId) {
            mUserId = userId;
        }

        public String getUserName() {
            return mUserName;
        }

        public void setUserName(String userName) {
            mUserName = userName;
        }

        public String getUserGender() {
            return mUserGender;
        }

        public void setUserGender(String userGender) {
            mUserGender = userGender;
        }

        public String getUserAge() {
            return mUserAge;
        }

        public void setUserAge(String userAge) {
            mUserAge = userAge;
        }

    }

    public final class DatabaseUtils {

        private Context mContext;

        private UserData mCurrentUser;

        public DatabaseUtils(Context ctx) {
            // TODO Auto-generated constructor stub
            mContext = ctx;
        }

        public void setUserData(UserData data) {
            mCurrentUser = data;
        }

        public UserData getCurrentUserData() {

            UserData current_data = null;
            Cursor user_cursor = null;
            try {
                user_cursor = mContext.getContentResolver().query(TulingDatabaseOpenHelper.BASE_URI, null, null, null,
                        null);

                if (user_cursor.moveToFirst()) {
                    current_data = new UserData(user_cursor.getLong(0), user_cursor.getString(1),
                            user_cursor.getString(2), user_cursor.getString(3));
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (user_cursor != null)
                    user_cursor.close();
            }

            return current_data;
        }

        public void deleteRecord() {
            long current_user_id = mCurrentUser.getUserId();
            Uri delete_uri = Uri.withAppendedPath(TulingDatabaseOpenHelper.BASE_URI, String.valueOf(current_user_id));
            int effect_rows = mContext.getContentResolver().delete(delete_uri, null, null);
            android.util.Log.e("TuringTag", "delete result=  " + effect_rows);
        }

        public void insertRecord(String user_name, String user_gender, String user_age) {
            ContentValues insert_values = new ContentValues();
            insert_values.put(TulingDatabaseOpenHelper.FIELD_USER_NAME, user_name);
            insert_values.put(TulingDatabaseOpenHelper.FIELD_USER_GENDER, user_gender);
            insert_values.put(TulingDatabaseOpenHelper.FIELD_USER_AGE, user_age);
            Uri insert_uri = mContext.getContentResolver().insert(TulingDatabaseOpenHelper.BASE_URI, insert_values);
            android.util.Log.e("TuringTag", "insert result=  " + insert_uri);
        }

    }

}
