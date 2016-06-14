package tk.cassioso.jobs;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by cassioso on 6/13/16.
 */
public class MyApplication extends Application {

    private RealmConfiguration mRealmConfiguration;

    /*
    The jobs should be requested only when the user opens the app (first time AND coming back from background).
     */
    private boolean fetchPandaJobData = true;

    @Override
    public void onCreate() {
        super.onCreate();
        mRealmConfiguration = new RealmConfiguration.Builder(this).build();
    }

    public Realm getRealm() {
        return Realm.getInstance(mRealmConfiguration);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        getRealm().close();
    }

    public boolean isFetchPandaJobData() {
        return fetchPandaJobData;
    }

    public void setFetchPandaJobData(boolean fetchPandaJobData) {
        this.fetchPandaJobData = fetchPandaJobData;
    }
}
