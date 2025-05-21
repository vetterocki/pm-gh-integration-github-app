package pm.gh.integration.github.app.application.mapper

import pm.gh.integration.github.app.common.TitleComposition as TitleCompositionProto
import pm.gh.integration.github.app.domain.pull_request.TitleComposition

object TItleCompositionMapper {

    fun TitleComposition.toProto(): TitleCompositionProto {
        return TitleCompositionProto.newBuilder().also {
            it.projectKey = projectKey.orEmpty()
            it.ticketSummary = ticketSummary
            it.ticketIdentifier = ticketIdentifier.orEmpty()
        }.build()
    }
}