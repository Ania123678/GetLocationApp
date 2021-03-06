package android.example.com.getlocationapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import androidx.annotation.Nullable;

class DataBase extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "LocationDatabase.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "location_information";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_LONGITUDE = "longitude";
    private static final String COLUMN_LATITUDE = "latitude";
    private static final String COLUMN_ALTITUDE = "altitude";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_SPEED = "speed";
    private static final String COLUMN_ACCURACY = "accuracy";


    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    private static DataBase databaseInstance = null;
    public static DataBase getInstance(Context context) {
        if (databaseInstance == null) {

            databaseInstance = new DataBase(context);

        }
        return databaseInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LONGITUDE + " TEXT, " +
                COLUMN_LATITUDE + " TEXT, " +
                COLUMN_ALTITUDE + " TEXT, " +
                COLUMN_TIME + " TEXT, " +
                COLUMN_SPEED + " TEXT, " +
                COLUMN_ACCURACY + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addInfo(String longitude, String latitude, String altitude, String time, String speed, String accuracy){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_LONGITUDE, longitude);
        cv.put(COLUMN_LATITUDE, latitude);
        cv.put(COLUMN_ALTITUDE, altitude);
        cv.put(COLUMN_TIME, time);
        cv.put(COLUMN_SPEED, speed);
        cv.put(COLUMN_ACCURACY, accuracy);
        long result = db.insert(TABLE_NAME,null,cv);
        if(result == -1){
            Toast.makeText(context, "Failed to Add Location InfoTo Database", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Success to Add Location InfoTo Database", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor listData(){
        String query = "SELECT *  FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
}