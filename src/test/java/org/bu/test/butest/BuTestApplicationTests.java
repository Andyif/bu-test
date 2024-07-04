package org.bu.test.butest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BuTestApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Test
    void dayCapacityTest() throws Exception {

        mvc.perform(
                get("/day-capacity")
                        .queryParam("economy", "0")
                        .queryParam("premium", "0")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.economy.occupied", is(0)))
                .andExpect(jsonPath("$.economy.revenue", is(0.0)))
                .andExpect(jsonPath("$.premium.occupied", is(0)))
                .andExpect(jsonPath("$.premium.revenue", is(0.0)))
        ;

    }

}
