package kr.or.connect.reservation.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import kr.or.connect.reservation.dto.CategoryWithDisplayInfoCount;
import kr.or.connect.reservation.service.ReservationService;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Collections;

public class ReservationControllerTest {
	
	@InjectMocks
	ReservationController reservationController;
	
	@Mock
	ReservationService reservationService;
	
	private MockMvc mockMvc;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build(); // 특정 Bean을 단위 테스트하기에 좋은 API
	}
	
	@Test
	public void getAllCategoryWithDisplayInfoCountTest() throws Exception {
		//Given
		CategoryWithDisplayInfoCount stub = new CategoryWithDisplayInfoCount();
		stub.setId(1);
		stub.setName("Test");
		stub.setCount(3);
		
		// When & Then
		when(reservationService.getAllCategoryWithDisplayInfoCount()).thenReturn(Collections.singletonList(stub));
		
		mockMvc.perform(get("/api/categories")
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size").value(1))
			.andExpect(jsonPath("$.items[0].id").value(1))
			.andExpect(jsonPath("$.items[0].name").value("Test"))
			.andExpect(jsonPath("$.items[0].count").value(3));
		
		verify(reservationService).getAllCategoryWithDisplayInfoCount();
		
		
		
	}
}
