package com.example.administrator.wexintest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Administrator on 2016/12/1 0001.
 */

public class DataHelper extends SQLiteOpenHelper{
    public DataHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // 订单表建表语句
        String createOrder = "CREATE TABLE " + TableContans.ORDER_TABLE + "("
                + TableContans.O_SEND_NUM + " varchar, "
                + TableContans.O_ORDER_NUM + " varchar UNIQUE, "            // 订单号唯一性
                + TableContans.O_SENDER + " varchar, "
                + TableContans.O_SENDER_PHONE + " varchar,"
                + TableContans.O_RECEIVER + " varchar, "
                + TableContans.O_RECEIVER_PHONE + " varchar,"
                + TableContans.O_SEND_DATE + " varchar, "
                + TableContans.O_ORDER_COMMENT + " varchar"
                +")";

        // 详情表建表语句
        String createOrderDetail = "CREATE TABLE " + TableContans.DETAIL_TABLE + "("
                + TableContans.D_ID + " integer primary key autoincrement, "    // 主键自增
                + TableContans.O_ORDER_NUM + " varchar, "                       // 订单号关联Order表
                + TableContans.D_SAVE_NUM + " varchar, "
                + TableContans.D_PRODUCT_NAME + " varchar, "
                + TableContans.D_FORMAT + " varchar,"
                + TableContans.D_MODEL + " varchar,"
                + TableContans.D_UNIT + " varchar,"
                + TableContans.D_PRICE + " varchar, "
                + TableContans.D_COUNT + " varchar, "
                + TableContans.D_DEAD_TIME + " varchar, "
                + TableContans.D_TARGET_ADDRESS + " varchar, "
                + TableContans.D_TARGET_DATE + " varchar, "
                + TableContans.D_PRODUCTOR + " varchar, "
                + TableContans.D_START_TEMPRETURE + " varchar, "
                + TableContans.D_END_TEMPRETURE + " varchar, "
                + TableContans.D_PRODUCT_INDEX + " varchar, "
                + TableContans.D_DETAIL_COMMENT + " varchar"
                +")";

        // 执行建表语句
        db.execSQL(createOrder);
        db.execSQL(createOrderDetail);
        Log.e("ws","创建表格！");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
