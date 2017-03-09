package net.jackofalltrades.workflow.model.inspection;

import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.DomElementVisitor;
import com.intellij.util.xml.highlighting.DomElementAnnotationHolder;
import com.intellij.util.xml.highlighting.DomElementsInspection;
import com.intellij.util.xml.highlighting.DomHighlightingHelper;
import net.jackofalltrades.workflow.model.fixes.MoveResultPostFunctionsToPreFunctionsQuickFix;
import net.jackofalltrades.workflow.model.xml.Action;
import net.jackofalltrades.workflow.model.xml.ConditionalResult;
import net.jackofalltrades.workflow.model.xml.Result;
import net.jackofalltrades.workflow.model.xml.Workflow;
import net.jackofalltrades.workflow.model.xml.WorkflowElement;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: bhandy
 * Date: 12/8/12
 * Time: 8:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class UseOfPostFunctionsInspection extends DomElementsInspection<Workflow> {

    public UseOfPostFunctionsInspection() {
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
        return "Inconsistent workflow state";
    }

    @NotNull
    @Override
    public String getShortName() {
        return "UseOfPostFunctions";
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

    public static class Visitor extends WorkflowElement.Visitor {

        private DomElementAnnotationHolder _holder;

        public Visitor(DomElementAnnotationHolder holder) {
            _holder = holder;
        }

        @Override
        public void visitAction(Action action) {
            if (action.getPostFunctions().getFunctions().size() > 0) {
                // TODO find a way to provide a quick fix.  This situation is more difficult than the results.
                _holder.createProblem(action, HighlightSeverity.INFORMATION, "Problematic function construct.");
            }
        }

        @Override
        public void visitResult(Result result) {
            handleResult(result);
        }

        @Override
        public void visitConditionalResult(ConditionalResult result) {
            handleResult(result);
        }

        private void handleResult(Result result) {
            if (result.getPostFunctions().getFunctions().size() > 0) {
                _holder.createProblem(result, HighlightSeverity.INFORMATION, "Problematic function construct.",
                        new MoveResultPostFunctionsToPreFunctionsQuickFix());
            }
        }

    }

}
