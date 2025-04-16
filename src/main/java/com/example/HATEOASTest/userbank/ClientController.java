package com.example.HATEOASTest.userbank;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<Client> createUser(@RequestBody ClientRequest clientRequest) {
        Client client = clientService.createUser(clientRequest);
        return ResponseEntity.ok(client);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getUserById(@PathVariable Long id) {
        return clientService.findUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
