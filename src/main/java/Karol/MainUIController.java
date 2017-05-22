package Karol;

import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.Window;
import org.tepi.imageviewer.ImageViewer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainUIController extends MainUI{

    private List<String> titles;

    public MainUIController() {
        imgViewer.setImages(createImageList());
        imgViewer.setSideImageCount(5);
        imgViewer.setAnimationEnabled(true);
        imgViewer.setSideImageRelativeWidth(0.6f);
        imgViewer.setCenterImageIndex(1);
        imgViewer.focus();
        imgViewer.setCenterImageRelativeWidth(0.4f);
        imgViewer.setHiLiteEnabled(true);
        imgViewer.addListener(new ImageViewer.ImageSelectionListener() {
            @Override
            public void imageSelected(ImageViewer.ImageSelectedEvent imageSelectedEvent) {
                int index = imageSelectedEvent.getSelectedImageIndex();
                index++;
                if(index == titles.size()) index = 0;
                whatever.setValue(titles.get(index));
                whatever2.setValue(String.valueOf(index));

                Window window = new Window();
                DetaliedUIController abc = new DetaliedUIController();
                abc.fillDetails(new DataProvider().getMovieByTitle(titles.get(index)),null);
                window.setContent(abc);
                UI.getCurrent().addWindow(window);
            }
        });
    }

    private List<Resource> createImageList(){
        DataProvider dataProvider = new DataProvider();
        titles = dataProvider.getAllTitles();
        List<Resource> images = new ArrayList<>();

        titles.forEach(x -> {
            StreamResource resource = new StreamResource((StreamResource.StreamSource) () -> dataProvider.downloadImageAsStream(x), x);
            images.add(resource);
        });
        return images;
    }


}

