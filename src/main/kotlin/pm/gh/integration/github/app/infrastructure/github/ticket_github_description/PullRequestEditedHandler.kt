package pm.gh.integration.github.app.infrastructure.github.ticket_github_description

import io.quarkiverse.githubapp.event.PullRequest
import jakarta.inject.Singleton
import org.kohsuke.github.GHEventPayload
import pm.gh.integration.github.app.application.service.UpdateTicketGithubDescriptionService
import pm.gh.integration.github.app.infrastructure.github.GithubEventHandler

@Singleton
class PullRequestEditedHandler(
    private val updateTicketGithubDescriptionService: UpdateTicketGithubDescriptionService,
) : GithubEventHandler<GHEventPayload.PullRequest> {

    override fun handle(ghEventPayload: GHEventPayload.PullRequest) {
        updateTicketGithubDescriptionService.publishUpdate(ghEventPayload)
    }

    override fun canHandle(eventPayload: GHEventPayload.PullRequest): Boolean {
        return eventPayload.pullRequest.user.name.contains("bot").not()
    }

    override fun onEvent(@PullRequest.Edited eventPayload: GHEventPayload.PullRequest) = handleEvent(eventPayload)
}