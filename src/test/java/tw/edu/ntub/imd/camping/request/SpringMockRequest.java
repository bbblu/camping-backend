package tw.edu.ntub.imd.camping.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tw.edu.ntub.imd.camping.enumerate.HttpMethod;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SpringMockRequest implements MockRequest {
    @NonNull
    private final MockMvc mockMvc;
    @NonNull
    private final String url;
    @NonNull
    private final MockHttpServletRequestBuilder request;
    private final Map<String, Object[]> headerMap = new HashMap<>();
    private final Map<String, String[]> queryParameterMap = new HashMap<>();

    protected SpringMockRequest(MockMvc mockMvc, String url, HttpMethod method) {
        this(
                mockMvc,
                url,
                MockMvcRequestBuilders.request(org.springframework.http.HttpMethod.valueOf(method.name()), url)
                        .characterEncoding("UTF-8")
        );
    }

    @Override
    public MockRequest addHeader(String header, String value) {
        headerMap.put(header, new Object[]{value});
        request.header(header, value);
        return this;
    }

    @Override
    public MockRequest addHeader(String header, String... valueArray) {
        Object[] values = Arrays.stream(valueArray).toArray();
        headerMap.put(header, valueArray);
        request.header(header, values);
        return this;
    }

    @Override
    public MockRequest addHeader(String header, LocalDateTime value) {
        headerMap.put(header, new Object[]{value});
        request.header(header, value);
        return this;
    }

    public MockRequest addHeader(String header, Object... values) {
        headerMap.put(header, values);
        request.header(header, values);
        return this;
    }

    @Override
    public MockRequest addQueryParameter(String key, Long... valueArray) {
        return addQueryParameter(key, Arrays.stream(valueArray)
                .map(value -> value != null ? String.valueOf(value) : null)
                .toArray(String[]::new)
        );
    }

    @Override
    public MockRequest addQueryParameter(String key, Double... valueArray) {
        return addQueryParameter(key, Arrays.stream(valueArray)
                .map(value -> value != null ? String.valueOf(value) : null)
                .toArray(String[]::new)
        );
    }

    @Override
    public MockRequest addQueryParameter(String key, Character... valueArray) {
        return addQueryParameter(key, Arrays.stream(valueArray)
                .map(value -> value != null ? String.valueOf(value) : null)
                .toArray(String[]::new)
        );
    }

    @Override
    public MockRequest addQueryParameter(String key, String... valueArray) {
        queryParameterMap.put(key, valueArray);
        request.queryParam(URLEncoder.encode(key, StandardCharsets.UTF_8), Arrays.stream(valueArray)
                .map(value -> URLEncoder.encode(value, StandardCharsets.UTF_8))
                .toArray(String[]::new)
        );
        return this;
    }

    @Override
    public MockJsonRequest json() {
        SpringMockJsonRequest springMockJsonRequest = new SpringMockJsonRequest(mockMvc, url, request);
        addHeader(springMockJsonRequest);
        addQueryParameter(springMockJsonRequest);
        return springMockJsonRequest;
    }

    private void addHeader(SpringMockRequest mockRequest) {
        headerMap.forEach(mockRequest::addHeader);
    }

    private void addQueryParameter(SpringMockRequest mockRequest) {
        queryParameterMap.forEach(mockRequest::addQueryParameter);
    }

    @Override
    public MockFormDataRequest formData() {
        SpringMockFormDataRequest springMockFormDataRequest = new SpringMockFormDataRequest(mockMvc, url);
        addHeader(springMockFormDataRequest);
        addQueryParameter(springMockFormDataRequest);
        return springMockFormDataRequest;
    }

    @Override
    public MockResponse send() throws Exception {
        return new SpringMockResponse(mockMvc.perform(request));
    }
}
