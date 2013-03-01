package ru.spb.cupchinolabs.githubclient.action;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import ru.spb.cupchinolabs.githubclient.R;

/**
 * Created with IntelliJ IDEA.
 * User: VladimirK
 * Date: 01.03.13
 * Time: 8:37
 */
public class RepoDetailsActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repodetails);
        TextView textView = (TextView) findViewById(R.id.message);
        int repoIndex = getIntent().getIntExtra(RepoListActivity.REPO_INDEX, -1);
        textView.setText(textView.getText().toString() + repoIndex);
    }

}