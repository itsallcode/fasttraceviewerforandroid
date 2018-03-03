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

package itsallcode.org.fasttraceviewerforandroid.ui.speclist

import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import itsallcode.org.fasttraceviewerforandroid.R
import itsallcode.org.fasttraceviewerforandroid.databinding.SpecItemBinding
import itsallcode.org.fasttraceviewerforandroid.ui.model.SpecItem
import itsallcode.org.fasttraceviewerforandroid.ui.model.TraceItem

internal class SpecAdapter internal constructor(private val mSpecItemClickCallback: SpecClickCallback?)
    : RecyclerView.Adapter<SpecAdapter.SpecItemViewHolder>() {

    internal var mSpecificationItems: List<SpecItem>? = null

    fun setSpecItemList(specificationItems: List<SpecItem>) {
        if (mSpecificationItems == null) {
            mSpecificationItems = specificationItems
            notifyItemRangeInserted(0, specificationItems.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return mSpecificationItems!!.size
                }

                override fun getNewListSize(): Int {
                    return specificationItems.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return mSpecificationItems?.get(oldItemPosition)?.id ==
                            specificationItems[newItemPosition].id
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val newFastTraceItem = specificationItems[newItemPosition]
                    val oldFastTraceItem = mSpecificationItems!![oldItemPosition]
                    return newFastTraceItem.id == oldFastTraceItem.id
                }
            })
            mSpecificationItems = specificationItems
            result.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecItemViewHolder {
        val binding = DataBindingUtil
                .inflate<SpecItemBinding>(LayoutInflater.from(parent.context), R.layout.spec_item,
                        parent, false)
        binding.callback = mSpecItemClickCallback
        return SpecItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpecItemViewHolder, position: Int) {
        holder.binding.specItem = mSpecificationItems!![position]
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return mSpecificationItems?.size ?: 0
    }

    internal class SpecItemViewHolder(val binding: SpecItemBinding) : RecyclerView.ViewHolder(binding.root)
}
