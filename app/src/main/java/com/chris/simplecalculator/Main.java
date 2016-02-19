package com.chris.simplecalculator;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Main extends Activity implements View.OnClickListener {

    Button  one, two, three, four, five,
            six, seven, eight, nine, zero,
            add, sub, mul, div, clear, equal,
            del, deci;
    EditText disp;
    String storedOp = "", operation = "", resString = "";
    float res, repeatedOp;
    boolean opLastPressed = false, equalsLastPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        one = (Button) findViewById(R.id.one);
        two = (Button) findViewById(R.id.two);
        three = (Button) findViewById(R.id.three);
        four = (Button) findViewById(R.id.four);
        five = (Button) findViewById(R.id.five);
        six = (Button) findViewById(R.id.six);
        seven = (Button) findViewById(R.id.seven);
        eight = (Button) findViewById(R.id.eight);
        nine = (Button) findViewById(R.id.nine);
        zero = (Button) findViewById(R.id.zero);
        add = (Button) findViewById(R.id.add);
        sub = (Button) findViewById(R.id.sub);
        div = (Button) findViewById(R.id.div);
        mul = (Button) findViewById(R.id.mul);
        clear = (Button) findViewById(R.id.clear);
        equal = (Button) findViewById(R.id.equal);
        del = (Button) findViewById(R.id.del);
        deci = (Button) findViewById(R.id.deci);

        disp = (EditText) findViewById(R.id.disp);

        try{
            one.setOnClickListener(this);
            two.setOnClickListener(this);
            three.setOnClickListener(this);
            four.setOnClickListener(this);
            five.setOnClickListener(this);
            six.setOnClickListener(this);
            seven.setOnClickListener(this);
            eight.setOnClickListener(this);
            nine.setOnClickListener(this);
            zero.setOnClickListener(this);
            clear.setOnClickListener(this);
            add.setOnClickListener(this);
            sub.setOnClickListener(this);
            mul.setOnClickListener(this);
            div.setOnClickListener(this);
            equal.setOnClickListener(this);
            deci.setOnClickListener(this);
            del.setOnClickListener(this);
        }
        catch(Exception e){

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onClick(View arg0) {
        Editable str = disp.getText();
        switch(arg0.getId()) {
            case R.id.deci:
                DecimalPress(str);
                break;

            case R.id.zero:
                NumberPress(0, str);
                break;

            case R.id.one:
                NumberPress(1, str);
                break;

            case R.id.two:
                NumberPress(2, str);
                break;

            case R.id.three:
                NumberPress(3, str);
                break;

            case R.id.four:
                NumberPress(4, str);
                break;

            case R.id.five:
                NumberPress(5, str);
                break;

            case R.id.six:
                NumberPress(6, str);
                break;

            case R.id.seven:
                NumberPress(7, str);
                break;

            case R.id.eight:
                NumberPress(8, str);
                break;

            case R.id.nine:
                NumberPress(9, str);
                break;

            case R.id.clear:
                ClearPress(str);
                break;

            case R.id.del:
                DeletePress(str);
                break;

            case R.id.add:
                OperationPress("+", str);
                break;

            case R.id.sub:
                OperationPress("-", str);
                break;

            case R.id.mul:
                OperationPress("*", str);
                break;

            case R.id.div:
                OperationPress("/", str);
                break;

            case R.id.equal:
                EqualPress(str);
                break;
        }
    }

    private static boolean isNumeric(String str)
    {
        if (str.matches("\\d+(\\.\\d*)?"))
            return true;
        else
            return str.isEmpty();
    }

    private static boolean hasDecimal(String str) {
        return str.contains(".");
    }

    private Editable clearDisp(Editable str) {
        str.clear();
        disp.setText(str);
        return str;
    }

    private void clearVariables() {
        operation = "";
        storedOp = "";
        opLastPressed = false;
        equalsLastPressed = false;
        res = 0;
        repeatedOp = 0;
    }

    private Editable resConvert(Editable str) {
        str.clear();
        if ( res - (int)res == 0)
            str.append(Integer.toString((int)res));
        else
            str.append(Float.toString(res));
        return str;
    }

    private void NumberPress (int value, Editable str) {
        // conditions:
        //      should only append if str in its entirety is a number
        //      0 should only be appended after a decimal
        //      ignore if there are already more than 10 digits (11 if decimal)

        // check if str is a number
        // if not (or if equals was pressed last), clear and append value
        if ( !isNumeric(str.toString()) || equalsLastPressed ) {
            ClearPress(str);
            str = clearDisp(str);
            if ( value != 0 ) {
                str.append((char) (value + '0'));
                disp.setText(str);
            }
        }
        // if the last thing pressed was an operation, clear the screen before appending
        else if ( opLastPressed ) {
            str = clearDisp(str);
            if ( value != 0 ) {
                str.append((char) (value + '0'));
                disp.setText(str);
            }
        }
        // check if str has a decimal and the length of str is less than 11
        else if ( hasDecimal(str.toString()) && str.length() < 11 ) {
            str.append((char) (value + '0'));
            disp.setText(str);
        }
        // if no decimal and length of str is less than 10
        else if ( str.length() < 10 ) {
            if ( value == 0 && str.toString().equals("") ) return;
            str.append((char) (value + '0'));
            disp.setText(str);
        }
        opLastPressed = false;
        equalsLastPressed = false;
    }


    private void OperationPress (String op, Editable str) {
        // conditions:
        //      should check if operation is non-empty (compute and reassign)
        //      if str is empty, assume it's 0
        //      convert firstOp to 0 if necessary

        // if str is not numeric, clear the display, assign 0 to firstOp, and set operation
        if ( !isNumeric(str.toString()) ) {
            str = clearDisp(str);

            operation = op;
            storedOp = "0";
        }
        // check if operation is already defined
        else if ( operation.equals("") ) {
            operation = op;

            // check if str is empty
            if ( str.length() == 0 ) storedOp = "0";
            else storedOp = str.toString();
        }
        else {
            // let Equal() deal with empty string
            Equal(str);

            // store the result in firstOp
            storedOp = Float.toString(res);
            operation = op;
        }
        opLastPressed = true;
        equalsLastPressed = false;

    }

    private void DeletePress (Editable str) {
        // conditions:
        //      also delete operator

        // if operation (or equals) was pressed prior, delete operation rather than operand
        if ( opLastPressed || equalsLastPressed ) {
            if ( equalsLastPressed )
                clearDisp(str);
            opLastPressed = false;
            equalsLastPressed = false;
            operation = "";
            storedOp = "";
        }
        // if current entering for secondOp, and str is empty, delete operation
        else if ( str.toString().isEmpty() && !operation.equals("") ) {
            operation = "";

            // display previous operand
            str.append(storedOp);
            disp.setText(str);
        }
        // if string is not empty and is numeric, delete the previous digit
        else if ( !str.toString().isEmpty() && isNumeric(str.toString()) ) {
            str.delete(str.length()-1, str.length());
            // if we've just deleted a decimal, and what's left is "0", delete that too
            if ( str.toString().equals("0") ) str.clear();
            disp.setText(str);
        }
        // if string is not empty and is not numeric, clear the display
        else if ( !str.toString().isEmpty() ) {
            clearDisp(str);
        }
    }

    private void ClearPress (Editable str) {
        // conditions:
        //      resets all variables to default values

        // clear the variables
        clearVariables();

        // clear the display
        clearDisp(str);

    }

    private void DecimalPress (Editable str) {
        // conditions:
        //      only one decimal max
        //      append 0 first if empty string
        //      if not numeric, clear, append 0, then append "."

        // if not numeric, or an operation/equals was last pressed
        // clear screen, append "0."
        if ( !isNumeric(str.toString()) || opLastPressed || equalsLastPressed) {
            str = clearDisp(str);
            str.append("0.");
            disp.setText(str);
        }
        // if str is empty, append 0 first
        else if ( str.toString().isEmpty() ) {
            str.append("0.");
            disp.setText(str);
        }
        // if str doesn't already have a decimal, then append "."
        else if ( !str.toString().contains(".") ) {
            str.append(".");
            disp.setText(str);
        }

        opLastPressed = false;
        equalsLastPressed = false;

    }

    private void EqualPress (Editable str) {
        Equal(str);
        equalsLastPressed = true;
    }

    private void Equal (Editable str) {
        // conditions:
        //      assign 0 to empty str

        // if the first operand is empty
        if ( storedOp.isEmpty() ) {
            // if the input is also empty, res = 0
            if (str.toString().isEmpty() ) {
                res = 0;
                str.append("0");
                disp.setText(str);
            }
            // if the input is a number
            else if ( isNumeric(str.toString()) ) {
                res = Float.parseFloat(str.toString());
            }
            // if the input isn't a number, set res and disp to 0
            else {
                res = 0;
                clearDisp(str);
            }
        }
        // first operand isn't empty, but is not numeric
        else if ( !isNumeric(storedOp) ) {
            clearVariables();
            str = clearDisp(str);
            str.append("ERROR");
            disp.setText(str);
        }
        // second operand isn't empty and numeric
        else if ( !isNumeric(str.toString()) && !str.toString().isEmpty() ) {
            clearVariables();
            str = clearDisp(str);
            str.append("ERROR");
            disp.setText(str);
        }
        // both operands aren't empty and are numeric
        else {
            float firstOp, secondOp;

            // if equals was pressed just before, repeat the last operation
            if ( equalsLastPressed )
                firstOp = Float.parseFloat(str.toString());
            else
                firstOp = Float.parseFloat(storedOp);

            // if secondOp is empty
            if ( opLastPressed || str.toString().isEmpty() )
                secondOp = 0;
            else if ( equalsLastPressed )
                secondOp = repeatedOp;
            else
                secondOp = Float.parseFloat(str.toString());
            repeatedOp = secondOp;
            str = clearDisp(str);

            Log.i("Equals 1", "firstOp = " + firstOp);
            Log.i("Equals 2", "secondOp = " + secondOp);
            Log.i("Equals 3", "operation = " + operation);

            if ( operation.matches("\\+") ) {
                res = firstOp + secondOp;
                str = resConvert(str);
            }
            else if ( operation.matches("-") ) {
                res = firstOp - secondOp;
                str = resConvert(str);
            }
            else if ( operation.matches("\\*") ) {
                res = firstOp * secondOp;
                str = resConvert(str);
            }
            else if ( operation.matches("/") ) {
                if ( secondOp == 0 ) {
                    res = 0;
                    str.append("ERROR");
                }
                else {
                    res = firstOp / secondOp;
                    str = resConvert(str);
                }
            }
            else {
                clearVariables();
                str.append("ERROR");
            }
            disp.setText(str);
        }

    }

}
