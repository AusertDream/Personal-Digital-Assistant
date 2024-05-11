package com.example.pda.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.pda.entity.DayAffair;
import com.example.pda.entity.MonthAffair;
import com.example.pda.entity.TallyRecord;
import com.example.pda.entity.YearAffair;

import java.util.ArrayList;

public class PDADataHelper extends SQLiteOpenHelper {

    private static final int dataBaseVersion = 3;
    private static final String dbName = "PDAData.db" ;

    //表名
    private static final String incomeTableName = "incomeTable"; //收入表
    private static final String expenseTableName = "expenseTable"; //支出表

    private static final String agendaDayTableName = "agendaDayTable"; //日程表
    private static final String agendaMonthTableName = "agendaMonthTable"; //月程表
    private static final String agendaYearTableName = "agendaYearTable"; //年程表

    private static PDADataHelper mHelper = null;
    SQLiteDatabase mRDB = null;
    SQLiteDatabase mWDB = null;

    public SQLiteDatabase openReadLink() {
        if(mRDB== null||!mRDB.isOpen())
            mRDB = mHelper.getReadableDatabase();
        return mRDB;
    }

    public SQLiteDatabase openWriteLink() {
        if(mWDB == null||!mWDB.isOpen())
            mWDB = mHelper.getWritableDatabase();
        return mWDB;
    }

    public void closeReadLink() {
        if(mRDB != null && mRDB.isOpen()) {
            mRDB.close();
            mRDB = null;
        }
    }

    public void closeWriteLink() {
        if(mWDB != null && mWDB.isOpen()) {
            mWDB.close();
            mWDB = null;
        }
    }

    private PDADataHelper(Context context) {
        super(context, dbName, null, dataBaseVersion);
    }

    //单例模式
    public static PDADataHelper getInstance(Context context){
        if(mHelper == null){
            mHelper = new PDADataHelper(context);
        }
        return mHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //建立收入表
        String sql = "CREATE TABLE IF NOT EXISTS "
                + incomeTableName
                +"(id INTEGER PRIMARY KEY AUTOINCREMENT, amount REAL, title TEXT, time TEXT )";
        db.execSQL(sql);
        //建立支出表
        sql = "CREATE TABLE IF NOT EXISTS "
                + expenseTableName
                +"(id INTEGER PRIMARY KEY AUTOINCREMENT, amount REAL, title TEXT, time TEXT )";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //建立日程表
        String sql = "CREATE TABLE IF NOT EXISTS "
                + agendaDayTableName
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT, hour INTEGER, minute INTEGER, affair TEXT) ";
        db.execSQL(sql);
        //建立月程表
        sql = "CREATE TABLE IF NOT EXISTS "
                + agendaMonthTableName
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT, month INTEGER, day INTEGER, affair TEXT) ";
        db.execSQL(sql);
        //建立年程表
        sql = "CREATE TABLE IF NOT EXISTS "
                + agendaYearTableName
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT, year INTEGER, month INTEGER, affair TEXT) ";
        db.execSQL(sql);
    }

    public long insertIncome(TallyRecord record){
        ContentValues values = new ContentValues();
        values.put("amount",record.getAmount());
        values.put("title",record.getTitle());
        values.put("time",record.getTime());
        long result = mWDB.insert(incomeTableName,null,values);
        return result;
    }

    public long deleteIncome(TallyRecord record){
        long result = mWDB.delete(incomeTableName,"title = ? and amount = ? and time = ?",
                new String[]{record.getTitle(),String.valueOf(record.getAmount()),record.getTime()});
        return result;
    }

    public long insertExpense(TallyRecord record){
        ContentValues values = new ContentValues();
        values.put("amount",record.getAmount());
        values.put("title",record.getTitle());
        values.put("time",record.getTime());
        long result = mWDB.insert(expenseTableName,null,values);
        return result;
    }

    public long deleteExpense(TallyRecord record){
        long result = mWDB.delete(expenseTableName,"title = ? and amount = ? and time = ?",
                new String[]{record.getTitle(),String.valueOf(record.getAmount()),record.getTime()});
        return result;
    }

    public ArrayList<TallyRecord> selectIncomeAll(){
        ArrayList<TallyRecord> res = new ArrayList<>();
        Cursor cursor = mRDB.query(incomeTableName,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            TallyRecord record = new TallyRecord();
            record.setAmount(cursor.getDouble(1));
            record.setTitle(cursor.getString(2));
            record.setTime(cursor.getString(3));
            res.add(record);
        }
        cursor.close();
        return res;
    }

    public ArrayList<TallyRecord> selectExpenseAll(){
        ArrayList<TallyRecord> res = new ArrayList<>();
        Cursor cursor = mRDB.query(expenseTableName,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            TallyRecord record = new TallyRecord();
            record.setAmount(cursor.getDouble(1));
            record.setTitle(cursor.getString(2));
            record.setTime(cursor.getString(3));
            res.add(record);
        }
        return res;
    }


    public long insertDayAffair(DayAffair dayAffair){
        ContentValues contentValues = new ContentValues();
        contentValues.put("hour",dayAffair.getHour());
        contentValues.put("minute",dayAffair.getMinute());
        contentValues.put("affair",dayAffair.getAffair());
        long result = mWDB.insert(agendaDayTableName,null,contentValues);
        return result;
    }

    public long deleteDayAffair(DayAffair dayAffair){
        long result = mWDB.delete(agendaDayTableName,"hour = ? and minute = ? and affair = ?",
                new String[]{String.valueOf(dayAffair.getHour()),String.valueOf(dayAffair.getMinute()),dayAffair.getAffair()});
        return result;
    }

    public long insertMonthAffair(MonthAffair monthAffair){
        ContentValues contentValues = new ContentValues();
        contentValues.put("month",monthAffair.getMonth());
        contentValues.put("day",monthAffair.getDay());
        contentValues.put("affair",monthAffair.getAffair());
        long result = mWDB.insert(agendaMonthTableName,null,contentValues);
        return result;
    }

    public long deleteMonthAffair(MonthAffair monthAffair){
        long result = mWDB.delete(agendaMonthTableName,"month = ? and day = ? and affair = ?",
                new String[]{String.valueOf(monthAffair.getMonth()),String.valueOf(monthAffair.getDay()),monthAffair.getAffair()});
        return result;
    }

    public long insertYearAffair(YearAffair yearAffair){
        ContentValues contentValues = new ContentValues();
        contentValues.put("year",yearAffair.getYear());
        contentValues.put("month",yearAffair.getMonth());
        contentValues.put("affair",yearAffair.getAffair());
        long result = mWDB.insert(agendaYearTableName,null,contentValues);
        return result;
    }

    public long deleteYearAffair(YearAffair yearAffair){
        long result = mWDB.delete(agendaYearTableName,"year = ? and month = ? and affair = ?",
                new String[]{String.valueOf(yearAffair.getYear()),String.valueOf(yearAffair.getMonth()),yearAffair.getAffair()});
        return result;
    }

    public ArrayList<DayAffair> selectDayAffairAll(){
        ArrayList<DayAffair> res = new ArrayList<>();
        Cursor cursor = mRDB.query(agendaDayTableName,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            DayAffair dayAffair = new DayAffair();
            dayAffair.setHour(cursor.getInt(1));
            dayAffair.setMinute(cursor.getInt(2));
            dayAffair.setAffair(cursor.getString(3));
            res.add(dayAffair);
        }
        cursor.close();
        return res;
    }

    public ArrayList<MonthAffair> selectMonthAffairAll(){
        ArrayList<MonthAffair> res = new ArrayList<>();
        Cursor cursor = mRDB.query(agendaMonthTableName,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            MonthAffair monthAffair = new MonthAffair();
            monthAffair.setMonth(cursor.getInt(1));
            monthAffair.setDay(cursor.getInt(2));
            monthAffair.setAffair(cursor.getString(3));
            res.add(monthAffair);
        }
        cursor.close();
        return res;
    }

    public ArrayList<YearAffair> selectYearAffairAll(){
        ArrayList<YearAffair> res = new ArrayList<>();
        Cursor cursor = mRDB.query(agendaYearTableName,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            YearAffair yearAffair = new YearAffair();
            yearAffair.setYear(cursor.getInt(1));
            yearAffair.setMonth(cursor.getInt(2));
            yearAffair.setAffair(cursor.getString(3));
            res.add(yearAffair);
        }
        cursor.close();
        return res;
    }
}
