package ru.spb.cupchinolabs.githubclient.action;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import ru.spb.cupchinolabs.githubclient.ApplicationContext;
import ru.spb.cupchinolabs.githubclient.GitHub;
import ru.spb.cupchinolabs.githubclient.R;
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
        // TODO check the network availability
        // otherwise reate a dialog here that requests the user to enable wifi or 3g
        //
    }

    public void login(View view) {

        //TODO disable editviews and button
        //TODO progress indication

        String name = findViewById(R.id.login_name).toString();
        String password = findViewById(R.id.login_password).toString();

        //TODO validate name and password: non-empty

        Intent intent = new Intent(this, RepoListActivity.class);
//        intent.putExtra(LOGIN_NAME, name);
//        intent.putExtra(LOGIN_PASSWORD, password);

        User user = new User(name, password);
        if (GitHub.authenticate(user)){
            ApplicationContext.getInstance().setUser(user);
            GitHub.populateRepoListForUser(user);
            startActivity(intent);
        } else {
            // TODO alert error on authenticate and stay on this activity
        }
    }

}