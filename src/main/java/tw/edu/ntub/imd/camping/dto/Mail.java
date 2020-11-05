package tw.edu.ntub.imd.camping.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class Mail {
    private final String templateViewName;
    private final ModelMap modelMap = new ModelMap();
    @Setter
    private String subject = "";
    private final List<String> to = new ArrayList<>();

    public Mail addAttribute(String name, Object value) {
        modelMap.addAttribute(name, value);
        return this;
    }

    public Mail addSendTo(String email) {
        to.add(email);
        return this;
    }
}
