package tw.edu.ntub.imd.camping.request;

import lombok.Getter;

public class Status {
    public static final Status OK = of(200);
    public static final Status REQUEST_PARAMETER_UNMATCHED = of(400);
    public static final Status UNAUTHORIZED = of(401);
    public static final Status ACCESS_DENIED = of(403);
    public static final Status NOT_FOUND = of(404);
    public static final Status METHOD_NOT_SUPPORTED = of(405);
    public static final Status JSON_CONTENT_TYPE_REQUIRED = of(415);
    public static final Status SERVER_ERROR = of(500);

    @Getter
    private final int code;

    private Status(int code) {
        this.code = code;
    }

    public static Status of(int code) {
        return new Status(code);
    }
}
