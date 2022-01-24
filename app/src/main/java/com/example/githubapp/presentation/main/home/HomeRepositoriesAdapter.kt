package com.example.githubapp.presentation.main.home

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubapp.databinding.ItemRepositoryBinding
import com.example.githubapp.presentation.main.OwnerView
import com.example.githubapp.presentation.main.RepositoryView
import kotlin.properties.Delegates

@SuppressLint("NotifyDataSetChanged")
class HomeRepositoriesAdapter(
    private val context: Context,
) : RecyclerView.Adapter<HomeRepositoriesAdapter.RepositoriesVH>() {

    internal var list: List<RepositoryView> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }
    private var onRepositoryClickListener: ((RepositoryView) -> Unit)? = null
    private var onAvatarClickListener: ((OwnerView) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoriesVH =
        RepositoriesVH(
            ItemRepositoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RepositoriesVH, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun setOnRepositoryItemClickListener(listener: (RepositoryView) -> Unit) {
        this.onRepositoryClickListener = listener
    }

    fun setOnAvatarClickListener(listener: (OwnerView) -> Unit) {
        this.onAvatarClickListener = listener
    }

    inner class RepositoriesVH(
        private val binding: ItemRepositoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(repositoryView: RepositoryView) {
            with(binding) {
                textViewFullName.text = repositoryView.fullName
                textViewForks.text = repositoryView.forks
                textViewWatchers.text = repositoryView.watchers
                textViewIssues.text = repositoryView.issues
                Glide.with(context)
                    .load(repositoryView.ownerView.avatarUrl)
                    .circleCrop()
                    .into(imageViewAvatarUrl)

                cardView.setOnClickListener {
                    onRepositoryClickListener?.invoke(repositoryView)
                }
                imageViewAvatarUrl.setOnClickListener {
                    onAvatarClickListener?.invoke(repositoryView.ownerView)
                }
            }
        }
    }
}
