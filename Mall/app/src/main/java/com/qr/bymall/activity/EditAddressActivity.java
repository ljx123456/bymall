package com.qr.bymall.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.qr.bymall.R;
import com.qr.bymall.bean.Address;
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
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by alu on 2018/7/13.
 */

public class EditAddressActivity extends BaseActivity {

    private RelativeLayout back,hold;
    private EditText edt_name,edt_phone,edt_address;
    private TextView tv_school,tv_male,tv_female,tv_del;
    private CheckBox cb_male,cb_female;
    private Switch aSwitch;
    private Intent intent;
    private int flag=0;
    private int sex=2;
    private String id,school_id,room_id;

    @Override
    public void addLayout() {
        setContentView(R.layout.activity_edit_address);
    }

    @Override
    public void initView() {
        back= (RelativeLayout) findViewById(R.id.edit_address_back);
        hold= (RelativeLayout) findViewById(R.id.layout_edit_address_hold);
        tv_del= (TextView) findViewById(R.id.tv_edit_address_del);
        edt_name= (EditText) findViewById(R.id.edt_edit_address_name);
        edt_phone= (EditText) findViewById(R.id.edt_edit_address_phone);
        edt_address= (EditText) findViewById(R.id.edt_edit_address_address);
        tv_male= (TextView) findViewById(R.id.tv_edit_address_male);
        tv_female= (TextView) findViewById(R.id.tv_edit_address_female);
        tv_school= (TextView) findViewById(R.id.tv_edit_address_school);
        cb_male= (CheckBox) findViewById(R.id.checkbox_edit_address_male);
        cb_female= (CheckBox) findViewById(R.id.checkbox_edit_address_female);
        aSwitch= (Switch) findViewById(R.id.switch_edit_address);
        intent=getIntent();
        if (intent!=null&&!intent.getStringExtra("address_id").equals("")){
            id=intent.getStringExtra("address_id");
            getAddress(SharedUtil.getToken(getApplicationContext()),intent.getStringExtra("address_id"));
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoActivity(AddressActivity.class,0);
            }
        });
        hold.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                if (edt_name.getText()!=null&&!edt_name.getText().toString().equals("")&&
                        edt_phone.getText()!=null&&!edt_phone.getText().toString().equals("")&&
                        edt_address.getText()!=null&&!edt_address.getText().toString().equals("")&& sex!=2) {
                    getEditAddress(id,SharedUtil.getToken(getApplicationContext()), edt_name.getText().toString(),
                            edt_phone.getText().toString(),school_id, room_id,sex,edt_address.getText().toString(),flag);
                }else {
                    Toast.makeText(EditAddressActivity.this, "请填写完整的信息", Toast.LENGTH_SHORT).show();
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
        tv_del.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                showDialog();
            }
        });
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(EditAddressActivity.this);
        View view = View
                .inflate(getApplicationContext(), R.layout.dialog_hold_address, null);
        builder.setView(view);
//        builder.setCancelable(true);
//        TextView title= (TextView) view
//                .findViewById(R.id.title);//设置标题
        TextView tv_nav= (TextView) view.findViewById(R.id.tv_dialog_hold_address_nav);
        TextView tv_pos= (TextView) view.findViewById(R.id.tv_dialog_hold_address_pos);
        //取消或确定按钮监听事件处理
        final AlertDialog dialog = builder.create();
        Window window=dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        tv_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tv_pos.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                getDelAddress(SharedUtil.getToken(getApplicationContext()),id);
            }
        });
        dialog.show();
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        DisplayMetrics dm = new DisplayMetrics();
        m.getDefaultDisplay().getMetrics(dm);
        android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
        p.height = (int)(120*dm.density);   //高度设置为屏幕的0.3
        p.width = (int)(276*dm.density);    //宽度设置为屏幕的0.5
        dialog.getWindow().setAttributes(p);     //设置生效


    }
    public void getAddress(String token, final String id){
        //1.初始化Retrofit
        //网址、解析工具（gosn等）
        //  /v2/address/detail/{id}
        final Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BaseUtil.url+"v2/address/").
                addConverterFactory(GsonConverterFactory.create())
                .build();
        //3.准备请求网络
        getAddressService service = retrofit.create(getAddressService.class);
        //4.通过回调获得结果
        Call<Address> call = service.getBean(id,token);
        //5.请求加入调度，正式排队去网络获取数据
        call.enqueue(new Callback<Address>() {
            @Override
            public void onResponse(Call<Address> call, Response<Address> response) {
                if (response.code()==200){
                    if (response.body().getCode()==1) {
                        edt_name.setText(response.body().getData().getRec_name());
                        if (response.body().getData().getSex().equals("0")){
                            cb_male.setChecked(true);
                            cb_female.setChecked(false);
                            sex=0;
                            tv_male.setTextColor(0xff444444);
                            tv_female.setTextColor(0xff7a7a7a);
                        }else {
                            cb_female.setChecked(true);
                            cb_male.setChecked(false);
                            sex=1;
                            tv_female.setTextColor(0xff444444);
                            tv_male.setTextColor(0xff7a7a7a);
                        }
                        edt_phone.setText(response.body().getData().getRec_phone());
                        tv_school.setText(response.body().getData().getSchool()+response.body().getData().getRoom());
                        edt_address.setText(response.body().getData().getRec_detail());
                        school_id=response.body().getData().getRec_school();
                        room_id=response.body().getData().getRec_room();
                        if (response.body().getData().getDefault_flag().equals("0")){
                            aSwitch.setChecked(false);
                            flag=0;
                        }else {
                            aSwitch.setChecked(true);
                            flag=1;
                        }
                    }else if (response.body().getCode()==1000){
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }else if (response.body().getCode()==1001){
                        new AlertDialog.Builder(EditAddressActivity.this)
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
                    Toast.makeText(EditAddressActivity.this, "登录过期，请重新登录", Toast.LENGTH_SHORT).show();
                    gotoActivity(LoginActivity.class,1);
                }else {
                    Toast.makeText(EditAddressActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Address> call, Throwable t) {
                Log.e("aa", "加载失败");

            }
        });
    }
    //定义接口
    public interface getAddressService{
        @GET("detail/{id}")
        Call<Address> getBean(@Path("id") String id, @Query("token") String token);
    }
    public void getEditAddress(String id,String token,String name,String phone,String school,String room,int sex,String detail,int flag){
        //1.初始化Retrofit
        //网址、解析工具（gosn等）
        // /v2/address/edit/{id}
        final Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BaseUtil.url+"v2/address/").
                addConverterFactory(GsonConverterFactory.create())
                .build();
        //3.准备请求网络
        getEditAddressService service = retrofit.create(getEditAddressService.class);
        //4.通过回调获得结果
        Call<Code> call = service.getBean(id,token,name,phone,school,room,sex,detail,flag);
        //5.请求加入调度，正式排队去网络获取数据
        call.enqueue(new Callback<Code>() {
            @Override
            public void onResponse(Call<Code> call, Response<Code> response) {
                if (response.code()==200){
                    if (response.body().getCode()==1){
                        Toast.makeText(EditAddressActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        gotoActivity(AddressActivity.class,2);
                    }else if (response.body().getCode()==1000){
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }else if (response.body().getCode()==1001){
                        new AlertDialog.Builder(EditAddressActivity.this)
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
                    Toast.makeText(EditAddressActivity.this, "登录过期，请重新登录", Toast.LENGTH_SHORT).show();
                    gotoActivity(LoginActivity.class,2);
                }else {
                    Toast.makeText(EditAddressActivity.this, response.message(), Toast.LENGTH_SHORT).show();
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
    public interface getEditAddressService{
        @FormUrlEncoded
        @POST("edit/{id}")
        Call<Code> getBean(@Path("id") String id,@Field("token") String token, @Field("rec_name") String rec_name, @Field("rec_phone") String phone,
                           @Field("rec_school") String school, @Field("rec_room") String room, @Field("sex") int sex,
                           @Field("rec_detail") String rec_detail, @Field("default_flag") int default_flag);
    }
    public void getDelAddress(String token, final String id){
        //1.初始化Retrofit
        //网址、解析工具（gosn等）
        //  /v2/address/del/{id}
        final Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BaseUtil.url+"v2/address/").
                addConverterFactory(GsonConverterFactory.create())
                .build();
        //3.准备请求网络
        getDelAddressService service = retrofit.create(getDelAddressService.class);
        //4.通过回调获得结果
        Call<Code> call = service.getBean(id,token);
        //5.请求加入调度，正式排队去网络获取数据
        call.enqueue(new Callback<Code>() {
            @Override
            public void onResponse(Call<Code> call, Response<Code> response) {
                if (response.code()==200){
                    if (response.body().getCode()==1) {
                        Toast.makeText(EditAddressActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        gotoActivity(AddressActivity.class,2);
                    }else if (response.body().getCode()==1000){
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }else if (response.body().getCode()==1001){
                        new AlertDialog.Builder(EditAddressActivity.this)
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
                    Toast.makeText(EditAddressActivity.this, "登录过期，请重新登录", Toast.LENGTH_SHORT).show();
                    gotoActivity(LoginActivity.class,1);
                }else {
                    Toast.makeText(EditAddressActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Code> call, Throwable t) {
                Log.e("aa", "加载失败");

            }
        });
    }
    //定义接口
    public interface getDelAddressService{
        @GET("del/{id}")
        Call<Code> getBean(@Path("id") String id, @Query("token") String token);
    }
}
