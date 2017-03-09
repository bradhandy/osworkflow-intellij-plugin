package net.jackofalltrades.workflow.model.xml;

import com.intellij.util.xml.DomElement;

import java.util.List;

/**
 * Defines the structure of the "permission" element.
 *
 * @author bhandy
 */
public interface PermissionHolder extends DomElement {

    List<Permission> getPermissions();

    Permission addPermission(int index);

    Permission addPermission();

}
