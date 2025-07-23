package wotc.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import wotc.data.CardJdbcTemplateRepository;
import wotc.data.CardRepository;
import wotc.models.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CardControllerTest {

    @MockitoBean
    CardJdbcTemplateRepository cardJdbcTemplateRepository;

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
