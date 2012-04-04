package net.triptech.metahive.model;

import net.triptech.metahive.model.DataSource;

import org.junit.Test;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.test.context.ContextConfiguration;

@RooIntegrationTest(entity = DataSource.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring-test/applicationContext.xml")
public class DataSourceIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }
}
