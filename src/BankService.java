package com.example.banksimulator;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class BankService {
    Queue<Request> serviceQueue = new ConcurrentLinkedQueue<>();
    public int doProcess(Request request) throws InterruptedException {
        serviceQueue.add(request);
        System.out.println("Processing :" + request.getRequestId() + " with body: " + request.requestBody);
        //processing for some time
        Thread.sleep(1000);
        return 0;
    }

    @Scheduled(fixedDelay = 30000)
    public void notifyClient(){
        if (serviceQueue.isEmpty()) return;
        Request request = serviceQueue.poll();
        //call the client to notify the pprocess is complete
        System.out.println("Going to call client with url :" + request.getNotifyURL() + " for requestId: " + request.getRequestId());
        RestTemplate template = new RestTemplate();
        request.setRequestBody("Completion from Bank for id:" + request.getRequestId());
        ResponseEntity<Response> responseEntity = template.postForEntity(request.notifyURL, request, Response.class);
        if(!responseEntity.getStatusCode().is2xxSuccessful()){
            System.out.println("Unable to update complete at swich side for id:" + request.getRequestId());
        }
    }

}
