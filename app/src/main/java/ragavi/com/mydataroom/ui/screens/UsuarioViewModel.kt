package ragavi.com.mydataroom.ui.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ragavi.com.mydataroom.data.AppDatabase
import ragavi.com.mydataroom.data.Usuario

class UsuarioViewModel (application: Application) : AndroidViewModel(application) {
    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "db_usuarios"
    ).build()

    private val dao = db.usuarioDao()

    val usuarios = dao.obtenerUsuarios().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun agregarUsuario(nombre: String) {
        viewModelScope.launch {
            dao.insertarUsuario(Usuario(nombre = nombre))
        }
    }

    fun eliminarUsuario(usuario: Usuario) {
        viewModelScope.launch {
            dao.eliminarUsuario(usuario)
        }
    }
}