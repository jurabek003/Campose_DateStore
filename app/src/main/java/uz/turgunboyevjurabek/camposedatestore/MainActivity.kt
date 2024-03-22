package uz.turgunboyevjurabek.camposedatestore

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import uz.turgunboyevjurabek.camposedatestore.DataStore.DataStore
import uz.turgunboyevjurabek.camposedatestore.ui.theme.CamposeDateStoreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CamposeDateStoreTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {

    val context = LocalContext.current
    val dataStore = DataStore(context)
    val scope = rememberCoroutineScope()
    val saveString = dataStore.getToken.collectAsState(initial = "")
    val lastValue=saveString.value
    var count by remember {
        mutableIntStateOf(
            if (saveString.value != "") {
                lastValue.toInt()
            } else {
               0
            }
        )
    }
    Toast.makeText(context, "$count", Toast.LENGTH_SHORT).show()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            val son = count++
            scope.launch {
                dataStore.saveToken("DataStore-> $son")
            }
        }) {
            Text(text = "Save at DataStore")
        }
        Text(
            text = saveString.value,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            fontSize = 20.sp
        )
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CamposeDateStoreTheme {
        Greeting()
    }
}