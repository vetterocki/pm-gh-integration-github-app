package pm.gh.integration.github.app.application.mapper

import org.kohsuke.github.GHPullRequest
import pm.gh.integration.github.app.common.PullRequestStatus
import pm.gh.integration.github.app.common.Status

object PullRequestStatusMapper {
    fun GHPullRequest.toProto(status: Status): PullRequestStatus {
        return PullRequestStatus.newBuilder().also {
            it.status = status
            it.branchRef = head.ref
        }.build()
    }
}