package pm.gh.integration.github.app.infrastructure.kafka.handlers

import io.smallrye.reactive.messaging.kafka.Record
import jakarta.inject.Singleton
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter
import org.slf4j.LoggerFactory
import pm.gh.integration.github.app.KafkaTopic
import pm.gh.integration.github.app.input.UpdateTicketStatusEvent

@Singleton
class UpdateTicketStatusEventKafkaPublisher(
    @Channel(KafkaTopic.TicketStatus.UPDATE_CHANNEL)
    val emitter: Emitter<Record<String, ByteArray>>,
) : KafkaPublisher<UpdateTicketStatusEvent> {

    override fun publishEvent(domainEvent: UpdateTicketStatusEvent) {
        logger.info(
            "Publishing ticket_status.update event with with payload: \n{}to topic {}",
            domainEvent,
            KafkaTopic.TicketStatus.UPDATE_TOPIC
        )
        emitter.send(Record.of(domainEvent.titleComposition.ticketIdentifier, domainEvent.toByteArray()))
    }

    companion object {
        private val logger = LoggerFactory.getLogger(UpdateTicketStatusEventKafkaPublisher::class.java)
    }
}