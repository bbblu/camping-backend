package tw.edu.ntub.imd.camping.util.http;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import tw.edu.ntub.imd.camping.dto.file.excel.workbook.Workbook;
import tw.edu.ntub.imd.camping.exception.file.FileUnknownException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@UtilityClass
public class ExcelResponseUtils {

    public void response(HttpServletResponse response, Workbook workbook) {
        try {
            String fileName = workbook.getFullFileName();
            String urlEncodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + urlEncodedFileName);
            response.setCharacterEncoding("UTF-8");
            workbook.export(response.getOutputStream());
        } catch (IOException e) {
            throw new FileUnknownException(e);
        }
    }
}
