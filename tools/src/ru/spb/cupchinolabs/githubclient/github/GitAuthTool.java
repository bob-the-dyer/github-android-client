package ru.spb.cupchinolabs.githubclient.github;

import sun.misc.BASE64Encoder;
import sun.org.mozilla.javascript.internal.json.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: VladimirK
 * Date: 26.02.13
 * Time: 11:01
 */
public class GitAuthTool {

    public static void main(String[] args) {

        HttpURLConnection conn = null;
        try {
            String name = "bob-the-dyer";
            String password = "focus1556!!";
            URL url = new URL("https://api.github.com/users/" + name + "/repos");
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            String encodedAuth = new BASE64Encoder().encode(( name + ":" + password).getBytes());
            conn.setRequestProperty("Authorization", "Basic " + encodedAuth);
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
            System.out.println(sb.toString());
            //TODO next
//            GitHubEmulator.populateRepoListForUser(
//                    ApplicationContext.getInstance().getUser());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}
