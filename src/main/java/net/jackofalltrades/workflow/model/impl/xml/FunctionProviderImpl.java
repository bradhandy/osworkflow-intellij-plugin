package net.jackofalltrades.workflow.model.impl.xml;

import net.jackofalltrades.workflow.model.xml.FunctionProvider;

/**
 * Implementation base class for restricting "function" in the configuration to implementations of FunctionProvider.
 *
 * @author bhandy
 */
public abstract class FunctionProviderImpl extends CommonWorkflowElement implements FunctionProvider {

    @Override
    public void accept(Visitor visitor) {
        visitor.visitFunctionProvider(this);
    }

    @Override
    public String getBasePsiClassName() {
        return "com.opensymphony.workflow.FunctionProvider";
    }

}
