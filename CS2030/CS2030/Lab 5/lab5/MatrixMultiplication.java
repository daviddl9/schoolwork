import java.util.concurrent.RecursiveTask;

/**
 * This class encapsulates the parallel version of a matrix multiplication,
 * using the divide and conquer algorithm.
 */
class MatrixMultiplication extends RecursiveTask<Matrix> {
  public static final long serialVersionUID = 1; 
  private static final int THRESHOLD = 64;
  private Matrix a;
  private int aRow;
  private int aCol;
  private Matrix b;
  private int bRow;
  private int bCol;
  private int dimension;

  /**
   * Creates a new matrix multiplication task.
   * @param a The first Matrix to be multiplied.
   * @param b The second Matrix to be multiplied.
   * @param aRow The row of the first matrix to start the multiplication.
   * @param aCol The column of the first matrix to start multiplication.
   * @param bRow The row of the second matrix to start multiplication.
   * @param bCol The column of the second matrix to start multiplication.
   * @param dimension The dimension of the matrices to be multiplied.
   */
  MatrixMultiplication(Matrix a, Matrix b, int aRow, int aCol, int bRow,  int
      bCol, int dimension) {
    this.a = a;
    this.aRow = aRow;
    this.aCol = aCol;
    this.b = b;
    this.bRow = bRow;
    this.bCol = bCol;
    this.dimension = dimension;
  }

  /**
   * Compute the resulting matrix.
   * @return The resulting matrix of the matrix multiplication.
   */
  @Override
  public Matrix compute() {
    if (dimension <= THRESHOLD) {
      return Matrix.multiplyNonRecursively(this.a, this.b, aRow, aCol, bRow, bCol, dimension);
    } else {
      int size = this.dimension / 2;

      MatrixMultiplication a11b11 = new MatrixMultiplication(a, b, aRow,
          aCol,  bRow, bCol, size);
      MatrixMultiplication a12b21 = new MatrixMultiplication(a, b, aRow,
          aCol + size, bRow + size, bCol, size);
      MatrixMultiplication a11b12 = new MatrixMultiplication(a, b, aRow,
          aCol, bRow, bCol + size, size);
      MatrixMultiplication a12b22 = new MatrixMultiplication(a, b, aRow,
          aCol + size, bRow + size, bCol + size, size);
      MatrixMultiplication a21b11 = new MatrixMultiplication(a, b, aRow +
          size, aCol, bRow, bCol, size);
      MatrixMultiplication a22b21 = new MatrixMultiplication(a, b, aRow +
          size, aCol + size, bRow + size, bCol, size);
      MatrixMultiplication a21b12 = new MatrixMultiplication(a, b, aRow +
          size, aCol, bRow, bCol + size, size);
      MatrixMultiplication a22b22 = new MatrixMultiplication(a, b, aRow +
          size, aCol + size, bRow + size, bCol + size, size);

      a22b22.fork();
      a21b12.fork();
      a22b21.fork();
      a21b11.fork();
      a12b22.fork();
      a11b12.fork();
      a12b21.fork();
      a11b11.fork();

      Matrix c = a11b11.join();
      Matrix d = a12b21.join();
      Matrix e = a11b12.join();
      Matrix f = a12b22.join();
      Matrix g = a21b11.join();
      Matrix h = a22b21.join();    
      Matrix i = a21b12.join();
      Matrix j = a22b22.join();        

      return combine(c, d, e, f, g, h, i, j, dimension);
    }
  }

  /**
   * Combines the products of the matrix multiplication into a resulting matrix.
   * @param a The sub-matrix a.
   * @param b The sub-matrix b.
   * @param c The sub-matrix c.
   * @param d The sub-matrix d.
   * @param e The sub-matrix e.
   * @param f The sub-matrix f.
   * @param g The sub-matrix g.
   * @param h The sub-matrix h.
   * @param dimension The dimension of the resulting matrix.
   * @return The resulting matrix, after matrix multiplication.
   */
  private Matrix combine(Matrix a, Matrix b, Matrix c, Matrix d, Matrix e,
      Matrix f, Matrix g, Matrix h, int dimension) {
    Matrix result = new Matrix(dimension);
    int size = dimension / 2;
    for (int i = 0; i < size; i++) {
      double[] m1m = a.m[i];
      double[] m2m = b.m[i];
      double[] r1m = result.m[i];
      for (int j = 0; j < size; j++) {
        r1m[j] = m1m[j] + m2m[j];
      }
    }

    for (int i = 0; i < size; i++) {
      double[] m1m = c.m[i];
      double[] m2m = d.m[i];
      double[] r1m = result.m[i];
      for (int j = 0; j < size; j++) {
        r1m[j + size] = m1m[j] + m2m[j];
      }
    }

    for (int i = 0; i < size; i++) {
      double[] m1m = e.m[i];
      double[] m2m = f.m[i];
      double[] r1m = result.m[i + size];
      for (int j = 0; j < size; j++) {
        r1m[j] = m1m[j] + m2m[j];
      }
    }

    for (int i = 0; i < size; i++) {
      double[] m1m = g.m[i];
      double[] m2m = h.m[i];
      double[] r1m = result.m[i + size];
      for (int j = 0; j < size; j++) {
        r1m[j + size] = m1m[j] + m2m[j];
      }
    }
    return result;
  }

}
