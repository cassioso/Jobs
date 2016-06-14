package tk.cassioso.jobs.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import io.realm.Realm;
import tk.cassioso.jobs.MainActivity;
import tk.cassioso.jobs.MyApplication;
import tk.cassioso.jobs.R;
import tk.cassioso.jobs.data.PandaHelper;
import tk.cassioso.jobs.data.PandaJobModel;

/**
 * An activity representing a single Job detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link MainActivity}.
 */
public class PandaJobDetailActivity extends AppCompatActivity {

    public static final String ARG_ITEM_ID = "item_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Realm realm = ((MyApplication) getApplication()).getRealm();
                PandaJobModel pandaJobModel = realm.where(PandaJobModel.class)
                        .equalTo("order_id", getIntent()
                                .getStringExtra(PandaJobDetailFragment.ARG_ITEM_ID))
                        .findAll().get(0);

                PandaHelper.copyJobDetailToClipboard(pandaJobModel, PandaJobDetailActivity.this);

                Snackbar.make(view, getString(R.string.copy_successful), Snackbar.LENGTH_LONG).show();
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(PandaJobDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(PandaJobDetailFragment.ARG_ITEM_ID));

            PandaJobDetailFragment fragment = new PandaJobDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.job_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
