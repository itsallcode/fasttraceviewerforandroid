package com.uebensee.thomasu.fasttrackmobile.util

import android.os.AsyncTask

class SomeTask<P> constructor(private val predicate: (P) -> Unit) : AsyncTask<P, Unit, Unit>() {
    override fun doInBackground(vararg params: P?) {
        if (params.size == 1) {
            params[0]?.let { this.predicate(it) }
        }
    }
}

/**
 * Created by thomasu on 12/23/17.
 */
fun <P> async(p: P, predicate: (P) -> Unit) {
    val t = SomeTask(predicate)
    t.execute(p)
}
