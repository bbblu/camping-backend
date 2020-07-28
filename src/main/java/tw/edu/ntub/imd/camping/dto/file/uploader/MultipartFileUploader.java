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
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MultipartFileUploader {
    private final Directory baseDirectory;
    private final String baseURL;

    public MultipartFileUploader(@Nonnull Directory baseDirectory, @Nonnull String baseURL) {
        this.baseDirectory = baseDirectory;
        this.baseURL = baseURL;
    }

    @Nonnull
    public UploadResult upload(@Nonnull MultipartFile multipartFile, String... subDirectoryName) throws FileException {
        if (multipartFile.isEmpty()) {
            throw new EmptyFileException(multipartFile.getName());
        } else if (baseDirectory.getAbsolutePath() == null) {
            throw new NotHavePathException();
        }
        Directory uploadTarget = baseDirectory;
        for (String name : subDirectoryName) {
            uploadTarget = uploadTarget.addDirectory(name);
        }
        if (uploadTarget.isNotExist()) {
            uploadTarget.create();
        }
        try {
            String fullRandomFileName = getFullRandomFileName(multipartFile);
            Path absolutePath = uploadTarget.getAbsolutePath();
            Path storePath = absolutePath.resolve(fullRandomFileName);
            multipartFile.transferTo(storePath);
            return UploadResult.builder()
                    .filePath(storePath)
                    .url(baseURL + "/" + String.join("/", subDirectoryName) + "/" + fullRandomFileName)
                    .build();
        } catch (IOException e) {
            throw new FileUnknownException(e);
        }
    }

    private String getFullRandomFileName(MultipartFile multipartFile) {
        String originalFilename = Objects.requireNonNull(multipartFile.getOriginalFilename());
        int firstDotIndex = originalFilename.indexOf(".");
        return FileUtils.getRandomFileName() + originalFilename.substring(firstDotIndex);
    }

    @Nonnull
    public List<UploadResult> upload(@Nonnull MultipartFile[] fileArray, String... subDirectoryName) throws FileException {
        return upload(List.of(fileArray), subDirectoryName);
    }

    @Nonnull
    public List<UploadResult> upload(@Nonnull List<MultipartFile> fileArray, String... subDirectoryName) throws FileException {
        return fileArray.parallelStream().map(file -> upload(file, subDirectoryName)).collect(Collectors.toList());
    }
}
