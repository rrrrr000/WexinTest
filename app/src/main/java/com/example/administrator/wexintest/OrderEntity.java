package com.example.administrator.wexintest;

import java.util.List;

/**
 * Created by Administrator on 2016/12/1 0001.
 * 订单实体类, 为了方便使用, 暂时不做封装
 */

public class OrderEntity {
    public String send_num; //送货单号
    public String order_num; //订单号
    public String sender; //发送人
    public String sender_phone;
    public String reciver;  //收货人
    public String reciver_phone; //收货人联系方式
    public String send_date; //发送日期
    public String order_comment; //备注

    public List<Detail> list;

    public OrderEntity(String send_num, String order_num, String sender, String sender_phone, String reciver, String reciver_phone, String send_date, String order_comment, List<Detail> list) {
        this.send_num = send_num;
        this.order_num = order_num;
        this.sender = sender;
        this.sender_phone = sender_phone;
        this.reciver = reciver;
        this.reciver_phone = reciver_phone;
        this.send_date = send_date;
        this.order_comment = order_comment;
        this.list = list;
    }

    public static class Detail {



        public String order_num; //订单号
        public String save_num; //存货编码
        public String product_name; //商品名称
        public String format; //规格
        public String model;//型号
        public String unit;//单位
        public String price;//单价
        public String count;//数量
        public String dead_time;//有效期
        public String target_address;//送达地点
        public String target_date;//送达日期
        public String productor;//生产厂家
        public String start_tempreture;//启运温度
        public String end_tempreture;//送达温度
        public String productor_index;//生产批次号
        public String detail_comment;//备注


        public Detail(String order_num, String save_num, String product_name, String format, String model, String unit, String price, String count, String dead_time, String target_address, String target_date, String productor, String start_tempreture, String end_tempreture, String productor_index, String detail_comment) {
            this.order_num = order_num;
            this.save_num = save_num;
            this.product_name = product_name;
            this.format = format;
            this.model = model;
            this.unit = unit;
            this.price = price;
            this.count = count;
            this.dead_time = dead_time;
            this.target_address = target_address;
            this.target_date = target_date;
            this.productor = productor;
            this.start_tempreture = start_tempreture;
            this.end_tempreture = end_tempreture;
            this.productor_index = productor_index;
            this.detail_comment = detail_comment;
        }

        @Override
        public String toString() {
            return "Detail{" +
                    "order_num='" + order_num + '\'' +
                    ", save_num='" + save_num + '\'' +
                    ", product_name='" + product_name + '\'' +
                    ", format='" + format + '\'' +
                    ", model='" + model + '\'' +
                    ", unit='" + unit + '\'' +
                    ", price='" + price + '\'' +
                    ", count='" + count + '\'' +
                    ", dead_time='" + dead_time + '\'' +
                    ", target_address='" + target_address + '\'' +
                    ", target_date='" + target_date + '\'' +
                    ", productor='" + productor + '\'' +
                    ", start_tempreture='" + start_tempreture + '\'' +
                    ", end_tempreture='" + end_tempreture + '\'' +
                    ", productor_index='" + productor_index + '\'' +
                    ", detail_comment='" + detail_comment + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "send_num='" + send_num + '\'' +
                ", order_num='" + order_num + '\'' +
                ", sender='" + sender + '\'' +
                ", sender_phone='" + sender_phone + '\'' +
                ", reciver='" + reciver + '\'' +
                ", reciver_phone='" + reciver_phone + '\'' +
                ", send_date='" + send_date + '\'' +
                ", order_comment='" + order_comment + '\'' +
                ", list=" + list.size()+
                '}';
    }
}
