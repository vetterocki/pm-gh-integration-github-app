package pm.gh.integration.github.app.application.mapper

import org.kohsuke.github.GHEventPayload.WorkflowRun
import org.kohsuke.github.GHUser
import pm.gh.integration.github.app.common.TitleComposition
import pm.gh.integration.github.app.common.WorkflowRunActor
import pm.gh.integration.github.app.output.WorkflowRunCompletedEvent

object WorkflowRunEventMapper {
    fun WorkflowRun.toKafkaEvent(titleComposition: TitleComposition): WorkflowRunCompletedEvent {
        return WorkflowRunCompletedEvent.newBuilder().also {
            it.actor = sender.toActor()
            it.htmlUrl = workflowRun.htmlUrl.toString()
            it.conclusion = workflowRun.conclusion.name
            it.titleComposition = titleComposition
        }.build()
    }

    fun GHUser.toActor(): WorkflowRunActor {
        return WorkflowRunActor.newBuilder().apply {
            email = email.orEmpty()
            name = name.orEmpty()
            login = login.orEmpty()
        }.build()
    }
}