package com.example.productmanagement;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ProductManagementApplicationTests {

    @Test
    void contextLoads() {
        assertThat(true).isTrue();
    }
}
