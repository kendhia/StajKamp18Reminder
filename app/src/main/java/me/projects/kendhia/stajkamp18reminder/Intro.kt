package me.projects.kendhia.stajkamp18reminder

import android.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import me.projects.kendhia.stajkamp18reminder.CustomeViews.TimePicker
import me.projects.kendhia.stajkamp18reminder.fragments.PlaceReminderFragment

class Intro : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        val manager = fragmentManager
        val transaction = manager.beginTransaction()
        val placeFragment : Fragment = PlaceReminderFragment()
        transaction.add(R.id.containerPlace, placeFragment, "PlaceReminderFragment")
        transaction.addToBackStack(null)
        transaction.commit()

        findViewById<Button>(R.id.buttonTime).setOnClickListener {
            val timeFragment  = TimePicker()
            timeFragment.show(supportFragmentManager, "TimePicker")
        }
    }
}
