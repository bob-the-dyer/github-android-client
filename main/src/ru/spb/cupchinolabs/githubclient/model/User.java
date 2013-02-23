package ru.spb.cupchinolabs.githubclient.model;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: VladimirK
 * Date: 23.02.13
 * Time: 14:14
 */
public class User {

    private String login;
    private String password; //TODO encryption

    private List<Repository> repoList;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Repository> getRepoList() {
        return repoList;
    }

    public void setRepoList(List<Repository> repoList) {
        this.repoList = repoList;
    }
}
