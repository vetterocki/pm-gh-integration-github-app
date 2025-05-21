package pm.gh.integration.github.app.application.service

import jakarta.inject.Singleton
import org.kohsuke.github.GHEventPayload.PullRequest
import pm.gh.integration.github.app.infrastructure.kafka.handlers.UpdateTicketReviewersEventKafkaPublisher
import pm.gh.integration.github.app.infrastructure.kafka.mapper.UpdateTicketReviewersMapper.toUpdateTicketReviewersEvent
import pm.gh.integration.github.app.input.Action

@Singleton
class UpdateTicketReviewersService(
    private val titleCompositionService: TitleCompositionService,
    private val updateTicketReviewersEventKafkaPublisher: UpdateTicketReviewersEventKafkaPublisher,
) {

    fun publishUpdate(ghEventPayload: PullRequest, action: Action) {
        ghEventPayload.pullRequest.run {
            titleCompositionService.extractProjectKeyAndTitle(title)
                .let { toUpdateTicketReviewersEvent(action, it) }
                .also { updateTicketReviewersEventKafkaPublisher.publishEvent(it) }
        }
    }
}