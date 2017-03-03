package com.test.githubsearch;

import android.content.Context;

import com.test.githubsearch.data.GithubRepo;
import com.test.githubsearch.utils.PreferenceManager;
import com.test.githubsearch.utils.ResourceUtils;
import com.test.githubsearch.utils.Utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = "src/main/AndroidManifest.xml")
public class GithubSearchUnitTest {

    private ArrayList<GithubRepo> githubRepos;
    private GithubRepo githubRepo;

    private Context mContext;

    @Before
    public void setup() throws Exception {
        mContext = RuntimeEnvironment.application.getApplicationContext();
        assertNotNull(mContext);
        ResourceUtils.initialize(mContext);
        PreferenceManager.initialize(mContext);
        githubRepo = new GithubRepo();
        githubRepo.setId(1);
        githubRepo.setBookmark(true);
        githubRepo.setDescription("desc");
        githubRepo.setFullName("name");
        githubRepo.setNumberOfForks(2);
        githubRepo.setNumberOfWatchers(3);
        githubRepo.setUrl("http");

        githubRepos = new ArrayList<>();
        githubRepos.add(githubRepo);

    }

    @After
    public void remove() throws Exception {
        mContext = null;
        githubRepos = new ArrayList<>();
        githubRepo = new GithubRepo();
    }

    @Test
    public void getStringTest() throws Exception {
        String appName = "Github Search";
        String result = ResourceUtils.getString(R.string.app_name);
        assertThat(appName, is(result));
    }

    @Test
    public void capitaliseNameTest() throws Exception {
        String name = "Okhttp";

        String result = Utils.firstLetterCapital("okhttp");

        assertThat(name, is(result));
    }

    @Test
    public void containsTest() throws Exception {
        assertFalse(!Utils.contains(githubRepos, githubRepo));
    }

    @Test
    public void indexTest() throws Exception {
        assertEquals(Utils.getIndex(githubRepos, githubRepo), 0);
    }

    @Test
    public void addBookMarkTest() throws Exception {
        Utils.addBookmark(githubRepo);
        ArrayList<GithubRepo> githubRepoArrayList = Utils.getAllBookmarks();
        assertEquals(githubRepoArrayList.get(0).getId(), githubRepo.getId());
    }

    @Test
    public void removeBookMarkTest() throws Exception {
        Utils.addBookmark(githubRepo);
        Utils.removeBookmark(githubRepo);
        assertFalse(!Utils.getAllBookmarks().isEmpty());
    }

    @Test
    public void markBookMarkTest() throws Exception {
        Utils.addBookmark(githubRepo);
        githubRepo.setBookmark(false);
        Utils.markBookmark(githubRepos);
        assertFalse(!githubRepos.get(0).isBookmark());
    }

}
