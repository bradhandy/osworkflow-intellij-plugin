package net.jackofalltrades.workflow.model.xml;

import com.intellij.util.xml.DomElement;

import java.util.List;

/**
 * Defines the structure of the "splits" element.
 *
 * @author bhandy
 */
public interface SplitHolder extends DomElement {

    List<Split> getSplits();

    Split addSplit(int index);

    Split addSplit();

}
