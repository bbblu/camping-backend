package tw.edu.ntub.imd.camping.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.hamcrest.Matchers;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SpringMockResponse implements MockResponse {
    @NonNull
    private final ResultActions response;

    @Override
    public Body getBody() {
        return null;
    }

    @Override
    public MockResponse assertStatusEquals(Status expectedHttpStatus) throws Exception {
        response.andExpect(MockMvcResultMatchers.status().is(expectedHttpStatus.getCode()));
        return this;
    }

    @Override
    public MockResponse assertHasHeader(String header) throws Exception {
        response.andExpect(MockMvcResultMatchers.header().exists(header));
        return this;
    }

    @Override
    public MockResponse assertHeaderEquals(String header, String expectedValue) throws Exception {
        response.andExpect(MockMvcResultMatchers.header().string(header, expectedValue));
        return this;
    }

    @Override
    public MockResponse assertHeaderEquals(String header, String... expectedValueArray) throws Exception {
        response.andExpect(MockMvcResultMatchers.header().stringValues(header, expectedValueArray));
        return this;
    }

    @Override
    public MockResponse assertHeaderEquals(String header, LocalDateTime expectedValue) throws Exception {
        ZonedDateTime zonedDateTime = expectedValue.atZone(ZoneId.systemDefault());
        response.andExpect(MockMvcResultMatchers.header().dateValue(header, zonedDateTime.toEpochSecond()));
        return this;
    }

    @Override
    public MockResponse assertBodyValueExist(String jsonPath) throws Exception {
        response.andExpect(MockMvcResultMatchers.jsonPath(jsonPath).exists());
        return this;
    }

    @Override
    public MockResponse assertBodyValueNotExist(String jsonPath) throws Exception {
        response.andExpect(MockMvcResultMatchers.jsonPath(jsonPath).doesNotExist());
        return this;
    }

    @Override
    public MockResponse assertBodyArrayLengthEquals(String jsonPath, int expectedLength) throws Exception {
        response.andExpect(MockMvcResultMatchers.jsonPath(jsonPath, Matchers.hasSize(expectedLength)));
        return this;
    }

    @Override
    public MockResponse assertBodyValueIsObject(String jsonPath) throws Exception {
        response.andExpect(MockMvcResultMatchers.jsonPath(jsonPath).isMap());
        return this;
    }

    @Override
    public MockResponse assertBodyValueIsArray(String jsonPath) throws Exception {
        response.andExpect(MockMvcResultMatchers.jsonPath(jsonPath).isArray());
        return this;
    }

    @Override
    public MockResponse assertBodyValueIsEmpty(String jsonPath) throws Exception {
        response.andExpect(MockMvcResultMatchers.jsonPath(jsonPath).isEmpty());
        return this;
    }

    @Override
    public MockResponse assertBodyValueIsNotEmpty(String jsonPath) throws Exception {
        response.andExpect(MockMvcResultMatchers.jsonPath(jsonPath).isNotEmpty());
        return this;
    }

    @Override
    public MockResponse assertBodyValueIsEmptyString(String jsonPath) throws Exception {
        response.andExpect(MockMvcResultMatchers.jsonPath(jsonPath).isEmpty());
        return this;
    }

    @Override
    public MockResponse assertBodyValueIsNotEmptyString(String jsonPath) throws Exception {
        response.andExpect(MockMvcResultMatchers.jsonPath(jsonPath).isNotEmpty());
        return this;
    }

    @Override
    public MockResponse assertBodyValueEquals(String jsonPath, String expectedValue) throws Exception {
        response.andExpect(MockMvcResultMatchers.jsonPath(jsonPath).value(expectedValue));
        return this;
    }

    @Override
    public MockResponse assertBodyValueEquals(String jsonPath, long expectedValue) throws Exception {
        response.andExpect(MockMvcResultMatchers.jsonPath(jsonPath).value(expectedValue));
        return this;
    }

    @Override
    public MockResponse assertBodyValueEquals(String jsonPath, double expectedValue) throws Exception {
        response.andExpect(MockMvcResultMatchers.jsonPath(jsonPath).value(expectedValue));
        return this;
    }

    @Override
    public MockResponse assertBodyValueEquals(String jsonPath, char expectedValue) throws Exception {
        response.andExpect(MockMvcResultMatchers.jsonPath(jsonPath).value(expectedValue));
        return this;
    }

    @Override
    public MockResponse assertBodyValueEquals(String jsonPath, boolean expectedValue) throws Exception {
        response.andExpect(MockMvcResultMatchers.jsonPath(jsonPath).value(expectedValue));
        return this;
    }
}
