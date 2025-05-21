package pm.gh.integration.github.app.infrastructure.github.pull_request_status

import io.quarkiverse.githubapp.event.PullRequest
import jakarta.inject.Singleton
import org.kohsuke.github.GHEventPayload
import pm.gh.integration.github.app.application.service.PullRequestStatusService
import pm.gh.integration.github.app.common.Status
import pm.gh.integration.github.app.infrastructure.github.GithubEventHandler


@Singleton
class PullRequestClosedStatusHandler(
    private val pullRequestStatusService: PullRequestStatusService
) : GithubEventHandler<GHEventPayload.PullRequest> {

    override fun handle(ghEventPayload: GHEventPayload.PullRequest) {
        val prStatus = if (ghEventPayload.pullRequest.isMerged) Status.STATUS_MERGED else Status.STATUS_CLOSED
        pullRequestStatusService.publishStatus(ghEventPayload, prStatus)
    }

    override fun canHandle(eventPayload: GHEventPayload.PullRequest): Boolean {
        return eventPayload.pullRequest.user.name.contains("bot").not()
    }

    override fun onEvent(@PullRequest.Closed eventPayload: GHEventPayload.PullRequest) = handleEvent(eventPayload)
}