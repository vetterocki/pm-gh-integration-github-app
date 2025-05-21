package pm.gh.integration.github.app.application.service

import jakarta.inject.Singleton
import org.kohsuke.github.GHEventPayload.PullRequest
import pm.gh.integration.github.app.common.Status
import pm.gh.integration.github.app.infrastructure.kafka.handlers.PullRequestStatusEventKafkaPublisher
import pm.gh.integration.github.app.infrastructure.kafka.mapper.PullRequestEventMapper.toPullRequestStatusEvent

@Singleton
class PullRequestStatusService(
    private val titleCompositionService: TitleCompositionService,
    private val pullRequestStatusEventKafkaPublisher: PullRequestStatusEventKafkaPublisher,
) {
    fun publishStatus(ghEventPayload: PullRequest, prStatus: Status) {
        return ghEventPayload.pullRequest.let {
            val titleComposition = titleCompositionService.extractProjectKeyAndTitle(it.title)
            it.toPullRequestStatusEvent(titleComposition, prStatus).also { kafkaEvent ->
                pullRequestStatusEventKafkaPublisher.publishEvent(kafkaEvent)
            }
        }
    }
}