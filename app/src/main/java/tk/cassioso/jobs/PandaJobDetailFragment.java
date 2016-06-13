package tk.cassioso.jobs;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import tk.cassioso.jobs.data.ImageCityFetcher;
import tk.cassioso.jobs.data.PandaJobModel;

/**
 * A fragment representing a single Job detail screen.
 * This fragment is either contained in a {@link MainActivity}
 * in two-pane mode (on tablets) or a {@link PandaJobDetailActivity}
 * on handsets.
 */
public class PandaJobDetailFragment extends Fragment {

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    @BindView(R.id.job_detail1)
    TextView mJobDetail1TextView;

    @BindView(R.id.job_detail2)
    TextView mJobDetail2TextView;

    private PandaJobModel mPandaJobModel;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PandaJobDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            Realm realm = ((MyApplication) getActivity().getApplication()).getRealm();
            mPandaJobModel = realm.where(PandaJobModel.class)
                    .equalTo("order_id", getArguments().getString(ARG_ITEM_ID)).findAll().get(0);

            final CollapsingToolbarLayout mAppBarLayout = (CollapsingToolbarLayout) getActivity().findViewById((R.id.toolbar_layout));
            if (mAppBarLayout != null) {
                mAppBarLayout.setTitle(mPandaJobModel.getCustomer_name());

                Picasso.with(getContext()).load(ImageCityFetcher.getImageUrl(mPandaJobModel.getJob_city())).into(new Target() {
                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }

                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom arg1) {
                        Drawable drawImage = new BitmapDrawable(getResources(), bitmap);
                        mAppBarLayout.setBackground(drawImage);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                    }
                });
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.job_detail, container, false);
        ButterKnife.bind(this, rootView);

        if (mPandaJobModel != null) {

            /*
            job details part 1
             */

            String text = "Order Id" + ": " + mPandaJobModel.getOrder_id();
            text += "\n";
            text += "Customer name" + ": " + mPandaJobModel.getCustomer_name();
            text += "\n";
            text += "\n";
            text += "Address" + ": " + mPandaJobModel.getFormattedAddress();
            text += "\n";
            text += "Distance" + ": " + mPandaJobModel.getFormattedDistance();
            text += "\n";
            text += "Latitude, Longitude" + ": " + mPandaJobModel.getJob_latitude() + ", " + mPandaJobModel.getJob_longitude();

            mJobDetail1TextView.setText(text);

            /*
            job details map
            */



            /*
            job details part 1
             */

            text = "Job date" + ": " + mPandaJobModel.getFormattedDate();
            text += "\n";
            text += "Job time" + ": " + mPandaJobModel.getOrder_time();
            text += "\n";
            text += "Duration" + ": " + mPandaJobModel.getFormattedDuration();
            text += "\n";
            text += "Extras" + ": " + mPandaJobModel.getExtras();
            text += "\n";
            text += "Payment method" + ": " + mPandaJobModel.getPayment_method();
            text += "\n";
            text += "Price" + ": " + mPandaJobModel.getFormattedPrice();
            text += "\n";
            text += "Recurrency" + ": " + mPandaJobModel.getRecurrency();
            text += "\n";
            text += "Status" + ": " + mPandaJobModel.getStatus();

            mJobDetail2TextView.setText(text);

        }
        return rootView;
    }
}
