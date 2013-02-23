package ru.spb.cupchinolabs.githubclient;

import ru.spb.cupchinolabs.githubclient.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: VladimirK
 * Date: 23.02.13
 * Time: 15:11
 */
public class ApplicationContext {

    private User user;

    private static ApplicationContext ourInstance = new ApplicationContext();

    public static ApplicationContext getInstance() {
        return ourInstance;
    }

    private ApplicationContext() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
