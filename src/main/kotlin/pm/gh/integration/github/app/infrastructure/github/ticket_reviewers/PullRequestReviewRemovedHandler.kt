package pm.gh.integration.github.app.infrastructure.github.ticket_reviewers

import io.quarkiverse.githubapp.event.PullRequest.ReviewRequestRemoved as OnReviewersRemoved
import jakarta.inject.Singleton
import org.kohsuke.github.GHEventPayload
import pm.gh.integration.github.app.application.service.UpdateTicketReviewersService
import pm.gh.integration.github.app.infrastructure.github.GithubEventHandler
import pm.gh.integration.github.app.input.Action

@Singleton
class PullRequestReviewRemovedHandler(
    private val updateTicketReviewersService: UpdateTicketReviewersService,
) : GithubEventHandler<GHEventPayload.PullRequest> {

    override fun handle(ghEventPayload: GHEventPayload.PullRequest) {
        updateTicketReviewersService.publishUpdate(ghEventPayload, Action.ACTION_REMOVED)
    }

    override fun canHandle(eventPayload: GHEventPayload.PullRequest): Boolean {
        return eventPayload.pullRequest.user.name.contains("bot").not()
    }

    override fun onEvent(@OnReviewersRemoved eventPayload: GHEventPayload.PullRequest) = handleEvent(eventPayload)
}