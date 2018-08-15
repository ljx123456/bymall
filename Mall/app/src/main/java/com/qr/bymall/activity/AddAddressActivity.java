package com.qr.bymall.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.qr.bymall.R;
import com.qr.bymall.bean.Code;
import com.qr.bymall.util.BaseUtil;
import com.qr.bymall.view.OnMultiClickListener;
import com.qr.bymall.util.SharedUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by alu on 2018/7/13.
 */

public class AddAddressActivity extends BaseActivity {

    private RelativeLayout back,hold;
    private EditText edt_name,edt_phone,edt_address;
    private TextView tv_school,tv_male,tv_female;
    private CheckBox cb_male,cb_female;
    private Switch aSwitch;
    private int flag=0;
    private int sex=2;
    private String str="1";
    private Intent intent;

    @Override
    public void addLayout() {
        setContentView(R.layout.activity_add_address);
    }

    @Override
    public void initView() {
        back= (RelativeLayout) findViewById(R.id.add_address_back);
        hold= (RelativeLayout) findViewById(R.id.layout_add_address_hold);
        edt_name= (EditText) findViewById(R.id.edt_add_address_name);
        edt_phone= (EditText) findViewById(R.id.edt_add_address_phone);
        edt_address= (EditText) findViewById(R.id.edt_add_address_address);
        tv_male= (TextView) findViewById(R.id.tv_add_address_male);
        tv_female= (TextView) findViewById(R.id.tv_add_address_female);
        tv_school= (TextView) findViewById(R.id.tv_add_address_school);
        cb_male= (CheckBox) findViewById(R.id.checkbox_add_address_male);
        cb_female= (CheckBox) findViewById(R.id.checkbox_add_address_female);
        aSwitch= (Switch) findViewById(R.id.switch_add_address);
        intent=getIntent();
        if (intent!=null&&intent.getStringExtra("flag")!=null){
            str=intent.getStringExtra("flag");
        }
        if (SharedUtil.getSchool_id(getApplicationContext())!=null&&SharedUtil.getRoom_id(getApplicationContext())!=null&&!SharedUtil.getSchool_id(getApplicationContext()).equals("")){
            tv_school.setText(SharedUtil.getSchool_Name(getApplicationContext())+SharedUtil.getRoom_Name(getApplicationContext()));
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        hold.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                if (edt_name.getText()!=null&&!edt_name.getText().toString().equals("")&&
                        edt_phone.getText()!=null&&!edt_phone.getText().toString().equals("")&&
                        edt_address.getText()!=null&&!edt_address.getText().toString().equals("")&& sex!=2) {
                    getAddAddress(SharedUtil.getToken(getApplicationContext()), edt_name.getText().toString(),
                            edt_phone.getText().toString(),SharedUtil.getSchool_id(getApplicationContext()),
                            SharedUtil.getRoom_id(getApplicationContext()),sex,edt_address.getText().toString(),flag);
                }else {
                    Toast.makeText(AddAddressActivity.this, "请填写完整的信息", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cb_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cb_male.isChecked()) {
                    cb_male.setChecked(true);
                    cb_female.setChecked(false);
                    sex=0;
                    tv_male.setTextColor(0xff444444);
                    tv_female.setTextColor(0xff7a7a7a);
                }
            }
        });
        cb_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cb_female.isChecked()) {
                    cb_female.setChecked(true);
                    cb_male.setChecked(false);
                    sex=1;
                    tv_female.setTextColor(0xff444444);
                    tv_male.setTextColor(0xff7a7a7a);
                }
            }
        });
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    aSwitch.setChecked(true);
                    flag=1;
                }else {
                    aSwitch.setChecked(false);
                    flag=0;
                }
            }
        });

    }
    public void getAddAddress(String token,String name,String phone,String school,String room,int sex,String detail,int flag){
        //1.初始化Retrofit
        //网址、解析工具（gosn等）
        // /v2/address/add
        final Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BaseUtil.url+"v2/address/").
                addConverterFactory(GsonConverterFactory.create())
                .build();
        //3.准备请求网络
        getAddAddressService service = retrofit.create(getAddAddressService.class);
        //4.通过回调获得结果
        Call<Code> call = service.getBean(token,name,phone,school,room,sex,detail,flag);
        //5.请求加入调度，正式排队去网络获取数据
        call.enqueue(new Callback<Code>() {
            @Override
            public void onResponse(Call<Code> call, Response<Code> response) {
                if (response.code()==200){
                    if (response.body().getCode()==1){
                        Toast.makeText(AddAddressActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                        gotoActivity(AddressActivity.class,"flag",str,2);
                    }else if (response.body().getCode()==1000){
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }else if (response.body().getCode()==1001){
                        new AlertDialog.Builder(AddAddressActivity.this)
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
                }else if (response.code()==401){
                    Toast.makeText(AddAddressActivity.this, "登录过期，请重新登录", Toast.LENGTH_SHORT).show();
                    gotoActivity(LoginActivity.class,2);
                }else {
                    Toast.makeText(AddAddressActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Code> call, Throwable t) {
                Log.e("aa", "加载失败");
                Log.e("测试",t.toString());

            }
        });
    }
    //定义接口
    public interface getAddAddressService{
        @FormUrlEncoded
        @POST("add")
        Call<Code> getBean(@Field("token") String token,@Field("rec_name") String rec_name,@Field("rec_phone") String phone,
                           @Field("rec_school") String school, @Field("rec_room") String room,@Field("sex") int sex,
                           @Field("rec_detail") String rec_detail, @Field("default_flag") int default_flag);
    }
}
