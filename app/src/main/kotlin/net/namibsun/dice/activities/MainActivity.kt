/*
Copyright 2015 Hermann Krumrey

This file is part of dice-roller.

dice-roller is an Android app that allows a user to roll a virtual
die. Multiple configurations are supported

dice-roller is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

dice-roller is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with dice-roller. If not, see <http://www.gnu.org/licenses/>.
*/

package net.namibsun.dice.activities

import android.os.Bundle
import android.view.View
import net.namibsun.dice.objects.ClassicDie
import net.namibsun.dice.R
import net.namibsun.dice.helpers.initializeBottomMenuBar
import net.namibsun.dice.helpers.initializeSettingsButton
import net.namibsun.dice.objects.loadTheme

/**
 * The Main Activity of the Application. Shows a single die, a settings button and
 * a bar to switch between modes
 */
class MainActivity : BaseActivity() {

    /**
     * The Die displayed on the activity
     */
    var die: ClassicDie? = null

    /**
     * Initializes the App's Main Activity View.
     * @param savedInstanceState: The Instance Information of the app.
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.main)

        // Get the die views and assign OnClickListeners, as well as initialize the die
        this.die = ClassicDie(
                this,
                this.findViewById(R.id.die),
                loadTheme(this.prefs!!),
                "" + R.id.die
        )

        // Define the OnClickListeners for the menu buttons
        initializeSettingsButton(this)
        initializeBottomMenuBar(this)
        this.findViewById<View>(R.id.single_die_activity).setOnClickListener { }
    }

    /**
     * Loads the theme whenever the Activity resumes, in case these values have changed
     */
    override fun onResume() {
        super.onResume()
        this.die!!.updateTheme(loadTheme(this.prefs!!))
    }
}
