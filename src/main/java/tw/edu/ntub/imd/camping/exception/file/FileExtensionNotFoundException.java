package tw.edu.ntub.imd.camping.exception.file;

public class FileExtensionNotFoundException extends FileException {
    public FileExtensionNotFoundException(String fileName) {
        super("此檔案沒有副檔名: " + fileName);
    }

    @Override
    public String getReason() {
        return "NoFileExtension";
    }
}
