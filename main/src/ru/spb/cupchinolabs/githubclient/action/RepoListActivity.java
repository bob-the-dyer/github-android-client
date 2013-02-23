package ru.spb.cupchinolabs.githubclient.action;

import android.app.Activity;
import android.os.Bundle;
import ru.spb.cupchinolabs.githubclient.R;

/**
 * Created with IntelliJ IDEA.
 * User: VladimirK
 * Date: 23.02.13
 * Time: 15:15
 */
public class RepoListActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            //TODO restore current position in list
        }
        setContentView(R.layout.repolist);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //TODO reauthenticate if needed, if fails - alert and redirect to login activity
        //TODO retrieve fresh repolist?
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //TODO save current position in list (scroll position of a ListView?)
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //TODO restore current position in list
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}