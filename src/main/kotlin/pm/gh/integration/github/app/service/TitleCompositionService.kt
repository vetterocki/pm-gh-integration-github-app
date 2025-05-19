package pm.gh.integration.github.app.service

import jakarta.enterprise.context.ApplicationScoped
import pm.gh.integration.github.app.domain.pull_request.TitleComposition

@ApplicationScoped
class TitleCompositionService {

    fun extractProjectKeyAndTitle(title: String): TitleComposition {
        val regex = Regex("""^([A-Z]+)-([A-Z0-9]+)\s*[:-]\s*(.+)$""", RegexOption.IGNORE_CASE)
        val match = regex.find(title.trim())

        return match?.let {
            val (projectKey, ticketId, summary) = it.destructured
            TitleComposition(
                projectKey = projectKey.uppercase(),
                ticketIdentifier = "$projectKey-$ticketId".uppercase(),
                ticketSummary = summary.trim()
            )
        } ?: TitleComposition(
            projectKey = null,
            ticketIdentifier = null,
            ticketSummary = title.trim()
        )
    }
}