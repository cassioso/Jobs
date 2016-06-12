package tk.cassioso.jobs.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

// "org.jsonschema2pojo"
public class JobModel {

    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("job_date")
    @Expose
    private String jobDate;
    @SerializedName("extras")
    @Expose
    private String extras;
    @SerializedName("order_duration")
    @Expose
    private Double orderDuration;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("order_time")
    @Expose
    private String orderTime;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("recurrency")
    @Expose
    private Integer recurrency;
    @SerializedName("job_city")
    @Expose
    private String jobCity;
    @SerializedName("job_latitude")
    @Expose
    private String jobLatitude;
    @SerializedName("job_longitude")
    @Expose
    private String jobLongitude;
    @SerializedName("job_postalcode")
    @Expose
    private Integer jobPostalcode;
    @SerializedName("job_street")
    @Expose
    private String jobStreet;
    @SerializedName("status")
    @Expose
    private String status;

    /**
     * @return The customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName The customer_name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return The distance
     */
    public String getDistance() {
        return distance;
    }

    /**
     * @param distance The distance
     */
    public void setDistance(String distance) {
        this.distance = distance;
    }

    /**
     * @return The jobDate
     */
    public String getJobDate() {
        return jobDate;
    }

    /**
     * @param jobDate The job_date
     */
    public void setJobDate(String jobDate) {
        this.jobDate = jobDate;
    }

    /**
     * @return The extras
     */
    public String getExtras() {
        return extras;
    }

    /**
     * @param extras The extras
     */
    public void setExtras(String extras) {
        this.extras = extras;
    }

    /**
     * @return The orderDuration
     */
    public Double getOrderDuration() {
        return orderDuration;
    }

    /**
     * @param orderDuration The order_duration
     */
    public void setOrderDuration(Double orderDuration) {
        this.orderDuration = orderDuration;
    }

    /**
     * @return The orderId
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * @param orderId The order_id
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * @return The orderTime
     */
    public String getOrderTime() {
        return orderTime;
    }

    /**
     * @param orderTime The order_time
     */
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    /**
     * @return The paymentMethod
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * @param paymentMethod The payment_method
     */
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * @return The price
     */
    public String getPrice() {
        return price;
    }

    /**
     * @param price The price
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * @return The recurrency
     */
    public Integer getRecurrency() {
        return recurrency;
    }

    /**
     * @param recurrency The recurrency
     */
    public void setRecurrency(Integer recurrency) {
        this.recurrency = recurrency;
    }

    /**
     * @return The jobCity
     */
    public String getJobCity() {
        return jobCity;
    }

    /**
     * @param jobCity The job_city
     */
    public void setJobCity(String jobCity) {
        this.jobCity = jobCity;
    }

    /**
     * @return The jobLatitude
     */
    public String getJobLatitude() {
        return jobLatitude;
    }

    /**
     * @param jobLatitude The job_latitude
     */
    public void setJobLatitude(String jobLatitude) {
        this.jobLatitude = jobLatitude;
    }

    /**
     * @return The jobLongitude
     */
    public String getJobLongitude() {
        return jobLongitude;
    }

    /**
     * @param jobLongitude The job_longitude
     */
    public void setJobLongitude(String jobLongitude) {
        this.jobLongitude = jobLongitude;
    }

    /**
     * @return The jobPostalcode
     */
    public Integer getJobPostalcode() {
        return jobPostalcode;
    }

    /**
     * @param jobPostalcode The job_postalcode
     */
    public void setJobPostalcode(Integer jobPostalcode) {
        this.jobPostalcode = jobPostalcode;
    }

    /**
     * @return The jobStreet
     */
    public String getJobStreet() {
        return jobStreet;
    }

    /**
     * @param jobStreet The job_street
     */
    public void setJobStreet(String jobStreet) {
        this.jobStreet = jobStreet;
    }

    /**
     * @return The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}