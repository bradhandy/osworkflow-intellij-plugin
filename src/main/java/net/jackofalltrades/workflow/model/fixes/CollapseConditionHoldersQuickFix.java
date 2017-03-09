package net.jackofalltrades.workflow.model.fixes;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.util.xml.DomUtil;
import net.jackofalltrades.workflow.model.xml.Condition;
import net.jackofalltrades.workflow.model.xml.ConditionHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

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

        int indexOfConditionHolder = parent.getConditions().indexOf(conditionHolder);
        moveLeafToIndexInParent(getDeepestSingleChild(conditionHolder), indexOfConditionHolder, parent);
        conditionHolder.undefine();
    }

    private Condition getDeepestSingleChild(ConditionHolder conditionHolder) {
        List<Condition> children = conditionHolder.getConditions();

        if (children.size() > 1) {
            return conditionHolder;
        } else if (children.size() == 1) {
            Condition condition = children.get(0);
            return (condition instanceof ConditionHolder) ?
                    getDeepestSingleChild((ConditionHolder) condition) : condition;
        }

        return null;
    }

    private void moveLeafToIndexInParent(Condition leaf, int index, ConditionHolder parent) {
        if (leaf == null) {
            return;
        }

        Condition newCondition = (leaf instanceof ConditionHolder)
                ? parent.addConditionHolder(index) : parent.addSingleCondition(index);
        newCondition.copyFrom(leaf);
    }
}
