package net.jackofalltrades.workflow.model.inspection;

import com.google.common.collect.Lists;
import net.jackofalltrades.BaseLightJavaCodeInsightFixtureTest;

import java.util.List;

public class UnnecessaryConditionHolderInspectionTest extends BaseLightJavaCodeInsightFixtureTest {

  public void testUnnecessaryConditionHolderIsIdentified() {
    myFixture.copyDirectoryToProject("unnecessary-condition-holder/highlighting", "");
    myFixture.configureByFile("workflow.xml");

    WorkflowInspectionToolProvider workflowInspectionToolProvider = new WorkflowInspectionToolProvider();
    List<Class> inspectionTools = Lists.newArrayList(workflowInspectionToolProvider.getInspectionClasses());
    assertContainsElements(inspectionTools, UnnecessaryConditionHolderInspection.class);

    myFixture.enableInspections(workflowInspectionToolProvider);
    myFixture.checkHighlighting(true, false, false, false);
  }

}
