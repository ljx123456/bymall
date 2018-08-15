package com.qr.bymall.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
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
import com.qr.bymall.bean.Code;
import com.qr.bymall.util.BaseUtil;
import com.qr.bymall.view.OnMultiClickListener;

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

public class ForgetActivity extends BaseActivity {

    private RelativeLayout back,look1,look2;
    private EditText edt_phone,edt_code,edt_password,edt_password_sure;
    private TextView tv;
    private Button btn;
    private Intent intent;
    private TextWatcher tw_phone,tw_password;
    private TimeCount time;
    private int m=0,flag1=0,flag2=0;

    @Override
    public void addLayout() {
        setContentView(R.layout.activity_forget);
    }

    @Override
    public void initView() {
        back= (RelativeLayout) findViewById(R.id.forget_back);
        look1= (RelativeLayout) findViewById(R.id.layout_forget_look1);
        look2= (RelativeLayout) findViewById(R.id.layout_forget_look2);
        edt_phone= (EditText) findViewById(R.id.edt_forget_phone);
        edt_code= (EditText) findViewById(R.id.edt_forget_code);
        edt_password= (EditText) findViewById(R.id.edt_forget_password);
        edt_password_sure= (EditText) findViewById(R.id.edt_forget_password_sure);
        tv= (TextView) findViewById(R.id.tv_forget_code);
        btn= (Button) findViewById(R.id.btn_forget);
        intent=getIntent();
        if (intent!=null&&intent.getStringExtra("phone")!=null&&!intent.getStringExtra("phone").equals("")){
            edt_phone.setText(intent.getStringExtra("phone"));
        }
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
                    tv.setTextColor(0xffca4553);
                }else {
                    tv.setTextColor(0x80ca4553);
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
                if (edt_phone.getText()!=null&&edt_phone.getText().length()==11&&edt_code.getText()!=null
                        &&edt_code.getText().length()==6&&edt_password.getText()!=null
                        &&edt_password.getText().length()>5&&editable.length()>5){
                    btn.setEnabled(true);
                }else {
                    btn.setEnabled(false);
                }
            }
        };
        edt_password_sure.addTextChangedListener(tw_password);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                if (edt_phone.getText()!=null&&edt_phone.getText().length()==11&&isMobile(edt_phone.getText().toString())){
                    //TODO 获取验证码
                    getCode(edt_phone.getText().toString());
                }else {
                    Toast.makeText(ForgetActivity.this, "请填写正确的手机号", Toast.LENGTH_SHORT).show();
                }
            }
        });
        look1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_password.getText()!=null&&edt_password.getText().length()>0) {
                    if (flag1 == 0) {
                        edt_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        flag1 = 1;
                    } else{
                        edt_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        flag1=0;
                    }
                }else {
                    Toast.makeText(ForgetActivity.this, "请输入", Toast.LENGTH_SHORT).show();
                }
            }
        });
        look2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_password_sure.getText()!=null&&edt_password_sure.getText().length()>0) {
                    if (flag2 == 0) {
                        edt_password_sure.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        flag2 = 1;
                    } else{
                        edt_password_sure.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        flag2=0;
                    }
                }else {
                    Toast.makeText(ForgetActivity.this, "请输入", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                if (edt_password.getText().toString().equals(edt_password_sure.getText().toString())){
                    //TODO
                    getPassword(edt_phone.getText().toString(),edt_code.getText().toString(),edt_password.getText().toString(),edt_password_sure.getText().toString());
                }else {
                    Toast.makeText(ForgetActivity.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
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
                        new AlertDialog.Builder(ForgetActivity.this)
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
                    Toast.makeText(ForgetActivity.this, response.message(), Toast.LENGTH_SHORT).show();
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
    public void getPassword(String number,String code,String password,String confirm_password){
        //1.初始化Retrofit
        //网址、解析工具（gosn等）
        // /open/mall/password/reset
        final Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BaseUtil.url+"open/mall/password/").
                addConverterFactory(GsonConverterFactory.create())
                .build();
        //3.准备请求网络
        getPasswordService service = retrofit.create(getPasswordService.class);
        //4.通过回调获得结果
        Call<Code> call = service.getBean(number,code,password,confirm_password);
        //5.请求加入调度，正式排队去网络获取数据
        call.enqueue(new Callback<Code>() {
            @Override
            public void onResponse(Call<Code> call, Response<Code> response) {
                if (response.code()==200){
                    if (response.body().getCode()==1){
                        Toast.makeText(ForgetActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        gotoActivity(LoginActivity.class,2);
                    }else if (response.body().getCode()==1000){
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }else if (response.body().getCode()==1001){
                        new AlertDialog.Builder(ForgetActivity.this)
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
                    Toast.makeText(ForgetActivity.this, response.message(), Toast.LENGTH_SHORT).show();
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
    public interface getPasswordService{
        @FormUrlEncoded
        @POST("reset")
        Call<Code> getBean(@Field("phone") String phone,@Field("code") String code,@Field("password") String password,@Field("confirm_password") String confirm_password);
    }


    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tv.setClickable(false);
            tv.setText("("+millisUntilFinished / 1000 +") 秒后可重新发送");
        }

        @Override
        public void onFinish() {
            tv.setText("重新获取验证码");
            tv.setClickable(true);

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
