package ir.hm.ramifood

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.hm.ramifood.Models.Food
import ir.hm.ramifood.databinding.*
import ir.hm.ramifood.room.FoodDao
import ir.hm.ramifood.room.MyDatabase


const val BASE_URL_IMAGE = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food"

class MainActivity : AppCompatActivity(), FoodAdapter.FoodEvents {
    lateinit var binding: ActivityMainBinding
    lateinit var myAdapter: FoodAdapter
    lateinit var foodDao: FoodDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        foodDao = MyDatabase.getDatabase(this).foodDao

        val sharedPreferences = getSharedPreferences("first_run", Context.MODE_PRIVATE)

        if (sharedPreferences.getBoolean("first_run", true)) {
            firstRun()
            sharedPreferences.edit().putBoolean("first_run", false).apply()
        }

        showAllData()

        binding.btnDeleteAll.setOnClickListener {

            removeAllFoods()

        }

        binding.btnAddFood.setOnClickListener {

            addNewFood()

        }

        binding.edtSearch.addTextChangedListener { editTextInput ->
            searchOnDataBase(editTextInput.toString())
        }


    }

    private fun searchOnDataBase(editTextInput: String) {

        if (editTextInput.isNotEmpty()) {

            // search 'R'
            val searchData = foodDao.searchFoods(editTextInput)
            myAdapter.setData( ArrayList(searchData) )

        } else {

            // show all data
            val data = foodDao.getAllFoods()
            myAdapter.setData( ArrayList(data) )

        }

    }

    private fun removeAllFoods() {

        val dialog = AlertDialog.Builder(this).create()
        val dialogDeletAllBinding = DialogDeleteAllItemsBinding.inflate(layoutInflater)
        dialog.setView(dialogDeletAllBinding.root)
        dialog.setCancelable(true)
        dialog.show()

        dialogDeletAllBinding.dialogBtnDeleteCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialogDeletAllBinding.dialogBtnDeleteSure.setOnClickListener {
            dialog.dismiss()
            foodDao.deleteAllFoods()
            showAllData()
        }

    }

    private fun firstRun() {

        val foodList = listOf(
            Food(
                txtSubject = "Hamburger",
                txtPrice = "15",
                txtDistance = "3",
                txtLocation = "Isfahan, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food1.jpg",
                numOfRating = 20,
                rating = 4.5f
            ),
            Food(
                txtSubject = "Grilled fish",
                txtPrice = "20",
                txtDistance = "2.1",
                txtLocation = "Tehran, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food2.jpg",
                numOfRating = 10,
                rating = 4f
            ),
            Food(
                txtSubject = "Lasania",
                txtPrice = "40",
                txtDistance = "1.4",
                txtLocation = "Isfahan, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food3.jpg",
                numOfRating = 30,
                rating = 2f
            ),
            Food(
                txtSubject = "pizza",
                txtPrice = "10",
                txtDistance = "2.5",
                txtLocation = "Zahedan, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food4.jpg",
                numOfRating = 80,
                rating = 1.5f
            ),
            Food(
                txtSubject = "Sushi",
                txtPrice = "20",
                txtDistance = "3.2",
                txtLocation = "Mashhad, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food5.jpg",
                numOfRating = 200,
                rating = 3f
            ),
            Food(
                txtSubject = "Roasted Fish",
                txtPrice = "40",
                txtDistance = "3.7",
                txtLocation = "Jolfa, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food6.jpg",
                numOfRating = 50,
                rating = 3.5f
            ),
            Food(
                txtSubject = "Fried chicken",
                txtPrice = "70",
                txtDistance = "3.5",
                txtLocation = "NewYork, USA",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food7.jpg",
                numOfRating = 70,
                rating = 2.5f
            ),
            Food(
                txtSubject = "Vegetable salad",
                txtPrice = "12",
                txtDistance = "3.6",
                txtLocation = "Berlin, Germany",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food8.jpg",
                numOfRating = 40,
                rating = 4.5f
            ),
            Food(
                txtSubject = "Grilled chicken",
                txtPrice = "10",
                txtDistance = "3.7",
                txtLocation = "Beijing, China",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food9.jpg",
                numOfRating = 15,
                rating = 5f
            ),
            Food(
                txtSubject = "Baryooni",
                txtPrice = "16",
                txtDistance = "10",
                txtLocation = "Ilam, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food10.jpg",
                numOfRating = 28,
                rating = 4.5f
            ),
            Food(
                txtSubject = "Ghorme Sabzi",
                txtPrice = "11.5",
                txtDistance = "7.5",
                txtLocation = "Karaj, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food11.jpg",
                numOfRating = 27,
                rating = 5f
            ),
            Food(
                txtSubject = "Rice",
                txtPrice = "12.5",
                txtDistance = "2.4",
                txtLocation = "Shiraz, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food12.jpg",
                numOfRating = 35,
                rating = 2.5f
            ),
        )
        foodDao.insertAllFood(foodList)

    }

    private fun showAllData() {

        val foodData = foodDao.getAllFoods()
        myAdapter = FoodAdapter(ArrayList(foodData), this)
        binding.recyclerMain.adapter = myAdapter
        binding.recyclerMain.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        Log.v("testLog", foodData.toString())
    }

    private fun addNewFood() {

        val dialog = AlertDialog.Builder(this).create()

        val dialogBinding = DialogAddNewItemBinding.inflate(layoutInflater)
        dialog.setView(dialogBinding.root)
        dialog.setCancelable(true)
        dialog.show()

        dialogBinding.dialogBtnAdd.setOnClickListener {

            if (
                dialogBinding.dialogEdtFoodName.length() > 0 &&
                dialogBinding.dialogEdtFoodLocation.length() > 0 &&
                dialogBinding.dialogEdtFoodPrice.length() > 0 &&
                dialogBinding.dialogEdtFoodDistance.length() > 0
            ) {

                val txtName = dialogBinding.dialogEdtFoodName.text.toString()
                val txtPrice = dialogBinding.dialogEdtFoodPrice.text.toString()
                val txtDistance = dialogBinding.dialogEdtFoodDistance.text.toString()
                val txtCity = dialogBinding.dialogEdtFoodLocation.text.toString()
                val txtRatingNumber: Int = (1..150).random()
                val ratingBarStar: Float = (1..5).random().toFloat()

                val randomForUrl = (1 until 12).random()
                val urlPic = BASE_URL_IMAGE + randomForUrl.toString() + ".jpg"

                val newFood = Food(
                    txtSubject = txtName,
                    txtPrice = txtPrice,
                    txtDistance = txtDistance,
                    txtLocation = txtCity,
                    urlImage = urlPic,
                    numOfRating = txtRatingNumber,
                    rating = ratingBarStar
                )

                myAdapter.addFood(newFood)
                foodDao.insertOrUpdate(newFood)

                dialog.dismiss()
                binding.recyclerMain.scrollToPosition(0)


            } else {

                Toast.makeText(this, "لطفا همه مقادیر را وارد کنید :)", Toast.LENGTH_SHORT)
                    .show()

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
                    id = food.id,
                    txtSubject = txtName,
                    txtPrice = txtPrice,
                    txtDistance = txtDistace,
                    txtLocation = txtLocation,
                    urlImage = food.urlImage,
                    numOfRating = food.numOfRating,
                    rating = food.rating
                )

                myAdapter.updateFood(newFood, position)
                foodDao.insertOrUpdate(newFood)
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
            foodDao.deleteFood(food)

        }
    }


}