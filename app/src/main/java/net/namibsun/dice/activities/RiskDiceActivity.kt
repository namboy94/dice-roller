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

import android.os.Bundle
import android.widget.ImageView
import net.namibsun.dice.R
import net.namibsun.dice.helpers.initializeBottomMenuBar
import net.namibsun.dice.helpers.initializeSettingsButton
import net.namibsun.dice.objects.ClassicDie
import net.namibsun.dice.objects.ThemeStyles
import net.namibsun.dice.objects.loadTheme

class RiskDiceActivity : BaseActivity() {

    val redDice : MutableList<ClassicDie> = mutableListOf()
    val blueDice : MutableList<ClassicDie> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.riskdice)
        initializeSettingsButton(this)
        initializeBottomMenuBar(this)

        listOf(R.id.red_die_1, R.id.red_die_2, R.id.red_die_3).mapTo(redDice) {
            ClassicDie(this, this.findViewById(it) as ImageView,
                    loadTheme(this.prefs!!, ThemeStyles.RED))
        }
        listOf(R.id.blue_die_1, R.id.blue_die_2).mapTo(blueDice) {
            ClassicDie(this, this.findViewById(it) as ImageView,
                    loadTheme(this.prefs!!, ThemeStyles.BLUE))
        }

        for (die in this.redDice + this.blueDice) {
            die.view.setOnClickListener {
                for (innerDie in this.redDice + this.blueDice) {
                    innerDie.roll()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        for (die in this.redDice) {
            die.updateTheme(loadTheme(this.prefs!!, ThemeStyles.RED))
        }
        for (die in this.blueDice) {
            die.updateTheme(loadTheme(this.prefs!!, ThemeStyles.BLUE))
        }
    }

}