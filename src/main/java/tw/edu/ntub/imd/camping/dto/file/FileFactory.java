package tw.edu.ntub.imd.camping.dto.file;

import lombok.experimental.UtilityClass;
import tw.edu.ntub.imd.camping.dto.file.excel.workbook.PoiWorkbook;
import tw.edu.ntub.imd.camping.util.file.FileUtils;

import java.nio.file.Path;

@UtilityClass
public class FileFactory {
    public File create(Path path) {
        Path fullFileNamePath = path.getFileName();
        String fileExtension = FileUtils.getFileExtension(fullFileNamePath.toString());
        switch (fileExtension) {
            case "txt":
            case "log":
                return new TextFile(path);
            case "xls":
            case "xlsx":
            case "xlsm":
                return new PoiWorkbook(path);
        }
        return new CommonFile(path);
    }
}
