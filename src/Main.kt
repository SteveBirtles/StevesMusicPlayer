package musicplayer

import javazoom.jlme.util.Player
import java.io.BufferedInputStream
import java.io.FileInputStream

class MP3(private val filename: String) : Thread()
{
    private var player: Player? = null
    private var total = 0L
    private var paused = 0L
    private lateinit var fis: FileInputStream
    private lateinit var bis: BufferedInputStream

    override fun run() {
        try {
            fis = FileInputStream(filename)
            bis = BufferedInputStream(fis)
            total = fis.available().toLong()
            player = Player(bis)
            player!!.play()
        } catch (e: Exception) {
            println("Problem playing file " + filename)
            println(e)
        }
    }

    fun halt() {
        println ("Pause.")
        paused = fis.available().toLong()
        player!!.stop()
        player = null
    }

    fun unpause() {
        println ("Unpause.")
        fis = FileInputStream(filename)
        fis.skip(total - paused)
        bis = BufferedInputStream(fis)
        player = Player(bis)
        player!!.play()
    }

}

fun main(args: Array<String>)
{
    val mp3 = MP3("u:\\music.mp3")
    mp3.start()


}
