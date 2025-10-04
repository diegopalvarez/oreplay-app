package org.oreplay.app.view.classes

    import androidx.compose.foundation.layout.Arrangement
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.material3.Button
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.LaunchedEffect
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.remember
    import androidx.compose.runtime.setValue
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import org.oreplay.app.model.Class
    import org.oreplay.app.model.EventClient
    import org.oreplay.app.model.util.onError
    import org.oreplay.app.model.util.onSuccess
    import org.oreplay.app.viewmodel.ClassScreenComponent
    import org.oreplay.app.viewmodel.ClassScreenEvent

@Composable
    fun ClassScreen(component: ClassScreenComponent, client: EventClient) {
        var classList by remember {
            mutableStateOf<List<Class>>(emptyList())
        }

        var errorMessage by remember {
            mutableStateOf<String>("No error")
        }

        LaunchedEffect(component.stage) {
            client.getClasses(component.stage)
                .onSuccess {
                    classList = it
                }
                .onError {
                    errorMessage = "Something went wrong"
                }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(errorMessage)
            Text(classList.size.toString())

            Text("Classes")

            for(class_ in classList){
                Button(onClick = {
                    component.onEvent(ClassScreenEvent.ClickEvent, component.stage, class_)
                }){
                    Text(class_.longName)
                }
            }
        }
    }