package ru.spb.cupchinolabs.githubclient.action;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import ru.spb.cupchinolabs.githubclient.ApplicationContext;
import ru.spb.cupchinolabs.githubclient.R;
import ru.spb.cupchinolabs.githubclient.github.GitHubEmulator;
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
        //TODO if name and password are found in storage then populate fields with the values
    }

    @Override
    protected void onPause() {
        super.onPause();
        //TODO save user's info here
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
        //TODO progress indication

        String name = findViewById(R.id.login_name).toString();
        String password = findViewById(R.id.login_password).toString();

        //TODO validate name and password: non-empty or if empty button should be disabled

        User user = new User(name, password);

        //TODO progress bar on
        boolean authenticationPassed = GitHubEmulator.authenticate(user);
        //TODO progress bar off

        if (authenticationPassed) {
            ApplicationContext.getInstance().setUser(user);
            GitHubEmulator.populateRepoListForUser(user);
            Intent intent = new Intent(this, RepoListActivity.class);
            startActivity(intent);
        } else {
            showDialogWithErrorMessageAndOkButton(R.string.login_auth_failed_message, null);
        }
    }

    public void onGitHubPingError(String errorMessage) {
        showDialogWithErrorMessageAndOkButton(R.string.login_github_unavailable, errorMessage);
    }

    public void onGitHubAuthenticationError(String errorMessage) {
        showDialogWithErrorMessageAndOkButton(R.string.login_github_autherntication_error, errorMessage);
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