package tk.cassioso.jobs.data;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

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
}