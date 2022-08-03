package edu.skillbox.nasa.pagedlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import edu.skillbox.nasa.databinding.PhotoItemBinding
import edu.skillbox.nasa.models.Photo
import edu.skillbox.nasa.photolist.PhotoViewHolder

class PhotoPagedListAdapter(
    private val onClick: (Photo) -> Unit
) : PagingDataAdapter<Photo, PhotoViewHolder> (DiffUtilCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            PhotoItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            rover.text = "Rover: ${item?.rover?.name.toString()}"
            camera.text = "Camera: ${item?.camera?.name.toString()}"
            sol.text = "Sol: ${item?.sol.toString()}"
            date.text = "Date: ${item?.earth_date.toString()}"
            item?.let {
                val url = it.img_src.removePrefix("http://mars.jpl.nasa.gov")
                Glide
                    .with(imageView.context)
                    .load("https://mars.nasa.gov$url")
                    .into(imageView)
            }
        }
        holder.binding.root.setOnClickListener {
            item?.let {
                    it1 -> onClick(it1)
            }
        }
    }
}

class DiffUtilCallBack : DiffUtil.ItemCallback<Photo>(){
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean = oldItem == newItem
}
