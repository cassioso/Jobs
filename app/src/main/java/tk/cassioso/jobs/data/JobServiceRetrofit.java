package tk.cassioso.jobs.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface JobServiceRetrofit {

    // http://docs.rentapanda.apiary.io/#reference/0/jobs-collection/list-all-jobs
    String BASE_URL = "http://private-14c693-rentapanda.apiary-mock.com/";

    Call<List<JobModel>> LIST_JOBS = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(JobServiceRetrofit.class).listJobs();

    @GET("jobs")
    Call<List<JobModel>> listJobs();
}
