package com.example.erp_fetch_data.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ErpController {
    @GetMapping("/getErp")
    public String getErp(@RequestParam("token") String token, HttpServletRequest request, Model model){
        String error = request.getParameter("error");
        model.addAttribute("token", token);
        model.addAttribute("error", error);
        return "ErpTimesheet";
    }
}
