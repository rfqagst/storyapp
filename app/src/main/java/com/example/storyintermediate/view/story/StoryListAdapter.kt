package com.example.storyintermediate.view.story

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyintermediate.api.response.ListStoryItem
import com.example.storyintermediate.databinding.StoryListBinding
import com.example.storyintermediate.view.detail.DetailActivity

class StoryListAdapter :
    PagingDataAdapter<ListStoryItem, StoryListAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(var binding: StoryListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = StoryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val story = getItem(position)
        if (story != null) {
            holder.binding.tvName.text = story.name
            holder.binding.tvDescription.text = story.description
            Glide.with(holder.itemView.context)
                .load(story.photoUrl)
                .fitCenter()
                .into(holder.binding.imgBanner)
        } else {
            holder.binding.tvName.text = "Nama tidak tersedia"
            holder.binding.tvDescription.text = "Deskripsi tidak tersedia"
        }

        with(holder) {
            itemView.setOnClickListener {
                val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
                intentDetail.putExtra(DetailActivity.EXTRA_ID, story?.id)

                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        androidx.core.util.Pair(binding.imgBanner, "imgBanner"),
                        androidx.core.util.Pair(binding.tvName, "tvName"),
                        androidx.core.util.Pair(binding.tvDescription, "tvDescription"),
                    )
                itemView.context.startActivity(intentDetail, optionsCompat.toBundle())

            }
        }
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}