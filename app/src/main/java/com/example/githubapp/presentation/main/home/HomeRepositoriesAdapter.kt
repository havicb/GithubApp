package com.example.githubapp.presentation.main.home

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubapp.databinding.ItemRepositoryBinding
import com.example.githubapp.domain.entity.RepositoryEntity

class HomeRepositoriesAdapter(
    private val context: Context
) : RecyclerView.Adapter<HomeRepositoriesAdapter.RepositoriesVH>() {

    // todo: Change this to view class
    private var mList: List<RepositoryEntity> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoriesVH =
        RepositoriesVH(
            ItemRepositoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RepositoriesVH, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int = mList.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<RepositoryEntity>) {
        mList = list
        notifyDataSetChanged()
    }

    inner class RepositoriesVH(
        private val binding: ItemRepositoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(repositoryEntity: RepositoryEntity) {
            with(binding) {
                textViewId.text = repositoryEntity.id.toString()
                textViewFullName.text = repositoryEntity.fullName
                Glide.with(context)
                    .load(repositoryEntity.owner.avatarUrl)
                    .circleCrop()
                    .into(imageViewAvatarUrl);
            }
        }
    }
}
