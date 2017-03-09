package net.jackofalltrades.workflow.model.structure;

import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.navigation.ItemPresentation;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.DomElementNavigationProvider;
import com.intellij.util.xml.ElementPresentation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: bhandy
 * Date: 12/10/12
 * Time: 1:04 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class DomElementStructureViewTreeElement<T extends DomElement>
        implements StructureViewTreeElement, ItemPresentation {

    private T _domElement;
    private DomElement _parent;
    private DomElementNavigationProvider _navigationProvider;

    protected DomElementStructureViewTreeElement(@NotNull T domElement, @Nullable DomElement parent,
                                                 DomElementNavigationProvider navigationProvider) {
        _domElement = domElement;
        _parent = parent;
        _navigationProvider = navigationProvider;
    }

    protected T getDomElement() {
        return _domElement;
    }

    protected DomElement getParent() {
        return _parent;
    }

    protected DomElementNavigationProvider getNavigationProvider() {
        return _navigationProvider;
    }

    @Nullable
    @Override
    public String getPresentableText() {
        if (!_domElement.isValid()) {
            return "<unknown>";
        }

        ElementPresentation presentation = _domElement.getPresentation();
        String name = presentation.getElementName();
        return (name == null) ? presentation.getTypeName() : name;
    }

    @Nullable
    @Override
    public String getLocationString() {
        return null;
    }

    @Nullable
    @Override
    public Icon getIcon(boolean unused) {
        return _domElement.isValid() ? _domElement.getPresentation().getIcon() : null;
    }

    @Override
    public Object getValue() {
        return _domElement.isValid() ? _domElement.getXmlElement() : null;
    }

    @Override
    public void navigate(boolean requestFocus) {
        if (_navigationProvider != null) {
            _navigationProvider.navigate(_domElement, requestFocus);
        }
    }

    @Override
    public boolean canNavigate() {
        return _navigationProvider != null && _navigationProvider.canNavigate(_domElement);
    }

    @Override
    public boolean canNavigateToSource() {
        return canNavigate();
    }

    @Override
    public ItemPresentation getPresentation() {
        return this;
    }

}
