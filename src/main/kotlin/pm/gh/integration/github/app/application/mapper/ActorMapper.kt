package pm.gh.integration.github.app.application.mapper

import org.kohsuke.github.GHUser
import pm.gh.integration.github.app.common.Actor

object ActorMapper {
    fun GHUser.toProto(): Actor {
        return Actor.newBuilder().also {
            it.email = email.orEmpty()
            it.name = name.orEmpty()
            it.login = login.orEmpty()
        }.build()
    }
}