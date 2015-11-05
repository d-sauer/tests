package com.rxtest.controller;

import com.rxtest.ListService;
import com.rxtest.ListService.Data;
import com.rxtest.StreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by davor on 22/10/15.
 */
@RestController
public class MainController {

    @Autowired
    private ListService listService;

    @Autowired
    private StreamService streamService;

    @RequestMapping(value = {"", "/", "index"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public String index() {
        return "Current time: " + LocalDateTime.now().toString();
    }


    /**
     * curl -i -X POST -d 'data=test1' http://localhost:8080/list
     * @param data
     * @return
     */
    @RequestMapping(value = {"list"}, method = {RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Data addToList(@RequestParam String data) {
        return listService.addData(data);
    }

    @RequestMapping(value = {"list"}, method = {RequestMethod.GET}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<Data> getToList() {
        streamService.processData().subscribe(System.out::println);


        return listService.getData();
    }

}
