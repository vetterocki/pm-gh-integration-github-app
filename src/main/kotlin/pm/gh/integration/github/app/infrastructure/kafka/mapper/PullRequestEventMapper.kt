package pm.gh.integration.github.app.infrastructure.kafka.mapper

import org.kohsuke.github.GHPullRequest
import pm.gh.integration.github.app.application.mapper.PullRequestStatusMapper.toProto
import pm.gh.integration.github.app.application.mapper.TItleCompositionMapper.toProto
import pm.gh.integration.github.app.application.mapper.ActorMapper.toProto
import pm.gh.integration.github.app.common.Status
import pm.gh.integration.github.app.domain.pull_request.TitleComposition
import pm.gh.integration.github.app.output.PullRequestStatusEvent

object PullRequestEventMapper {
    fun GHPullRequest.toPullRequestStatusEvent(
        titleComposition: TitleComposition,
        status: Status,
    ): PullRequestStatusEvent {
        return PullRequestStatusEvent.newBuilder().also {
            it.htmlUrl = htmlUrl.toString()
            it.actor = user.toProto()
            it.titleComposition = titleComposition.toProto()
            it.pullRequestStatus = toProto(status)
            it.pullRequestNumber = number.toString()
            it.repositoryName = repository.name
        }.build()
    }
}