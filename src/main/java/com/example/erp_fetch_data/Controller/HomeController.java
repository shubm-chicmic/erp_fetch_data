package com.example.erp_fetch_data.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ArrayNode;
import net.minidev.json.JSONObject;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Text;

import java.util.ArrayList;

@RestController
public class HomeController {
    @RequestMapping("/")
    public String health(){
        return "running";
    }
    @GetMapping("/dataFetch/{empCode}")
    public String empDataFetch(@PathVariable("empCode") String empCode) throws JsonProcessingException {

        String url = "https://apigateway.erp.chicmic.in/v1/auth/login";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject obj = new JSONObject();
        obj.put("loginId", "CHM/2023/" + empCode);
        obj.put("password", "123456");

        HttpEntity<String> request =
                new HttpEntity<String>(obj.toString(), headers);
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseEntity<String> responseEntityStr = restTemplate.postForEntity(url, request, String.class);
        JsonNode root = objectMapper.readTree(responseEntityStr.getBody());
        String token = root.get("accessToken").textValue();
        System.out.println("\u001B[44m" + responseEntityStr.getBody() + "\u001B[0m");
        System.out.println("\u001B[43m" + token + "\u001B[0m");

        Workbook workBook = new XSSFWorkbook();
        Sheet sheet = workBook.createSheet("My Sheet");
        sheet.setColumnWidth(0, 2560);
        sheet.setColumnWidth(1, 2560);
        
        Row row = sheet.createRow(0);
       // row.createCell(0).setCellValue("Hello World");
        String[] day = { "Saturday","Sunday","Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        for (int i = 02; i <= 02; i++){
            for(int j = 29; j <= 29; j++) {
                url = "https://apigateway.erp.chicmic.in/v1/timesheet/time?entryDate=2023-" + i + "-" + j;
                headers.clear();
                headers.set("Authorization", token);
                HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

                responseEntityStr = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
                System.out.println("\u001B[43m" + responseEntityStr.getHeaders() + "\u001B[0m");

                root = objectMapper.readTree(responseEntityStr.getBody());
                System.out.println("\u001B[43m" + root.get("data") + "\u001B[0m");
                System.out.println("\u001B[43m" + root.get("data").get(0).get("notes") + "\u001B[0m");
                String[] arr = new String[6];
                if(root.get("data").size() != 0){
                    arr[0]= j + "-" + i + "-2023";
                    arr[1] = day[(j % 7) - 0];
                    arr[2]="MISCELLANEOUS";
                    arr[3]="Training";
                    arr[4]= String.valueOf(root.get("data").get(0).get("timeSpent"));
                    arr[5]= String.valueOf(root.get("data").get(0).get("notes"));
                }else {
                    arr[0]= j + "-" + i + "-2023";
                    arr[1] = day[(j % 7) - 0];
                    arr[2]="";
                    arr[3]="";
                    arr[4]="";
                    arr[5]="";
                }



            }
        }

        return "";
    }
}
/*

(async()=>{
var excelData =[
["Date","Day","Project","Task","Time Spend","Note/ Description"]
];
var day = [ "Saturday","Sunday","Monday", "Tuesday", "Wednesday", "Thursday", "Friday"];
var month = [31,28,31,30,31];
var monthname = ["JANUARY","FEBRUARY","MARCH","APRIL","MAY",]
for (let i = 01; i <= 05; i++){

let data2222 = [monthname[i - 1]];
excelData.push(data2222)

for(let j = 01; j <= month[i-1]; j++) {
let arr = [];
var data = await fetch("https://apigateway.erp.chicmic.in/v1/timesheet/time?entryDate=2023-0"+i+"-" + j,
{
headers:{
"Authorization":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2M2NlNThhMDkzZTkxMzA2N2QwMjc2YjMiLCJlbWFpbCI6ImttYWhleTI4QGdtYWlsLmNvbSIsInRpbWUiOjE2ODQ4MzU2MTY4NTgsImlhdCI6MTY4NDgzNTYxNn0.OEcA3Oh9A02ePd_pA6gND7CLm8l0Rslh3MqtkjZn-ic"
}
})



data = await data.json();
if(data.data.length != 0){
arr[0]= j + "-" + i + "-2023";
arr[1] = day[(j % 7) - 0];
arr[2]="MISCELLANEOUS";
arr[3]="Training";
arr[4]=data.data[0].timeSpent;
arr[5]=data.data[0].notes;
}else {
arr[0]= j + "-" + i + "-2023";
arr[1] = day[(j % 7) - 0];
arr[2]="";
arr[3]="";
arr[4]="";
arr[5]="";
}

<!--console.log()-->
<!--if(data.data.length != 0)-->
<!--console.log(data.data[0].notes);-->
<!--console.log("**************************");}-->
<!--console.log("i??????????????????????????????????=" + i)-->


<!--console.log(arr);-->

excelData.push(arr);
}
}

 var workbook = XLSX.utils.book_new(),
            worksheet = XLSX.utils.aoa_to_sheet(excelData);
<!--worksheet.rows(0).cells(0).value("Center");-->

        workbook.SheetNames.push("First");
        workbook.Sheets["First"] = worksheet;
<!--        var worksheet = workbook.Sheets["First"];-->
<!--        worksheet.columns(0).width(6000);-->
<!--*********************************************-->
<!--        XSSFCellStyle style = workbook.createCellStyle();-->
<!--style.setBorderTop((short) 6); // double lines border-->
<!--style.setBorderBottom((short) 1); // single line border-->
<!--XSSFFont font = workbook.createFont();-->
<!--font.setFontHeightInPoints((short) 15);-->
<!--font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);-->
<!--style.setFont(font);-->

<!--Row row = sheet.createRow(0);-->
<!--Cell cell0 = row.createCell(0);-->
<!--cell0.setCellValue("Nav Value");-->
<!--cell0.setCellStyle(style);-->
<!--*********************************************-->


        XLSX.writeFile(workbook, "Diary_Chicmic.xlsx");

})()
*/
