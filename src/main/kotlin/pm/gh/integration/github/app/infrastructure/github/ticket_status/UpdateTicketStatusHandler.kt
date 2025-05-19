package pm.gh.integration.github.app.infrastructure.github.ticket_status

import org.kohsuke.github.GHEventPayload.PullRequest
import pm.gh.integration.github.app.infrastructure.github.GithubEventHandler
import pm.gh.integration.github.app.infrastructure.kafka.handlers.UpdateTicketStatusEventKafkaPublisher
import pm.gh.integration.github.app.infrastructure.kafka.mapper.UpdateTicketStatusMapper.toKafkaEvent
import pm.gh.integration.github.app.service.TitleCompositionService

abstract class UpdateTicketStatusHandler(
    private val titleCompositionService: TitleCompositionService,
    private val updateTicketStatusEventKafkaPublisher: UpdateTicketStatusEventKafkaPublisher,
) : GithubEventHandler<PullRequest> {

    protected abstract val status: String

    override fun handle(ghEventPayload: PullRequest) {
        ghEventPayload.pullRequest.let { pullRequest ->
            titleCompositionService.extractProjectKeyAndTitle(pullRequest.title)
                .run { toKafkaEvent(status) }
                .also { updateTicketStatusEventKafkaPublisher.publishEvent(it) }
        }
    }
}