package wotc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import wotc.data.CardRepository;
import wotc.data.CollectionRepository;
import wotc.models.CollectedCard;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CollectionControllerTest {

    @MockitoBean
    CollectionRepository repository;

    @Autowired
    MockMvc mvc;

    @Test
    void addShouldReturn400WhenEmpty() throws Exception {
        var request = post("/api/collection/cards")
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void addShouldReturn400WhenInvalid() throws Exception {

        ObjectMapper jsonMapper = new ObjectMapper();

        CollectedCard card = new CollectedCard();
        String cardJson = jsonMapper.writeValueAsString(card);

        var request = post("/api/collection/cards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cardJson);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }
}
