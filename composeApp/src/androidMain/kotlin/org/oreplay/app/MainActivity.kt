package org.oreplay.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.retainedComponent
import io.ktor.client.engine.okhttp.OkHttp
import okhttp3.OkHttpClient
import org.oreplay.app.model.EventClient
import org.oreplay.app.model.createHTTPClient
import org.oreplay.app.viewmodel.RootComponent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = retainedComponent {
            RootComponent(it)
        }
        setContent {
            App(
                client = remember {
                    EventClient(createHTTPClient(OkHttp.create()))
                },
                root
            )
        }
    }
}