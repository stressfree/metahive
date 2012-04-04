package net.triptech.metahive.model;

import net.triptech.metahive.model.Organisation;

import org.junit.Test;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.test.context.ContextConfiguration;

@RooIntegrationTest(entity = Organisation.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring-test/applicationContext.xml")
public class OrganisationIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }
}
