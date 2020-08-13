package tw.edu.ntub.imd.camping.dto.file;

import tw.edu.ntub.imd.camping.exception.file.FileExtensionNotFoundException;
import tw.edu.ntub.imd.camping.util.file.FileUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.nio.file.Path;

public class CommonFile implements File {
    private String name;
    private final String extension;
    private Path path;

    public CommonFile(@Nonnull String fullFileName) {
        this.name = FileUtils.getFileName(fullFileName);
        this.extension = FileUtils.getFileExtension(fullFileName);
        this.path = null;
    }

    public CommonFile(@Nonnull Path path) {
        Path fileNamePath = path.getFileName();
        this.name = fileNamePath.toString();
        String tempExtension;
        try {
            tempExtension = FileUtils.getFileExtension(name);
        } catch (FileExtensionNotFoundException e) {
            tempExtension = "";
        }
        this.extension = tempExtension;
        this.path = path;
    }

    @Nonnull
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Nonnull
    @Override
    public String getExtension() {
        return extension;
    }

    @Nullable
    @Override
    public Path getPath() {
        return path;
    }

    @Override
    public void setPath(Path path) {
        this.path = path;
    }
}
