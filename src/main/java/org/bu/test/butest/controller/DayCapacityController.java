package org.bu.test.butest.controller;

import jakarta.validation.constraints.PositiveOrZero;
import org.bu.test.butest.dto.DailyAssignment;
import org.bu.test.butest.dto.DailyCapacity;
import org.bu.test.butest.service.RoomFillmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DayCapacityController {
    public final RoomFillmentService roomFillmentService;

    public DayCapacityController(RoomFillmentService roomFillmentService) {
        this.roomFillmentService = roomFillmentService;
    }

    @GetMapping("/day-capacity")
    public ResponseEntity<DailyAssignment> setDailyCapacity(
            @RequestParam(name = "economy") @PositiveOrZero int economyRoomQuantity,
            @RequestParam(name = "premium") @PositiveOrZero int premiumRoomQuantity) {
        final DailyCapacity dailyCapacity = new DailyCapacity(economyRoomQuantity, premiumRoomQuantity);

        final DailyAssignment dailyAssignment = this.roomFillmentService.fillRooms(dailyCapacity);

        return ResponseEntity.ok(dailyAssignment);
    }
}
