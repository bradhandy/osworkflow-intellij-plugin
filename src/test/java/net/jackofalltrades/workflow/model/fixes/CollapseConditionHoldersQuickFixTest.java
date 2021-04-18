package net.jackofalltrades.workflow.model.fixes;

import com.google.common.collect.Lists;
import com.intellij.codeInsight.actions.ReformatCodeProcessor;
import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.QuickFix;
import com.intellij.codeInspection.ex.QuickFixWrapper;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import net.jackofalltrades.BaseLightJavaCodeInsightFixtureTest;
import net.jackofalltrades.workflow.model.inspection.UnnecessaryConditionHolderInspection;
import net.jackofalltrades.workflow.model.inspection.WorkflowInspectionToolProvider;

import java.util.List;

public class CollapseConditionHoldersQuickFixTest extends BaseLightJavaCodeInsightFixtureTest {

  public void testQuickFixIsAppliedCorrectlyWhenAffectedElementIsChildOfRootConditionsElement() {
    myFixture.copyDirectoryToProject("unnecessary-condition-holder/quick-fix/child-of-root-conditions", "");
    myFixture.configureByFile("before/workflow.xml");

    WorkflowInspectionToolProvider workflowInspectionToolProvider = new WorkflowInspectionToolProvider();
    List<Class> inspectionTools = Lists.newArrayList(workflowInspectionToolProvider.getInspectionClasses());
    assertContainsElements(inspectionTools, UnnecessaryConditionHolderInspection.class);

    myFixture.enableInspections(workflowInspectionToolProvider);
    myFixture.checkHighlighting(true, false, false, false);

    List<IntentionAction> intentionActions = myFixture.getAllQuickFixes();

    assertEquals("There should be one intention action.", 1, intentionActions.size());
    assertTrue("The intention action should be a wrapper around the quick fix.",
        intentionActions.get(0) instanceof QuickFixWrapper);

    QuickFixWrapper quickFixWrapper = null;
    for (IntentionAction intentionAction : intentionActions) {
      quickFixWrapper = (QuickFixWrapper) intentionAction;
      LocalQuickFix quickFix = quickFixWrapper.getFix();
      if (quickFix.getName().equals("Collapse Conditions Elements")
          && quickFix.getFamilyName().equals("OS Workflow")) {
        break;
      }
    }
    assertNotNull("The expected quick fix type is not present.", quickFixWrapper);

    CommandProcessor commandProcessor = CommandProcessor.getInstance();
    commandProcessor.runUndoTransparentAction(getQuickFixRunnable(quickFixWrapper, myFixture.getProject(),
        myFixture.getEditor(), myFixture.getFile()));


    ReformatCodeProcessor reformatCodeProcessor = new ReformatCodeProcessor(myFixture.getProject(), myFixture.getFile(), null, false);
    reformatCodeProcessor.runWithoutProgress();

    myFixture.checkResultByFile("unnecessary-condition-holder/quick-fix/child-of-root-conditions/after/workflow.xml", true);
  }

  public void testQuickFixIsAppliedCorrectlyWhenAffectedElementIsDeeperInTree() {
    myFixture.copyDirectoryToProject("unnecessary-condition-holder/quick-fix/deep-child-conditions", "");
    myFixture.configureByFile("before/workflow.xml");

    WorkflowInspectionToolProvider workflowInspectionToolProvider = new WorkflowInspectionToolProvider();
    List<Class> inspectionTools = Lists.newArrayList(workflowInspectionToolProvider.getInspectionClasses());
    assertContainsElements(inspectionTools, UnnecessaryConditionHolderInspection.class);

    myFixture.enableInspections(workflowInspectionToolProvider);
    myFixture.checkHighlighting(true, false, false, false);

    List<IntentionAction> intentionActions = myFixture.getAllQuickFixes();

    assertEquals("There should be one intention action.", 1, intentionActions.size());
    assertTrue("The intention action should be a wrapper around the quick fix.",
        intentionActions.get(0) instanceof QuickFixWrapper);

    QuickFixWrapper quickFixWrapper = null;
    for (IntentionAction intentionAction : intentionActions) {
      quickFixWrapper = (QuickFixWrapper) intentionAction;
      LocalQuickFix quickFix = quickFixWrapper.getFix();
      if (quickFix.getName().equals("Collapse Conditions Elements")
          && quickFix.getFamilyName().equals("OS Workflow")) {
        break;
      }
    }
    assertNotNull("The expected quick fix type is not present.", quickFixWrapper);

    CommandProcessor commandProcessor = CommandProcessor.getInstance();
    commandProcessor.runUndoTransparentAction(getQuickFixRunnable(quickFixWrapper, myFixture.getProject(),
        myFixture.getEditor(), myFixture.getFile()));


    ReformatCodeProcessor reformatCodeProcessor = new ReformatCodeProcessor(myFixture.getProject(), myFixture.getFile(), null, false);
    reformatCodeProcessor.runWithoutProgress();

    myFixture.checkResultByFile("unnecessary-condition-holder/quick-fix/deep-child-conditions/after/workflow.xml", true);
  }

  private Runnable getQuickFixRunnable(QuickFixWrapper wrapper, Project project, Editor editor, PsiFile file) {
    QuickFix quickFix = wrapper.getFix();
    if (quickFix.startInWriteAction()) {
      return () -> ApplicationManager.getApplication().runWriteAction(() -> wrapper.invoke(project, editor, file));
    }

    return () -> wrapper.invoke(project, editor, file);
  }

}
