package net.jackofalltrades.workflow.model.xml;

import com.intellij.util.xml.DomElement;

/**
 * Interface for artifacts which may be typed.  The type should be restricted to the return value of
 * {@link #getBasePsiClassName() getBasePsiClassName}.
 *
 * @author bhandy
 */
public interface PsiClassRestrictable extends DomElement {

    String getBasePsiClassName();

}
