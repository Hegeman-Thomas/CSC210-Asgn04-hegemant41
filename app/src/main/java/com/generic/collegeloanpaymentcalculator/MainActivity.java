package com.generic.collegeloanpaymentcalculator;

import java.text.NumberFormat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

/**
 * CSC 210
 * Asgn 04
 * DUE 9/20/2016
 *
 * Author: Hegeman, Thomas
 * Version: 1.0
 */

// MainActivity for the program.
public class MainActivity extends AppCompatActivity {

    private static final NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat =
            NumberFormat.getPercentInstance();

    private static final int FIVE_YEARS_MONTHS = 60;
    private static final int TEN_YEARS_MONTHS = 120;
    private static final int FIFTEEN_YEARS_MONTHS = 180;
    private static final int TWENTY_YEARS_MONTHS = 240;
    private static final int TWENTYFIVE_YEARS_MONTHS = 300;
    private static final int THIRTY_YEARS_MONTHS = 360;

    private double loanAmount = 0.0;
    private double loanRate = 0.001;

    private TextView amountInputTextView;
    private TextView rateInputTextView;

    private TextView monthlyFiveTextView;
    private TextView monthlyTenTextView;
    private TextView monthlyFifteenTextView;
    private TextView monthlyTwentyTextView;
    private TextView monthlyTwentyfiveTextView;
    private TextView monthlyThirtyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        percentFormat.setMinimumFractionDigits(1);

        amountInputTextView =
                (TextView) findViewById(R.id.amountInputTextView);
        rateInputTextView =
                (TextView) findViewById(R.id.rateInputTextView);
        monthlyFiveTextView =
                (TextView) findViewById(R.id.monthlyFiveTextView);
        monthlyTenTextView =
                (TextView) findViewById(R.id.monthlyTenTextView);
        monthlyFifteenTextView =
                (TextView) findViewById(R.id.monthlyFifteenTextView);
        monthlyTwentyTextView =
                (TextView) findViewById(R.id.monthlyTwentyTextView);
        monthlyTwentyfiveTextView =
                (TextView) findViewById(R.id.monthlyTwentyfiveTextView);
        monthlyThirtyTextView =
                (TextView) findViewById(R.id.monthlyThirtyTextView);

        amountInputTextView.setText(currencyFormat.format(loanAmount));

        rateInputTextView.setText(percentFormat.format(loanRate));

        EditText loanAmountEditText =
                (EditText) findViewById(R.id.loanAmountEditText);
        loanAmountEditText.addTextChangedListener(loanAmountEditTextWatcher);

        EditText loanRateEditText =
                (EditText) findViewById(R.id.loanRateEditText);
        loanRateEditText.addTextChangedListener(loanRateEditTextWatcher);

    } // end onCreate method

    // Update all time periods for the monthly payment.
    private void updateAll()
    {
        updateFive();
        updateTen();
        updateFifteen();
        updateTwenty();
        updateTwentyfive();
        updateThirty();

    } // end updateAll method

    // Update the monthly amount over the five year period.
    private void updateFive()
    {
        double five = 0.0;

        five = monthlyPayment(FIVE_YEARS_MONTHS, loanAmount, loanRate);

        monthlyFiveTextView.setText(currencyFormat.format(five));

    } // end updateFive method

    // Update the monthly amount over the ten year period.
    private void updateTen()
    {
        double ten = 0.0;

        ten = monthlyPayment(TEN_YEARS_MONTHS, loanAmount, loanRate);

        monthlyTenTextView.setText(currencyFormat.format(ten));

    } // end updateTen method

    // Update the monthly amount over the fifteen year period.
    private void updateFifteen()
    {
        double fifteen = 0.0;

        fifteen = monthlyPayment(FIFTEEN_YEARS_MONTHS, loanAmount, loanRate);

        monthlyFifteenTextView.setText(currencyFormat.format(fifteen));

    } // end updateFifteen method

    // Update the monthly amount over the twenty year period.
    private void updateTwenty()
    {
        double twenty = 0.0;

        twenty = monthlyPayment(TWENTY_YEARS_MONTHS, loanAmount, loanRate);

        monthlyTwentyTextView.setText(currencyFormat.format(twenty));

    } // end updateTwenty method

    // Update the monthly amount over the twenty-five year period.
    private void updateTwentyfive()
    {
        double twentyfive = 0.0;

        twentyfive = monthlyPayment(TWENTYFIVE_YEARS_MONTHS, loanAmount, loanRate);

        monthlyTwentyfiveTextView.setText(currencyFormat.format(twentyfive));

    } // end updateTwentyfive method

    // Update the monthly amount over the thirty year period.
    private void updateThirty()
    {
        double thirty = 0.0;

        thirty = monthlyPayment(THIRTY_YEARS_MONTHS, loanAmount, loanRate);

        monthlyThirtyTextView.setText(currencyFormat.format(thirty));

    } // end updateThirty method

    // Calculate the monthly payment based off of the time period and actual rate.
    // M = P * ( J / (1 - (1 + J)^-N))
    // M = monthly, P = loan amount (principle), J = loan rate (actual) (J = J / 12),
    // -N = the amount of periods (months).
    private double monthlyPayment(int months, double loan, double rate)
    {
        double monthly = 0.0;

        rate /= 12;

        double actualRateMonthly = monthlyRate(months, rate);

        monthly = loan * (rate / (1 - (1 / actualRateMonthly)));

        return monthly;

    } // end monthlyPayment method

    // Calculate the monthly loan rate for the actual rate of all months (periods)
    private double monthlyRate(int months, double rate)
    {
        double interest = 1 + rate;
        double actualRateMonthly = 0.0;

        actualRateMonthly = interest * interest;

        for (int i = months; i > 2; i--)
        {
            actualRateMonthly *= interest;
        }

        return actualRateMonthly;

    } // end monthlyRate method

    // TextWatcher for the loan amount change by the user
    private TextWatcher loanAmountEditTextWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try
            {
                loanAmount = Double.parseDouble(s.toString()) / 100.0;
            }
            catch (NumberFormatException e)
            {
                loanAmount = 0.0;
            }

            amountInputTextView.setText(currencyFormat.format(loanAmount));
            updateAll();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }
    }; // end loanAmountEditTextWatcher

    // TextWatcher for the change of the rate of the loan by the user
    private TextWatcher loanRateEditTextWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try
            {
                loanRate = Double.parseDouble(s.toString()) / 1000;
            }
            catch (NumberFormatException e)
            {
                loanRate = 0.001;
            }

            rateInputTextView.setText(percentFormat.format(loanRate));
            updateAll();

        }

        @Override
        public void afterTextChanged(Editable s) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
    }; // end loanRateEditTextWatcher

} // end MainActivity class
