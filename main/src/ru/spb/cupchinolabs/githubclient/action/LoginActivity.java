package ru.spb.cupchinolabs.githubclient.action;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import ru.spb.cupchinolabs.githubclient.ApplicationContext;
import ru.spb.cupchinolabs.githubclient.R;
import ru.spb.cupchinolabs.githubclient.github.GitHubAuthenticateAsyncTask;
import ru.spb.cupchinolabs.githubclient.github.GitHubPingAsyncTask;
import ru.spb.cupchinolabs.githubclient.model.User;

public class LoginActivity extends Activity {

    public final static String LOGIN_NAME = "ru.spb.cupchinolabs.githubclient.login.name";
    public final static String LOGIN_PASSWORD = "ru.spb.cupchinolabs.githubclient.login.password";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //if name and password are found in storage then populate fields with the values
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);

        String savedUsername = sharedPref.getString(getString(R.string.login_username_saved), "");
        String savedPassword = sharedPref.getString(getString(R.string.login_password_saved), ""); //TODO decript password

        ((EditText)findViewById(R.id.login_name)).setText(savedUsername);
        ((EditText)findViewById(R.id.login_password)).setText(savedPassword);
    }

    @Override
    protected void onPause() {
        super.onPause();

        //save user's info here
        String name = ((EditText)findViewById(R.id.login_name)).getText().toString();
        String password = ((EditText) findViewById(R.id.login_password)).getText().toString();

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.login_username_saved), name);
        editor.putString(getString(R.string.login_password_saved), password); //TODO encript password
        editor.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new GitHubPingAsyncTask(this).execute();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setPositiveButton(R.string.login_errordialog_ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            });
            builder.setMessage(getString(R.string.login_network_unavailable));
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    public void login(View view) {

        //TODO disable editviews and button

        String name = ((EditText)findViewById(R.id.login_name)).getText().toString();
        String password = ((EditText) findViewById(R.id.login_password)).getText().toString();

        //TODO validate name and password: non-empty or if empty button should be disabled

        User user = new User(name, password);
        ApplicationContext.getInstance().setUser(user);

        new GitHubAuthenticateAsyncTask(this, name, password).execute();
    }

    public void onGitHubAuthenticateSuccess() {
        Intent intent = new Intent(this, RepoListActivity.class);
        startActivity(intent);
    }

    public void onGitHubAuthenticationError(String errorMessage) {
        showDialogWithErrorMessageAndOkButton(R.string.login_github_autherntication_error, errorMessage);
    }

    public void onGitHubPingError(String errorMessage) {
        showDialogWithErrorMessageAndOkButton(R.string.login_github_unavailable, errorMessage);
    }

    private void showDialogWithErrorMessageAndOkButton(int messageCode, String errorMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton(R.string.login_errordialog_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.setMessage(getString(messageCode) + (errorMessage == null ? "" : "\n" + errorMessage));
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}