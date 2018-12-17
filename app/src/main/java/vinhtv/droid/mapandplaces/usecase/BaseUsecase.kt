package vinhtv.droid.mapandplaces.usecase

interface BaseUsecase<P, R> {

    fun execute(params: P): R

}