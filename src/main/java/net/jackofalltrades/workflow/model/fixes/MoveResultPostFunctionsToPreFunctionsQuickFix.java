package net.jackofalltrades.workflow.model.fixes;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.util.xml.DomUtil;
import net.jackofalltrades.workflow.model.xml.FunctionProvider;
import net.jackofalltrades.workflow.model.xml.Result;
import org.jetbrains.annotations.NotNull;

/**
 * Implementation of LocalQuickFix to move the contents of the post-functions element under result or
 * unconditional-result to a pre-functions element under the same parent result or unconditional-result.
 *
 * @author bhandy
 */
public class MoveResultPostFunctionsToPreFunctionsQuickFix implements LocalQuickFix {

    @NotNull
    @Override
    public String getName() {
        return "Move to pre-functions";
    }

    @NotNull
    @Override
    public String getFamilyName() {
        return "OS Workflow";
    }

    @Override
    public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
        Result result = (Result) DomUtil.getDomElement(descriptor.getPsiElement());

        if (result != null) {
            if (!result.getPostFunctions().getFunctions().isEmpty()) {
                if (result.getPreFunctions().getFunctions().isEmpty()) {
                    result.getPreFunctions().copyFrom(result.getPostFunctions());
                } else {
                    for (FunctionProvider postFunction : result.getPostFunctions().getFunctions()) {
                        FunctionProvider preFunction = result.getPreFunctions().addFunction();
                        preFunction.copyFrom(postFunction);
                    }
                }
            }

            result.getPostFunctions().undefine();
        }
    }

}
