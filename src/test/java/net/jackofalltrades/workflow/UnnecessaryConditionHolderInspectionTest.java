package net.jackofalltrades.workflow;

import com.google.common.collect.Lists;
import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.codeInspection.QuickFix;
import com.intellij.codeInspection.ex.QuickFixWrapper;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import net.jackofalltrades.BaseLightCodeInsightFixtureTest;
import net.jackofalltrades.workflow.model.fixes.CollapseConditionHoldersQuickFix;
import net.jackofalltrades.workflow.model.inspection.UnnecessaryConditionHolderInspection;
import net.jackofalltrades.workflow.model.inspection.WorkflowInspectionToolProvider;

import java.util.List;

public class UnnecessaryConditionHolderInspectionTest extends BaseLightCodeInsightFixtureTest {

    public void testUnnecessaryConditionHolderIsIdentified() {
        myFixture.copyDirectoryToProject("unnecessary-condition-holder", "");
        myFixture.configureByFile("before/workflow.xml");

        WorkflowInspectionToolProvider workflowInspectionToolProvider = new WorkflowInspectionToolProvider();
        List<Class> inspectionTools = Lists.newArrayList(workflowInspectionToolProvider.getInspectionClasses());
        assertContainsElements(inspectionTools, UnnecessaryConditionHolderInspection.class);

        myFixture.enableInspections(new UnnecessaryConditionHolderInspection());
        myFixture.checkHighlighting(false, false, true, false);
    }

    public void testQuickFixIsAppliedCorrectly() {
        myFixture.copyDirectoryToProject("unnecessary-condition-holder", "");
        myFixture.configureByFile("before/workflow.xml");

        WorkflowInspectionToolProvider workflowInspectionToolProvider = new WorkflowInspectionToolProvider();
        List<Class> inspectionTools = Lists.newArrayList(workflowInspectionToolProvider.getInspectionClasses());
        assertContainsElements(inspectionTools, UnnecessaryConditionHolderInspection.class);

        myFixture.enableInspections(new UnnecessaryConditionHolderInspection());
        myFixture.checkHighlighting(false, false, true, false);

        List<IntentionAction> intentionActions = myFixture.getAllQuickFixes();

        assertEquals("There should be one intention action.", 2, intentionActions.size());
        assertTrue("The intention action should be a wrapper around the quick fix.",
                intentionActions.get(0) instanceof QuickFixWrapper);

        QuickFixWrapper quickFixWrapper = (QuickFixWrapper) intentionActions.get(0);
        assertTrue("The quick fix type does not match.",
                quickFixWrapper.getFix() instanceof CollapseConditionHoldersQuickFix);

        CommandProcessor commandProcessor = CommandProcessor.getInstance();
        commandProcessor.runUndoTransparentAction(getQuickFixRunnable(quickFixWrapper, myFixture.getProject(),
                myFixture.getEditor(), myFixture.getFile()));

        myFixture.checkResultByFile("unnecessary-condition-holder/after/workflow.xml", true);
    }

    private Runnable getQuickFixRunnable(QuickFixWrapper wrapper, Project project, Editor editor, PsiFile file) {
        QuickFix quickFix = wrapper.getFix();
        if (quickFix.startInWriteAction()) {
            return () -> ApplicationManager.getApplication().runWriteAction(() -> wrapper.invoke(project, editor, file));
        }

        return () -> wrapper.invoke(project, editor, file);
    }

}
