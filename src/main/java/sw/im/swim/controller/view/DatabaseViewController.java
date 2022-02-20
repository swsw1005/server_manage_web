package sw.im.swim.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import sw.im.swim.bean.dto.DatabaseServerEntityDto;
import sw.im.swim.bean.dto.ServerInfoEntityDto;
import sw.im.swim.bean.enums.DbType;
import sw.im.swim.service.DatabaseServerService;
import sw.im.swim.service.ServerInfoService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Controller
@RequestMapping("/database")
@RequiredArgsConstructor
public class DatabaseViewController {

    private final DatabaseServerService databaseServerService;

    private final ServerInfoService serverInfoService;

    @GetMapping("/home")
    public ModelAndView home(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("common/main");

        mav.addObject("title", "database 관리");
        mav.addObject("mainPageUrl", "/database/main");
        return mav;
    }

    @GetMapping("/main")
    public ModelAndView main(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("database/main");
        return mav;
    }

    @GetMapping("/list")
    public ModelAndView list(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("database/list");

        List<DatabaseServerEntityDto> list = databaseServerService.getAll();
        mav.addObject("list", list);

        return mav;
    }

    @GetMapping("/form")
    public ModelAndView form(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("database/form");

        List<String> list = new ArrayList();
        for (DbType dbType : DbType.values()) {
            list.add(dbType.name());
        }

        mav.addObject("dbTypes", list);

        List<ServerInfoEntityDto> serverList = serverInfoService.getAll();
        mav.addObject("serverList", serverList);

        return mav;
    }


}
