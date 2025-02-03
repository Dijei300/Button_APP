package com.example.buttonsapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // List of labels for each row
        val labels = listOf("OV", "Pré-angariação", "Estudo de Mercado", "CMI assinado", "Transação",
            "QC", "Qualificação", "Visitas", "Propostas", "Transação")

        // Find all row layouts and set values dynamically
        val rowIds = listOf(
            R.id.row1, R.id.row2, R.id.row3, R.id.row4, R.id.row5,
            R.id.row6, R.id.row7, R.id.row8, R.id.row9, R.id.row10
        )

        for (i in rowIds.indices) {
            val rowView = findViewById<View>(rowIds[i])
            val labelTextView = rowView.findViewById<TextView>(R.id.text_label)
            val counterTextView = rowView.findViewById<TextView>(R.id.text_counter)
            val plusButton = rowView.findViewById<Button>(R.id.button_plus)
            val minusButton = rowView.findViewById<Button>(R.id.button_minus)

            // Set label text
            labelTextView.text = labels[i]

            // Plus button click event
            plusButton.setOnClickListener {
                val count = counterTextView.text.toString().toInt() + 1
                counterTextView.text = count.toString()
            }

            // Minus button click event
            minusButton.setOnClickListener {
                val count = counterTextView.text.toString().toInt()
                if (count > 0) {
                    counterTextView.text = (count - 1).toString()
                }
            }
        }

        // Expand/collapse functionality
        setupExpandableSection(R.id.title_proprietario, R.id.container_proprietario)
        setupExpandableSection(R.id.title_comprador, R.id.container_comprador)
    }

    private fun setupExpandableSection(titleId: Int, containerId: Int) {
        val titleTextView = findViewById<TextView>(titleId)
        val containerLayout = findViewById<LinearLayout>(containerId)

        titleTextView.setOnClickListener {
            if (containerLayout.visibility == View.VISIBLE) {
                containerLayout.visibility = View.GONE
                titleTextView.text = titleTextView.text.toString().replace("▲", "▼") // Collapse
            } else {
                containerLayout.visibility = View.VISIBLE
                titleTextView.text = titleTextView.text.toString().replace("▼", "▲") // Expand
            }
        }
    }
}
