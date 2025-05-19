package pm.gh.integration.github.app.application.mapper

import org.kohsuke.github.GHPullRequest
import pm.gh.integration.github.app.common.TitleComposition
import pm.gh.integration.github.app.domain.pull_request.PullRequestOpenedEvent

object PullRequestEventMapper {
    fun GHPullRequest.toKafkaEvent(titleComposition: TitleComposition): PullRequestOpenedEvent {
        return PullRequestOpenedEvent.newBuilder().also {
            it.pullRequestBody = body.orEmpty()
            it.actor = user.name
            it.pullRequestNumber = number.toString()
            it.htmlUrl = htmlUrl.toString()
            it.titleComposition = titleComposition
        }.build()
    }
}