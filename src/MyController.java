package com.example.bankswitch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestController
public class MyController {
    @Autowired
    SwitchService switchService;

    @GetMapping("/test")
    public String test(@RequestParam String txtName, @RequestParam String txtAmt) throws InterruptedException, URISyntaxException, ExecutionException, TimeoutException {
        System.out.println("Welcome home :" + txtName + "Amount :" + txtAmt);
        //Thread.sleep(3000);
        String response = switchService.executeBankprocess(txtName, txtAmt);
        return response; //"Welcome Home " + txtName;
    }

    @PostMapping("/complete")
    public Response doPayment(@RequestBody Request request) throws InterruptedException {
        System.out.println("Received Request for id:" + request.getRequestId());
        int respCode = switchService.complete(request);
        return respCode==0? new Response(0,"success",request):new Response(respCode,"failure",request);
    }
}
