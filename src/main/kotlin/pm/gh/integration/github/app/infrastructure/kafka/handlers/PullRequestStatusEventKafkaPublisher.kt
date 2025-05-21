package pm.gh.integration.github.app.infrastructure.kafka.handlers

import io.smallrye.reactive.messaging.kafka.Record
import jakarta.inject.Singleton
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter
import org.slf4j.LoggerFactory
import pm.gh.integration.github.app.KafkaTopic
import pm.gh.integration.github.app.KafkaTopic.PullRequest.STATUS_TOPIC
import pm.gh.integration.github.app.output.PullRequestStatusEvent


@Singleton
class PullRequestStatusEventKafkaPublisher(
    @Channel(KafkaTopic.PullRequest.STATUS_CHANNEL)
    val emitter: Emitter<Record<String, ByteArray>>,
) : KafkaPublisher<PullRequestStatusEvent> {

    override fun publishEvent(domainEvent: PullRequestStatusEvent) {
        logger.info(
            "Publishing pull_request.status event with payload\n{}to topic {}",
            domainEvent,
            STATUS_TOPIC
        )
        emitter.send(Record.of(domainEvent.htmlUrl, domainEvent.toByteArray()))
    }

    companion object {
        private val logger = LoggerFactory.getLogger(PullRequestStatusEventKafkaPublisher::class.java)
    }
}