package tw.edu.ntub.imd.camping.util.http;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import tw.edu.ntub.birc.common.exception.ProjectException;
import tw.edu.ntub.imd.camping.dto.CodeEntry;
import tw.edu.ntub.imd.camping.util.function.AddObjectDataConsumer;
import tw.edu.ntub.imd.camping.util.function.AddObjectDataMapConsumer;
import tw.edu.ntub.imd.camping.util.function.TripleConsumer;
import tw.edu.ntub.imd.camping.util.json.ResponseData;
import tw.edu.ntub.imd.camping.util.json.array.ArrayData;
import tw.edu.ntub.imd.camping.util.json.array.CollectionArrayData;
import tw.edu.ntub.imd.camping.util.json.array.MapArrayData;
import tw.edu.ntub.imd.camping.util.json.object.ObjectData;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * 回傳標準格式
 * {
 * "result": Boolean,
 * "errorCode": String,
 * "message": String,
 * "data": Object/Array
 * }
 */
@Log4j2
public class ResponseEntityBuilder {
    private final ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.ok();
    private boolean success;
    private ProjectException ProjectException;
    private String message;
    private ResponseData responseData;

    private ResponseEntityBuilder(ProjectException ProjectException) {
        this(false);
        this.ProjectException = ProjectException;
        this.message = ProjectException.getMessage();
    }

    private ResponseEntityBuilder(boolean success) {
        this.success = success;
    }

    public static ResponseEntity<String> buildSuccessMessage(String message) {
        return success(message).build();
    }

    public static ResponseEntityBuilder success(String message) {
        return success().message(message);
    }

    public static ResponseEntityBuilder success() {
        return new ResponseEntityBuilder(true);
    }

    public static ResponseEntityBuilder error() {
        return new ResponseEntityBuilder(false);
    }

    public static ResponseEntityBuilder error(@Nonnull ProjectException projectException) {
        log.error("發生錯誤！", projectException);
        return new ResponseEntityBuilder(projectException);
    }

    public ResponseEntityBuilder result(boolean isSuccess) {
        success = isSuccess;
        return this;
    }

    public ResponseEntityBuilder message(String message) {
        this.message = message;
        return this;
    }

    public ResponseEntityBuilder emptyObject() {
        return data(new ObjectData());
    }

    public ResponseEntityBuilder emptyArray() {
        return data(new ArrayData());
    }

    public ResponseEntityBuilder data(Collection<? extends CodeEntry> resource) {
        return data(new CollectionArrayData(resource));
    }

    public <T> ResponseEntityBuilder data(Collection<T> resource, BiConsumer<ObjectData, T> addObjectDataConsumer) {
        return data(new CollectionArrayData(resource, addObjectDataConsumer));
    }

    public <T> ResponseEntityBuilder data(Collection<T> resource, AddObjectDataConsumer<T> addObjectDataConsumer) {
        return data(new CollectionArrayData(resource, addObjectDataConsumer));
    }

    public ResponseEntityBuilder data(Map<String, String> resource) {
        return data(new MapArrayData(resource));
    }

    public <K, V> ResponseEntityBuilder data(Map<K, V> resource, TripleConsumer<ObjectData, K, V> addObjectDataConsumer) {
        return data(new MapArrayData(resource, addObjectDataConsumer));
    }

    public <K, V> ResponseEntityBuilder data(Map<K, V> resource, AddObjectDataMapConsumer<K, V> addObjectDataMapConsumer) {
        return data(new MapArrayData(resource, addObjectDataMapConsumer));
    }

    public ResponseEntityBuilder data(ResponseData responseData) {
        this.responseData = responseData;
        return this;
    }

    public ResponseEntityBuilder addHeader(String name, String... valueArray) {
        bodyBuilder.header(name, valueArray);
        return this;
    }

    public ResponseEntity<String> build() {
        return bodyBuilder.body(buildJSONString());
    }

    public String buildJSONString() {
        ObjectData result = new ObjectData()
                .add("result", success)
                .add("errorCode", ProjectException != null ? ProjectException.getErrorCode() : "")
                .add("message", message)
                .replace("data",
                        responseData != null ?
                                responseData.getData() :
                                new ObjectData().getData()
                );
        String body = result.toString();
        System.out.println("Response JSON = " + body);
        return body;
    }
}
