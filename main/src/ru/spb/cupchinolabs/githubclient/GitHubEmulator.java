package ru.spb.cupchinolabs.githubclient;

import ru.spb.cupchinolabs.githubclient.model.Repository;
import ru.spb.cupchinolabs.githubclient.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: VladimirK
 * Date: 23.02.13
 * Time: 15:49
  */
public class GitHubEmulator {

    public static boolean authenticate(User user) {
        return true;
    }

    public static void populateRepoListForUser(User user) {
        user.setRepoList(
                getRepoListForUser(user));
    }

    private static List<Repository> getRepoListForUser(User user) {
        List<Repository> repoList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Repository repository = new Repository();
            repository.setName("repo_name" + i);
            repository.setDescription("repo_description_short" + i);
            repository.setAuthorName("repo_author_name" + i);
            repository.setForksCount(i);
            repository.setWatchersCount(i);
            // TODO repository.setCommits();
            repoList.add(repository);
        }
        return repoList;
    }
}
