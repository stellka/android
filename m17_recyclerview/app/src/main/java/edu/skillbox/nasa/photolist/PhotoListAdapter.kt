package edu.skillbox.nasa.photolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import edu.skillbox.nasa.databinding.PhotoItemBinding
import edu.skillbox.nasa.models.Photo

class PhotoListAdapter : ListAdapter<Photo, PhotoViewHolder> (DiffUtilCallBack()) {
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
                Glide
                    .with(imageView.context)
                    .load(it.img_src)
                    .into(imageView)
            }
        }
    }

}

class DiffUtilCallBack : DiffUtil.ItemCallback<Photo>(){
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean = oldItem == newItem
}
