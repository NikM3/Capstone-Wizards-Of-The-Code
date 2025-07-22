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
import wotc.models.Card;
import wotc.models.CardColor;
import wotc.models.CardRarity;
import wotc.models.CardType;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CardControllerTest {

    @MockitoBean
    CardRepository repository;

    @Autowired
    MockMvc mvc;

    @Test
    void addShouldReturn400WhenEmpty() throws Exception {
//
//        var request = post("/api/card")
//                .contentType(MediaType.APPLICATION_JSON);
//
//        mvc.perform(request)
//                .andExpect(status().isBadRequest());
    }

    @Test
    void addShouldReturn400WhenInvalid() throws Exception {

//        ObjectMapper jsonMapper = new ObjectMapper();
//
//        Card card = new Card();
//        String cardJson = jsonMapper.writeValueAsString(card);
//
//        var request = post("/api/card")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(cardJson);
//
//        mvc.perform(request)
//                .andExpect(status().isBadRequest());
    }

    @Test
    void addShouldReturn201() throws Exception {

//        Card card = new Card(0, "Black Lotus", "", CardType.ARTIFACT, List.of(CardColor.COLORLESS), CardRarity.COMMON, "Limited Edition Alpha", "test uri");
//        Card expected = new Card(1, "Black Lotus", "", CardType.ARTIFACT, List.of(CardColor.COLORLESS), CardRarity.COMMON, "Limited Edition Alpha", "test uri");
//

    }
}
