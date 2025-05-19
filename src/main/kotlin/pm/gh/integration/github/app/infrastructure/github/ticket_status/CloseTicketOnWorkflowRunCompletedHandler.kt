package pm.gh.integration.github.app.infrastructure.github.ticket_status

import jakarta.inject.Singleton
import org.kohsuke.github.GHEventPayload.WorkflowRun
import org.kohsuke.github.GHWorkflowRun.Conclusion.SUCCESS
import pm.gh.integration.github.app.infrastructure.github.GithubEventHandler
import pm.gh.integration.github.app.infrastructure.kafka.handlers.UpdateTicketStatusEventKafkaPublisher
import pm.gh.integration.github.app.infrastructure.kafka.mapper.UpdateTicketStatusMapper.toKafkaEvent
import pm.gh.integration.github.app.service.TitleCompositionService
import io.quarkiverse.githubapp.event.WorkflowRun.Completed as OnWorkflowRunCompleted

@Singleton
class CloseTicketOnWorkflowRunCompletedHandler(
    private val titleCompositionService: TitleCompositionService,
    private val updateTicketStatusEventKafkaPublisher: UpdateTicketStatusEventKafkaPublisher,
) : GithubEventHandler<WorkflowRun> {

    override fun onEvent(@OnWorkflowRunCompleted eventPayload: WorkflowRun) = handleEvent(eventPayload)

    override fun handle(ghEventPayload: WorkflowRun) {
        return ghEventPayload.workflowRun.let { workflowRun ->
            titleCompositionService.extractProjectKeyAndTitle(workflowRun.displayTitle)
                .run { toKafkaEvent(DONE) }
                .also { updateTicketStatusEventKafkaPublisher.publishEvent(it) }
        }
    }

    override fun canHandle(eventPayload: WorkflowRun): Boolean {
        return eventPayload.run {
            val isDefaultBranch = listOf("main", "master").any { workflowRun.headBranch.contains(it) }
            val isBuildWorkflow = workflow.path.contains("build")
            isDefaultBranch && isBuildWorkflow && workflowRun.conclusion == SUCCESS
        }
    }

    companion object {
        private const val DONE = "DONE"
    }
}