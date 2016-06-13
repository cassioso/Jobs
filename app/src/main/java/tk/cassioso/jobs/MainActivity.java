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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tk.cassioso.jobs.adapter.JobRecyclerViewAdapter;
import tk.cassioso.jobs.data.PandaJobModel;

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

    @BindView(R.id.job_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.no_data)
    TextView mNoData;

    private JobRecyclerViewAdapter mJobRecyclerViewAdapter;
    private String order = "job_date";

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

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
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);

        Realm realm = ((MyApplication) getApplication()).getRealm();

        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        mJobRecyclerViewAdapter = new JobRecyclerViewAdapter(this, getSupportFragmentManager());

        RealmResults<PandaJobModel> realmResultJobs = realm.where(PandaJobModel.class).findAllSorted(order);
        List<PandaJobModel> listPandaJobsModel = realm.copyFromRealm(realmResultJobs);
        refreshData(listPandaJobsModel);

        mRecyclerView.setAdapter(mJobRecyclerViewAdapter);

        fetchJobModelList();
    }

    private void fetchJobModelList() {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(BASE_URL).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, java.io.IOException e) {
                Log.e(TAG, e.getMessage(), e);

                Snackbar.make(mRecyclerView, R.string.no_data_available, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.snackbar_action_retry, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                fetchJobModelList();
                            }
                        }).show();

            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    Log.d(TAG, "Response code: " + response.code());
                    final String json = response.body().string();

                    new AsyncTask<Void, Void, List<PandaJobModel>>() {
                        @Override
                        protected List<PandaJobModel> doInBackground(Void... params) {
                            Realm realm = ((MyApplication) getApplication()).getRealm();
                            realm.beginTransaction();
                            realm.deleteAll();
                            realm.createAllFromJson(PandaJobModel.class, json);
                            realm.commitTransaction();

                            RealmResults<PandaJobModel> pandaJobModelRealmResults = realm.where(PandaJobModel.class).findAllSorted(order);
                            List<PandaJobModel> copy = realm.copyFromRealm(pandaJobModelRealmResults);

                            return copy;
                        }

                        @Override
                        protected void onPostExecute(List<PandaJobModel> listPandaJobsModel) {
                            super.onPostExecute(listPandaJobsModel);
                            refreshData(listPandaJobsModel);
                        }
                    }.execute();

                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void refreshData(List<PandaJobModel> listPandaJobModel){
        if(listPandaJobModel == null || listPandaJobModel.size() == 0){
            mNoData.setVisibility(View.VISIBLE);
        } else {
            mNoData.setVisibility(View.GONE);
        }
        mJobRecyclerViewAdapter.refreshData(listPandaJobModel);
    }
}
