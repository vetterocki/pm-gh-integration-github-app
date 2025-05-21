package pm.gh.integration.github.app.infrastructure.kafka.handlers

interface KafkaPublisher<T> {
    fun publishEvent(domainEvent: T)

    companion object {
        const val TYPE_HEADER = "__TypeId__"
    }
}