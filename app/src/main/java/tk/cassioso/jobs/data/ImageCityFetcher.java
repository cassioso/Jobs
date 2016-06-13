package tk.cassioso.jobs.data;

/**
 * Created by cassioso on 6/13/16.
 * TODO: implement a real city image fetcher
 */
public class ImageCityFetcher {

    public static final String getImageUrl(String cityname){
        switch(cityname){
            case "Berlin":
                return "http://www.elal.co.il/NR/rdonlyres/FBCB40B7-2C32-453B-885B-409798A3C894/0/shutterstock_107513486.jpg";
            case "Munich":
                return "https://www.explorica.com/-/media/Images/Tour%20Pictures/destinations/ix-mpk.ashx";
            case "Stuttgart":
                return "http://www.stuttgart-journal.de/tp2/uploads/pics/Sommerfest-Stuttgart-SM1_01.jpg";
            default:
                return null;
        }
    }

}
