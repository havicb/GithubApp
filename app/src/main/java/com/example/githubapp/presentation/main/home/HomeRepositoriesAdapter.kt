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
    private val context: Context,
) : RecyclerView.Adapter<HomeRepositoriesAdapter.RepositoriesVH>() {

    // todo: Change this to view class
    private var mList: List<RepositoryEntity> = emptyList()
    private var mOnRepositoryClickListener: ((RepositoryEntity) -> Unit)? = null

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

    fun setOnItemClickListener(listener: (RepositoryEntity) -> Unit) {
        this.mOnRepositoryClickListener = listener
    }

    inner class RepositoriesVH(
        private val binding: ItemRepositoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(repositoryEntity: RepositoryEntity) {
            with(binding) {
                textViewFullName.text = repositoryEntity.fullName
                textViewForks.text = repositoryEntity.forksCount.toString()
                textViewWatchers.text = repositoryEntity.watchersCount.toString()
                textViewIssues.text = repositoryEntity.openIssues.toString()
                Glide.with(context)
                    .load(repositoryEntity.owner.avatarUrl)
                    .circleCrop()
                    .into(imageViewAvatarUrl)

                cardView.setOnClickListener {
                    mOnRepositoryClickListener?.invoke(repositoryEntity)
                }
            }
        }
    }
}
