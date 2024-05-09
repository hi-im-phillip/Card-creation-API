package hr.card.management.web.ui;

import hr.card.management.web.ui.configuration.RestTemplateConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {RestTemplateConfiguration.class})
public abstract class ControllerTest {

    @Autowired
    protected TestRestTemplate restTemplate;
    
}
