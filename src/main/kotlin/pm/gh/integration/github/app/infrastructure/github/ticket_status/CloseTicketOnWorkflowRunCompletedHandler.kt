package pm.gh.integration.github.app.infrastructure.github.ticket_status

import jakarta.inject.Singleton
import org.kohsuke.github.GHEventPayload.WorkflowRun
import org.kohsuke.github.GHWorkflowRun.Conclusion.SUCCESS
import pm.gh.integration.github.app.application.service.UpdateTicketStatusService
import pm.gh.integration.github.app.infrastructure.github.GithubEventHandler
import io.quarkiverse.githubapp.event.WorkflowRun.Completed as OnWorkflowRunCompleted

@Singleton
class CloseTicketOnWorkflowRunCompletedHandler(
    private val updateTicketStatusService: UpdateTicketStatusService,
) : GithubEventHandler<WorkflowRun> {

    override fun onEvent(@OnWorkflowRunCompleted eventPayload: WorkflowRun) = handleEvent(eventPayload)

    override fun handle(ghEventPayload: WorkflowRun) {
        updateTicketStatusService.publishUpdate(ghEventPayload, ticketStatus = DONE)
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