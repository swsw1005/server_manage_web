package sw.im.swim.config;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class ControllerAdviceConfig {
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        String[] abd = new String[]{"class.*", "Class.*", "*.class.*", "*.Class.*"};
        dataBinder.setDisallowedFields(abd);
        StringTrimmerEditor stringtrimmer = new StringTrimmerEditor(false);
        dataBinder.registerCustomEditor(String.class, stringtrimmer);
    }
}
