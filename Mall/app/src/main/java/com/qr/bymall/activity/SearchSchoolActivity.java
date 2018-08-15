package com.qr.bymall.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qr.bymall.R;
import com.qr.bymall.adapter.SearchSchoolAdapter;
import com.qr.bymall.bean.SearchSchool;
import com.qr.bymall.util.BaseUtil;
import com.qr.bymall.view.OnMultiClickListener;
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

public class SearchSchoolActivity extends BaseActivity {

    private RelativeLayout back,layout;
    private EditText search;
    private TextView tv;
    private ListView lv;
    private SearchSchoolAdapter adapter;
    private List<SearchSchool.DataBean> schools;

    @Override
    public void addLayout() {
        setContentView(R.layout.activity_searchschool);
    }

    @Override
    public void initView() {
        back= (RelativeLayout) findViewById(R.id.search_school_back);
        layout= (RelativeLayout) findViewById(R.id.layout_school_default);
        search= (EditText) findViewById(R.id.edt_search_school);
        tv= (TextView) findViewById(R.id.tv_search_school);
        lv= (ListView) findViewById(R.id.lv_search_school);
        schools=new ArrayList<SearchSchool.DataBean>();
        tv.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                if (search.getText()!=null&&!search.getText().toString().equals("")){
                    getSearchSchool(search.getText().toString());
                }else {
                    Toast.makeText(SearchSchoolActivity.this, "请输入内容", Toast.LENGTH_SHORT).show();
                }
            }
        });
        back.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                gotoActivity(MainActivity.class,2);
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


    }

    public void getSearchSchool(String q){
        //1.初始化Retrofit
        //网址、解析工具（gosn等）
        ///v2/school/search
        final Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BaseUtil.url+"v2/school/").
                addConverterFactory(GsonConverterFactory.create())
                .build();
        //3.准备请求网络
        getSearchSchoolService service = retrofit.create(getSearchSchoolService.class);
        //4.通过回调获得结果
        Call<SearchSchool> call = service.getBean(q);
        //5.请求加入调度，正式排队去网络获取数据
        call.enqueue(new Callback<SearchSchool>() {
            @Override
            public void onResponse(Call<SearchSchool> call, Response<SearchSchool> response) {
                if (response.code()==200){
                    if (response.body().getCode()==1) {
                        schools=response.body().getData();
                        if (schools.size()!=0) {
                            lv.setVisibility(View.VISIBLE);
                            layout.setVisibility(View.GONE);
                            adapter = new SearchSchoolAdapter(getApplicationContext(), schools);
                            lv.setAdapter(adapter);
                        }else {
                            lv.setVisibility(View.GONE);
                            layout.setVisibility(View.VISIBLE);
                        }
                    }else if (response.body().getCode()==1000){
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }else if (response.body().getCode()==1001){
                        new AlertDialog.Builder(SearchSchoolActivity.this)
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
            public void onFailure(Call<SearchSchool> call, Throwable t) {
                Log.e("aa", "加载失败");

            }
        });
    }
    //定义接口
    public interface getSearchSchoolService{
        @GET("search")
        Call<SearchSchool> getBean(@Query("q") String q );
    }
}
