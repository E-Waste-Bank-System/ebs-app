package com.example.ebs.ui.components.shapes

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ebs.ui.components.structures.CenterColumn
import com.example.ebs.ui.components.structures.CenterRow
import com.example.ebs.ui.components.texts.TextContentL
import com.example.ebs.ui.components.texts.TextContentM

@Composable
fun SimpleTable(
    data: List<List<String>>,
    modifier: Modifier = Modifier
) {
    CenterColumn(modifier = modifier) {
        data.forEachIndexed { index, row ->
            CenterRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                row.forEachIndexed { cellIndex, cell ->
                    TextContentM(
                        text = cell,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp),
                        textAlign = TextAlign.Start
                    )
                    if (cellIndex < row.lastIndex) {
                        TextContentL("|")
                    }
                }
            }
            if (index < data.lastIndex) {
                HorizontalDivider()
            }
        }
    }
}