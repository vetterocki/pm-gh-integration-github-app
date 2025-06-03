package pm.gh.integration.github.app.infrastructure.github.ticket_status

import jakarta.inject.Singleton
import org.kohsuke.github.GHEventPayload.PullRequest
import pm.gh.integration.github.app.application.service.UpdateTicketStatusService
import pm.gh.integration.github.app.infrastructure.github.GithubEventHandler
import io.quarkiverse.githubapp.event.PullRequest.Closed as OnPullRequestClosed


@Singleton
class WaitingForMergeStatusOnPullRequestClosedHandler(
    private val updateTicketStatusService: UpdateTicketStatusService,
) : GithubEventHandler<PullRequest> {
    override fun canHandle(eventPayload: PullRequest): Boolean {
        return eventPayload.pullRequest.run {
            val isDefaultBranch = listOf("main", "master").any { base.ref.contains(it) }
            isDefaultBranch && isMerged
        }
    }

    override fun handle(ghEventPayload: PullRequest) {
        updateTicketStatusService.publishUpdate(ghEventPayload, ticketStatus = WAITING_FOR_MERGE)
    }

    override fun onEvent(@OnPullRequestClosed eventPayload: PullRequest) = handleEvent(eventPayload)

    companion object {
        const val WAITING_FOR_MERGE: String = "WAITING FOR MERGE"
    }
}