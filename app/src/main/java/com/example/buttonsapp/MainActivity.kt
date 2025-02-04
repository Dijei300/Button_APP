package com.example.buttonsapp

import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var counterTextViews: List<TextView>
    private val labels = listOf(
        "QV", "Pré-angariação", "Estudo de Mercado", "CMI assinado", "Transação",
        "QC", "Qualificação", "Visitas", "Propostas", "Transação"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Row IDs
        val rowIds = listOf(
            R.id.row1, R.id.row2, R.id.row3, R.id.row4, R.id.row5,
            R.id.row6, R.id.row7, R.id.row8, R.id.row9, R.id.row10
        )

        // Find all counter text views
        counterTextViews = rowIds.map { findViewById<View>(it).findViewById(R.id.text_counter) }

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

        // Reset button click event
        findViewById<Button>(R.id.button_action1).setOnClickListener {
            resetCounters()
        }

        // Export CSV button click event
        findViewById<Button>(R.id.button_action2).setOnClickListener {
            exportToCSV()
        }

        // Expand/collapse sections
        setupExpandableSection(R.id.title_proprietario, R.id.container_proprietario)
        setupExpandableSection(R.id.title_comprador, R.id.container_comprador)
    }

    // Function to reset all counters
    private fun resetCounters() {
        for (counter in counterTextViews) {
            counter.text = "0"
        }
        Toast.makeText(this, "Counters Reset", Toast.LENGTH_SHORT).show()
    }

    // Function to export data to CSV
    private fun exportToCSV() {
        val csvData = StringBuilder()

        // Cabeçalho do arquivo CSV
        csvData.append("Layout,Name,Count\n")

        // Adicionando dados do layout 'Proprietario'
        for (i in 0..4) {  // As primeiras 5 linhas pertencem ao Proprietario
            val count = counterTextViews[i].text.toString()
            csvData.append("Proprietario,${labels[i]},$count\n")
        }

        // Adicionando dados do layout 'Comprador'
        for (i in 5..9) {  // As últimas 5 linhas pertencem ao Comprador
            val count = counterTextViews[i].text.toString()
            csvData.append("Comprador,${labels[i]},$count\n")
        }

        try {
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val fileName = "counts_export_$timeStamp.csv"
            val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName)

            val writer = OutputStreamWriter(FileOutputStream(file), "UTF-8")
            writer.append(csvData.toString())
            writer.flush()
            writer.close()

            Toast.makeText(this, "CSV Exported: ${file.absolutePath}", Toast.LENGTH_LONG).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Failed to Export CSV", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to toggle sections
    private fun setupExpandableSection(titleId: Int, containerId: Int) {
        val titleTextView = findViewById<TextView>(titleId)
        val containerLayout = findViewById<LinearLayout>(containerId)

        titleTextView.setOnClickListener {
            if (containerLayout.visibility == View.VISIBLE) {
                containerLayout.visibility = View.GONE
                titleTextView.text = titleTextView.text.toString().replace("▲", "▼")
            } else {
                containerLayout.visibility = View.VISIBLE
                titleTextView.text = titleTextView.text.toString().replace("▼", "▲")
            }
        }
    }
}
