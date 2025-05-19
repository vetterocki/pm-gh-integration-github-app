package pm.gh.integration.github.app.domain.pull_request

data class PullRequestOpenedEvent(
    val pullRequestBody: String,
    val actor: String,
    val pullRequestNumber: String,
    val htmlUrl: String,
    val titleComposition: TitleComposition
)