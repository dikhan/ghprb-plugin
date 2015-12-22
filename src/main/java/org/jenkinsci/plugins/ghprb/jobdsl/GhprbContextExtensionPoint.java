package org.jenkinsci.plugins.ghprb.jobdsl;

import antlr.ANTLRException;
import com.google.common.base.Joiner;
import hudson.Extension;
import javaposse.jobdsl.dsl.helpers.triggers.TriggerContext;
import javaposse.jobdsl.plugin.ContextExtensionPoint;
import javaposse.jobdsl.plugin.DslExtensionMethod;
import org.jenkinsci.plugins.ghprb.GhprbBranch;
import org.jenkinsci.plugins.ghprb.GhprbTrigger;

import java.util.ArrayList;

@Extension(optional = true)
public class GhprbContextExtensionPoint extends ContextExtensionPoint {
    @DslExtensionMethod(context = TriggerContext.class)
    public Object githubPullRequest(Runnable closure) throws ANTLRException {
        GhprbTriggerContext context = new GhprbTriggerContext();
        executeInContext(closure, context);
        return new GhprbTrigger(
                Joiner.on("\n").join(context.admins),
                Joiner.on("\n").join(context.userWhitelist),
                Joiner.on("\n").join(context.orgWhitelist),
                context.cron,
                context.triggerPhrase,
                context.onlyTriggerPhrase,
                context.useGitHubHooks,
                context.permitAll,
                context.autoCloseFailedPullRequests,
                null,
                null,
                new ArrayList<GhprbBranch>(),
                context.allowMembersOfWhitelistedOrgsAsAdmin,
                null,
                null,
                null,
                null,
                null,
                context.extensionContext.extensions
        );
    }
}