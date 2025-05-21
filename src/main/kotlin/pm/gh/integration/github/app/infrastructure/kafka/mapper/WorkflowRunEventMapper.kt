package pm.gh.integration.github.app.infrastructure.kafka.mapper

import org.kohsuke.github.GHEventPayload.WorkflowRun
import pm.gh.integration.github.app.application.mapper.TItleCompositionMapper.toProto
import pm.gh.integration.github.app.application.mapper.ActorMapper.toProto
import pm.gh.integration.github.app.domain.pull_request.TitleComposition
import pm.gh.integration.github.app.output.WorkflowRunCompletedEvent

object WorkflowRunEventMapper {
    fun WorkflowRun.toWorkflowRunCompletedEvent(titleComposition: TitleComposition): WorkflowRunCompletedEvent {
        return WorkflowRunCompletedEvent.newBuilder().also {
            it.actor = sender.toProto()
            it.htmlUrl = workflowRun.htmlUrl.toString()
            it.conclusion = workflowRun.conclusion.name
            it.titleComposition = titleComposition.toProto()
            it.repositoryName = repository.name
        }.build()
    }
}