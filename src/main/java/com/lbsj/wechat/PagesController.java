package com.lbsj.wechat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PagesController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
