package tk.cassioso.jobs.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import tk.cassioso.jobs.MyApplication;
import tk.cassioso.jobs.PandaJobDetailActivity;
import tk.cassioso.jobs.PandaJobDetailFragment;
import tk.cassioso.jobs.R;
import tk.cassioso.jobs.data.PandaJobModel;

public class JobRecyclerViewAdapter
        extends RecyclerView.Adapter<JobRecyclerViewAdapter.ViewHolder> {

    public static final String TAG = "JobRecyclerViewAdapter";

    boolean mTwoPane;
    private List<PandaJobModel> mListPandaJobModel;
    private FragmentManager mSupportFragmentManager;
    private Context mContext;

    public JobRecyclerViewAdapter(Context context, FragmentManager supportFragmentManager ) {
        mContext = context;
        mSupportFragmentManager = supportFragmentManager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pandajob_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // The detail container view will be present only in the
                // large-screen layouts (res/values-sw600dp).
                // If this view is present, then the
                // activity should be in two-pane mode.

                Context context = v.getContext();
                if (context.getResources().getBoolean(R.bool.dual_pane)) {
                    Bundle arguments = new Bundle();
                    arguments.putString(PandaJobDetailFragment.ARG_ITEM_ID, mListPandaJobModel.get(position).getOrder_id());
                    PandaJobDetailFragment fragment = new PandaJobDetailFragment();
                    fragment.setArguments(arguments);
                    mSupportFragmentManager.beginTransaction()
                            .replace(R.id.job_detail_container, fragment)
                            .commit();
                } else {
                    Intent intent = new Intent(context, PandaJobDetailActivity.class);
                    intent.putExtra(PandaJobDetailActivity.ARG_ITEM_ID, mListPandaJobModel.get(position).getOrder_id());
                    context.startActivity(intent);
                }
            }
        });

        PandaJobModel job = mListPandaJobModel.get(position);

        viewHolder.mOrderDate.setText(job.getFormattedDate() + " " + job.getOrder_time() + " (" + job.getFormattedDuration() + ")");
        viewHolder.mStatus.setText(job.get__status());
        switch (job.get__status()){
            case "FULFILLED":
                viewHolder.mStatus.setBackgroundColor(Color.parseColor("#0D47A1")); // blue
                break;
            case "START":
                viewHolder.mStatus.setBackgroundColor(Color.parseColor("#1B5E20")); // green
                break;
            case "INVOICED":
                viewHolder.mStatus.setBackgroundColor(Color.parseColor("#880E4F")); // pink
                break;
            case "CANCELLED":
                viewHolder.mStatus.setBackgroundColor(Color.parseColor("#E65100")); // orange
                break;
            case "ERROR":
                viewHolder.mStatus.setBackgroundColor(Color.parseColor("##b71c1c")); // red
                break;
            default:
                viewHolder.mStatus.setBackgroundColor(Color.BLACK);
                break;
        }
        viewHolder.mCustomerName.setText(job.getCustomer_name());
        viewHolder.mJobId.setText(job.getOrder_id());
        viewHolder.mDistance.setText(job.getFormattedDistance());
        viewHolder.mPrice.setText(job.getFormattedPrice());
        viewHolder.mPaymentMethod.setText(job.getPayment_method());
    }

    @Override
    public int getItemCount() {
        if(mListPandaJobModel == null){
            return 0;
        }
        return mListPandaJobModel.size();
    }

    public void refreshData(List<PandaJobModel> listPandaJobModel) {
        mListPandaJobModel = listPandaJobModel;
        notifyDataSetChanged();
    }

    public void orderBy(String order) {
        Log.d(TAG, "Order by: " + order);
        Realm realm = ((MyApplication) mContext.getApplicationContext()).getRealm();
        RealmResults<PandaJobModel> realmResultJobs = realm.where(PandaJobModel.class).findAllSorted(order);
        List<PandaJobModel> listPandaJobsModel = realm.copyFromRealm(realmResultJobs);
        refreshData(listPandaJobsModel);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.pandajob_list_item_root)
        View rootView;

        @BindView(R.id.pandajob_list_item_id)
        TextView mJobId;

        @BindView(R.id.pandajob_list_item_customername)
        TextView mCustomerName;

        @BindView(R.id.pandajob_list_item_orderdatetime)
        TextView mOrderDate;

        @BindView(R.id.pandajob_list_item_distance)
        TextView mDistance;

        @BindView(R.id.pandajob_list_item_price)
        TextView mPrice;

        @BindView(R.id.pandajob_list_item_payment_method)
        TextView mPaymentMethod;

        @BindView(R.id.pandajob_list_item_status)
        TextView mStatus;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}