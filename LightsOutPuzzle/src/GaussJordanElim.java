
public class GaussJordanElim {
	private double[][] matrix;
	private int rank;

	public GaussJordanElim(double[][] matrix) {
		this.matrix = matrix;
		makeJordanGaussElim();
		findRank();
	}
	
	public GaussJordanElim(int[][] matrix) {
		this.matrix = new double[matrix.length][matrix[0].length];
		for (int i = 0; i < matrix.length; i++){
			for (int j = 0; j < matrix[i].length; j++)
				this.matrix[i][j] = (double)matrix[i][j]; 
		}
		makeJordanGaussElim();
		findRank();
	}

	public void makeJordanGaussElim() {

		// cycle of Gauss's elimination
		GaussElim: {
			for (int p = 0, q = 0; p < matrix.length; p++, q++) {
				// p is row number, q is column number

				// find row with non-zero element of q-th column and make it
				// first
				// in the matrix
				boolean flag = true;
				do {
					if (matrix[p][q] == 0) {
						flag = false;
						for (int i = p + 1; i < matrix.length; i++) {
							if (matrix[i][q] != 0) {
								double buf[] = matrix[i];
								matrix[i] = matrix[p];
								matrix[p] = buf;
								flag = true;
								break;
							}
						}
					}
					if (!flag)
						if (q == matrix[p].length - 1) // if the last column
							break GaussElim; // every of rest elements are zero
						else // if not the last column
							q++;
				} while (!flag);
				// make first non-zero element of p-th row equals to 1, dividing
				// every elements of p-th row by the first element's value:
				for (int j = matrix[p].length - 1; j >= q; j--) {
					matrix[p][j] /= matrix[p][q];
				}
				// make all elements below the first non-zero element of p-th
				// row equals to 0:
				for (int i = p + 1; i < matrix.length; i++) {
					for (int j = matrix[i].length - 1; j >= q; j--) {
						matrix[i][j] += (-1) * matrix[p][j] * matrix[i][q];
					}
				}
			}
		}

		// cycle of Jordano's elimination
		for (int p = matrix.length - 1; p > 0; p--) {
			// p is row number, q is column number
			int q = 0;
			boolean flag = false;
			// find first non-zero element of p-th row equals to 1:
			for (; q < matrix[p].length; q++) {
				if (matrix[p][q] != 0) {
					flag = true;
					break;
				}
			}
			if (!flag)
				continue; // if all elements of p-th row equal to 0
			// make all elements above the first non-zero element of p-th row
			// equals to 0:
			for (int i = p - 1; i >= 0; i--) {
				for (int j = matrix[i].length - 1; j >= q; j--) {
					matrix[i][j] += (-1) * matrix[p][j] * matrix[i][q];
				}
			}
		}
	}

	public void findRank() {
		int rank = matrix.length;
		for (int i = matrix.length - 1; i >= 0; i--){
			boolean flag = false;
			for (int j = 0; j < matrix[i].length; j++){
				if (matrix[i][j] != 0)
					flag = true;
			}
			if (!flag)
				rank--;
		}
		this.rank = rank;
	}
	
	public int getRank(){
		return rank;
	}

	public void printMatrix() {
		for (double a[] : matrix) {
			for (double b : a)
				System.out.printf("%.2f ", b);
			System.out.println();
		}
		System.out.println("Rank of matrix is: " + rank);
	}
}
