package android.love.learnintent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.core.content.ContextCompat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.love.learnintent.utils.AppConstant;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Random;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int REQUEST_CODE = 100;
    private TextInputEditText userName;
    private AppCompatRadioButton male;
    private AppCompatRadioButton female;
    private TextInputEditText scoreTI_ET;
    private Stack<Boolean> myStack = new Stack<>();

    BroadcastReceiver ShowToast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context,"Broadcast event received!",Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        launchCreateActivity();
        setContentView(R.layout.activity_main);
        userName = findViewById(R.id.user_ti_et);
        userName.setEnabled(false);
        userName.setTextColor(ContextCompat.getColor(this,android.R.color.black));
        scoreTI_ET = findViewById(R.id.score_ti_et);
        scoreTI_ET.setEnabled(false);
        scoreTI_ET.setTextColor(ContextCompat.getColor(this,android.R.color.black));
        findViewById(R.id.button).setOnClickListener(this);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        male.setClickable(false);
        female.setClickable(false);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(AppConstant.MY_BR_ACTION);
        registerReceiver(ShowToast,intentFilter);
    }

    private void launchCreateActivity() {
        Intent intent = new Intent(this,CreateUserActivity.class);
        startActivityForResult(intent,REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            if(data != null){
                Bundle bundle = data.getExtras();
                if(bundle != null){
                    Intent intent = new Intent(AppConstant.MY_BR_ACTION);
                    sendBroadcast(intent);
                    String userNameText = bundle.getString(AppConstant.USERNAME);
                    if(userNameText != null){
                        userName.setText(userNameText);
                        if(bundle.getBoolean(AppConstant.IS_MALE)){
                            male.setChecked(true);
                            return;
                        }
                        female.setChecked(true);
                    }
                }
            }
        }
        else{
            launchCreateActivity();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        boolean isPositive = new Random().nextInt() > 0;
        scoreTI_ET.setText(isPositive ? AppConstant.POSITIVE : AppConstant.NEGATIVE);
        if(myStack.size() == 0){
            myStack.push(isPositive);
        }
        else{
            if(isPositive == myStack.pop()){
                Uri web = Uri.parse("http://www.google.com");
                Intent i = new Intent(Intent.ACTION_VIEW,web);
                startActivity(i);
            }
        }

    }
}
