package com.example.HATEOASTest;

import com.example.HATEOASTest.userbank.Client;
import com.example.HATEOASTest.userbank.ClientRepository;
import com.example.HATEOASTest.userbank.ClientRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClientIntegrationTest {
    @Autowired
    private ClientRepository clientRepository;
    @LocalServerPort
    private int port;
    private WebClient webClient;

    @BeforeEach
    void setUp() {
        // Configure WebClient with the random port
        webClient = WebClient.builder()
                .baseUrl("http://localhost:" + port)
                .build();

        // Clear the database before each test to ensure isolation
        clientRepository.deleteAll();
    }


    @Test
    void shouldCreateUserAndPersistToDatabase() {
        // Arrange
        ClientRequest cr = new ClientRequest("Winston Churchill","winston@gmail.com");

        // Act
        Client createdUser = webClient.post()
                .uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(cr)
                .retrieve()
                .bodyToMono(Client.class)
                .block(); // Block for simplicity in test

        // Assert
        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getId()).isNotNull();
        assertThat(createdUser.getUsername()).isEqualTo("Winston Churchill");
        assertThat(createdUser.getEmail()).isEqualTo("winston@gmail.com");

        // Verify database persistence
        Client persistedUser = clientRepository.findById(createdUser.getId()).orElse(null);
        assertThat(persistedUser).isNotNull();
        assertThat(persistedUser.getUsername()).isEqualTo("Winston Churchill");
        assertThat(persistedUser.getEmail()).isEqualTo("winston@gmail.com");
    }

    @Test
    void shouldReturnNotFoundEntityWhenUserDoesNotExist() {
        // Act
//        WebClient.ResponseSpec resultResponse = webClient.get()
//                .uri("/api/users/999")
//                .retrieve();

//        HttpStatusCode resultStatus = resultResponse
//                // Handle 404 to avoid WebClientResponseException
//                .onStatus(
//                        status -> status.equals(HttpStatus.NOT_FOUND),
//                        response -> Mono.empty()
//                )
//                .toBodilessEntity()
//                .map(entity -> entity.getStatusCode())
//                .block();

        // shorter and simpler alternative
        HttpStatusCode resultStatus = webClient.get()
                .uri("/api/users/999")
                .exchangeToMono(response -> Mono.just(response.statusCode()))
                .block();
        assertThat(resultStatus).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
