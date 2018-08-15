package com.qr.bymall.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qr.bymall.R;
import com.qr.bymall.bean.User;
import com.qr.bymall.util.BaseUtil;
import com.qr.bymall.view.OnMultiClickListener;
import com.qr.bymall.util.SharedUtil;

import cn.jpush.android.api.JPushInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by alu on 2018/7/2.
 */

public class LoginActivity extends BaseActivity {

    private RelativeLayout back,cancel,look;
    private TextView tv_forget,tv_code,tv_register;
    private EditText edt_phone,edt_password;
    private Button btn;
    private TextWatcher tw_phone,tw_password;
    private int flag=0;

    @Override
    public void addLayout() {
        setContentView(R.layout.activity_login);
    }

    @Override
    public void initView() {
        back= (RelativeLayout) findViewById(R.id.login_back);
        cancel= (RelativeLayout) findViewById(R.id.layout_login_cancel);
        look= (RelativeLayout) findViewById(R.id.layout_login_look);
        tv_forget= (TextView) findViewById(R.id.tv_login_forget);
        tv_code= (TextView) findViewById(R.id.tv_login_code);
        tv_register= (TextView) findViewById(R.id.tv_register);
        edt_phone= (EditText) findViewById(R.id.edt_login_phone);
        edt_password= (EditText) findViewById(R.id.edt_login_password);
        btn= (Button) findViewById(R.id.btn_login);
        tw_phone=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length()==11){
                    cancel.setVisibility(View.VISIBLE);
                }else {
                    cancel.setVisibility(View.GONE);
                }
            }
        };
        edt_phone.addTextChangedListener(tw_phone);
        tw_password=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length()>5&&edt_phone.getText()!=null&&edt_phone.getText().length()==11){
                    btn.setEnabled(true);
                }else {
                    btn.setEnabled(false);
                }
            }
        };
        edt_password.addTextChangedListener(tw_password);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoActivity(MainActivity.class,2);
            }
        });
        look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_password.getText()!=null&&edt_password.getText().length()>0) {
                    if (flag == 0) {
                        edt_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        flag = 1;
                    } else{
                        edt_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        flag=0;
                    }
                }else {
                    Toast.makeText(LoginActivity.this, "请输入", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt_phone.setText("");
            }
        });
        tv_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone="";
                if (edt_phone.getText()!=null&&edt_phone.getText().length()==11){
                    phone=edt_phone.getText().toString();
                }
                gotoActivity(ForgetActivity.class,"phone",phone,1);
            }
        });
        tv_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoActivity(LoginCodeActivity.class,2);
            }
        });
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoActivity(RegisterActivity.class,2);
            }
        });
        btn.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                getLogin(edt_phone.getText().toString(),edt_password.getText().toString());
            }
        });
    }
    //ToDo 获取验证码
    public void getLogin(String number,String code){
        //1.初始化Retrofit
        //网址、解析工具（gosn等）
        // /open/mall/password/login
        final Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BaseUtil.url+"open/mall/password/").
                addConverterFactory(GsonConverterFactory.create())
                .build();
        //3.准备请求网络
        getLoginService service = retrofit.create(getLoginService.class);
        //4.通过回调获得结果
        Call<User> call = service.getBean(number,code);
        //5.请求加入调度，正式排队去网络获取数据
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code()==200){
                    if (response.body().getCode()==1){
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        SharedUtil.saveToken(getApplicationContext(),response.body().getData().getToken());
                        Log.e("token",SharedUtil.getToken(getApplicationContext()));
                        JPushInterface.setAlias(getApplicationContext(),1,response.body().getData().getToken());
                        SharedUtil.saveCustomer_id(getApplicationContext(),response.body().getData().getCustomer_id());
                        gotoActivity(MainActivity.class,2);
                    }else if (response.body().getCode()==1000){
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }else if (response.body().getCode()==1001){
                        new AlertDialog.Builder(LoginActivity.this)
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
                    Toast.makeText(LoginActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("aa", "加载失败");
                Log.e("测试",t.toString());

            }
        });
    }
    //定义接口
    public interface getLoginService{
        @FormUrlEncoded
        @POST("login")
        Call<User> getBean(@Field("phone") String phone, @Field("code") String code);
    }
}
