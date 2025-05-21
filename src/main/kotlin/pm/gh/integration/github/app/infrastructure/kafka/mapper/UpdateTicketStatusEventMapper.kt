package pm.gh.integration.github.app.infrastructure.kafka.mapper

import org.kohsuke.github.GHPullRequest
import org.kohsuke.github.GHWorkflowRun
import pm.gh.integration.github.app.application.mapper.TItleCompositionMapper.toProto
import pm.gh.integration.github.app.domain.pull_request.TitleComposition
import pm.gh.integration.github.app.input.UpdateTicketStatusEvent

object UpdateTicketStatusEventMapper {
    fun GHPullRequest.toUpdateTicketStatusEvent(
        ticketStatus: String,
        titleComposition: TitleComposition,
    ): UpdateTicketStatusEvent {
        return UpdateTicketStatusEvent.newBuilder().also {
            it.status = ticketStatus
            it.titleComposition = titleComposition.toProto()
        }.build()
    }

    fun GHWorkflowRun.toUpdateTicketStatusEvent(
        ticketStatus: String,
        titleComposition: TitleComposition,
    ): UpdateTicketStatusEvent {
        return UpdateTicketStatusEvent.newBuilder().also {
            it.status = ticketStatus
            it.titleComposition = titleComposition.toProto()
        }.build()
    }
}