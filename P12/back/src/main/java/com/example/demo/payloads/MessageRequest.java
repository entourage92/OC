package com.example.demo.payloads;

import lombok.Data;

@Data
public class MessageRequest {

    private String message;

    private String receiver;

    private String sender;



}
