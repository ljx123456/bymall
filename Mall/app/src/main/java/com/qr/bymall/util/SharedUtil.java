package com.qr.bymall.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by alu on 2018/6/29.
 */

public class SharedUtil {
    private static String fileName="tag";//共享参数文件名字
    private static String fileName2="token";
    private static String fileName3="school_id";
    private static String fileName4="room_id";
    private static String fileName5="school_name";
    private static String fileName6="room_name";
    private static String fileName7="search_text";
    private static String fileName8="customer_id";
    private static String fileName9="agent_id";
    private static String fileName10="products";
    public static void saveTag( Context context){
        SharedPreferences sp=context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("tag",true);//存入标签值true
        editor.commit();
    }

    public static boolean getTag(Context context){
        SharedPreferences sp=context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getBoolean("tag",false);//没取到默认值是false
    }

    public static void saveToken( Context context,String token){
        SharedPreferences sp=context.getSharedPreferences(fileName2, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("token",token);
        editor.commit();
    }
    public static String getToken(Context context){
        SharedPreferences sp=context.getSharedPreferences(fileName2, Context.MODE_PRIVATE);
        return sp.getString("token","");
    }
    public static void saveSchool_id( Context context,String school_id){
        SharedPreferences sp=context.getSharedPreferences(fileName3, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("school_id",school_id);
        editor.commit();
    }
    public static String getSchool_id(Context context){
        SharedPreferences sp=context.getSharedPreferences(fileName3, Context.MODE_PRIVATE);
        return sp.getString("school_id","");
    }
    public static void saveRoom_id( Context context,String room_id){
        SharedPreferences sp=context.getSharedPreferences(fileName4, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("room_id",room_id);
        editor.commit();
    }
    public static String getRoom_id(Context context){
        SharedPreferences sp=context.getSharedPreferences(fileName4, Context.MODE_PRIVATE);
        return sp.getString("room_id","");
    }
    public static void saveSchool_Name( Context context,String school_name){
        SharedPreferences sp=context.getSharedPreferences(fileName5, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("school_name",school_name);
        editor.commit();
    }
    public static String getSchool_Name(Context context){
        SharedPreferences sp=context.getSharedPreferences(fileName5, Context.MODE_PRIVATE);
        return sp.getString("school_name","");
    }
    public static void saveRoom_Name( Context context,String room_name){
        SharedPreferences sp=context.getSharedPreferences(fileName6, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("room_name",room_name);
        editor.commit();
    }
    public static String getRoom_Name(Context context){
        SharedPreferences sp=context.getSharedPreferences(fileName6, Context.MODE_PRIVATE);
        return sp.getString("room_name","");
    }
    public static void saveSearch_Text( Context context,String search_text){
        SharedPreferences sp=context.getSharedPreferences(fileName7, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("search_text",search_text);
        editor.commit();
    }
    public static String getSearch_Text(Context context){
        SharedPreferences sp=context.getSharedPreferences(fileName7, Context.MODE_PRIVATE);
        return sp.getString("search_text","");
    }
    public static void saveCustomer_id( Context context,int customer_id){
        SharedPreferences sp=context.getSharedPreferences(fileName8, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putInt("customer_id",customer_id);
        editor.commit();
    }
    public static int getCustomer_id(Context context){
        SharedPreferences sp=context.getSharedPreferences(fileName8, Context.MODE_PRIVATE);
        return sp.getInt("customer_id",0);
    }
    public static void saveAgent_id( Context context,String agent_id){
        SharedPreferences sp=context.getSharedPreferences(fileName9, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("agent_id",agent_id);
        editor.commit();
    }
    public static String getAgent_id(Context context){
        SharedPreferences sp=context.getSharedPreferences(fileName9, Context.MODE_PRIVATE);
        return sp.getString("agent_id","0");
    }
    public static void saveProducts( Context context,String products){
        SharedPreferences sp=context.getSharedPreferences(fileName10, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("products",products);
        editor.commit();
    }
    public static String getProducts(Context context){
        SharedPreferences sp=context.getSharedPreferences(fileName10, Context.MODE_PRIVATE);
        return sp.getString("products","");
    }
}
