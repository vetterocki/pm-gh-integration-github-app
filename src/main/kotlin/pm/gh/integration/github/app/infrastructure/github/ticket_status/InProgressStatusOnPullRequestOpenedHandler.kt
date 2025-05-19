package pm.gh.integration.github.app.infrastructure.github.ticket_status

import jakarta.inject.Singleton
import org.kohsuke.github.GHEventPayload.PullRequest
import pm.gh.integration.github.app.infrastructure.kafka.handlers.UpdateTicketStatusEventKafkaPublisher
import pm.gh.integration.github.app.service.TitleCompositionService
import io.quarkiverse.githubapp.event.PullRequest.Opened as OnPullRequestOpened


@Singleton
class InProgressStatusOnPullRequestOpenedHandler(
    titleCompositionService: TitleCompositionService,
    updateTicketStatusEventKafkaPublisher: UpdateTicketStatusEventKafkaPublisher,
) : UpdateTicketStatusHandler(
    titleCompositionService,
    updateTicketStatusEventKafkaPublisher
) {
    override val status: String = "IN_PROGRESS"

    override fun canHandle(eventPayload: PullRequest): Boolean {
        return eventPayload.pullRequest.user.name.contains("bot").not()
    }

    override fun onEvent(@OnPullRequestOpened eventPayload: PullRequest) = handleEvent(eventPayload)
}