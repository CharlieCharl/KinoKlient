package Karol;

import Karol.model.Movie;

import javax.imageio.ImageIO;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class DataProvider {
    private WebTarget webTarget = ClientBuilder.newClient().target("http://0.0.0.0:8082");

    public Movie getMovieByTitle(String title){
        return webTarget.path("kino/movies/movie/" + title).request().get(Movie.class);
    }
    public BufferedImage downloadImage(String title){
        BufferedImage bitmap = null;
        String path = "http://0.0.0.0:8082/kino/image/" + title;
        try {
            InputStream in = new java.net.URL(path).openStream();
            bitmap = ImageIO.read(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
