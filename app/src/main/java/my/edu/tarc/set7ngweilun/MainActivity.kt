package my.edu.tarc.set7ngweilun

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Reset all fields
        reset("All")

        // Calculate Tax
        val txtName: TextView = findViewById(R.id.txtName)
        val txtAge: TextView = findViewById(R.id.txtAge)
        val txtTaxableIncome: TextView = findViewById(R.id.txtTaxableIncome)
        val btnCalculate: Button = findViewById(R.id.btnCalculate)
        val txtResultName: TextView = findViewById(R.id.txtResultName)
        val txtResultAge: TextView = findViewById(R.id.txtResultAge)
        val txtResultIncome: TextView = findViewById(R.id.txtResultIncome)
        val txtResult2015Tax: TextView = findViewById(R.id.txtResult2015Tax)
        val txtResult2016Tax: TextView = findViewById(R.id.txtResult2016Tax)

        btnCalculate.setOnClickListener {
            val error: Boolean = validation()
            var tax2015: Double = 0.0;
            var tax2016: Double = 0.0;

            if (!error) {
                txtResultName.text = txtName.text.toString()
                txtResultAge.text = txtAge.text.toString()
                txtResultIncome.text = "RM " + txtTaxableIncome.text.toString().toDouble().toString()

                tax2015 = calculateTax("2015")
                tax2016 = calculateTax("2016")
                txtResult2015Tax.text = "RM " + tax2015.toString()
                txtResult2016Tax.text = "RM " + tax2016.toString()
                reset("input")
            }
        }

        // Contact
        val txtContact: TextView = findViewById(R.id.txtContact)

        txtContact.setOnClickListener {
            val call = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+0123456789"))
            startActivity(call)
        }
    }

    // Calculation
    private fun calculateTax(type: String): Double {
        val txtAge: TextView = findViewById(R.id.txtAge)
        val txtTaxableIncome: TextView = findViewById(R.id.txtTaxableIncome)
        val tempIncome = txtTaxableIncome.text.toString().toDouble()
        var taxRate: Int = 0;
        var tax: Double = 0.0;

        when {
            tempIncome in 0.0..5000.0 -> taxRate = 0
            tempIncome in 5001.0..20000.0 -> taxRate = 1
            tempIncome in 20001.0..35000.0 -> {
                taxRate = if (type == "2015") 6
                else 5
            }
            tempIncome >= 35001 -> {
                taxRate = if (type == "2015") 11
                else 10
            }
        }

        tax = tempIncome * taxRate / 100

        if (txtAge.text.toString().toInt() < 26 && tax >= 300) tax -= 300.0
        else if (txtAge.text.toString().toInt() < 26 && tax < 300) tax = 0.0

        return tax
    }

    // Validation
    private fun validation(): Boolean {
        val txtName: TextView = findViewById(R.id.txtName)
        val txtAge: TextView = findViewById(R.id.txtAge)
        val txtTaxableIncome: TextView = findViewById(R.id.txtTaxableIncome)
        var error: Boolean = false

        if (txtName.text.isEmpty()) {
            Toast.makeText(this, "Invalid Name", Toast.LENGTH_SHORT).show()
            error = true
        } else if (txtAge.text.isEmpty() || txtAge.text.toString()
                .toInt() > 100 || txtAge.text.toString().toInt() < 0
        ) {
            Toast.makeText(this, "Invalid Age", Toast.LENGTH_SHORT).show()
            error = true
        } else if (txtTaxableIncome.text.isEmpty() || txtTaxableIncome.text.toString()
                .toDouble() < 0
        ) {
            Toast.makeText(this, "Invalid Taxable Income", Toast.LENGTH_SHORT).show()
            error = true
        }

        return error;
    }

    // Reset fields
    private fun reset(type: String) {
        val txtName: TextView = findViewById(R.id.txtName)
        val txtAge: TextView = findViewById(R.id.txtAge)
        val txtTaxableIncome: TextView = findViewById(R.id.txtTaxableIncome)
        val txtResultName: TextView = findViewById(R.id.txtResultName)
        val txtResultAge: TextView = findViewById(R.id.txtResultAge)
        val txtResultIncome: TextView = findViewById(R.id.txtResultIncome)
        val txtResult2015Tax: TextView = findViewById(R.id.txtResult2015Tax)
        val txtResult2016Tax: TextView = findViewById(R.id.txtResult2016Tax)


        txtName.text = ""
        txtAge.text = ""
        txtTaxableIncome.text = ""
        if (type == "All") {
            txtResultName.text = ""
            txtResultAge.text = ""
            txtResultIncome.text = ""
            txtResult2015Tax.text = ""
            txtResult2016Tax.text = ""
        }
    }
}