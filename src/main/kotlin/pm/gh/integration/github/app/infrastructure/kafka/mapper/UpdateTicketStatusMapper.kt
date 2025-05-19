package pm.gh.integration.github.app.infrastructure.kafka.mapper

import pm.gh.integration.github.app.domain.pull_request.TitleComposition
import pm.gh.integration.github.app.domain.pull_request.UpdateTicketStatusEvent

object UpdateTicketStatusMapper {
    fun TitleComposition.toKafkaEvent(status: String): UpdateTicketStatusEvent {
        return UpdateTicketStatusEvent(status = status, titleComposition = this)
    }
}