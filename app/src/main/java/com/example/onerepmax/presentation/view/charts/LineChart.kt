package com.example.onerepmax.presentation.view.charts

import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.onerepmax.domain.entity.HistoricalMaxRepRecord
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter

@Composable
fun LineChart (
    data : List<HistoricalMaxRepRecord>,
    lineColor: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.secondary
) {
    AndroidView(
        modifier = Modifier
            .height(300.dp)
            .fillMaxWidth(),
        factory = { context ->
            createLineChart(context, data, lineColor.toArgb(), textColor.toArgb())
        }
    )
}

fun createLineChart(
    context: Context,
    data: List<HistoricalMaxRepRecord>,
    lineColor: Int,
    textColor: Int
): LineChart {
    val chart = LineChart(context)
    val entries: MutableList<Entry> = mutableListOf()
    val xLabels: MutableList<String> = mutableListOf()

    for (i in data.indices) {
        val historical = data[i]
        entries.add(Entry(i.toFloat(), historical.maxRep.toFloat()))
        xLabels.add(historical.date)
    }

    val dataSet = LineDataSet(entries, "").apply {
        color = lineColor
        setDrawValues(false)
        circleColors = listOf(lineColor)
        circleHoleColor = lineColor
    }

    chart.apply {
        this.data = LineData(dataSet)
        legend.isEnabled = false
        axisRight.isEnabled = false
        setDrawBorders(true)
        setBorderColor(textColor)
    }

    chart.xAxis.apply {
        isGranularityEnabled = true
        granularity = 2f
        labelCount = xLabels.size
        valueFormatter = IndexAxisValueFormatter(xLabels)
        setDrawLabels(true)
        position = XAxis.XAxisPosition.BOTTOM
        labelRotationAngle = -90f
        this.textColor = textColor
    }

    chart.axisLeft.apply {
        isGranularityEnabled = true
        granularity = 15f
        this.textColor = textColor
        valueFormatter = LbsLabelFormatter()
    }

    chart.invalidate()
    return chart
}