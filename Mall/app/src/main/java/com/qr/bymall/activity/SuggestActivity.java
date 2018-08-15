package com.qr.bymall.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qr.bymall.R;
import com.qr.bymall.bean.Code;
import com.qr.bymall.util.BaseUtil;
import com.qr.bymall.util.SharedUtil;
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
 * Created by alu on 2018/7/26.
 */

public class SuggestActivity extends BaseActivity {

    private RelativeLayout back;
    private EditText edt_suggestion,edt_phone;
    private TextView tv;

    @Override
    public void addLayout() {
        setContentView(R.layout.activity_suggest);
    }

    @Override
    public void initView() {
        back= (RelativeLayout) findViewById(R.id.suggest_back);
        edt_suggestion= (EditText) findViewById(R.id.edt_suggestion);
        edt_phone= (EditText) findViewById(R.id.edt_suggestion_phone);
        tv= (TextView) findViewById(R.id.tv_suggest);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                if (edt_suggestion.getText()!=null&&!edt_suggestion.getText().toString().equals("")
                        &&edt_phone.getText()!=null&&!edt_phone.getText().toString().equals("")){
                    getSuggest(SharedUtil.getToken(getApplicationContext()),edt_suggestion.getText().toString(),edt_phone.getText().toString());
                }else {
                    Toast.makeText(SuggestActivity.this, "请填写完整", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getSuggest(String token,String advice,String number){
        //1.初始化Retrofit
        //网址、解析工具（gosn等）
        // /v2/customer/feedback
        final Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BaseUtil.url+"v2/customer/").
                addConverterFactory(GsonConverterFactory.create())
                .build();
        //3.准备请求网络
        getSuggestService service = retrofit.create(getSuggestService.class);
        //4.通过回调获得结果
        Call<Code> call = service.getBean(token, advice, number);
        //5.请求加入调度，正式排队去网络获取数据
        call.enqueue(new Callback<Code>() {
            @Override
            public void onResponse(Call<Code> call, Response<Code> response) {
                if (response.code()==200){
                    if (response.body().getCode()==1){
                        Log.e("测试",""+response.body().getData());
                        Toast.makeText(SuggestActivity.this, "提交成功,感谢您的反馈", Toast.LENGTH_SHORT).show();
                        finish();
                    }else if (response.body().getCode()==1000){
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }else if (response.body().getCode()==1001){
                        new AlertDialog.Builder(SuggestActivity.this)
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
                    Toast.makeText(getApplicationContext(), "登录过期，请重新登录", Toast.LENGTH_SHORT).show();
                    gotoActivity(LoginActivity.class,1);
                }else {
                    Toast.makeText(SuggestActivity.this, response.message(), Toast.LENGTH_SHORT).show();
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
    public interface getSuggestService{
        @FormUrlEncoded
        @POST("feedback")
        Call<Code> getBean(@Field("token") String token,@Field("advice") String advice,@Field("phone") String phone);
    }
}
