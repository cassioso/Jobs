package tk.cassioso.jobs.data;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;

import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;

import tk.cassioso.jobs.R;

/**
 * Created by cassioso on 6/13/16.
 * <p/>
 * Helper class to support data formatting
 */
public class PandaHelper {

    /*
    Returns a correspondent color depending on the status. Default color is Black.
     */
    public static int getStatusColor(PandaJobModel pandaJobModel) {
        switch (pandaJobModel.get__status()) {
            case "FULFILLED":
                return Color.parseColor("#0D47A1"); // blue
            case "START":
                return Color.parseColor("#1B5E20"); // green
            case "INVOICED":
                return Color.parseColor("#880E4F"); // pink
            case "CANCELLED":
                return Color.parseColor("#E65100"); // orange
            case "ERROR":
                return Color.parseColor("#b71c1c"); // red
            default:
                return Color.BLACK;
        }
    }


    /*
    Returns a correspondent res image depending on the city.
     */
    public static final int getImageResId(String cityname) {
        switch (cityname) {
            case "Berlin":
                return R.drawable.city_berlin;
            case "Cologne":
                return R.drawable.city_cologne;
            case "Düsseldorf":
                return R.drawable.city_dusseldorf;
            case "Frankfurt":
                return R.drawable.city_frankfurt;
            case "Hamburg":
                return R.drawable.city_hamburg;
            case "Munich":
                return R.drawable.city_munich;
            case "Stuttgart":
                return R.drawable.city_stuttgart;
            default:
                return R.drawable.city_default;
        }
    }

    /*
    Returns the full address
     */
    public static String getFormattedAddress(PandaJobModel pandaJobModel) {
        return pandaJobModel.getJob_city() + ", " + pandaJobModel.getJob_postalcode() + ", " + pandaJobModel.getJob_street();

    }

    /*
    The distance in the json is in Km
    */
    public static String getFormattedDistance(PandaJobModel pandaJobModel) {
        final String distance = pandaJobModel.getDistance();
        if (distance == null || distance.isEmpty()) {
            return "- KM"; // unkown distance
        } else {
            return String.format("%.2f KM", Double.valueOf(distance));
        }
    }

    /*
    The Recurrence translates as following:
    0 = Once
    7 = Weekly
    14 = Every 2 weeks
    28 = Monthly
     */
    public static String getFormattedRecurrency(Context context, PandaJobModel pandaJobModel) {
        switch (pandaJobModel.getRecurrency()) {
            case 0:
                return context.getString(R.string.recurrency_once);
            case 7:
                return context.getString(R.string.recurrency_weekly);
            case 14:
                return context.getString(R.string.recurrency_every2weeks);
            case 28:
                return context.getString(R.string.recurrency_monthly);
            default:
                return context.getString(R.string.recurrency_undefined); // unknown recurrency
        }
    }

    /*
    Dates should be displayed as following: dd.mm.yyyy
     */
    public static String getFormattedDate(PandaJobModel pandaJobModel) {
        final SimpleDateFormat sm = new SimpleDateFormat("dd.MM.yyyy");
        final String strDate = sm.format(pandaJobModel.getJob_date());
        return strDate;
    }

    /*
    Default price in euro
    TODO: internationalize price
     */
    public static String getFormattedPrice(PandaJobModel pandaJobModel) {
        return pandaJobModel.getPrice() + " EUR";
    }


    /*
     Duration
     */
    public static String getFormattedDuration(PandaJobModel pandaJobModel) {
        return pandaJobModel.getOrder_duration() + "h";
    }

    /*
    Returns LatLng object based on latlng data on PandaJobModel.
    It will returns 0,0 coordinates if something wrong happens during values parsing.
     */
    public static LatLng getFormattedLatLng(PandaJobModel pandaJobModel) {
        LatLng latlng = null;

        try {
            final double lat = Double.parseDouble(pandaJobModel.getJob_latitude());
            final double lng = Double.parseDouble(pandaJobModel.getJob_longitude());
            latlng = new LatLng(lat, lng);
        } catch (Exception e) {
            latlng = new LatLng(0, 0);
        }

        return latlng;
    }

    /*
    Copy PandaJobModel data to clipboard
     */
    public static void copyJobDetailToClipboard(PandaJobModel pandaJobModel, Activity activity) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard =
                    (android.text.ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);

            clipboard.setText(pandaJobModel.toString());
        } else {
            android.content.ClipboardManager clipboard =
                    (android.content.ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);

            android.content.ClipData clip =
                    android.content.ClipData.newPlainText(
                            "Copied Text", pandaJobModel.toString()
                    );

            clipboard.setPrimaryClip(clip);
        }
    }

}
