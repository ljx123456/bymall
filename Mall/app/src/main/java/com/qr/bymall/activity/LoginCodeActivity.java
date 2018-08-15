package com.qr.bymall.activity;

import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qr.bymall.R;
import com.qr.bymall.bean.Code;
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

public class LoginCodeActivity extends BaseActivity {

    private RelativeLayout back,cancel;
    private EditText edt_phone,edt_code;
    private TextView tv_get,tv_password,tv_register;
    private Button btn;
    private TextWatcher tw_phone,tw_code;
    private TimeCount time;
    private int m=0;

    @Override
    public void addLayout() {
        setContentView(R.layout.activity_login_code);
    }

    @Override
    public void initView() {
        back= (RelativeLayout) findViewById(R.id.login_code_back);
        cancel= (RelativeLayout) findViewById(R.id.layout_login_code_cancel);
        edt_phone= (EditText) findViewById(R.id.edt_login_code_phone);
        edt_code= (EditText) findViewById(R.id.edt_login_code_code);
        tv_get= (TextView) findViewById(R.id.tv_login_code_get);
        tv_password= (TextView) findViewById(R.id.tv_login_password);
        tv_register= (TextView) findViewById(R.id.tv_login_code_register);
        btn= (Button) findViewById(R.id.btn_login_code);
        tw_phone=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (i==10&&i1==1&&m!=0) {
                    if (time!=null) {
                        time.onFinish();
                        time.cancel();
                        time = null;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length()==11){
                    tv_get.setTextColor(0xffca4553);
                    cancel.setVisibility(View.VISIBLE);
                }else {
                    tv_get.setTextColor(0x80ca4553);
                    cancel.setVisibility(View.GONE);
                }
            }
        };
        edt_phone.addTextChangedListener(tw_phone);
        tw_code=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length()==6&&edt_phone.getText()!=null&&edt_phone.getText().length()==11){
                    btn.setEnabled(true);
                }else {
                    btn.setEnabled(false);
                }
            }
        };
        edt_code.addTextChangedListener(tw_code);
        tv_get.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                //TODO 获取验证码
                if (edt_phone.getText()!=null&&edt_phone.getText().length()==11) {
                    if (isMobile(edt_phone.getText().toString())){
                        getCode(edt_phone.getText().toString());
                    }else {
                        Toast.makeText(LoginCodeActivity.this, "手机号不正确", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(LoginCodeActivity.this, "请输入完整的手机号", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                getLoginCode(edt_phone.getText().toString(),edt_code.getText().toString());
            }
        });
        tv_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoActivity(LoginActivity.class,2);
            }
        });
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoActivity(RegisterActivity.class,2);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoActivity(LoginActivity.class,2);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt_phone.setText("");
                if (time!=null) {
                    time.onFinish();
                    time.cancel();
                    time = null;
                }
            }
        });
    }
    //ToDo 获取验证码
    public void getCode(String number){
        //1.初始化Retrofit
        //网址、解析工具（gosn等）
        // /open/sms/code
        final Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BaseUtil.url+"open/sms/").
                addConverterFactory(GsonConverterFactory.create())
                .build();
        //3.准备请求网络
        getCodeService service = retrofit.create(getCodeService.class);
        //4.通过回调获得结果
        Call<Code> call = service.getBean(number);
        //5.请求加入调度，正式排队去网络获取数据
        call.enqueue(new Callback<Code>() {
            @Override
            public void onResponse(Call<Code> call, Response<Code> response) {
                if (response.code()==200){
                    if (response.body().getCode()==1){
                        Log.e("测试",""+response.body().getData());
                        m=1;
                        time = new TimeCount(60000, 1000);
                        time.start();
                    }else if (response.body().getCode()==1000){
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }else if (response.body().getCode()==1001){
                        new AlertDialog.Builder(LoginCodeActivity.this)
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
                    Toast.makeText(LoginCodeActivity.this, response.message(), Toast.LENGTH_SHORT).show();
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
    public interface getCodeService{
        @FormUrlEncoded
        @POST("code")
        Call<Code> getBean(@Field("phone") String phone);
    }
    //ToDo 获取验证码
    public void getLoginCode(String number,String code){
        //1.初始化Retrofit
        //网址、解析工具（gosn等）
        // /open/mall/phone/login
        final Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BaseUtil.url+"open/mall/phone/").
                addConverterFactory(GsonConverterFactory.create())
                .build();
        //3.准备请求网络
        getLoginCodeService service = retrofit.create(getLoginCodeService.class);
        //4.通过回调获得结果
        Call<User> call = service.getBean(number,code);
        //5.请求加入调度，正式排队去网络获取数据
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code()==200){
                    if (response.body().getCode()==1){
                        Toast.makeText(LoginCodeActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        SharedUtil.saveToken(getApplicationContext(),response.body().getData().getToken());
                        JPushInterface.setAlias(getApplicationContext(),1,response.body().getData().getToken());
                        SharedUtil.saveCustomer_id(getApplicationContext(),response.body().getData().getCustomer_id());
                        finish();
                    }else if (response.body().getCode()==1000){
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }else if (response.body().getCode()==1001){
                        new AlertDialog.Builder(LoginCodeActivity.this)
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
                    Toast.makeText(LoginCodeActivity.this, response.message(), Toast.LENGTH_SHORT).show();
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
    public interface getLoginCodeService{
        @FormUrlEncoded
        @POST("login")
        Call<User> getBean(@Field("phone") String phone,@Field("code") String code);
    }
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tv_get.setClickable(false);
            tv_get.setText("("+millisUntilFinished / 1000 +") 秒后可重新发送");
        }

        @Override
        public void onFinish() {
            tv_get.setText("重新获取验证码");
            tv_get.setClickable(true);

        }
    }
    public  boolean isMobile(String number) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        //"^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$"
        //"^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$"
        String num ="^1[3|4|5|7|8|9]\\d{9}$";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (edt_phone.getText().toString()==null) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }

}
