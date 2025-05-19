package pm.gh.integration.github.app.domain.workflow_run

import pm.gh.integration.github.app.domain.pull_request.TitleComposition

data class WorkflowRunCompletedEvent(
    val actor: WorkflowRunActor,
    val htmlUrl: String,
    val conclusion: String,
    val titleComposition: TitleComposition
)