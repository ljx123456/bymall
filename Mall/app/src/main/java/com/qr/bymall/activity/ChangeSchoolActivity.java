package com.qr.bymall.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qr.bymall.R;
import com.qr.bymall.adapter.NearBySchoolAdapter;
import com.qr.bymall.bean.NearBySchool;
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
 * Created by alu on 2018/6/28.
 */

public class ChangeSchoolActivity extends BaseActivity {

    private RelativeLayout back;
    private TextView tv_search,tv_school;
    private ListView lv;
    private NearBySchoolAdapter adapter;
    private List<NearBySchool.DataBean.NearBean> schools;

    @Override
    public void addLayout() {
        setContentView(R.layout.activity_changeschool);
    }

    @Override
    public void initView() {
        back= (RelativeLayout) findViewById(R.id.change_school_back);
        tv_search= (TextView) findViewById(R.id.tv_change_school);
        tv_school= (TextView) findViewById(R.id.tv_location_school);
        lv= (ListView) findViewById(R.id.lv_change_school);
        schools=new ArrayList<NearBySchool.DataBean.NearBean>();
        if (!SharedUtil.getSchool_id(getApplicationContext()).equals("")) {
            getNearBySchool(SharedUtil.getSchool_id(getApplicationContext()));
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SharedUtil.getSchool_id(getApplicationContext()).equals("")){
                    Toast.makeText(ChangeSchoolActivity.this, "请选择学校", Toast.LENGTH_SHORT).show();
                }else {
                    gotoActivity(MainActivity.class, 2);
                }
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!schools.get(i).getSchool_id().equals(SharedUtil.getSchool_id(getApplicationContext()))) {
                    SharedUtil.saveSchool_id(getApplicationContext(), schools.get(i).getSchool_id());
                    SharedUtil.saveSchool_Name(getApplicationContext(), schools.get(i).getSchool_name());
                    SharedUtil.saveRoom_id(getApplicationContext(),"");
                    SharedUtil.saveRoom_Name(getApplicationContext(),"");
                }
                gotoActivity(ChangeRoomActivity.class,2);
            }
        });
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoActivity(SearchSchoolActivity.class,1);
            }
        });
    }

    public void getNearBySchool(String school_id){
        //1.初始化Retrofit
        //网址、解析工具（gosn等）
        ///v2/school/list
        final Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BaseUtil.url+"v2/school/").
                addConverterFactory(GsonConverterFactory.create())
                .build();
        //3.准备请求网络
        getNearBySchoolService service = retrofit.create(getNearBySchoolService.class);
        //4.通过回调获得结果
        Call<NearBySchool> call = service.getBean(school_id);
        //5.请求加入调度，正式排队去网络获取数据
        call.enqueue(new Callback<NearBySchool>() {
            @Override
            public void onResponse(Call<NearBySchool> call, Response<NearBySchool> response) {
                if (response.code()==200){
                    if (response.body().getCode()==1) {
                        tv_school.setText(response.body().getData().getCurrent().getSchool_name());
                        schools=response.body().getData().getNear();
                        adapter=new NearBySchoolAdapter(getApplicationContext(),schools);
                        lv.setAdapter(adapter);
                    }else if (response.body().getCode()==1000){
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }else if (response.body().getCode()==1001){
                        new AlertDialog.Builder(ChangeSchoolActivity.this)
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
                    }else if (response.body().getCode()==1003){
                        gotoActivity(SearchSchoolActivity.class,1);
                    }
                }else {
                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NearBySchool> call, Throwable t) {
                Log.e("aa", "加载失败");

            }
        });
    }
    //定义接口
    public interface getNearBySchoolService{
        @GET("list")
        Call<NearBySchool> getBean(@Query("school_id") String school_id );
    }
}
