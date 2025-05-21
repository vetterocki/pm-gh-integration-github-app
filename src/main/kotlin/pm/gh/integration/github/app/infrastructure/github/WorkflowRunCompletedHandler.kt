package pm.gh.integration.github.app.infrastructure.github

import jakarta.inject.Singleton
import org.kohsuke.github.GHEventPayload.WorkflowRun
import org.kohsuke.github.GHWorkflowRun.Status.COMPLETED
import pm.gh.integration.github.app.infrastructure.kafka.handlers.WorkflowRunCompletedEventKafkaPublisher
import pm.gh.integration.github.app.infrastructure.kafka.mapper.WorkflowRunEventMapper.toWorkflowRunCompletedEvent
import pm.gh.integration.github.app.application.service.TitleCompositionService
import io.quarkiverse.githubapp.event.WorkflowRun.Completed as OnWorkflowRunCompleted

@Singleton
class WorkflowRunCompletedHandler(
    private val workflowRunCompletedEventKafkaPublisher: WorkflowRunCompletedEventKafkaPublisher,
    private val titleCompositionService: TitleCompositionService,
) : GithubEventHandler<WorkflowRun> {

    override fun onEvent(@OnWorkflowRunCompleted eventPayload: WorkflowRun) = handleEvent(eventPayload)

    override fun handle(ghEventPayload: WorkflowRun) {
        return ghEventPayload.let {
            it.workflowRun.let { workflowRun ->
                val titleComposition = titleCompositionService.extractProjectKeyAndTitle(workflowRun.displayTitle)
                it.toWorkflowRunCompletedEvent(titleComposition).also { kafkaEvent ->
                    workflowRunCompletedEventKafkaPublisher.publishEvent(kafkaEvent)
                }
            }
        }
    }

    override fun canHandle(eventPayload: WorkflowRun): Boolean {
        return eventPayload.run {
            val isDefaultBranch = listOf("main", "master").any { workflowRun.headBranch.contains(it) }
            val isBuildWorkflow = workflow.path.contains("build")
            isDefaultBranch && isBuildWorkflow && workflowRun.status == COMPLETED
        }
    }
}
