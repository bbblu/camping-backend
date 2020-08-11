package tw.edu.ntub.imd.camping.request;

import tw.edu.ntub.imd.camping.dto.FormDataFile;
import tw.edu.ntub.imd.camping.enumerate.ContentType;

import java.io.IOException;
import java.time.LocalDateTime;

public interface MockFormDataRequest extends MockRequest {
    @Override
    default MockFormDataRequest setContentType(ContentType contentType) {
        return (MockFormDataRequest) MockRequest.super.setContentType(contentType);
    }

    @Override
    MockFormDataRequest addHeader(String header, String value);

    @Override
    MockFormDataRequest addHeader(String header, String... valueArray);

    @Override
    MockFormDataRequest addHeader(String header, LocalDateTime value);

    @Override
    MockFormDataRequest addQueryParameter(String key, Long... valueArray);

    @Override
    MockFormDataRequest addQueryParameter(String key, Double... valueArray);

    @Override
    MockFormDataRequest addQueryParameter(String key, Character... valueArray);

    @Override
    MockFormDataRequest addQueryParameter(String key, String... valueArray);

    MockFormDataRequest addParameter(String key, Long... valueArray);

    MockFormDataRequest addParameter(String key, Double... valueArray);

    MockFormDataRequest addParameter(String key, Boolean... valueArray);

    MockFormDataRequest addParameter(String key, Character... valueArray);

    MockFormDataRequest addParameter(String key, String... valueArray);

    MockFormDataRequest addParameter(String key, FormDataFile... fileArray) throws IOException;
}
