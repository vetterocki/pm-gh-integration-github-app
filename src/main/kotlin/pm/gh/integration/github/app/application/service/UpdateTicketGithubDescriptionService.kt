package pm.gh.integration.github.app.application.service

import jakarta.inject.Singleton
import org.kohsuke.github.GHEventPayload.PullRequest
import pm.gh.integration.github.app.infrastructure.kafka.handlers.UpdateTicketGithubDescriptionEventKafkaPublisher
import pm.gh.integration.github.app.infrastructure.kafka.mapper.UpdateTicketGithubDescriptionMapper.toUpdateTicketGithubDescriptionEvent

@Singleton
class UpdateTicketGithubDescriptionService(
    private val titleCompositionService: TitleCompositionService,
    private val updateTicketGithubDescriptionEventKafkaPublisher: UpdateTicketGithubDescriptionEventKafkaPublisher,
) {

    fun publishUpdate(ghEventPayload: PullRequest) {
        ghEventPayload.pullRequest.run {
            titleCompositionService.extractProjectKeyAndTitle(title)
                .let { toUpdateTicketGithubDescriptionEvent(it) }
                .also { updateTicketGithubDescriptionEventKafkaPublisher.publishEvent(it) }
        }
    }
}