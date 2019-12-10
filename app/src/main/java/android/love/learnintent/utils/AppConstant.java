package android.love.learnintent.utils;

import android.content.Context;
import android.widget.Toast;

public class AppConstant {
    public static final String USERNAME = "userName";
    public static final String IS_MALE = "isMale";
    public static final String POSITIVE = "Positive";
    public static final String MY_BR_ACTION = "com.android.love.learnintent";
    public static String NEGATIVE = "Negative";

    public static void showToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
