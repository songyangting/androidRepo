package com.example.kotlinpractical05

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.*
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import com.example.kotlinpractical05.ui.theme.KotlinPractical05Theme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinPractical05Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TicTacToeGame()

                }
            }
        }
    }
}

@Composable
fun TicTacToeGame() {
    // State to track the state of each square in the Tic Tac Toe grid
//    var squares by remember { mutableStateOf(List(9) { "" }) }

    var squares = remember { mutableStateListOf<String>() }.apply {
        ifEmpty {
            repeat(9) {add("")}
        }
    }

    // State to track whose turn it is (X or O)
    var isPlayerXTurn by remember { mutableStateOf(true) }

    // State to update the title text
    var titleText by remember { mutableStateOf("") }

    // var to track error
    var showError by remember { mutableStateOf(false) }


    // Function to reset the game
    fun resetGame() {
        // Implement the logic to reset the game
        for (i in 0..8) {
            squares[i] = ""
        }
        titleText = ""
    }

    // Function to check if a player has won
    fun checkWin(): Boolean {
        // Implement the logic to check if a player has won
        // You can refer to the provided Java reference for Tic Tac Toe logic
        var lineList = mutableListOf<String>()

        // horizontal lines
        lineList.add(squares[0] + squares[1] + squares[2])
        lineList.add(squares[3] + squares[4] + squares[5])
        lineList.add(squares[6] + squares[7] + squares[8])

        // vertical lines
        lineList.add(squares[0] + squares[3] + squares[6])
        lineList.add(squares[1] + squares[4] + squares[7])
        lineList.add(squares[2] + squares[5] + squares[8])

        // diagonal lines
        lineList.add(squares[0] + squares[4] + squares[8])
        lineList.add(squares[2] + squares[4] + squares[6])

        return (lineList.contains("XXX") || lineList.contains("OOO"))
    }

    // Column composable that fills the entire vertical space with padding
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Text indicating whose turn it is, centered horizontally
        Text(
            text = if (titleText.isNotEmpty()) titleText else if (isPlayerXTurn) "Player X's Turn" else "Player O's Turn",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
        )

        AnimatedVisibility(visible = showError) {
            ErrorMessage(color = Color.Black)
        }


        // LazyVerticalGrid to display the Tic Tac Toe grid with 3 columns
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            // Iterate through the indices of the Tic Tac Toe grid
            items((0..8).toList()) { index ->
                // Button for each square in the grid
                Button(
                    onClick = {
                        // Check if the square is empty before making a move
                        // Update the square based on the current player's turn
                        // Make use of the checkWin() function to check is the user has won and update the titleText
                        // Switch to the other player's turn

                        if (squares[index] == "") {

                            showError = false

                            // make a move
                            squares[index] = if (isPlayerXTurn) "X" else "O"
                            if (checkWin()) {
                                titleText = if (isPlayerXTurn) "Player X won!!" else "Player O won!!"
                            } else if (!checkWin() && !squares.contains("")) {
                                titleText = "It's a draw!!"
                            }

                            isPlayerXTurn = !isPlayerXTurn

                        } else {
                            // tell user that square is already occupied
                            showError = true
                        }

                    },
                    // Button styling and appearance
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.Gray)
                        .padding(4.dp),
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White
                    )
                ) {
                    // Display the content of the square (X, O, or empty)
                    Text(
                        text = squares[index],
                        style = MaterialTheme.typography.titleMedium
                    )

                }
            }
        }

        // Reset button to reset the game
        Button(
            onClick = { resetGame() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Text(text = "Reset Game")
        }
    }
}

@Composable
fun ErrorMessage(color: Color = Color.White) {

    Text(
        text = "Square already occupied, choose another one.",
        modifier = Modifier
            .padding(bottom = 16.dp),
        color = color
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KotlinPractical05Theme {
        TicTacToeGame()

    }
}