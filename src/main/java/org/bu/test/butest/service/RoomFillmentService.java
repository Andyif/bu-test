package org.bu.test.butest.service;

import org.bu.test.butest.dto.DailyAssignment;
import org.bu.test.butest.dto.DailyCapacity;
import org.bu.test.butest.dto.RoomAllocation;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.PriorityQueue;

@Service
public class RoomFillmentService {

    private static final double PREMIUM_REQUEST_AMOUNT = 100;

    public DailyAssignment fillRooms(DailyCapacity dailyCapacity) {

        final var economyQueue = new PriorityQueue<Double>(Collections.reverseOrder());
        final var premiumQueue = new PriorityQueue<Double>(Collections.reverseOrder());

        this.splitCustomersRequestsToBuckets(CustomerGenerator.getCustomerRequests(),
                economyQueue, premiumQueue);

        final var premiumRoomAllocation  = assignCustomersToPremiumRooms(dailyCapacity, premiumQueue, economyQueue);
        final var economyRoomAllocation  = assignCustomersToEconomyRooms(dailyCapacity, economyQueue);

        return new DailyAssignment(economyRoomAllocation, premiumRoomAllocation);
    }

    private void splitCustomersRequestsToBuckets(double[] customerRequests, PriorityQueue<Double> economyQueue,
                                                 PriorityQueue<Double> premiumQueue) {
        for (double customerRequest : customerRequests) {
            if (customerRequest < PREMIUM_REQUEST_AMOUNT) {
                economyQueue.add(customerRequest);
            } else {
                premiumQueue.add(customerRequest);
            }
        }
    }

    private RoomAllocation assignCustomersToPremiumRooms(DailyCapacity dailyCapacity,
                                                         PriorityQueue<Double> premiumQueue,
                                                         PriorityQueue<Double> economyQueue) {
        var premiumRoomAllocation = new RoomAllocation(0,0);

        for (int i = 0; i < dailyCapacity.premiumRoomQuantity(); i++) {
            final Double premiumCustomerRequest = premiumQueue.poll();
            if (null != premiumCustomerRequest) {
                premiumRoomAllocation = premiumRoomAllocation.addCustomer(premiumCustomerRequest);
            } else if (dailyCapacity.economyRoomQuantity() < economyQueue.size()) {
                final Double economyCustomerRequest = economyQueue.poll();
                if (null != economyCustomerRequest) {
                    premiumRoomAllocation = premiumRoomAllocation.addCustomer(economyCustomerRequest);
                }
            } else {
                break;
            }
        }

        return premiumRoomAllocation;
    }

    private RoomAllocation assignCustomersToEconomyRooms(DailyCapacity dailyCapacity,
                                                         PriorityQueue<Double> economyQueue) {
        var economyRoomAllocation = new RoomAllocation(0,0);

        for (int i = 0; i < dailyCapacity.economyRoomQuantity(); i++) {
            final Double economyCustomerRequest = economyQueue.poll();
            if (null != economyCustomerRequest) {
                economyRoomAllocation = economyRoomAllocation.addCustomer(economyCustomerRequest);
            } else {
                break;
            }
        }

        return economyRoomAllocation;
    }
}
