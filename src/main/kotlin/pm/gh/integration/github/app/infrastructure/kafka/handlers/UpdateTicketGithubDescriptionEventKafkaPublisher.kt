package pm.gh.integration.github.app.infrastructure.kafka.handlers

import io.smallrye.reactive.messaging.kafka.Record
import jakarta.inject.Singleton
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter
import org.slf4j.LoggerFactory
import pm.gh.integration.github.app.KafkaTopic
import pm.gh.integration.github.app.input.UpdateTicketGithubDescriptionEvent

@Singleton
class UpdateTicketGithubDescriptionEventKafkaPublisher(
    @Channel(KafkaTopic.TicketGithubDescription.UPDATE_CHANNEL)
    val emitter: Emitter<Record<String, ByteArray>>,
) : KafkaPublisher<UpdateTicketGithubDescriptionEvent> {

    override fun publishEvent(domainEvent: UpdateTicketGithubDescriptionEvent) {
        logger.info(
            "Publishing ticket_github_description.update event with with payload: \n{}to topic {}",
            domainEvent,
            KafkaTopic.TicketGithubDescription.UPDATE_TOPIC
        )
        emitter.send(Record.of(domainEvent.titleComposition.ticketIdentifier, domainEvent.toByteArray()))
    }

    companion object {
        private val logger = LoggerFactory.getLogger(UpdateTicketGithubDescriptionEventKafkaPublisher::class.java)
    }
}