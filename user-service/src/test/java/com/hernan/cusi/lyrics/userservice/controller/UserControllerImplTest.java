package com.hernan.cusi.lyrics.userservice.controller;

import org.hernan.cussi.lyrics.userservice.LyricsUserServiceApplication;
import org.hernan.cussi.lyrics.userservice.business.UserBusiness;
import org.hernan.cussi.lyrics.userservice.controller.UserControllerImpl;
import org.hernan.cussi.lyrics.userservice.dto.UserDto;
import org.hernan.cussi.lyrics.userservice.model.User;
import org.hernan.cussi.lyrics.utils.test.PaginationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.MOCK,
	classes = LyricsUserServiceApplication.class
)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerImplTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserBusiness userBusiness;

	@InjectMocks
	private UserControllerImpl userController;

	@BeforeEach
	public void preTest() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void getUser_thenStatus200() throws Exception {
		when(userBusiness.getUser(anyString())).thenReturn(
			User.builder().id("534rdc6l5i").name("test").email("test@gmail.com").build()
		);
		mockMvc
			.perform(get("/api/v1/users/534rdc6l5i"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(content().json("{\"id\":\"534rdc6l5i\",\"name\":\"test\",\"email\":\"test@gmail.com\"}"));
	}

	@Test
	public void createUser_thenStatus200() throws Exception {
		when(userBusiness.createUser(any(UserDto.class))).thenReturn(
			User.builder().id("534rdc6l5i").name("test").password("{bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG").email("test@gmail.com").build()
		);
		mockMvc
			.perform(
				post("/api/v1/users")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content("{\"name\":\"test\",\"password\":\"12345678\",\"email\":\"test@gmail.com\"}")
			)
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(content().json("{\"id\":\"534rdc6l5i\",\"name\":\"test\",\"email\":\"test@gmail.com\"}"));
	}

	@Test
	public void createUser_thenStatus400() throws Exception {
		mockMvc
			.perform(
				post("/api/v1/users")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content("{\"name\":\"t\",\"email\":\"test@gmail.com\"}")
			)
			.andExpect(status().is4xxClientError());
	}

	@Test
	public void updateUser_thenStatus200() throws Exception {
		when(userBusiness.updateUser(anyString(), any(UserDto.class))).thenReturn(
			User.builder().id("534rdc6l5i").name("test2").email("test@gmail.com").build()
		);
		mockMvc
			.perform(
				put("/api/v1/users/1")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content("{\"name\":\"test2\",\"email\":\"test@gmail.com\"}")
			)
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(content().json("{\"id\":\"534rdc6l5i\",\"name\":\"test2\",\"email\":\"test@gmail.com\"}"));
	}

	@Test
	public void updateUser_thenStatus400() throws Exception {
		mockMvc
			.perform(
				put("/api/v1/users/1")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content("{\"email\":\"test@gmail.com\"}")
			)
			.andExpect(status().is4xxClientError());
	}

	@Test
	public void deleteUser_thenStatus200() throws Exception {
		doNothing().when(userBusiness).deleteUser(anyString());
		mockMvc
			.perform(delete("/api/v1/users/1"))
			.andExpect(status().isOk());
	}

	@Test
	public void getUsers_thenStatus200() throws Exception {
		var pageable = PageRequest.of(0, 5, Sort.Direction.ASC, "name");

		when(userBusiness.getUsers(anyInt(), anyInt(), anyString())).thenReturn(
			PaginationUtil.paginateList(pageable, List.of(
				User.builder().id("534rdc6l5i").name("test1").email("test1@gmail.com").build(),
				User.builder().id("534rdc6l5j").name("test2").email("test2@gmail.com").build()
			))
		);
		mockMvc
			.perform(get("/api/v1/users?page=0&size=10&sort=name"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(content().json("{\"content\":[{\"id\":\"534rdc6l5i\",\"name\":\"test1\",\"email\":\"test1@gmail.com\"},{\"id\":\"534rdc6l5j\",\"name\":\"test2\",\"email\":\"test2@gmail.com\"}],\"pageable\":{\"pageNumber\":0,\"pageSize\":5,\"sort\":{\"empty\":false,\"unsorted\":false,\"sorted\":true},\"offset\":0,\"paged\":true,\"unpaged\":false},\"totalElements\":2,\"totalPages\":1,\"last\":true,\"size\":5,\"number\":0,\"sort\":{\"empty\":false,\"unsorted\":false,\"sorted\":true},\"numberOfElements\":2,\"first\":true,\"empty\":false}"));
	}

}
