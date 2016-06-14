package tk.cassioso.jobs.data;

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
    Full address
     */
    public static String getFormattedAddress(PandaJobModel pandaJobModel) {
        return pandaJobModel.getJob_city() + ", " + pandaJobModel.getJob_postalcode() + ", " + pandaJobModel.getJob_street();

    }

    /*
        The distance in the json is in Km
    */
    public static String getFormattedDistance(PandaJobModel pandaJobModel) {
        String distance = pandaJobModel.getDistance();
        if (distance == null || distance.isEmpty()) {
            return "- KM";
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
                return context.getString(R.string.recurrency_undefined);
        }
    }

    /*
    Dates should be displayed as following: dd.mm.yyyy
     */
    public static String getFormattedDate(PandaJobModel pandaJobModel) {
        SimpleDateFormat sm = new SimpleDateFormat("dd.MM.yyyy");
        String strDate = sm.format(pandaJobModel.getJob_date());
        return strDate;
    }

    /*
    Default price in euro
    TODO: internationalize price
     */
    public static String getFormattedPrice(PandaJobModel pandaJobModel) {
        return pandaJobModel.getPrice() + "EUR";
    }


    /**
     * Duration
     */
    public static String getFormattedDuration(PandaJobModel pandaJobModel) {
        return pandaJobModel.getOrder_duration() + "h";
    }

    public static String toString(Context context, PandaJobModel pandaJobModel) {
        String text = "Order Id" + ": " + pandaJobModel.getOrder_id();
        text += "\n";
        text += "Customer name" + ": " + pandaJobModel.getCustomer_name();
        text += "\n";
        text += "\n";
        text += "Address" + ": " + getFormattedAddress(pandaJobModel);
        text += "\n";
        text += "Distance" + ": " + getFormattedDistance(pandaJobModel);
        text += "\n";
        text += "Latitude, Longitude" + ": " + pandaJobModel.getJob_latitude() + ", " + pandaJobModel.getJob_longitude();
        text += "\n";
        text += "\n";
        text += "Job date" + ": " + getFormattedDate(pandaJobModel);
        text += "\n";
        text += "Job time" + ": " + pandaJobModel.getOrder_time();
        text += "\n";
        text += "Duration" + ": " + getFormattedDuration(pandaJobModel);
        text += "\n";
        text += "Extras" + ": " + pandaJobModel.getExtras();
        text += "\n";
        text += "Payment method" + ": " + pandaJobModel.getPayment_method();
        text += "\n";
        text += "Price" + ": " + getFormattedPrice(pandaJobModel);
        text += "\n";
        text += "Recurrency" + ": " + getFormattedRecurrency(context, pandaJobModel);
        text += "\n";
        text += "Status" + ": " + pandaJobModel.getStatus();
        return text;
    }

    public static LatLng getFormattedLatLng(PandaJobModel pandaJobModel) {
        LatLng latlng = null;

        try {
            double lat = Double.parseDouble(pandaJobModel.getJob_latitude());
            double lng = Double.parseDouble(pandaJobModel.getJob_longitude());
            latlng = new LatLng(lat, lng);
        } catch (Exception e) {
            latlng = new LatLng(0, 0);
        }

        return latlng;
    }

}
