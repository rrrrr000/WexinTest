package com.example.administrator.wexintest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/1 0001.
 *
 * 数据库操作工具类
 * 注意:暂时没考虑数据库操作的耗时问题, 全部在UI线程操作
 *      实际开发中应该把数据库操作放入新的线程中执行
 */

public class DbDao {
    SQLiteDatabase db;
    public DbDao(Context context){
        DataHelper helper = new DataHelper(context, "oder.db", null, 1);
        db = helper.getReadableDatabase();
    }

    // // // // // // // // // // 插入操作// // // // // // // // // // // //

    /**
     * 插入一个集合到数据库
     * */
    public void insertList(List<OrderEntity> list){
        for (OrderEntity orderEntity : list){
            insert(orderEntity);
        }
    }

    /**
     * 插入单个的OrderEntity对象到数据库
     *
     * 使用事务保证插入操作的原子性
     * */
    public synchronized void insert(OrderEntity entity){
        db.beginTransaction();
        try{
            ContentValues orderValues = orderToValues(entity);
            db.insert(TableContans.ORDER_TABLE, null, orderValues);
            List<OrderEntity.Detail> list = entity.list;
            for(OrderEntity.Detail detail : list){
                ContentValues detailValues = detailToValues(detail);
                long insert = db.insert(TableContans.DETAIL_TABLE, null, detailValues);
                Log.e("ws","插入系ixixxixix！"+insert);
            }
            Log.e("ws","插入OrderEntity对象到数据库成功！");
            db.setTransactionSuccessful();
        }catch (Exception e){
            Log.d("DbDao", "数据库事务执行失败:"+e.toString());
        } finally {
            db.endTransaction();
        }
    }

    // // // // // // // // // // 查询操作// // // // // // // // // // // //

    /**
     * 查询所有订单
     *
     * */
    public synchronized List<OrderEntity> queryAllOrder() {
        List<OrderEntity> orderList = new ArrayList<>();
        String queryAll = "select * from " + TableContans.ORDER_TABLE;
        Cursor orderCursor = db.rawQuery(queryAll, null);
        while (orderCursor.moveToNext()) {
            String send_num = orderCursor.getString(orderCursor.getColumnIndex(TableContans.O_SEND_NUM));
            String order_num = orderCursor.getString(orderCursor.getColumnIndex(TableContans.O_ORDER_NUM));
            String sender = orderCursor.getString(orderCursor.getColumnIndex(TableContans.O_SENDER));
            String sender_phone = orderCursor.getString(orderCursor.getColumnIndex(TableContans.O_SENDER_PHONE));
            String reciver = orderCursor.getString(orderCursor.getColumnIndex(TableContans.O_RECEIVER));
            String reciver_phone = orderCursor.getString(orderCursor.getColumnIndex(TableContans.O_RECEIVER_PHONE));
            String send_date = orderCursor.getString(orderCursor.getColumnIndex(TableContans.O_SEND_DATE));
            String order_comment = orderCursor.getString(orderCursor.getColumnIndex(TableContans.O_ORDER_COMMENT));

            List<OrderEntity.Detail> detailList = new ArrayList<>();
            String queryAllDetail = "select * from " + TableContans.DETAIL_TABLE + " where " + TableContans.O_ORDER_NUM
                    + "=" + order_num;
            Cursor detailCursor = db.rawQuery(queryAllDetail, null);
            while(detailCursor.moveToNext()){
                String order_num1 = detailCursor.getString(detailCursor.getColumnIndex(TableContans.O_ORDER_NUM));
                String save_num = detailCursor.getString(detailCursor.getColumnIndex(TableContans.D_SAVE_NUM));
                String product_name = detailCursor.getString(detailCursor.getColumnIndex(TableContans.D_PRODUCT_NAME));
                String format = detailCursor.getString(detailCursor.getColumnIndex(TableContans.D_FORMAT));
                String model = detailCursor.getString(detailCursor.getColumnIndex(TableContans.D_MODEL));
                String unit = detailCursor.getString(detailCursor.getColumnIndex(TableContans.D_UNIT));
                String price = detailCursor.getString(detailCursor.getColumnIndex(TableContans.D_PRICE));
                String count = detailCursor.getString(detailCursor.getColumnIndex(TableContans.D_COUNT));
                String dead_time = detailCursor.getString(detailCursor.getColumnIndex(TableContans.D_DEAD_TIME));
                String target_address = detailCursor.getString(detailCursor.getColumnIndex(TableContans.D_TARGET_ADDRESS));
                String target_date = detailCursor.getString(detailCursor.getColumnIndex(TableContans.D_TARGET_DATE));
                String productor = detailCursor.getString(detailCursor.getColumnIndex(TableContans.D_PRODUCTOR));
                String start_tempreture = detailCursor.getString(detailCursor.getColumnIndex(TableContans.D_START_TEMPRETURE));
                String end_tempreture = detailCursor.getString(detailCursor.getColumnIndex(TableContans.D_END_TEMPRETURE));
                String productor_index = detailCursor.getString(detailCursor.getColumnIndex(TableContans.D_PRODUCT_INDEX));
                String detail_comment = detailCursor.getString(detailCursor.getColumnIndex(TableContans.D_DETAIL_COMMENT));

                OrderEntity.Detail detail = new OrderEntity.Detail(order_num1, save_num, product_name, format, model,
                        unit, price, count, dead_time, target_address, target_date, productor, start_tempreture,
                        end_tempreture, productor_index, detail_comment);
                detailList.add(detail);
            }

            OrderEntity entity = new OrderEntity(send_num, order_num, sender, sender_phone, reciver,
                    reciver_phone, send_date, order_comment, detailList);
            orderList.add(entity);
        }
        Log.e("ws","查询所有");
        return orderList;
    }


    // // // // // // // // // // 实体类转换操作// // // // // // // // // // // //

    /**
     * 将Detail对象转成ContentValues对象
     *
     * */
    public ContentValues detailToValues(OrderEntity.Detail detail) {
        Log.e("ws","将detail转换成ContentValues");
        ContentValues values = new ContentValues();
        values.put(TableContans.O_ORDER_NUM, detail.order_num);
        values.put(TableContans.D_SAVE_NUM, detail.save_num);
        values.put(TableContans.D_PRODUCT_NAME, detail.product_name);
        values.put(TableContans.D_FORMAT, detail.format);
        values.put(TableContans.D_MODEL, detail.model);
        values.put(TableContans.D_UNIT, detail.unit);
        values.put(TableContans.D_PRICE, detail.price);
        values.put(TableContans.D_COUNT, detail.count);
        values.put(TableContans.D_DEAD_TIME, detail.dead_time);
        values.put(TableContans.D_TARGET_ADDRESS, detail.target_address);
        values.put(TableContans.D_TARGET_DATE, detail.target_date);
        values.put(TableContans.D_PRODUCTOR, detail.productor);
        values.put(TableContans.D_START_TEMPRETURE, detail.start_tempreture);
        values.put(TableContans.D_END_TEMPRETURE, detail.end_tempreture);
        values.put(TableContans.D_PRODUCT_INDEX, detail.productor_index);
        values.put(TableContans.D_DETAIL_COMMENT, detail.detail_comment);
        return values;
    }

    /**
     * 将OrderEntity对象基本信息转成ContentValues对象
     *
     * */
    public ContentValues orderToValues(OrderEntity entity) {
        Log.e("ws","OrderEntity对象转换成ContentValues");
        ContentValues values = new ContentValues();
        values.put(TableContans.O_SEND_NUM, entity.send_num);
        values.put(TableContans.O_ORDER_NUM, entity.order_num);
        values.put(TableContans.O_SENDER, entity.sender);
        values.put(TableContans.O_SENDER_PHONE, entity.sender_phone);
        values.put(TableContans.O_RECEIVER, entity.reciver);
        values.put(TableContans.O_RECEIVER_PHONE, entity.reciver_phone);
        values.put(TableContans.O_SEND_DATE, entity.send_date);
        values.put(TableContans.O_ORDER_COMMENT, entity.order_comment);
        return values;
    }
}
