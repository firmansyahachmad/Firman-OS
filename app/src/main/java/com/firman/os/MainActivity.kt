package com.firman.os

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.delay
import java.util.Locale

private val Bg = Color(0xFF090B10)
private val Card = Color(0xFF141821)
private val Accent = Color(0xFF6DE1FF)
private val Muted = Color(0xFF9AA5B5)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme { FirmanDashboard() } }
    }
}

@Composable
fun FirmanDashboard() {
    val context = LocalContext.current
    val fused = remember { LocationServices.getFusedLocationProviderClient(context) }
    var speed by remember { mutableDoubleStateOf(0.0) }
    var distanceKm by remember { mutableDoubleStateOf(0.0) }
    var seconds by remember { mutableLongStateOf(0L) }
    var riding by remember { mutableStateOf(false) }
    val fuelEfficiency = 40.0
    val fuelPrice = 10000.0
    val liters = distanceKm / fuelEfficiency
    val cost = liters * fuelPrice

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    LaunchedEffect(riding) {
        while (riding) {
            delay(1000)
            seconds++
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                fused.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null).addOnSuccessListener { loc ->
                    if (loc != null) {
                        speed = if (loc.hasSpeed()) (loc.speed * 3.6).coerceAtLeast(0.0) else 0.0
                        distanceKm += speed / 3600.0
                    }
                }
            }
        }
    }

    Surface(color = Bg, modifier = Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().padding(18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text("FIRMAN.OS", color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Black)
                    Text("SMART RIDE DASHBOARD • V0.1", color = Muted, fontSize = 11.sp)
                }
                Text(if (riding) "● RIDE ACTIVE" else "● READY", color = if (riding) Accent else Muted, fontWeight = FontWeight.Bold)
            }

            Row(Modifier.weight(1f), horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                Box(Modifier.weight(1.35f).fillMaxHeight().background(Card, RoundedCornerShape(22.dp)), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(String.format(Locale.US, "%.0f", speed), color = Color.White, fontSize = 92.sp, fontWeight = FontWeight.Black)
                        Text("KM/H", color = Accent, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(18.dp))
                        Text("GPS SPEED", color = Muted, fontSize = 12.sp)
                    }
                }

                Column(Modifier.weight(1f).fillMaxHeight(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Metric("TRIP TIME", "%02d:%02d:%02d".format(seconds / 3600, (seconds % 3600) / 60, seconds % 60), Modifier.weight(1f))
                    Metric("DISTANCE", String.format(Locale.US, "%.2f km", distanceKm), Modifier.weight(1f))
                    Metric("EST. FUEL", String.format(Locale.US, "%.2f L", liters), Modifier.weight(1f))
                    Metric("EST. COST", "Rp %, .0f".format(cost).replace(" ", ""), Modifier.weight(1f))
                }

                Column(Modifier.weight(1.1f).fillMaxHeight(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Box(Modifier.weight(1f).fillMaxWidth().background(Card, RoundedCornerShape(22.dp)).padding(18.dp)) {
                        Column {
                            Text("NAVIGATION", color = Muted, fontSize = 12.sp)
                            Spacer(Modifier.height(10.dp))
                            Text("Ready for Google Maps", color = Color.White, fontSize = 21.sp, fontWeight = FontWeight.Bold)
                            Text("Destination entry comes next.", color = Muted, fontSize = 12.sp)
                        }
                    }
                    Button(
                        onClick = { context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=Jakarta")).apply { setPackage("com.google.android.apps.maps") }) },
                        modifier = Modifier.fillMaxWidth().height(56.dp)
                    ) { Text("OPEN GOOGLE MAPS") }
                    Button(
                        onClick = { riding = !riding },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = if (riding) Color(0xFF7D2633) else Accent, contentColor = if (riding) Color.White else Color.Black)
                    ) { Text(if (riding) "STOP TRIP" else "START TRIP", fontWeight = FontWeight.Bold) }
                }
            }
        }
    }
}

@Composable
private fun Metric(label: String, value: String, modifier: Modifier = Modifier) {
    Box(modifier.fillMaxWidth().background(Card, RoundedCornerShape(18.dp)).padding(14.dp)) {
        Column {
            Text(label, color = Muted, fontSize = 11.sp)
            Spacer(Modifier.height(5.dp))
            Text(value, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }
    }
}
