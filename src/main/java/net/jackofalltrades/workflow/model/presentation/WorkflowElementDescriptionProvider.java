package net.jackofalltrades.workflow.model.presentation;

import com.intellij.ide.presentation.Presentation;
import com.intellij.ide.presentation.PresentationProvider;
import com.intellij.pom.PomTarget;
import com.intellij.pom.PomTargetPsiElement;
import com.intellij.psi.ElementDescriptionLocation;
import com.intellij.psi.ElementDescriptionProvider;
import com.intellij.psi.PsiElement;
import com.intellij.usageView.UsageViewShortNameLocation;
import com.intellij.usageView.UsageViewTypeLocation;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.DomTarget;
import com.intellij.util.xml.DomUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Implementation of ElementDescriptionProvider which locates the Presentation annotation for a DomElement and queries
 * the name and/or type from the configured PresentationProvider.
 *
 * @author bhandy
 */
public class WorkflowElementDescriptionProvider implements ElementDescriptionProvider {

    @Nullable
    @Override
    public String getElementDescription(@NotNull PsiElement element, @NotNull ElementDescriptionLocation location) {
        DomElement domElement = unwrapPomTargetPsiElement(element);
        if (domElement == null) {
            domElement = DomUtil.getDomElement(element);
            if (domElement == null) {
                return null;
            }
        }

        Presentation presentation = getElementPresentationAnnotation(domElement);
        if (presentation == null) {
            return null;
        }

        try {
            PresentationProvider<? super DomElement> provider = presentation.provider().newInstance();
            String value = null;
            if (location == UsageViewShortNameLocation.INSTANCE) {
                value = provider.getName(domElement);
            } else if (location == UsageViewTypeLocation.INSTANCE) {
                value = provider.getTypeName(domElement);
            }

            return value;
        } catch (Exception e) {
            // ignore
        }

        return null;
    }

    private Presentation getElementPresentationAnnotation(@NotNull DomElement element) {
        for (Class<?> interfaceClass : element.getClass().getInterfaces()) {
            Presentation presentation = interfaceClass.getAnnotation(Presentation.class);
            if (presentation != null) {
                return presentation;
            }
        }

        return null;
    }

    private DomElement unwrapPomTargetPsiElement(@NotNull PsiElement element) {
        if (!(element instanceof PomTargetPsiElement)) {
            return null;
        }

        PomTargetPsiElement targetPsiElement = (PomTargetPsiElement) element;
        PomTarget target = targetPsiElement.getTarget();

        if (!(target instanceof DomTarget)) {
            return null;
        }

        return ((DomTarget) target).getDomElement();
    }
}
