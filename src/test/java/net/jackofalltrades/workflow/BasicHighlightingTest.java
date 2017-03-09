package net.jackofalltrades.workflow;

import net.jackofalltrades.BaseLightCodeInsightFixtureTest;

public class BasicHighlightingTest extends BaseLightCodeInsightFixtureTest {

    public void testMissingFunctionProviderClassReferenceIsSoft() {
        myFixture.copyDirectoryToProject("parsing", "");
        myFixture.configureByFile("basic-workflow.xml");

        myFixture.checkHighlighting(true, false, false, false);
    }

}
