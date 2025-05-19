package pm.gh.integration.github.app.infrastructure.github.ticket_status

import jakarta.inject.Singleton
import org.kohsuke.github.GHEventPayload.PullRequest
import pm.gh.integration.github.app.infrastructure.kafka.handlers.UpdateTicketStatusEventKafkaPublisher
import pm.gh.integration.github.app.service.TitleCompositionService
import io.quarkiverse.githubapp.event.PullRequest.Labeled as OnPullRequestLabeled


@Singleton
class WaitingForReviewStatusOnReadyLabelHandler(
    titleCompositionService: TitleCompositionService,
    updateTicketStatusEventKafkaPublisher: UpdateTicketStatusEventKafkaPublisher,
) : UpdateTicketStatusHandler(
    titleCompositionService,
    updateTicketStatusEventKafkaPublisher
) {
    override val status = "WAITING_FOR_REVIEW"

    override fun canHandle(eventPayload: PullRequest): Boolean {
        return eventPayload.label.name.lowercase().contains("ready")
    }

    override fun onEvent(@OnPullRequestLabeled eventPayload: PullRequest) = handleEvent(eventPayload)
}