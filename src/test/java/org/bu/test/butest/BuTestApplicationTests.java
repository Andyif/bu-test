package org.bu.test.butest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.stream.Stream;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BuTestApplicationTests {

    @Autowired
    private MockMvc mvc;

    private static Stream<Arguments> roomAllocationProvider() {
        return Stream.of(
                Arguments.of("0", "0", 0, 0, 0, 0),
                Arguments.of("3", "3", 3, 167.99, 3, 738),
                Arguments.of("5", "7", 4, 189.99, 6, 1054),
                Arguments.of("7", "2", 4, 189.99, 2, 583),
                Arguments.of("1", "7", 1, 45, 7, 1153.99)
        );
    }

    @ParameterizedTest
    @MethodSource("roomAllocationProvider")
    void dayCapacityTest(String economyQuantity, String premiumQuantity,
                         int economyAllocated, double economyRevenue,
                         int premiumAllocated, double premiumRevenue) throws Exception {

        final MockHttpServletRequestBuilder requestBuilder = get("/day-capacity")
                .queryParam("economy", economyQuantity)
                .queryParam("premium", premiumQuantity)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.economy.occupied", is(economyAllocated)))
                .andExpect(jsonPath("$.economy.revenue", is(economyRevenue)))
                .andExpect(jsonPath("$.premium.occupied", is(premiumAllocated)))
                .andExpect(jsonPath("$.premium.revenue", is(premiumRevenue)));
    }

    @Test
    void dayCapacityParamValidationTest() throws Exception {

        final MockHttpServletRequestBuilder requestBuilder = get("/day-capacity")
                .queryParam("economy", "0")
                .queryParam("premium", "-1")
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMsg", is("premium parameter cant be negative")));
    }
}
