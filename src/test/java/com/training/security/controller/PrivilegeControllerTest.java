package com.training.security.controller;

import com.training.security.entity.PrivilegeType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PrivilegeControllerTest {


    @Autowired
    private MockMvc mvc;

    /*
        GET : /read
        - With : READ_PRIVILEGE -> expect : status OK
     */
    @Test
    @WithMockUser(authorities = PrivilegeType.READ)
    void AsReadWhenGetReadThenExpectStatus200() throws Exception {
        mvc.perform(get("/read"))
                .andExpect(status().isOk());
    }

    /*
        GET : /read
        - With : WRITE_PRIVILEGE -> expect : status 403
     */
    @Test
    @WithMockUser(roles = PrivilegeType.WRITE)
    void AsWriteWhenGetReadThenExpectStatu403() throws Exception {
        mvc.perform(get("/read"))
                .andExpect(status().isForbidden());
    }

    /*
        GET : /read
        - With : DELETE_PRIVILEGE -> expect : status 403
     */
    @Test
    @WithMockUser(authorities = PrivilegeType.DELETE)
    void AsDeleteWhenGetReadThenExpectStatus403() throws Exception {
        mvc.perform(get("/read"))
                .andExpect(status().isForbidden());
    }

}