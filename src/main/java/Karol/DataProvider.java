package Karol;

import Karol.model.Movie;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class DataProvider {
    private WebTarget webTarget = ClientBuilder.newClient().target("http://0.0.0.0:8082");
    public Movie getMovieByTitle(String title){
        return webTarget.path("kino/movies/movie/" + title).request().get(Movie.class);
    }
}
