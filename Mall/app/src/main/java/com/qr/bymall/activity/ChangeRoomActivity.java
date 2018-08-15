package com.qr.bymall.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.qr.bymall.R;
import com.qr.bymall.adapter.NearByRoomAdapter;
import com.qr.bymall.bean.NearByRoom;
import com.qr.bymall.util.BaseUtil;
import com.qr.bymall.util.SharedUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by alu on 2018/6/29.
 */

public class ChangeRoomActivity extends BaseActivity {

    private LinearLayout layout;
    private TextView tv;
    private ListView lv;
    private NearByRoomAdapter adapter;
    private List<NearByRoom.DataBean.OtherBean> rooms;

    @Override
    public void addLayout() {
        setContentView(R.layout.activity_changeroom);
    }

    @Override
    public void initView() {
        layout= (LinearLayout) findViewById(R.id.layout_location_room);
        tv= (TextView) findViewById(R.id.tv_location_room);
        lv= (ListView) findViewById(R.id.lv_change_room);
        rooms=new ArrayList<NearByRoom.DataBean.OtherBean>();
        if (SharedUtil.getRoom_id(getApplicationContext()).equals("")
                &&SharedUtil.getRoom_Name(getApplicationContext()).equals("")){
            layout.setVisibility(View.GONE);
        }else {
            layout.setVisibility(View.VISIBLE);
        }
        getNearByRoom(SharedUtil.getSchool_id(getApplicationContext()),SharedUtil.getRoom_id(getApplicationContext()));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SharedUtil.saveRoom_id(getApplicationContext(),rooms.get(i).getRoom_id());
                SharedUtil.saveRoom_Name(getApplicationContext(),rooms.get(i).getRoom_name());
                gotoActivity(MainActivity.class,2);
            }
        });
    }

    public void getNearByRoom(String school_id,String room_id){
        //1.初始化Retrofit
        //网址、解析工具（gosn等）
        ///v2/school/room/list
        final Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BaseUtil.url+"v2/school/room/").
                addConverterFactory(GsonConverterFactory.create())
                .build();
        //3.准备请求网络
        getNearByRoomService service = retrofit.create(getNearByRoomService.class);
        //4.通过回调获得结果
        Call<NearByRoom> call = service.getBean(school_id,room_id);
        //5.请求加入调度，正式排队去网络获取数据
        call.enqueue(new Callback<NearByRoom>() {
            @Override
            public void onResponse(Call<NearByRoom> call, Response<NearByRoom> response) {
                if (response.code()==200){
                    if (response.body().getCode()==1) {
                        tv.setText(response.body().getData().getCurrent().getRoom_name());
                        rooms=response.body().getData().getOther();
                        adapter=new NearByRoomAdapter(getApplicationContext(),rooms);
                        lv.setAdapter(adapter);
                    }else if (response.body().getCode()==1000){
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }else if (response.body().getCode()==1001){
                        new AlertDialog.Builder(ChangeRoomActivity.this)
                                .setTitle("系统提示")
                                .setMessage(response.body().getMessage())
                                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .create()
                                .show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NearByRoom> call, Throwable t) {
                Log.e("aa", "加载失败");

            }
        });
    }
    //定义接口
    public interface getNearByRoomService{
        @GET("list")
        Call<NearByRoom> getBean(@Query("school_id") String school_id ,@Query("room_id") String room_id);
    }
}
