package pm.gh.integration.github.app.infrastructure.github.pull_request_status

import io.quarkiverse.githubapp.event.PullRequest
import jakarta.inject.Singleton
import org.kohsuke.github.GHEventPayload
import pm.gh.integration.github.app.application.service.PullRequestStatusService
import pm.gh.integration.github.app.common.Status
import pm.gh.integration.github.app.infrastructure.github.GithubEventHandler

@Singleton
class PullRequestOpenedStatusHandler(
    private val pullRequestStatusService: PullRequestStatusService
) : GithubEventHandler<GHEventPayload.PullRequest> {

    override fun handle(ghEventPayload: GHEventPayload.PullRequest) {
        val prStatus = if (ghEventPayload.pullRequest.isDraft) Status.STATUS_DRAFT else Status.STATUS_OPENED
        pullRequestStatusService.publishStatus(ghEventPayload, prStatus)
    }

    override fun canHandle(eventPayload: GHEventPayload.PullRequest): Boolean {
        return eventPayload.pullRequest.user.name.contains("bot").not()
    }

    override fun onEvent(@PullRequest.Opened eventPayload: GHEventPayload.PullRequest) = handleEvent(eventPayload)
}