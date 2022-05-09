package ir.hm.ramifood

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ir.hm.ramifood.Models.Food
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class FoodAdapter(private val data: ArrayList<Food>, private val foodEvents:FoodEvents) :
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {


    inner class FoodViewHolder(itemView: View, private val context: Context) :
        RecyclerView.ViewHolder(itemView){

        val imgFood = itemView.findViewById<ImageView>(R.id.item_img_food)
        val txtSubject = itemView.findViewById<TextView>(R.id.item_txt_subject)
        val txtLocation = itemView.findViewById<TextView>(R.id.item_txt_location)
        val txtPrice = itemView.findViewById<TextView>(R.id.item_txt_price)
        val txtDistance = itemView.findViewById<TextView>(R.id.item_txt_distance)
        val txtRating = itemView.findViewById<TextView>(R.id.item_txt_rating)
        val rating = itemView.findViewById<RatingBar>(R.id.item_rating)
        val container = itemView.findViewById<ConstraintLayout>(R.id.container)

        fun bindData(position: Int) {

            txtSubject.text = data[position].txtSubject
            txtLocation.text = data[position].txtLocation
            txtPrice.text = "$ " + data[position].txtPrice + " VIP"
            txtDistance.text = data[position].txtDistance + " miles from you"
            rating.rating = data[position].rating
            txtRating.text = "(" + data[position].numOfRating.toString() + " Rating)"


            Glide
                .with(context)
                .load(data[position].urlImage)
                .transform(RoundedCornersTransformation(16, 4))
                .into(imgFood)

            itemView.setOnClickListener {

                foodEvents.onFoodClicked(data[adapterPosition], position)

            }

            itemView.setOnLongClickListener {

                foodEvents.onFoodLongClicked(data[adapterPosition], adapterPosition)

                true
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bindData(position)

        // animation for recycler's item
        holder.container.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.anim_test)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addFood(newFood: Food) {

        //add food to list
        data.add(0, newFood)
        notifyItemInserted(0)
    }

    fun removeFood(oldFood: Food, oldPosition: Int) {

        // remove food from list
        data.remove(oldFood)
        notifyItemRemoved(oldPosition)
    }

    fun updateFood( newFood:Food, position: Int ){

        // update food from list
        data.set(position, newFood)
        notifyItemChanged(position)

    }

    fun setData(newFood: ArrayList<Food>){

        // set new data on list
        data.clear()
        data.addAll(newFood)

        notifyDataSetChanged()

    }

    interface FoodEvents {

        // 1. create interface in adapter
        // 2. get an object  of interface in args of adapter
        // 3. fill (call) object of interface with your data
        // 4. implementation in MainActivity

        fun onFoodClicked(food:Food, position: Int)
        fun onFoodLongClicked(food:Food , pos:Int)

    }
}