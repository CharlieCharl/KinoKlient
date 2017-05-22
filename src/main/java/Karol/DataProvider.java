package Karol;

import Karol.model.Movie;
import Karol.model.Titles;

import javax.imageio.ImageIO;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class DataProvider {
    private WebTarget webTarget = ClientBuilder.newClient().target("http://localhost:8082");

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

    public InputStream downloadImageAsStream(String title){
        String path = "http://0.0.0.0:8082/kino/image/" + title;

        InputStream in = null;
        try {
            in = new java.net.URL(path).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return in;
    }

    public List<String> getAllTitles(){
        //webTarget.path("kino/movies/titles");
        return webTarget.path("kino/movies/titles").request().get(Titles.class).getTitles();

    }

}
