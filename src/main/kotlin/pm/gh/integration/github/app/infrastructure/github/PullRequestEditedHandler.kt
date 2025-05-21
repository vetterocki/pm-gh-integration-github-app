package pm.gh.integration.github.app.infrastructure.github

import org.kohsuke.github.GHEventPayload
import io.quarkiverse.githubapp.event.PullRequest.Edited as OnPullRequestEdited


@jakarta.inject.Singleton
class PullRequestEditedHandler : GithubEventHandler<GHEventPayload.PullRequest> {

    //
    override fun onEvent(@OnPullRequestEdited eventPayload: GHEventPayload.PullRequest) = handleEvent(eventPayload)

    override fun handle(ghEventPayload: GHEventPayload.PullRequest) {

    }

    override fun canHandle(eventPayload: GHEventPayload.PullRequest): Boolean {
        return true
    }
}