package net.jackofalltrades.workflow.model.inspection;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.openapi.project.Project;
import com.intellij.pom.references.PomService;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.DomElementVisitor;
import com.intellij.util.xml.DomTarget;
import com.intellij.util.xml.DomUtil;
import com.intellij.util.xml.highlighting.DomElementAnnotationHolder;
import com.intellij.util.xml.highlighting.DomElementsInspection;
import com.intellij.util.xml.highlighting.DomHighlightingHelper;
import net.jackofalltrades.workflow.model.xml.Step;
import net.jackofalltrades.workflow.model.xml.Workflow;
import net.jackofalltrades.workflow.model.xml.WorkflowElement;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * Implementation of DomElementsInspection which identifies steps that are unused in a workflow.
 *
 * @author bhandy
 */
public class UnusedStepInspection extends DomElementsInspection<Workflow> {

    public UnusedStepInspection() {
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
        return "Unused Step";
    }

    @NotNull
    @Override
    public String getShortName() {
        return "UnusedStep";
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

        private DomElementAnnotationHolder _holder;

        private Visitor(DomElementAnnotationHolder holder) {
            _holder = holder;
        }

        @Override
        public void visitStep(Step step) {
            Project project = step.getManager().getProject();
            GlobalSearchScope scope = GlobalSearchScope.fileScope(project, DomUtil.getFile(step).getVirtualFile());
            DomTarget domTarget = DomTarget.getTarget(step);
            if (domTarget != null) {
                PsiElement element = PomService.convertToPsi(project, domTarget);
                Collection<PsiReference> references = ReferencesSearch.search(element, scope).findAll();
                if (references.size() < 1) {
                    _holder.createProblem(step.getId(), ProblemHighlightType.LIKE_UNUSED_SYMBOL, "Unused Workflow Step",
                            null, new RemoveUnusedStepQuickFix());
                }
            }
        }

    }

    private static class RemoveUnusedStepQuickFix implements LocalQuickFix {

        @NotNull
        @Override
        public String getName() {
            return "Remove Unused Step";
        }

        @NotNull
        @Override
        public String getFamilyName() {
            return "OS Workflow";
        }

        @Override
        public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
            DomElement domElement = DomUtil.getDomElement(descriptor.getPsiElement());
            Step step = (domElement instanceof Step)
                    ? (Step) domElement : DomUtil.getParentOfType(domElement, Step.class, true);

            if (step != null) {
                step.undefine();
            }
        }

    }

}
