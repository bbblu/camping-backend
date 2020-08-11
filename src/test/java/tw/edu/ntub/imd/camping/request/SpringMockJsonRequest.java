package tw.edu.ntub.imd.camping.request;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tw.edu.ntub.imd.camping.enumerate.HttpMethod;
import tw.edu.ntub.imd.camping.util.json.object.ObjectData;

import java.time.LocalDateTime;

public class SpringMockJsonRequest extends SpringMockRequest implements MockJsonRequest {
    private final String url;
    private final MockHttpServletRequestBuilder request;
    private ObjectData body;

    protected SpringMockJsonRequest(MockMvc mockMvc, String url, HttpMethod method) {
        this(mockMvc, url, MockMvcRequestBuilders.request(org.springframework.http.HttpMethod.valueOf(method.name()), url));
    }

    protected SpringMockJsonRequest(MockMvc mockMvc, String url, MockHttpServletRequestBuilder request) {
        super(mockMvc, url, request);
        this.url = url;
        this.request = request.contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8");
    }

    @Override
    public MockJsonRequest addHeader(String header, String value) {
        return (MockJsonRequest) super.addHeader(header, value);
    }

    @Override
    public MockJsonRequest addHeader(String header, String... valueArray) {
        return (MockJsonRequest) super.addHeader(header, valueArray);
    }

    @Override
    public MockJsonRequest addHeader(String header, LocalDateTime value) {
        return (MockJsonRequest) super.addHeader(header, value);
    }

    @Override
    public MockJsonRequest addQueryParameter(String key, Long... valueArray) {
        return (MockJsonRequest) super.addQueryParameter(key, valueArray);
    }

    @Override
    public MockJsonRequest addQueryParameter(String key, Double... valueArray) {
        return (MockJsonRequest) super.addQueryParameter(key, valueArray);
    }

    @Override
    public MockJsonRequest addQueryParameter(String key, Character... valueArray) {
        return (MockJsonRequest) super.addQueryParameter(key, valueArray);
    }

    @Override
    public MockJsonRequest addQueryParameter(String key, String... valueArray) {
        return (MockJsonRequest) super.addQueryParameter(key, valueArray);
    }

    @Override
    public MockJsonRequest json() {
        return this;
    }

    @Override
    public MockFormDataRequest formData() {
        MockFormDataRequest result = super.formData();
        if (body != null) {

        }
        return result;
    }

    @Override
    public MockJsonRequest setBody(ObjectData body) {
        this.body = body;
        request.content(body.toString());
        return this;
    }
}
