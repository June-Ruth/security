package com.training.security.controller;

import com.training.security.authorizationserver.entity.RoleType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private MockMvc mvc;

    /*
        GET : /login
        - With : Unauthenticated / anonymous user -> expect : status OK
     */
    @Test
    void AsUnauthenticatedWhenGetLoginThenExpectStatusOk() throws Exception {
        mvc.perform(get("/login"))
                .andExpect(status().isOk());
    }

    /*
        FormLogin : /login
        - With : user = "admin", password = "test" -> expect : authenticated + redirection /default
     */
    @Test
    void AsUnauthenticatedWhenLogWithAdminLogThenExpectAuthenticatedAndRedirectDefault() throws Exception {
        mvc.perform(formLogin("/login").user("admin").password("test"))
                .andExpect(authenticated())
                .andExpect(redirectedUrl("/default"));
    }

    /*
        FormLogin : /login
        - With : user = "editor", password = "test" -> expect : authenticated + redirection /default     */
    @Test
    void AsUnauthenticatedWhenLogWithEditorLogThenExpectAuthenticatedAndRedirectDefault() throws Exception {
        mvc.perform(formLogin("/login").user("editor").password("test"))
                .andExpect(authenticated())
                .andExpect(redirectedUrl("/default"));    }

    /*
        FormLogin : /login
        - With : user = "user", password = "test" -> expect : authenticated + redirection /default
     */
    @Test
    void AsUnauthenticatedWhenLogWithUserLogThenExpectAuthenticatedAndRedirectDefault() throws Exception {
        mvc.perform(formLogin("/login").user("user").password("test"))
                .andExpect(authenticated())
                .andExpect(redirectedUrl("/default"));
    }

    /*
        FormLogin : /login
        - With : user = "wrong", password = "wrong" -> expect : unauthenticated
     */
    @Test
    void AsUnauthenticatedWhenLogWithWrongLogThenExpectUnauthenticated() throws Exception {
        mvc.perform(formLogin("/login").user("wrong").password("wrong"))
                .andExpect(unauthenticated());
    }

    /*
        GET : /home
        - With : Unauthenticated / anonymous user -> expect : status OK
     */
    @Test
    @WithAnonymousUser
    void AsUnauthenticatedWhenGetHomeThenExpectStatus200() throws Exception {
        mvc.perform(get("/home"))
                .andExpect(status().isOk());
    }

    /*
        GET : /home
        - With : Admin -> expect : status OK
     */
    @Test
    @WithMockUser(roles = RoleType.ADMIN)
    void AsAdminWhenGetHomeThenExpectStatus200() throws Exception {
        mvc.perform(get("/home"))
                .andExpect(status().isOk());
    }

    /*
        GET : /home
        - With : Editor -> expect : status OK
     */
    @Test
    @WithMockUser(roles = RoleType.EDITOR)
    void AsEditorWhenGetHomeThenExpectStatus200() throws Exception {
        mvc.perform(get("/home"))
                .andExpect(status().isOk());
    }


    /*
        GET : /home
        - With : User -> expect : status OK
     */
    @Test
    @WithMockUser
    void AsUserWhenGetHomeThenExpectStatus200() throws Exception {
        mvc.perform(get("/home"))
                .andExpect(status().isOk());
    }

    /*
        GET : /admin
        - With : Unauthenticated / anonymous user -> expect : redirection /login
     */
    @Test
    void AsUnauthenticatedWhenGetAdminThenExpectRedirectionLogin() throws Exception {
        mvc.perform(get("/admin"))
                .andExpect(status().is3xxRedirection());
    }

    /*
        GET : /admin
        - With : Role Admin -> expect : status 200
     */
    @Test
    @WithMockUser(roles = RoleType.ADMIN)
    void AsAdminWhenGetAdminThenExpectStatus200() throws Exception {
        mvc.perform(get("/admin"))
                .andExpect(status().isOk());
    }

    /*
        GET : /admin
        - With : Role Editor -> expect : status 403
     */
    @Test
    @WithMockUser(roles = RoleType.EDITOR)
    void AsEditorWhenGetAdminThenExpectStatus403() throws Exception {
        mvc.perform(get("/admin"))
                .andExpect(status().isForbidden());
        }

    /*
        GET : /admin
        - With : Role User -> expect : status 403
     */
    @Test
    @WithMockUser
    void AsUserWhenGetAdminThenExpectStatus403() throws Exception {
        mvc.perform(get("/admin"))
                .andExpect(status().isForbidden());
    }


    /*
        GET : /editor
        - With : Role Admin -> expect : status 200
     */
    @Test
    @WithMockUser(roles = RoleType.ADMIN)
    void AsAdminWhenGetEditorThenExpectStatus200() throws Exception {
        mvc.perform(get("/editor"))
                .andExpect(status().isOk());
    }

    /*
        GET : /editor
        - With : Role Editor -> expect : status 200
     */
    @Test
    @WithMockUser(roles = RoleType.EDITOR)
    void AsEditorWhenGetEditorThenExpectStatus200() throws Exception {
        mvc.perform(get("/editor"))
                .andExpect(status().isOk());
    }

    /*
        GET : /editor
        - With : Role User -> expect : status 403
     */
    @Test
    @WithMockUser
    void AsUserWhenGetEditorThenExpectStatus403() throws Exception {
        mvc.perform(get("/editor"))
                .andExpect(status().isForbidden());
    }

    /*
        GET : /user
        - With : Role Admin -> expect : status 200
     */
    @Test
    @WithMockUser(roles = RoleType.ADMIN)
    void AsAdminWhenGetUserThenExpectStatus200() throws Exception {
        mvc.perform(get("/user"))
                .andExpect(status().isOk());
    }

    /*
        GET : /user
        - With : Role Editor -> expect : status 200
     */
    @Test
    @WithMockUser(roles = RoleType.EDITOR)
    void AsEditorWhenGetUserThenExpectStatus200() throws Exception {
        mvc.perform(get("/user"))
                .andExpect(status().isOk());
    }

    /*
        GET : /user
        - With : Role User -> expect : status 200
     */
    @Test
    @WithMockUser
    void AsUserWhenGetUserThenExpectStatus200() throws Exception {
        mvc.perform(get("/user"))
                .andExpect(status().isOk());
    }


    /*
        GET : /default
        - With : Role Admin -> expect : status 200
     */
    @Test
    @WithMockUser(roles = RoleType.ADMIN)
    void AsAdminWhenGetDefaultThenExpectStatus200() throws Exception {
        mvc.perform(get("/user"))
                .andExpect(status().isOk());
    }

    /*
        GET : /default
        - With : Role Editor -> expect : status 200
     */
    @Test
    @WithMockUser(roles = RoleType.EDITOR)
    void AsEditorWhenGetDefaultThenExpectStatus200() throws Exception {
        mvc.perform(get("/user"))
                .andExpect(status().isOk());
    }

    /*
        GET : /default
        - With : Role User -> expect : status 200
     */
    @Test
    @WithMockUser
    void AsUserWhenGetDefaultThenExpectStatus200() throws Exception {
        mvc.perform(get("/user"))
                .andExpect(status().isOk());
    }


}
