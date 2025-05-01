package com.example.HATEOASTest;

import com.example.HATEOASTest.userbank.Client;
import com.example.HATEOASTest.userbank.ClientRepository;
import com.example.HATEOASTest.userbank.ClientRequest;
import com.example.HATEOASTest.userbank.HealthStatus;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.reactive.function.client.WebClient;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.PostgreSQLContainer;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//Starts the application on a random port, ensuring no conflicts.
public class ClientServiceIT {
    @Autowired
    private ClientRepository clientRepository;

    //Spring injects the random port assigned to the embedded server
    @LocalServerPort
    private int port;
    private WebClient webClient;
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientServiceIT.class);

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass")
            .withLogConsumer(new Slf4jLogConsumer(LOGGER));
    @DynamicPropertySource
    static void configureDataSourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
    static {
        postgres.start();
    }
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
        ClientRequest cr = new ClientRequest("Winston Churchill","winston@gmail.com", HealthStatus.GOT_SICK);

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
        assertThat(createdUser.getStatus()).isEqualTo(HealthStatus.GOT_SICK);

        // Verify database persistence
        Client persistedUser = clientRepository.findById(createdUser.getId()).orElse(null);
        assertThat(persistedUser).isNotNull();
        assertThat(persistedUser.getUsername()).isEqualTo("Winston Churchill");
        assertThat(persistedUser.getEmail()).isEqualTo("winston@gmail.com");
        assertThat(persistedUser.getStatus()).isEqualTo(HealthStatus.GOT_SICK);
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

        // Below is shorter and simpler alternative
        HttpStatusCode resultStatus = webClient.get()
                .uri("/api/users/999")
                .exchangeToMono(response -> Mono.just(response.statusCode()))
                .block();
        assertThat(resultStatus).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
