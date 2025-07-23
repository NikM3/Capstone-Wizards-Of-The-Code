package wotc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import wotc.data.CardRepository;
import wotc.data.CollectionRepository;
import wotc.data.UserRepository;
import wotc.models.CollectedCard;
import wotc.models.Role;
import wotc.models.User;
import wotc.models.UserRole;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CollectionControllerTest {

    @MockitoBean
    CollectionRepository collectionRepository;

    @MockitoBean
    UserRepository userRepository;

    @Autowired
    MockMvc mvc;

    String token;

    @BeforeEach
    void setup() {
        User user = new User(1, "Test", "Test@mail.com", "Pass1234", false, Role.ADMIN);

        when(userRepository.findByUsername("Test")).thenReturn(user);

        // TODO: Mock a login?
        // token = service.authenticate(user);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
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
