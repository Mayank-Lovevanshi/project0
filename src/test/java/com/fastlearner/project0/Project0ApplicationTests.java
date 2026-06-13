package com.fastlearner.project0;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
		"spring.jpa.hibernate.ddl-auto=none",
		"spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect",
		"spring.jpa.properties.hibernate.boot.allow_jdbc_metadata_access=false"
})
class Project0ApplicationTests {

	@Test
	void contextLoads() {
	}

}
