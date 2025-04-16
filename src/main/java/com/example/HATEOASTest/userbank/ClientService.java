package com.example.HATEOASTest.userbank;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client createUser(ClientRequest clientRequest) {
        if (clientRequest.getUsername() == null || clientRequest.getEmail() == null) {
            throw new IllegalArgumentException("Username and email cannot be null");
        }
        ObjectMapper mapper = new ObjectMapper();
        Client client = mapper.convertValue(clientRequest, Client.class);
        return clientRepository.save(client);
    }

    public Optional<Client> findUserById(Long id) {
        Optional<Client> myClient = clientRepository.findById(id);
        if(myClient.isEmpty())
            throw new ClientNotFoundException(id);
        return myClient;
    }

    public Optional<Client> findUserByUsername(String username) {
        return clientRepository.findByUsername(username);
    }
}