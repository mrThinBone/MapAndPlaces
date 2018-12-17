package vinhtv.droid.mapandplaces.mediator

class OperationWrapper<T>(val indicator: Indicator, val data: T?, val e: Exception?) {

    fun loading() = indicator == Indicator.LOADING

    fun success() = indicator == Indicator.SUCCESS

    fun reason(): String = e?.message?: "unknown"

    companion object {
        fun <T> stateLoad(): OperationWrapper<T> {
            return OperationWrapper(Indicator.LOADING, null, null)
        }

        fun <T> success(result: T): OperationWrapper<T> {
            return OperationWrapper(Indicator.SUCCESS, result, null)
        }

        fun <T> failed(e: Exception): OperationWrapper<T> {
            return OperationWrapper(Indicator.FAILED, null, e)
        }
    }

}