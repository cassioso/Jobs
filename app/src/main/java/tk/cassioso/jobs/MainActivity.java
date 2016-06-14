package tk.cassioso.jobs;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tk.cassioso.jobs.view.JobRecyclerViewAdapter;
import tk.cassioso.jobs.data.PandaJobModel;
import tk.cassioso.jobs.view.PandaJobDetailActivity;

/**
 * An activity representing a list of Jobs. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link PandaJobDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final String BASE_URL = "http://private-14c693-rentapanda.apiary-mock.com/jobs";

    private JobRecyclerViewAdapter mJobRecyclerViewAdapter;

    // TODO: Persist order on SharedPreference
    private String order = "job_date";

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @BindView(R.id.job_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.no_data)
    TextView mNoData;

    @BindView(R.id.progressbar)
    MaterialProgressBar mProgressbar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                fetchJobModelList();
                break;
            case R.id.action_order_by_date:
                item.setChecked(true);
                order = "job_date";
                mJobRecyclerViewAdapter.orderBy(order);
                break;
            case R.id.action_order_by_distance:
                item.setChecked(true);
                order = "distance";
                mJobRecyclerViewAdapter.orderBy(order);
                break;
            case R.id.action_order_by_price:
                item.setChecked(true);
                order = "price";
                mJobRecyclerViewAdapter.orderBy(order);
                break;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        mJobRecyclerViewAdapter = new JobRecyclerViewAdapter(this, getSupportFragmentManager(), this);
        mRecyclerView.setAdapter(mJobRecyclerViewAdapter);


        // retrieve stored data
        Realm realm = ((MyApplication) getApplication()).getRealm();

        RealmResults<PandaJobModel> realmResultJobs = realm.where(PandaJobModel.class).findAllSorted(order);
        List<PandaJobModel> listPandaJobsModel = realm.copyFromRealm(realmResultJobs);
        refreshData(listPandaJobsModel);

        // retrieve data on app initialization
        if (((MyApplication) getApplication()).isFetchPandaJobData()) {
            fetchJobModelList();
            ((MyApplication) getApplication()).setFetchPandaJobData(false);
        }
    }

    /*
    TODO: move fetchJobModeList() method to a dedicated class
     */
    private void fetchJobModelList() {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(BASE_URL).build();

        mProgressbar.setVisibility(View.VISIBLE);

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, java.io.IOException e) {
                Log.e(TAG, e.getMessage(), e);

                mProgressbar.setVisibility(View.GONE);

                Snackbar.make(mRecyclerView, R.string.no_data_available, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.snackbar_action_retry, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                fetchJobModelList();
                            }
                        }).show();
            }

            @Override
            public void onResponse(Call call, final Response response) {
                Log.d(TAG, "Response code: " + response.code());

                new AsyncTask<Void, Void, List<PandaJobModel>>() {

                    @Override
                    protected List<PandaJobModel> doInBackground(Void... params) {
                        try {
                            String json = response.body().string();

                            Realm realm = ((MyApplication) getApplication()).getRealm();
                            realm.beginTransaction();
                            realm.deleteAll();
                            realm.createAllFromJson(PandaJobModel.class, json);
                            realm.commitTransaction();

                            RealmResults<PandaJobModel> pandaJobModelRealmResults = realm.where(PandaJobModel.class).findAllSorted(order);
                            List<PandaJobModel> copy = realm.copyFromRealm(pandaJobModelRealmResults);

                            return copy;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(List<PandaJobModel> listPandaJobsModel) {
                        super.onPostExecute(listPandaJobsModel);

                        mProgressbar.setVisibility(View.GONE);

                        if (listPandaJobsModel != null) {
                            refreshData(listPandaJobsModel);
                        }
                    }
                }.execute();
            }
        });
    }

    /*
    If data list is null or has size 0, a no data to display view will be shown
     */
    private void refreshData(List<PandaJobModel> listPandaJobModel) {
        if (listPandaJobModel == null || listPandaJobModel.size() == 0) {
            mNoData.setVisibility(View.VISIBLE);
        } else {
            mNoData.setVisibility(View.GONE);
        }
        mJobRecyclerViewAdapter.refreshData(listPandaJobModel);
    }
}
