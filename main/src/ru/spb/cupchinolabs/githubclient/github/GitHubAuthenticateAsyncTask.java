package ru.spb.cupchinolabs.githubclient.github;

import android.os.AsyncTask;
import android.util.Base64;
import org.json.JSONException;
import org.json.JSONObject;
import ru.spb.cupchinolabs.githubclient.ApplicationContext;
import ru.spb.cupchinolabs.githubclient.action.LoginActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: VladimirK
 * Date: 24.02.13
 * Time: 19:48
 */
public class GitHubAuthenticateAsyncTask extends AsyncTask<String, String, String> {

    private final LoginActivity loginActivity;
    private final String name;
    private final String password;

    public GitHubAuthenticateAsyncTask(LoginActivity loginActivity, String name, String password) {
        this.loginActivity = loginActivity;
        this.name = name;
        this.password = password;
    }

    @Override
    protected String doInBackground(String ... objects) {
        HttpURLConnection conn = null;
        try {
            //TODO remove hardcode
            String name = "bob-the-dyer";
            String password = "focus1556!!";

            URL url = new URL("https://api.github.com/users/" + name + "/repos");
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setRequestProperty("Authorization", "Basic " + Base64.encodeToString((name + ":" + password).getBytes(),
                    Base64.DEFAULT));
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.connect();
            int response = conn.getResponseCode();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + System.getProperty("line.separator"));
            }
            //TODO next
//            JSONObject json = new JSONObject(sb.toString());
            GitHubEmulator.populateRepoListForUser(
                    ApplicationContext.getInstance().getUser());
            return String.valueOf(response);
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
//        } catch (JSONException e) {
//            e.printStackTrace();
//            throw new IllegalStateException("github replied with incorrect json", e);
        } finally {
            if (conn != null){
                conn.disconnect();
            }
        }
    }

    @Override
    protected void onPostExecute(String errorMessage) {
        if (!"200".equals(errorMessage)){
            loginActivity.onGitHubAuthenticationError(errorMessage);
        } else {
            //TODO store auth data
            loginActivity.onGitHubAuthenticateSuccess();
        }
    }
}


