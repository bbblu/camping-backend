package tw.edu.ntub.imd.camping.dto.file.uploader;

import org.springframework.web.multipart.MultipartFile;
import tw.edu.ntub.imd.camping.dto.file.directory.Directory;
import tw.edu.ntub.imd.camping.exception.file.EmptyFileException;
import tw.edu.ntub.imd.camping.exception.file.FileException;
import tw.edu.ntub.imd.camping.exception.file.FileUnknownException;
import tw.edu.ntub.imd.camping.exception.file.NotHavePathException;
import tw.edu.ntub.imd.camping.util.file.FileUtils;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

public class MultipartFileUploader implements Uploader {
    private final String uploadBaseUrl;
    private final MultipartFile multipartFile;
    private final String[] subUrl;

    public MultipartFileUploader(@Nonnull String uploadBaseUrl, @Nonnull MultipartFile multipartFile, String... subUrl) {
        this.uploadBaseUrl = uploadBaseUrl;
        this.multipartFile = multipartFile;
        this.subUrl = subUrl;
    }

    @Nonnull
    @Override
    public UploadResult upload(@Nonnull Directory uploadTo) throws FileException {
        if (multipartFile.isEmpty()) {
            throw new EmptyFileException(multipartFile.getName());
        } else if (uploadTo.getAbsolutePath() == null) {
            throw new NotHavePathException();
        }
        if (uploadTo.isNotExist()) {
            uploadTo.create();
        }
        try {
            String fullRandomFileName = getFullRandomFileName();
            Path absolutePath = uploadTo.getAbsolutePath();
            Path storePath = absolutePath.resolve(fullRandomFileName);
            multipartFile.transferTo(storePath);
            return UploadResult.builder()
                    .filePath(storePath)
                    .url(uploadBaseUrl + "/" + String.join("/", subUrl) + "/" + fullRandomFileName)
                    .build();
        } catch (IOException e) {
            throw new FileUnknownException(e);
        }
    }

    private String getFullRandomFileName() {
        String originalFilename = Objects.requireNonNull(multipartFile.getOriginalFilename());
        int firstDotIndex = originalFilename.indexOf(".");
        return FileUtils.getRandomFileName() + originalFilename.substring(firstDotIndex);
    }
}
