package com.example.gdziejestczoperweb

import com.example.gdziejestczoperweb.model.Position
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class GdziejestczoperwebApplicationTests {

	@Autowired
	lateinit var testRestTemplate: TestRestTemplate


	@Test
	fun testHelloController() {
		val result = testRestTemplate.getForEntity("/position", Position::class.java)
		assertNotNull(result)
		assertEquals(result.statusCode, HttpStatus.OK)
	}

}
