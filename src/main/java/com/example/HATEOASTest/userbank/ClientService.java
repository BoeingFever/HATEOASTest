package com.example.HATEOASTest.userbank;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final ObjectMapper clientMapper;
    public ClientService(ClientRepository clientRepository,ObjectMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    public Client createUser(ClientRequest clientRequest) {
        if (clientRequest.username() == null || clientRequest.email() == null) {
            throw new IllegalArgumentException("Username and email cannot be null");
        }
        Client client = clientMapper.convertValue(clientRequest, Client.class);
        return clientRepository.save(client);
    }

    public Optional<Client> findUserById(Long id) {
        Optional<Client> myClient = clientRepository.findById(id);
//        if(myClient.isEmpty())
//            throw new ClientNotFoundException(id);
        return myClient;
    }

    public Optional<Client> findUserByUsername(String username) {
        return clientRepository.findByUsername(username);
    }
}