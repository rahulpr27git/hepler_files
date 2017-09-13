package com.jbmatrix.utils.helper.widgets;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by android-arindam on 11/12/15.
 */
public class DatePickerController {
    private Context context;
    private EditText edtDate;
    private int year;
    private int month;
    private int day;
    private String minDate="";
    boolean timeDialog=false;

    public DatePickerController(Context context, EditText edtDate, String minDate, boolean timeDialog)
    {
        this.context=context;
        this.edtDate=edtDate;
        this.minDate=minDate;
        this.timeDialog=timeDialog;
        showDatePicker();
    }
    public Dialog showDatePicker()
    {
        DatePickerDialog dialog=null;
        if(minDate.equals(""))
        {
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
             dialog = new DatePickerDialog(context, datePickerListener,
                    year, month, day);

        }
        else
        {
            ////////////Set Minimum Date Range///////////////////////
                Calendar fromDateCal= Calendar.getInstance();
                SimpleDateFormat myDateFormat=new SimpleDateFormat("MM/dd/yyyy");
                try {
                    fromDateCal.setTime(myDateFormat.parse(minDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
             dialog = new DatePickerDialog(context, datePickerListener, year, month, day);
                dialog.getDatePicker().setMinDate(fromDateCal.getTime().getTime());

            dialog.getDatePicker().setMinDate(fromDateCal.getTime().getTime());
            ////////////Set Minimum Date Range End///////////////////////
         }

        dialog.show();
        return dialog;

    }
private DatePickerDialog.OnDateSetListener datePickerListener
        = new DatePickerDialog.OnDateSetListener() {

    // when dialog box is closed, below method will be called.
    public void onDateSet(DatePicker view, int selectedYear,
                          int selectedMonth, int selectedDay) {
        year = selectedYear;
        month = selectedMonth+1;
        day = selectedDay;
        // set selected date into textview

        StringBuilder stringBuilder = new StringBuilder();
       if(edtDate!=null) {

           stringBuilder.append(year).append("-");

           if (String.valueOf(month).length()<2){
               stringBuilder.append("0"+month).append("-");
           }else{
               stringBuilder.append(month).append("-");
           }

           if (String.valueOf(day).length()<2 ){
               stringBuilder.append("0"+day);
           }else{
               stringBuilder.append(day);
           }

           edtDate.setText(stringBuilder);
        }

    }
};

    public static String convertDateFormat(String dateValue){

        String[] stringDateToken = dateValue.split("-");


        return (stringDateToken[2]+"/"+stringDateToken[1]+"/"+stringDateToken[0]).toString();
    }
}
