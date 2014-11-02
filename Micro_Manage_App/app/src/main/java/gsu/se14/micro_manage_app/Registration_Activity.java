package gsu.se14.micro_manage_app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

// External Imports from Android
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// Imports from Parse
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class Registration_Activity extends ActionBarActivity {

    // Variables to be used in the class.
    String emailAddress = null;
    String password = null;
    String firstName = null;
    String lastName = null;
    String company = null;
    String userName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_layout);

        // Connects to the parse database.
        Parse.initialize(this, "F0jsKTo5e0cQwP2cAUZGvdm7Oyxd2JN6MT8mnrK4", "dW7E6SbLphe3IzdPkMNzbuw5o1QCLKb9SBYZ854R");

        // Creates Buttons on the Register Screen.
        final Button btnBack = (Button)findViewById(R.id.BTNback_register);
        final Button btnRegister = (Button)findViewById(R.id.BTNregister_register);

        btnRegister.setOnClickListener(
                new Button.OnClickListener()
                {
                    public void onClick(View v)
                    {

                        // Gets the Email Address from the User.
                        EditText etEmail = (EditText)findViewById(R.id.ETemail_register);
                        emailAddress = etEmail.getText().toString();

                        // Gets the Password from the User.
                        EditText etPassword = (EditText)findViewById(R.id.ETpassword_register);
                        password = etPassword.getText().toString();

                        // Gets the First Name from the User.
                        EditText etFirstName = (EditText)findViewById(R.id.ETfirstName_register);
                        firstName = etFirstName.getText().toString();

                        // Gets the Last Name from the User.
                        EditText etLastName = (EditText)findViewById(R.id.ETlastName_register);
                        lastName = etLastName.getText().toString();

                        // Gets the Company from the User.
                        EditText etCompany = (EditText)findViewById(R.id.ETcompany_register);
                        company = etCompany.getText().toString();

                        // Temporarily uses the User's First Name as a User Name.
                        userName = firstName;

                        // Validates ALL the User Input Fields.
                        if(validateInput(emailAddress) || validateInput(password) || validateInput(firstName) || validateInput(lastName) || validateInput(company))
                        {
                            Toast.makeText(getApplicationContext(), "Sorry, One of the entries is invalid.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Adds new Company to the Company Table/Directory.
                        ParseObject newCompany = new ParseObject("Company");
                        newCompany.put("company", company);
                        newCompany.saveInBackground();

                        // Creates the Tables/Databases for the new Company.
                        ParseObject newCompanyUsers = new ParseObject(company + "_Users");
                        ParseObject newCompanyInventory = new ParseObject(company + "_Inventory");
                        ParseObject newCompanyTransactions = new ParseObject(company + "_Transactions");

                        // Automatically Registers User as Owner.
                        newCompanyUsers.put("firstName", firstName);
                        newCompanyUsers.put("lastName", lastName);
                        newCompanyUsers.put("userName", userName);
                        newCompanyUsers.put("password", password);
                        newCompanyUsers.put("emailAddress", emailAddress);
                        newCompanyUsers.put("type", 0);
                        newCompanyUsers.saveInBackground();

                        // Saves other Tables.
                        newCompanyInventory.saveInBackground();
                        newCompanyTransactions.saveInBackground();

                        // Lets User know that they have Successfully Registered.
                        Toast.makeText(getApplicationContext(), "Success. Thanks for registering.", Toast.LENGTH_SHORT).show();

                        // Completes the Current Activity and goes to the Previous Activity on the stack.
                        finish();

                    }
                }
        );

        btnBack.setOnClickListener(
                new Button.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        // Goes Back to the Previous Screen.
                        finish();
                    }
                }
        );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration, menu);
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

    // Validates User Input to make sure the input fields aren't empty.
    public boolean validateInput(String item)
    {
        // Value True/False to be Returned.
        boolean value = false;

        // Trims the White Space and Compares Length.
        if (item.trim().length() == 0)
            value = true;

        return value;
    }
}
