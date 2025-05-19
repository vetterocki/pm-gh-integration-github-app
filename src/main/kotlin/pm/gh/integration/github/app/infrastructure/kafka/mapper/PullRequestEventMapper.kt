package pm.gh.integration.github.app.infrastructure.kafka.mapper

import org.kohsuke.github.GHPullRequest
import pm.gh.integration.github.app.domain.pull_request.PullRequestOpenedEvent
import pm.gh.integration.github.app.domain.pull_request.TitleComposition

object PullRequestEventMapper {
    fun GHPullRequest.toKafkaEvent(titleComposition: TitleComposition): PullRequestOpenedEvent {
        return pullRequest.run {
            PullRequestOpenedEvent(
                pullRequestBody = body.orEmpty(),
                actor = user.name,
                pullRequestNumber = number.toString(),
                htmlUrl = htmlUrl.toString(),
                titleComposition = titleComposition
            )
        }
    }
}