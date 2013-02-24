package ru.spb.cupchinolabs.githubclient.action;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;
import ru.spb.cupchinolabs.githubclient.ApplicationContext;
import ru.spb.cupchinolabs.githubclient.R;
import ru.spb.cupchinolabs.githubclient.model.Repository;

import java.util.List;

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

        GridView gridview = (GridView) findViewById(R.id.repolist);
        List<Repository> repoList = ApplicationContext.getInstance().getUser().getRepoList();
        gridview.setAdapter(
                new ArrayAdapter<>(
                        this, android.R.layout.simple_list_item_1, repoList.toArray(new Repository[repoList.size()])));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(RepoListActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });

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