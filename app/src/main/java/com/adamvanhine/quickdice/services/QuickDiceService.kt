package com.adamvanhine.quickdice.services

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.SUBPIXEL_TEXT_FLAG
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
    private var PREFS_KEY = "QUICK_DICE_PREFERENCES"
    private var SIDES_KEY = "DiceSides"

    override fun onClick() {
        val tile = this.qsTile
        setRolling(tile)
        Thread.sleep(1000)
        val sides = getSides()
        val num = rollDice(sides)
        if (sides > 6) {
            digitUpdate(num)
        } else {
            diceFaceUpdate(num)
        }
    }

    fun getSides(): Int {
        val sharedPrefs: SharedPreferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)

        return sharedPrefs.getInt(SIDES_KEY, 6)
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

    fun digitUpdate(num: Int) {
        val tile = this.qsTile
        val bitmap = createBitmap(num)

        val newLabel: String? = String.format(Locale.US,
                "%s %d",
                getString(R.string.you_rolled),
                num)

        val newIcon: Icon? = Icon.createWithBitmap(bitmap)

        tile.label = newLabel
        tile.icon = newIcon

        tile.updateTile()
    }

    fun createBitmap(num: Int): Bitmap {
        val paint: Paint = Paint(SUBPIXEL_TEXT_FLAG)
        paint.isAntiAlias = true
        paint.textSize = 62.0f
        paint.color = Color.BLACK
        paint.textAlign = Paint.Align.CENTER

        val width = 64
        val height = 64
        val baseline: Float = -paint.ascent()
        val bitmap: Bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas: Canvas = Canvas(bitmap)

        canvas.drawText(num.toString(), (width / 2).toFloat(), baseline, paint)
        return bitmap
    }

    fun rollDice(faces: Int): Int {
        val rand = Random()

        return 1 + rand.nextInt(faces)
    }

    fun diceFaceUpdate(num: Int) {
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
