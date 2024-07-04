package org.bu.test.butest.service;

import org.bu.test.butest.dto.DailyAssignment;
import org.bu.test.butest.dto.DailyCapacity;
import org.bu.test.butest.dto.RoomAllocation;
import org.springframework.stereotype.Service;

@Service
public class RoomFillmentService {

    public DailyAssignment fillRooms(DailyCapacity dailyCapacity) {
        return new DailyAssignment(new RoomAllocation(0,0), new RoomAllocation(0,0));
    }
}
