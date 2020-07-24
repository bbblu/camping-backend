package tw.edu.ntub.imd.camping.dto.file;

import tw.edu.ntub.imd.camping.enumerate.ImageType;
import tw.edu.ntub.imd.camping.exception.file.FileUnknownException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

public class Image extends CommonFile {
    private final BufferedImage image;
    private final ImageType type;

    public Image(Path path) {
        super(path);
        try {
            this.image = ImageIO.read(path.toFile());
            this.type = ImageType.getByFormatName(getExtension());
        } catch (IOException e) {
            throw new FileUnknownException(e);
        }
    }

    public int getHeight() {
        return image.getHeight();
    }

    public int getWidth() {
        return image.getWidth();
    }

    public long getPixelCount() {
        return (long) getHeight() * getWidth();
    }

    public ImageType getType() {
        return type;
    }
}
