package ru.spb.cupchinolabs.githubclient.action;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import ru.spb.cupchinolabs.githubclient.ApplicationContext;
import ru.spb.cupchinolabs.githubclient.GitHubEmulator;
import ru.spb.cupchinolabs.githubclient.R;
import ru.spb.cupchinolabs.githubclient.model.User;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

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
            new GitHubPingAsyncTask().execute();
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

        if (authenticationPassed){
            ApplicationContext.getInstance().setUser(user);
            GitHubEmulator.populateRepoListForUser(user);
            Intent intent = new Intent(this, RepoListActivity.class);
            startActivity(intent);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setPositiveButton(R.string.login_errordialog_ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                }
            });
            builder.setMessage(getString(R.string.login_auth_failed_message));
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private class GitHubPingAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... objects) {
            try {
                URL url = new URL("https://api.github.com/");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                int response = conn.getResponseCode();
                System.out.println("The response is: " + response);
                InputStream is = conn.getInputStream();
                System.out.println(readIt(is, 500));
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }

        // Reads an InputStream and converts it to a String.
        public String readIt(InputStream stream, int len) throws IOException {
            Reader reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[len];
            reader.read(buffer);
            return new String(buffer);
        }

        @Override
        protected void onPostExecute(String errorMessage) {
            if (errorMessage != null){
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setPositiveButton(R.string.login_errordialog_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                builder.setMessage(LoginActivity.this.getString(R.string.login_github_unavailable) + "\n" + errorMessage);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    }
}