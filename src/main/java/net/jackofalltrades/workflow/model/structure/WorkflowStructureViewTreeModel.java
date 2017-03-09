package net.jackofalltrades.workflow.model.structure;

import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.structureView.impl.xml.XmlFileTreeElement;
import com.intellij.ide.structureView.impl.xml.XmlStructureViewTreeModel;
import com.intellij.ide.util.treeView.smartTree.Sorter;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.xml.XmlFile;
import com.intellij.util.xml.DomElementNavigationProvider;
import com.intellij.util.xml.DomElementsNavigationManager;
import com.intellij.util.xml.DomFileElement;
import com.intellij.util.xml.DomManager;
import net.jackofalltrades.workflow.model.xml.Workflow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Implementation of the XmlStructureViewTreeModel to handle the tree model for OS Workflow files.
 *
 * @author bhandy
 */
public class WorkflowStructureViewTreeModel extends XmlStructureViewTreeModel {

    private XmlFile _xmlFile;
    private DomElementNavigationProvider _navigationProvider;

    public WorkflowStructureViewTreeModel(@NotNull XmlFile file, @Nullable Editor editor) {
        super(file, editor);
        _xmlFile = file;
        _navigationProvider = DomElementsNavigationManager.getManager(file.getProject())
                .getDomElementsNavigateProvider(DomElementsNavigationManager.DEFAULT_PROVIDER_NAME);
    }

    @NotNull
    @Override
    public StructureViewTreeElement getRoot() {
        DomFileElement<Workflow> fileElement =
                DomManager.getDomManager(_xmlFile.getProject()).getFileElement(_xmlFile, Workflow.class);
        return (fileElement == null) ? new XmlFileTreeElement(_xmlFile)
                : new WorkflowStructureViewTreeElement(fileElement.getRootElement(), _navigationProvider);
    }

    @NotNull
    @Override
    public Sorter[] getSorters() {
        return Sorter.EMPTY_ARRAY;
    }

}
