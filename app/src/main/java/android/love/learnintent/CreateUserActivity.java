package android.love.learnintent;

import android.content.Intent;
import android.content.IntentFilter;
import android.love.learnintent.utils.AppConstant;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class CreateUserActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, TextWatcher {

    private TextInputEditText userNameTIET;
    int[] rg = new int[]{R.id.male, R.id.female};
    int selectedGender = -1;
    private TextInputLayout userTIL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userTIL = findViewById(R.id.user_ti_lay);
        userNameTIET = findViewById(R.id.user_ti_et);
        userNameTIET.setImeOptions(EditorInfo.IME_ACTION_DONE);
        userNameTIET.addTextChangedListener(this);
        TextInputLayout scoreLayout = findViewById(R.id.score_ti_lay);
        scoreLayout.setVisibility(View.GONE);
        RadioGroup genderGroup = findViewById(R.id.gender_rg);
        genderGroup.setOnCheckedChangeListener(this);
        MaterialButton mButton = findViewById(R.id.button);
        mButton.setText(getString(R.string.button_create));
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(Pattern.compile("[a-zA-Z]+").matcher(userNameTIET.getText().toString()).matches() && selectedGender >= 0){
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString(AppConstant.USERNAME,userNameTIET.getText().toString());
            bundle.putBoolean(AppConstant.IS_MALE,selectedGender == 0);
            intent.putExtras(bundle);
            setResult(RESULT_OK,intent);
            finish();

        }
        else if(userNameTIET.getText().toString().isEmpty()) {
            userTIL.setError(getString(R.string.validation_username));
        }
        else if(selectedGender < 0) {
           AppConstant.showToast(this,getString(R.string.validation_gender));
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(checkedId == rg[0]){
            selectedGender = 0;
        }
        else{
            selectedGender = 1;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(count > 0 && Pattern.compile("[a-zA-Z]+").matcher(s.toString()).matches()){
            userTIL.setError(null);
        }
        else{
            userTIL.setError(getString(R.string.validation_username));
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
