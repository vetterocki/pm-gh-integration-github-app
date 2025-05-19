package pm.gh.integration.github.app.infrastructure.github.ticket_status

import jakarta.inject.Singleton
import org.kohsuke.github.GHEventPayload.PullRequest
import pm.gh.integration.github.app.infrastructure.kafka.handlers.UpdateTicketStatusEventKafkaPublisher
import pm.gh.integration.github.app.service.TitleCompositionService
import io.quarkiverse.githubapp.event.PullRequest.Closed as OnPullRequestClosed


@Singleton
class WaitingForMergeStatusOnPullRequestClosedHandler(
    titleCompositionService: TitleCompositionService,
    updateTicketStatusEventKafkaPublisher: UpdateTicketStatusEventKafkaPublisher,
) : UpdateTicketStatusHandler(
    titleCompositionService,
    updateTicketStatusEventKafkaPublisher
) {
    override val status = "WAITING_FOR_MERGE"

    override fun canHandle(eventPayload: PullRequest): Boolean {
        return eventPayload.pullRequest.run {
            val isDefaultBranch = listOf("main", "master").any { base.ref.contains(it) }
            isDefaultBranch && isMerged
        }
    }

    override fun onEvent(@OnPullRequestClosed eventPayload: PullRequest) = handleEvent(eventPayload)
}