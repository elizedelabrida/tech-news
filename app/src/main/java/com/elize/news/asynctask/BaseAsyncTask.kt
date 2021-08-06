package com.elize.news.asynctask

import android.os.AsyncTask

class BaseAsyncTask<T>(
    private val whenExecute: () -> T,
    private val whenFinish: (result : T) -> Unit
) : AsyncTask<Void, Void, T>() {

    override fun doInBackground(vararg params: Void?) = whenExecute()

    override fun onPostExecute(result: T) {
        super.onPostExecute(result)
        whenFinish(result)
    }

}