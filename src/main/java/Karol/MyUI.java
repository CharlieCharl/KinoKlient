package Karol;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.ui.*;

import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
        * overridden to add component to the user interface and initialize non-component functionality.
        */
@Push()
@Theme("mytheme")
public class MyUI extends UI {

    private Image image;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        Label label = new Label();
        image = new Image();

        DataProvider dataProvider = new DataProvider();//  layout.addComponent(new Label(dataProvider.getMovieByTitle("Obcy:Przymierze").getDescription()));
        CompletableFuture.supplyAsync(() -> dataProvider.downloadImageAsStream("Obcy:Przymierze"))
                .thenAccept((InputStream x) -> {
                    image.setSource(new StreamResource((StreamResource.StreamSource) () -> x, ""));
                    //UI.getCurrent().push();
                    image.requestRepaint();
                });
        layout.addComponents(label, image);
       // setContent(layout);
        setContent(new MainUIController());
    }


    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}

