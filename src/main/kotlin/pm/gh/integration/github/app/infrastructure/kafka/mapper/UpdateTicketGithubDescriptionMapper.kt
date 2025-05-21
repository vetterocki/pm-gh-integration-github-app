package pm.gh.integration.github.app.infrastructure.kafka.mapper

import org.kohsuke.github.GHPullRequest
import pm.gh.integration.github.app.application.mapper.TItleCompositionMapper.toProto
import pm.gh.integration.github.app.domain.pull_request.TitleComposition
import pm.gh.integration.github.app.input.UpdateTicketGithubDescriptionEvent

object UpdateTicketGithubDescriptionMapper {
    fun GHPullRequest.toUpdateTicketGithubDescriptionEvent(titleComposition: TitleComposition): UpdateTicketGithubDescriptionEvent {
        return UpdateTicketGithubDescriptionEvent.newBuilder().also {
            it.description = body.orEmpty()
            it.titleComposition = titleComposition.toProto()
        }.build()
    }
}