package tw.edu.ntub.imd.camping.request;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tw.edu.ntub.imd.camping.dto.FormDataFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

public class SpringMockFormDataRequest extends SpringMockRequest implements MockFormDataRequest {
    private final String url;
    private final MockMultipartHttpServletRequestBuilder request;

    protected SpringMockFormDataRequest(MockMvc mockMvc, String url) {
        this(mockMvc, url, MockMvcRequestBuilders.multipart(url));
    }

    protected SpringMockFormDataRequest(MockMvc mockMvc, String url, MockMultipartHttpServletRequestBuilder request) {
        super(mockMvc, url, request);
        this.url = url;
        this.request = request;
    }

    @Override
    public MockFormDataRequest addHeader(String header, String value) {
        return (MockFormDataRequest) super.addHeader(header, value);
    }

    @Override
    public MockFormDataRequest addHeader(String header, String... valueArray) {
        return (MockFormDataRequest) super.addHeader(header, valueArray);
    }

    @Override
    public MockFormDataRequest addHeader(String header, LocalDateTime value) {
        return (MockFormDataRequest) super.addHeader(header, value);
    }

    @Override
    public MockFormDataRequest addQueryParameter(String key, Long... valueArray) {
        return (MockFormDataRequest) super.addQueryParameter(key, valueArray);
    }

    @Override
    public MockFormDataRequest addQueryParameter(String key, Double... valueArray) {
        return (MockFormDataRequest) super.addQueryParameter(key, valueArray);
    }

    @Override
    public MockFormDataRequest addQueryParameter(String key, Character... valueArray) {
        return (MockFormDataRequest) super.addQueryParameter(key, valueArray);
    }

    @Override
    public MockFormDataRequest addQueryParameter(String key, String... valueArray) {
        return (MockFormDataRequest) super.addQueryParameter(key, valueArray);
    }

    @Override
    public MockFormDataRequest addParameter(String key, Long... valueArray) {
        return addParameter(key, Arrays.stream(valueArray)
                .map(value -> value != null ? String.valueOf(value) : null)
                .toArray(String[]::new)
        );
    }

    @Override
    public MockFormDataRequest addParameter(String key, Double... valueArray) {
        return addParameter(key, Arrays.stream(valueArray)
                .map(value -> value != null ? String.valueOf(value) : null)
                .toArray(String[]::new)
        );
    }

    @Override
    public MockFormDataRequest addParameter(String key, Boolean... valueArray) {
        return addParameter(key, Arrays.stream(valueArray)
                .map(value -> value != null ? String.valueOf(value) : null)
                .toArray(String[]::new)
        );
    }

    @Override
    public MockFormDataRequest addParameter(String key, Character... valueArray) {
        return addParameter(key, Arrays.stream(valueArray)
                .map(value -> value != null ? String.valueOf(value) : null)
                .toArray(String[]::new)
        );
    }

    @Override
    public MockFormDataRequest addParameter(String key, String... valueArray) {
        request.param(key, valueArray);
        return this;
    }

    @Override
    public MockFormDataRequest addParameter(String key, FormDataFile... fileArray) throws IOException {
        for (FormDataFile file : fileArray) {
            request.file(new MockMultipartFile(key, file.getFileName(), file.getMimeType(), file.getFile()));
        }
        return this;
    }

    @Override
    public MockJsonRequest json() {
        return super.json();
    }

    @Override
    public MockFormDataRequest formData() {
        return this;
    }
}
