package com.example.erp_fetch_data.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ErpController {
    @GetMapping("/getErp")
    public String getErp(@RequestParam("token") String token, Model model){

        model.addAttribute("token", token);
        return "ErpTimesheet";
    }
}
