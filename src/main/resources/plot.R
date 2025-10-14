
library(lattice)

plot_function <- function(values){
  x <- seq(0, length(values) - 1)

  file_name <- tempfile(fileext = ".svg")
  svg(filename = file_name, width = 8, height = 6)



  plot <- xyplot(values ~ x,
                 type = "l",
                 col = "brown4",
                 lwd = 2,
                 main = "Live Plot From MongoDB (Col-14)",
                 xlab = "Index (0-99)",
                 ylab = "Value",
                 panel = function(...) {
                   panel.grid(h = -1, v = -1)
                   panel.xyplot(...)
                 })
  print(plot)
  dev.off()
  paste(readLines(file_name), collapse = "\n")
}
plot_function