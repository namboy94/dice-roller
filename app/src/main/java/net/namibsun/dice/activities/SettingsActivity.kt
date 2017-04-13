/*
Copyright 2015-2017 Hermann Krumrey

This file is part of android-dice.

    android-dice is an Android app that allows a user to roll a virtual
    die. Multiple configurations are supported

    android-dice is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    android-dice is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with android-dice. If not, see <http://www.gnu.org/licenses/>.
*/

package net.namibsun.dice.activities

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.CheckBox
import android.widget.RadioGroup
import net.namibsun.dice.R
import net.namibsun.dice.objects.ThemeStyles

/**
 * The Settings Activity for the Android App. It enables the user to define certain settings
 * to his/her liking.
 */
class SettingsActivity : BaseActivity() {

    /**
     * A shared preferences object used to store and load settings
     */
    var prefs: SharedPreferences? = null

    /**
     * The Checkbox for the vibration option
     */
    var vibrateCheck : CheckBox? = null

    /**
     * The Checkbox for the Wiggle Animation option
     */
    var wiggleAnimationCheck : CheckBox? = null

    /**
     * The Checkbox for the Change Animation option
     */
    var changeAnimationCheck : CheckBox? = null

    /**
     * The Radiobutton Group for the style options
     */
    var styleGroup : RadioGroup? = null

    /**
     * Initializes the layout and the OnClickListeners
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        this.setContentView(net.namibsun.dice.R.layout.settings)
        this.prefs = this.getSharedPreferences("SHARED_PREFS", android.content.Context.MODE_PRIVATE)

        this.findViewById(net.namibsun.dice.R.id.ok_button).setOnClickListener {
            this.storeAndReturn()
        }

        this.vibrateCheck =
                this.findViewById(net.namibsun.dice.R.id.vibrate_check) as CheckBox
        this.styleGroup =
                this.findViewById(net.namibsun.dice.R.id.style_select_group) as RadioGroup
        this.wiggleAnimationCheck =
                this.findViewById(net.namibsun.dice.R.id.wiggle_animation_check) as CheckBox
        this.changeAnimationCheck =
                this.findViewById(net.namibsun.dice.R.id.change_animation_check) as CheckBox

        val style = ThemeStyles.valueOf(this.prefs!!.getString("style", "CLASSIC"))
        val vibrate = this.prefs!!.getBoolean("vibrate", true)
        val wiggleAnimation = this.prefs!!.getBoolean("wiggleAnimation", true)
        val changeAnimation = this.prefs!!.getBoolean("changeAnimation", true)

        this.vibrateCheck!!.isChecked = vibrate
        this.wiggleAnimationCheck!!.isChecked = wiggleAnimation
        this.changeAnimationCheck!!.isChecked = changeAnimation

        when (style) {
            ThemeStyles.CLASSIC -> this.styleGroup!!.check(R.id.classic_style)
            ThemeStyles.RED -> this.styleGroup!!.check(R.id.red_style)
            ThemeStyles.BLUE -> this.styleGroup!!.check(R.id.blue_style)
        }
    }

    /**
     * Stores the current input and returns to the previous activity
     */
    @SuppressLint("CommitPrefEdits")
    fun storeAndReturn() {
        val editor = this.prefs!!.edit()

        val radioGroup = this.findViewById(net.namibsun.dice.R.id.style_select_group) as RadioGroup
        val selected = this.findViewById(radioGroup.checkedRadioButtonId)
        val styleName: String = selected.tag as String

        editor.putString("style", styleName)
        editor.putBoolean("vibrate", this.vibrateCheck!!.isChecked)
        editor.putBoolean("wiggleAnimation", this.wiggleAnimationCheck!!.isChecked)
        editor.putBoolean("changeAnimation", this.changeAnimationCheck!!.isChecked)

        editor.commit() // We need to commit to ensure that other activities are updated ASAP
        this.finish()
    }

}