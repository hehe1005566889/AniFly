package ink.flybird.anifly.data.exception

enum class CommonError {
    SearchError,
    UnKnownError
}

class AFCommonError(
    error: CommonError
) : Exception("Common Error -> $error") {
}