import java.io.*;
import java.util.ArrayList;

public class EnterMatrix {
	private int[][] matrix;

	public EnterMatrix() {
		ArrayList<Integer[]> matrixRead = new ArrayList<Integer[]>();
		Integer[] rowRead = new Integer[0];
		String str;
		BufferedReader br;

		try {
			br = new BufferedReader(new FileReader("bin/input"));
			while ((str = br.readLine()) != null) {
				if (str.equals(""))
					continue;
				int countElem = 0;
				for (int i = 0; i < str.length(); i++) {
					if (str.charAt(i) == ',')
						countElem++;
				}
				rowRead = new Integer[++countElem];
				int pos = 0;
				for (int i = 0; i < countElem - 1; i++) {
					rowRead[i] = Integer.parseInt(str.substring(pos, (pos = str.indexOf(',', pos))));
					pos++;
				}
				rowRead[countElem - 1] = Integer.parseInt(str.substring(pos));
				matrixRead.add(rowRead);

			}
			matrixRead.trimToSize();
			matrix = new int[matrixRead.size()][rowRead.length];
			for (int i = 0; i < matrixRead.size(); i++){
				Integer[] buf = matrixRead.get(i);
				for (int j = 0; j < buf.length; j++)
					matrix[i][j] = (int)buf[j]; 
			}

		} catch (IOException exc) {
			System.out.println("I/O Error: " + exc);
		}
	}

	public int[][] getMatrix() {
		return matrix;
	}
}
