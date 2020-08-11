package tw.edu.ntub.imd.camping.dto;

import lombok.*;

import java.io.InputStream;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class FormDataFile {
    private final String fileName;
    private final String mimeType;
    @NonNull
    private final InputStream file;
}
