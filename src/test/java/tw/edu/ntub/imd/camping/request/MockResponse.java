package tw.edu.ntub.imd.camping.request;

import tw.edu.ntub.imd.camping.enumerate.ContentType;

import java.time.LocalDateTime;

public interface MockResponse {
    Body getBody();

    MockResponse assertStatusEquals(Status expectedHttpStatus) throws Exception;

    MockResponse assertHasHeader(String header) throws Exception;

    default MockResponse assertContentTypeEquals(ContentType contentType) throws Exception {
        return assertHeaderEquals("Content-Type", contentType.type);
    }

    default MockResponse assertHeaderEquals(String header, long expectedValue) throws Exception {
        return assertHeaderEquals(header, String.valueOf(expectedValue));
    }

    MockResponse assertHeaderEquals(String header, String expectedValue) throws Exception;

    MockResponse assertHeaderEquals(String header, String... expectedValueArray) throws Exception;

    MockResponse assertHeaderEquals(String dateTimeHeader, LocalDateTime expectedValue) throws Exception;

    MockResponse assertBodyValueExist(String jsonPath) throws Exception;

    MockResponse assertBodyValueNotExist(String jsonPath) throws Exception;

    MockResponse assertBodyArrayLengthEquals(String jsonPath, int expectedLength) throws Exception;

    MockResponse assertBodyValueIsObject(String jsonPath) throws Exception;

    MockResponse assertBodyValueIsArray(String jsonPath) throws Exception;

    MockResponse assertBodyValueIsEmpty(String jsonPath) throws Exception;

    MockResponse assertBodyValueIsNotEmpty(String jsonPath) throws Exception;

    MockResponse assertBodyValueIsEmptyString(String jsonPath) throws Exception;

    MockResponse assertBodyValueIsNotEmptyString(String jsonPath) throws Exception;

    MockResponse assertBodyValueEquals(String jsonPath, String expectedValue) throws Exception;

    MockResponse assertBodyValueEquals(String jsonPath, long expectedValue) throws Exception;

    MockResponse assertBodyValueEquals(String jsonPath, double expectedValue) throws Exception;

    MockResponse assertBodyValueEquals(String jsonPath, char expectedValue) throws Exception;

    MockResponse assertBodyValueEquals(String jsonPath, boolean expectedValue) throws Exception;
}
