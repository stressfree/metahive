package net.triptech.metahive.model;

import net.triptech.metahive.model.Comment;

import org.junit.Test;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.test.context.ContextConfiguration;

@RooIntegrationTest(entity = Comment.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring-test/applicationContext.xml")
public class CommentIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }
}
