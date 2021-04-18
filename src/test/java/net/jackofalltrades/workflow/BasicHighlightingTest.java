package net.jackofalltrades.workflow;

import net.jackofalltrades.BaseLightJavaCodeInsightFixtureTest;

public class BasicHighlightingTest extends BaseLightJavaCodeInsightFixtureTest {

    public void testMissingFunctionProviderClassReferenceIsSoft() {
        myFixture.copyDirectoryToProject("parsing", "");
        myFixture.configureByFile("workflow.xml");

        myFixture.checkHighlighting(true, false, false, false);
    }

}
