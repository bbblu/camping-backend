package tw.edu.ntub.imd.camping.exception.file;

public class NotHavePathException extends FileException {
    public NotHavePathException() {
        super("該檔案沒有設定路徑");
    }

    @Override
    public String getReason() {
        return "NoPath";
    }
}
