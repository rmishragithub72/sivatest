package com.example.banksimulator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankController {
    @Autowired
     BankService bankService;
    @PostMapping("/process")
    public Response doPayment(@RequestBody Request request) throws InterruptedException {
        System.out.println("Received Request for id:" + request.getRequestId());
        int respCode = bankService.doProcess(request);
        return respCode==0? new Response(0,"success",request):new Response(respCode,"failure",request);
    }

    @GetMapping("/")
    public String home(){
        return "Welcome to Bank Service!!!";
    }
}
