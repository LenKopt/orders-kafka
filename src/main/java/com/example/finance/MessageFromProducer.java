package com.example.finance;

import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
public class MessageFromProducer {
    private String techID;
    private String text;

    public MessageFromProducer(String techID, String text) {
        this.techID = techID;
        this.text = text;
    }
}
