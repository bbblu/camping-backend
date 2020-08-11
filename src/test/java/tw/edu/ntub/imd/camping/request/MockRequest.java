package tw.edu.ntub.imd.camping.request;

import tw.edu.ntub.imd.camping.enumerate.ContentType;

import java.time.LocalDateTime;

public interface MockRequest {
    default MockRequest setContentType(ContentType contentType) {
        return addHeader("Content-Type", contentType.type);
    }

    MockRequest addHeader(String header, String value);

    MockRequest addHeader(String header, String... valueArray);

    MockRequest addHeader(String header, LocalDateTime value);

    MockRequest addQueryParameter(String key, Long... valueArray);

    MockRequest addQueryParameter(String key, Double... valueArray);

    MockRequest addQueryParameter(String key, Character... valueArray);

    MockRequest addQueryParameter(String key, String... valueArray);

    MockJsonRequest json();

    MockFormDataRequest formData();

    MockResponse send() throws Exception;
}
