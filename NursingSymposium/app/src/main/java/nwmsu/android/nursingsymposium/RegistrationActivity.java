package nwmsu.android.nursingsymposium;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }
    public void onRegisterBtnClick(View view)
    {
        TextView txtFirstName = findViewById(R.id.registerValidationTXT_firstName);
        TextView txtLastName = findViewById(R.id.registerValidationTXT_lastName);
        TextView txtEmail = findViewById(R.id.registerValidationTXT_email);
        TextView txtValidation = findViewById(R.id.registerValidationTXT_email);
        EditText editTxtFirstName = findViewById(R.id.registerText_firstName);
        EditText editTxtLastName = findViewById(R.id.registerText_lastName);
        EditText editTxtEmail = findViewById(R.id.registerText_email);

        txtFirstName.setText("Fist Name:"+editTxtFirstName.getText().toString());
        txtLastName.setText("Last Name:"+editTxtLastName.getText().toString());
        txtEmail.setText("Email :"+editTxtEmail.getText().toString());
        String myValidationText = "";
        if ( !verifyFastName( editTxtFirstName.getText().toString())) {
            myValidationText += "First Name is not valid\n";
            txtFirstName.setText(myValidationText);
        }
        if( !verifyLastName( editTxtLastName.getText().toString())) {
            myValidationText += "Last Name is not valid\n";
            txtFirstName.setText(myValidationText);
        }

        if( !verifyEmail( editTxtEmail.getText().toString())) {
            myValidationText += "Email is not a valid email\n";
            txtFirstName.setText(myValidationText);
        }
        if( !myValidationText.equals("")) {
            txtValidation.setText( "Data cannot be empty");
        }



    }

  /*
  Last Name verification
   */
    private boolean verifyLastName(String lname)
    {
        lname = lname.trim();

        if(lname == null || lname.equals(""))
            return false;

        if(!lname.matches("[a-zA-Z]*"))
            return false;

        return true;
    }
    /*
    Fast Name verification
     */
    private boolean verifyFastName(String fname)
    {
        fname = fname.trim();

        if(fname == null || fname.equals(""))
            return false;

        if(!fname.matches("[a-zA-Z]*"))
            return false;

        return true;
    }
    /*
     Email verification
     */
    private boolean verifyEmail(String email)
    {
        email = email.trim();

        if(email == null || email.equals(""))
            return false;

        if(!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"))
            return false;

        return true;
    }
}