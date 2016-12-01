package com.example.administrator.wexintest;

/**
 * Created by Administrator on 2016/12/1 0001.
 */

public class TableContans {
    // 数据库表名
    public static final String ORDER_TABLE = "order_table";               // 订单表
    public static final String DETAIL_TABLE = "detail_table";             // 详情表

    // 订单表字段名
    public static final String O_SEND_NUM = "send_num";                   // 送货单号
    public static final String O_ORDER_NUM = "order_num";                 // 订单号
    public static final String O_SENDER = "sender";                       // 发货人
    public static final String O_SENDER_PHONE = "sender_phone";           // 发货人电话
    public static final String O_RECEIVER = "reciver";                    // 收货人
    public static final String O_RECEIVER_PHONE = "reciver_phone";        // 收货人电话
    public static final String O_SEND_DATE = "send_date";                 // 发货日期
    public static final String O_ORDER_COMMENT = "order_comment";         // 订单备注

    // 详情表字段名
    public static final String D_ID = "_id";                              // 序号
    public static final String D_SAVE_NUM = "save_num";                   // 存货编号
    public static final String D_PRODUCT_NAME = "product_name";           // 商品名
    public static final String D_FORMAT = "format";                       // 规格
    public static final String D_MODEL = "model";                         // 型号
    public static final String D_UNIT = "unit";                           // 单位
    public static final String D_PRICE = "price";                         // 单价
    public static final String D_COUNT = "count";                         // 数量
    public static final String D_DEAD_TIME = "dead_time";                 // 有效期
    public static final String D_TARGET_ADDRESS = "target_address";       // 送达地点
    public static final String D_TARGET_DATE = "target_date";             // 送达日期
    public static final String D_PRODUCTOR = "productor";                 // 生产厂家
    public static final String D_START_TEMPRETURE = "start_tempreture";   // 启运温度
    public static final String D_END_TEMPRETURE = "end_tempreture";       // 送达温度
    public static final String D_PRODUCT_INDEX = "productor_index";       // 生产批次号
    public static final String D_DETAIL_COMMENT = "detail_comment";       // 详情备注
}
