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
import java.text.NumberFormat
import java.util.Locale

private val Bg = Color(0xFF07090D)
private val Panel = Color(0xFF11151D)
private val Panel2 = Color(0xFF181D27)
private val Accent = Color(0xFF62E6FF)
private val Green = Color(0xFF70E5A0)
private val Amber = Color(0xFFFFC857)
private val Muted = Color(0xFF8D98A8)

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
    var destination by remember { mutableStateOf("") }
    val fuelEfficiency = 40.0
    val fuelPrice = 10000.0
    val liters = distanceKm / fuelEfficiency
    val cost = liters * fuelPrice
    val rupiah = NumberFormat.getCurrencyInstance(Locale("id", "ID")).format(cost).replace(",00", "")

    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { }
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
        Column(Modifier.fillMaxSize().padding(horizontal = 18.dp, vertical = 12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Box(Modifier.size(38.dp).background(Accent, RoundedCornerShape(11.dp)), contentAlignment = Alignment.Center) {
                        Text("F", color = Bg, fontSize = 22.sp, fontWeight = FontWeight.Black)
                    }
                    Column {
                        Text("FIRMAN.OS", color = Color.White, fontSize = 23.sp, fontWeight = FontWeight.Black)
                        Text("RIDE COMPUTER • ANDROID 14 • V0.2", color = Muted, fontSize = 10.sp)
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("GPS ●", color = Green, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Text(if (riding) "● RIDING" else "● READY", color = if (riding) Accent else Muted, fontWeight = FontWeight.Bold)
                }
            }

            Row(Modifier.weight(1f), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Column(Modifier.weight(1.25f).fillMaxHeight(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Box(Modifier.weight(1f).fillMaxWidth().background(Panel, RoundedCornerShape(24.dp)), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("LIVE SPEED", color = Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            Text(String.format(Locale.US, "%.0f", speed), color = Color.White, fontSize = 86.sp, fontWeight = FontWeight.Black)
                            Text("KM/H", color = Accent, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    Row(Modifier.height(72.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        SmallMetric("AVG", if (seconds > 0) String.format(Locale.US, "%.0f", distanceKm / (seconds / 3600.0)) else "0", "KM/H", Modifier.weight(1f))
                        SmallMetric("EFFICIENCY", String.format(Locale.US, "%.0f", fuelEfficiency), "KM/L", Modifier.weight(1f))
                    }
                }

                Column(Modifier.weight(1f).fillMaxHeight(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Metric("TRIP TIME", "%02d:%02d:%02d".format(seconds / 3600, (seconds % 3600) / 60, seconds % 60), Accent, Modifier.weight(1f))
                    Metric("DISTANCE", String.format(Locale.US, "%.2f KM", distanceKm), Color.White, Modifier.weight(1f))
                    Metric("FUEL USED", String.format(Locale.US, "%.2f L", liters), Amber, Modifier.weight(1f))
                    Metric("FUEL COST", rupiah, Green, Modifier.weight(1f))
                }

                Column(Modifier.weight(1.25f).fillMaxHeight(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Column(Modifier.weight(1f).fillMaxWidth().background(Panel, RoundedCornerShape(22.dp)).padding(15.dp)) {
                        Text("NAVIGATION", color = Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(7.dp))
                        OutlinedTextField(
                            value = destination,
                            onValueChange = { destination = it },
                            placeholder = { Text("Tujuan: Monas, kantor, rumah…") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                        )
                        Spacer(Modifier.height(8.dp))
                        Text("Google Maps akan memakai posisi GPS perangkat sebagai titik awal.", color = Muted, fontSize = 10.sp)
                    }
                    Button(
                        onClick = {
                            val q = Uri.encode(destination.ifBlank { "Jakarta" })
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=$q&mode=d"))
                            intent.setPackage("com.google.android.apps.maps")
                            runCatching { context.startActivity(intent) }.onFailure {
                                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query=$q")))
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(52.dp)
                    ) { Text("NAVIGATE WITH GOOGLE MAPS", fontWeight = FontWeight.Bold) }
                    Button(
                        onClick = {
                            if (!riding) {
                                seconds = 0; distanceKm = 0.0; speed = 0.0
                            }
                            riding = !riding
                        },
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = if (riding) Color(0xFF9C3040) else Accent, contentColor = if (riding) Color.White else Bg)
                    ) { Text(if (riding) "■  END RIDE" else "▶  START RIDE", fontWeight = FontWeight.Black) }
                }
            }
        }
    }
}

@Composable
private fun Metric(label: String, value: String, valueColor: Color, modifier: Modifier = Modifier) {
    Box(modifier.fillMaxWidth().background(Panel, RoundedCornerShape(18.dp)).padding(horizontal = 14.dp, vertical = 10.dp)) {
        Column {
            Text(label, color = Muted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(3.dp))
            Text(value, color = valueColor, fontSize = 20.sp, fontWeight = FontWeight.Black)
        }
    }
}

@Composable
private fun SmallMetric(label: String, value: String, unit: String, modifier: Modifier = Modifier) {
    Box(modifier.fillMaxHeight().background(Panel2, RoundedCornerShape(17.dp)).padding(10.dp)) {
        Column {
            Text(label, color = Muted, fontSize = 9.sp)
            Row(verticalAlignment = Alignment.Bottom) {
                Text(value, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Black)
                Spacer(Modifier.width(4.dp)); Text(unit, color = Muted, fontSize = 9.sp)
            }
        }
    }
}
