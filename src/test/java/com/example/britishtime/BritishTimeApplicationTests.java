package com.example.britishtime;

import com.example.britishtime.service.TimeConversionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class BritishTimeApplicationTests {

	@Autowired
	private TimeConversionService timeConversionService;

	@Test
	void contextLoads() {
		assertThat(timeConversionService).isNotNull();
	}
}
