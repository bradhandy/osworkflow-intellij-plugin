package net.jackofalltrades.workflow.model.inspection;

import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.DomElementVisitor;
import com.intellij.util.xml.DomUtil;
import com.intellij.util.xml.highlighting.DomElementAnnotationHolder;
import com.intellij.util.xml.highlighting.DomElementsInspection;
import com.intellij.util.xml.highlighting.DomHighlightingHelper;
import net.jackofalltrades.workflow.model.fixes.CollapseConditionHoldersQuickFix;
import net.jackofalltrades.workflow.model.xml.ConditionHolder;
import net.jackofalltrades.workflow.model.xml.Workflow;
import net.jackofalltrades.workflow.model.xml.WorkflowElement;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

/**
 * Custom inspection for checking "conditions" to see if they are truly necessary.
 *
 * @author bhandy
 */
public class UnnecessaryConditionHolderInspection extends DomElementsInspection<Workflow> {

    public UnnecessaryConditionHolderInspection() {
        super(Workflow.class);
    }

    @Nls
    @NotNull
    @Override
    public String getGroupDisplayName() {
        return "OS Workflow";
    }

    @Nls
    @NotNull
    @Override
    public String getDisplayName() {
        return "Unnecessary conditions elements";
    }

    @NotNull
    @Override
    public String getShortName() {
        return "UnnecessaryConditions";
    }

    @Override
    protected void checkDomElement(DomElement element, DomElementAnnotationHolder holder, DomHighlightingHelper helper) {
        final Visitor visitor = new Visitor(holder);
        element.accept(new DomElementVisitor() {
            @Override
            public void visitDomElement(DomElement element) {
                if (element instanceof WorkflowElement) {
                    ((WorkflowElement) element).accept(visitor);
                }
            }
        });
    }

    private static class Visitor extends WorkflowElement.Visitor {

        private DomElementAnnotationHolder _annotationHolder;

        private Visitor(DomElementAnnotationHolder annotationHolder) {
            _annotationHolder = annotationHolder;
        }

        @Override
        public void visitConditionHolder(ConditionHolder conditionHolder) {
            ConditionHolder parent = DomUtil.getParentOfType(conditionHolder, ConditionHolder.class, true);
            ConditionHolder grandParent = DomUtil.getParentOfType(parent, ConditionHolder.class, true);
            if (parent != null && (grandParent == null || parent.getConditions().size() > 1)
                    && conditionHolder.getConditions().size() == 1) {
                _annotationHolder.createProblem(conditionHolder, HighlightSeverity.INFORMATION,
                        "Unnecessary conditions tag.", new CollapseConditionHoldersQuickFix());
            }
        }

    }


}
