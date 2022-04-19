package ir.hm.ramifood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.animation.BounceInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.hm.ramifood.Models.Food
import ir.hm.ramifood.databinding.ActivityMainBinding
import ir.hm.ramifood.databinding.DialogAddNewItemBinding
import ir.hm.ramifood.databinding.DialogDeletItemBinding
import ir.hm.ramifood.databinding.DialogUpdateItemBinding
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

class MainActivity : AppCompatActivity(), FoodAdapter.FoodEvents {
    lateinit var binding: ActivityMainBinding
    lateinit var myAdapter: FoodAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // how to use recycler view :
        // 1. create view of recyclerView in activity_main.xml
        // 2. create item for recyclerView
        // 3. create adapter and view holder for recyclerView
        // 4. set adapter to recyclerView and set layout manager

        val foodList = arrayListOf(

            Food(
                "Hamburger",
                "15",
                "3",
                "Isfahan, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food1.jpg",
                20,
                4.5f
            ),
            Food(
                "Grilled fish",
                "20",
                "2.1",
                "Tehran, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food2.jpg",
                10,
                4f
            ),
            Food(
                "Lasania",
                "40",
                "1.4",
                "Isfahan, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food3.jpg",
                30,
                2f
            ),
            Food(
                "pizza",
                "10",
                "2.5",
                "Zahedan, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food4.jpg",
                80,
                1.5f
            ),
            Food(
                "Sushi",
                "20",
                "3.2",
                "Mashhad, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food5.jpg",
                200,
                3f
            ),
            Food(
                "Roasted Fish",
                "40",
                "3.7",
                "Jolfa, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food6.jpg",
                50,
                3.5f
            ),
            Food(
                "Fried chicken",
                "70",
                "3.5",
                "NewYork, USA",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food7.jpg",
                70,
                2.5f
            ),
            Food(
                "Vegetable salad",
                "12",
                "3.6",
                "Berlin, Germany",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food8.jpg",
                40,
                4.5f
            ),
            Food(
                "Grilled chicken",
                "10",
                "3.7",
                "Beijing, China",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food9.jpg",
                15,
                5f
            ),
            Food(
                "Baryooni",
                "16",
                "10",
                "Ilam, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food10.jpg",
                28,
                4.5f
            ),
            Food(
                "Ghorme Sabzi",
                "11.5",
                "7.5",
                "Karaj, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food11.jpg",
                27,
                5f
            ),
            Food(
                "Rice",
                "12.5",
                "2.4",
                "Shiraz, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food12.jpg",
                35,
                2.5f
            ),
        )


        myAdapter = FoodAdapter(foodList.clone() as ArrayList<Food>, this)
        binding.recyclerMain.adapter = myAdapter
        binding.recyclerMain.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)


        binding.btnAddFood.setOnClickListener {

            val dialog = AlertDialog.Builder(this).create()
            val bindingDialog = DialogAddNewItemBinding.inflate(layoutInflater)
            dialog.setView(bindingDialog.root)
            dialog.setCancelable(true)
            dialog.show()


            bindingDialog.dialogBtnAdd.setOnClickListener {

                if (

                    bindingDialog.dialogEdtFoodName.length() > 0 &&
                    bindingDialog.dialogEdtFoodPrice.length() > 0 &&
                    bindingDialog.dialogEdtFoodLocation.length() > 0 &&
                    bindingDialog.dialogEdtFoodDistance.length() > 0

                ) {

                    val txtName = bindingDialog.dialogEdtFoodName.text.toString()
                    val txtLocation = bindingDialog.dialogEdtFoodLocation.text.toString()
                    val txtPrice = bindingDialog.dialogEdtFoodPrice.text.toString()
                    val txtDistace = bindingDialog.dialogEdtFoodDistance.text.toString()
                    val txtRatingNumber: Int = (1..150).random()
                    val ratingBarStar: Float = (1..5).random().toFloat()

                    val randomForUrl = (0 until 12).random()
                    val urlPic = foodList[randomForUrl].urlImage

                    // create new food to add to recycler view
                    val newFood = Food(
                        txtName,
                        txtPrice,
                        txtDistace,
                        txtLocation,
                        urlPic,
                        txtRatingNumber,
                        ratingBarStar
                    )

                    myAdapter.addFood(newFood)
                    dialog.dismiss()
                    binding.recyclerMain.scrollToPosition(0)

                } else {

                    Toast.makeText(this, "Please fill all of the blank!", Toast.LENGTH_SHORT).show()
                }
            }


        }


        binding.edtSearch.addTextChangedListener { editTextInput ->

            if (editTextInput!!.isNotEmpty()) {

                // filter list
                val cloneList = foodList.clone() as ArrayList<Food>
                val filterList = cloneList.filter { foodItem ->

                    foodItem.txtSubject.lowercase().contains(editTextInput.toString().lowercase())

                }

                myAdapter.setData(ArrayList(filterList))

            } else {

                // show all data
                myAdapter.setData(foodList.clone() as ArrayList<Food>)


            }


        }

    }

    override fun onFoodClicked(food: Food, position: Int) {

        val dialog = AlertDialog.Builder(this).create()
        val dialogUpdateBinding = DialogUpdateItemBinding.inflate(layoutInflater)

        dialogUpdateBinding.dialogEdtFoodName.setText(food.txtSubject)
        dialogUpdateBinding.dialogEdtFoodLocation.setText(food.txtLocation)
        dialogUpdateBinding.dialogEdtFoodPrice.setText(food.txtPrice)
        dialogUpdateBinding.dialogEdtFoodDistance.setText(food.txtDistance)

        dialog.setView(dialogUpdateBinding.root)
        dialog.setCancelable(true)
        dialog.show()

        dialogUpdateBinding.dialogUpdateBtnCancel.setOnClickListener {

            dialog.dismiss()

        }

        dialogUpdateBinding.dialogUpdateBtnDone.setOnClickListener {

            if (

                dialogUpdateBinding.dialogEdtFoodName.length() > 0 &&
                dialogUpdateBinding.dialogEdtFoodPrice.length() > 0 &&
                dialogUpdateBinding.dialogEdtFoodLocation.length() > 0 &&
                dialogUpdateBinding.dialogEdtFoodDistance.length() > 0

            ) {

                val txtName = dialogUpdateBinding.dialogEdtFoodName.text.toString()
                val txtLocation = dialogUpdateBinding.dialogEdtFoodLocation.text.toString()
                val txtPrice = dialogUpdateBinding.dialogEdtFoodPrice.text.toString()
                val txtDistace = dialogUpdateBinding.dialogEdtFoodDistance.text.toString()

                val newFood = Food(
                    txtName,
                    txtPrice,
                    txtDistace,
                    txtLocation,
                    food.urlImage,
                    food.numOfRating,
                    food.rating
                )

                myAdapter.updateFood(newFood, position)
                dialog.dismiss()

            } else {

                Toast.makeText(this, "Fill all of the blank!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onFoodLongClicked(food: Food, pos: Int) {
        val dialog = AlertDialog.Builder(this).create()
        val dialogDeleteBinding = DialogDeletItemBinding.inflate(layoutInflater)
        dialog.setView(dialogDeleteBinding.root)
        dialog.setCancelable(true)
        dialog.show()

        dialogDeleteBinding.dialogBtnDeleteCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialogDeleteBinding.dialogBtnDeleteSure.setOnClickListener {

            dialog.dismiss()
            myAdapter.removeFood(food, pos)

        }
    }


}