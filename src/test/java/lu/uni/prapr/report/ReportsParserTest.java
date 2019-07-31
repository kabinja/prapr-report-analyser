package lu.uni.prapr.report;

import lu.uni.prapr.Helpers;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReportsParserTest {
    @Test
    void TestParsingSingleXml(){
        File singleProject = Helpers.getResourceFile("single-project");

        try {
            List<Project> projects = ReportsParser.parse(singleProject);
            assertEquals(1, projects.size());

            Project project = projects.get(0);
            assertEquals("project1", project.getName());
            assertEquals(1, project.getBugs().size());

            Mutations mutations = project.getBugs().get("project1-1");
            assertEquals(158, mutations.getMutations().size());

            Mutation mutation = mutations.getMutation(0);
            assertEquals(16, mutation.getCoveringTestsAsArray().length);
            assertEquals(1, mutation.getKillingTestsAsArray().length);

        } catch (IOException e) {
            fail("IOException raised: " + e.getMessage());
        }
    }
}