package com.example.administrator.wexintest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    private static final int REQUEST_IMAGE = 2;

    private EditText edt_order;
    private TextView tv_orders;
    private TextView tv_result;
    private ImageView iv_result;

    private DbDao db;

    private boolean hasOrder = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    private void initData() {
        db = new DbDao(this);
        SharedPreferences sp = getSharedPreferences("config", Context.MODE_PRIVATE);
        hasOrder = sp.getBoolean("hasOrder", false);
        if(!hasOrder){      // 没有订单信息, 则插入几条订单信息到数据库

            // 插入数据后把hasOrder置为true, 通过SharedPrefrence持久化数据
            // 此处暂不考虑数据插入失败的情况
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("hasOrder", true);
            editor.commit();

            // 构造临时数据插入
            // 订单1
            List<OrderEntity.Detail> detailList1 = new ArrayList<>();
            OrderEntity.Detail d1 = new OrderEntity.Detail("10001", "100", "貂皮大衣", null,
                    null, null, null, null, null, null, null, null, null, null, null,
                    null);
            OrderEntity.Detail d2 = new OrderEntity.Detail("10001", "37", "皮尔卡丹公文包", null,
                    null, null, null, null, null, null, null, null, null, null, null,
                    null);
            OrderEntity.Detail d3 = new OrderEntity.Detail("10001", "80", "耐克运动鞋", null,
                    null, null, null, null, null, null, null, null, null, null, null,
                    null);
            detailList1.add(d1);
            detailList1.add(d2);
            detailList1.add(d3);

            OrderEntity entity1 = new OrderEntity(null, "10001", null, null, null, null, null, null, detailList1);
            db.insert(entity1);

            // 订单2
            List<OrderEntity.Detail> detailList2 = new ArrayList<>();
            OrderEntity.Detail d4 = new OrderEntity.Detail("10002", "100", "连衣裙", null,
                    null, null, null, null, null, null, null, null, null, null, null,
                    null);
            OrderEntity.Detail d5 = new OrderEntity.Detail("10002", "37", "披肩", null,
                    null, null, null, null, null, null, null, null, null, null, null,
                    null);
            detailList2.add(d4);
            detailList2.add(d5);

            OrderEntity entity2 = new OrderEntity(null, "10002", null, null, null, null, null, null, detailList2);
            db.insert(entity2);

            // 订单3
            List<OrderEntity.Detail> detailList3 = new ArrayList<>();
            OrderEntity.Detail d6 = new OrderEntity.Detail("10003", "121", "连衣裙", null,
                    null, null, null, null, null, null, null, null, null, null, null,
                    null);
            OrderEntity.Detail d7 = new OrderEntity.Detail("10003", "27", "雨靴", null,
                    null, null, null, null, null, null, null, null, null, null, null,
                    null);
            detailList3.add(d6);
            detailList3.add(d7);

            OrderEntity entity3 = new OrderEntity(null, "10003", null, "110", null, null, null, null, detailList3);
            db.insert(entity3);

            Log.e("ws","插入了3调数据！");
        }
    }

    private void initView() {
        iv_result = (ImageView) findViewById(R.id.iv_result);
        edt_order = (EditText) findViewById(R.id.edt_order);
        tv_result = (TextView) findViewById(R.id.tv_result);
        tv_orders = (TextView) findViewById(R.id.tv_orders);

    }

    public void click(View v){
        switch (v.getId()){
            case R.id.btn_read:         // 扫描并解析二维码
                startActivityForResult(new Intent(this, CaptureActivity.class),
                        REQUEST_CODE);
                break;

            case R.id.btn_create:       // 根据订单文字生成二维码, 并设置到ImageView
                String content = edt_order.getText().toString();
                Bitmap bmp = CodeUtils.createImage(content, 400, 400, null);
                iv_result.setImageBitmap(bmp);
                break;

            case R.id.btn_query:        // 查询所有订单, 将订单号显示到TextView
                StringBuffer buffer = new StringBuffer();   // 有可能多线程操作, 使用StringBuffer拼接字符串
                buffer.append("订单列表:\n");
                for (OrderEntity orderEntity : db.queryAllOrder()) {
                    buffer.append(orderEntity.order_num + "\n");
                }
                tv_orders.setText(buffer.toString());
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    for(OrderEntity orderEntity:db.queryAllOrder()){
                        if (result.equals( orderEntity.order_num)){
                            Toast.makeText(MainActivity.this, "解析结果:" + orderEntity.toString(), Toast.LENGTH_LONG).show();
                            Log.e("ws",orderEntity.toString());
                            tv_result.setText(orderEntity.toString());

                        }
                    }

                   // Toast.makeText(this, "解析扫码结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
        /**
         * 选择系统图片并解析
         */
        else if (requestCode == REQUEST_IMAGE) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    CodeUtils.analyzeBitmap(uri.getPath(), new CodeUtils.AnalyzeCallback() {
                        @Override
                        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                            for(OrderEntity orderEntity:db.queryAllOrder()){
                                if (result.equals( orderEntity.order_num)){
                                    Toast.makeText(MainActivity.this, "解析结果:" + orderEntity.toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                        @Override
                        public void onAnalyzeFailed() {
                            Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
