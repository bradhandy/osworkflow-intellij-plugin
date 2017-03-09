package net.jackofalltrades.workflow.model.converter;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.pom.references.PomService;
import com.intellij.psi.ElementDescriptionUtil;
import com.intellij.psi.PsiElement;
import com.intellij.usageView.UsageViewShortNameLocation;
import com.intellij.usageView.UsageViewTypeLocation;
import com.intellij.util.xml.ConvertContext;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.DomResolveConverter;
import com.intellij.util.xml.DomTarget;
import com.intellij.util.xml.DomUtil;
import com.intellij.util.xml.ResolvingConverter;
import net.jackofalltrades.workflow.model.xml.ActionHolder;
import net.jackofalltrades.workflow.model.xml.Result;
import net.jackofalltrades.workflow.model.xml.Step;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * Implementation of ResolvingConverter which wrapper the DomResolveConverter for all necessary operations except the
 * {@link #fromString(String, com.intellij.util.xml.ConvertContext) fromString},
 * {@link #createLookupElement(net.jackofalltrades.workflow.model.xml.Step) createLookupElement} and
 * {@link #getVariants(com.intellij.util.xml.ConvertContext) getVariants} operations.
 *
 * @author bhandy
 */
public class WorkflowStepSelfReferenceResolveConverter extends ResolvingConverter<Step> {

    private DomResolveConverter<Step> domResolveConverter = DomResolveConverter.createConverter(Step.class);

    @NotNull
    @Override
    public Collection<? extends Step> getVariants(ConvertContext context) {
        Collection<? extends Step> steps = domResolveConverter.getVariants(context);

        ActionHolder actionHolder = DomUtil.getParentOfType(context.getInvocationElement(), ActionHolder.class, true);
        if (actionHolder != null && !"initial-actions".equals(actionHolder.getXmlTag().getName())) {
            Collection<Step> copySteps = Lists.newArrayList(steps);
            Step step = context.getInvocationElement().getManager().createMockElement(Step.class, context.getModule(), false);
            step.getId().setValue(-1);
            step.getName().setValue("Current Step");
            copySteps.add(step);
            steps = Lists.newArrayList(copySteps);
        }

        return steps;
    }

    @Nullable
    @Override
    public Step fromString(@Nullable @NonNls String value, ConvertContext context) {
        if ("-1".equals(value)) {
            DomElement domElement = context.getInvocationElement();
            Result result = DomUtil.getParentOfType(domElement, Result.class, true);
            if (result != null) {
                Step step = DomUtil.getParentOfType(result, Step.class, true);
                if (step != null) {
                    return step;
                }
            }
        }

        return domResolveConverter.fromString(value, context);
    }

    @Nullable
    @Override
    public LookupElement createLookupElement(Step step) {
        LookupElementBuilder builder = LookupElementBuilder.create(step, step.getId().getStringValue());

        String name = step.getName().getStringValue();
        if (!Strings.isNullOrEmpty(name)) {
            PsiElement target = PomService.convertToPsi(DomTarget.getTarget(step));
            builder = builder.withLookupString(name)
                    .withPresentableText(ElementDescriptionUtil.getElementDescription(target, UsageViewShortNameLocation.INSTANCE))
                    .withTypeText(ElementDescriptionUtil.getElementDescription(target, UsageViewTypeLocation.INSTANCE), true);
        }

        return builder;
    }

    @Nullable
    @Override
    public PsiElement getPsiElement(@Nullable Step resolvedValue) {
        return domResolveConverter.getPsiElement(resolvedValue);
    }

    @Override
    public String getErrorMessage(@Nullable String s, ConvertContext context) {
        return domResolveConverter.getErrorMessage(s, context);
    }

    @Override
    public boolean isReferenceTo(@NotNull PsiElement element, String stringValue, @Nullable Step resolveResult, ConvertContext context) {
        return domResolveConverter.isReferenceTo(element, stringValue, resolveResult, context);
    }

    @Override
    public LocalQuickFix[] getQuickFixes(ConvertContext context) {
        return domResolveConverter.getQuickFixes(context);
    }

    @Nullable
    @Override
    public String toString(@Nullable Step step, ConvertContext context) {
        return domResolveConverter.toString(step, context);
    }

}
