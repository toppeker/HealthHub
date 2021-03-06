package halmob.healthhub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import halmob.healthhub.EventListeners.FoodListener;
import halmob.healthhub.Models.Food;
import halmob.healthhub.Models.Meal;

public class MealActivity extends AppCompatActivity implements FoodListener, TextWatcher {
    private Spinner foodTypeSpinner;
    private Spinner fruitFoodNameSpinner;
    private Spinner milkProductFoodNameSpinner;
    private Spinner vegetableFoodNameSpinner;
    private Spinner dessertFoodNameSpinner;
    private Spinner meatFoodNameSpinner;
    private Spinner riceFoodNameSpinner;
    private Spinner pastaFoodNameSpinner;
    private String selectedFoodType;
    private String selectedFood;
    private TextView foodNameTextView;
    private boolean spinnerInitFlag;
    private ArrayList<Food> allFoodList = new ArrayList<>();;
    private EditText portionSizeEditText;
    private TextView portionSizeTextView;
    private TextView samplePortionSizeTextView;
    private Button mealSubmitButton;
    private String userId;
    Meal NewMeal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        Intent intent = getIntent(); // gets the previously created intent
        userId = intent.getStringExtra("userId");

        FirebaseTransaction.setFoodListener(this);
        FirebaseTransaction.getFoods();
        spinnerInitFlag = false;

        portionSizeEditText = (EditText) findViewById(R.id.portion_size_input_edit_text);

        //foodType spinner creation code
        Spinner spinner = (Spinner) findViewById(R.id.food_type_spinner);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.food_type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        foodTypeSpinner = (Spinner) findViewById(R.id.food_type_spinner);

        foodNameTextView = (TextView) findViewById(R.id.food_name_text_view);

        portionSizeTextView = (TextView)findViewById(R.id.portion_size_text_view);
        portionSizeTextView.setVisibility(View.INVISIBLE);

        portionSizeEditText = (EditText) findViewById(R.id.portion_size_input_edit_text);
        portionSizeEditText.setVisibility(View.INVISIBLE);
        portionSizeEditText.addTextChangedListener(this);

        samplePortionSizeTextView = (TextView)findViewById(R.id.sample_portion_size_text_view);
        samplePortionSizeTextView.setVisibility(View.INVISIBLE);

        mealSubmitButton = (Button) findViewById(R.id.meal_submit_button);
        mealSubmitButton.setVisibility(View.INVISIBLE);
        mealSubmitButton.setEnabled(false);

        foodTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedFoodType = String.valueOf(foodTypeSpinner.getSelectedItem());

                if(selectedFoodType.equals("Select Food Type")) {
                    foodNameTextView.setVisibility(View.INVISIBLE);
                    samplePortionSizeTextView.setVisibility(View.INVISIBLE);
                    portionSizeEditText.setVisibility(View.INVISIBLE);
                    portionSizeTextView.setVisibility(View.INVISIBLE);
                    mealSubmitButton.setVisibility(View.INVISIBLE);

                    if(spinnerInitFlag == true) { //if the program applied the steps for the first time
                        milkProductFoodNameSpinner.setVisibility(View.INVISIBLE);
                        fruitFoodNameSpinner.setVisibility(View.INVISIBLE);
                        vegetableFoodNameSpinner.setVisibility(View.INVISIBLE);
                        meatFoodNameSpinner.setVisibility(View.INVISIBLE);
                        dessertFoodNameSpinner.setVisibility(View.INVISIBLE);
                        pastaFoodNameSpinner.setVisibility(View.INVISIBLE);
                        riceFoodNameSpinner.setVisibility(View.INVISIBLE);
                    }
                }
                else if( selectedFoodType.equals("Milk Product")) {
                    foodNameTextView.setVisibility(View.VISIBLE);

                    //String t = allFoodList.get(1).getFoodName();
                    //Meal m = new Meal(allFoodList.get(1),"1.5");
                    //String s = fruitFoodNameSpinner.getSelectedItem().toString();
                    milkProductFoodNameSpinner.setVisibility(View.VISIBLE);
                    fruitFoodNameSpinner.setVisibility(View.INVISIBLE);
                    vegetableFoodNameSpinner.setVisibility(View.INVISIBLE);
                    meatFoodNameSpinner.setVisibility(View.INVISIBLE);
                    dessertFoodNameSpinner.setVisibility(View.INVISIBLE);
                    pastaFoodNameSpinner.setVisibility(View.INVISIBLE);
                    riceFoodNameSpinner.setVisibility(View.INVISIBLE);
                    selectedFood = String.valueOf(milkProductFoodNameSpinner.getSelectedItem());
                    setSamplePortionSize();
                    makePortionVisible();


                    milkProductFoodNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selectedFood = String.valueOf(milkProductFoodNameSpinner.getSelectedItem());
                            setSamplePortionSize();
                        }
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            return;
                        }
                    });

                }
                else if( selectedFoodType.equals("Fruit")) {
                    foodNameTextView.setVisibility(View.VISIBLE);

                    fruitFoodNameSpinner.setVisibility(View.VISIBLE);
                    milkProductFoodNameSpinner.setVisibility(View.INVISIBLE);
                    vegetableFoodNameSpinner.setVisibility(View.INVISIBLE);
                    meatFoodNameSpinner.setVisibility(View.INVISIBLE);
                    dessertFoodNameSpinner.setVisibility(View.INVISIBLE);
                    pastaFoodNameSpinner.setVisibility(View.INVISIBLE);
                    riceFoodNameSpinner.setVisibility(View.INVISIBLE);

                    selectedFood = String.valueOf(fruitFoodNameSpinner.getSelectedItem());
                    setSamplePortionSize();

                    makePortionVisible();

                    fruitFoodNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selectedFood = String.valueOf(fruitFoodNameSpinner.getSelectedItem());
                            setSamplePortionSize();
                        }
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            return;
                        }
                    });
                }

                else if( selectedFoodType.equals("Vegetable")) {
                    foodNameTextView.setVisibility(View.VISIBLE);

                    vegetableFoodNameSpinner.setVisibility(View.VISIBLE);
                    fruitFoodNameSpinner.setVisibility(View.INVISIBLE);
                    milkProductFoodNameSpinner.setVisibility(View.INVISIBLE);
                    meatFoodNameSpinner.setVisibility(View.INVISIBLE);
                    dessertFoodNameSpinner.setVisibility(View.INVISIBLE);
                    pastaFoodNameSpinner.setVisibility(View.INVISIBLE);
                    riceFoodNameSpinner.setVisibility(View.INVISIBLE);

                    selectedFood = String.valueOf(vegetableFoodNameSpinner.getSelectedItem());
                    setSamplePortionSize();

                    makePortionVisible();

                    vegetableFoodNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selectedFood = String.valueOf(vegetableFoodNameSpinner.getSelectedItem());
                            setSamplePortionSize();
                        }
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            return;
                        }
                    });
                }

                else if( selectedFoodType.equals("Dessert")) {
                    foodNameTextView.setVisibility(View.VISIBLE);

                    dessertFoodNameSpinner.setVisibility(View.VISIBLE);
                    vegetableFoodNameSpinner.setVisibility(View.INVISIBLE);
                    fruitFoodNameSpinner.setVisibility(View.INVISIBLE);
                    milkProductFoodNameSpinner.setVisibility(View.INVISIBLE);
                    meatFoodNameSpinner.setVisibility(View.INVISIBLE);
                    pastaFoodNameSpinner.setVisibility(View.INVISIBLE);
                    riceFoodNameSpinner.setVisibility(View.INVISIBLE);

                    selectedFood = String.valueOf(dessertFoodNameSpinner.getSelectedItem());
                    setSamplePortionSize();

                    makePortionVisible();

                    dessertFoodNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selectedFood = String.valueOf(dessertFoodNameSpinner.getSelectedItem());
                            setSamplePortionSize();
                        }
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            return;
                        }
                    });
                }

                else if( selectedFoodType.equals("Meat and Meat Products")) {
                    foodNameTextView.setVisibility(View.VISIBLE);

                    meatFoodNameSpinner.setVisibility(View.VISIBLE);
                    dessertFoodNameSpinner.setVisibility(View.INVISIBLE);
                    vegetableFoodNameSpinner.setVisibility(View.INVISIBLE);
                    fruitFoodNameSpinner.setVisibility(View.INVISIBLE);
                    milkProductFoodNameSpinner.setVisibility(View.INVISIBLE);
                    pastaFoodNameSpinner.setVisibility(View.INVISIBLE);
                    riceFoodNameSpinner.setVisibility(View.INVISIBLE);

                    selectedFood = String.valueOf(meatFoodNameSpinner.getSelectedItem());
                    setSamplePortionSize();

                    makePortionVisible();

                    meatFoodNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selectedFood = String.valueOf(meatFoodNameSpinner.getSelectedItem());
                            setSamplePortionSize();
                        }
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            return;
                        }
                    });
                }

                else if( selectedFoodType.equals("Rice")) {
                    foodNameTextView.setVisibility(View.VISIBLE);

                    riceFoodNameSpinner.setVisibility(View.VISIBLE);
                    meatFoodNameSpinner.setVisibility(View.INVISIBLE);
                    dessertFoodNameSpinner.setVisibility(View.INVISIBLE);
                    vegetableFoodNameSpinner.setVisibility(View.INVISIBLE);
                    fruitFoodNameSpinner.setVisibility(View.INVISIBLE);
                    milkProductFoodNameSpinner.setVisibility(View.INVISIBLE);
                    pastaFoodNameSpinner.setVisibility(View.INVISIBLE);

                    selectedFood = String.valueOf(riceFoodNameSpinner.getSelectedItem());
                    setSamplePortionSize();

                    makePortionVisible();

                    riceFoodNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selectedFood = String.valueOf(riceFoodNameSpinner.getSelectedItem());
                            setSamplePortionSize();
                        }
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            return;
                        }
                    });
                }

                else if( selectedFoodType.equals("Pasta")) {
                    foodNameTextView.setVisibility(View.VISIBLE);

                    pastaFoodNameSpinner.setVisibility(View.VISIBLE);
                    riceFoodNameSpinner.setVisibility(View.INVISIBLE);
                    meatFoodNameSpinner.setVisibility(View.INVISIBLE);
                    dessertFoodNameSpinner.setVisibility(View.INVISIBLE);
                    vegetableFoodNameSpinner.setVisibility(View.INVISIBLE);
                    fruitFoodNameSpinner.setVisibility(View.INVISIBLE);
                    milkProductFoodNameSpinner.setVisibility(View.INVISIBLE);



                    selectedFood = String.valueOf(pastaFoodNameSpinner.getSelectedItem());
                    setSamplePortionSize();

                    makePortionVisible();

                    pastaFoodNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selectedFood = String.valueOf(pastaFoodNameSpinner.getSelectedItem());
                            setSamplePortionSize();
                        }
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            return;
                        }
                    });
                }

                spinnerInitFlag = true;
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });




        mealSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitMeal();
                FirebaseTransaction.addMeal(NewMeal);
                Toast.makeText(getApplicationContext(),
                        "Meal record is saved successfully!",
                        Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    @Override
    public void foodRead(List<Food> foods){


        //fruitFoodName spinner creation code
        Spinner spinner2 = (Spinner) findViewById(R.id.fruit_food_name_spinner);

        List<String> fruitFoodNames = new ArrayList<String>();

        for (int i=0;i<foods.size();i++) {
            if(foods.get(i) != null )
                allFoodList.add(foods.get(i));

            if(foods.get(i).getFoodType().equals("Fruit") )
                fruitFoodNames.add(foods.get(i).getFoodName());
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fruitFoodNames);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        fruitFoodNameSpinner = (Spinner) findViewById(R.id.fruit_food_name_spinner);


        //milkProductFoodName spinner creation code
        Spinner spinner3 = (Spinner) findViewById(R.id.milk_food_name_spinner);

        List<String> milkProductFoodNames = new ArrayList<String>();

        for (int i=0;i<foods.size();i++) {
            if(foods.get(i).getFoodType().equals("Milk Product") )
                milkProductFoodNames.add(foods.get(i).getFoodName());
        }
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, milkProductFoodNames);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);

        milkProductFoodNameSpinner = (Spinner) findViewById(R.id.milk_food_name_spinner);



        //vegetableFoodName spinner creation code
        Spinner spinner4 = (Spinner) findViewById(R.id.vegetable_food_name_spinner);

        List<String> VegetableFoodNames = new ArrayList<String>();

        for (int i=0;i<foods.size();i++) {
            if(foods.get(i).getFoodType().equals("Vegetable") )
                VegetableFoodNames.add(foods.get(i).getFoodName());
        }
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, VegetableFoodNames);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter4);

        vegetableFoodNameSpinner = (Spinner) findViewById(R.id.vegetable_food_name_spinner);




        //dessertFoodName spinner creation code
        Spinner spinner5 = (Spinner) findViewById(R.id.dessert_food_name_spinner);

        List<String> dessertFoodNames = new ArrayList<String>();

        for (int i=0;i<foods.size();i++) {
            if(foods.get(i).getFoodType().equals("Dessert") )
                dessertFoodNames.add(foods.get(i).getFoodName());
        }
        ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dessertFoodNames);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner5.setAdapter(adapter5);

        dessertFoodNameSpinner = (Spinner) findViewById(R.id.dessert_food_name_spinner);




        //meatFoodName spinner creation code
        Spinner spinner6 = (Spinner) findViewById(R.id.meat_food_name_spinner);

        List<String> meatFoodNames = new ArrayList<String>();

        for (int i=0;i<foods.size();i++) {
            if(foods.get(i).getFoodType().equals("Meat and Meat Products") )
                meatFoodNames.add(foods.get(i).getFoodName());
        }
        ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, meatFoodNames);
        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner6.setAdapter(adapter6);

        meatFoodNameSpinner = (Spinner) findViewById(R.id.meat_food_name_spinner);




        //riceFoodName spinner creation code
        Spinner spinner7 = (Spinner) findViewById(R.id.rice_food_name_spinner);

        List<String> riceFoodNames = new ArrayList<String>();

        for (int i=0;i<foods.size();i++) {
            if(foods.get(i).getFoodType().equals("Rice") )
                riceFoodNames.add(foods.get(i).getFoodName());
        }
        ArrayAdapter<String> adapter7 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, riceFoodNames);
        adapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner7.setAdapter(adapter7);

        riceFoodNameSpinner = (Spinner) findViewById(R.id.rice_food_name_spinner);



        //pastaFoodName spinner creation code
        Spinner spinner8 = (Spinner) findViewById(R.id.pasta_food_name_spinner);

        List<String> pastaFoodNames = new ArrayList<String>();

        for (int i=0;i<foods.size();i++) {
            if(foods.get(i).getFoodType().equals("Pasta") )
                pastaFoodNames.add(foods.get(i).getFoodName());
        }
        ArrayAdapter<String> adapter8 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, pastaFoodNames);
        adapter8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner8.setAdapter(adapter8);

        pastaFoodNameSpinner = (Spinner) findViewById(R.id.pasta_food_name_spinner);




        /*
        DAHA SONRA EKLEYECEGİM SPİNNERLAR


        //vegetableFoodName spinner creation code
        Spinner spinner4 = (Spinner) findViewById(R.id.fruit_food_name_spinner);

        List<String> VegetableFoodNames = new ArrayList<String>();

        for (int i=0;i<foods.size();i++) {
            if(foods.get(i).getFoodType().equals("Vegetable") )
                VegetableFoodNames.add(foods.get(i).getFoodName());
        }
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, VegetableFoodNames);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter4);

        vegetableFoodNameSpinner = (Spinner) findViewById(R.id.fruit_food_name_spinner);



        //dessertFoodName spinner creation code
        Spinner spinner5 = (Spinner) findViewById(R.id.fruit_food_name_spinner);

        List<String> dessertFoodNames = new ArrayList<String>();

        for (int i=0;i<foods.size();i++) {
            if(foods.get(i).getFoodType().equals("Dessert") )
                dessertFoodNames.add(foods.get(i).getFoodName());
        }
        ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dessertFoodNames);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner5.setAdapter(adapter5);

        dessertFoodNameSpinner = (Spinner) findViewById(R.id.fruit_food_name_spinner);

        //aşağıdaki şekilde kullanılabilir, denemek için commentı kaldırıp debugger kullan
        Food newFood = foods.get(1);
        //String calorie = newFood.getCalorie();
        //int a=1;

        */
    }

    public void submitMeal()
    {
        if( selectedFoodType.equals("Milk Product" ) ) {
            selectedFood = String.valueOf(milkProductFoodNameSpinner.getSelectedItem());
        }

        else if( selectedFoodType.equals("Fruit" ) ) {
            selectedFood = String.valueOf(fruitFoodNameSpinner.getSelectedItem());
        }

        else if( selectedFoodType.equals("Meat and Meat Products" ) ) {
            selectedFood = String.valueOf(meatFoodNameSpinner.getSelectedItem());
        }



        String stringPortionSize = portionSizeEditText.getText().toString();
        //String stringBloodSugarValue = editTextSugarValue.getText().toString();
        //int intBloodSugarValue = -1;

//        try {
  //          intBloodSugarValue = Integer.parseInt(stringBloodSugarValue);
      //  }
    //    catch (NumberFormatException e) {

        //}



        createMealRecord(stringPortionSize);

    }

    public void createMealRecord(String stringNewPortionSize) {
        Food newFoodRecord = null;
        for (int i = 0; i < allFoodList.size(); i++) {
            if (selectedFood.equals(allFoodList.get(i).getFoodName())) {
                newFoodRecord = allFoodList.get(i);
            }
        }


        if (newFoodRecord != null) {

            Float floatNewPortionSize = 0f;

            try {
                floatNewPortionSize = Float.parseFloat(stringNewPortionSize.toString());
            } catch (NumberFormatException NFE) {
                System.out.println("Parsing int error: " + NFE);
            }

            NewMeal = new Meal();

            NewMeal.setPortionSize(newFoodRecord.getPortionSize());
            NewMeal.setFloatPortionSize(floatNewPortionSize);
            NewMeal.setTotalProtein(newFoodRecord.getProtein() * floatNewPortionSize);
            NewMeal.setTotalCarbohydrate(newFoodRecord.getCarbohydrate() * floatNewPortionSize);
            NewMeal.setTotalFat(newFoodRecord.getFat() * floatNewPortionSize);
            NewMeal.setTotalCalorie(newFoodRecord.getCalorie() * floatNewPortionSize);
            NewMeal.setTotalFiber(newFoodRecord.getFiber() * floatNewPortionSize);
            NewMeal.setTotalGlysemicIndex(newFoodRecord.getGlysemicIndex()* floatNewPortionSize);
            NewMeal.setTotalCarbohydrateCount(newFoodRecord.getCarbohydrateCount()* floatNewPortionSize);
            NewMeal.setDate();
            NewMeal.setTime();
            NewMeal.setFoodName(newFoodRecord.getFoodName());
            NewMeal.setFoodType(newFoodRecord.getFoodType());
        }
    }


    public void setSamplePortionSize() {
        Food newFoodRecord = null;
        for( int i = 0; i < allFoodList.size(); i++ ) {
            if(selectedFood.equals(allFoodList.get(i).getFoodName())) {
                newFoodRecord = allFoodList.get(i);
            }
        }

        samplePortionSizeTextView.setText(newFoodRecord.getPortionSize());
    }


    public void makePortionVisible() {
        portionSizeTextView.setVisibility(View.VISIBLE);
        portionSizeEditText.setVisibility(View.VISIBLE);
        samplePortionSizeTextView.setVisibility(View.VISIBLE);
        mealSubmitButton.setVisibility(View.VISIBLE);
    }

    public void mealsRead(List<Meal> mealList){
    }


    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        int intS = 0;
        if(s.toString().trim().length() != 0)
            intS = Integer.parseInt(s.toString());
        if(s.toString().trim().length()==0){
            mealSubmitButton.setEnabled(false);
            portionSizeEditText.setError("This field can not be empty!");
        }

        else if (s.toString().trim().length() >= 4 && s.toString().contains(".") == false ){
            mealSubmitButton.setEnabled(false);
            portionSizeEditText.setError("The portion size is too high!");
        }

        else if(intS > 40) {
            mealSubmitButton.setEnabled(false);
            portionSizeEditText.setError("The entered portion size is not valid!");
        }

        else {
            mealSubmitButton.setEnabled(true);
        }
    }

    public void afterTextChanged(Editable s) {
    }

}
