package itsallcode.org.fasttraceviewerforandroid.platformaccess

import android.content.Context
import android.util.Log

import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.*
import javax.inject.Inject

/**
 * Created by thomasu on 2/11/18.
 */
class CacheAccessImpl @Inject constructor (val context : Context) : CacheAccess {
    override fun copyToCache(source : InputStream, name : String?) : File {
        val cacheDir = context.cacheDir
        val extension = getExtension(name) ?: "tmp"
        val cacheFile =
                File.createTempFile(Calendar.getInstance().timeInMillis.toString(),
                        "." + extension, cacheDir)
        if (extension == "xml") {
            //Need to copy XML buffer wise and replace CR's with spaces, because Expat Parser
            //in Android does not understand the return characters...tststs
            try {
                val out = FileOutputStream(cacheFile)
                try {
                    // Transfer bytes from in to out
                    val buf = ByteArray(1024)
                    var len: Int = source.read(buf)
                    while (len > 0) {
                        val str = String(buf).replace('\r', ' ').replace('\n', ' ')
                        Log.d(TAG, "From:" + str)
                        Log.d(TAG, "To:" + str)
                        out.write(str.toByteArray(), 0, len)

                        len = source.read(buf)
                    }
                } finally {
                    out.close()
                }
            } finally {
                source.close()
            }
        } else {
            source.copyTo(cacheFile.outputStream())
        }
        return cacheFile
    }

    private fun getExtension(fileName : String?) : String? {
        var extension : String? = null

        val i = fileName?.lastIndexOf('.') ?: -1
        if (i > 0) {
            extension = fileName?.substring(i + 1)
        }
        return extension
    }

    companion object {
        private val TAG = "CacheAccessImpl"
    }
}