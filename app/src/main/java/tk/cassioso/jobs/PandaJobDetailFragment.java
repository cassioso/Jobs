package tk.cassioso.jobs;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import tk.cassioso.jobs.data.PandaHelper;
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

    @BindView(R.id.mapview)
    MapView mMapView;

    @BindView(R.id.pandajob_detail_status)
    TextView mStatusTextView;

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
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.pandajob_detail, container, false);
        ButterKnife.bind(this, rootView);

        final CollapsingToolbarLayout mAppBarLayout = (CollapsingToolbarLayout) getActivity().findViewById((R.id.toolbar_layout));
        if (mAppBarLayout != null) {
            mAppBarLayout.setTitle(mPandaJobModel.getCustomer_name() + " - " + mPandaJobModel.getJob_city());
            mAppBarLayout.setBackgroundResource(PandaHelper.getImageResId(mPandaJobModel.getJob_city()));
        }

        mStatusTextView.setText(mPandaJobModel.getStatus());
        mStatusTextView.setBackgroundColor(PandaHelper.getStatusColor(mPandaJobModel));

        if (mPandaJobModel != null) {

            /*
            job details part 1
             */

            String text = "Order Id" + ": " + mPandaJobModel.getOrder_id();
            text += "\n";
            text += "Customer name" + ": " + mPandaJobModel.getCustomer_name();
            text += "\n";
            text += "\n";
            text += "Address" + ": " + PandaHelper.getFormattedAddress(mPandaJobModel);
            text += "\n";
            text += "Distance" + ": " + PandaHelper.getFormattedDistance(mPandaJobModel);
            text += "\n";
            text += "Latitude, Longitude" + ": " + mPandaJobModel.getJob_latitude() + ", " + mPandaJobModel.getJob_longitude();

            mJobDetail1TextView.setText(text);

            /*
            job details map
            */
            mMapView.onCreate(savedInstanceState);
            mMapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    googleMap.clear();

                    UiSettings settings = googleMap.getUiSettings();
                    settings.setAllGesturesEnabled(false);
                    settings.setMyLocationButtonEnabled(false);

                    final LatLng latlng = PandaHelper.getFormattedLatLng(mPandaJobModel);
                    googleMap.addMarker(
                            new MarkerOptions()
                                    .position(latlng)
                                    .icon(BitmapDescriptorFactory
                                            .fromResource(R.drawable.ic_person_pin_black_36dp))
                    );

                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 13));
                }
            });


            /*
            job details part 2
             */

            text = "Job date" + ": " + PandaHelper.getFormattedDate(mPandaJobModel);
            text += "\n";
            text += "Job time" + ": " + mPandaJobModel.getOrder_time();
            text += "\n";
            text += "Duration" + ": " + PandaHelper.getFormattedDuration(mPandaJobModel);
            text += "\n";
            text += "Extras" + ": " + mPandaJobModel.getExtras();
            text += "\n";
            text += "Payment method" + ": " + mPandaJobModel.getPayment_method();
            text += "\n";
            text += "Price" + ": " + PandaHelper.getFormattedPrice(mPandaJobModel);
            text += "\n";
            text += "Recurrency" + ": " + mPandaJobModel.getRecurrency();
            text += "\n";
            text += "Status" + ": " + mPandaJobModel.getStatus();

            mJobDetail2TextView.setText(text);

        }
        return rootView;
    }
}
