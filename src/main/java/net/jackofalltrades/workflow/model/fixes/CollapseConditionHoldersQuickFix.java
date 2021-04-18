package net.jackofalltrades.workflow.model.fixes;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.util.xml.DomUtil;
import net.jackofalltrades.workflow.model.xml.Condition;
import net.jackofalltrades.workflow.model.xml.ConditionHolder;
import org.jetbrains.annotations.NotNull;

/**
 * Implementation of LocalQuickFix to handle collapsing unnecessary "conditions" elements.
 *
 * @author bhandy
 */
public class CollapseConditionHoldersQuickFix implements LocalQuickFix {

  @NotNull
  @Override
  public String getName() {
    return "Collapse Conditions Elements";
  }

  @NotNull
  @Override
  public String getFamilyName() {
    return "OS Workflow";
  }

  @Override
  public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
    ConditionHolder conditionHolder = (ConditionHolder) DomUtil.getDomElement(descriptor.getPsiElement());
    ConditionHolder parent = DomUtil.getParentOfType(conditionHolder, ConditionHolder.class, true);

    if (conditionHolder == null || parent == null) {
      return;
    }

    for (Condition condition : conditionHolder.getConditions()) {
      moveConditionToParent(condition, parent);
    }
    conditionHolder.undefine();
  }

  private void moveConditionToParent(Condition condition, ConditionHolder parent) {
    if (condition == null) {
      return;
    }

    Condition newCondition = (condition instanceof ConditionHolder)
        ? parent.addConditionHolder() : parent.addSingleCondition();
    newCondition.copyFrom(condition);
  }
}
