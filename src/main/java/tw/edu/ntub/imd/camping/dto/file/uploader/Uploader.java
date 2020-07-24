package tw.edu.ntub.imd.camping.dto.file.uploader;

import tw.edu.ntub.imd.camping.dto.file.directory.Directory;
import tw.edu.ntub.imd.camping.exception.file.FileException;

import javax.annotation.Nonnull;

@FunctionalInterface
public interface Uploader {
    @Nonnull
    UploadResult upload(@Nonnull Directory uploadTo) throws FileException;
}
