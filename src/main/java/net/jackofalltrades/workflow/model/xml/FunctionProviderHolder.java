package net.jackofalltrades.workflow.model.xml;

import com.intellij.util.xml.DomElement;

import java.util.List;

/**
 * Defines the structure for the "pre-functions" and "post-functions" elements.
 *
 * @author bhandy
 */
public interface FunctionProviderHolder extends DomElement {

    List<FunctionProvider> getFunctions();

    FunctionProvider addFunction(int index);

    FunctionProvider addFunction();

}
