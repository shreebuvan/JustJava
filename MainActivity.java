/**
 * IMPORTANT: Make sure you are using the correct package name. 
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */

    int numberOfCoffees=0;
    boolean check1;
    boolean check3;
    int whippPrice=1;
    int chocoPrice=2;
    int price=0;
    String name;
    String priceMessage;
    public void increment(View view){
        numberOfCoffees++;
        display(numberOfCoffees);
    }
    public void decrement(View view){
        if(numberOfCoffees>0)
        numberOfCoffees--;
        display(numberOfCoffees);
    }
    public void submitOrder(View view)
    {
        CheckBox check=(CheckBox)findViewById(R.id.checkbox_whipped);
        CheckBox check2=(CheckBox)findViewById(R.id.checkbox_chocolate);
        EditText e=(EditText)findViewById(R.id.name);
        name= e.getText().toString();
        check1=check.isChecked();
        check3=check2.isChecked();
        priceMessage=createOrderSummary(name);
        displayMessage(priceMessage);
    }
    public int calculatePrice(int n)
    {
        if(check1==true)
        {
            if(check3==true)
                price=n*(5+whippPrice+chocoPrice);
            else
                price=n*(5+whippPrice);
        }
        else
        {
            if(check3==true)
                price=n*(5+chocoPrice);
            else
                price=n*5;
        }
        return price;
    }
    public String createOrderSummary(String nam)
    {
        String finalSummary=getString(R.string.order_sum_name)+" "+name+"\nWhipped Cream : "+check1+"\nChocolate : "+check3+"\nQuantity : "+numberOfCoffees+"\nTotal : $"+calculatePrice(numberOfCoffees)+"\n"+getString(R.string.thank_you);
        return finalSummary;
    }
    /**private void displayPrice(int number) {
       * TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
       * priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }*/

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
        TextView OrderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        OrderSummaryTextView.setText(message);
        String subjec="Just Java order for "+name;
        composeEmail(subjec);
    }
    public void composeEmail(String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}