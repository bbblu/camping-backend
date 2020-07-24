package tw.edu.ntub.imd.camping.exception.file;

public class EmptyFileException extends FileException {
    public EmptyFileException(String fileName) {
        super("未選擇檔案: " + fileName);
    }

    @Override
    public String getReason() {
        return "NotChoose";
    }
}
