package tw.edu.ntub.imd.camping.dto.file.excel.workbook;

import tw.edu.ntub.imd.camping.dto.file.File;
import tw.edu.ntub.imd.camping.dto.file.excel.sheet.Sheet;
import tw.edu.ntub.imd.camping.enumerate.ExcelType;
import tw.edu.ntub.imd.camping.exception.file.FileUnknownException;
import tw.edu.ntub.imd.camping.util.file.FileUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public interface Workbook extends File, AutoCloseable {
    Object getOriginalObject();

    ExcelType getType();

    List<Sheet> getLoadedSheetList();

    Workbook copyNewWorkbook(String newWorkbookName);

    Sheet getSheet(int index);

    Sheet getSheet(String sheetName);

    default Sheet copySheet(@Nonnull String copySource) {
        return copySheet(copySource, null);
    }

    Sheet copySheet(@Nonnull String copySource, @Nullable String newSheetName);

    void moveSheetAfter(String moveTargetName, String anotherSheetName);

    void moveSheetAfter(Sheet moveTarget, Sheet anotherSheet);

    void moveSheetBefore(String moveTargetName, String anotherSheetName);

    void moveSheetBefore(Sheet moveTarget, Sheet anotherSheet);

    default void saveAs(@Nonnull Path savePath) {
        saveAs(savePath, Objects.requireNonNull(getFullFileName()));
    }

    default void saveAs(@Nonnull Path savePath, @Nonnull String fileName) {
        try {
            FileOutputStream savePathOutputStream;
            if (Files.notExists(savePath)) {
                if (FileUtils.isDirectory(savePath)) {
                    Files.createDirectories(savePath);
                } else {
                    Files.createDirectories(savePath.getParent());
                }
            }
            if (FileUtils.isDirectory(savePath)) {
                savePathOutputStream = new FileOutputStream(savePath.resolve(fileName).toFile());
            } else {
                savePathOutputStream = new FileOutputStream(savePath.toFile());
            }
            export(savePathOutputStream);
            savePathOutputStream.close();
        } catch (IOException e) {
            throw new FileUnknownException(e);
        }
    }

    void export(OutputStream outputStream);
}
