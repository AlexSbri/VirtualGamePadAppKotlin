import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.gamepadtcpudp.ui.theme.GamePadTCPUDPTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.div
import kotlin.math.hypot

@Composable
fun VirtualJoystick(
    modifier: Modifier = Modifier,
    size: Dp = 150.dp,
    onMove: (Offset) -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    val radius = with(LocalDensity.current) { size.toPx() / 2 }
    val center = remember { Offset(radius, radius) }
    val knobPosition = remember { Animatable(center, Offset.VectorConverter) }

    Box(
        modifier = modifier
            .size(size)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        // Torna al centro con animazione
                        onMove(Offset.Zero)
                        coroutineScope.launch {
                            knobPosition.animateTo(center, animationSpec = tween(100))
                            //onMove(Offset.Zero)
                        }
                    },
                    onDrag = { change, dragAmount ->

                        val newOffset = (knobPosition.value + dragAmount).let {
                            val dx = it.x - radius
                            val dy = it.y - radius
                            val distance = hypot(dx, dy)
                            if (distance > radius) {
                                val scale = radius / distance
                                Offset(dx * scale + radius, dy * scale + radius)
                            } else it
                        }
                        coroutineScope.launch {
                            knobPosition.snapTo(newOffset)
                        }
                        val delta = Offset(
                            x = (knobPosition.value.x - radius) / radius,
                            y = (knobPosition.value.y - radius) / radius
                        )
                        onMove(delta)
                        change.consume()
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Canvas (modifier = Modifier
            .fillMaxSize()
        ){
            // Outer circle
            drawCircle(
                color = Color.Gray.copy(alpha = 0.3f),
                radius = radius
            )

            // Inner knob
            drawCircle(
                color = Color.DarkGray,
                radius = radius / 2,
                center = knobPosition.value
            )

        }
    }
}



@Preview
@Composable
fun PreviewAnalog(){
    GamePadTCPUDPTheme {
        VirtualJoystick()
    }
}
