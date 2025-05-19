package pm.gh.integration.github.app.infrastructure.kafka.mapper

import org.kohsuke.github.GHEventPayload.WorkflowRun
import org.kohsuke.github.GHUser
import pm.gh.integration.github.app.domain.pull_request.TitleComposition
import pm.gh.integration.github.app.domain.workflow_run.WorkflowRunActor
import pm.gh.integration.github.app.domain.workflow_run.WorkflowRunCompletedEvent

object WorkflowRunEventMapper {
    fun WorkflowRun.toKafkaEvent(titleComposition: TitleComposition): WorkflowRunCompletedEvent {
        return workflowRun.run {
            WorkflowRunCompletedEvent(
                actor = sender.toActor(),
                htmlUrl = htmlUrl.toString(),
                conclusion = conclusion.name,
                titleComposition = titleComposition
            )
        }
    }

    fun GHUser.toActor(): WorkflowRunActor {
        return WorkflowRunActor(
            email = email.orEmpty(),
            name = name.orEmpty(),
            login = login.orEmpty()
        )
    }
}