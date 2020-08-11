package tw.edu.ntub.imd.camping.enumerate;

public enum ContentType {
    JSON("application/json"),
    JSON_UTF_8("application/json;charset=UTF-8"),
    TEXT("text/plain"),
    HTML("text/html");

    public final String type;

    ContentType(String type) {
        this.type = type;
    }
}
