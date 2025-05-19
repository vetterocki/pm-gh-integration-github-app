package pm.gh.integration.github.app.domain.pull_request


data class UpdateTicketStatusEvent(
    val status: String,
    val titleComposition: TitleComposition
)