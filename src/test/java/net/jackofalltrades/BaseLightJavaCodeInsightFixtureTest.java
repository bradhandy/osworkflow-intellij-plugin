package net.jackofalltrades;

import com.google.common.collect.Lists;
import com.intellij.javaee.ExternalResourceManagerEx;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;

import java.util.List;

public abstract class BaseLightJavaCodeInsightFixtureTest
    extends LightJavaCodeInsightFixtureTestCase {

  @Override
  protected void setUp() throws Exception {
    super.setUp();

    /*
     * determine if the missing schema error highlighting should be suppressed.  suppressing these errors will
     * allow inspection test case to pass when extra highlighting is used as a failure condition in a test case.
     */
    if (suppressMissingSchemaHighlighting()) {
      final ExternalResourceManagerEx externalResourceManager =
          ExternalResourceManagerEx.getInstanceEx();
      ApplicationManager.getApplication()
          .runWriteAction(
              () -> {
                for (String ignorableSchemaResource : getIgnorableSchemaResources()) {
                  externalResourceManager.addIgnoredResources(
                      List.of(ignorableSchemaResource), null);
                }
              });
    }
  }

  @Override
  protected String getTestDataPath() {
    return "build/resources/test";
  }

  protected boolean suppressMissingSchemaHighlighting() {
    return true;
  }

  protected List<String> getIgnorableSchemaResources() {
    return Lists.newArrayList("http://www.opensymphony.com/osworkflow/workflow_2_8.dtd");
  }
}
