package pm.gh.integration.github.app.infrastructure.github.ticket_status

import jakarta.inject.Singleton
import org.kohsuke.github.GHEventPayload.PullRequest
import pm.gh.integration.github.app.application.service.UpdateTicketStatusService
import pm.gh.integration.github.app.common.Status
import pm.gh.integration.github.app.infrastructure.github.GithubEventHandler
import io.quarkiverse.githubapp.event.PullRequest.Labeled as OnPullRequestLabeled


@Singleton
class WaitingForReviewStatusOnReadyLabelHandler(
    private val updateTicketStatusService: UpdateTicketStatusService,
) : GithubEventHandler<PullRequest> {

    override fun canHandle(eventPayload: PullRequest): Boolean {
        return eventPayload.label.name.lowercase().contains("ready")
    }

    override fun handle(ghEventPayload: PullRequest) {
        updateTicketStatusService.publishUpdate(ghEventPayload, ticketStatus = WAITING_FOR_REVIEW)
    }

    override fun onEvent(@OnPullRequestLabeled eventPayload: PullRequest) = handleEvent(eventPayload)

    companion object {
        const val WAITING_FOR_REVIEW: String = "WAITING FOR REVIEW"
    }
}