package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    int quantity = 2;
    int basePrice = 5;
    int whipped = 1;
    int chocolate = 2;
    boolean hasWhippedCream=true;
    boolean hasChocolate=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = findViewById(R.id.notify_me_checkbox);
        /*Получаем, отмечен ли данный флажок*/
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        /*Получаем, отмечен ли данный флажок*/
        boolean hasChocolate = chocolateCheckBox.isChecked();
        int price = calculatePrice(hasWhippedCream, hasChocolate);

        String priceMessage = createOrderSummary(price);
        displayMessage(priceMessage);

//       Intent intent = new Intent(Intent.ACTION_VIEW);
//       intent.setData(Uri.parse("geo:latitude,longitude?z=zoom"));
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        }

            Intent intent2 = new Intent(Intent.ACTION_SENDTO);
            intent2.setData(Uri.parse("mailto:" + "andrey157161@gmail.com")); // only email apps should handle this
            intent2.putExtra(Intent.EXTRA_EMAIL, "");
            intent2.putExtra(Intent.EXTRA_SUBJECT, " ");
        intent2.putExtra(Intent.EXTRA_TEXT, priceMessage);
            if (intent2.resolveActivity(getPackageManager()) != null) {
                startActivity(intent2);
            }
        }


    /**
     * Calculates the price of the order.
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {

        if (hasWhippedCream) {
            basePrice += whipped;
        }
        if (hasChocolate) {
            basePrice += chocolate;
        }
        return basePrice * quantity;
    }

    private String chooseCream(){
        if(hasWhippedCream)
            return getString(R.string.yes);
        else
            return getString(R.string.no);
    }

    private String chooseChocolate(){
        if(hasChocolate)
            return getString(R.string.yes);
        else
            return getString(R.string.no);
    }




    public void increment(View view) {
        quantity = quantity + 1;
        display(quantity);

        if (quantity > 99) {
            Toast.makeText(this, "You cannot order more than 100 cup of coffee", Toast.LENGTH_SHORT).show();
            quantity = 99;
        }
    }

    public void decrement(View view) {
        quantity = quantity - 1;
        display(quantity);

        if (quantity < 1) {
            Toast.makeText(this, "You cannot order less than one cup of coffee", Toast.LENGTH_SHORT).show();
            quantity = 1;
        }
    }

    /**
     * Create summary of the order.
     */

    public String createOrderSummary(int price) {
        EditText nameEditText = findViewById(R.id.description_view);
        String message = getString(R.string.Name) +" "+ nameEditText.getText();

        message += "\n" +getString(R.string.QUANTITY)+" " + quantity;
        message += "\n" +getString(R.string.ORDER_SUMMARY)+" " + price + getString(R.string.dollars);
        message += "\n" + getString(R.string.Whipped_cream)+" " + chooseCream();
        message += "\n" + getString(R.string.Chocolate)+" " + chooseChocolate();
        message += "\n" + getString(R.string.Thank_you);
        return message;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryView.setText(message);
    }

}