/*-
 * #%L
 \* FastTraceViewerForAndroid
 * %%
 * Copyright (C) 2017 - 2018 itsallcode.org
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
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
