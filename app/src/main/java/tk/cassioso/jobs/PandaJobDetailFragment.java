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

    @BindView(R.id.mapview)
    MapView mMapView;

    @BindView(R.id.pandajob_detail_status)
    TextView mStatusTextView;

    @BindView(R.id.pandajob_detail_orderid)
    TextView mOrderIdTextView;

    @BindView(R.id.pandajob_detail_address)
    TextView mAddressTextView;

    @BindView(R.id.pandajob_detail_distance)
    TextView mDitanceTextView;

    @BindView(R.id.pandajob_detail_latlng)
    TextView mLatLngTextView;

    @BindView(R.id.pandajob_detail_datetime)
    TextView mDateTimeTextView;

    @BindView(R.id.pandajob_detail_duration)
    TextView mDurationTextView;

    @BindView(R.id.pandajob_detail_recurrency)
    TextView mRecurrencyTextView;

    @BindView(R.id.pandajob_detail_price)
    TextView mPriceTextView;

    @BindView(R.id.pandajob_detail_paymentmethod)
    TextView mPaymentTextView;

    @BindView(R.id.pandajob_detail_extras)
    TextView mExtraTextView;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.pandajob_detail, container, false);
        ButterKnife.bind(this, rootView);


        // header, status and orderID

        final CollapsingToolbarLayout mAppBarLayout = (CollapsingToolbarLayout) getActivity().findViewById((R.id.toolbar_layout));
        if (mAppBarLayout != null) {
            mAppBarLayout.setTitle(mPandaJobModel.getCustomer_name() + " - " + mPandaJobModel.getJob_city());
            mAppBarLayout.setBackgroundResource(PandaHelper.getImageResId(mPandaJobModel.getJob_city()));
        }

        mStatusTextView.setText(mPandaJobModel.getStatus());
        mStatusTextView.setBackgroundColor(PandaHelper.getStatusColor(mPandaJobModel));

        mOrderIdTextView.setText(
                getString(R.string.pandajob_detail_orderid, mPandaJobModel.getOrder_id())
        );

        // location

        String txtAddress = mPandaJobModel.getJob_street() + ", " + mPandaJobModel.getJob_postalcode() + " " + mPandaJobModel.getJob_city();
        mAddressTextView.setText(txtAddress);

        mDitanceTextView.setText(
                getString(R.string.pandajob_detail_distance, PandaHelper.getFormattedDistance(mPandaJobModel))
        );

        mLatLngTextView.setText(
                PandaHelper.getFormattedLatLng(mPandaJobModel).toString()
        );

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

        // schedule

        mDateTimeTextView.setText(
                PandaHelper.getFormattedDate(mPandaJobModel) + " " + mPandaJobModel.getOrder_time()
        );

        mDurationTextView.setText(
                getString(R.string.pandajob_detail_duration, PandaHelper.getFormattedDuration(mPandaJobModel))
        );

        mRecurrencyTextView.setText(
                getString(R.string.pandajob_detail_recurrency, PandaHelper.getFormattedRecurrency(getContext(), mPandaJobModel).toLowerCase())
        );

        // service

        mPriceTextView.setText(
                PandaHelper.getFormattedPrice(mPandaJobModel)
        );

        mPaymentTextView.setText(
                getString(R.string.pandajob_detail_payment_method, mPandaJobModel.getPayment_method().toLowerCase())
        );

        String extras = mPandaJobModel.getExtras();
        if (extras == null || extras.isEmpty()) {
            mExtraTextView.setVisibility(View.GONE);
        } else {
            mExtraTextView.setVisibility(View.VISIBLE);

            extras = extras.replaceAll(";", "\n- ");

            mExtraTextView.setText(
                    getString(R.string.pandajob_detail_extras,
                            "\n- "+ extras)
            );
        }

        return rootView;
    }
}
