package tw.edu.ntub.imd.camping.util.file;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tw.edu.ntub.imd.camping.config.properties.ImageProperties;

@Getter
@Component
public class UploadImageUtils {
    private final String url;
    private final String path;

    @Autowired
    public UploadImageUtils(ImageProperties imageProperties) {
        this.url = imageProperties.getUrl();
        this.path = imageProperties.getPath();
    }
}
