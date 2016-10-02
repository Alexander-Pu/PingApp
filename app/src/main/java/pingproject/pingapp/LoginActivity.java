package pingproject.pingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.database.Cursor;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewDebug;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.design.widget.TextInputEditText;

import java.io.File;
import java.io.FileInputStream;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{

    private Context context;

    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "test1:hello", "test2:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private TextInputEditText mUserView;
    private TextInputEditText mPasswordView;

    private String getStringFromFile(String filename) {
        String returnString = "test1:hello";
        Log.d("Default string",returnString);
        try{
            Log.d("Trying","To work");
            FileInputStream fis;
            fis = openFileInput(filename);
            StringBuffer fileContent = new StringBuffer("");

            byte[] buffer = new byte[1024];
            int n;

            while ((n = fis.read(buffer)) != -1)
            {
                Log.e("PRINTING LETTER", String.valueOf(n));
                fileContent.append(new String(buffer, 0, n));
            }
        }
        catch (Exception e){
            Log.e("Exception","Failed to read file");
        }
        Log.d("return string",returnString);
        return returnString;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getApplicationContext();
        String configPath = "config.txt";
        Log.v("configPath",configPath);
        for (String temp: fileList()){
            Log.v("File",temp);
        }
        File file = new File(context.getFilesDir()+"/"+configPath);
        Log.v("file dir",file.getParent()+"config.txt");
        if (file.exists()){
            Log.v("Attempting","Preload login");
            String logInString = getStringFromFile(configPath);
            String[] stringSplit = logInString.split(":");
            String user = stringSplit[0];
            String pass = stringSplit[1];
            mAuthTask = new UserLoginTask(user, pass,true);
            mAuthTask.execute((Void) null);
        }
        setContentView(R.layout.activity_login);

        // Set up the login form.
        mUserView = (TextInputEditText) findViewById(R.id.user);
        mPasswordView = (TextInputEditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mUserSignInButton = (Button) findViewById(R.id.sign_in_button);
        mUserSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        Button signUpButton = (Button) findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSignUp();
            }
        });
    }

    private void switchToSignUp() {
        Intent toSignUp = new Intent(this,SignUpActivity.class);
        startActivity(toSignUp);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid user, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUserView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String user = mUserView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid user address.
        if (TextUtils.isEmpty(user)) {
            mUserView.setError(getString(R.string.error_field_required));
            focusView = mUserView;
            cancel = true;
        } else if (!isUserValid(user)) {
            mUserView.setError(getString(R.string.error_invalid_user));
            focusView = mUserView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            mAuthTask = new UserLoginTask(user, password,false);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isUserValid(String user) {
        return user.matches("^[a-zA-Z0-9]*$");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUser;
        private final String mPassword;
        private boolean autoAttempt;

        UserLoginTask(String user, String password, boolean auto) {
            mUser = user;
            mPassword = password;
            autoAttempt = auto;
            Log.v("Created","UserLogTask");
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            return existingCredential(mUser,mPassword);
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            if (success) {
                finish();
                Intent MyIntent = new Intent(context,MainActivity.class);
                startActivity(MyIntent);
            }
            else if(autoAttempt==true){
                return;
            }
            else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }

    private boolean existingCredential(String user, String pass) {
        String mUser = user;
        String mPassword = pass;
        for( String credential:DUMMY_CREDENTIALS) {
            String[] pieces = credential.split(":");
            if (pieces[0].equals(mUser)) {
                // Account exists, return true if the password matches.
                return pieces[1].equals(mPassword);
            }
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}

