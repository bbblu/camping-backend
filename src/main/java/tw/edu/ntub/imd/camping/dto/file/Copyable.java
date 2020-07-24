package tw.edu.ntub.imd.camping.dto.file;

import tw.edu.ntub.imd.camping.dto.file.directory.Directory;

import java.nio.file.StandardCopyOption;

public interface Copyable {
    void copyTo(Directory newDirectory, StandardCopyOption... options);
}
