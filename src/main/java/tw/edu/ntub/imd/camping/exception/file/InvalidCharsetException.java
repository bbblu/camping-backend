package tw.edu.ntub.imd.camping.exception.file;

public class InvalidCharsetException extends FileException {

    public InvalidCharsetException(String charsetName, Throwable cause) {
        super("此檔案的編碼不為: " + charsetName + ", 或是檔案內容為亂碼", cause);
    }

    @Override
    public String getReason() {
        return "InvalidCharset";
    }
}
