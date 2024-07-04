package org.bu.test.butest.service;

import java.util.Arrays;

public class CustomerGenerator {

    private final static double[] customerRequests =
            new double[] {23, 45, 155, 374, 22, 99.99, 100, 101, 115, 209};

    private CustomerGenerator() {
    }

    static double[] getCustomerRequests() {
        return customerRequests;
    }
}
