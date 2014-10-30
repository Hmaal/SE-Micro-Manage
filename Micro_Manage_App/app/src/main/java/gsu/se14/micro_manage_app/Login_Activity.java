package gsu.se14.micro_manage_app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

// Android Imports
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// Parse Imports
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class Login_Activity extends ActionBarActivity {

    // Creates variables the the class will use later.
    String userName = null;
    String password = null;
    boolean valid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        // Connects to the parse database.
        Parse.initialize(this, "F0jsKTo5e0cQwP2cAUZGvdm7Oyxd2JN6MT8mnrK4", "dW7E6SbLphe3IzdPkMNzbuw5o1QCLKb9SBYZ854R");

        // Creates variables for buttons.
        final Button btnLogin = (Button)findViewById(R.id.BTNlogin_login);
        final Button btnRegister = (Button)findViewById(R.id.BTNregister_login);

        // Intent for going to the registration page.
        final Intent goToRegistration = new Intent(this, Registration_Activity.class);

        // OnClickListener for the Login Button.
        btnLogin.setOnClickListener(
            new Button.OnClickListener()
            {
                // OnClick Method
                public void onClick(View v)
                {
                    // Converts User Name from Edit Text to String.
                    EditText etUserName = (EditText) findViewById(R.id.ETuserName_login);
                    userName = etUserName.getText().toString();

                    // Converts Password from Edit Text to String.
                    EditText etPassword = (EditText) findViewById(R.id.ETpassword_login);
                    password = etPassword.getText().toString();

                    //Log.e("VALUES", "User Name: " + userName + " Password: " + password);

                    // Validates the Login before continuing.
                    if (validateLogin(userName, password) == true)
                        return;

                    // TODO: Transfer code to proper place in Registration Activity.
                    ParseUser userJ = new ParseUser();
                    userJ.setUsername(userName);
                    userJ.setPassword(password);
                    userJ.setEmail("judgemg09@gmail.com");
                    userJ.put("type", 1);
                    userJ.put("phoneNumber", "912-977-6775");

                    // TODO: Needs to transfer too, code is used for Successfully Registering.
                    userJ.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            if (e == null)
                            {
                                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
                                Log.e("APP_ERROR", "Login Error", e);

                            }
                        }
                    });
                }
            }
        );

        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                startActivity(goToRegistration);

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // TODO: A copy of this need to go to the Registration Activity too.
    // Method that makes sure Edit Text input fields aren't empty.
    public boolean validateLogin(String userName, String password) {
        boolean value = false;

        if (userName.trim().length() == 0 && password.trim().length() == 0) {
            Toast.makeText(getApplicationContext(), "Sorry. User Name and Password are incorret.", Toast.LENGTH_SHORT).show();
            value = true;
        }
        else if (userName.trim().length() == 0) {
            Toast.makeText(getApplicationContext(), "Sorry. User Name is incorret.", Toast.LENGTH_SHORT).show();
            value = true;
        }
        else if (password.trim().length() == 0) {
            Toast.makeText(getApplicationContext(), "Sorry. Password is incorret.", Toast.LENGTH_SHORT).show();
            value = true;
        }

        return value;
    }
}
