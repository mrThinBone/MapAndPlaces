package vinhtv.droid.mapandplaces

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import vinhtv.droid.mapandplaces.model.MapPlace

class MapRestaurantAdapter: RecyclerView.Adapter<MapRestaurantAdapter.RestaurantViewHolder>() {

    private val items = ArrayList<MapPlace>()

    fun load(payload: List<MapPlace>?) {
        payload?.let {
            items.addAll(payload)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(root: ViewGroup, type: Int): RestaurantViewHolder {
        val view = LayoutInflater.from(root.context).inflate(R.layout.item_restaurant, root,false)
        return RestaurantViewHolder(view = view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(viewHolder: RestaurantViewHolder, position: Int) {
        viewHolder.bind(items[position])
    }

    class RestaurantViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val image: ImageView = view.findViewById(R.id.vRestaurantImage)
        private val name: TextView = view.findViewById(R.id.vRestaurantName)
        private val phone: TextView = view.findViewById(R.id.vRestaurantPhone)
        private val address: TextView = view.findViewById(R.id.vRestaurantAddress)
        private val rating: TextView = view.findViewById(R.id.vRestaurantRating)
        private val status: TextView = view.findViewById(R.id.vRestaurantStatus)

        fun bind(place: MapPlace) {
            name.text = place.name
            phone.text = place.phone
            address.text = place.address
            rating.text = "${place.rating}"
            status.text = place.status
            place.photoUrl?.let {
                Glide.with(image.context)
                    .load(place.photoUrl)
                    .into(image)
            }
        }
    }
}