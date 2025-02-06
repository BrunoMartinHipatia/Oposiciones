package com.example.oposiciones.compose

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.oposiciones.ExamenesContainerActivity
import com.example.oposiciones.ExamenesGuardadosActivity
import com.example.oposiciones.FlashCardsActivity
import com.example.oposiciones.R
import com.example.oposiciones.component.CustomDrawerState
import com.example.oposiciones.component.NavigationItem
import com.example.oposiciones.component.isOpened
import com.example.oposiciones.component.opposite

import kotlin.math.roundToInt

@Composable
@Preview
fun MainScreen() {


    var drawerState by remember { mutableStateOf(CustomDrawerState.Closed) }
    var selectedNavigationItem by remember { mutableStateOf(NavigationItem.Home) }

    val configuration = LocalConfiguration.current
    val density = LocalDensity.current.density

    val screenWidth = remember {
        derivedStateOf { (configuration.screenWidthDp * density).roundToInt() }
    }
    val offsetValue by remember { derivedStateOf { (screenWidth.value / 4.5).dp } }
    val animatedOffset by animateDpAsState(
        targetValue = if (drawerState.isOpened()) offsetValue else 0.dp,
        label = "Animated Offset"
    )
    val animatedScale by animateFloatAsState(
        targetValue = if (drawerState.isOpened()) 0.9f else 1f,
        label = "Animated Scale"
    )

    BackHandler(enabled = drawerState.isOpened()) {
        drawerState = CustomDrawerState.Closed
    }

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
            .statusBarsPadding()
            .navigationBarsPadding()
            .fillMaxSize()
    ) {
        CustomDrawer(
            selectedNavigationItem = selectedNavigationItem,
            onNavigationItemClick = {
                selectedNavigationItem = it
            },
            onCloseClick = { drawerState = CustomDrawerState.Closed }
        )
        MainContent(
            modifier = Modifier
                .offset(x = animatedOffset)
                .scale(scale = animatedScale)
           ,
            drawerState = drawerState,
            onDrawerClick = { drawerState = it },
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable


fun MainContent(
    modifier: Modifier = Modifier,
    drawerState: CustomDrawerState,
    onDrawerClick: (CustomDrawerState) -> Unit
) {
    val context = LocalContext.current
    Scaffold(
        modifier = modifier
            .clickable(enabled = drawerState == CustomDrawerState.Opened) {
                onDrawerClick(CustomDrawerState.Closed)
            },
        topBar = {
            TopAppBar(
                title = { Text(text = "Home") },
                navigationIcon = {
                    IconButton(onClick = { onDrawerClick(drawerState.opposite()) }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu Icon"
                        )
                    }
                }
            )
        }
    ) {
        Box(


        ) {
           Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
               Row(
                   modifier = Modifier

                       .fillMaxWidth(),
                   horizontalArrangement = Arrangement.SpaceAround,
               ) {
                   Card(
                       onClick = {

                               context.startActivity(Intent(context, ExamenesContainerActivity::class.java))



                       },
                       modifier = Modifier.size(160.dp),
                       elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                   ) {
                       Column(
                           modifier = Modifier.fillMaxSize(),
                           horizontalAlignment = Alignment.CenterHorizontally
                       ) {
                           Image(
                               painter = painterResource(R.drawable.opo),
                               contentDescription = "Imagen Examenes",
                               modifier = Modifier
                                   .weight(1f)
                               ,

                               )
                           Text(
                               text = "Examenes",
                               fontSize = MaterialTheme.typography.titleMedium.fontSize,
                               fontWeight = FontWeight.Medium,
                               modifier = Modifier
                                   .padding(8.dp)  ,
                               color = MaterialTheme.colorScheme.onSurface
                           )
                       }
                   }
                   Card(
                       onClick = {

                           context.startActivity(Intent(context, ExamenesGuardadosActivity::class.java))



                       },
                       modifier = Modifier.size(160.dp).padding(),
                       elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                   ) {
                       Column(
                           modifier = Modifier.fillMaxSize(),
                           horizontalAlignment = Alignment.CenterHorizontally
                       ) {
                           Image(
                               painter = painterResource(R.drawable.opo),
                               contentDescription = "Imagen Examenes",
                               modifier = Modifier
                                   .weight(1f)
                                   .fillMaxWidth(),

                               )
                           Text(
                               text = "Mis resultados",
                               fontSize = MaterialTheme.typography.titleMedium.fontSize,
                               fontWeight = FontWeight.Medium,
                               modifier = Modifier
                                   .padding(8.dp)  ,
                               color = MaterialTheme.colorScheme.onSurface
                           )
                       }
                   }
               }
               Row(
                   modifier = Modifier
                       .padding(top = 90.dp)
                       .fillMaxWidth(),
                   horizontalArrangement = Arrangement.SpaceAround,
               ) {
                   Card(
                       onClick ={
                           context.startActivity(Intent(context, FlashCardsActivity ::class.java))
                       },
                       modifier = Modifier.size(160.dp),
                       elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                   ) {
                       Column(
                           modifier = Modifier.fillMaxSize(),
                           horizontalAlignment = Alignment.CenterHorizontally
                       ) {
                           Image(
                               painter = painterResource(R.drawable.opo),
                               contentDescription = "Imagen Examenes",
                               modifier = Modifier
                                   .weight(1f)
                               ,

                               )
                           Text(
                               text = "Flashcards",
                               fontSize = MaterialTheme.typography.titleMedium.fontSize,
                               fontWeight = FontWeight.Medium,
                               modifier = Modifier
                                   .padding(8.dp)  ,
                               color = MaterialTheme.colorScheme.onSurface
                           )
                       }
                   }

               }

           }

        }
    }
}