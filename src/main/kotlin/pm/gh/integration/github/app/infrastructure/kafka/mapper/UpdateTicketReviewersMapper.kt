package pm.gh.integration.github.app.infrastructure.kafka.mapper

import org.kohsuke.github.GHPullRequest
import pm.gh.integration.github.app.application.mapper.ActorMapper.toProto
import pm.gh.integration.github.app.application.mapper.TItleCompositionMapper.toProto
import pm.gh.integration.github.app.domain.pull_request.TitleComposition
import pm.gh.integration.github.app.input.Action
import pm.gh.integration.github.app.input.UpdateTicketReviewersEvent

object UpdateTicketReviewersMapper {
    fun GHPullRequest.toUpdateTicketReviewersEvent(
        action: Action,
        titleComposition: TitleComposition,
    ): UpdateTicketReviewersEvent {
        return UpdateTicketReviewersEvent.newBuilder().also {
            it.action = action
            it.reviewersList.addAll(requestedReviewers.map { ghUser -> ghUser.toProto() })
            it.titleComposition = titleComposition.toProto()
        }.build()
    }
}