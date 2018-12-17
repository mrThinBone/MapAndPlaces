package vinhtv.droid.mapandplaces.datasource.deserializer

import com.google.android.gms.maps.model.LatLng
import com.google.gson.*
import vinhtv.droid.mapandplaces.BuildConfig
import vinhtv.droid.mapandplaces.model.MapPlace
import java.lang.reflect.Type

// https://medium.com/@andersonk/retrofit-2-custom-deserializer-for-unwrapping-properties-8cb8b2ec901e
class PlaceDeserializer: JsonDeserializer<PlaceResponseDataWrapper> {

    override fun deserialize(jsonElement: JsonElement, typeOfT: Type, context: JsonDeserializationContext)
            : PlaceResponseDataWrapper {

        val result = ArrayList<MapPlace>()
        if(jsonElement.isJsonArray) {
            val images = images()
            var index = 0
            val jsonArray:JsonArray = jsonElement.asJsonArray
            for (json in jsonArray) {
                val place = parse(json = json.asJsonObject, photoUrl = images[index])
                result.add(place)
                index++
                if(index == images.size) index = 0
            }
        } else {
            result.add(parse(json = jsonElement.asJsonObject, detail = true))
        }
        return PlaceResponseDataWrapper(result)
    }

    private fun parse(json: JsonObject, detail: Boolean = false, photoUrl: String= ""): MapPlace {
        val placeId = json.get("place_id").asString

        if(detail) {
            val address = json.get("formatted_address").asString
            val phone = json.get("formatted_phone_number").asString
            val rating = json.get("rating").asFloat
            val opening = json.get("opening_hours").asJsonObject.get("open_now").asBoolean
            return MapPlace(
                placeId,
                rating = rating,
                address = address,
                phone = phone,
                status = if(opening) "Opening" else "Closed",
                contentIncrement = 1
            )
        } else {
            val id = json.get("id").asString
            val name = json.get("name").asString
            val latLng = latLngFromJson(json.get("geometry"))
//            val photoUrl = photoFromJson(json.get("photos"))
            return MapPlace(
                placeId,
                id = id,
                name = name,
                latLng = latLng,
                photoUrl = photoUrl
            )
        }
    }

    private fun photoFromJson(jsonElement: JsonElement): String {
        val jsonArray = jsonElement.asJsonArray
        val jsonPhoto = (if(jsonArray.size()> 0) jsonArray[0].asJsonObject else null) ?: return ""
        val photoReference: String = jsonPhoto.get("photo_reference").asString
        return "https://maps.googleapis.com/maps/api/place/photo?key=${BuildConfig.API_KEY}&photoreference=$photoReference"
    }

    private fun latLngFromJson(jsonElement: JsonElement): LatLng {
        val location = jsonElement.asJsonObject.get("location").asJsonObject
        return LatLng(location["lat"].asDouble, location["lng"].asDouble)
    }

    private fun getOrNull(json: JsonObject, key: String): JsonElement? {
        return if(json.has(key)) json[key] else null
    }

    private fun images() = arrayOf(
        "https://www.omnihotels.com/-/media/images/hotels/bospar/restaurants/bospar-omni-parker-house-parkers-restaurant-1170.jpg",
        "https://file.videopolis.com/D/9dc9f4ba-0b2d-4cbb-979f-fee7be8a4198/8485.11521.brussels.the-hotel-brussels.amenity.restaurant-AD3WAP2L-13000-853x480.jpeg",
        "https://libertyhouserestaurant.com/wp-content/uploads/sites/13/2016/11/slide-restaurant.jpg",
        "https://media-cdn.tripadvisor.com/media/photo-s/0e/cc/0a/dc/restaurant-chocolat.jpg",
        "https://images.pexels.com/photos/9315/menu-restaurant-france-eating-9315.jpg?cs=srgb&dl=chairs-cutlery-fork-9315.jpg&fm=jpg",
        "https://media-cdn.tripadvisor.com/media/photo-s/0a/7b/7b/c6/restaurant-view.jpg",
        "https://www.thechediandermatt.com/images/content/mood/Big/The-Chedi-Andermatt-RESTAURANT-Main-Dining-03.jpg",
        "https://www.theriverside.co.uk/images/Inside-Restaurant.jpg",
        "http://d2e5ushqwiltxm.cloudfront.net/wp-content/uploads/sites/29/2016/09/06034825/01-Beach-Club-Restaurant.jpg",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTN8Xr88Tm6k-uR0lRg4kAu_oAym_mk2Kx5WyqhPuzf7YDdsJrg",
        "https://cdn.vox-cdn.com/uploads/chorus_image/image/57557049/Restaurant_View_2.0.jpg",
        "https://d2ile4x3f22snf.cloudfront.net/wp-content/uploads/sites/122/2017/02/08072912/manavabeachresortmoorea_restaurant_11.jpg",
        "https://www.ca-beachhotel.com/wp-content/uploads/2016/03/CABH-SHOOT-MAI-1061_626x300.jpg",
        "http://www.thelonestar.com/images/suites/viewfromRestauranttothebeach.jpg",
        "https://www.leopardbeachresort.com/images/category/460/dining-header_.jpg",
        "https://www.irishexaminer.com/remote/media.central.ie/media/images/t/TheIvyDublin_large.jpg?width=648&s=ie-861367",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTO6UznAt13GQkSbHhsa3ilV2iTQN-Mlq1lhKkdSGK2B1hJ2W6H",
        "https://www.stratospherehotel.com/var/ezwebin_site/storage/images/media/gallery-images/food-and-drink/tow/top-of-the-world-restaurant/22159-7-eng-GB/Top-of-the-World-Restaurant.jpg"
    )
}