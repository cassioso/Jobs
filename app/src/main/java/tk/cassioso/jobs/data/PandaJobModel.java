package tk.cassioso.jobs.data;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;
import tk.cassioso.jobs.R;

// "org.jsonschema2pojo"
@RealmClass
public class PandaJobModel extends RealmObject {
    private String __status;
    private String customer_name;
    private String distance;
    private Date job_date;
    private String extras;
    private int order_duration;
    private String order_id;
    private String order_time;
    private String payment_method;
    private float price;
    private int recurrency;
    private String job_city;
    private String job_latitude;
    private String job_longitude;
    private int job_postalcode;
    private String job_street;
    private String status;

    public String get__status() {
        return __status;
    }

    public void set__status(String __status) {
        this.__status = __status;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }


    public Date getJob_date() {
        return job_date;
    }

    public void setJob_date(Date job_date) {
        this.job_date = job_date;
    }

    public String getExtras() {
        return extras;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }

    public int getOrder_duration() {
        return order_duration;
    }

    public void setOrder_duration(int order_duration) {
        this.order_duration = order_duration;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getRecurrency() {
        return recurrency;
    }

    public void setRecurrency(int recurrency) {
        this.recurrency = recurrency;
    }

    public String getJob_city() {
        return job_city;
    }

    public void setJob_city(String job_city) {
        this.job_city = job_city;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getJob_latitude() {
        return job_latitude;
    }

    public void setJob_latitude(String job_latitude) {
        this.job_latitude = job_latitude;
    }

    public String getJob_longitude() {
        return job_longitude;
    }

    public void setJob_longitude(String job_longitude) {
        this.job_longitude = job_longitude;
    }

    public int getJob_postalcode() {
        return job_postalcode;
    }

    public void setJob_postalcode(int job_postalcode) {
        this.job_postalcode = job_postalcode;
    }

    public String getJob_street() {
        return job_street;
    }

    public void setJob_street(String job_street) {
        this.job_street = job_street;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /*
    Full address
     */
    public String getFormattedAddress() {
        return job_city + ", " + job_postalcode + ", " + job_street;

    }

    /*
        The distance in the json is in Km
         */
    public String getFormattedDistance() {
        if (distance == null || distance.isEmpty()) {
            return null;
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
    public String getFormattedRecurrency(Context context) {
        switch (recurrency) {
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
    public String getFormattedDate() {
        SimpleDateFormat sm = new SimpleDateFormat("dd.MM.yyyy");
        String strDate = sm.format(job_date);
        return strDate;
    }

    /*
    Default price in euro
    TODO: internationalize price
     */
    public String getFormattedPrice() {
        return "€" + price;
    }


    /**
     * Duration
     */
    public String getFormattedDuration() {
        return order_duration + "h";
    }


    @Override
    public String toString() {
        String text = "Order Id" + ": " + order_id;
        text += "\n";
        text += "Customer name" + ": " + customer_name;
        text += "\n";
        text += "\n";
        text += "Address" + ": " + getFormattedAddress();
        text += "\n";
        text += "Distance" + ": " + getFormattedDistance();
        text += "\n";
        text += "Latitude, Longitude" + ": " + job_latitude + ", " + job_longitude;
        text += "\n";
        text += "\n";
        text += "Job date" + ": " + getFormattedDate();
        text += "\n";
        text += "Job time" + ": " + order_time;
        text += "\n";
        text += "Duration" + ": " + getFormattedDuration();
        text += "\n";
        text += "Extras" + ": " + getExtras();
        text += "\n";
        text += "Payment method" + ": " + getPayment_method();
        text += "\n";
        text += "Price" + ": " + getFormattedPrice();
        text += "\n";
        text += "Recurrency" + ": " + recurrency;
        text += "\n";
        text += "Status" + ": " + status;
        return text;
    }
}