package com.parkinglot.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ParkingLotController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
