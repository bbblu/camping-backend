package tw.edu.ntub.imd.camping.request;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.test.web.servlet.MockMvc;
import tw.edu.ntub.imd.camping.enumerate.HttpMethod;

@AllArgsConstructor(staticName = "getInstance")
public class SpringMockRequestFactory implements MockRequestFactory {
    @NonNull
    private final MockMvc mockMvc;

    @Override
    public MockRequest create(String controllerUrl, String methodUrl, HttpMethod method) {
        return new SpringMockRequest(mockMvc, controllerUrl + methodUrl, method);
    }

    @Override
    public MockJsonRequest createJsonRequest(String controllerUrl, String methodUrl, HttpMethod method) {
        return new SpringMockJsonRequest(mockMvc, controllerUrl + methodUrl, method);
    }

    @Override
    public MockFormDataRequest createFormDataRequest(String controllerUrl, String methodUrl) {
        return new SpringMockFormDataRequest(mockMvc, controllerUrl + methodUrl);
    }
}
