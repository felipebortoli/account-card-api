package com.altbank.controller;


import com.altbank.service.AccountService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

    @Mock
    private AccountService accountService;

    private AccountController accountController;

    @BeforeEach
    public void setUp() {
        accountController = new AccountController(accountService);
    }

    @Test
    public void testCancelAccount() {
        String accountNumber = "ACC12345";

        doNothing().when(accountService).cancelAccount(accountNumber);

        Response response = accountController.cancelAccount(accountNumber);

        verify(accountService, times(1)).cancelAccount(accountNumber);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Account canceled successfully.", response.getEntity());
    }
}
