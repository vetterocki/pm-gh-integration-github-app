package pm.gh.integration.github.app.infrastructure.github.pull_request_status

import io.quarkiverse.githubapp.event.PullRequest
import jakarta.inject.Singleton
import org.kohsuke.github.GHEventPayload
import pm.gh.integration.github.app.application.service.PullRequestStatusService
import pm.gh.integration.github.app.common.Status
import pm.gh.integration.github.app.infrastructure.github.GithubEventHandler

@Singleton
class PullRequestReadyForReviewStatusHandler(
    private val pullRequestStatusService: PullRequestStatusService
) : GithubEventHandler<GHEventPayload.PullRequest> {

    override fun handle(ghEventPayload: GHEventPayload.PullRequest) {
        pullRequestStatusService.publishStatus(ghEventPayload, Status.STATUS_OPENED)
    }

    override fun canHandle(eventPayload: GHEventPayload.PullRequest): Boolean {
        return eventPayload.pullRequest.user.name.contains("bot").not()
    }

    override fun onEvent(@PullRequest.ReadyForReview eventPayload: GHEventPayload.PullRequest) = handleEvent(eventPayload)
}