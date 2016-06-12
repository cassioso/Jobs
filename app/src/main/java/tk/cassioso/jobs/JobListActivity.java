package tk.cassioso.jobs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tk.cassioso.jobs.data.JobModel;
import tk.cassioso.jobs.data.JobServiceRetrofit;

/**
 * An activity representing a list of Jobs. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link JobDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class JobListActivity extends AppCompatActivity {

    public static final List<JobModel> ITEMS = new ArrayList<>();
    public static final Map<String, JobModel> ITEM_MAP = new HashMap<>();
    private static final String TAG = "JobListActivity";
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final View recyclerView = findViewById(R.id.job_list);
        assert recyclerView != null;

        JobServiceRetrofit.LIST_JOBS.enqueue(new Callback<List<JobModel>>() {
            @Override
            public void onResponse(Call<List<JobModel>> call, Response<List<JobModel>> response) {
                int statusCode = response.code();
                Log.d(TAG, "statusCode=" + statusCode);
                ITEMS.addAll(response.body());
                setupRecyclerView((RecyclerView) recyclerView, ITEMS);
                for (JobModel jobModel : ITEMS) {
                    ITEM_MAP.put(jobModel.getOrderId(), jobModel);
                }
            }

            @Override
            public void onFailure(Call<List<JobModel>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });

        if (findViewById(R.id.job_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-sw600dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, List<JobModel> jobModelList) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(jobModelList));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<JobModel> mValues;

        public SimpleItemRecyclerViewAdapter(List<JobModel> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.job_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).getOrderId());
            holder.mContentView.setText(mValues.get(position).getCustomerName());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(JobDetailFragment.ARG_ITEM_ID, holder.mItem.getOrderId());
                        JobDetailFragment fragment = new JobDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.job_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, JobDetailActivity.class);
                        intent.putExtra(JobDetailFragment.ARG_ITEM_ID, holder.mItem.getOrderId());

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public JobModel mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
