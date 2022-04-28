package com.example.layoutscodelab

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.atLeast
import com.example.layoutscodelab.ui.theme.LayoutsCodelabTheme

@ExperimentalComposeUiApi
@Composable
fun ConstraintLayoutContent() {
    ConstraintLayout {

        // Create references for the composables to constrain
        val (button1, button2, text) = createRefs()

        OutlinedButton(
            onClick = { /* Do something */ },
            modifier = Modifier.constrainAs(button1) {
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            Text("Button 1")
        }
        Text("Text", Modifier.constrainAs(text) {
            top.linkTo(button1.bottom, margin = 16.dp)
            centerAround(button1.end)
        })
        val barrier = createEndBarrier(button1, text)
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.constrainAs(button2) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(barrier)
            }
        ) {
            Text("Button 2")
        }
    }
}

@Composable
fun LargeConstraintLayout() {
    ConstraintLayout {
        val text = createRef()

        val guideline = createGuidelineFromStart(fraction = 0.5f)
        Text(
            "This is a very very very very very very very very long text",
            Modifier.constrainAs(text) {
                linkTo(start = guideline, end = parent.end)
                width = Dimension.preferredWrapContent.atLeast(100.dp)
            }
        )
    }
}

@Composable
fun DecoupledConstraintLayout() {
    BoxWithConstraints {
        val constraints = if (maxWidth < maxHeight) decoupledConstraints(margin = 16.dp)
            else decoupledConstraints(margin = 32.dp)

        ConstraintLayout(constraints) {
            OutlinedButton(onClick = { /*TODO*/ },
                modifier = Modifier.layoutId("button")
            ) {
                Text("Button")
            }

            Text("Text", Modifier.layoutId("text"))
        }
    }
}

private fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val button = createRefFor("button")
        val text = createRefFor("text")

        constrain(button) { top.linkTo(parent.top, margin = margin) }
        constrain(text) {top.linkTo(button.bottom, margin)}
    }
}

@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun ConstraintLayoutContentPreview() {
    LayoutsCodelabTheme {
        ConstraintLayoutContent()
    }
}

@Preview(showBackground = true)
@Composable
fun LargeConstraintLayoutPreview(){
    LayoutsCodelabTheme{
        LargeConstraintLayout()
    }
}


@Preview(showBackground = true)
@Composable
fun DecoupledConstraintLayoutPreview(){
    LayoutsCodelabTheme{
        DecoupledConstraintLayout()
    }
}

/*
Available Dimension behaviors are:

→ preferredWrapContent - the layout is wrap content, subject to the constraints in that dimension.
→ wrapContent - the layout is wrap content even if the constraints would not allow it.
→ fillToConstraints - the layout will expand to fill the space defined by its constraints in that
    dimension.
→ preferredValue - the layout is a fixed dp value, subject to the constraints in that dimension.
→ value - the layout is a fixed dp value, regardless of the constraints in that dimension
 */