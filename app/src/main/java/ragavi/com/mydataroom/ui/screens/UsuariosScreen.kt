package ragavi.com.mydataroom.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun UsuarioScreen(
    modifier: Modifier = Modifier,
    viewModel: UsuarioViewModel = viewModel()
) {
    val listaUsuarios by viewModel.usuarios.collectAsState()
    var textoInput by remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = textoInput,
                onValueChange = { textoInput = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Escribe un nombre") }
            )
            Button(onClick = {
                if (textoInput.isNotBlank()) {
                    viewModel.agregarUsuario(textoInput)
                    textoInput = ""
                }
            }) {
                Text("Guardar")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(listaUsuarios) { usuario ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = usuario.nombre)
                        TextButton(onClick = { viewModel.eliminarUsuario(usuario) }) {
                            Text("Eliminar", color = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
        }
    }
}

