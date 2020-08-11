package tw.edu.ntub.imd.camping.request;

import tw.edu.ntub.imd.camping.enumerate.HttpMethod;

public interface MockRequestFactory {
    default MockRequest create(String controllerUrl, HttpMethod method) {
        return create(controllerUrl, "", method);
    }

    MockRequest create(String controllerUrl, String methodUrl, HttpMethod method);

    default MockJsonRequest createJsonRequest(String controllerUrl, HttpMethod method) {
        return createJsonRequest(controllerUrl, "", method);
    }

    MockJsonRequest createJsonRequest(String controllerUrl, String methodUrl, HttpMethod method);

    default MockFormDataRequest createFormDataRequest(String controllerUrl, HttpMethod method) {
        return createFormDataRequest(controllerUrl, "");
    }

    MockFormDataRequest createFormDataRequest(String controllerUrl, String methodUrl);
}
