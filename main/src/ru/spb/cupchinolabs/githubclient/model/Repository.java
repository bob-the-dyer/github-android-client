package ru.spb.cupchinolabs.githubclient.model;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: VladimirK
 * Date: 23.02.13
 * Time: 14:15
 */
public class Repository {

    private String name;

    private String description;

    private String authorName;

    //TODO author's avatar

    private int forksCoount;

    private List<String> watchers;

    private List<Commit> commits;

}
