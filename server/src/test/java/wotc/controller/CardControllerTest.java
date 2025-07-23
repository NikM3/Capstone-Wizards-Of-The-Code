<<<<<<< HEAD
//package wotc.controller;
=======
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
>>>>>>> 43afac437e4777f69977ba766bc9875aeb080cc1
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//import wotc.data.CardRepository;
//import wotc.models.*;
//
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import java.util.List;
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class CardControllerTest {
//
//    @MockitoBean
//    CardRepository repository;
//
<<<<<<< HEAD
//    @Autowired
//    MockMvc mvc;
//
//    @Test
//    void addShouldReturn400WhenEmpty() throws Exception {
////
////        var request = post("/api/card")
////                .contentType(MediaType.APPLICATION_JSON);
////
////        mvc.perform(request)
////                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void addShouldReturn400WhenInvalid() throws Exception {
//
////        ObjectMapper jsonMapper = new ObjectMapper();
////
////        Card card = new Card();
////        String cardJson = jsonMapper.writeValueAsString(card);
////
////        var request = post("/api/card")
////                .contentType(MediaType.APPLICATION_JSON)
////                .content(cardJson);
////
////        mvc.perform(request)
////                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void addShouldReturn201() throws Exception {
//
////        Card card = new Card(0, "Black Lotus", "", CardType.ARTIFACT, List.of(CardColor.COLORLESS), CardRarity.COMMON, "Limited Edition Alpha", "test uri");
////        Card expected = new Card(1, "Black Lotus", "", CardType.ARTIFACT, List.of(CardColor.COLORLESS), CardRarity.COMMON, "Limited Edition Alpha", "test uri");
////
//
//    }
//
//    @Test
//    void experimentingWithScryfall() throws Exception {
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create("https://api.scryfall.com/bulk-data"))
//                .build();
//
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//        ObjectMapper mapper = new ObjectMapper();
//        ScryfallBulkResponse scryfallBulkResponse = mapper.readValue(response.body(), ScryfallBulkResponse.class);
//
//        Optional<ScryfallBulkData> defaultCards = scryfallBulkResponse.getData().stream().filter(data -> data.getType().equals("default_cards")).findFirst();
//
//        if (defaultCards.isPresent()) {
//            String cardDataUri = defaultCards.get().getDownload_uri();
//            System.out.println(cardDataUri);
//
//            // Wait until the download is finished
//
//            populateLocalDatabase();
//        }
//    }
//
//    private void populateLocalDatabase() {
//        private final String sql = "insert or replace into card (card_id, card_type_id, rarity_id, card_name, mana_cost, color_identity, set, image_uri) " +
//                "values (?, ?, ?, ?, ?, ?, ?, ?)";
//    }
//}
=======

    }
}
>>>>>>> 43afac437e4777f69977ba766bc9875aeb080cc1
