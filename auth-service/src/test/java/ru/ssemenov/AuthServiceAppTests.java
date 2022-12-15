package ru.ssemenov;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.ssemenov.dtos.JwtRequest;
import ru.ssemenov.dtos.UserDto;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthServiceAppTests {

	@Autowired
	MockMvc mvc;

	@Test
	public void authorizationTest() throws Exception{
		JwtRequest request = new JwtRequest("admin", "admin");
		mvc
				.perform(
						post("/api/v1/secure/authenticate")
								.contentType(MediaType.APPLICATION_JSON)
								.content(new ObjectMapper().writeValueAsString(request))
				)
				.andDo(print())
				.andExpect(status().isOk());

	}

	@Test
	public void authorizationWithBadCredentialsTest() throws Exception {
		JwtRequest request = new JwtRequest("admin", "123");
		mvc
				.perform(
						post("/api/v1/secure/authenticate")
								.contentType(MediaType.APPLICATION_JSON)
								.content(new ObjectMapper().writeValueAsString(request))
				)
				.andDo(print())
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.message", is("Bad credentials")));
	}

	@Test
	public void addUserTest() throws Exception{
		UserDto user = UserDto.builder()
				.username("Gork")
				.email("gork@mork.fb")
				.password("green")
				.rolesNames(List.of("ROLE_ADMIN"))
				.build();

		mvc
				.perform(
						post("/api/v1/secure/register")
								.header("vatCode", "7777777777")
								.contentType(MediaType.APPLICATION_JSON)
								.content(new ObjectMapper().writeValueAsString(user))
				)
				.andDo(print())
				.andExpect(status().isOk());

	}

	@Test
	public void deleteUserTest() throws Exception {
		mvc
				.perform(
						delete("/api/v1/secure/{id}","fa6f0504-54bd-42b1-8363-7970492e724d")
								.header("vatCode", "6767676767")
				)
				.andDo(print())
				.andExpect(status().isOk());
	}



}
