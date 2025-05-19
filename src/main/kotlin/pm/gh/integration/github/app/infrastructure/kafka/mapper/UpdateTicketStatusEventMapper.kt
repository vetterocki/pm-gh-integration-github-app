package pm.gh.integration.github.app.application.mapper

import pm.gh.integration.github.app.common.TitleComposition
import pm.gh.integration.github.app.domain.pull_request.UpdateTicketStatusEvent

object UpdateTicketStatusMapper {
    fun TitleComposition.toKafkaEvent(status: String): UpdateTicketStatusEvent {
        return UpdateTicketStatusEvent.newBuilder().also {
            it.status = status
            it.titleComposition = this
        }.build()
    }
}