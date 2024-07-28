package com.example.evalutionpractical.Model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Temp(
    val current: Current,
    val forecast: Forecast,
    val location: Location
) : Parcelable{
    @Parcelize
    data class Current(
        val air_quality: AirQuality,
        val cloud: Int,
        val condition: Condition,
        val dewpoint_c: Double,
        val dewpoint_f: Double,
        val feelslike_c: Double,
        val feelslike_f: Double,
        val gust_kph: Double,
        val gust_mph: Double,
        val heatindex_c: Double,
        val heatindex_f: Double,
        val humidity: Int,
        val is_day: Int,
        val last_updated: String,
        val last_updated_epoch: Int,
        val precip_in: Double,
        val precip_mm: Double,
        val pressure_in: Double,
        val pressure_mb: Double,
        val temp_c: Double,
        val temp_f: Double,
        val uv: Double,
        val vis_km: Double,
        val vis_miles: Double,
        val wind_degree: Int,
        val wind_dir: String,
        val wind_kph: Double,
        val wind_mph: Double,
        val windchill_c: Double,
        val windchill_f: Double
    ) : Parcelable

    @Parcelize
    data class Forecast(
        val forecastday: List<Forecastday>
    ) : Parcelable

    @Parcelize
    data class Location(
        val country: String,
        val lat: Double,
        val localtime: String,
        val localtime_epoch: Int,
        val lon: Double,
        val name: String,
        val region: String,
        val tz_id: String
    ) : Parcelable

    @Parcelize
    data class AirQuality(
        val co: Double,
        val gb_defra_index: Int,
        val no2: Double,
        val o3: Double,
        val pm10: Double,
        val pm2_5: Double,
        val so2: Double,
        val us_epa_index: Int
    ) : Parcelable

    @Parcelize
    data class Condition(
        val code: Int,
        val icon: String,
        val text: String
    ) : Parcelable

    @Parcelize
    data class Forecastday(
        val astro: Astro,
        val date: String,
        val date_epoch: Int,
        val day: Day,
        val hour: List<Hour>
    ) : Parcelable

    @Parcelize
    data class Astro(
        val is_moon_up: Int,
        val is_sun_up: Int,
        val moon_illumination: Int,
        val moon_phase: String,
        val moonrise: String,
        val moonset: String,
        val sunrise: String,
        val sunset: String
    ) : Parcelable

    @Parcelize
    data class Day(
        val air_quality: AirQualityX,
        val avghumidity: Int,
        val avgtemp_c: Double,
        val avgtemp_f: Double,
        val avgvis_km: Double,
        val avgvis_miles: Double,
        val condition: Condition,
        val daily_chance_of_rain: Int,
        val daily_chance_of_snow: Int,
        val daily_will_it_rain: Int,
        val daily_will_it_snow: Int,
        val maxtemp_c: Double,
        val maxtemp_f: Double,
        val maxwind_kph: Double,
        val maxwind_mph: Double,
        val mintemp_c: Double,
        val mintemp_f: Double,
        val totalprecip_in: Double,
        val totalprecip_mm: Double,
        val totalsnow_cm: Double,
        val uv: Double
    ) : Parcelable

    @Parcelize
    data class Hour(
        val chance_of_rain: Int,
        val chance_of_snow: Int,
        val cloud: Int,
        val condition: Condition,
        val dewpoint_c: Double,
        val dewpoint_f: Double,
        val feelslike_c: Double,
        val feelslike_f: Double,
        val gust_kph: Double,
        val gust_mph: Double,
        val heatindex_c: Double,
        val heatindex_f: Double,
        val humidity: Int,
        val is_day: Int,
        val precip_in: Double,
        val precip_mm: Double,
        val pressure_in: Double,
        val pressure_mb: Double,
        val snow_cm: Double,
        val temp_c: Double,
        val temp_f: Double,
        val time: String,
        val time_epoch: Int,
        val uv: Double,
        val vis_km: Double,
        val vis_miles: Double,
        val will_it_rain: Int,
        val will_it_snow: Int,
        val wind_degree: Int,
        val wind_dir: String,
        val wind_kph: Double,
        val wind_mph: Double,
        val windchill_c: Double,
        val windchill_f: Double
    ) : Parcelable

    @Parcelize
    data class AirQualityX(
        val aqi_data: String,
        val co: Double,
        val gb_defra_index:Int,
        val no2: Double,
        val o3: Double,
        val pm10: Double,
        val pm2_5: Double,
        val so2: Double,
        val us_epa_index: Int
    ) : Parcelable
}
