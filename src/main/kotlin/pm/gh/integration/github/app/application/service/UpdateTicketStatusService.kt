package pm.gh.integration.github.app.application.service

import jakarta.inject.Singleton
import org.kohsuke.github.GHEventPayload.PullRequest
import org.kohsuke.github.GHEventPayload.WorkflowRun
import pm.gh.integration.github.app.infrastructure.kafka.handlers.UpdateTicketStatusEventKafkaPublisher
import pm.gh.integration.github.app.infrastructure.kafka.mapper.UpdateTicketStatusEventMapper.toUpdateTicketStatusEvent

@Singleton
class UpdateTicketStatusService(
    private val titleCompositionService: TitleCompositionService,
    private val updateTicketStatusEventKafkaPublisher: UpdateTicketStatusEventKafkaPublisher,
) {

    fun publishUpdate(ghEventPayload: PullRequest, ticketStatus: String) {
        ghEventPayload.pullRequest.run {
            titleCompositionService.extractProjectKeyAndTitle(title)
                .let { toUpdateTicketStatusEvent(ticketStatus, it) }
                .also { updateTicketStatusEventKafkaPublisher.publishEvent(it) }
        }
    }

    fun publishUpdate(ghEventPayload: WorkflowRun, ticketStatus: String) {
        return ghEventPayload.workflowRun.run {
            titleCompositionService.extractProjectKeyAndTitle(displayTitle)
                .let { toUpdateTicketStatusEvent(ticketStatus, it) }
                .also { updateTicketStatusEventKafkaPublisher.publishEvent(it) }
        }
    }
}