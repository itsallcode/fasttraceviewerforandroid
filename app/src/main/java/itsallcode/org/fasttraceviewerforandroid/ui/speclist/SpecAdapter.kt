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

package itsallcode.org.fasttraceviewerforandroid.ui.speclist

import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import itsallcode.org.fasttraceviewerforandroid.R
import itsallcode.org.fasttraceviewerforandroid.databinding.SpecItemBinding
import itsallcode.org.fasttraceviewerforandroid.model.SpecItem

internal class SpecAdapter internal constructor(private val mSpecItemClickCallback: SpecClickCallback?)
    : RecyclerView.Adapter<SpecAdapter.SpecItemViewHolder>() {

    internal var mSpecificationItems: SpecItem? = null

    fun setSpecItemList(specificationItems: SpecItem) {
        if (mSpecificationItems == null) {
            mSpecificationItems = specificationItems
            notifyItemRangeInserted(0, specificationItems.items.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return mSpecificationItems!!.items.size
                }

                override fun getNewListSize(): Int {
                    return specificationItems.items.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return mSpecificationItems?.items?.get(oldItemPosition)?.id ==
                            specificationItems.items[newItemPosition].id
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val newFastTraceItem = specificationItems.items[newItemPosition]
                    val oldFastTraceItem = mSpecificationItems!!.items[oldItemPosition]
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
        holder.binding.specItem = mSpecificationItems!!.items[position]
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return mSpecificationItems?.items?.size ?: 0
    }

    internal class SpecItemViewHolder(val binding: SpecItemBinding) : RecyclerView.ViewHolder(binding.root)
}
