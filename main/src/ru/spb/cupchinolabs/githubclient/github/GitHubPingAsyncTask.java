package ru.spb.cupchinolabs.githubclient.github;

import android.os.AsyncTask;
import ru.spb.cupchinolabs.githubclient.action.LoginActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: VladimirK
 * Date: 24.02.13
 * Time: 19:01
 */
public class GitHubPingAsyncTask extends AsyncTask<String, String, String> {

    private LoginActivity loginActivity;

    public GitHubPingAsyncTask(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

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
            return String.valueOf(response);
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
        if (!"200".equals(errorMessage)){
            loginActivity.onGitHubPingError(errorMessage);
        }
    }
}