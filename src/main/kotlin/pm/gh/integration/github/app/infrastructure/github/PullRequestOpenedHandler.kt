package pm.gh.integration.github.app.infrastructure.github

import jakarta.inject.Singleton
import org.kohsuke.github.GHEventPayload.PullRequest
import pm.gh.integration.github.app.infrastructure.kafka.handlers.PullRequestOpenedEventKafkaPublisher
import pm.gh.integration.github.app.infrastructure.kafka.mapper.PullRequestEventMapper.toKafkaEvent
import pm.gh.integration.github.app.service.TitleCompositionService
import io.quarkiverse.githubapp.event.PullRequest.Opened as OnPullRequestOpened

@Singleton
class PullRequestOpenedHandler(
    private val titleCompositionService: TitleCompositionService,
    private val pullRequestOpenedEventKafkaPublisher: PullRequestOpenedEventKafkaPublisher,
) : GithubEventHandler<PullRequest> {

    override fun handle(ghEventPayload: PullRequest) {
        return ghEventPayload.pullRequest.let {
            val titleComposition = titleCompositionService.extractProjectKeyAndTitle(it.title)
            it.toKafkaEvent(titleComposition).also { kafkaEvent ->
                pullRequestOpenedEventKafkaPublisher.publishEvent(kafkaEvent)
            }
        }
    }

    override fun canHandle(eventPayload: PullRequest): Boolean {
        return eventPayload.pullRequest.user.name.contains("bot").not()
    }

    override fun onEvent(@OnPullRequestOpened eventPayload: PullRequest) = handleEvent(eventPayload)
}