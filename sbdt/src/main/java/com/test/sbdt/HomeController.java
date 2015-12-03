package com.test.sbdt;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

/**
 * @author Davor Sauer
 */
@Controller
public class HomeController {


    @RequestMapping(value = "/time", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public LocalDateTime time() {
        return LocalDateTime.now();
    }


    @RequestMapping(value = "/name/{name}")
    @ResponseBody
    public String name(@PathVariable("name") String name) {
        return "Hi there: " + name;
    }

}
