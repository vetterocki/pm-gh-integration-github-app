package pm.gh.integration.github.app.infrastructure.kafka.handlers

import io.smallrye.reactive.messaging.kafka.Record
import jakarta.inject.Singleton
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter
import org.slf4j.LoggerFactory
import pm.gh.integration.github.app.KafkaTopic
import pm.gh.integration.github.app.output.WorkflowRunCompletedEvent

@Singleton
class WorkflowRunCompletedEventKafkaPublisher(
    @Channel(KafkaTopic.WorkflowRun.COMPLETED_CHANNEL)
    val emitter: Emitter<Record<String, ByteArray>>,
) : KafkaPublisher<WorkflowRunCompletedEvent> {

    override fun publishEvent(domainEvent: WorkflowRunCompletedEvent) {
        logger.info(
            "Publishing workflow_run.completed event with payload: \n{}to topic {}",
            domainEvent,
            KafkaTopic.WorkflowRun.COMPLETED_TOPIC
        )
        emitter.send(Record.of(domainEvent.titleComposition.ticketIdentifier, domainEvent.toByteArray()))
    }

    companion object {
        private val logger = LoggerFactory.getLogger(WorkflowRunCompletedEventKafkaPublisher::class.java)
    }
}