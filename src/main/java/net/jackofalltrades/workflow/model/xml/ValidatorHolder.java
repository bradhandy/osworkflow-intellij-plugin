package net.jackofalltrades.workflow.model.xml;

import com.intellij.util.xml.DomElement;

import java.util.List;

/**
 * Defines the structure of the "validators" element.
 *
 * @author bhandy
 */
public interface ValidatorHolder extends DomElement {

    List<Validator> getValidators();

    Validator addValidator(int index);

    Validator addValidator();

}
