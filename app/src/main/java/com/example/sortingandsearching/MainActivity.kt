package com.example.sortingandsearching

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sortingandsearching.ui.theme.SORTINGANDSEARCHINGTheme
import kotlin.random.Random
import kotlin.system.measureTimeMillis

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Su dung ten Theme tu du an cua ban
            SORTINGANDSEARCHINGTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DemoScreen()
                }
            }
        }
    }
}

//  Man hinh chinh
@Composable
fun DemoScreen() {
    // Cac bien State de luu tru dau vao
    val sortInput = remember { mutableStateOf("") }
    val graphInput = remember { mutableStateOf("") }
    val startNodeInput = remember { mutableStateOf("") }

    //
    val resultLog = remember { mutableStateOf("Chon mot demo de chay...") }
    // val scrollState = rememberScrollState() // Da xoa

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing) // Da them
            .padding(16.dp)
        // .verticalScroll(scrollState) // Da xoa
    ) {

        // --- PHAN 1: SAP XEP (SORTING) ---
        Text(text = "SắpXếp(Sorting)", style = MaterialTheme.typography.headlineSmall)

        // O nhap lieu cho mang
        OutlinedTextField(
            value = sortInput.value,
            onValueChange = { sortInput.value = it },
            label = { Text("Nhap mang (vd: 8, 3, 5, 1)") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )

        // Cac nut bam cho Sorting
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = {
                try {
                    val arr = parseInputArray(sortInput.value)
                    val time = measureTimeMillis { insertionSort(arr) }
                    resultLog.value = "--- Insertion Sort ---\nKet qua: ${arr.contentToString()}\nTHOI GIAN: $time ms"
                } catch (e: Exception) { resultLog.value = "Loi dau vao mang: ${e.message}" }
            }) { Text("Insertion") }

            // --- SUA DOI: XOA GIAI THICH BIG O ---
            Button(onClick = {
                try {
                    val arr = parseInputArray(sortInput.value)
                    val time = measureTimeMillis { mergeSort(arr) }
                    resultLog.value = "--- Merge Sort ---\nKet qua: ${arr.contentToString()}\nTHOI GIAN: $time ms"
                } catch (e: Exception) { resultLog.value = "Loi dau vao mang: ${e.message}" }
            }) { Text("Merge") }

            // --- SUA DOI: XOA GIAI THICH BIG O ---
            Button(onClick = {
                try {
                    val arr = parseInputArray(sortInput.value)
                    val time = measureTimeMillis { quickSort(arr, 0, arr.size - 1) }
                    resultLog.value = "--- Quick Sort ---\nKet qua: ${arr.contentToString()}\nTHOI GIAN: $time ms"
                } catch (e: Exception) { resultLog.value = "Loi dau vao mang: ${e.message}" }
            }) { Text("Quick") }
        }


        Button(
            onClick = {
                    val list = (0..20000).shuffled(Random(System.currentTimeMillis())) 
                val arrInsertion = list.toIntArray()
                val arrQuick = list.toIntArray()
                val arrMerge = list.toIntArray()

                resultLog.value = "Dang chay Stress Test"

                val timeInsertion = measureTimeMillis { insertionSort(arrInsertion) }
                val timeQuick = measureTimeMillis { quickSort(arrQuick, 0, arrQuick.size - 1) }
                val timeMerge = measureTimeMillis { mergeSort(arrMerge) }


                resultLog.value = " STRESS TEST \n" +
                        "Insertion Sort (O(n²)) took: $timeInsertion ms\n" +
                        "Merge Sort (O(n log n)) took: $timeMerge ms\n" +
                        "Quick Sort (O(n log n)) took: $timeQuick ms"
            },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        ) {
            Text("Stress Test")
        }

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        // --- PHAN 2: TIM KIEM (SEARCHING) --- (Giu nguyen)
        Text(text = "Phần 2: Tìm kiếm (Searching)️", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = graphInput.value,
            onValueChange = { graphInput.value = it },
            label = { Text("Nhap do thi (Dinh dang: A: B, C)") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp).height(150.dp)
        )
        OutlinedTextField(
            value = startNodeInput.value,
            onValueChange = { startNodeInput.value = it },
            label = { Text("Nut bat dau (vd: 0)") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                try {
                    val graph = parseInputGraph(graphInput.value)
                    val start = startNodeInput.value.trim()
                    val path = dfs(graph, start)
                    resultLog.value = "--- DFS (Uu tien sau) ---\n$path"
                } catch (e: Exception) { resultLog.value = "Loi do thi/nut bat dau: ${e.message}" }
            }) { Text("Chay DFS") }

            Button(onClick = {
                try {
                    val graph = parseInputGraph(graphInput.value)
                    val start = startNodeInput.value.trim()
                    val path = bfs(graph, start)
                    resultLog.value = "--- BFS (Uu tien rong) ---\n$path"
                } catch (e: Exception) { resultLog.value = "Loi do thi/nut bat dau: ${e.message}" }
            }) { Text("Chay BFS") }
        }

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        // --- KHU VUC HIEN THI KET QUA (LOG) --- (Giu nguyen)
        Text(
            text = "KET QUA DEMO:",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = resultLog.value,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .weight(1f) // Da sua
                .padding(8.dp),
            fontFamily = FontFamily.Monospace,
            fontSize = 14.sp,
            lineHeight = 18.sp
        )
    }
}