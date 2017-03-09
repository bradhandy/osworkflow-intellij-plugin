package net.jackofalltrades.workflow.model.xml;

import com.intellij.util.xml.DomElement;

/**
 * Common interface for visiting specific element types and knowing the type without having to check.
 *
 * @author bhandy
 */
public interface WorkflowElement extends DomElement {

    void accept(Visitor visitor);

    void acceptChildren(Visitor visitor);

    public abstract class Visitor {

        public void visitWorkflowElement(WorkflowElement workflowElement) {

        }

        public void visitAction(Action action) {
            visitWorkflowElement(action);
        }

        public void visitResult(Result result) {
            visitWorkflowElement(result);
        }

        public void visitConditionalResult(ConditionalResult result) {
            visitWorkflowElement(result);
        }

        public void visitRootConditionHolder(ConditionHolder conditionHolder) {
            visitWorkflowElement(conditionHolder);
        }

        public void visitConditionHolder(ConditionHolder conditionHolder) {
            visitWorkflowElement(conditionHolder);
        }

        public void visitFunctionProvider(FunctionProvider functionProvider) {
            visitWorkflowElement(functionProvider);
        }

        public void visitSingleCondition(SingleCondition condition) {
            visitWorkflowElement(condition);
        }

        public void visitStep(Step step) {
            visitWorkflowElement(step);
        }

        public void visitConditionArgument(WorkflowValue value) {
            visitWorkflowElement(value);
        }

        public void visitFunctionProviderArgument(WorkflowValue value) {
            visitWorkflowElement(value);
        }

    }

}
