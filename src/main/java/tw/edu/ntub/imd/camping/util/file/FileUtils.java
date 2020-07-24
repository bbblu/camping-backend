package tw.edu.ntub.imd.camping.util.file;

import lombok.experimental.UtilityClass;
import tw.edu.ntub.birc.common.exception.UnknownException;
import tw.edu.ntub.imd.camping.exception.file.FileExtensionNotFoundException;
import tw.edu.ntub.imd.camping.exception.file.InvalidCharsetException;
import tw.edu.ntub.imd.camping.exception.file.InvalidOptionException;
import tw.edu.ntub.imd.camping.exception.file.UnauthorizedException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.UUID;

@UtilityClass
@SuppressWarnings("unused")
public class FileUtils {
    @Nonnull
    public String getRandomFileName() {
        return UUID.randomUUID().toString();
    }

    @Nonnull
    public String replaceFileName(@Nonnull String fullFileName) {
        return replaceFileName(fullFileName, null);
    }

    @Nonnull
    public String replaceFileName(@Nonnull String fullFileName, @Nullable String newFileName) {
        if (newFileName == null) {
            newFileName = getRandomFileName();
        }
        try {
            String fileExtension = getFileExtension(fullFileName);
            return newFileName + "." + fileExtension;
        } catch (FileExtensionNotFoundException e) {
            return newFileName;
        }
    }

    public String getFileName(String fullFileName) {
        int lastDotIndex = fullFileName.lastIndexOf('.');
        if (lastDotIndex != -1) {
            return fullFileName.substring(0, lastDotIndex);
        } else {
            return fullFileName;
        }
    }

    @Nonnull
    public String getFileExtension(@Nonnull String fullFileName) throws FileExtensionNotFoundException {
        int lastDotIndex = fullFileName.lastIndexOf('.');
        if (lastDotIndex == -1) {
            throw new FileExtensionNotFoundException(fullFileName);
        } else {
            return fullFileName.substring(lastDotIndex + 1);
        }
    }

    @Nonnull
    public InputStream openInputStream(@Nonnull String filePath, OpenOption... openOptionArray) {
        try {
            return Files.newInputStream(getFilePath(filePath), openOptionArray);
        } catch (IllegalArgumentException e) {
            throw new InvalidOptionException(e);
        } catch (SecurityException e) {
            throw new UnauthorizedException(e);
        } catch (Exception e) {
            throw new UnknownException(e);
        }
    }

    @Nonnull
    public Path getFilePath(@Nonnull String filePath) {
        try {
            return Paths.get(filePath);
        } catch (InvalidPathException e) {
            throw new tw.edu.ntub.imd.camping.exception.file.InvalidPathException(e, filePath);
        }
    }

    @Nonnull
    public Path getDirectoryPath(@Nonnull String firstDirectoryPath, String... subDirectoryPath) {
        try {
            return Paths.get(firstDirectoryPath, subDirectoryPath);
        } catch (InvalidPathException e) {
            throw new tw.edu.ntub.imd.camping.exception.file.InvalidPathException(e, firstDirectoryPath, subDirectoryPath);
        }
    }

    @Nonnull
    public OutputStream openOutputStreamUseOverwriteMode(@Nonnull String filePath) {
        return openOutputStream(filePath);
    }

    @Nonnull
    public OutputStream openOutputStream(@Nonnull String filePath, OpenOption... openOptionArray) {
        try {
            return Files.newOutputStream(getFilePath(filePath), openOptionArray);
        } catch (Exception e) {
            throw new UnknownException(e);
        }
    }

    @Nonnull
    public OutputStream openOutputStreamUseAppendMode(@Nonnull String filePath) {
        return openOutputStream(filePath, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
    }

    @Nonnull
    public BufferedReader openReader(@Nonnull String filePath) {
        return openReader(filePath, StandardCharsets.UTF_8);
    }

    @Nonnull
    public BufferedReader openReader(@Nonnull String filePath, @Nonnull Charset charset) {
        try {
            return Files.newBufferedReader(getFilePath(filePath), charset);
        } catch (MalformedInputException e) {
            throw new InvalidCharsetException(charset.name(), e);
        } catch (SecurityException e) {
            throw new UnauthorizedException(e);
        } catch (Exception e) {
            throw new UnknownException(e);
        }
    }

    @Nonnull
    public BufferedWriter openWriter(@Nonnull String filePath, @Nonnull Charset charset) {
        try {
            return Files.newBufferedWriter(getFilePath(filePath), charset);
        } catch (IllegalArgumentException e) {
            throw new InvalidOptionException(e);
        } catch (SecurityException e) {
            throw new UnauthorizedException(e);
        } catch (Exception e) {
            throw new UnknownException(e);
        }
    }

    public void copy(@Nonnull InputStream source, @Nonnull String targetPath, @Nonnull String fileNameExtension) {
        copy(source, targetPath, getRandomFileName(), fileNameExtension);
    }

    public void copy(@Nonnull InputStream source, @Nonnull String targetPath, @Nonnull String fileName, @Nonnull String fileNameExtension) {
        copyByFullFileName(source, targetPath, fileName + "." + fileNameExtension);
    }

    public void copyByFullFileName(@Nonnull InputStream source, @Nonnull String targetPath, @Nonnull String fullFileName) {
        Path directoryPath = getDirectoryPath(targetPath);
        Path targetFilePath = directoryPath.resolve(fullFileName);
        copy(source, targetFilePath);
    }

    public void copy(@Nonnull InputStream source, @Nonnull Path targetFilePath) {
        try {
            Files.copy(source, targetFilePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (SecurityException e) {
            throw new UnauthorizedException(e);
        } catch (Exception e) {
            throw new UnknownException(e);
        }
    }

    public void copyByRandomFileName(@Nonnull InputStream source, @Nonnull String targetPath, @Nonnull String fullFileName) {
        copyByFullFileName(source, targetPath, replaceFileName(fullFileName));
    }

    public boolean isNotDirectory(@Nonnull Path path) {
        return !isDirectory(path);
    }

    public boolean isDirectory(@Nonnull Path path) {
        return Files.isDirectory(path);
    }

    public String getFullFileNameFromPath(Path path) {
        Path fileNamePath = path.getFileName();
        return fileNamePath.toString();
    }
}
