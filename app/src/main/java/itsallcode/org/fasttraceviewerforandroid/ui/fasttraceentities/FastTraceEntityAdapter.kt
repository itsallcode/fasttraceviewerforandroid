/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package itsallcode.org.fasttraceviewerforandroid.ui.fasttraceentities

import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup

import itsallcode.org.fasttraceviewerforandroid.R
import itsallcode.org.fasttraceviewerforandroid.databinding.FastTraceItemBinding
import itsallcode.org.fasttraceviewerforandroid.ui.model.FastTraceItem

internal class FastTraceEntityAdapter internal constructor(private val mFastTraceClickCallback: FastTraceClickCallback?)
    : RecyclerView.Adapter<FastTraceEntityAdapter.FastTraceViewHolder>() {

    internal var mFastTraceItemList: List<FastTraceItem>? = null

    fun setFastTraceItemList(fastTraceItems: List<FastTraceItem>) {
        if (mFastTraceItemList == null) {
            mFastTraceItemList = fastTraceItems
            notifyItemRangeInserted(0, fastTraceItems.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return mFastTraceItemList!!.size
                }

                override fun getNewListSize(): Int {
                    return fastTraceItems.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return mFastTraceItemList?.get(oldItemPosition)?.id ==
                            fastTraceItems[newItemPosition].id
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val newFastTraceItem = fastTraceItems[newItemPosition]
                    val oldFastTraceItem = mFastTraceItemList!![oldItemPosition]
                    return newFastTraceItem.id == oldFastTraceItem.id && TextUtils.equals(newFastTraceItem.name, oldFastTraceItem.name)
                }
            })
            mFastTraceItemList = fastTraceItems
            result.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FastTraceViewHolder {
        val binding = DataBindingUtil
                .inflate<FastTraceItemBinding>(LayoutInflater.from(parent.context), R.layout.fast_trace_item,
                        parent, false)
        binding.callback = mFastTraceClickCallback
        return FastTraceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FastTraceViewHolder, position: Int) {
        holder.binding.fastTraceItem = mFastTraceItemList!![position]
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return if (mFastTraceItemList == null) 0 else mFastTraceItemList!!.size
    }

    internal class FastTraceViewHolder(val binding: FastTraceItemBinding) : RecyclerView.ViewHolder(binding.root)
}
