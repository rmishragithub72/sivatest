package com.example.bankswitch;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

@Service
public class SwitchService {
    Map<String, CompletableFuture> futureMap = new ConcurrentHashMap<>();
    public String executeBankprocess(String name, String amt) throws URISyntaxException, ExecutionException, InterruptedException, TimeoutException {

        Request request = new Request();
        long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
        request.setRequestId(String.valueOf(number));
        request.setNotifyURL("http://localhost:8080/complete");
        request.setRequestBody("Process Txn for "+ name + " with amount: " + amt);

        // call bank service
        RestTemplate restTemplate = new RestTemplate();
        URI url = new URI("http://localhost:9090/process");
        ResponseEntity<Response> respEntity = restTemplate.postForEntity(url, request, Response.class);
        System.out.println("Got respoonse:" + respEntity + "\n" + respEntity.getBody());
        Response response = respEntity.getBody();
        System.out.println("Received response for id:" + response.getRequest().getRequestId());
        return (awaitForCompletionFromBank(request.getRequestId()));
    }

    private String awaitForCompletionFromBank(String requestId) throws ExecutionException, InterruptedException, TimeoutException {
        CompletableFuture<String> future = new CompletableFuture<>();
        futureMap.put(requestId, future);
        String response = future.get(100, TimeUnit.SECONDS);
        System.out.println("Got response from future :" + response);
        return response;
    }

    public int complete(Request request) {
        System.out.println("Got completion request from bank for id:" + request.getRequestId());
        CompletableFuture future = futureMap.get(request.getRequestId());
        return  future.complete(request.getRequestBody())? 0 : 1;

    }
}
