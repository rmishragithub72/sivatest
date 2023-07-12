package com.example.banksimulator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
@NoArgsConstructor
@Getter @Setter
@ToString
public class Request implements Serializable {
    String requestId;
    String notifyURL;
    String requestBody;
}
