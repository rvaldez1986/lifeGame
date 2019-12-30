# lifeGame
Java program to play [Conway's Game of Life](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life)

![BeFunky-collage (1)](https://user-images.githubusercontent.com/19597283/70262813-0289ae00-1763-11ea-987b-540215ee196a.jpg)

### About

Entirely built on java, uses a console as graphical interface. Cells survive a stage based on the following rules:

* Any live cell with fewer than two live neighbours dies, as if by underpopulation.
* Any live cell with two or three live neighbours lives on to the next generation.
* Any live cell with more than three live neighbours dies, as if by overpopulation.
* Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.

The program reads the initial stage from a .dat file and plots it using a dot matrix.
