package com.ing.broker.app.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoneyRequest {

    private long 			customerId;
    private double 			amount;

}
