package com.example.HATEOASTest;

import com.example.HATEOASTest.userbank.Client;
import com.example.HATEOASTest.userbank.ClientNotFoundException;
import com.example.HATEOASTest.userbank.ClientRepository;
import com.example.HATEOASTest.userbank.ClientRequest;
import com.example.HATEOASTest.userbank.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceUnitTest {
    @Mock
    ClientRepository clientRepository;
    @InjectMocks
    ClientService clientService;

    Client client;
    @BeforeEach
    void setUpBeforeEachCase(){
        client = new Client(1L,"Winston Churchill","winston@gmail.com");
    }
    @Test
    void shouldCreateClientRecord_WhenValidInfoIsProvided(){
        //Given
        when(clientRepository.save(any(Client.class))).thenReturn(client);
        ClientRequest cr = new ClientRequest("Winston Churchill","winston@gmail.com");
        //When
        Client savedClient = clientService.createUser(cr);
        //Then
        assertThat(savedClient.getEmail()).isEqualTo("winston@gmail.com");
        assertThat(savedClient.getUsername()).isEqualTo("Winston Churchill");
        verify(clientRepository, times(1)).save(any(Client.class));
    }
    @Test
    void shouldThrowIllegalArgumentException_WhenUserNameNull(){
        //Given
        ClientRequest cr = new ClientRequest(null,"ramdom@gmail.com");
        //When
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,()->clientService.createUser(cr));
        //Then
        assertThat(ex.getMessage()).isEqualTo("Username and email cannot be null");
        verify(clientRepository,never()).save(any(Client.class));
    }

//    @ParameterizedTest
//    @ValueSource(strings = {""," "})
    void shouldThrowIllegalArgumentException_WhenEmailEmpty(String invalidEmail){
        //This test method is mean to fail to show that my business logic miss to handle some cases
        //Given
        ClientRequest cr = new ClientRequest("Tom",invalidEmail);
        //When
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,()->clientService.createUser(cr));
        //Then
        assertThat(ex.getMessage()).isEqualTo("Username and email cannot be null");
        verify(clientRepository,never()).save(any(Client.class));
    }

    @Test
    void shouldReturnUser_WhenFoundById() {
        // Given
        Long Id = 1L;
        when(clientRepository.findById(Id)).thenReturn(Optional.of(client));
        // When
        Optional<Client> foundClient = clientService.findUserById(Id);
        // Then
        assertThat(foundClient).isPresent();
        assertThat(foundClient.get().getUsername()).isEqualTo("Winston Churchill");
        verify(clientRepository, times(1)).findById(Id);
    }

//    @Test
//    void shouldThrowClientNotFoundException_whenNotFoundById(){
//        //Given
//        Long Id = 99L;
//        when(clientRepository.findById(Id)).thenReturn(Optional.empty());
//        //When
//        ClientNotFoundException ex = assertThrows(ClientNotFoundException.class,()->clientService.findUserById(Id));
//        //Then
//        assertThat(ex.getMessage()).isEqualTo("Could not find client " + Id);
//        verify(clientRepository, times(1)).findById(Id);
//    }

    @Test
    void shouldReturnEmptyOptional_WhenUserNotFound(){
        //Given
        Long Id = 99L;
        when(clientRepository.findById(Id)).thenReturn(Optional.empty());
        //When
        Optional<Client> result = clientService.findUserById(Id);
        //Then
        assertThat(result).isEmpty();
        verify(clientRepository, times(1)).findById(Id);
    }
}
