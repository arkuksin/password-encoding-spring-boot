package de.kuksin.passwordencoding.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.kuksin.passwordencoding.authentication.UserCredentials;
import de.kuksin.passwordencoding.authentication.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CarResourcesTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Test
    void getCarsShouldReturnUnauthorizedIfTheRequestHasNoBasicAuthentication() throws Exception {
        mockMvc.perform(get("/cars"))
                // then
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getCarsShouldReturnCarsForTheAuthenticatedUser() throws Exception {
        mockMvc.perform(get("/cars")
                .with(httpBasic("user", "password")))
                // then
                .andExpect(status().isOk());
    }

    @Test
    void registrationShouldReturnCreated() throws Exception {

        // register
        de.kuksin.passwordencoding.resources.UserCredentials userCredentials = de.kuksin.passwordencoding.resources.UserCredentials.builder().username("toyota").password("my secret").build();
        mockMvc.perform(post("/registration")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userCredentials))
        )
                // then
                .andExpect(status().isCreated());
    }

    @Test
    void registrationShouldReturnUnauthorizedWithWrongCredentials() throws Exception {

        // get cars
        mockMvc.perform(get("/cars")
                .with(httpBasic("user", "wrong password")))

                // then
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getCarsShouldUpdatePasswordFromWorkingFactor5to10() throws Exception {
        // get cars
        mockMvc.perform(get("/cars")
                .with(httpBasic("user with working factor 5", "password")))

                // then
                .andExpect(status().isOk());

        UserCredentials userCredentials = userRepository.findByUsername("user with working factor 5");
        assertThat(userCredentials.getPassword()).startsWith("{bcrypt}$2a$10");
    }

    @Test
    void getCarsShouldUpdateSha1PasswordToBcrypt() throws Exception {
        // get cars
        mockMvc.perform(get("/cars")
                .with(httpBasic("user with sha1 encoding", "password")))

                // then
                .andExpect(status().isOk());

        UserCredentials userCredentials = userRepository.findByUsername("user with sha1 encoding");
        assertThat(userCredentials.getPassword()).startsWith("{bcrypt}");
    }

    @Test
    void getCarsShouldReturnOkForScryptUser() throws Exception {
        // get cars
        mockMvc.perform(get("/cars")
                .with(httpBasic("scrypt user", "password")))

                // then
                .andExpect(status().isOk());
    }
}