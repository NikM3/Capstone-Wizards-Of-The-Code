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
import wotc.data.CollectedCardRepository;
import wotc.data.CollectionRepository;
import wotc.models.CollectedCard;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GlobalExceptionHandlerTest {
    @MockitoBean
    CollectedCardRepository repository;

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldHandleExceptions() throws Exception {
        when(repository.addCollectedCard(any())).thenThrow(RuntimeException.class);

        // Make a collected card
        CollectedCard collectedCard = new CollectedCard();
        collectedCard.setCollectedCardId(1);
        collectedCard.setCardId(1);
        collectedCard.setCollectionId(1);
        collectedCard.setCondition("Test");
        collectedCard.setQuantity(1);
        collectedCard.setInUse(true);

        String ccJson = objectMapper.writeValueAsString(collectedCard);

        ErrorResponse errorResponse = new ErrorResponse("Something unexpected happened! Unhandled error.");

        String expectedJson = objectMapper.writeValueAsString(errorResponse);

        var request = post("/api/collection/cards/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ccJson);

        mvc.perform(request)
                .andExpect(status().isInternalServerError())
                .andExpect(content().json(expectedJson));
    }
}
