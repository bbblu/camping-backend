package tw.edu.ntub.imd.camping.request;

import tw.edu.ntub.imd.camping.enumerate.ContentType;
import tw.edu.ntub.imd.camping.util.json.object.ObjectData;

import java.time.LocalDateTime;

public interface MockJsonRequest extends MockRequest {
    @Override
    default MockJsonRequest setContentType(ContentType contentType) {
        return (MockJsonRequest) MockRequest.super.setContentType(contentType);
    }

    @Override
    MockJsonRequest addHeader(String header, String value);

    @Override
    MockJsonRequest addHeader(String header, String... valueArray);

    @Override
    MockJsonRequest addHeader(String header, LocalDateTime value);

    MockJsonRequest setBody(ObjectData body);
}
