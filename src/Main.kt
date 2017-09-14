package musicplayer

import java.io.BufferedInputStream
import java.io.FileInputStream

import javazoom.jl.player.Player

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
        player!!.close()
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

    fun done(): Boolean = player?.isComplete ?: false

    fun position() = if (player != null) fis.channel.position() else 0

    fun time(): Int = player?.position ?: 0

}

fun main(args: Array<String>)
{
    val mp3 = MP3("/home/steve/Music/#SymphonicRock/Delain/Delain - The Human Contradiction - 06 - Sing to Me.mp3")
    mp3.start()

    while (true) {
        val pos = mp3.position()
        val time = mp3.time()
        println("$pos bytes ($time ms)")
        Thread.sleep(1000)

        if (mp3.done()) break

        if (time in 3000..4000) {
            mp3.halt()
            Thread.sleep(3000)
            mp3.unpause()
        }
    }

    println ("End.")







}
