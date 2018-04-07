package me.projects.kendhia.stajkamp18reminder.fragments

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlacePicker

import me.projects.kendhia.stajkamp18reminder.R
import com.google.android.gms.location.Geofence
import io.nlopez.smartlocation.SmartLocation
import io.nlopez.smartlocation.geofencing.model.GeofenceModel
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationManagerCompat


class PlaceReminderFragment : Fragment() {

    private val PLACE_PICKER_REQUEST = 1
    private val placeBuilder : PlacePicker.IntentBuilder by lazy {
        PlacePicker.IntentBuilder()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView =  inflater.inflate(R.layout.fragment_place_reminder, container, false)
        rootView.findViewById<Button>(R.id.pickPlace).setOnClickListener {
            startActivityForResult(placeBuilder.build(activity), PLACE_PICKER_REQUEST)
        }

        return rootView
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val place = PlacePicker.getPlace(data, activity)
                Toast.makeText(activity, "places' lat: ${place.latLng} \n places name: ${place.name}", Toast.LENGTH_LONG).show()
                triggerGeofencing(place.latLng.latitude, place.latLng.longitude)
            }
            else {
                Toast.makeText(activity, "Something wrong happened ! $resultCode", Toast.LENGTH_LONG).show()

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun triggerGeofencing(lat : Double, longitute : Double) {
        val geofence = GeofenceModel.Builder("id_${lat+longitute}")
                .setTransition(Geofence.GEOFENCE_TRANSITION_ENTER)
                .setLatitude(lat)
                .setLongitude(longitute)
                .setRadius(0f)
                .build()
        SmartLocation.with(activity.baseContext).geofencing()
                .add(geofence)
                .start {

                    val channel = NotificationChannel("remindercamp", "Reminder", NotificationManager.IMPORTANCE_DEFAULT)
                    // Register the channel with the system
                    val notificationManager = NotificationManagerCompat.from(activity)

                    val mBuilder = NotificationCompat.Builder(activity, "remindercamp")
                            .setContentTitle("Reminder")
                            .setContentText("You're near place you set the alarm to.")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    notificationManager.notify(123, mBuilder.build())
                }
    }




}
