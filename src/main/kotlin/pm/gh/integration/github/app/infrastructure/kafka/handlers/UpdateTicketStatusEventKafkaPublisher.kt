package pm.gh.integration.github.app.infrastructure.kafka.handlers

import io.smallrye.reactive.messaging.kafka.Record
import jakarta.inject.Singleton
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter
import org.slf4j.LoggerFactory
import pm.gh.integration.github.app.domain.pull_request.UpdateTicketStatusEvent
import pm.gh.integration.github.app.infrastructure.kafka.KafkaTopic

@Singleton
class UpdateTicketStatusEventKafkaPublisher(
    @Channel(KafkaTopic.TicketStatus.UPDATE_CHANNEL)
    val emitter: Emitter<Record<String, UpdateTicketStatusEvent>>,
) : KafkaPublisher<UpdateTicketStatusEvent> {

    override fun publishEvent(domainEvent: UpdateTicketStatusEvent) {
        logger.info(
            "Publishing ticket_status.update event with payload {} to topic {}",
            domainEvent,
            KafkaTopic.TicketStatus.UPDATE_TOPIC
        )
        emitter.send(Record.of(domainEvent.titleComposition.ticketIdentifier, domainEvent))
    }

    companion object {
        private val logger = LoggerFactory.getLogger(UpdateTicketStatusEventKafkaPublisher::class.java)
    }
}