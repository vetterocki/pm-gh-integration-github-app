package pm.gh.integration.github.app.infrastructure.github

import org.kohsuke.github.GHEventPayload

interface GithubEventHandler<P> where P : GHEventPayload {
    fun onEvent(eventPayload: P)
    fun handle(ghEventPayload: P)
    fun canHandle(eventPayload: P): Boolean

    fun GithubEventHandler<P>.handleEvent(eventPayload: P) {
        eventPayload.takeIf { this.canHandle(it) }?.let { approvedEvent ->
            this.handle(approvedEvent)
        }
    }
}