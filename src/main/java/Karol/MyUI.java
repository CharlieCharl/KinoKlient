package Karol;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
        * overridden to add component to the user interface and initialize non-component functionality.
        */
@Theme("mytheme")
public class MyUI extends UI {

    Image image;
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        Label label = new Label();
        Button button = new Button("Click Me");
       image = new Image();
        button.addClickListener( e -> {
         DataProvider dataProvider = new DataProvider();//  layout.addComponent(new Label(dataProvider.getMovieByTitle("Obcy:Przymierze").getDescription()));
           // label.setValue(dataProvider.getMovieByTitle("Obcy:Przymierze").getDescription());

            CompletableFuture.supplyAsync(() -> dataProvider.downloadImage("Obcy:Przymierze"))
                    .thenApply(x ->{
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        try {
                            ImageIO.write(x, "png", bos);
                            StreamResource resource = new StreamResource(new ByteArrayInputStream(bos.toByteArray()),"cokolwiek");
                            image.setSource(resource);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                    });
        });

        layout.addComponents(button, label, image);
       // setContent(new MainUIController());
    }



    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}

