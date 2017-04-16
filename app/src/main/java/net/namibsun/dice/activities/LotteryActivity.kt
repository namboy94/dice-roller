package net.namibsun.dice.activities

import android.os.Bundle
import android.widget.TextView
import net.namibsun.dice.R
import net.namibsun.dice.helpers.initializeBottomMenuBar
import net.namibsun.dice.helpers.initializeSettingsButton
import net.namibsun.dice.objects.TextDie
import net.namibsun.dice.objects.loadTheme

/**
 * An activity that allows a user to generate a lottery number
 */
class LotteryActivity : BaseActivity() {

    /**
     * The Lottery number UI elements, which are represented by TextDies
     */
    val lotteryNumbers: MutableList<TextDie> = mutableListOf()

    /**
     * Initializes the lottery TextDies and sets their limit to a value
     * between 1 and 49
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.lottery)
        initializeBottomMenuBar(this)
        initializeSettingsButton(this)

        listOf(R.id.lottery_die_one, R.id.lottery_die_two, R.id.lottery_die_three,
                R.id.lottery_die_four, R.id.lottery_die_five, R.id.lottery_die_six)
                .mapTo(lotteryNumbers) {
            TextDie(this,
                    this.findViewById(it) as TextView,
                    loadTheme(this.prefs!!),
                    initialValue=1, limit=49, minimum=1)
                }

        this.lotteryNumbers.map { die -> die.view.setOnClickListener {
            this.lotteryNumbers.map(TextDie::roll)
        } }
    }

    /**
     * Applies the current theme to the lottery numbers
     */
    override fun onResume() {
        super.onResume()
        this.lotteryNumbers.map { die -> die.updateTheme(loadTheme(this.prefs!!)) }
    }

}