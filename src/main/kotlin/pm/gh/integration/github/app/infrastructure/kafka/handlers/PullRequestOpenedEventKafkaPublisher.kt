package pm.gh.integration.github.app.infrastructure.kafka.handlers

import io.smallrye.reactive.messaging.kafka.Record
import jakarta.inject.Singleton
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter
import org.slf4j.LoggerFactory
import pm.gh.integration.github.app.domain.pull_request.PullRequestOpenedEvent
import pm.gh.integration.github.app.infrastructure.kafka.KafkaTopic
import pm.gh.integration.github.app.infrastructure.kafka.KafkaTopic.PullRequest.OPENED_TOPIC


@Singleton
class PullRequestOpenedEventKafkaPublisher(
    @Channel(KafkaTopic.PullRequest.OPENED_CHANNEL)
    val emitter: Emitter<Record<String, PullRequestOpenedEvent>>,
) : KafkaPublisher<PullRequestOpenedEvent> {

    override fun publishEvent(domainEvent: PullRequestOpenedEvent) {
        logger.info(
            "Publishing pull_request.opened.ticket.create event with payload {} to topic {}",
            domainEvent,
            OPENED_TOPIC
        )
        emitter.send(Record.of(domainEvent.pullRequestNumber, domainEvent))
    }

    companion object {
        private val logger = LoggerFactory.getLogger(PullRequestOpenedEventKafkaPublisher::class.java)
    }
}