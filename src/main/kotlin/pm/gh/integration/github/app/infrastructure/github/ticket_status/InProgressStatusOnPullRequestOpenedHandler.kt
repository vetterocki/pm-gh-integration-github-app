package pm.gh.integration.github.app.infrastructure.github.ticket_status

import jakarta.inject.Singleton
import org.kohsuke.github.GHEventPayload.PullRequest
import pm.gh.integration.github.app.application.service.UpdateTicketStatusService
import pm.gh.integration.github.app.common.Status
import pm.gh.integration.github.app.infrastructure.github.GithubEventHandler
import io.quarkiverse.githubapp.event.PullRequest.Opened as OnPullRequestOpened


@Singleton
class InProgressStatusOnPullRequestOpenedHandler(
    private val updateTicketStatusService: UpdateTicketStatusService
) : GithubEventHandler<PullRequest> {

    override fun canHandle(eventPayload: PullRequest): Boolean {
        return eventPayload.pullRequest.user.name.contains("bot").not()
    }

    override fun handle(ghEventPayload: PullRequest) {
        updateTicketStatusService.publishUpdate(ghEventPayload, ticketStatus = IN_PROGRESS)
    }

    override fun onEvent(@OnPullRequestOpened eventPayload: PullRequest) = handleEvent(eventPayload)

    companion object {
        const val IN_PROGRESS: String = "IN PROGRESS"
    }
}