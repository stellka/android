package edu.skillbox.nasa.photolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import edu.skillbox.nasa.databinding.PhotoItemBinding
import edu.skillbox.nasa.models.Photo

class Adapter : RecyclerView.Adapter<PhotoViewHolder>() {
    private var data: List<Photo> = emptyList()
    fun setData(data: List<Photo>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = data.size

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
        val item = data.getOrNull(position)
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


class PhotoViewHolder(
    val binding: PhotoItemBinding
    ) : RecyclerView.ViewHolder(binding.root)