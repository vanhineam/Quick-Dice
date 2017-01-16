package com.adamvanhine.quickdice.services

import android.content.Context
import android.graphics.drawable.Icon
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import com.adamvanhine.quickdice.R
import java.util.*

/**
 * Created by Adam Van Hine on 12/28/2016.
 *
 * The service that will handle all Quick Settings Tile events. Ok
 */
class QuickDiceService : TileService() {

    override fun onClick() {
        val tile = this.qsTile
        setRolling(tile)
        Thread.sleep(1000)
        val num = rollDice()
        updateTile(num)
    }

    fun setRolling(tile: Tile) {
        val newLabel: String? = String.format(Locale.US,
                "%s",
                "Rolling...")

        val newIcon: Icon? = Icon.createWithResource(applicationContext, R.drawable.ic_unsure)

        tile.label = newLabel
        tile.icon = newIcon

        tile.updateTile()
    }

    fun rollDice(): Int {
        val rand = Random()
        val faces = 6

        return 1 + rand.nextInt(faces)
    }

    fun updateTile(num: Int) {
        val tile = this.qsTile

        val drawableId = getDrawable(applicationContext, "ic_" + num + "_black")

        val newLabel: String? = String.format(Locale.US,
                "%s %d",
                getString(R.string.you_rolled),
                num)

        val newIcon: Icon? = Icon.createWithResource(applicationContext, drawableId)

        tile.label = newLabel
        tile.icon = newIcon

        tile.updateTile()
    }

    fun getDrawable(context: Context, name: String): Int {
        return context.resources.getIdentifier(name, "drawable", context.packageName)
    }
}
