package vinhtv.droid.mapandplaces

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

/**
 * detect when fling gesture on recycler view is finished and return current visible item position
 */
object ScrollListener {

    fun scrollStateChangeObservable(recyclerView: RecyclerView): Observable<Int> = Observable.create<Int> {emitter ->
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val visiblePos =  (recyclerView.layoutManager as LinearLayoutManager)
                        .findFirstCompletelyVisibleItemPosition()
                    emitter.onNext(visiblePos)
                }
            }
        })
    }.debounce(300, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread())

}