package pm.gh.integration.github.app.infrastructure.kafka.handlers

import io.smallrye.reactive.messaging.kafka.Record
import jakarta.inject.Singleton
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter
import org.slf4j.LoggerFactory
import pm.gh.integration.github.app.KafkaTopic
import pm.gh.integration.github.app.input.UpdateTicketReviewersEvent

@Singleton
class UpdateTicketReviewersEventKafkaPublisher(
    @Channel(KafkaTopic.TicketReviewers.UPDATE_CHANNEL)
    val emitter: Emitter<Record<String, ByteArray>>,
) : KafkaPublisher<UpdateTicketReviewersEvent> {

    override fun publishEvent(domainEvent: UpdateTicketReviewersEvent) {
        logger.info(
            "Publishing ticket_reviewers.update event with with payload: \n{}to topic {}",
            domainEvent,
            KafkaTopic.TicketReviewers.UPDATE_TOPIC
        )
        emitter.send(Record.of(domainEvent.titleComposition.ticketIdentifier, domainEvent.toByteArray()))
    }

    companion object {
        private val logger = LoggerFactory.getLogger(UpdateTicketGithubDescriptionEventKafkaPublisher::class.java)
    }
}