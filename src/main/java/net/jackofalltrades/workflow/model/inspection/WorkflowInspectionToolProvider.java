package net.jackofalltrades.workflow.model.inspection;

import com.intellij.codeInspection.InspectionToolProvider;

/**
 * Implementation of InspectionToolProvider which informs IntelliJ of the provided inspections.
 *
 * @author bhandy
 */
public class WorkflowInspectionToolProvider implements InspectionToolProvider {

    @Override
    public Class[] getInspectionClasses() {
        return new Class[]{UnnecessaryConditionHolderInspection.class, UseOfPostFunctionsInspection.class,
                UnusedStepInspection.class};
    }

}
