package pl.pinakoteka.customersservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/appointments")
    public String showappointments(){
        return "appointments";
    }
}
