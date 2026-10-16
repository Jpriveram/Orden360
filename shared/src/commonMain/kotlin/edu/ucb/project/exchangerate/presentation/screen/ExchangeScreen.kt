package edu.ucb.project.exchangerate.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import edu.ucb.project.exchangerate.presentation.viewmodel.ExchangeViewModel
import kotlinfirstproject.shared.generated.resources.Res
import kotlinfirstproject.shared.generated.resources.exchange_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ExchangeScreen(viewModel: ExchangeViewModel = koinViewModel()) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val message = viewModel.message.collectAsState()
        Text(stringResource(Res.string.exchange_title))
        Text(message.value)
    }

}
